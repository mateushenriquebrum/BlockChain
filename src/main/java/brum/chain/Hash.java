package brum.chain;

import brum.shared.Digest;

import java.util.Objects;

class Hash {
    final String value;
    final Integer nonce;
    final Integer difficulty;

    private Hash(String hash, Integer difficulty, Integer nonce) {
        if (
                hash.length() != 64 &&
                hash.length() != 2 &&
                !hash.startsWith(stampOf(difficulty))
        ) throw new IllegalArgumentException();
        this.value = hash;
        this.nonce = nonce;
        this.difficulty = difficulty;
    }

    public static Hash of(Hash previous, Data content, TimeStamp at, Integer difficulty) {
        var nonce = 0;
        String chain;
        do {
            chain = Digest.hash(previous.value, content.raw(), at.toEpochSecond().toString(), String.valueOf(nonce));
            nonce ++;
        } while (!chain.startsWith(stampOf(difficulty)));
        return new Hash(chain, difficulty, nonce);
    }

    private static String stampOf(Integer difficulty) {
        return new String(new char[difficulty]).replace('\0', '0');
    }

    public static Hash genesis() {
        return new Hash(stampOf(2), 2, 0);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Hash hash = (Hash) o;
        return Objects.equals(value, hash.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}