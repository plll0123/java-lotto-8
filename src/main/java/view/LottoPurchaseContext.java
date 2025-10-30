package view;

import java.util.List;
import lotto.Lotto;
import lotto.LottoParser;
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

    public PurchasedLotto execute() {
        this.purchasedLotto = retryTemplate.execute(
                () -> {
                    writer.write(PURCHASED_AMOUNT_MESSAGE);
                    return this.getLotto();
                },
                ex -> writer.write(ex.getMessage() + LINE_BREAK)
        );
        writer.write(getPurchasedLottoMessage());
        return purchasedLotto;
    }

    private PurchasedLotto getLotto() {
        String source = reader.read();
        int amount = lottoParser.sourceToNumber(source);
        return store.sell(amount);
    }

    private String getPurchasedLottoMessage() {
        List<Lotto> lottos = purchasedLotto.values();

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(PURCHASED_COUNT_MESSAGE.formatted(lottos.size()))
                .append(LINE_BREAK);
        for (Lotto lotto : lottos) {
            stringBuilder.append(lotto.toString()).append(LINE_BREAK);
        }
        return stringBuilder.toString();
    }

}
