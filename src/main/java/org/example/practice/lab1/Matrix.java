package org.example.practice.lab1;

import java.io.*;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

public class Matrix {
    private final double[][] matrix;

    public Matrix(int numberOfRows, int numberOfColumns) {
        if (numberOfRows == 0) {
            throw new IllegalArgumentException("Ошибка: в матрице должна быть хотя бы одна строка!");
        }
        if (numberOfColumns == 0) {
            throw new IllegalArgumentException("Ошибка: в матрице должен быть хотя бы один столбец!");
        }
        matrix = new double[numberOfRows][numberOfColumns];
    }

    public int getNumberOfRows() {
        return matrix.length;
    }

    public int getNumberOfColumns() {
        return matrix[0].length;
    }

    public double getAt(int m, int n) {
        return matrix[m][n];
    }

    public void setAt(int m, int n, double value) {
        matrix[m][n] = value;
    }

    public static Matrix multiply(Matrix firstMatrix, Matrix secondMatrix) {
        if (firstMatrix.getNumberOfColumns() != secondMatrix.getNumberOfRows()) {
            throw new IllegalArgumentException("Ошибка: число столбцов первой матрицы не равно числу строк второй матрицы");
        }
        Matrix result = new Matrix(firstMatrix.getNumberOfRows(), secondMatrix.getNumberOfColumns());
        for (int i = 0; i < firstMatrix.getNumberOfRows(); i++) {
            for (int j = 0; j < secondMatrix.getNumberOfColumns(); j++) {
                double sum = 0;
                for (int k = 0; k != secondMatrix.getNumberOfRows(); k++) {
                    sum += firstMatrix.getAt(i, k) * secondMatrix.getAt(k, j);
                }
                result.setAt(i, j, sum);
            }
        }
        return result;
    }

    public static void printMatrix(Matrix matrix) {
        for (int i = 0; i < matrix.getNumberOfRows(); i++) {
            for (int j = 0; j < matrix.getNumberOfColumns(); j++) {
                System.out.print(" " + matrix.getAt(i, j));
            }
            System.out.println();
        }
    }

    public static void writeMatrix(BufferedWriter writer, Matrix matrix) throws IOException {
        PrintWriter write = new PrintWriter(writer);
        int numberOfRows = matrix.getNumberOfRows();
        int numberOfColumns = matrix.getNumberOfColumns();
        write.printf("%d %d\n", numberOfRows, numberOfColumns);
        for (int i = 0; i < numberOfRows; i++) {
            for (int j = 0; j < numberOfColumns; j++) {
                write.printf("%f ", matrix.getAt(i, j));
            }
            write.println();
        }
        write.flush();
    }

    public static Matrix readMatrix(BufferedReader reader) throws IOException {
        NumberFormat formatter = NumberFormat.getInstance(Locale.forLanguageTag("ru"));
        String[] size = reader.readLine().split(" ");
        int numberOfRows;
        int numberOfColumns;
        try {
            numberOfRows = formatter.parse(size[0]).intValue();
            numberOfColumns = formatter.parse(size[1]).intValue();
            Matrix matrix = new Matrix(numberOfRows, numberOfColumns);
            for (int i = 0; i < numberOfRows; i++) {
                String[] values = reader.readLine().split(" ");
                for (int j = 0; j < numberOfColumns; j++) {
                    matrix.setAt(i, j, formatter.parse(values[j]).doubleValue());
                }
            }
            return matrix;
        } catch (ParseException e) {
            return new Matrix(0,0);
        }
    }
}
