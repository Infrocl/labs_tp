package org.example.practice.lab2.filter;

import org.example.practice.lab2.server.Kernel;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class KernelTest {
    private final int[] values = new int[9];

    private Kernel getKernel() {
        Kernel kernel = new Kernel(9);
        for (int i = 0; i < 9; i++) {
            kernel.setValue(i, i, i + 1, i + 2);
            values[i] = i;
        }
        values[4] = 100;
        kernel.setValue(100, 4, 4 + 1, 4 + 2);
        values[6] = 40;
        kernel.setValue(40, 6, 6 + 1, 6 + 2);
        values[1] = 200;
        kernel.setValue(200, 1, 1 + 1, 1 + 2);
        values[8] = 2;
        kernel.setValue(2, 8, 8 + 1, 8 + 2);
        return kernel;
    }


    @Test
    public void testGetNumberOfValues() {
        assertEquals(9, getKernel().getNumberOfValues());
    }

    @Test
    public void testSetValue() {
    }

    @Test
    public void testGetValues() {
        Kernel kernel = getKernel();
        assertEquals(values, kernel.getValues());
    }

    @Test
    public void testGetIndexes() {
        Kernel kernel = getKernel();
        assertEquals(new int[]{1, 2}, kernel.getIndexes(0));
    }

    @Test
    public void testFindMedianIndexes() {
        Kernel kernel = getKernel();
        for (int value : values) {
            System.out.println(value);
        }
        assertEquals(new int[]{5, 6}, Kernel.findMedianIndexes(kernel));
    }
}