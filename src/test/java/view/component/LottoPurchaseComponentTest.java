package view.component;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.list;

import camp.nextstep.edu.missionutils.test.NsTest;
import config.ApplicationComponentConfig;
import java.util.List;
import lotto.Lotto;
import lotto.PurchasedLotto;
import org.junit.jupiter.api.Test;

class LottoPurchaseComponentTest extends NsTest {

    private final LottoPurchaseComponent lottoPurchaseComponent = ApplicationComponentConfig.lottoPurchaseContext();

    @Test
    void 로또_구메_기능_테스트() {
        int expectLottoCount = 3;
        int totalAmount = Lotto.AMOUNT * expectLottoCount;
        run(String.valueOf(totalAmount));

        PurchasedLotto result = lottoPurchaseComponent.execute();

        assertThat(result)
                .as("구매된 로또의 수는 %s장이어야 한다", expectLottoCount)
                .matches(e -> e.count() == expectLottoCount)
                .extracting(PurchasedLotto::values)
                .asInstanceOf(list(Lotto.class))
                .as("로또는 중복 번호 없는 6개의 숫자로 이뤄져야한다.")
                .allSatisfy(lotto -> assertThat(lotto.getNumbers())
                        .hasSize(Lotto.LOTTO_COUNT)
                        .doesNotHaveDuplicates());
        assertThat(output()).contains(
                "구입금액을 입력해 주세요.",
                "\n%d개를 구매했습니다.\n".formatted(result.count()) + result
        );
    }

    @Test
    void 실패시_재시도_테스트() {
        int expectLottoCount = 3;
        int totalAmount = Lotto.AMOUNT * expectLottoCount;

        List<String> invalidArguments = List.of("1001", "999", ",", String.valueOf(totalAmount));
        run(invalidArguments.toArray(String[]::new));

        lottoPurchaseComponent.execute();

        assertThat(output()).contains(
                "[ERROR] 금액은 천원단위여야 합니다.",
                "[ERROR] 금액은 천원 이상이어야 합니다.",
                "[ERROR] 숫자를 입력해주세요.",
                "%d개를 구매했습니다.".formatted(expectLottoCount)
        );
        assertThat(output().split("구입금액을 입력해 주세요\\.", -1).length - 1)
                .as("구매 금액 출력 메세지는 잘못된 구매 금액의 입력 수만큼 존재해야 합니다.")
                .isEqualTo(invalidArguments.size());
    }

    @Override
    protected void runMain() {
        //do nothing
    }

}