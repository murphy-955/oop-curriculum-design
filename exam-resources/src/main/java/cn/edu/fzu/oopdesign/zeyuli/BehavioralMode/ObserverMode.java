package cn.edu.fzu.oopdesign.zeyuli.BehavioralMode;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * 观察者模式<br>
 * 观察者模式是一种行为型设计模式，它定义了一种一对多的依赖关系，当一个对象的状态发生改变时，其所有依赖者都会收到通知并自动更新。
 *
 * @author : 李泽聿
 * @since : 2026:01:08 11:08
 */
public class ObserverMode {
    public static void main() {
        Subject stockSubject = new StockSubject();
        Alibaba alibaba1 = new Alibaba();
        alibaba1.setName("user1");
        Alibaba alibaba2 = new Alibaba();
        alibaba2.setName("user2");

        stockSubject.registerObserver(alibaba1);
        stockSubject.registerObserver(alibaba2);
        stockSubject.notifyObservers();
    }
}

interface Observer {
    void update(String message);
}

@Slf4j
@Setter
class Alibaba implements Observer {
    private String name = "user1";

    @Override
    public void update(String message) {
        log.info("用户{}, 收到阿里股票收到通知：{}", name, message);
    }
}

interface Subject {
    // 注册观察者
    void registerObserver(Observer observer);

    // 通知观察者
    void notifyObservers();
}

@Slf4j
@Getter
@Setter
class StockSubject implements Subject {
    private List<Observer> observers = new ArrayList<>();

    @Override
    public void registerObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update("股价大涨");
        }
    }
}