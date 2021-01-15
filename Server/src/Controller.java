import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

public class Controller {
    private Map<String, User> mapUsers;
    //TODO: Maybe colocar aqui o Stub, idk
    private ReentrantLock lock;

    /**
     * Regista um User no Map
     * @param name username
     * @param pw password
     */
    public void register(String name, String pw) {
        try {
            this.lock.lock();
            User user = new User(name, pw);
            this.mapUsers.put(name, user);
        } finally {
            this.lock.unlock();
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
     * @param id identificador
     * @param l localizacao
     */
    public void addLocalizacao(String id, Location l) {
        User user = this.mapUsers.get(id);
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
