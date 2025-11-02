package view.component;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import camp.nextstep.edu.missionutils.test.NsTest;
import config.ApplicationComponentConfig;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import lotto.Lotto;
import lotto.WinningLotto;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.Test;

class WinningLottoComponentTest extends NsTest {

    private final WinningLottoComponent winningLottoComponent = ApplicationComponentConfig.winningLottoContext();

    @Test
    void 당첨_로또_뷰_문자열_테스트() {
        String lottoNumbers = "1,2,3,4,5,6";
        String bonusNumber = "7";
        run(lottoNumbers, bonusNumber);

        WinningLotto winningLotto = winningLottoComponent.execute();

        assertThat(output()).contains(
                "당첨 번호를 입력해주세요.",
                "보너스 번호를 입력해주세요."
        );

        List<Integer> numbers = Arrays.stream(lottoNumbers.split(","))
                .map(Integer::parseInt)
                .toList();
        assertThat(winningLotto.value()).isEqualTo(new Lotto(numbers));
        assertThat(winningLotto.bonusNumber()).isEqualTo(Integer.parseInt(bonusNumber));
    }

    @Test
    void 보너스_번호가_당첨_번호와_중복되면_예외를_던진다() {
        String lottoNumbers = "1,2,3,4,5,6";
        String invalidBonusNumber = "6";
        String bonusNumber = "7";

        run(lottoNumbers, invalidBonusNumber, bonusNumber);

        WinningLotto winningLotto = winningLottoComponent.execute();

        assertThat(output())
                .contains("[ERROR] 보너스 번호는 당첨 번호와 중복될 수 없습니다.")
                .satisfies(it -> {
                    Pattern pattern = Pattern.compile(Pattern.quote("보너스 번호를 입력해주세요."));
                    long count = pattern.matcher(it).results().count();
                    assertThat(count)
                            .as("'" + pattern.pattern() + "' 문자열이 나온 횟수 검증")
                            .isEqualTo(2L);
                });

        assertThat(winningLotto.bonusNumber())
                .isNotEqualTo(Integer.parseInt(invalidBonusNumber))
                .isEqualTo(Integer.parseInt(bonusNumber));
    }

    @Test
    void 보너스_번호의_숫자_범위_검사() {
        String lottoNumbers = "1,2,3,4,5,6";
        String invalidBonusNumber = "0";
        String ignored = "44";
        run(lottoNumbers, invalidBonusNumber, ignored);

        winningLottoComponent.execute();
        assertThat(output()).contains("[ERROR] 로또 번호는 1부터 45까지여야 합니다.");
    }

    @Override
    protected void runMain() {
        //do nothing
    }

}