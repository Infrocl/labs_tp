package org.example.practice.lab2.server;

import java.util.Arrays;

public class Kernel {
    private final int[] values;
    private final int[][] indexes;

    public Kernel(int numberOfValues) {
        this.values = new int[numberOfValues];
        this.indexes = new int[numberOfValues][2];
    }

    public int getNumberOfValues() {
        return values.length;
    }

    public void setValue(int value, int index, int x, int y) {
        values[index] = value;
        indexes[index][0] = x;
        indexes[index][1] = y;
    }

    public int[] getValues() {
        return values;
    }

    public int[] getIndexes(int index) {
        return indexes[index];
    }

    public static int[] findMedianIndexes(Kernel kernel) {
        int[] valuesArray = kernel.getValues();
        Arrays.sort(valuesArray);
        int[] inputValuesArray = kernel.getValues();
        int m = 0;
        for (int i = 0; i < 9; i++) {
            if (inputValuesArray[i] == valuesArray[(kernel.getNumberOfValues() - 1) / 2]) {
                m = i;
            }
        }
        return kernel.getIndexes(m);
    }
}
