package lotto;

import java.util.List;
import java.util.stream.IntStream;
import util.ErrorMessage;

public class LottoStore {

    private static final int ZERO = 0;
    private final LottoMachine lottoMachine;

    public LottoStore(LottoMachine lottoMachine) {
        this.lottoMachine = lottoMachine;
    }

    public PurchasedLotto sell(int amount) {
        if (amount < Lotto.AMOUNT) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_AMOUNT);
        }
        if (amount % Lotto.AMOUNT != ZERO) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_AMOUNT_UNIT);
        }
        int lottoCount = amount / Lotto.AMOUNT;
        List<Lotto> lottos = IntStream.range(ZERO, lottoCount)
                .mapToObj(e -> lottoMachine.getLotto())
                .toList();
        return new PurchasedLotto(lottos);
    }

}
