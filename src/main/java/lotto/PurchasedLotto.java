package lotto;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public record PurchasedLotto(
        List<Lotto> values
) {

    public Map<Prize, Long> getWinningDetails(WinningLotto winningLotto) {
        Lotto lotto = winningLotto.value();
        return values.stream()
                .filter(e -> e.matchesCount(lotto) >= 3)
                .map(e -> {
                    int matchCount = e.matchesCount(lotto);
                    boolean bonusNumberMatches = e.contains(winningLotto.bonusNumber());
                    return Prize.from(matchCount, bonusNumberMatches);
                })
                .collect(Collectors.groupingBy(e -> e, Collectors.counting()));
    }

    public int totalAmount() {
        return values.size() * Lotto.AMOUNT;
    }

    public int count() {
        return values.size();
    }

    @Override
    public String toString() {
        return values.stream()
                .map(Lotto::toString)
                .collect(Collectors.joining("\n"));
    }

}
