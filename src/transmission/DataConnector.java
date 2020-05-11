package transmission;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;


public class DataConnector extends Thread implements DataConnection {
    private Socket socket;
    private ServerSocket server;

    /**
     * Create client side - open connection to address / port
     * Creates a stream socket and connects it to the port number on the specified host.
     *
     * @param address ip address of localhost
     * @param port    TCP port
     */
    public DataConnector(String address, int port) throws IOException {
        this.socket = new Socket(address, port);
    }

    /**
     * Create server side - open port on this port and wait for one client
     *
     * @param port TCP port
     */
    public DataConnector(int port) throws IOException {

        this.server = new ServerSocket(port);
        this.start();
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            System.out.println("Thread was interrupted");
        }


    }

    @Override
    public DataInputStream getDataInputStream() throws IOException {
        return new DataInputStream(this.socket.getInputStream());
    }

    @Override
    public DataOutputStream getDataOutputStream() throws IOException {
        return new DataOutputStream(this.socket.getOutputStream());
    }

    public void run() {
        try {
            this.socket = server.accept();
        } catch (IOException e) {
            System.out.println("Something's wrong");
        }

    }

}
