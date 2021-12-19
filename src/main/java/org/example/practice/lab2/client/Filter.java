package org.example.practice.lab2.client;

import javax.imageio.ImageIO;
import java.io.File;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Arrays;

public class Filter {
    //Медианная фильтрация с окном из 9 элементов, расположенных «крестом»
    public static void main(String[] args) {
        try {
            BufferedImage source = ImageIO.read(new File("input/Yae.jpg"));
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
                for (int j = 0; j < 2; j++) {
                    result.setRGB(i, j, source.getRGB(i, j));
                    result.setRGB(i, height - j - 1, source.getRGB(i, height - j - 1));
                }
            }
            for (int i = 0; i < 2; i++) {
                for (int j = 2; j < height - 2; j++) {
                    result.setRGB(i, j, source.getRGB(i, j));
                    result.setRGB(width - i - 1, j, source.getRGB(width - 1 - i, j));
                }
            } //применяем фильтр
            for (int i = 2; i <= width - 2; i++) {
                for (int j = 2; j <= height - 2; j++) {
                    int[] kernel = new int[8]; //создать класс Point
                    kernel[0] = inputImagePixels[i][j];
                    for (int k = 0, counter = -2; k < 2; k ++, counter ++) {
                        kernel[k] = inputImagePixels[i][j+counter];
                        kernel[3-k] = inputImagePixels[i][j-counter];
                        kernel[5-k] = inputImagePixels[i+counter][j];
                        kernel[7-k] = inputImagePixels[i-counter][j];
                    }
                    int[] sortedKernel = Arrays.copyOf(kernel, 8);
                    Arrays.sort(sortedKernel);
                    int index = Arrays.binarySearch(kernel,sortedKernel[4]);

                }
            }

            // Созраняем результат в новый файл
            //File output = new File("output/Yae_grey.jpg");
            //ImageIO.write(result, "jpg", output);

        } catch (IOException e) {

            // При открытии и сохранении файлов, может произойти неожиданный случай.
            // И на этот случай у нас try catch
            System.out.println("Файл не найден или не удалось сохранить");
        }
    }
}