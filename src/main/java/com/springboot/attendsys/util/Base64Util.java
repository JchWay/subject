package com.springboot.attendsys.util;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

public class Base64Util{
    public static String EncodeByBase64(String str)throws UnsupportedEncodingException {
        Base64.Encoder encoder=Base64.getEncoder();
        byte[]strByte=str.getBytes("UTF-8");
        String encodedText=encoder.encodeToString(strByte);
        return encodedText;
    }
}