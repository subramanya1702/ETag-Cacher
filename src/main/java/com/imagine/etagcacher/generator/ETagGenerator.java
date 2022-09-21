package com.imagine.etagcacher.generator;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.util.DigestUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ETagGenerator {

    public static String generateETag(final Object inputObject,
                                      final boolean isWeakETag) throws IOException {
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        final ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);

        objectOutputStream.writeObject(inputObject);
        objectOutputStream.flush();
        objectOutputStream.close();

        final InputStream inputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
        StringBuilder builder = new StringBuilder(37);
        if (isWeakETag) {
            builder.append("W/");
        }
        builder.append("\"0");
        DigestUtils.appendMd5DigestAsHex(inputStream, builder);
        builder.append('"');

        return builder.toString();
    }
}
