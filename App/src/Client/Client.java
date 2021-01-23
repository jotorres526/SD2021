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

    public static void main(String[] args) {
        Stub stub = null;
        boolean isConnected = false;
        while (!isConnected) {
            try {
                stub = new Stub();
                isConnected = true;
            } catch (IOException e) {
                System.out.println("Conexão não estabelecida. Tentando novamente em 5 segundos...");
                timeout();
            }
        }
        new Prompt(stub).display();
        stub.killStub();
    }
}
