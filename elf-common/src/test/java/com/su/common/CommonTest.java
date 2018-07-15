package com.su.common;


import com.su.common.utils.encrypt.AESUtil;
import org.junit.Test;


/**
 * Hello world!
 *
 */
public class CommonTest {

    @Test
    public void test() {
        try {
            String str = AESUtil.encrypt("{\"key\":\"value\"}", "123");
            System.out.println(str);
            String s = AESUtil.decrypt(str, "123");
            System.out.println(s);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
