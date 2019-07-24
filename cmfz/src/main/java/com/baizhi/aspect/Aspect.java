package com.baizhi.aspect;

import com.baizhi.util.SerializeUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.lang.reflect.Method;

@Configuration
@org.aspectj.lang.annotation.Aspect
public class Aspect {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Around("execution(* com.baizhi.service.*.query*(..))")
    public Object queryAspect(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
        String methodName = methodSignature.getMethod().getName();
        Object[] args = proceedingJoinPoint.getArgs();
        StringBuilder stringBuilder = new StringBuilder(methodName);
        for (Object arg : args) {
            String s = arg.toString();
            stringBuilder.append(s);
        }
        String name = proceedingJoinPoint.getTarget().getClass().getName();
        String o = (String) stringRedisTemplate.opsForHash().get(name, stringBuilder.toString());
        System.out.println(name+"=="+stringBuilder.toString());
        if(o==null){
            Object proceed = proceedingJoinPoint.proceed();
            System.out.println("缓存没有，执行，存入缓存");
            stringRedisTemplate.opsForHash().put(name,stringBuilder.toString(), SerializeUtils.serialize(proceed));
            return proceed;
        }else {
            System.out.println("缓存中有返回");
            return SerializeUtils.serializeToObject(o);
        }
    }

    @AfterReturning("execution(* com.baizhi.service.*.*(..)) && !execution(* com.baizhi.service.*.query*(..))")
    public void aspect2(JoinPoint joinPoint){
        String name = joinPoint.getTarget().getClass().getName();
        System.out.println("修改，清空缓存");
        stringRedisTemplate.delete(name);
    }
}
