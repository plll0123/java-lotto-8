package lotto;

import java.util.Arrays;
import java.util.List;
import util.ErrorMessage;

public enum Prize {

    FIFTH(3, 5_000),
    FOURTH(4, 50_000),
    THIRD(5, 1_500_000),
    SECOND(5, 30_000_000, true, Constants.BONUS_ADDITIONAL_MESSAGE),
    FIRST(6, 2_000_000_000),
    ;

    private static final Prize[] _PRIZES = Prize.values();
    private static final List<Prize> PRIZES = Arrays.asList(_PRIZES);

    private final int matchCount;
    private final int value;
    private final boolean needBonus;
    private final String description;

    Prize(int matchCount, int value) {
        this(matchCount, value, false, "");
    }

    Prize(int matchCount, int value, boolean needBonus, String descriptionFormat) {
        this.matchCount = matchCount;
        this.value = value;
        this.needBonus = needBonus;
        this.description = Constants.MATCH_INFORMATION_FORMAT.formatted(matchCount, descriptionFormat, value);
    }

    public static List<Prize> valuez() {
        return PRIZES;
    }

    public static Prize from(int matchCount, boolean bonus) {
        return PRIZES.stream()
                .filter(e -> e.matchCount == matchCount)
                .filter(e -> e.needBonus == bonus)
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.PRIZE_NOT_EXIST));
    }

    public int getMatchCount() {
        return matchCount;
    }

    public boolean isNeedBonus() {
        return needBonus;
    }

    public String getDescription() {
        return description;
    }

    public int getValue() {
        return value;
    }

    private static class Constants {
        private static final String MATCH_INFORMATION_FORMAT = "%d개 일치%s (%,d원)";
        private static final String BONUS_ADDITIONAL_MESSAGE = ", 보너스 볼 일치";
    }

}
