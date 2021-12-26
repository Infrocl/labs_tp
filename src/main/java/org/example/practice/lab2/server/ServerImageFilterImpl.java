package org.example.practice.lab2.server;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class ServerImageFilterImpl extends UnicastRemoteObject implements ServerImageFilter {
    @Serial
    private static final long serialVersionUID = -3931008145305629709L;
    ByteArrayOutputStream baos;
    // инициализация сервера
    public ServerImageFilterImpl() throws RemoteException {
        super();
    }
    @Override
    public byte[] filter(byte[] inputImageArray) {
        try {
            BufferedImage source = ImageIO.read(new ByteArrayInputStream(inputImageArray));
            int width = source.getWidth();
            int height = source.getHeight();
            BufferedImage result = new BufferedImage(width, height, source.getType());
            int[][] inputImagePixels = new int[width][height];
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    Color color = new Color(source.getRGB(x, y));
                    inputImagePixels[x][y] = (int) (color.getRed() * 0.299 + color.getGreen() * 0.587 + color.getBlue() * 0.114);
                }
            } //устанавливаем крайние пиксели
            for (int i = 0; i < width; i++) {
                for (int j = 0; j < 3; j++) {
                    result.setRGB(i, j, source.getRGB(i, j));
                    result.setRGB(i, height - j - 1, source.getRGB(i, height - j - 1));
                }
            }
            for (int i = 0; i < 2; i++) {
                for (int j = 2; j < height; j++) {
                    result.setRGB(i, j, source.getRGB(i, j));
                    result.setRGB(width - i - 1, j, source.getRGB(width - i - 1, j));
                }
            }
            //применяем фильтр
            for (int i = 2; i < width - 2; i++) {
                for (int j = 3; j < height - 3; j++) {
                    Kernel kernel = new Kernel(9);
                    kernel.setValue(inputImagePixels[i][j], 2, i, j);
                    for (int k = 0, counter = -2; k < 2; k++, counter++) {
                        kernel.setValue(inputImagePixels[i][j + counter], k, i, j + counter);
                        kernel.setValue(inputImagePixels[i][j - counter], 4 - k, i, j - counter);
                        kernel.setValue(inputImagePixels[i + counter][j], 6 - k, i + counter, j);
                        kernel.setValue(inputImagePixels[i + counter][j], 8 - k, i - counter, j);
                    }
                    int[] indexes = Kernel.findMedianIndexes(kernel);
                    result.setRGB(i, j, source.getRGB(indexes[0], indexes[1]));
                }
            }
            baos = new ByteArrayOutputStream();
            ImageIO.write(result, "png", baos);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        return baos.toByteArray();
    }

    public static void main(String[] args) {
        try {
            ServerImageFilter service = new ServerImageFilterImpl();
            String serviceName = "FilterService";
            // Регистрация удаленного RMI объекта в реестре rmi registry
            Registry registry = LocateRegistry.createRegistry(3000);
            registry.rebind(serviceName, service);
        } catch (RemoteException e) {
            System.err.println("RemoteException : " + e.getMessage());
        }
    }
}

