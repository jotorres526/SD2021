import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.stream.Collectors;

public class User {
    private String username;
    private String password;
    // TODO: Encapsulamento
    private Collection<Location> location;
    private ReadWriteLock wlock;
    private ReadWriteLock rlock;

    //TODO aplicar os locks aos métodos
    public User() throws IOException {
        this.username = "";
        this.password = "";
        this.location = new ArrayList<>();
    }

    public User(String username, String password) throws IOException {
        this.username = username;
        this.password = password;
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

    public Collection<Location> getLocation() {
        return this.location.stream().map(Location::clone).collect(Collectors.toList());
    }

    public void setLocation(Collection<Location> location) {
        this.location = location.stream().map(Location::clone).collect(Collectors.toList());
    }

    /**
     * Adiciona uma localizacao à cauda da lista, passando a ser a localização mais atual
     * @param location localizacao
     */
    public void addLocation(Location location) {
            this.location.add(location);
    }

    /**
     * Retorna 1 se o User está atualmente numa localizacao e 0 caso não esteja
     * A variável loc serve para alcançar a localizacao atual que está na cauda
     * da lista de localizacoes do User
     * @param l localizacao
     * @return 1 se estiver na loc, 0 caso contrário
     */
    public int getNumberInLoc(Location l) {
        int r = 0;
        Location loc = new Location();
        for (Location location : this.location)
            loc = location;
        if (loc.equals(l)) r++;
        return r;
    }

    /**
     * Verifica se a password corresponde à do User
     * @param pw password
     * @return true caso corresponda, false caso contrário
     */
    public boolean login(String pw) {
        return this.password.equals(pw);
    }

    public static void main(String[] args) throws IOException {
        Stub stub = new Stub(); //TODO acabar main do User


    }

}

