import java.io.*;
import java.net.ServerSocket;
import java.util.*;

/**
 * Classe Servidor
 * Nesta classe, é criado um Map que visa guardar classes como o Controller que têm
 * o conjunto de todos os Users e, caso seja necessário criar uma nova classe deste
 * género mas com outras aplicabilidades, apenas será necessário inserir um novo
 * Controller no map, não havendo alterações significativas no código.
 */
public class Server {
    public static void main(String[] args) throws Exception {
        Map<Integer, Skeleton> map = new HashMap<>();
        map.put(0, new ControllerSkeleton(new Controller()));

        var ss = new ServerSocket(12345);
        while(true) {
            try (var s = ss.accept()) {
                var dos = new DataOutputStream(new BufferedOutputStream(s.getOutputStream()));
                var dis = new DataInputStream(new BufferedInputStream(s.getInputStream()));
                while(true) {
                    Skeleton skeleton = map.get(dis.readInt());
                    skeleton.handle(dis,dos);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
