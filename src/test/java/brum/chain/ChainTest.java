package brum.chain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ChainTest {

    @Test
    public void whenValidateBlockChain() {
        var chain = new Chain(0);
        chain
                .mine(new Data("1"))
                .mine(new Data("2"));
        assertTrue(chain.isValid());
    }
}
