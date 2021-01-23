package Controller;

import User.User;
import User.Location;

import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Classe Controller.Controller
 * Pretende guardar o conjunto de utilizadores normais da aplicação.
 * Dispõe de um Map para esse efeito e, para além disso, de um ReentrantLock
 * caso seja necessário proceder a alterações no Map de forma concorrente.
 */
public class Controller {
    private final Map<String, User> mapUsers;
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private final Lock rlock = lock.readLock();
    private final Lock wlock = lock.writeLock();

    public Controller() {
        this.mapUsers = new TreeMap<>();
    }

    /**
     * Regista um User.User no Map
     * @param name username
     * @param pw password
     */
    public void register(String name, String pw) {
        try {
            wlock.lock();
            User user = new User(name, pw);
            this.mapUsers.put(name, user);
        } finally {
            wlock.unlock();
        }
    }

    /**
     * Autenticação de um User.User
     * @param name username
     * @param pw password
     * @return true caso as credenciais correspondam, false caso contrário
     */
    public boolean login(String name, String pw) {
        try {
            rlock.lock();
            boolean r = false;
            User user = this.mapUsers.get(name);
            if (user != null) r = user.login(pw);
            return r;
        } finally {
            rlock.unlock();
        }

    }

    /**
     * Adiciona uma localizacao à cauda da lista de localizacoes
     * @param username identificador
     * @param l localizacao
     */
    public void addLocalizacao(String username, Location l) {
        try {
            rlock.lock();
            User user = this.mapUsers.get(username);
            if (user != null) user.addLocation(l);
        } finally {
            rlock.unlock();
        }

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
            if (user.isInLocation(loc)) r++;
        }
        return r;
    }
}
