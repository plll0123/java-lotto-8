package view;

import lotto.PurchasedLotto;
import lotto.WinningLotto;
import view.component.LottoPurchaseComponent;
import view.component.LottoResultComponent;
import view.component.WinningLottoComponent;

public class LottoService {

    private final LottoPurchaseComponent lottoPurchaseComponent;
    private final WinningLottoComponent winningLottoComponent;
    private final LottoResultComponent lottoResultComponent;

    public LottoService(
            LottoPurchaseComponent lottoPurchaseComponent,
            WinningLottoComponent winningLottoComponent,
            LottoResultComponent lottoResultComponent
    ) {
        this.lottoPurchaseComponent = lottoPurchaseComponent;
        this.winningLottoComponent = winningLottoComponent;
        this.lottoResultComponent = lottoResultComponent;
    }

    public void service() {
        PurchasedLotto purchasedLotto = lottoPurchaseComponent.execute();
        WinningLotto winningLotto = winningLottoComponent.execute();
        lottoResultComponent.execute(winningLotto, purchasedLotto);
    }

}
