import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Server {
    static final int PORT = 1234;
    static List<Socket> list = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        ServerSocket ss = new ServerSocket(PORT);
        try{
            while (true)
            {
                Socket socket = ss.accept();
                System.out.println(socket.toString() + " joined!");
                try{
                    new ServerThread(socket);
                    list.add(socket);
                }
                catch (IOException e)
                {
                   socket.close();
                }
            }
        }
        finally {
            ss.close();
        }
    }
}
