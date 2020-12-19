import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

public class User {
    private String username;
    private String password;
    // TODO: Encapsulamento
    private Collection<Location> location;

    public User() {
        this.username = "";
        this.password = "";
        this.location = new ArrayList<>();
    }

    public User(String username, String password) {
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
}
