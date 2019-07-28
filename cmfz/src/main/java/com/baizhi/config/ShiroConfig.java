package com.baizhi.config;

import com.baizhi.security.MyAdminRealm;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;

import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.mgt.WebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {
    @Bean
    public ShiroFilterFactoryBean getShiroFilter(WebSecurityManager securityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        Map<String, String> map = new HashMap<>();
        map.put("/jsp/*","authc");
        map.put("/admin/*","authc");
        map.put("/album/*","authc");
        map.put("/article/*","authc");
        map.put("/carousel/*","authc");
        map.put("/chapter/*","authc");
        map.put("/guru/*","authc");
        map.put("/user/*","authc");
        map.put("/admin/login","anon");
        map.put("/jsp/main.jsp","anon");
       shiroFilterFactoryBean.setFilterChainDefinitionMap(map);
       shiroFilterFactoryBean.setLoginUrl("/jsp/login.jsp");
        return shiroFilterFactoryBean;
    }
    //将安全管理器交给工厂管理
    @Bean
    public WebSecurityManager getWebSecurityManager(AuthorizingRealm realm){
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        defaultWebSecurityManager.setRealm(realm);
        return defaultWebSecurityManager;
    }
    //将realm源交给工厂管理
    @Bean
    public AuthorizingRealm getRealm(CredentialsMatcher hashedCredentialsMatcher){
        MyAdminRealm myRealm = new MyAdminRealm();
        myRealm.setCredentialsMatcher(hashedCredentialsMatcher);
        return myRealm;
    }
    //将认证匹配器交给工厂管理
    @Bean
    public CredentialsMatcher getCredentialsMatcher(){
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName("MD5");
        hashedCredentialsMatcher.setHashIterations(0);
        return hashedCredentialsMatcher;
    }
}
