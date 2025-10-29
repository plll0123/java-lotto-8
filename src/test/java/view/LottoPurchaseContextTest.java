package view;

import static org.assertj.core.api.Assertions.assertThat;

import camp.nextstep.edu.missionutils.test.NsTest;
import config.ApplicationComponentConfig;
import java.util.List;
import lotto.Lotto;
import lotto.PurchasedLotto;
import org.junit.jupiter.api.Test;

class LottoPurchaseContextTest extends NsTest {

    private LottoPurchaseContext lottoPurchaseContext = ApplicationComponentConfig.lottoPurchaseContext();

    @Test
    void 로또_구메_기능_테스트() {
        int expectLottoCount = 3;
        int totalAmount = Lotto.AMOUNT * expectLottoCount;
        run(String.valueOf(totalAmount));
        lottoPurchaseContext.execute();

        PurchasedLotto result = lottoPurchaseContext.getPurchaseLotto();
        List<Lotto> lottos = result.values();
        assertThat(lottos).hasSize(expectLottoCount);
        lottos.forEach(it -> assertThat(it.getNumbers())
                .doesNotHaveDuplicates()
                .hasSize(Lotto.LOTTO_COUNT));
        assertThat(output()).contains(
                "구입금액을 입력해 주세요.",
                "%d개를 구매했습니다.".formatted(expectLottoCount)
        );
    }

    @Test
    void 실패시_재시도_테스트() {
        int expectLottoCount = 3;
        int totalAmount = Lotto.AMOUNT * expectLottoCount;
        run("1001", "999", ",", String.valueOf(totalAmount));
        lottoPurchaseContext.execute();

        assertThat(output()).contains(
                "[ERROR] 금액은 천원단위여야 합니다.",
                "[ERROR] 금액은 천원 이상이어야 합니다.",
                "[ERROR] 숫자를 입력해주세요.",
                "%d개를 구매했습니다.".formatted(expectLottoCount)
        );
    }

    @Override
    protected void runMain() {
        //do nothing
    }

}