package com.fanlm.config;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import com.fanlm.shiro.realm.CustomerRealm;
import com.fanlm.shiro.realm.MySessionDao;
import com.fanlm.shiro.realm.MyShiroSessionListener;
import com.fanlm.shiro.realm.ShiroRedisCacheManager;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authc.pam.AtLeastOneSuccessfulStrategy;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListener;
import org.apache.shiro.session.mgt.ExecutorServiceSessionValidationScheduler;
import org.apache.shiro.session.mgt.eis.JavaUuidSessionIdGenerator;
import org.apache.shiro.session.mgt.eis.SessionIdGenerator;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class ShiroConfig {
    @Bean(name = "shiroFilter")
    public ShiroFilterFactoryBean shiroFilter() {
        System.out.println("ShiroConfiguration.shirFilter()");
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        //给filter设置安全管理器
        shiroFilterFactoryBean.setSecurityManager(securityManager());
        // 拦截器. 设置系统公共资源、拦截资源
        // authc 请求这个资源需要认证、授权
        // anon  指定URL 可以匿名访问

        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
        // filterChainDefinitionMap.put("/details", "user");
        // 配置不会被拦截的链接 顺序判断
        filterChainDefinitionMap.put("/css/**", "anon");
        filterChainDefinitionMap.put("/fonts/**", "anon");
        filterChainDefinitionMap.put("/img/**", "anon");
        filterChainDefinitionMap.put("/js/**", "anon");
        filterChainDefinitionMap.put("/plugins/**", "anon");
        filterChainDefinitionMap.put("/custom-js/**", "anon");

        filterChainDefinitionMap.put("/", "anon");
        filterChainDefinitionMap.put("/leftmenu", "anon");
        filterChainDefinitionMap.put("/checkLogin", "anon");
        filterChainDefinitionMap.put("/ui/articles", "anon");
        // 配置退出 过滤器,其中的具体的退出代码Shiro已经替我们实现了
        filterChainDefinitionMap.put("/logout", "logout");
        filterChainDefinitionMap.put("/**", "authc");
        // 如果不设置默认会自动寻找Web工程根目录下的"/login.jsp"页面
        shiroFilterFactoryBean.setLoginUrl("/login");
        // 登录成功后要跳转的链接
        shiroFilterFactoryBean.setSuccessUrl("/");
        // 未授权界面;
        shiroFilterFactoryBean.setUnauthorizedUrl("/403");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }

    /**
     * 加密方式
     *
     * @return
     */
    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName("md5");// 散列算法:这里使用MD5算法;
        hashedCredentialsMatcher.setHashIterations(1024);// 散列的次数，比如散列两次，相当于md5(md5(""));
        hashedCredentialsMatcher.setStoredCredentialsHexEncoded(true);// 表示是否存储散列后的密码为16进制，需要和生成密码时的一样，默认是base64；
        return hashedCredentialsMatcher;
    }

    /**
     * Realm实现
     *
     * @return
     */
    @Bean
    public CustomerRealm myRealm() {
        CustomerRealm myRealm = new CustomerRealm();
        myRealm.setCredentialsMatcher(hashedCredentialsMatcher());

//        开启缓存管理器
//        myRealm.setCacheManager(new EhCacheManager());
//        myRealm.setCachingEnabled(true);  //开启全局缓存
//        myRealm.setAuthenticationCachingEnabled(true);//开启认证缓存
//        myRealm.setAuthenticationCacheName("authenticationCache");
//
//        myRealm.setAuthorizationCachingEnabled(true);//开启授权缓存
//        myRealm.setAuthorizationCacheName("authorizationCache");

        return myRealm;
    }

    @Bean
    public Collection<Realm> realms() {
        Collection<Realm> realms = new ArrayList<>();
        realms.add(myRealm());
        return realms;
    }

    /**
     * shiro缓存管理器; 需要注入对应的其它的实体类中： 1、安全管理器：securityManager
     * 可见securityManager是整个shiro的核心；
     *
     * @return
     */
    @Bean
    public EhCacheManager cacheManager() {
        System.out.println("ShiroConfiguration.ehCacheManager()");
        EhCacheManager cacheManager = new EhCacheManager();
        cacheManager.setCacheManagerConfigFile("classpath:ehcache-shiro.xml");
        return cacheManager;
    }

    @Bean
    AtLeastOneSuccessfulStrategy authenticationStrategy() {
        return new AtLeastOneSuccessfulStrategy();
    }

    /**
     * 当只有一个Realm时，就使用这个Realm，当配置了多个Realm时，会使用所有配置的Realm。
     *
     * @return
     */
    @Bean
    ModularRealmAuthenticator authenticator() {
        ModularRealmAuthenticator authenticator = new ModularRealmAuthenticator();
        authenticator.setAuthenticationStrategy(authenticationStrategy());
        return authenticator;
    }

    @Bean
    public SimpleCookie rememberMeCookie() {
        // 这个参数是cookie的名称，对应前端的checkbox的name = rememberMe
        SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
        // <!-- 记住我cookie生效时间30天 ,单位秒;-->
        simpleCookie.setMaxAge(259200);
        return simpleCookie;
    }

    /**
     * CookieRememberMeManager
     *
     * @return
     */
    @Bean
    public CookieRememberMeManager rememberMeManager() {
        System.out.println("ShiroConfiguration.rememberMeManager()");
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        cookieRememberMeManager.setCookie(rememberMeCookie());
        cookieRememberMeManager.setCipherKey(Base64.decode("2AvVhdsgUs0FSA3SDFAdag=="));
        return cookieRememberMeManager;
    }

    @Bean
    public MyShiroSessionListener myShiroSessionListener() {
        return new MyShiroSessionListener();
    }

    /**
     * 会话监听器
     *
     * @return
     */
    @Bean
    public Collection<SessionListener> sessionListeners() {
        Collection<SessionListener> listeners = new ArrayList<>();
        listeners.add(myShiroSessionListener());
        return listeners;
    }

    /**
     * 会话ID生成器
     *
     * @return
     */
    @Bean
    public SessionIdGenerator sessionIdGenerator() {
        SessionIdGenerator idGenerator = new SessionIdGenerator() {
            @Override
            public Serializable generateId(Session session) {
                Serializable uuid = new JavaUuidSessionIdGenerator().generateId(session);
                System.out.println("sessionIdGenerator:" + uuid);
                return uuid;
            }
        };
        return idGenerator;
    }

    /**
     * 会话DAO
     *
     * @return
     */
    @Bean
    public MySessionDao mySessionDao() {
        System.out.println("ShiroConfiguration.mySessionDao()");
        MySessionDao mySessionDao = new MySessionDao();
        mySessionDao.setActiveSessionsCacheName("shiro-activeSessionCache");
        mySessionDao.setSessionIdGenerator(sessionIdGenerator());
        return mySessionDao;
    }

    /**
     * 处理session有效期
     *
     * @return
     */
    @Bean
    public ExecutorServiceSessionValidationScheduler sessionValidationScheduler() {
        ExecutorServiceSessionValidationScheduler sessionValidationScheduler = new ExecutorServiceSessionValidationScheduler();
        sessionValidationScheduler.setInterval(1800000);
        return sessionValidationScheduler;
    }

    @Bean(name = "sessionManager")
    public DefaultWebSessionManager sessionManager() {
        System.out.println("ShiroConfiguration.sessionManager()");
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.getSessionIdCookie().setName("sId");
        sessionManager.setGlobalSessionTimeout(1800000);
        sessionManager.setDeleteInvalidSessions(true);
        sessionManager.setSessionValidationScheduler(sessionValidationScheduler());
        sessionManager.setSessionValidationSchedulerEnabled(true);
        sessionManager.setSessionListeners(sessionListeners());
        sessionManager.setSessionDAO(mySessionDao());
        return sessionManager;
    }

    /**
     * 会话管理器
     *
     * @return
     */
    @Bean(name = "securityManager")
    public DefaultWebSecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //设置安全管理器
        //设置 权限、角色、用户 缓存
        securityManager.setCacheManager(new ShiroRedisCacheManager(new RedisTemplate<String, Object>()));

        securityManager.setAuthenticator(authenticator());
        securityManager.setRememberMeManager(rememberMeManager());
        securityManager.setRealms(realms());
        //securityManager.setSessionManager(sessionManager());
        return securityManager;
    }

    /**
     * 开启shiro注解 ---- 注解权限
     *
     * @param securityManager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        System.out.println("ShiroConfiguration.authorizationAttributeSourceAdvisor()");
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    /**
     * Shiro生命周期处理器 ---可以自定的来调用配置在 Spring IOC 容器中 shiro bean 的生命周期方法.
     *
     * @return
     */
    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    /**
     * 开启shiro注解 ----启用 IOC 容器中使用 shiro 的注解. 但必须在配置了 LifecycleBeanPostProcessor
     * 之后才可以使用
     *
     * @return
     */
    @Bean
    @DependsOn("lifecycleBeanPostProcessor")
    public DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator daap = new DefaultAdvisorAutoProxyCreator();
        daap.setProxyTargetClass(true);
        return daap;
    }



    /**
     * RedisTemplate配置--Shiro专属
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(LettuceConnectionFactory lettuceConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        RedisSerializer<String> stringSerializer = new StringRedisSerializer();
        JdkSerializationRedisSerializer fastJsonRedisSerializer = new JdkSerializationRedisSerializer();

        redisTemplate.setKeySerializer(stringSerializer);
        redisTemplate.setValueSerializer(fastJsonRedisSerializer);

        redisTemplate.setHashKeySerializer(stringSerializer);
        redisTemplate.setHashValueSerializer(fastJsonRedisSerializer);

        redisTemplate.setConnectionFactory(lettuceConnectionFactory);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }
}
