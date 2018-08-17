package com.su.common;


import com.su.common.utils.DateUtil;
import org.junit.Test;

import java.text.ParseException;
import java.util.Date;

/**
 * Hello world!
 *
 */
public class CommonTest {

    @Test
    public void test() {
        String time = "2018-08-16 10:00-11:00";
        System.out.println(time.substring(0, 16));
        /*
        long now = System.currentTimeMillis();
        System.out.println(now);
        Date today = new Date();
        System.out.println(DateUtil.date2String(today, "yyyy-MM-dd HH:mm:ss"));

        Date yeye = DateUtil.addHour(today, -24);
        System.out.println(DateUtil.date2Long(yeye));
        System.out.println(DateUtil.date2String(yeye, "yyyy-MM-dd HH:mm:ss"));
        */
    }

}
