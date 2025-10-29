package lotto;

import strategy.NumberStrategy;

public class LottoMachine {

    private final NumberStrategy numberStrategy;

    public LottoMachine(NumberStrategy numberStrategy) {
        this.numberStrategy = numberStrategy;
    }

    public Lotto getLotto() {
        return new Lotto(numberStrategy.getNumbers());
    }

}
