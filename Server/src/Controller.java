import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Classe Controller
 * Pretende guardar o conjunto de utilizadores normais da aplicação.
 * Dispõe de um Map para esse efeito e, para além disso, de um ReentrantLock
 * caso seja necessário proceder a alterações no Map de forma concorrente.
 */
public class Controller {
    private final Map<String, User> mapUsers;
    private ReentrantLock lock;

    public Controller() {
        this.mapUsers = new TreeMap<>();
        this.lock = new ReentrantLock();
    }

    public Controller(Map<String, User> map) {
        this.mapUsers = map;
    }

    /**
     * Regista um User no Map
     * @param name username
     * @param pw password
     */
    public void register(String name, String pw) {
        try {
            lock.lock();
            User user = new User(name, pw);
            this.mapUsers.put(name, user);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    /**
     * Autenticação de um User
     * @param name username
     * @param pw password
     * @return true caso as credenciais correspondam, false caso contrário
     */
    public boolean login(String name, String pw) {
        User user = this.mapUsers.get(name);
        boolean r = false;
        if (user != null) r = user.login(pw);
        return r;
    }

    /**
     * Adiciona uma localizacao à cauda da lista de localizacoes
     * @param username identificador
     * @param l localizacao
     */
    public void addLocalizacao(String username, Location l) {
        User user = this.mapUsers.get(username);
        if (user != null) user.addLocation(l);
    }

    /**
     * Retorna o número de Users numa localizacao
     * @param loc localizacao
     * @return número de users
     */
    public int getNumberInLoc(Location loc) {
        int r = 0;
        for (Map.Entry<String, User> entry : this.mapUsers.entrySet()) {
            User user = entry.getValue();
            r += user.getNumberInLoc(loc);
        }
        return r;
    }
}
