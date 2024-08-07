package org.example;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Client {
    public Socket socket;
    public BufferedReader in;
    public BufferedWriter out;
    public Avatar avatar;
    public InetAddress inetAddress;
    public List<AvatarBullet> bulletList;

    public Client(Socket socket, int id) throws IOException {
        this.socket = socket;
        bulletList = new ArrayList<>();
        avatar = new Avatar(id);

        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        inetAddress = socket.getInetAddress();
    }

    public boolean checkEqualAddr(InetAddress inetAddress_) {
        return inetAddress.getHostAddress().equals(inetAddress_.getHostAddress());
    }
}
