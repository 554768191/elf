package com.su;


import com.su.common.utils.CaptchaUtil;
import com.su.common.utils.encrypt.AESUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * Hello world!
 *
 */
public class App {


    public static void main( String[] args ) {
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
