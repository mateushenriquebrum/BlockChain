package brum.chain;

import static java.time.ZonedDateTime.now;
import static java.time.ZoneOffset.*;

class Block {
    public final Signature mine;
    public final Signature parent;
    public final Data content;
    public final TimeStamp at;

    private Block(Signature mine, Signature parent, Data content, TimeStamp at) {
        this.content = content;
        this.mine = mine;
        this.at = at;
        this.parent = parent;
    }

    public static Block of(Signature theirs, Data content, Integer difficulty) {
        var at = new TimeStamp(now(UTC));
        return new Block(Signature.of(theirs, content, at, difficulty), theirs, content, at);
    }

    public static Block genesis() {
        return of(Signature.genesis(), new Data("00"), 2);
    }

    public boolean isValid() {
        return this.mine.equals(Signature.of(parent, content, at, mine.difficulty));
    }
}
