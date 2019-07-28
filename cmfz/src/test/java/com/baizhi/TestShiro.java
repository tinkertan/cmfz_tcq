package com.baizhi;

import com.baizhi.util.SerializeUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.mgt.SubjectFactory;
import org.apache.shiro.subject.Subject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestShiro {
    @Test
    public void testShiro(){
        IniSecurityManagerFactory securityManagerFactory = new IniSecurityManagerFactory();
        //创建安全管理器
        SecurityManager securityManager = securityManagerFactory.getInstance();
        //将安全管理器放入工具中
        SecurityUtils.setSecurityManager(securityManager);
        //用工具创建主体
        Subject subject = SecurityUtils.getSubject();
        //创建令牌
        UsernamePasswordToken token = new UsernamePasswordToken("0d24d3d9-1ad3-4fea-b764-ad5dadc187f8","8136b941829600dfac40a0aa79205ac1");
        //主体带着令牌登录
        subject.login(token);
        boolean authenticated = subject.isAuthenticated();
        System.out.println(authenticated);
        boolean userRole = subject.hasRole("userRole");
        System.out.println(userRole);

        boolean permitted = subject.isPermitted("userpermission");
        System.out.println(permitted+"+++++++++++++++++");
    }
}
