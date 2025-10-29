package config;

import lotto.LottoMachine;
import lotto.LottoParser;
import lotto.LottoStore;
import reader.ConsoleReader;
import strategy.RandomNumbersGenerateStrategy;
import util.RetryTemplate;
import view.LottoPurchaseContext;
import writer.Writer;

public class ApplicationComponentConfig {

    public static LottoPurchaseContext lottoPurchaseContext() {
        return new LottoPurchaseContext(
                new ConsoleReader(),
                new Writer(),
                new LottoParser(),
                new LottoStore(new LottoMachine(new RandomNumbersGenerateStrategy())),
                new RetryTemplate()
        );
    }

}
