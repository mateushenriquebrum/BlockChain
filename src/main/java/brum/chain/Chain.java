package brum.chain;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.IntStream.*;


public class Chain {
    final private List<Block> chaining = new ArrayList<>();
    private final Integer difficulty;

    public Chain(Integer difficulty) {
        this.difficulty = difficulty;
    }

    public Chain mine(Data data) {
        if (chaining.isEmpty()) chaining.add(Block.genesis());
        chaining.add(Block.of(chaining.getLast().mine, data, difficulty));
        return this;
    }

    public Boolean isValid() {
        var first = chaining.stream().limit(chaining.size() - 1).toList();
        var second = chaining.stream().skip(1).toList();
        return range(0, chaining.size() - 1)
                .mapToObj(index -> new Block[]{first.get(index), second.get(index)})
                .allMatch(pair -> pair[1].isValid() && pair[0].mine.equals(pair[1].parent));
    }
}
