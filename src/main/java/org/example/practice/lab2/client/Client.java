package org.example.practice.lab2.client;

import org.example.practice.lab2.server.ServerImageFilter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.rmi.NotBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;


public class Client {
    public static final String UNIQUE_BINDING_NAME = "FilterService";

    public static void main(String[] args) throws NotBoundException, IOException {
        String inputFileName;
        String outputFileName;
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in)); //запуск потока чтения с консоли
        System.out.println("Введите имя файла исходного изображения: ");
        inputFileName = in.readLine();
        System.out.println("Введите имя файла итогового изображения: ");
        outputFileName = in.readLine();
        Registry registry = LocateRegistry.getRegistry(3000);
        ServerImageFilter filter = (ServerImageFilter) registry.lookup(UNIQUE_BINDING_NAME);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        BufferedImage image = ImageIO.read(new File(inputFileName));
        ImageIO.write(image, "jpg", baos);
        byte[] inputImageArray = baos.toByteArray();
        // считываем полученный массив в объект BufferedImage и сохраняем объект BufferedImage в виде нового изображения
        ImageIO.write(ImageIO.read(new ByteArrayInputStream(filter.filter(inputImageArray))), "png", new File(outputFileName));
    }
}
