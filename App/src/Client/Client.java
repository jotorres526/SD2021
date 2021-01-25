package Client;

import Server.Stub;
import java.io.IOException;

public class Client {

    /**
     * Pausa a thread 5 segundos para tentar reconectar
     */
    public static void timeout() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        Stub stub = new Stub();
        new Prompt(stub).display();
        stub.killStub();
    }
}
