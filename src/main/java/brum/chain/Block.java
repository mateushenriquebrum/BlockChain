package brum.chain;

import static java.time.ZonedDateTime.now;
import static java.time.ZoneOffset.*;

class Block {
    public final Hash mine;
    public final Hash parent;
    public final Data content;
    public final TimeStamp at;

    private Block(Hash mine, Hash parent, Data content, TimeStamp at) {
        this.content = content;
        this.mine = mine;
        this.at = at;
        this.parent = parent;
    }

    public static Block of(Hash theirs, Data content, Integer difficulty) {
        var at = new TimeStamp(now(UTC));
        return new Block(Hash.of(theirs, content, at, difficulty), theirs, content, at);
    }

    public static Block genesis() {
        return of(Hash.genesis(), new Data("00"), 2);
    }

    public boolean isValid() {
        return this.mine.equals(Hash.of(parent, content, at, mine.difficulty));
    }
}
