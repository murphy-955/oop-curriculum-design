package cn.edu.fzu.oopdesign.zeyuli.BehavioralMode;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * 命令模式<br>
 * 命令模式是一种行为设计模式，它使你能够将一个请求封装为一个对象，从而使你可用不同的请求对客户进行参数化；对请求排队或记录请求日志，以及支持可撤销的操作。<br>
 * 命令模式也支持可组合的命令，即你可将多个命令组合在一起，使它们可以按照顺序执行。
 *
 * @author : 李泽聿
 * @since : 2026:01:08 09:49
 */
public class CommandMode {
    public static void main() {
        Broker broker = new Broker();
        Stock stockA = new Stock("A股", 10.0);
        SellStock sellStock = new SellStock(stockA);
        BuyStock buyStock = new BuyStock(stockA);

        broker.takeOrder(sellStock);
        broker.takeOrder(buyStock);
        broker.placeOrders();
    }
}

interface Order {
    void execute();
}

@Slf4j
class Stock {
    private final String name;
    private final Double price;

    public Stock(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public void buy() {
        log.info("买入股票{}，价格为{}元", name, price);
    }

    public void sell() {
        log.info("卖出股票{}，价格为{}元", name, price);
    }
}

class BuyStock implements Order {
    private final Stock stock;

    public BuyStock(Stock stock) {
        this.stock = stock;
    }

    @Override
    public void execute() {
        stock.buy();
    }

}

class SellStock implements Order {
    private final Stock stock;

    public SellStock(Stock stock) {
        this.stock = stock;
    }

    @Override
    public void execute() {
        stock.sell();
    }
}

class Broker {
    private List<Order> orderList = new ArrayList<>();

     public void takeOrder(Order order) {
         orderList.add(order);
     }

     public void placeOrders() {
         for (Order order : orderList) {
             order.execute();
         }
     }
}