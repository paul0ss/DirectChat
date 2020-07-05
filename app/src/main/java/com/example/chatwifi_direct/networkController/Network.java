package com.example.chatwifi_direct.networkController;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;

public interface Network {
    void open() throws IOException;
    void connect(InetAddress ip) throws IOException;
    void close();
    InputStream getInputStream();
    OutputStream getOutputStream();
}
