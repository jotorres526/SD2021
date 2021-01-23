import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;

public class User {
    private String username;
    private String password;
    private boolean privileged;
    // TODO: Encapsulamento
    private ArrayList<Location> location;
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private final Lock wlock = lock.writeLock();
    private final Lock rlock = lock.readLock();

    public User() {
        this.username = "";
        this.password = "";
        this.privileged = false;
        this.location = new ArrayList<>();
    }

    public User(String username, String password, boolean privileged) {
        this.username = username;
        this.password = password;
        this.privileged = privileged;
        this.location = new ArrayList<>();
    }

    public User(User user) {
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.location = user.getLocation();
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ArrayList<Location> getLocation() {
        try {
            rlock.lock();
            return new ArrayList<>(this.location);
        }
        finally {
          rlock.unlock();
        }
    }

    public void setLocation(ArrayList<Location> location) {
        try {
            wlock.lock();
            this.location = new ArrayList<>(location);
        } finally {
            wlock.unlock();
        }
    }

    /**
     * Adiciona uma localizacao à cabeça da lista, passando a ser a localização mais atual
     * @param location localizacao
     */
    public void addLocation(Location location) {
        try {
            wlock.lock();
            this.location.add(0, location);
        } finally {
            wlock.unlock();
        }
    }

    /**
     * Retorna true se o User está atualmente numa localizacao e false caso não esteja
     * A variável loc serve para alcançar a localizacao atual que está na cauda
     * da lista de localizacoes do User
     * @param l localizacao
     * @return true se estiver na location, false caso contrário
     */
    public boolean isInLocation(Location l) {
        try {
            rlock.lock();
            boolean r = false;
            if (this.location.get(0).equals(l)) r = true;
            return r;
        }
        finally {
            rlock.unlock();
        }
    }

    /**
     * Verifica se a password corresponde à do User
     * @param pw password
     * @return true caso corresponda, false caso contrário
     */
    public boolean login(String pw) {
        try {
            rlock.lock();
            return this.password.equals(pw);
        } finally {
            rlock.unlock();
        }
    }

    /**
     * Obtém a localização mais atual
     * @return localização mais atual
     */
    public Location getLastLoc() {
        return this.location.get(0);
    }

    public static void main(String[] args) throws IOException {
        Stub stub = new Stub(); //TODO acabar main do User
        System.out.println("AAA");
        stub.logout();
    }
}

