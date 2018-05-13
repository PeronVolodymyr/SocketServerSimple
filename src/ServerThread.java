import jdk.net.Sockets;

import java.io.*;
import java.net.Socket;


public class ServerThread extends Thread {
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;


    public ServerThread(Socket socket) throws IOException {
        this.socket = socket;
        this.in = new DataInputStream(socket.getInputStream());
//        this.out = new DataOutputStream(socket.getOutputStream());
        start();
    }

    @Override
    public void run(){
        try{
            while (true)
            {
                String line  = in.readUTF();
                if(line.equals("quit")) {
                    this.out = new DataOutputStream(socket.getOutputStream());
                    out.writeUTF(line);
                    break;
                }
                System.out.println(socket.toString() +  "sending '" + line + "' to clients!" );
                for(Socket socket : Server.list) {
                    if(socket.equals(this.socket))
                        continue;
                    this.out = new DataOutputStream(socket.getOutputStream());
                    out.writeUTF(line);
                    out.flush();
                }
            }
            System.out.println(socket.toString() + "closed!");
            Server.list.remove(socket);
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

//    public static void main(String[] args) {
//
//        int port = 1234;
//        try {
//            ServerSocket ss = new ServerSocket(port);
//            System.out.println("Waiting for a request...");
//            // заставляем сервер ждать подключений и выводим сообщение когда кто-то связался с сервером
//            Socket socket = ss.accept();
//            System.out.println(" I have got a request! ");
//            System.out.println();
//
//            // Берем входной и выходной потоки сокета, теперь можем получать и отсылать данные клиенту.
//            InputStream sIn = socket.getInputStream();
//            OutputStream sOut = socket.getOutputStream();
//
//            // Конвертируем потоки в другой тип, чтоб легче обрабатывать текстовые сообщения.
//            DataInputStream in = new DataInputStream(sIn);
//            DataOutputStream out = new DataOutputStream(sOut);
//
//            String line = null;
//            while(true) {
//                line = in.readUTF(); // ожидаем пока клиент пришлет строку текста.
//                System.out.println("The client sent me this line : " + line);
//                System.out.println(" Take it back!");
//                out.writeUTF(line); // отсылаем клиенту обратно ту самую строку текста.
//                out.flush(); // заставляем поток закончить передачу данных.
//                System.out.println("Waiting for the next line...");
//                System.out.println();
//            }
//        } catch(Exception x) { x.printStackTrace(); }
//
//    }
}
