package com.gt.scr.user.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gt.scr.domain.User;
import com.gt.scr.user.service.domain.Role;
import com.gt.scr.utils.JwtTokenUtil;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;

public class TestCredentialsGenerator {
    private static final Logger LOG = LoggerFactory.getLogger(TestCredentialsGenerator.class);

    public static void main(String[] args) throws IOException {
        String fileName = "conf/config.json";
        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File(fileName);
        Map<String, Object> map = objectMapper.readValue(file, Map.class);

        String secret = (String) map.get("auth.token.key");
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(secret);
        SecretKeySpec secretKeySpec = new SecretKeySpec(apiKeySecretBytes, SignatureAlgorithm.HS256.getJcaName());

        String userName = RandomStringUtils.randomAlphabetic(6);
        String firstName = RandomStringUtils.randomAlphabetic(6);
        String lastName = RandomStringUtils.randomAlphabetic(6);
        String password = RandomStringUtils.randomAlphabetic(6);
        String role = Role.ADMIN.toString();
        User user = new User(UUID.randomUUID(),
                firstName,
                lastName,
                userName,
                password,
                Collections.singletonList(role));
        Duration tokenDuration = Duration.ofDays(3650);

        String token = JwtTokenUtil.generateTokenV2(user, tokenDuration, secretKeySpec);
        LOG.info("token: {}", token);
        LOG.info("User: {}", user);
    }
}
