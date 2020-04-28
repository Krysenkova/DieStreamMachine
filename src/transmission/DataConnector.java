package transmission;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class DataConnector implements DataConnection {
    private Socket socket = null;

    /**
     * Create client side - open connection to address / port
     * Creates a stream socket and connects it to the port number on the specified host.
     * @param address ip address of localhost
     * @param port TCP port
     */
    public DataConnector(String address, int port) {

        try {
            /*address = "localhost";
            port = 9876;*/
            socket = new Socket(address, port);
            System.out.println("Connected");

        } catch (UnknownHostException u) {
            System.out.println("Unknown host");
        } catch (IOException ex) {
            System.out.println("IOException");
        }
    }

    /**
     * Create server side - open port on this port and wait for one client
     *
     * @param port TCP port
     */
    public DataConnector(int port) throws IOException {

        ServerSocket server = null;
        try {
            server = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Server started");

        try {
            socket = server.accept();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Client accepted");


    }

    @Override
    public DataInputStream getDataInputStream() throws IOException {
        InputStream is = null;
        try{
            is = socket.getInputStream();
        } catch (IOException e){
            e.printStackTrace();
        }
        DataInputStream dis = new DataInputStream(is);
        return dis;
    }

    @Override
    public DataOutputStream getDataOutputStream() throws IOException {
        OutputStream os = null;
        try {
            os = socket.getOutputStream();
        } catch (IOException e){
            e.printStackTrace();
        }
        DataOutputStream dos = new DataOutputStream(os);
        return dos;
    }
}
