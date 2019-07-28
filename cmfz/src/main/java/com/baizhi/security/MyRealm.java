package com.baizhi.security;


import com.baizhi.dao.UserDAO;
import com.baizhi.entity.User;
import com.baizhi.util.MD5Utils;
import com.baizhi.util.SpringContextUtil;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

public class MyRealm extends AuthorizingRealm {
    @Override
    //授权
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String primaryPrincipal = (String) principals.getPrimaryPrincipal();
        UserDAO userDAO = (UserDAO) SpringContextUtil.getBean(UserDAO.class);
        User user = userDAO.selectById(primaryPrincipal);
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.addRole("userRole");
        simpleAuthorizationInfo.addStringPermission("userpermission");
        System.out.println("进行授权");
        return simpleAuthorizationInfo;
    }

    @Override
    //认证
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UserDAO userDAO = (UserDAO) SpringContextUtil.getBean(UserDAO.class);
        String principal = (String) token.getPrincipal();
        User user = userDAO.selectById((String) token.getPrincipal());
        //String password = (String) token.getCredentials();
        Object credentials = token.getCredentials();
        System.out.println("进行认证");
        if(user==null){
            return null;
        }
        //没有加密方式的匹配
        return new SimpleAuthenticationInfo(user.getId(),user.getPassword(),MyRealm.class.getName());
        //使用shiro中提供的方式进行加密之后的验证匹配，这个需要在配置文件中配置这个的匹配方式
        //return new SimpleAuthenticationInfo(user.getId(),user.getPassword(), ByteSource.Util.bytes(user.getSalt()),MyRealm.class.getName());
    }
}
