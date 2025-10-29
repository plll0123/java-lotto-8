package reader;

import camp.nextstep.edu.missionutils.Console;
import java.util.NoSuchElementException;
import util.ErrorMessage;
import util.InputSourceError;

public class ConsoleReader implements Reader {

    @Override
    public String read() {
        try {
            String source = doRead();
            if (source == null || source.isEmpty()) {
                throw new IllegalArgumentException(ErrorMessage.EMPTY_STRING);
            }
            return source;
        } catch (NoSuchElementException ex) {
            throw new InputSourceError(ErrorMessage.INPUT_ERROR);
        }
    }

    protected String doRead() {
        return Console.readLine();
    }

}
