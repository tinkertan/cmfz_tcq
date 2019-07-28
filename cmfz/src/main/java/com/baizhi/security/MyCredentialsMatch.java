package com.baizhi.security;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.codec.CodecSupport;

import java.security.MessageDigest;

public class MyCredentialsMatch extends CodecSupport implements CredentialsMatcher {
    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        Object tokenCredentials = token.getCredentials();
        Object infoCredentials = info.getCredentials();
        byte[] tokenBytes = toBytes(tokenCredentials);
        byte[] accountBytes = toBytes(infoCredentials);
        return MessageDigest.isEqual(tokenBytes, accountBytes);
    }
}
