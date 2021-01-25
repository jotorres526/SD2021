package Client;


import Server.Stub;
public class Client {

    /**
     * Pausa a thread 5 segundos para tentar reconectar
     */


    public static void main(String[] args) {
        Stub stub = new Stub();
        new Prompt(stub).display();
    }
}
