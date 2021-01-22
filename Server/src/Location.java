import java.util.Objects;

/**
 * Classe Localização
 * Contém as coordenadas de um User, que são atribuídas através
 * de uma grelha NxN.
 */
public class Location {
    private String coordX;
    private String coordY;

    public Location() {
        this.coordX = "";
        this.coordY = "";
    }

    public Location(String coordX, String coordY) {
        this.coordX = coordX;
        this.coordY = coordY;
    }

    public Location(Location loc) {
        this.coordX = loc.getCoordX();
        this.coordY = loc.getCoordY();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return Objects.equals(coordX, location.coordX) && Objects.equals(coordY, location.coordY);
    }

    @Override
    public int hashCode() {
        return Objects.hash(coordX, coordY);
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

    public Location clone() {
        return new Location(this);
    }
}
