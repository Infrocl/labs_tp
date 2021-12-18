package org.example.practice.lab1.server;

import org.example.practice.lab1.Matrix;

import java.io.*;
import java.net.Socket;

public class ServerClientThread extends Thread {
    Socket clientSocket;
    int clientNo;
    BufferedWriter output;

    ServerClientThread(Socket inSocket, int counter) {
        clientSocket = inSocket;
        clientNo = counter;
    }

    @Override
    public void run() {
        try {
            BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            output = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            Matrix firstMatrix = Matrix.readMatrix(input);
            Matrix secondMatrix = Matrix.readMatrix(input);
            Matrix result = Matrix.multiply(firstMatrix, secondMatrix);
            output.write(1);
            Matrix.writeMatrix(output, result);
            output.flush();
            clientSocket.close();
            input.close();
            output.close();
            System.out.println(" >> " + "Пользователь №" + clientNo + " закончил работу!");
        } catch (IOException | IllegalArgumentException e) {
            System.out.println(" >> " + "Пользователь №" + clientNo + " закончил работу!");
            try {
                output.write(0);
                output.flush();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
}
