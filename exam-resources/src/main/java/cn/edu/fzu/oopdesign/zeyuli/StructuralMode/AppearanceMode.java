package cn.edu.fzu.oopdesign.zeyuli.StructuralMode;

import lombok.extern.slf4j.Slf4j;

/**
 * 外观模式
 *
 * @author : 李泽聿
 * @since : 2026:01:20 09:12
 */
public class AppearanceMode {
    public static void main() {
        LogAppearance logAppearance = new LogAppearance();

        logAppearance.doMyLog();
        logAppearance.doYourLog();
    }
}

interface Log {
    void log();
}

@Slf4j
class MyLog implements Log {
    @Override
    public void log() {
        log.info("这是我的日志记录");
    }
}

@Slf4j
class YourLog implements Log {
    @Override
    public void log() {
        log.info("这是你的日志记录");
    }
}


class LogAppearance {
    private final MyLog myLog;
    private final YourLog yourLog ;

    public LogAppearance() {
        myLog = new MyLog();
        yourLog = new YourLog();
    }

    public void doMyLog() {
        myLog.log();
    }

    public void doYourLog() {
        yourLog.log();
    }
}