package view;

import lotto.LottoParser;
import lotto.LottoStore;
import lotto.PurchasedLotto;
import reader.Reader;
import util.RetryTemplate;
import writer.Writer;

public class LottoPurchaseContext {

    private final static String PURCHASED_AMOUNT_MESSAGE = "구입금액을 입력해 주세요.";
    private final static String PURCHASED_COUNT_MESSAGE = "%d개를 구매했습니다.";

    private final Reader reader;
    private final Writer writer;
    private final LottoParser lottoParser;
    private final LottoStore store;
    private final RetryTemplate retryTemplate;
    private PurchasedLotto purchasedLotto;

    public LottoPurchaseContext(
            Reader reader,
            Writer writer,
            LottoParser lottoParser,
            LottoStore store,
            RetryTemplate retryTemplate
    ) {
        this.reader = reader;
        this.writer = writer;
        this.lottoParser = lottoParser;
        this.store = store;
        this.retryTemplate = retryTemplate;
    }

    public void execute() {
        writer.write(PURCHASED_AMOUNT_MESSAGE);
        PurchasedLotto sell = retryTemplate.execute(
                this::getLotto,
                ex -> writer.write(ex.getMessage())
        );
        this.purchasedLotto = sell;
        writer.write(PURCHASED_COUNT_MESSAGE.formatted(sell.values().size()));
    }

    public PurchasedLotto getPurchaseLotto() {
        return purchasedLotto;
    }

    private PurchasedLotto getLotto() {
        String source = reader.read();
        int amount = lottoParser.sourceToNumber(source);
        return store.sell(amount);
    }

}
