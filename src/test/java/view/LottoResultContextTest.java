package view;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Map;
import lotto.Lotto;
import lotto.Prize;
import lotto.PurchasedLotto;
import org.junit.jupiter.api.Test;

class LottoResultContextTest {

    @Test
    void 수익률_계산() {
        LottoResultContext lottoResultContext = new LottoResultContext(null);
        PurchasedLotto purchasedLotto = new PurchasedLotto(List.of(
                new Lotto(1, 2, 3, 4, 5, 6),
                new Lotto(1, 2, 3, 4, 5, 6),
                new Lotto(1, 2, 3, 4, 5, 6),
                new Lotto(1, 2, 3, 4, 5, 6),
                new Lotto(11, 12, 13, 14, 15, 16)
        ));
        Map<Prize, Long> winningDetails = purchasedLotto.getWinningDetails(new Lotto(11, 12, 13, 14, 20, 21), 22);
        assertThat(lottoResultContext.getRoi(purchasedLotto, winningDetails)).isEqualTo(1000.0);

        Map<Prize, Long> winningDetails2 = purchasedLotto.getWinningDetails(new Lotto(1, 2, 3, 34, 35, 36), 7);
        assertThat(lottoResultContext.getRoi(purchasedLotto, winningDetails2)).isEqualTo(400.0);
    }

    @Test
    void 당첨_등수와_수익률_계산() {
        LottoResultContext lottoResultContext = new LottoResultContext(null);
        String prizeResult = lottoResultContext.getPrizeResult(Map.of(Prize.FIFTH, 1L), 100.0);
        assertThat(prizeResult).isEqualTo("""
                3개 일치 (5,000원) - 1개
                4개 일치 (50,000원) - 0개
                5개 일치 (1,500,000원) - 0개
                5개 일치, 보너스 볼 일치 (30,000,000원) - 0개
                6개 일치 (2,000,000,000원) - 0개
                총 수익률은 100.0%입니다.
                """.stripTrailing()
        );
    }


}