package brum;

import org.junit.jupiter.api.Test;

import static brum.Signature.genesis;
import static org.junit.jupiter.api.Assertions.*;

public class BlockTest {
    @Test
    public void whenMakingTheGenesisBlockShouldBePopulated() {
        var block = Block.genesis();
        assertNotNull(block);
        assertNotNull(block.mine);
        assertEquals(genesis(), block.parent);
        assertEquals(new Data("00"), block.content);
    }

    @Test
    public void whenChainingBlocksShouldReferToThePreviousHash() {
        var genesis = Block.genesis();
        var first = Block.of(genesis.mine, new Data("1"), 1);
        var second = Block.of(first.mine, new Data("2"), 1);

        assertEquals(genesis.mine, first.parent);
        assertEquals(first.mine, second.parent);
        assertNotEquals(genesis.mine, first.mine);
        assertNotEquals(first.mine, second.mine);
        assertNotEquals(genesis.mine, second.mine);
    }
}
