package cn.edu.fzu.oopdesign.zeyuli.CreatedMode;

import lombok.extern.slf4j.Slf4j;

/**
 * 抽象工厂模式<br>
 * <b>提供一个创建一系列相关或相互依赖对象的接口，而无需指定它们的具体类</b>
 *
 * @author : 李泽聿
 * @since : 2026:01:02 20:51
 */
public class AbstractFactoryPattern {
    public static void main() {
        AbstractFactory shapeFactory = FactoryBuilder.build("shape");
        AbstractFactory colorFactory = FactoryBuilder.build("color");

        Shape square = shapeFactory.getShape("square");
        square.draw();

        Color red = colorFactory.getColor("red");
        red.fill();
    }
}

interface Shape {
    void draw();
}

interface Color {
    void fill();
}

@Slf4j
class Square implements Shape {
    @Override
    public void draw() {
        log.info("正方形");
    }
}

@Slf4j
class Red implements Color {
    @Override
    public void fill() {
        log.info("红色");
    }
}

class ShapeFactory  extends AbstractFactory {
    @Override
    public Shape getShape(String shapeType) {
        if ("square".equals(shapeType)) {
            return new Square();
        }
        throw new IllegalArgumentException("不支持的形状类型：" + shapeType);
    }

    @Override
    public Color getColor(String colorType) {
        return null;
    }
}

class ColorFactory extends AbstractFactory {
    @Override
    public Color getColor(String colorType) {
        if ("red".equals(colorType)) {
            return new Red();
        }
        throw new IllegalArgumentException("不支持的颜色类型：" + colorType);
    }

    @Override
    public Shape getShape(String shapeType) {
        return null;
    }
}

class FactoryBuilder{
    public static AbstractFactory build(String factoryType) {
        if ("shape".equals(factoryType)) {
            return new ShapeFactory();
        } else if ("color".equals(factoryType)) {
            return new ColorFactory();
        }
        throw new IllegalArgumentException("不支持的工厂类型：" + factoryType);
    }
}