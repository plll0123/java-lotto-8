package lotto;

import config.ApplicationComponentConfig;
import util.InputSourceError;

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
            System.out.println("[ERROR] " + "입력값이 존재하지 않으므로 프로그램을 종료합니다.");
        }
    }

}
