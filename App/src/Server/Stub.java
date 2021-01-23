package Server;

import java.io.*;
import java.net.Socket;

/**
 * Classe Server.Stub
 * Tem o propósito de criar uma conexão com o Servidor.
 * Sempre que algum User.User fizer uma query, o Server.Stub irá enviar os dados
 * necessários e, de seguida, irá receber a resposta do Server.Server e
 * retorná-la ao User.User correspondente.
 */
public class Stub {
    private final DataOutputStream dos;
    private final DataInputStream dis;
    private final Socket s;

    public Stub() throws IOException {
        this.s = new Socket("localhost", 12345);
        this.dos = new DataOutputStream(new BufferedOutputStream(s.getOutputStream()));
        this.dis = new DataInputStream(new BufferedInputStream(s.getInputStream()));
    }

    /**
     * Registo de um User.User no Map
     * @param user username
     * @param pw password
     * @throws IOException
     */
    public void register(String user, String pw) {
        try {
            this.dos.writeUTF("register");
            this.dos.writeUTF(user);
            this.dos.writeUTF(pw);
            this.dos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Autenticação de um User.User
     * @param user username
     * @param pw password
     * @return true caso a autenticação seja bem sucedida, false em caso contrário
     * @throws IOException exceção
     */
    public boolean login(String user, String pw) {
        boolean r = false;
        try {
            this.dos.writeUTF("login");
            this.dos.writeUTF(user);
            this.dos.writeUTF(pw);
            this.dos.flush();
            r = this.dis.readBoolean();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return r;
    }

    /**
     * Dá logout no User

    public void logout() {
        try {
            this.dos.writeUTF("exit");
            this.dos.flush();
            s.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

    /**
     * Usado caso o stub não tenha conseguido estabelecer conexão.
     * Destroi o socket criado.
     */
    public void killStub() {
        try {
            this.dos.writeUTF("exit");
            this.dos.flush();
            s.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Atualiza a localização de um User.User
     * @param user username
     * @param locX primeiro parâmetro da localização, sendo que uma localização
     *             corresponde a um par (x,y)
     * @param locY segundo parâmetro da localização, sendo que uma localização
     *             corresponde a um par (x,y)
     * @throws IOException exceção
     */
    public void changeLoc(String user, String locX, String locY) {
        try {
            this.dos.writeUTF("change location");
            this.dos.writeUTF(user);
            this.dos.writeUTF(locX);
            this.dos.writeUTF(locY);
            this.dos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Calcula o número de pessoas situadas numa dada localização
     * @param locX primeiro parâmetro da localização, sendo que uma localização
     *             corresponde a um par (x,y)
     * @param locY segundo parâmetro da localização, sendo que uma localização
     *             corresponde a um par (x,y)
     * @return número de pessoas situadas numa dada localização
     * @throws IOException exceção
     */
    public int howManyInLocation(String locX, String locY) {
        int r = 0;
        try {
            this.dos.writeUTF("how many people in location");
            this.dos.writeUTF(locX);
            this.dos.writeUTF(locY);
            r = this.dis.readInt();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return r;
    }



    //TODO queries restantes no Server.Stub
}
