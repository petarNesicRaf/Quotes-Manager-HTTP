package http;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static final int TCP_PORT = 8113;

    public static void main(String[] args) throws IOException {
        ServerSocket ss= new ServerSocket(TCP_PORT);
        while(true)
        {
            Socket socket = ss.accept();
            new Thread(new ServerThread(socket)).start();
        }
    }

}
