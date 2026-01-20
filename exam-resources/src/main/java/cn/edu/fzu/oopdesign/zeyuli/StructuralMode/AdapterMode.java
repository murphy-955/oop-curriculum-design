package cn.edu.fzu.oopdesign.zeyuli.StructuralMode;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 适配器模式
 *
 * @author : 李泽聿
 * @since : 2026:01:19 17:33
 */
public class AdapterMode {
    public static void main() {
        Adaptee javaDev = new DevByJava();
        Adaptee pythonDev = new DevByPython();
        Target target1 = new Adapter(javaDev);
        target1.developer();
        Target target2 = new Adapter(pythonDev);
        target2.developer();

    }
}

interface Target {
    void developer();
}

interface Adaptee {
    void devWebsite();
}

@Slf4j
class DevByJava implements Adaptee {
    @Override
    public void devWebsite() {
        log.info("使用Java 开发");
    }
}

@Slf4j
class DevByPython implements Adaptee {
    @Override
    public void devWebsite() {
        log.info("使用Python 开发");
    }
}

@AllArgsConstructor
class Adapter implements Target {
    private Adaptee adaptee;

    @Override
    public void developer() {
        adaptee.devWebsite();
    }
}
