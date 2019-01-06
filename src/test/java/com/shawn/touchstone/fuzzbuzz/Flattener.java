package com.shawn.touchstone.fuzzbuzz;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertArrayEquals;

/**
 * Created by shangfei on 23/7/17.
 */
public class Flattener {

    public Object[] arrays;
    public Integer[] expectedResult;

    @Before
    public void initArrays(){
        arrays = new Object[]{1, 2, new Object[]{new Object[]{3, 4}, new Object[]{new Object[]{5, 6}}, 7}};
        expectedResult = new Integer[]{1, 2, 3, 4, 5, 6, 7};
    }

    @Test
    public void testFlattenByStream(){
        Integer[] actualResult = Flatten.flattenByStream(arrays);
        assertArrayEquals(expectedResult, actualResult);
    }

    @Test
    public void testFlatternByRecursion(){
        List<Integer> flattenArrays = Flatten.flattenByRecursion(arrays);
        Integer[] actualResult = new Integer[flattenArrays.size()];
        flattenArrays.toArray(actualResult);
        assertArrayEquals(expectedResult, actualResult);
    }
}
