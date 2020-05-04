package transmission;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;


public class DataConnector implements DataConnection, Runnable {
    private Socket socket;
    int port;

    /**
     * Create client side - open connection to address / port
     * Creates a stream socket and connects it to the port number on the specified host.
     * @param address ip address of localhost
     * @param port TCP port
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
        //ServerSocket server = new ServerSocket(port);
        this.port = port;
        Thread serverThread = new Thread(this);
        serverThread.start();
        //System.out.println("Server started");
        //this.socket = server.accept();
        //System.out.println("Client accepted");


    }

    @Override
    public DataInputStream getDataInputStream() throws IOException {
        return new DataInputStream(this.socket.getInputStream());
    }

    @Override
    public DataOutputStream getDataOutputStream() throws IOException {
        return new DataOutputStream(this.socket.getOutputStream());
    }

    @Override
    public void run() {
        ServerSocket server = null;
        try {
            server = new ServerSocket(port);
            System.out.println("Server started");
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            socket = server.accept();
            System.out.println("Client accepted");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
