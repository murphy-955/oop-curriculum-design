package cn.edu.fzu.oopdesign.zeyuli.CreatedMode;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * 单例模式<br>
 * <b>确保一个类只有一个实例，并提供一个全局访问点来访问示例</b>
 *
 * @author : 李泽聿
 * @since : 2026:01:02 20:23
 */
@Slf4j
public class SingletonPattern {
    public static void main() {
        // 从未创建过实例，创建实例
        LazyManMode lazyManMode1 = LazyManMode.getInstance();
        // 已经创建过实例，直接返回
        LazyManMode lazyManMode2 = LazyManMode.getInstance();
        log.info("内存地址是否相同：{}", lazyManMode2.equals(lazyManMode1));

        // 饿汉模式
        HungryManMode hungryManMode1 = HungryManMode.getINSTANCE();
        HungryManMode hungryManMode2 = HungryManMode.getINSTANCE();
        log.info("内存地址是否相同：{}", hungryManMode2.equals(hungryManMode1));
    }
}

/**
 * 懒汉模式<br>
 * <b>延迟初始化，只有在第一次被请求时才初始化</b><br>
 * 优点：节省内存，避免频繁创建实例<br>
 * 缺点：线程不安全，多线程访问时，可能会创建多个实例<br>
 * 这里使用双重检，定来实现线程安全
 *
 * @author 李泽聿
 * @since 2026-01-02 20:26
 */
@Slf4j
class LazyManMode {
    private static volatile LazyManMode instance = null;

    private LazyManMode() {
    }

    public static LazyManMode getInstance() {
        if (instance == null) {
            synchronized (LazyManMode.class) {
                if (instance == null) {
                    instance = new LazyManMode();
                }
            }
        }
        log.info("已经创建过实例，直接返回");
        return instance;
    }
}

/**
 * 饿汉模式<br>
 * <b>在类加载时就完成初始化</b><br>
 *
 * @author 李泽聿
 * 优点：线程安全，在类加载时就完成初始化，避免多线程访问时创建多个实例<br>
 * 缺点：浪费内存，如果没有用到，则浪费内存<br>
 * @since 2026-01-02 20:45
 */
class HungryManMode {
    @Getter
    private static final HungryManMode INSTANCE = new HungryManMode();

    private HungryManMode() {
    }

}
