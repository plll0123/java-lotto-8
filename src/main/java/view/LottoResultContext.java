package view;

import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import lotto.Prize;
import lotto.PurchasedLotto;
import lotto.WinningLotto;
import writer.Writer;

public class LottoResultContext {

    private static final String MATCH_MESSAGE_PREFIX = """
            
            당첨 통계
            ---
            """;
    private static final String MATCH_MESSAGE = "%s - %d개";
    private static final String ROI_MESSAGE = "총 수익률은 %.1f%%입니다.";
    private static final String LINE_BREAK = "\n";

    private final Writer writer;

    public LottoResultContext(Writer writer) {
        this.writer = writer;
    }

    public void execute(WinningLotto winningLotto, PurchasedLotto purchasedLotto) {
        Map<Prize, Long> lottoResult = purchasedLotto.getWinningDetails(winningLotto);
        double roi = getRoi(purchasedLotto, lottoResult);
        String prizeResult = getPrizeResult(lottoResult, roi);
        writer.write(MATCH_MESSAGE_PREFIX + prizeResult);
    }

    protected final double getRoi(PurchasedLotto purchasedLotto, Map<Prize, Long> lottoResult) {
        long sum = lottoResult.entrySet()
                .stream()
                .mapToLong(LottoResultContext::calculatePrizeForRank)
                .sum();
        double hundred = 100.0;
        double roi = (double) sum / purchasedLotto.totalAmount() * hundred;
        return Math.round(roi * hundred) / hundred;
    }

    private static long calculatePrizeForRank(Entry<Prize, Long> e) {
        return e.getKey().getValue() * e.getValue();
    }

    protected final String getPrizeResult(Map<Prize, Long> lottoResult, double roi) {
        return Prize.valuez()
                .stream()
                .map(e -> String.format(MATCH_MESSAGE, e.getDescription(), lottoResult.getOrDefault(e, 0L)))
                .collect(Collectors.joining(LINE_BREAK, "", LINE_BREAK + ROI_MESSAGE.formatted(roi)));
    }

}
