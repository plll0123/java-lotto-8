package util;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class RetryTemplate {

    public <T> T execute(Supplier<T> supplier, Consumer<Exception> onErrorAction) {
        while (true) {
            try {
                return supplier.get();
            } catch (Exception cause) {
                onErrorAction.accept(cause);
            }
        }
    }

}
