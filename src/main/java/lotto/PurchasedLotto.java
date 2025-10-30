package lotto;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public record PurchasedLotto(
        List<Lotto> values
) {

    public Map<Prize, Long> getWinningDetails(Lotto winningLotto, int bonusNumber) {
        return values.stream()
                .filter(e -> e.matchesCount(winningLotto) >= 3)
                .map(e -> {
                    int matchCount = e.matchesCount(winningLotto);
                    boolean contains = e.contains(bonusNumber);
                    return Prize.from(matchCount, contains);
                })
                .collect(Collectors.groupingBy(e -> e, Collectors.counting()));
    }

}
