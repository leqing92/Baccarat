package sg.edu.nus.iss.baccarat.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class NetworkIO {
    private InputStream is;
    private DataInputStream dis;
    private OutputStream os;
    private DataOutputStream dos;

    public NetworkIO (Socket _s) throws IOException{
        this.is = _s.getInputStream();
        dis = new DataInputStream(is);

        this.os = _s.getOutputStream();
        dos = new DataOutputStream(os);

    }

    public String read() throws IOException{
        return dis.readUTF();
    }

    public void write (String _msg) throws IOException{
        dos.writeUTF(_msg);
        dos.flush();
    }

    public void close() throws IOException{
        dis.close();
        is.close();
        dos.close();
        os.close();
    }
}
