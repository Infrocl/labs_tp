package org.example.practice.lab1.server;

import org.example.practice.lab1.Matrix;

import java.io.*;
import java.net.Socket;

public class ServerClientThread extends Thread { //поток выполнения
    Socket clientSocket;
    int clientNo;
    BufferedWriter output;

    ServerClientThread(Socket inSocket, int counter) {
        clientSocket = inSocket;
        clientNo = counter;
    }

    @Override  //переопределяем метод run() класса Thread
    public void run() {
        try { //потоки ввода-вывода
            BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            output = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            Matrix firstMatrix = Matrix.readMatrix(input);
            Matrix secondMatrix = Matrix.readMatrix(input);
            Matrix result = Matrix.multiply(firstMatrix, secondMatrix);
            output.write(1); //передаём флаг
            Matrix.writeMatrix(output, result);
            output.flush(); //принудительно записываем в поток данные из буфера
            clientSocket.close();
            input.close();
            output.close();
        } catch (IOException | IllegalArgumentException | NullPointerException e) {
            try {
                output.write(0);
                output.flush();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        } finally {
            System.out.println(" >> " + "Пользователь №" + clientNo + " закончил работу!");
        }
    }
}
