package cn.edu.fzu.oopdesign.zeyuli.StructuralMode;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

/**
 * 装饰器模式
 *
 * @author : 李泽聿
 * @since : 2026:01:20 09:01
 */
@Slf4j
public class DecoratorMode {
    public static void main() {
        Beverage coffee = new Coffee();
        Beverage milk = new Milk(coffee);
        Beverage sugar = new Sugar(milk);
        log.info(sugar.getDescription());
        log.info("价格：{}", sugar.cost());
    }
}


abstract class Beverage {
    public abstract String getDescription();

    public abstract double cost();
}

class Coffee extends Beverage {
    @Override
    public String getDescription() {
        return "咖啡";
    }

    @Override
    public double cost() {
        return 5;
    }
}
@AllArgsConstructor
class Milk extends Beverage{
    private Beverage beverage;

    @Override
    public String getDescription() {
        return "咖啡加牛奶";
    }

    @Override
    public double cost() {
        return beverage.cost() + 1;
    }
}

@AllArgsConstructor
class Sugar extends Beverage{
    private Beverage beverage;

    @Override
    public String getDescription() {
        return "咖啡加糖";
    }

    @Override
    public double cost() {
        return beverage.cost() + 0.5;
    }
}