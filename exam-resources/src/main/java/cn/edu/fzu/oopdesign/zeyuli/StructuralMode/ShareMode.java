package cn.edu.fzu.oopdesign.zeyuli.StructuralMode;

import lombok.Setter;

import java.util.HashMap;

/**
 * 享元模式
 *
 * @author : 李泽聿
 * @since : 2026:01:20 09:33
 */
public class ShareMode {
    private static final String[] COLORS = {"Red", "Green", "Blue", "White", "Black"};

    public static void main() {
        for (int i = 0; i < 5; i++) {
            Circle circle = (Circle) ShapeFactory.getCircle(COLORS[i % COLORS.length]);
            circle.setX(getRandomX());
            circle.setY(getRandomY());
            circle.setRadius(100);
            circle.draw();
        }
    }

    private static int getRandomX() {
        return (int) (Math.random() * 100);
    }

    private static int getRandomY() {
        return (int) (Math.random() * 100);
    }
}

interface Shape {
    void draw();
}

class Circle implements Shape {
    private final String color;
    @Setter
    private int x;
    @Setter
    private int y;
    @Setter
    private int radius;

    public Circle(String color) {
        this.color = color;
    }

    @Override
    public void draw() {
        System.out.println("Circle: Draw() [Color : " + color + ", x : " + x + ", y : " + y + ", radius :" + radius + "]");
    }
}

class ShapeFactory {
    private static final HashMap<String, Shape> CIRCLE_MAP = new HashMap<>();

    public static Shape getCircle(String color) {
        Circle circle = (Circle) CIRCLE_MAP.get(color);

        if (circle == null) {
            circle = new Circle(color);
            CIRCLE_MAP.put(color, circle);
            System.out.println("Creating circle of color : " + color);
        }
        return circle;
    }
}
