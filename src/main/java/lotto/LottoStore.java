package lotto;

import java.util.List;
import java.util.stream.IntStream;
import util.ErrorMessage;

public class LottoStore {

    private final LottoMachine lottoMachine;

    public LottoStore(LottoMachine lottoMachine) {
        this.lottoMachine = lottoMachine;
    }

    public PurchasedLotto sell(int amount) {
        if (amount < 1000) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_AMOUNT);
        }
        if (amount % Lotto.AMOUNT != 0) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_AMOUNT_UNIT);
        }
        int lottoCount = amount / Lotto.AMOUNT;
        List<Lotto> lottos = IntStream.range(0, lottoCount)
                .mapToObj(e -> lottoMachine.getLotto())
                .toList();
        return new PurchasedLotto(lottos);
    }

}
