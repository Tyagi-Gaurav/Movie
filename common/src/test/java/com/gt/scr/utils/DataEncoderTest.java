package com.gt.scr.utils;

import org.bouncycastle.util.encoders.Encoder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class DataEncoderTest {

    @Mock
    private Encoder encoder;

    private DataEncoder dataEncoder;

    @BeforeEach
    void setUp() {
        dataEncoder = new DataEncoderImpl(encoder);
    }

    @Test
    void shouldEncodeString() throws IOException {
        String stringToEncode = "stringToEncode";
        String expectedEncodedString = "encodedString";

        given(encoder.encode(eq(stringToEncode.getBytes(StandardCharsets.UTF_8)), eq(0),
                eq(stringToEncode.length()),
                any(ByteArrayOutputStream.class)))
                .willAnswer(invocation -> {
                    ByteArrayOutputStream outputStream = invocation.getArgument(3);
                    outputStream.writeBytes(expectedEncodedString.getBytes(StandardCharsets.UTF_8));
                    return null;
                });

        String encodedString = dataEncoder.encode(stringToEncode);

        assertThat(encodedString).isEqualTo(expectedEncodedString);
    }
}