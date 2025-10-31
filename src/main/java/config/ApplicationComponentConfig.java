package config;

import lotto.LottoMachine;
import util.Converter;
import lotto.LottoStore;
import reader.ConsoleReader;
import lotto.strategy.RandomNumbersGenerateStrategy;
import util.RetryTemplate;
import view.component.LottoPurchaseComponent;
import view.component.LottoResultComponent;
import view.component.WinningLottoComponent;
import writer.Writer;

public class ApplicationComponentConfig {

    public static LottoPurchaseComponent lottoPurchaseContext() {
        return new LottoPurchaseComponent(
                new ConsoleReader(),
                new Writer(),
                new Converter(),
                new LottoStore(new LottoMachine(new RandomNumbersGenerateStrategy())),
                new RetryTemplate()
        );
    }

    public static WinningLottoComponent winningLottoContext() {
        return new WinningLottoComponent(
                new ConsoleReader(),
                new Writer(),
                new Converter(),
                new RetryTemplate()
        );
    }

    public static LottoResultComponent lottoResultContext() {
        return new LottoResultComponent(
                new Writer()
        );
    }

}
