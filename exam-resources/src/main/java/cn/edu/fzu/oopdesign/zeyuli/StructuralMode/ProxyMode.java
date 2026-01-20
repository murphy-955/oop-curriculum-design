package cn.edu.fzu.oopdesign.zeyuli.StructuralMode;

/**
 * 代理模式
 *
 * @author : 李泽聿
 * @since : 2026:01:20 09:41
 */
public class ProxyMode {
    public static void main() {
        UserServiceImpl userServiceImpl = new UserServiceImpl();
        UserService userServiceProxy = new ProxyConfig(userServiceImpl).createUserServiceProxy();
        userServiceProxy.saveUser("张三");
        System.out.println(userServiceProxy.findUserById(1L));
    }
}

// 定义接口
interface UserService {
    void saveUser(String username);
    String findUserById(Long id);
}

// 真实主题类 - 实现业务逻辑
class UserServiceImpl implements UserService {
    
    @Override
    public void saveUser(String username) {
        System.out.println("保存用户：" + username);
        // 模拟实际的数据库保存操作
    }
    
    @Override
    public String findUserById(Long id) {
        System.out.println("根据ID查询用户：" + id);
        // 模拟实际的数据库查询操作
        return "User" + id;
    }
}

// 代理类 - 添加额外功能如日志、权限检查等
class UserServiceProxy implements UserService {
    
    private final UserServiceImpl target;
    
    public UserServiceProxy(UserServiceImpl target) {
        this.target = target;
    }
    
    @Override
    public void saveUser(String username) {
        // 在执行业务逻辑前添加日志
        System.out.println("【代理】开始保存用户，时间：" + System.currentTimeMillis());
        
        // 调用真实对象的方法
        target.saveUser(username);
        
        // 在执行业务逻辑后添加日志
        System.out.println("【代理】完成保存用户，时间：" + System.currentTimeMillis());
    }
    
    @Override
    public String findUserById(Long id) {
        // 在执行业务逻辑前添加日志
        System.out.println("【代理】开始查询用户，时间：" + System.currentTimeMillis());
        
        // 调用真实对象的方法
        String result = target.findUserById(id);
        
        // 在执行业务逻辑后添加日志
        System.out.println("【代理】完成查询用户，时间：" + System.currentTimeMillis());
        return result;
    }
}

// 配置类，创建代理对象
class ProxyConfig {
    
    private final UserServiceImpl userServiceImpl;
    
    public ProxyConfig(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }
    
    public UserService createUserServiceProxy() {
        return new UserServiceProxy(userServiceImpl);
    }
}

