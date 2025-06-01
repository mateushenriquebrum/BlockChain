package brum.shared;

import java.security.MessageDigest;

import static java.nio.charset.StandardCharsets.UTF_8;

public class Digest {
    public static String hash(String... parameters) {
        try {
            var chain = new StringBuilder();
            var input = String.join(",",parameters);
            var digest = MessageDigest.getInstance("SHA-256");
            byte[] hashes = digest.digest(input.getBytes(UTF_8));
            chain = new StringBuilder();
            for (byte hash : hashes) {
                String hex = Integer.toHexString(0xff & hash);
                if(hex.length() == 1) chain.append('0');
                chain.append(hex);
            }
            return chain.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
