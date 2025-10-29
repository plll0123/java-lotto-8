package view;

import java.util.List;
import lotto.Lotto;
import lotto.LottoParser;
import reader.Reader;
import util.ErrorMessage;
import util.RetryTemplate;
import writer.Writer;

public class WinningLottoContext {

    private static final String WINNING_LOTTO_MESSAGE = "\n당첨 번호를 입력해주세요.";
    private static final String BONUS_LOTTO_NUMBEr_MESSAGE = "\n보너스 번호를 입력해주세요.";

    private final Reader reader;
    private final Writer writer;
    private final LottoParser lottoParser;
    private final RetryTemplate retryTemplate;

    private Lotto winningLotto;
    private int bonusNumber;

    public WinningLottoContext(Reader reader, Writer writer, LottoParser lottoParser, RetryTemplate retryTemplate) {
        this.reader = reader;
        this.writer = writer;
        this.lottoParser = lottoParser;
        this.retryTemplate = retryTemplate;
    }

    public void printWinningLottoNumbers() {
        getWinningLottoNumbers();
        getBonusLottoNumber();
    }

    public Lotto getWinningLotto() {
        return winningLotto;
    }

    public int getBonusNumber() {
        return bonusNumber;
    }

    private void getWinningLottoNumbers() {
        retryTemplate.execute(() -> {
            writer.write(WINNING_LOTTO_MESSAGE);

            String source = reader.read();
            List<Integer> winningNumbers = lottoParser.sourceToNumbers(source);
            this.winningLotto = new Lotto(winningNumbers);

            return null;
        }, ex -> writer.write(ex.getMessage()));
    }

    private void getBonusLottoNumber() {
        retryTemplate.execute(() -> {
            writer.write(BONUS_LOTTO_NUMBEr_MESSAGE);

            String bonusNumberSource = reader.read();
            int bonusNumber = lottoParser.sourceToNumber(bonusNumberSource);
            if (winningLotto.contains(bonusNumber)) {
                throw new IllegalArgumentException(ErrorMessage.BONUS_NUMBER_DUPLICATE);
            }
            this.bonusNumber = bonusNumber;

            return null;
        }, ex -> writer.write(ex.getMessage()));
    }

}
