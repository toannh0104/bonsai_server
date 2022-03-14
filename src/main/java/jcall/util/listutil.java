package com.ascendmoney.ami.report.util;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ListVerifyUtil {

    public static void assertListEquals(List<? extends Object> expected, List<? extends Object> actuals) {
        assertEquals("Expect size of list is " + expected.size() + " but actual size is " + actuals.size(),
                expected.size(), actuals.size());
        for(int i = 0; i < expected.size(); i++) {
            Object expectedElement = expected.get(i);
            Object actualElement = actuals.get(i);
            assertEquals("Expect element " + i + " is " + expectedElement + " but actual element is " + actualElement,
                    expectedElement, actualElement);
        }
    }

    public static void assertListEqualsWithoutOrder(List<? extends Object> expected, List<? extends Object> actuals) {
        assertEquals("Expect size of list is " + expected.size() + " but actual size is " + actuals.size() + "\nExpected: " + expected + "\nActuals: " + actuals,
                expected.size(), actuals.size());
        for (Object expectedElement : expected) {
            assertTrue("Expect element " + expectedElement + " but actual list doesn't contain" + "\nActuals: " + actuals,
                    actuals.contains(expectedElement));
        }
    }

    public static void assertArrayEquals(Object[] expected, Object[] actuals) {
        assertEquals("Expect length of array is " + expected.length + " but actual length is " + actuals.length,
                expected.length, actuals.length);
        for(int i = 0; i < expected.length; i++) {
            Object expectedElement = expected[i];
            Object actualElement = actuals[i];
            assertEquals("Expect element " + i + " is " + expectedElement + " but actual element is " + actualElement,
                    expectedElement, actualElement);
        }
    }

    public static void assertRowEquals(Row row, Object... expected) {
        for (int i = 0; i < expected.length; i++) {
            Object value = expected[i];
            Cell cell = row.getCell(i);
            if (value instanceof String) {
                assertEquals(String.format("Cell at index %s not match", i), value, cell.getStringCellValue());
            } else if (value instanceof BigDecimal) {
                assertEquals(String.format("Cell at index %s not match", i), ((BigDecimal) value).doubleValue(), cell.getNumericCellValue(), 0.0);
            } else if (value instanceof Integer) {
                assertEquals(String.format("Cell at index %s not match", i), (Integer) value, cell.getNumericCellValue(), 0.0);
            }
        }
    }
}
