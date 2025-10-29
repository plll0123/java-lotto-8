package reader;

import camp.nextstep.edu.missionutils.Console;
import util.ErrorMessage;

public class ConsoleReader implements Reader {

    @Override
    public String read() {
        String source = doRead();
        if (source == null || source.isEmpty()) {
            throw new IllegalArgumentException(ErrorMessage.EMPTY_STRING);
        }
        return source;
    }

    protected String doRead() {
        return Console.readLine();
    }

}
