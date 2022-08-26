import java.io.*;
import java.net.*;

public class server {
    public static void main(String[] args) throws Exception {
        ServerSocket sersock = new ServerSocket(3000);
        System.out.println("Server ready for chatting");
        
        Socket sock = sersock.accept(); // accept exactly 1 client
        BufferedReader keyRead = new BufferedReader(new InputStreamReader(System.in)); // read keyboard input
        OutputStream ostream = sock.getOutputStream(); // ostream for pwrite
        PrintWriter pwrite = new PrintWriter(ostream, true); // 
        InputStream istream = sock.getInputStream(); // istream for receiveRead
        BufferedReader receiveRead = new BufferedReader(new InputStreamReader(istream));
        
        Thread t1 = new sendMessages(keyRead, pwrite);
        t1.start();
        Thread t2 = new receiveMessages(receiveRead);
        t2.start();

    }
}

class receiveMessages extends Thread {
    final BufferedReader receiveRead;
    public receiveMessages(BufferedReader receiveRead) {
        this.receiveRead = receiveRead;
    }
    @Override
    public void run() {
        String receiveMessage;
        while(true) {
            try {
                if((receiveMessage = receiveRead.readLine()) != null) {
                    System.out.print("\u001B[34m" + "<Client> " + "\u001B[0m");
                    System.out.println(receiveMessage);
                }
            }
            catch(IOException e) {
                e.printStackTrace();
            }
        }
    }
}

class sendMessages extends Thread {
    final BufferedReader keyRead;
    final PrintWriter pwrite;
    public sendMessages(BufferedReader keyRead, PrintWriter pwrite) {
        this.keyRead = keyRead;
        this.pwrite = pwrite;
    }
    @Override
    public void run() {
        String sendMessage;
        while(true) {
            try {
                sendMessage = keyRead.readLine();
                pwrite.println(sendMessage);
                pwrite.flush();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}