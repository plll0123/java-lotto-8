package view;

import java.util.function.Function;
import lotto.Converter;
import lotto.LottoStore;
import lotto.PurchasedLotto;
import reader.Reader;
import util.RetryTemplate;
import writer.Writer;

public class LottoPurchaseContext {

    private final static String PURCHASED_AMOUNT_MESSAGE = "구입금액을 입력해 주세요.";
    private final static String PURCHASED_COUNT_MESSAGE = "\n%d개를 구매했습니다.";
    private static final String LINE_BREAK = "\n";

    private final Reader reader;
    private final Writer writer;
    private final Converter converter;
    private final LottoStore store;
    private final RetryTemplate retryTemplate;

    public LottoPurchaseContext(
            Reader reader,
            Writer writer,
            Converter converter,
            LottoStore store,
            RetryTemplate retryTemplate
    ) {
        this.reader = reader;
        this.writer = writer;
        this.converter = converter;
        this.store = store;
        this.retryTemplate = retryTemplate;
    }

    public PurchasedLotto execute() {
        return doExecute(store::sell);
    }

    private PurchasedLotto doExecute(Function<Integer, PurchasedLotto> function) {
        PurchasedLotto purchasedLotto = retryTemplate.execute(
                () -> {
                    writer.write(PURCHASED_AMOUNT_MESSAGE);
                    String source = reader.read();
                    int amount = converter.stringToInteger(source);
                    return function.apply(amount);
                },
                ex -> writer.write(ex.getMessage() + LINE_BREAK)
        );
        writer.write(PURCHASED_COUNT_MESSAGE.formatted(purchasedLotto.count()) + LINE_BREAK + purchasedLotto);
        return purchasedLotto;
    }

}
