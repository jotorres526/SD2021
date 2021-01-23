package Controller;

import User.Location;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

/**
 * A classe ControllerSkeleton serve como um middleware que decide a qual dos
 * "Controllers" recorrer, dependendo da query que está a ser executada.
 * Neste momento, como só foi implementado um Controller, a escolha desta classe será
 * simples, mas, eventualmente, será necessário criar novos Controllers que armazenam
 * diferentes objetos e aí irá ser posta em prática a utilidade desta classe.
 * Por isso, esta classe está preparada para uma eventual expansão de código.
 */
public class ControllerSkeleton implements Skeleton {
    private final Controller controller;

    public ControllerSkeleton(Controller controller) {
        this.controller = controller;
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
                    this.controller.register(name, pw, privileged);
                    break;
                case "login":
                    String username = dis.readUTF();
                    String password = dis.readUTF();
                    boolean success = this.controller.login(username, password);
                    dos.writeBoolean(success);
                    dos.flush();
                    break;
                case "change location":
                    String n = dis.readUTF();
                    String locX = dis.readUTF();
                    String locY = dis.readUTF();
                    this.controller.addLocalizacao(n, new Location(locX, locY));
                    break;
                case "how many people in location":
                    String x = dis.readUTF();
                    String y = dis.readUTF();
                    int number = this.controller.getNumberInLoc(new Location(x, y));
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
                    if (privilege) {
                        map = this.controller.loadMap(num);
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
                    }
                    break;
                case "exit":
                    cont = false;
                    break;
            }
        }
    }
}
