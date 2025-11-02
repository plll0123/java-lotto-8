package lotto;

import config.ApplicationComponentConfig;
import util.ErrorMessage;
import util.InputSourceError;
import view.LottoService;

public class Application {

    public static void main(String[] args) {
        LottoService application = new LottoService(
                ApplicationComponentConfig.lottoPurchaseContext(),
                ApplicationComponentConfig.winningLottoContext(),
                ApplicationComponentConfig.lottoResultContext()
        );
        try {
            application.service();
        } catch (InputSourceError error) {
            System.out.println(ErrorMessage.INPUT_ERROR);
        }
    }

}
