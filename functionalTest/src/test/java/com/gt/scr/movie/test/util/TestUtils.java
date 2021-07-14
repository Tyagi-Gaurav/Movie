package com.gt.scr.movie.test.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class TestUtils {
    public static byte[] readByteStreamFor(String videoFileName) throws IOException {
        try (InputStream inputStream = TestUtils.class.getClassLoader().getResourceAsStream("videos/"+videoFileName);
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            byte[] content = new byte[1024];

            while (inputStream != null && inputStream.available() > 0) {
                int bytesRead = inputStream.read(content);
                out.write(content, 0, bytesRead);
            }

            return out.toByteArray();
        }
    }
}
