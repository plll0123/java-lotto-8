package lotto;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;

class LottoMachineTest {

    @Test
    void 로또_생성() {
        LottoMachine lottoMachine = new LottoMachine(() -> List.of(1, 2, 3, 4, 5, 6));
        assertThat(lottoMachine.getLotto().getNumbers()).containsExactly(1, 2, 3, 4, 5, 6);
    }

}