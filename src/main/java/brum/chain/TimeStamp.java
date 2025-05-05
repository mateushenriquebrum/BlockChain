package brum.chain;

import java.time.ZonedDateTime;
record TimeStamp(ZonedDateTime time) {
    public Long toEpochSecond() {
        return this.time.toEpochSecond();
    }
}
