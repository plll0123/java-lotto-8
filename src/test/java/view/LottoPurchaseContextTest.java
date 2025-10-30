package view;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.in;

import camp.nextstep.edu.missionutils.test.NsTest;
import config.ApplicationComponentConfig;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
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

        PurchasedLotto result = lottoPurchaseContext.execute();
        List<Lotto> lottos = result.values();
        assertThat(lottos).hasSize(expectLottoCount);
        lottos.forEach(it -> assertThat(it.getNumbers())
                .doesNotHaveDuplicates()
                .hasSize(Lotto.LOTTO_COUNT));

        String resultToFlatString = lottos.stream()
                .map(Lotto::toString)
                .collect(Collectors.joining("\n"));

        assertThat(output()).contains(
                "구입금액을 입력해 주세요.",
                "\n%d개를 구매했습니다.\n".formatted(lottos.size()) + resultToFlatString
        );
    }

    @Test
    void 실패시_재시도_테스트() {
        int expectLottoCount = 3;
        int totalAmount = Lotto.AMOUNT * expectLottoCount;

        List<String> invalidArguments = List.of("1001", "999", ",", String.valueOf(totalAmount));
        run(invalidArguments.toArray(String[]::new));
        lottoPurchaseContext.execute();

        assertThat(output()).contains(
                "[ERROR] 금액은 천원단위여야 합니다.",
                "[ERROR] 금액은 천원 이상이어야 합니다.",
                "[ERROR] 숫자를 입력해주세요.",
                "%d개를 구매했습니다.".formatted(expectLottoCount)
        );
        long count = Pattern.compile("구입금액을 입력해 주세요.").matcher(output())
                .results()
                .count();
        assertThat(count).isEqualTo(invalidArguments.size());
    }

    @Override
    protected void runMain() {
        //do nothing
    }

}