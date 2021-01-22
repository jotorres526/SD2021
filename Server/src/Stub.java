import java.io.*;
import java.net.Socket;

/**
 * Classe Stub
 * Tem o propósito de criar uma conexão com o Servidor.
 * Sempre que algum User fizer uma query, o Stub irá enviar os dados
 * necessários e, de seguida, irá receber a resposta do Server e
 * retorná-la ao User correspondente.
 */
public class Stub {
    private final DataOutputStream dos;
    private final DataInputStream dis;

    public Stub() throws IOException {
        Socket s = new Socket("localhost", 12345);
        this.dos = new DataOutputStream(new BufferedOutputStream(s.getOutputStream()));
        this.dis = new DataInputStream(new BufferedInputStream(s.getInputStream()));
    }

    /**
     * Registo de um User no Map
     * @param user username
     * @param pw password
     * @throws IOException
     */
    public void register(String user, String pw) throws IOException {
        this.dos.writeUTF("register");
        this.dos.writeUTF(user);
        this.dos.writeUTF(pw);
        this.dos.flush();
    }

    /**
     * Autenticação de um User
     * @param user username
     * @param pw password
     * @return true caso a autenticação seja bem sucedida, false em caso contrário
     * @throws IOException exceção
     */
    public boolean login(String user, String pw) throws IOException {
        this.dos.writeUTF("login");
        this.dos.writeUTF(user);
        this.dos.writeUTF(pw);
        this.dos.flush();
        return this.dis.readBoolean();

    }

    /**
     * Atualiza a localização de um User
     * @param user username
     * @param locX primeiro parâmetro da localização, sendo que uma localização
     *             corresponde a um par (x,y)
     * @param locY segundo parâmetro da localização, sendo que uma localização
     *             corresponde a um par (x,y)
     * @throws IOException exceção
     */
    public void changeLoc(String user, String locX, String locY) throws IOException {
        this.dos.writeUTF("change location");
        this.dos.writeUTF(user);
        this.dos.writeUTF(locX);
        this.dos.writeUTF(locY);
        this.dos.flush();
    }

    /**
     * Calcula o número de pessoas situadas numa dada localização
     * @param user username do User que enviou a query
     * @param locX primeiro parâmetro da localização, sendo que uma localização
     *             corresponde a um par (x,y)
     * @param locY segundo parâmetro da localização, sendo que uma localização
     *             corresponde a um par (x,y)
     * @return número de pessoas situadas numa dada localização
     * @throws IOException exceção
     */
    public int howManyInLocation(String user, String locX, String locY) throws IOException {
        this.dos.writeUTF("how many people in a location");
        this.dos.writeUTF(user);
        this.dos.writeUTF(locX);
        this.dos.writeUTF(locY);
        return this.dis.readInt();
    }

    //TODO queries restantes no Stub
}
