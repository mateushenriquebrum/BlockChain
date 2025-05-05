package brum.wallet;

import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;

public class Wallet {
    public final PrivateKey sign;
    public final PublicKey address;

    public Wallet() {
        try {
            var gen = KeyPairGenerator.getInstance("RSA");
            gen.initialize(2048);
            var pair = gen.generateKeyPair();
            this.address = pair.getPublic();
            this.sign = pair.getPrivate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
