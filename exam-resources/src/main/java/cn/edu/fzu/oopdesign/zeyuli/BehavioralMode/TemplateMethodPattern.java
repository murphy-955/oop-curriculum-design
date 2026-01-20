package cn.edu.fzu.oopdesign.zeyuli.BehavioralMode;

/**
 * 模板方法模式<br>
 * 通过模板方法，子类可以在不改变算法结构的情况下重新定义算法的某些步骤
 *
 * @author : 李泽聿
 * @since : 2026:01:08 09:35
 */
public class TemplateMethodPattern {
    public static void main() {
        Animal dog = new Dog();
        dog.play();

        Animal cat = new Cat();
        cat.play();
    }
}


abstract class Animal {
    abstract void eat();

    abstract void sleep();

    // 这是模板方法
    public final void play() {
        eat();
        sleep();
    }
}

class Dog extends Animal {
    @Override
    void eat() {
        System.out.println("狗狗吃骨头");
    }

    @Override
    void sleep() {
        System.out.println("狗狗睡觉");
    }
}

class Cat extends Animal {
    @Override
    void eat() {
        System.out.println("猫吃鱼");
    }

    @Override
    void sleep() {
        System.out.println("猫睡觉");
    }
}
