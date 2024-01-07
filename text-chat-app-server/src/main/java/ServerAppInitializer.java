import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerAppInitializer {
    private static String messages = "";

    public static void main(String[] args) throws IOException {

        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(5050);
            while (true) {
                System.out.println("Waiting for an incoming connection");
                Socket localSocket = serverSocket.accept();

                new Thread(() -> {
                    try {
                        OutputStream os = localSocket.getOutputStream();
                        BufferedOutputStream bos = new BufferedOutputStream(os);
                        bos.write(messages.getBytes());
                        bos.flush();

                        InputStream is = localSocket.getInputStream();
                        BufferedInputStream bis = new BufferedInputStream(is);

                        while (true) {
                            byte[] buffer = new byte[1024];
                            int read = bis.read(buffer);
                            if (read == -1) throw new EOFException();
                            messages += new String(buffer, 0, read);
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }).start();
            }
        }finally {
            if (serverSocket != null && !serverSocket.isClosed()) serverSocket.close();
        }
    }
}
