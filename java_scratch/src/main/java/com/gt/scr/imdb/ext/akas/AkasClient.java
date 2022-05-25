package com.gt.scr.imdb.ext.akas;

import com.gt.scr.imdb.common.DataReader;
import com.gt.scr.imdb.common.TSVFileReader;
import com.gt.scr.imdb.ext.akas.domain.Aka;
import reactor.core.publisher.Mono;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class AkasClient implements AkasReader {

    public AkasClient(DataReader dataReader) {
        try {
            final Map<String, List<Integer>> keyMap = new HashMap<>();
            String fileName = "/title.akas.tsv";
            System.out.println("Before Start (In kb): " + Runtime.getRuntime().freeMemory() / 1000);
            final long startTime = System.currentTimeMillis();
            final URL resource = TSVFileReader.class.getResource(fileName);
            final RandomAccessFile randomAccessFile = new RandomAccessFile(new File(resource.getPath()), "r");

            final int BLOCK_SIZE = 2 * 1024 * 1024;
            int totalRow = 0;
            final AtomicInteger position = new AtomicInteger();
            final FileChannel channel = randomAccessFile.getChannel();
            final ByteBuffer byteBuffer = ByteBuffer.allocateDirect(BLOCK_SIZE);
            final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            while (channel.read(byteBuffer) > 0) {
                int i = 0;
                while (i < byteBuffer.limit()) {
                    int end = getRow(i, byteBuffer, byteArrayOutputStream, position);
                    if (end + 1 < byteBuffer.limit() && byteBuffer.get(end) == 10) {
                        final byte[] rowAsBytes = byteArrayOutputStream.toByteArray();
                        final String title = findTitle(rowAsBytes, position);
                        int finalI = i;
                        keyMap.compute(title, (key1, oldValue) -> {
                            if (oldValue == null) {
                                final List<Integer> objects1 = new ArrayList<>(5);
                                objects1.add(finalI);
                                return objects1;
                            } else {
                                oldValue.add(finalI);
                                return oldValue;
                            }
                        });
                        byteArrayOutputStream.reset();
                        totalRow++;
                    } else {
                        break;
                    }
                    i = end + 1;
                }
                byteBuffer.clear();
            }
            final long endTime = System.currentTimeMillis();
            System.out.println("Total rows " + totalRow + ", time taken(In sec): " + (endTime - startTime)/1000);
            System.out.println("Objects size: " + keyMap.size());
            final List<Integer> tt0000001 = keyMap.get("tt0000001");
            tt0000001.forEach(id -> {
                try {
                    randomAccessFile.seek(id);
                    String line = randomAccessFile.readLine();
                    String utf8 = new String(line.getBytes("ISO-8859-1"), "UTF-8");
                    System.out.println("Line: " + utf8);
                } catch (IOException e) {
                    throw new IllegalStateException(e);
                }
            });

        } catch (IOException exception) {
            throw new IllegalStateException(exception);
        }
    }

    private String findTitle(byte[] rowAsbytes, AtomicInteger position) {
        return new String(rowAsbytes, 0, position.get(), StandardCharsets.UTF_8);
    }

    private int getRow(int start, ByteBuffer bytes, ByteArrayOutputStream byteArrayOutputStream, AtomicInteger position) {
        int i = start;
        boolean found = false;
        while (i < bytes.limit() && bytes.get(i) != 10) {
            byteArrayOutputStream.write(bytes.get(i));
            if (!found && bytes.get(i) == 9) {
                position.set(i - start);
                found = true;
            }
            ++i;
        }

        return i;
    }

    @Override
    public Mono<Aka> getTitleById(String titleId) {
        return Mono.empty();
    }

    @Override
    public long getTotalNumberOfTitles() {
        return 0L;
    }
}
