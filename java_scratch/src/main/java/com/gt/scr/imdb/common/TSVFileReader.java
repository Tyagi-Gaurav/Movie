package com.gt.scr.imdb.common;

import reactor.core.publisher.Flux;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class TSVFileReader extends DataReader {
    private final RandomAccessFile randomAccessFile;

    public TSVFileReader(String fileName) {
        InputStream resourceAsStream = TSVFileReader.class.getResourceAsStream(fileName);
        final URL resource = TSVFileReader.class.getResource(fileName);
        try {
            final File file = new File(resource.toURI().getPath());
            randomAccessFile = new RandomAccessFile(file, "r");
        } catch (URISyntaxException | FileNotFoundException e) {
            throw new IllegalStateException(e);
        }
        assertThat(resourceAsStream).describedAs("Could not find file " + fileName).isNotNull();
    }

    @Override
    public void load(Map<String, List<Integer>> keyMap) {
        try {
            final int BLOCK_SIZE = 2 * 1024 * 1024;
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
                        final String title = new String(rowAsBytes, 0, position.get(), StandardCharsets.UTF_8);
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
                    } else {
                        break;
                    }
                    i = end + 1;
                }
                byteBuffer.clear();
            }
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public Flux<String> readRowsAtLocation(List<Integer> rows) {
        return Flux.fromIterable(rows.stream()
                .map(rowPos -> {
                    try {
                        randomAccessFile.seek(rowPos);
                        String line = randomAccessFile.readLine();
                        return new String(line.getBytes("ISO-8859-1"), "UTF-8");
                    } catch (IOException e) {
                        throw new IllegalStateException(e);
                    }
                })
                .collect(Collectors.toList()));
    }

    private int getRow(int start, ByteBuffer bytes,
                       ByteArrayOutputStream byteArrayOutputStream,
                       AtomicInteger position) {
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
}
