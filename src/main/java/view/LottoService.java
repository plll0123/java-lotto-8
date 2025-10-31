package view;

import lotto.PurchasedLotto;
import lotto.WinningLotto;

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
        WinningLotto winningLotto = winningLottoContext.execute();
        lottoResultContext.execute(winningLotto, purchasedLotto);
    }

}
