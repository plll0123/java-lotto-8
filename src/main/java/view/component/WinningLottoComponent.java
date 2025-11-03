package view.component;

import java.util.List;
import lotto.Lotto;
import lotto.WinningLotto;
import reader.Reader;
import util.Converter;
import util.ErrorMessage;
import util.RetryTemplate;
import writer.Writer;

public class WinningLottoComponent {

    private static final String WINNING_LOTTO_MESSAGE = "\n당첨 번호를 입력해주세요.";
    private static final String BONUS_LOTTO_NUMBEr_MESSAGE = "\n보너스 번호를 입력해주세요.";

    private final Reader reader;
    private final Writer writer;
    private final Converter converter;
    private final RetryTemplate retryTemplate;

    public WinningLottoComponent(Reader reader, Writer writer, Converter converter, RetryTemplate retryTemplate) {
        this.reader = reader;
        this.writer = writer;
        this.converter = converter;
        this.retryTemplate = retryTemplate;
    }

    public WinningLotto execute() {
        Lotto winningLotto = getWinningLottoNumbers();
        int bonusNumber = getBonusNumber(winningLotto);
        return new WinningLotto(winningLotto, bonusNumber);
    }

    private Lotto getWinningLottoNumbers() {
        return retryTemplate.execute(() -> {
            writer.write(WINNING_LOTTO_MESSAGE);
            String source = reader.read();
            List<Integer> winningNumbers = converter.stringToIntegers(source);
            return new Lotto(winningNumbers);
        }, ex -> writer.write(ex.getMessage()));
    }

    private int getBonusNumber(Lotto winningLotto) {
        return retryTemplate.execute(() -> {
            writer.write(BONUS_LOTTO_NUMBEr_MESSAGE);

            String bonusNumberSource = reader.read();
            int bonusNumber = converter.stringToInteger(bonusNumberSource);

            validateBonusNumber(winningLotto, bonusNumber);

            return bonusNumber;
        }, ex -> writer.write(ex.getMessage()));
    }

    private static void validateBonusNumber(Lotto winningLotto, int bonusNumber) {
        Lotto.validateRange(bonusNumber);
        if (winningLotto.contains(bonusNumber)) {
            throw new IllegalArgumentException(ErrorMessage.BONUS_NUMBER_DUPLICATE);
        }
    }

}
