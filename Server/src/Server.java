import java.io.*;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.Collection;

public class Server {
    public static void main(String[] args) throws Exception {
        var ss = new ServerSocket(12345);
        while(true) {
            try (var s = ss.accept()) {
                var dos = new DataOutputStream(new BufferedOutputStream(s.getOutputStream()));
                var dis = new DataInputStream(new BufferedInputStream(s.getInputStream()));
                while(true) {
                    switch(dis.readUTF()) {
                        case "register":
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
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
