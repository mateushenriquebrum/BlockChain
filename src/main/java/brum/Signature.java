package brum;

import java.security.MessageDigest;
import java.util.Objects;

import static java.lang.String.format;
import static java.nio.charset.StandardCharsets.UTF_8;

class Signature {
    final String value;
    final Integer nonce;
    final Integer difficulty;

    private Signature(String hash, Integer difficulty, Integer nonce) {
        if (
                hash.length() != 64 &&
                hash.length() != 2 &&
                !hash.startsWith(stampOf(difficulty))
        ) throw new IllegalArgumentException();
        this.value = hash;
        this.nonce = nonce;
        this.difficulty = difficulty;
    }

    public static Signature of(Signature previous, Data content, TimeStamp at, Integer difficulty) {
        var nonce = 0;
        try {
            StringBuilder chain;
            do {
                var input = format("%s%s%s%s", previous.value, content.raw(), at.toEpochSecond(), nonce);
                var digest = MessageDigest.getInstance("SHA-256");
                byte[] hashes = digest.digest(input.getBytes(UTF_8));
                chain = new StringBuilder();
                for (byte hash : hashes) {
                    String hex = Integer.toHexString(0xff & hash);
                    if(hex.length() == 1) chain.append('0');
                    chain.append(hex);
                }
                nonce ++;
            } while (!chain.toString().startsWith(stampOf(difficulty)));

            return new Signature(chain.toString(), difficulty, nonce);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static String stampOf(Integer difficulty) {
        return new String(new char[difficulty]).replace('\0', '0');
    }

    public static Signature genesis() {
        return new Signature(stampOf(2), 2, 0);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Signature signature = (Signature) o;
        return Objects.equals(value, signature.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}