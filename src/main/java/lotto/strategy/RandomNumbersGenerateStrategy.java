package lotto.strategy;

import camp.nextstep.edu.missionutils.Randoms;
import java.util.List;
import lotto.Lotto;

public class RandomNumbersGenerateStrategy implements NumberStrategy {

    @Override
    public List<Integer> getNumbers() {
        return Randoms.pickUniqueNumbersInRange(
                Lotto.MIN_LOTTO_NUMBER,
                Lotto.MAX_LOTTO_NUMBER,
                Lotto.LOTTO_COUNT
        );
    }

}
