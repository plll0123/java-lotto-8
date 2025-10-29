package util;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class RetryTemplate {

    public <T> T execute(Supplier<T> supplier, Consumer<Exception> onErrorAction) {
        while (true) {
            try {
                return supplier.get();
            } catch (InputSourceError ex) {
                System.out.println(ex.getMessage());
                throw ex;
            } catch (Exception cause) {
                onErrorAction.accept(cause);
            }
        }
    }


}
