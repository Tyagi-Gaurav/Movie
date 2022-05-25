package com.gt.scr.imdb.ext.akas;

import com.gt.scr.imdb.common.DataReader;
import com.gt.scr.imdb.common.TSVFileReader;
import com.gt.scr.imdb.ext.akas.domain.Aka;
import reactor.core.publisher.Mono;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public class AkasClient implements AkasReader {

    public AkasClient(DataReader dataReader) {
        try {
            final Set<String> objects = new HashSet<>();
            final Map<String, List<Integer>> keyMap = new HashMap<>();
            String fileName = "/title.akas.tsv";
            System.out.println("Before Start (In kb): " + Runtime.getRuntime().freeMemory() / 1000);
            final long startTime = System.currentTimeMillis();
            InputStream resourceAsStream = TSVFileReader.class.getResourceAsStream(fileName);
            final URL resource = TSVFileReader.class.getResource(fileName);
            final RandomAccessFile randomAccessFile = new RandomAccessFile(new File(resource.getPath()), "r");

            final int BLOCK_SIZE = 2 * 1024 * 1024;
            int totalRow = 0;
            final byte[] bytes = new byte[BLOCK_SIZE];
            int off = 0;
            final AtomicInteger position = new AtomicInteger();
            final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            while (resourceAsStream.available() > 0) {
                resourceAsStream.read(bytes, off, BLOCK_SIZE);
                int i = 0;
                while (i < bytes.length) {
                    int end = getRow(i, bytes, byteArrayOutputStream, position);
                    if (end + 1 < bytes.length && bytes[end] == 10) {
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
                        //randomAccessFile.seek(i);
                        //System.out.println("Row: " + randomAccessFile.readLine());
                        //System.out.println(byteArrayOutputStream);
                        byteArrayOutputStream.reset();
                        totalRow++;
                        //Thread.sleep(2000);
                    } else {
//                        System.out.println("Reached end of stream now. Reading data again.: " + i + ", len: " + bytes.length + ", " +
//                                "end: " + end);
                        //Thread.sleep(2000);
                        break;
                    }
                    i = end + 1;
                }
            }
            final long endTime = System.currentTimeMillis();
            System.out.println("Total rows " + totalRow + ", time taken(In sec): " + (endTime - startTime)/1000);
            System.out.println("Objects size: " + keyMap.size());
            final List<Integer> tt0000001 = keyMap.get("tt0000001");
            tt0000001.forEach(id -> {
                try {
                    randomAccessFile.seek(id);
                    System.out.println(randomAccessFile.readLine());
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

    private int getRow(int start, byte[] bytes, ByteArrayOutputStream byteArrayOutputStream, AtomicInteger position) {
        int i = start;
        boolean found = false;
        while (i < bytes.length && bytes[i] != 10) {
            byteArrayOutputStream.write(bytes[i]);
            if (!found && bytes[i] == 9) {
                position.set(i - start);
                found = true;
            }
            ++i;
        }

        return i;
    }

    @Override
    public Mono<Aka> getTitleById(String titleId) {
        return Mono.empty(); //justOrEmpty(akasMap.get(titleId));
    }

    @Override
    public long getTotalNumberOfTitles() {
        return 0L;
    }
}
