import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

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
    private final Socket s;

    public Stub() throws IOException {
        this.s = new Socket("localhost", 12345);
        this.dos = new DataOutputStream(new BufferedOutputStream(s.getOutputStream()));
        this.dis = new DataInputStream(new BufferedInputStream(s.getInputStream()));
    }

    /**
     * Registo de um User no Map
     * @param user username
     * @param pw password
     * @throws IOException exceção
     */
    public boolean register(String user, String pw) throws IOException {
        this.dos.writeUTF("register");
        this.dos.writeUTF(user);
        this.dos.writeUTF(pw);
        this.dos.flush();
        return this.dis.readBoolean();
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
     * Dá logout no User
     */
    public void logout() {
        try {
            this.dos.writeUTF("exit");
            this.dos.flush();
            s.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
     * @param locX primeiro parâmetro da localização, sendo que uma localização
     *             corresponde a um par (x,y)
     * @param locY segundo parâmetro da localização, sendo que uma localização
     *             corresponde a um par (x,y)
     * @return número de pessoas situadas numa dada localização
     * @throws IOException exceção
     */
    public int howManyInLocation(String locX, String locY) throws IOException {
        this.dos.writeUTF("how many people in location");
        this.dos.writeUTF(locX);
        this.dos.writeUTF(locY);
        return this.dis.readInt();
    }

    /**
     * Carrega o mapa de todas as localizações da grelha
     * Primeiro, começa por enviar ao seu servidor um boolean para que seja validado
     * com o objetivo de ter acesso ao mapa. Também é enviado o número de linhas e
     * colunas da grelha. De seguida, inicia-se um ciclo que começa por receber um
     * booleano que lhe indica se irá receber uma última "entrada" do mapa pretendido
     * com o par (Localização, Lista de usernames). Caso esse booleano seja true,
     * significa que será a última iteração do ciclo. Depois, recebe duas Strings que
     * correspondem aos parâmetros da localização, com o par (x,y). Recebe também,
     * associado a essa localização, o tamanho da lista de usernames irá receber,
     * relembrando que essa lista corresponde aos usernames que estão nessa localização.
     * Ao receber o tamanho, começa-se um novo ciclo que adiciona todos os usernames
     * à lista associada. Caso não haja nenhum elemento a adicionar, a lista irá
     * permanecer vazia, significando que não há ninguém nessa localização.
     * @param privilege true caso o User seja privilegiado, false caso contrário
     * @param n número de linhas e colunas da grelha
     * @return mapa com todas as localizações e listas de usernames de cada cliente
     * associadas
     * @throws IOException exceção
     */
    public Map<Location, Collection<String>> loadMap(boolean privilege, int n) throws IOException {
        this.dos.writeBoolean(privilege);
        this.dos.writeInt(n);
        boolean keepGoing = true;
        Map<Location, Collection<String>> map = new TreeMap<>();
        while (keepGoing) {
            keepGoing = dis.readBoolean();
            String locX = dis.readUTF();
            String locY = dis.readUTF();
            int size = dis.readInt();
            Location loc = new Location(locX, locY);
            map.put(loc, new ArrayList<>());
            if (size > 0) {
                for (int i = 0; i < size; i++) {
                    String user = dis.readUTF();
                    Collection<String> list = map.get(loc);
                    list.add(user);
                }
            }
        }
        return map;
    }

    //TODO queries restantes no Stub
}
