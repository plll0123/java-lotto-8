package lotto;

import view.LottoPurchaseContext;
import view.LottoResultContext;
import view.WinningLottoContext;

public class LottoService {

    private final LottoPurchaseContext lottoPurchaseContext;
    private final WinningLottoContext winningLottoContext;
    private final LottoResultContext lottoResultContext;

    public LottoService(
            LottoPurchaseContext lottoPurchaseContext,
            WinningLottoContext winningLottoContext,
            LottoResultContext lottoResultContext
    ) {
        this.lottoPurchaseContext = lottoPurchaseContext;
        this.winningLottoContext = winningLottoContext;
        this.lottoResultContext = lottoResultContext;
    }

    public void service() {
        PurchasedLotto purchasedLotto = lottoPurchaseContext.execute();
        winningLottoContext.printWinningLottoNumbers();
        Lotto winningLotto = winningLottoContext.getWinningLotto();
        int bonusNumber = winningLottoContext.getBonusNumber();
        lottoResultContext.execute(winningLotto, bonusNumber, purchasedLotto);
    }

}
