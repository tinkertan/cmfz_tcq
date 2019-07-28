package com.baizhi;

import com.sun.org.apache.bcel.internal.generic.NEW;
import org.apache.commons.lang3.builder.ToStringExclude;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.junit.Test;
import org.omg.CORBA.PUBLIC_MEMBER;

public class TestMD5 {

    @Test
    public void testPass(){
        Md5Hash md5Hash = new Md5Hash("123456","qwer");
        md5Hash.setIterations(1024);
        String s1 = md5Hash.toString();
        System.out.println(s1);
        String s3 = md5Hash.toHex();
        System.out.println(s3);
    }
}
