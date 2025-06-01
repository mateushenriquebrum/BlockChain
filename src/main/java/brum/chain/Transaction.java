package brum.chain;

import brum.shared.Digest;

import java.security.PublicKey;
import java.util.Base64;
import java.util.List;
import java.util.function.Function;

import static java.lang.String.format;

public class Transaction {
    private final PublicKey sender;
    private final PublicKey receipt;
    private final Long funds;
    private final List<TransactionInputs> inputs;
    private Integer sequence = 0;

    public Transaction(PublicKey sender, PublicKey receipt, Long funds, List<TransactionInputs> inputs) {
        this.sender = sender;
        this.receipt = receipt;
        this.funds = funds;
        this.inputs = inputs;
    }

    private String hash() {
        Function<PublicKey, String> asString = (PublicKey key) -> Base64.getEncoder().encodeToString(key.getEncoded());
        sequence++;
        return Digest.hash(asString.apply(this.sender), asString.apply(this.receipt), String.valueOf(this.funds), String.valueOf(this.sequence));
    }
}
