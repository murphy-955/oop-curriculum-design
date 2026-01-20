package cn.edu.fzu.oopdesign.zeyuli.BehavioralMode;

import lombok.extern.slf4j.Slf4j;

/**
 * 策略模式<br>
 * 策略模式定义了算法家族，分别封装起来，让它们之间可以相互替换，此模式让算法的变化，不会影响到使用算法的客户。
 *
 * @author : 李泽聿
 * @since : 2026:01:08 09:43
 */
public class StrategyMode {
    public static void main() {
        Context qrCodeContext = new Context(new QrCodeLogin());
        qrCodeContext.login();

        Context wechatContext = new Context(new WechatLogin());
        wechatContext.login();
    }
}

interface Login {
    void login();
}

@Slf4j
class QrCodeLogin implements Login {
    @Override
    public void login() {
        log.info("使用二维码登录");
    }
}

@Slf4j
class WechatLogin implements Login {
    @Override
    public void login() {
        log.info("使用微信登录");
    }
}

class Context {
    private final Login login;

    public Context(Login login) {
        this.login = login;
    }

    public void login() {
        login.login();
    }
}