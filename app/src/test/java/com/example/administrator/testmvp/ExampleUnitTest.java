package com.example.administrator.testmvp;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
        List<Integer> list = new ArrayList<>();
        list.add(0);
        list.add(0,1);
        list.add(1,2);
        for(Integer i : list) {
            System.out.println("==:"+i);
        }
    }
}