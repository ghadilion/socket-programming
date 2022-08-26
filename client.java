import java.io.*;
import java.net.*;

public class client {
    public static void main(String args[]) throws Exception {
        Socket sock = new Socket("127.0.0.1", 3000);
        BufferedReader keyRead = new BufferedReader(new InputStreamReader(System.in));
        OutputStream ostream = sock.getOutputStream();
        PrintWriter pwrite = new PrintWriter(ostream, true);
        InputStream istream = sock.getInputStream();
        BufferedReader receiveRead = new BufferedReader(new InputStreamReader(istream));
        System.out.println("Start chat, type and press enter");
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
                    System.out.print("\u001B[33m<Server> \u001B[0m");
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
                // System.out.print("\u001B[34m" + "<Client> " + "\u001B[0m");
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