package org.example.practice.lab1;

import org.testng.annotations.Test;

import java.io.*;

import static org.testng.Assert.*;

public class MatrixTest {
    Matrix getFirstMatrix() {
        Matrix firstMatrix = new Matrix(4, 3);
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 3; j++) {
                firstMatrix.setAt(i, j, i + 1);
            }
        }
        return firstMatrix;
    }

    Matrix getSecondMatrix() {
        Matrix secondMatrix = new Matrix(3, 2);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 2; j++) {
                secondMatrix.setAt(i, j, j + 1);
            }
        }
        return secondMatrix;
    }

    @Test
    public void testGetNumberOfRows() {
        assertEquals(getFirstMatrix().getNumberOfRows(), 4);
        assertEquals(getSecondMatrix().getNumberOfRows(), 3);
    }

    @Test
    public void testGetNumberOfColumns() {
        assertEquals(getFirstMatrix().getNumberOfColumns(), 3);
        assertEquals(getSecondMatrix().getNumberOfColumns(), 2);
    }

    @Test
    public void testGetAt() {
    }

    @Test
    public void testSetAt() {
    }

    @Test
    public void testWriteMatrix() {
        try (BufferedWriter firstMatrixWriter = new BufferedWriter(new FileWriter("output/matrix1.txt"));
             BufferedWriter secondMatrixWriter = new BufferedWriter(new FileWriter("output/matrix2.txt"))) {
            Matrix firstMatrix = getFirstMatrix();
            Matrix secondMatrix = getSecondMatrix();
            Matrix.writeMatrix(firstMatrixWriter, firstMatrix);
            Matrix.writeMatrix(secondMatrixWriter, secondMatrix);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testPrintMatrix() {
        Matrix.printMatrix(getFirstMatrix());
        Matrix.printMatrix(getSecondMatrix());
    }

    @Test
    public void testReadMatrix() {
        try (BufferedReader in = new BufferedReader(new FileReader("output/matrix1.txt"));
             BufferedReader in2 = new BufferedReader(new FileReader("output/matrix2.txt"))) {
            Matrix matrix = Matrix.readMatrix(in);
            Matrix secondMatrix = Matrix.readMatrix(in2);
            Matrix.printMatrix(matrix);
            Matrix.printMatrix(secondMatrix);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testMultiply() {
        Matrix.printMatrix(Matrix.multiply(getFirstMatrix(), getSecondMatrix()));
        assertThrows(IllegalArgumentException.class, () -> Matrix.multiply(getSecondMatrix(), getFirstMatrix()));
    }
}