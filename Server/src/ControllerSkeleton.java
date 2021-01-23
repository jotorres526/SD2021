import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.Collection;
import java.util.Map;

/**
 * A classe ControllerSkeleton serve como um middleware que decide a qual dos
 * "Controllers" recorrer, dependendo da query que está a ser executada.
 * Neste momento, como só foi implementado um Controller, a escolha desta classe será
 * simples, mas, eventualmente, será necessário criar novos Controllers que armazenam
 * diferentes objetos e aí irá ser posta em prática a utilidade desta classe.
 * Por isso, esta classe está preparada para uma eventual expansão de código.
 */
public class ControllerSkeleton implements Skeleton {
    private final UsersController userscontroller;
    private final RegisterUsers regUsers;

    public ControllerSkeleton(UsersController controller, RegisterUsers regUsers) {
        this.userscontroller = controller;
        this.regUsers = regUsers;
    }

    @Override
    public void handle(DataInputStream dis, DataOutputStream dos) throws Exception {
        boolean cont = true;
        while (cont) {
            switch (dis.readUTF()) {
                case "register":
                    String name = dis.readUTF();
                    String pw = dis.readUTF();
                    boolean privileged = dis.readBoolean();
                    boolean suc = this.userscontroller.register(name, pw, privileged);
                    if (suc) this.regUsers.createEntry(name);
                    dos.writeBoolean(suc);
                    dos.flush();
                    break;
                case "login":
                    String username = dis.readUTF();
                    String password = dis.readUTF();
                    boolean success = this.userscontroller.login(username, password);
                    dos.writeBoolean(success);
                    dos.flush();
                    break;
                case "change location":
                    String n = dis.readUTF();
                    String locX = dis.readUTF();
                    String locY = dis.readUTF();
                    Location loc = new Location(locX, locY);
                    this.userscontroller.addLocalizacao(n, loc);
                    Collection<String> regUsers = this.userscontroller.getNewRegUsers(loc);
                    break;
                case "how many people in location":
                    String x = dis.readUTF();
                    String y = dis.readUTF();
                    int number = this.userscontroller.getNumberInLoc(new Location(x, y));
                    dos.writeInt(number);
                    dos.flush();
                    break;
                //TODO terminar queries
                case "communicate infection": //para além de comunicar, avisar todos os users que já tiveram na loc do User
                    break;
                case "load map":
                    boolean privilege = dis.readBoolean();
                    int num = dis.readInt();
                    Map<Location, Collection<String>> map;
                        map = this.userscontroller.loadMap(num, privilege);
                        for (Map.Entry<Location, Collection<String>> entry : map.entrySet()) {
                            dos.writeBoolean(true);
                            dos.writeUTF(entry.getKey().getCoordX());
                            dos.writeUTF(entry.getKey().getCoordY());
                            dos.writeInt(entry.getValue().size());
                            for (String user : entry.getValue())
                                dos.writeUTF(user);
                        }
                        dos.writeBoolean(false);
                        dos.flush();
                    break;
                case "exit":
                    cont = false;
                    break;
            }
        }
    }
}
