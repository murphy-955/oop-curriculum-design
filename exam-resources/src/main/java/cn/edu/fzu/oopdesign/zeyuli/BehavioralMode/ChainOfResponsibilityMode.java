package cn.edu.fzu.oopdesign.zeyuli.BehavioralMode;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * 职责链模式<br>
 * 定义一个处理者链，使得每个处理者都有对其下一个处理者的引用，如果一个处理者不能处理该请求，则它会把请求传给下一个处理者，依此类推，直到请求被处理为止。
 *
 * @author : 李泽聿
 * @since : 2026:01:08 10:24
 */
public class ChainOfResponsibilityMode {
    public static void main() {
        InfoHandler infoHandler = new InfoHandler(Handler.INFO);
        WarningHandler warningHandler = new WarningHandler(Handler.WARNING);
        ErrorHandler errorHandler = new ErrorHandler(Handler.ERROR);

        infoHandler.setNext(warningHandler);
        warningHandler.setNext(errorHandler);

        infoHandler.dealWork("这是一条INFO 信息");
        warningHandler.dealWork("这是一条WARNING 信息");
        errorHandler.dealWork("这是一条ERROR 信息");
    }
}

abstract class Handler {
    public static int INFO = 1;
    public static int WARNING = 2;
    public static int ERROR = 3;

    protected int level;

    // 定义下一个处理者
    @Setter
    protected Handler next;

    public void deal(int level, String message) {
        if (this.level <= level) {
            dealWork(message);
        }
        if (next != null) {
            next.deal(level, message);
        }
    }

    abstract protected void dealWork(String message);
}

@Slf4j
class InfoHandler extends Handler {

    public InfoHandler(int level) {
        this.level = level;
    }

    @Override
    protected void dealWork(String message) {
        log.info("INFO 信息: {}", message);
    }
}

@Slf4j
class WarningHandler extends Handler {

    public WarningHandler(int level) {
        this.level = level;
    }

    @Override
    protected void dealWork(String message) {
        log.warn("WARNING 信息: {}", message);
    }
}

@Slf4j
class ErrorHandler extends Handler {

    public ErrorHandler(int level) {
        this.level = level;
    }

    @Override
    protected void dealWork(String message) {
        log.error("ERROR 信息: {}", message);
    }
}