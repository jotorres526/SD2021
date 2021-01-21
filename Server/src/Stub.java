import java.io.*;
import java.net.Socket;

public class Stub {
    private final Socket s;
    private final DataOutputStream dos;
    private final DataInputStream dis;

    public Stub() throws IOException {
        this.s = new Socket("localhost", 12345);
        this.dos = new DataOutputStream(new BufferedOutputStream(s.getOutputStream()));
        this.dis = new DataInputStream(new BufferedInputStream(s.getInputStream()));
    }

    public void registar() throws IOException {}

    public boolean login() { return false; }

    public /*int*/void setLoc() {}

}
