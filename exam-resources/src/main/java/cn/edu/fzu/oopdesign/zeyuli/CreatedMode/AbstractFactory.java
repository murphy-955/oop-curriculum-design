package cn.edu.fzu.oopdesign.zeyuli.CreatedMode;

/**
 * @author 李泽聿
 * @since 2026-01-02 20:57
 */
public abstract class AbstractFactory {
    public abstract Shape getShape(String shapeType);

    public abstract Color getColor(String colorType);
}
