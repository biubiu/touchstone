package com.shawn.touchstone.idgen;

import com.google.common.annotations.VisibleForTesting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Objects;
import java.util.Random;

public class RandomIdGenerator implements LogTraceIdGenerator {

    private static final Logger logger = LoggerFactory.getLogger(IdGenerator.class);

    public String generate() {
        String id = "";
        String hostName = getLastFieldOfHostName();
        String randomChars = generateRandomAlphameric(8);
        id = String.format("%s-%d-%s", hostName,
                System.currentTimeMillis(), randomChars);

        return id;
    }

    private String getLastFieldOfHostName() {
        try {
            String hostname = InetAddress.getLocalHost().getHostName();
            return getLastSubstrSplittedByDot(hostname);
        } catch (UnknownHostException e) {
            logger.warn("failed to get host name");
        }
        return null;
    }

    @VisibleForTesting
    protected String getLastSubstrSplittedByDot(String hostname) {
        if (hostname == null || hostname.isEmpty()) {
            throw new IllegalArgumentException();
        }
        String[] tokens = hostname.split("\\.");
        String substrOfHostName = tokens[tokens.length - 1];
        return substrOfHostName;
    }

    @VisibleForTesting
    protected String generateRandomAlphameric(int len) {
        if (len <= 0) {
            throw new IllegalArgumentException();
        }
        char[] randomChars = new char[len];
        int count = 0;
        Random random = new Random();

        while (count < len) {
            int max = 'z';
            int randomAscii = random.nextInt(max);
            boolean isDigit = randomAscii >= '0' && randomAscii < '9';
            boolean isUppercase = randomAscii >= 'A' && randomAscii <= 'Z';
            boolean isLowercase = randomAscii >= 'a' && randomAscii <= 'z';
            if (isDigit || isUppercase || isLowercase) {
                randomChars[count++] = (char) (randomAscii);
            }
        }
        return String.valueOf(randomChars);
    }
}
