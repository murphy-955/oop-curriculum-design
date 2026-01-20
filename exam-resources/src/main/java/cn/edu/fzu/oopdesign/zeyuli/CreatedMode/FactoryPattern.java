package cn.edu.fzu.oopdesign.zeyuli.CreatedMode;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AssignableTypeFilter;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 简单工厂模式。<br>
 * 简单工厂模式是由一个工厂类负责创建所有实例，并隐藏了实例的创建逻辑，客户端只需要知道传入的参数即可获取到所需的实例。
 *
 * @author : 李泽聿
 * @since : 2025:12:31 19:22
 */
public class FactoryPattern {
    static void main() {
        // 简单工厂模式
        Animal dog = SimpleFactoryPattern.AnimalFactory.createAnimal("dog");
        dog.sayHello();
        Animal cat = SimpleFactoryPattern.AnimalFactory.createAnimal("cat");
        cat.sayHello();

        // 工厂方法模式
        FactoryMethodPattern.AnimalFactory factory = new FactoryMethodPattern.DogFactory();
        Animal dog2 = factory.createAnimal();
        dog2.sayHello();

        // 基于反射的工厂模式
        Factory factory1;
        try {
            factory1 = new Factory();
        } catch (NoSuchMethodException | InvocationTargetException | ClassNotFoundException | InstantiationException |
                 IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        Animal dog3 = factory1.createAnimal("Dog");
        dog3.sayHello();
    }
}

@Slf4j
@NoArgsConstructor
class Dog implements Animal {
    @Override
    public void sayHello() {
        log.info("村里面的狗叫了");
    }
}

@Slf4j
@NoArgsConstructor
class Cat implements Animal {
    @Override
    public void sayHello() {
        log.info("喵(⊙o⊙)？");
    }
}

interface Animal {
    void sayHello();
}

/**
 * 工厂方法模式
 *
 * @author 李泽聿
 * @since 2026-01-01 20:32
 */
class FactoryMethodPattern {
    interface AnimalFactory {
        Animal createAnimal();
    }

    static class DogFactory implements AnimalFactory {
        @Override
        public Animal createAnimal() {
            return new Dog();
        }
    }

    static class CatFactory implements AnimalFactory {
        @Override
        public Animal createAnimal() {
            return new Cat();
        }
    }
}


/**
 * @author 李泽聿
 * @since : 2026:1:1 20:31
 */
@Slf4j
class SimpleFactoryPattern {
    static class AnimalFactory {
        public static Animal createAnimal(String animalType) {
            if ("dog".equals(animalType)) {
                return new Dog();
            }
            if ("cat".equals(animalType)) {
                return new Cat();
            }
            throw new IllegalArgumentException("没有这个动物");
        }
    }
}

/**
 * 可以通过jdk的反射机制来优化工厂模式，使得符合开闭原则。
 *
 * @author : 李泽聿
 * @since : 2026:1:1 20:41
 */
@Slf4j
class Factory {
    public static ConcurrentHashMap<String, Animal> animalMap = new ConcurrentHashMap<>();

    public Factory() throws NoSuchMethodException, InvocationTargetException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        // 扫描Animal接口的所有实现类，包含没有加@Component注解的类
        ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);
        // 获取所有实现类
        scanner.addIncludeFilter(new AssignableTypeFilter(Animal.class));
        for (BeanDefinition bean : scanner.findCandidateComponents("cn.edu.fzu.oopdesign.zeyuli")) {
            String fullClassName = Objects.requireNonNull(bean.getBeanClassName());
            Class<? extends Animal> beanClass = Class.forName(fullClassName).asSubclass(Animal.class);
            Constructor<? extends Animal> constructor = beanClass.getConstructor();
            // 默认以类名作为key 存入map
            String[] split = fullClassName.split("\\.");
            String className = split[split.length - 1];
            animalMap.put(className, constructor.newInstance());
        }
    }

    public Animal createAnimal(String animalType) {
        Animal animal = animalMap.get(animalType);
        if (!animalMap.containsKey(animalType)) {
            throw new IllegalArgumentException("没有这个动物");
        }
        return animal;
    }
}