package view;

import java.util.Map;
import java.util.stream.Collectors;
import lotto.Lotto;
import lotto.Prize;
import lotto.PurchasedLotto;
import writer.Writer;

public class LottoResultContext {

    private static final String MATCH_MESSAGE_PREFIX = """
            
            당첨 통계
            ---
            """;
    private static final String MATCH_MESSAGE = "%s - %d개";
    private static final String ROI_MESSAGE = "총 수익률은 %.1f%%입니다.";
    private static final String LINE_BREAK = "\n";
    private static final double HUNDRED = 100.0;

    private final Writer writer;

    public LottoResultContext(Writer writer) {
        this.writer = writer;
    }

    public void execute(Lotto winningLotto, int bonusNumber, PurchasedLotto purchasedLotto) {
        Map<Prize, Long> lottoResult = purchasedLotto.getWinningDetails(winningLotto, bonusNumber);
        double roi = getRoi(purchasedLotto, lottoResult);
        String prizeResult = getPrizeResult(lottoResult, roi);
        writer.write(MATCH_MESSAGE_PREFIX + prizeResult);
    }

    protected final double getRoi(PurchasedLotto purchasedLotto, Map<Prize, Long> lottoResult) {
        long sum = lottoResult.entrySet()
                .stream()
                .mapToLong(e -> e.getKey().getValue() * e.getValue())
                .sum();
        double roi = (double) sum / (purchasedLotto.values().size() * Lotto.AMOUNT) * HUNDRED;
        return Math.round(roi * HUNDRED) / HUNDRED;
    }

    protected final String getPrizeResult(Map<Prize, Long> lottoResult, double roi) {
        return Prize.valuez()
                .stream()
                .map(e -> String.format(MATCH_MESSAGE, e.getDescription(), lottoResult.getOrDefault(e, 0L)))
                .collect(Collectors.joining(LINE_BREAK, "", LINE_BREAK + ROI_MESSAGE.formatted(roi)));
    }

}
