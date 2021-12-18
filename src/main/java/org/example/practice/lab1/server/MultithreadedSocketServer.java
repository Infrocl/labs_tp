package org.example.practice.lab1.server;

import java.io.IOException;
import java.net.*;

public class MultithreadedSocketServer {
    private static ServerSocket server;

    public static void main(String[] args) throws Exception {
        try {
            server = new ServerSocket(50001);
            int counter = 0;
            System.out.println("Запуск сервера ....");
            while (true) {
                counter++;
                Socket serverClient = server.accept();  // сервер принимает запрос на подключение клиента
                System.out.println(" >> " + "Пользователь №" + counter + " начал работу!");
                ServerClientThread sct = new ServerClientThread(serverClient, counter); // отправляем запрос в отдельный поток
                sct.start();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            server.close();
        }
    }
}

