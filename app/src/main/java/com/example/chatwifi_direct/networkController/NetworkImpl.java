package com.example.chatwifi_direct.networkController;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class NetworkImpl implements Network{
    private int port = 88888;
    private Socket socket;
    private InputStream is;
    private OutputStream os;

    @Override
    public void open() throws IOException {
        ServerSocket server = new ServerSocket(port);
        socket = server.accept();
        is = socket.getInputStream();
        os = socket.getOutputStream();
    }

    @Override
    public void connect(InetAddress ip) throws IOException {
        socket = new Socket(ip, port);
        os = socket.getOutputStream();
        is = socket.getInputStream();
    }

    @Override
    public void close() {
        if(this.socket != null){
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public InputStream getInputStream() {
        if(this.is == null){
            throw new NullPointerException("No InputStream");
        }
        return is;
    }

    @Override
    public OutputStream getOutputStream() {
        if(this.os == null){
            throw new NullPointerException("No OutputStream");
        }
        return os;
    }
}
