package org.example.practice.lab1.client;

import org.example.practice.lab1.Matrix;

import java.io.*;
import java.net.Socket;

public class Client {
    public static void main(String[] args) {
        try {
            Socket clientSocket = new Socket("localhost", 50001); //localhost = 127.0.0.1 - локальная сеть
            String[] fileNames = new String[3]; //массив названий файлов
            Matrix firstMatrix;
            Matrix secondMatrix;
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in)); //запуск потока чтения с консоли
            System.out.println("Введите имя файла первой матрицы: ");
            fileNames[0] = in.readLine();
            System.out.println("Введите имя файла второй матрицы: ");
            fileNames[1] = in.readLine();
            System.out.println("Введите имя файла для результата вычислений: ");
            fileNames[2] = in.readLine();
            in.close(); // закрываем поток
            //потоки взаимодействия с сервером
            BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            BufferedWriter output = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            try (BufferedReader in1 = new BufferedReader(new FileReader(fileNames[0])); //try-with-resources
                 BufferedReader in2 = new BufferedReader(new FileReader(fileNames[1]))) {
                firstMatrix = Matrix.readMatrix(in1); //читаем из файлов матрицы
                secondMatrix = Matrix.readMatrix(in2);
                Matrix.writeMatrix(output, firstMatrix); //отправляем матрицы на сервер
                Matrix.writeMatrix(output, secondMatrix);
            } catch (IOException e) {
                System.out.println(e.getMessage());
                input.close();
                output.close();
            }
            int flag = input.read();
            if (flag == 1) {
                Matrix result = Matrix.readMatrix(input);
                try (BufferedWriter resultMatrixWriter = new BufferedWriter(new FileWriter(fileNames[2]))) {
                    Matrix.writeMatrix(resultMatrixWriter, result);
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            } else {
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileNames[2]))) {
                    PrintWriter write = new PrintWriter(writer);
                    write.println("Невозможно выполнить операцию...");
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
            input.close();
            output.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}



