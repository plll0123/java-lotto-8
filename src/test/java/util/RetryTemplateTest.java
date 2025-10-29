package util;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.Supplier;
import org.junit.jupiter.api.Test;

class RetryTemplateTest {

    @Test
    void retryTemplate은_로직이_성공하면_종료되고_예외가_발생하면_성공시까지_시도한다() {
        var numberHolder = new AtomicInteger();
        var failureCounter = new AtomicInteger();

        int tryCount = 3;
        Supplier<Integer> integerSupplier = () -> {
            numberHolder.incrementAndGet();
            if (numberHolder.get() == tryCount) {
                return numberHolder.get();
            }
            throw new IllegalArgumentException();
        };
        Consumer<Exception> exceptionHandler = e -> failureCounter.incrementAndGet();

        RetryTemplate retryTemplate = new RetryTemplate();
        retryTemplate.execute(integerSupplier, exceptionHandler);

        assertThat(numberHolder.get()).isEqualTo(tryCount);
        assertThat(failureCounter.get()).isEqualTo(tryCount - 1);
    }

}