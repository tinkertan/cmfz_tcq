package com.baizhi.security;


import com.baizhi.dao.AdminDAO;
import com.baizhi.dao.UserDAO;
import com.baizhi.entity.Admin;
import com.baizhi.entity.Permission;
import com.baizhi.entity.Role;
import com.baizhi.entity.User;
import com.baizhi.util.SpringContextUtil;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import java.util.List;

public class MyAdminRealm extends AuthorizingRealm {
    @Override
    //授权
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        AdminDAO adminDAO = (AdminDAO) SpringContextUtil.getBean(AdminDAO.class);
        Admin admin = adminDAO.selectByName((String) principals.getPrimaryPrincipal());
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        //遍历角色数组，添加角色
        //遍历权限数组，添加权限
        List<Role> role = admin.getRole();
        if(role==null) return simpleAuthorizationInfo;
        for (Role role1 : role) {
            simpleAuthorizationInfo.addRole(role1.getName());
            List<Permission> permission = role1.getPermission();
            if(permission==null) continue;
            for (Permission permission1 : permission) {
                simpleAuthorizationInfo.addStringPermission(permission1.getName());
            }
        }
        System.out.println("进行授权");
        return simpleAuthorizationInfo;
    }

    @Override
    //认证
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        AdminDAO adminDAO = (AdminDAO) SpringContextUtil.getBean(AdminDAO.class);
        String principal = (String) token.getPrincipal();
        Admin admin = adminDAO.selectByName(principal);
        System.out.println("进行认证");
        if(admin==null){
            return null;
        }
        return new SimpleAuthenticationInfo(admin.getUsername(),admin.getPassword(), ByteSource.Util.bytes(admin.getSalt()), MyAdminRealm.class.getName());
    }
}
