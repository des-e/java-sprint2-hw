package exceptions;

import java.io.IOException;

public class ManagerSaveException extends RuntimeException {
    public ManagerSaveException(IOException e) {
        System.out.println("Произошла ошибка во время чтения файла.");
        e.printStackTrace();
    }
}
