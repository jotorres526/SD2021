import java.io.DataInputStream;
import java.io.DataOutputStream;

/**
 * A classe ControllerSkeleton serve como um middleware que decide a qual dos
 * "Controllers" recorrer, dependendo da query que está a ser executada.
 * Neste momento, como só foi implementado um Controller, a escolha desta classe será
 * simples, mas, eventualmente, será necessário criar novos Controllers que armazenam
 * diferentes objetos e aí irá ser posta em prática a utilidade desta classe.
 * Por isso, esta classe está preparada para a eventual expansão de código.
 */
public class ControllerSkeleton implements Skeleton {
    private final Controller controller;

    public ControllerSkeleton(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void handle(DataInputStream dis, DataOutputStream dos) throws Exception {
        switch(dis.readUTF()) {
            case "register":
                String name = dis.readUTF();
                String pw = dis.readUTF();
                dos.writeUTF(name);
                dos.writeUTF(pw);
                dos.flush();
                break;
            case "login":
                break;
            case "change location":
                break;
            case "how many people in a location":
                break;
            case "comunicate infection":
                break;
            case "load map":
                break;
        }
    }
}
