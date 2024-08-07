package org.example;

import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class Server {

    //UDP
    private DatagramSocket udpSocket;
    private final int PORT_UDP = 5556;

    //TCP
    private volatile ServerSocket serverSocket;
    private List<Client> clients;
    private final int PORT_TCP = 5555;

    private int id_fix; //присваивает новый id
    private final int delay = 50; //задержка отправки запросов

    Server() {
        try {
            udpSocket = new DatagramSocket(PORT_UDP);
            serverSocket = new ServerSocket(PORT_TCP);
            clients = new ArrayList<>();
            id_fix = 0;

        } catch (SocketException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    /** Любое прослушивание TCP и UDP */
    public void listener() {

        /** Регистрация новых клиентов */
        new Thread(new Runnable() {
            @Override
            public void run() {

                while (true) {
                    try {
                        Socket socket = serverSocket.accept();

                        //Проверим наличие неактуального зарегистрированного клиента с этого адреса
                        for (int j = 0; j < clients.size(); j++)
                            if (clients.get(j).checkEqualAddr(socket.getInetAddress()))
                                clients.remove(clients.get(j)); //удаляем чтобы после добавить актуального

                        id_fix += 1;
                        clients.add(new Client(socket, id_fix));
                        socket.getOutputStream().write(String.valueOf(id_fix).getBytes());
                        socket.getOutputStream().flush();

                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }).start();

        /** Задача потока обновлять данные аватара клиента. Держать данные аватара актуальными */
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        byte[] receiveData = new byte[1024];
                        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                        udpSocket.receive(receivePacket);

                        InetAddress inetAddress = receivePacket.getAddress();
                        Client CurrentClient = null;
                        for (Client client : clients)
                            if (client.checkEqualAddr(inetAddress)) {
                                CurrentClient = client;
                                break;
                            }
                        if (CurrentClient == null)
                            throw new IOException("Запрос от незарегистрированного клиента");

                        byte typeId = receivePacket.getData()[0];

                        if (typeId == 1) //тип аватара игрока
                            CurrentClient.avatar = Avatar.fromByteArray(receivePacket.getData(), 1);
                        if (typeId == 2) //тип аватара пули игрока
                            CurrentClient.bulletList.add(AvatarBullet.fromByteArray(receivePacket.getData(), 1));

                    } catch (IOException e) {
                        System.err.println(e.getMessage());
                    }
                }
            }
        }).start();
    }

    /** Отправка UDP датаграмм аватаров всех типов всем игрокам */
    public void engine() {
        while (true) {

            try {

                for (Client client : clients) {
                    for (Client client1 : clients) {
                        if (client != client1) {

                            //Отправляем аватар игрока
                            ByteBuffer buffer = ByteBuffer.allocate(128);
                            buffer.put((byte) 1);
                            buffer.put(client1.avatar.toByteArray());
                            byte[] bytes = buffer.array();

                            DatagramPacket packet = new DatagramPacket(bytes, bytes.length, client.inetAddress, PORT_UDP);
                            udpSocket.send(packet);

                            //=============================================================

                            //отправляем аватары пуль после удаляем их
                            for (AvatarBullet bullet : client1.bulletList) {
                                ByteBuffer buffer2 = ByteBuffer.allocate(128);
                                buffer2.put((byte) 2);
                                buffer2.put(bullet.toByteArray());
                                byte[] bytes2 = buffer2.array();

                                DatagramPacket packet_bullet = new DatagramPacket(bytes2, bytes2.length, client.inetAddress, PORT_UDP);
                                udpSocket.send(packet_bullet);
                            }
                            client1.bulletList.clear();

                        }
                    }
                }

                Thread.sleep(delay);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }


}
