package brum.wallet;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class WalletTest {
    @Test
    public void whenCreatingWallet() {
        var wallet = new Wallet();
        assertNotNull(wallet.address);
        assertNotNull(wallet.sign);
    }
}
