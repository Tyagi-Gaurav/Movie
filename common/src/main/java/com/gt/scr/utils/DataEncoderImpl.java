package com.gt.scr.utils;

import org.bouncycastle.util.encoders.Encoder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class DataEncoderImpl implements DataEncoder {

    private final Encoder encoder;

    public DataEncoderImpl(Encoder encoder) {
        this.encoder = encoder;
    }

    public String encode(String originalString) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        encoder.encode(originalString.getBytes(StandardCharsets.UTF_8), 0,
                originalString.length(), byteArrayOutputStream);

        return byteArrayOutputStream.toString(Charset.defaultCharset());
    }

}
