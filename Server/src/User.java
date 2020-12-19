public class User {
    private String username;
    private String password;
    private String coordX;
    private String coordY;

    public User() {
        this.username = "";
        this.password = "";
        this.coordX   = "";
        this.coordY   = "";
    }

    public User(String username, String password, String coordX, String coordY) {
        this.username = username;
        this.password = password;
        this.coordX   = coordX;
        this.coordY   = coordY;
    }

    public User(User user) {
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.coordX   = user.getCoordX();
        this.coordY   = user.getCoordY();
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

    public String getCoordX() {
        return this.coordX;
    }

    public void setCoordX(String coordX) {
        this.coordX = coordX;
    }

    public String getCoordY() {
        return this.coordY;
    }

    public void setCoordY(String coordY) {
        this.coordY = coordY;
    }
}
