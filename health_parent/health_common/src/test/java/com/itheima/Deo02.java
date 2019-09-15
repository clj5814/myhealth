package com.itheima;

import org.junit.Test;

import java.util.Calendar;

public class Deo02 {
    @Test
    public void fun(){
        Calendar c = Calendar.getInstance();
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
        if (dayOfWeek == 0) {
            dayOfWeek = 7;
        }
        c.add(Calendar.DATE, -dayOfWeek + 7);
        System.out.println(c.getTime());
    }
}
