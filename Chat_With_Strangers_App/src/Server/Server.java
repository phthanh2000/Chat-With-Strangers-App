package Server;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class Server {

    private Object lock;
    private ServerSocket s;
    private Socket socket;
    public static ArrayList<Handler> clients = new ArrayList<>();
    public static String dataFile = "./src/main/username.txt";

    public Server() throws IOException {
        try {
            lock = new Object();
            this.load();
            s = new ServerSocket(1234);
            while (true) {
                socket = s.accept();
                DataInputStream dis = new DataInputStream(socket.getInputStream());
                DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                String request = dis.readUTF();
                if (request.equals("Bắt đầu")) {
                    String username = dis.readUTF();
                    if (isExisted(username) == false) {
                        Handler newHandler = new Handler(socket, username, true, lock);
                        clients.add(newHandler);
                        this.luutendangnhap();
                        dos.writeUTF("Ok");
                        dos.flush();
                        Thread t = new Thread(newHandler);
                        t.start();
                        capnhat();
                    } else {
                        dos.writeUTF("Tên này đã được sử dụng. Vui lòng chọn tên khác !");
                        dos.flush();
                    }
                }
            }

        } catch (Exception ex) {
            System.err.println(ex);
        } finally {
            if (s != null) {
                s.close();
            }
        }
    }

    public static boolean isExisted(String name) {
        for (Handler client : clients) {
            if (client.getUsername().equals(name)) {
                return true;
            }
        }
        return false;
    }

    private void load() {

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(dataFile), "utf8"));

            String info = br.readLine();
            while (info != null && !(info.isEmpty())) {
                clients.add(new Handler(info.split("\n")[0], false, lock));
                info = br.readLine();
            }

            br.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void luutendangnhap() {
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(new File(dataFile), "utf8");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        for (Handler client : clients) {
            pw.print(client.getUsername() + "\n");
        }
        pw.println("");
        if (pw != null) {
            pw.close();
        }
    }

    public static void capnhat() {
        String message = " ";
        for (Handler client : clients) {
            if (client.getIsLoggedIn() == true) {
                message += ",";
                message += client.getUsername();
            }
        }
        for (Handler client : clients) {
            if (client.getIsLoggedIn() == true) {
                try {
                    client.getDos().writeUTF("Online users");
                    client.getDos().writeUTF(message);
                    client.getDos().flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        Thread t = new Thread() {
            public void run() {
                try {
                    new Server();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        };
        t.start();
    }
}

class Handler implements Runnable {

    private Object lock;
    private Socket socket;
    private DataInputStream dis;
    private DataOutputStream dos;
    private String username;
    private boolean isLoggedIn;

    public boolean getIsLoggedIn() {
        return this.isLoggedIn;
    }

    public String getUsername() {
        return this.username;
    }

    public DataOutputStream getDos() {
        return this.dos;
    }

    public Handler(Socket socket, String username, boolean isLoggedIn, Object lock) throws IOException {
        this.socket = socket;
        this.username = username;
        this.dis = new DataInputStream(socket.getInputStream());
        this.dos = new DataOutputStream(socket.getOutputStream());
        this.isLoggedIn = isLoggedIn;
        this.lock = lock;
    }

    public Handler(String username, boolean isLoggedIn, Object lock) {
        this.username = username;
        this.isLoggedIn = isLoggedIn;
        this.lock = lock;
    }
    @Override
    public void run() {
        while (true) {
            try {
                String message = null;
                message = dis.readUTF();
                if (message.equals("Thoát")) {
                    dos.writeUTF("Safe to leave");
                    dos.flush();
                    socket.close();
                    this.isLoggedIn = false;
                    Server.capnhat();
                    break;
                } else if (message.equals("Text")) {
                    String receiver = dis.readUTF();
                    String content = dis.readUTF();
                    for (Handler client : Server.clients) {
                        if (client.getUsername().equals(receiver)) {
                            synchronized (lock) {
                                client.getDos().writeUTF("Text");
                                client.getDos().writeUTF(this.username);
                                client.getDos().writeUTF(content);
                                client.getDos().flush();
                                break;
                            }
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
