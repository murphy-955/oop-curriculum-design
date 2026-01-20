package cn.edu.fzu.oopdesign.zeyuli.BehavioralMode;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * 状态模式<br>
 * 状态模式是一种行为型设计模式，它允许一个对象在其内部状态改变时改变它的行为，对象看起来似乎修改了它的类。
 *
 * @author : 李泽聿
 * @since : 2026:01:08 10:52
 */
@Slf4j
public class StatusMode {
    public static void main() {
        StatusModeContext context = new StatusModeContext();
        context.setState(StateEnum.A);
        StateA stateA = new StateA();
        StateB stateB = new StateB();
        StateC stateC = new StateC();

        stateA.doAction(context);
        log.info("当前状态为：{}", context.getState());
        stateB.doAction(context);
        log.info("当前状态为：{}", context.getState());
        stateC.doAction(context);
        log.info("当前状态为：{}", context.getState());
    }
}

@Setter
@Getter
class StatusModeContext {
    private StateEnum state;
}

@Getter
@AllArgsConstructor
enum StateEnum {
    A("状态A"),
    B("状态B"),
    C("状态C");
    private final String description;
}

interface State {
    void doAction(StatusModeContext context);
}

@Slf4j
class StateA implements State {

    @Override
    public void doAction(StatusModeContext context) {
        log.info("当前状态为：{}，正在执行状态A的动作", context.getState());
        context.setState(StateEnum.A);
    }
}

@Slf4j
class StateB implements State {

    @Override
    public void doAction(StatusModeContext context) {
        log.info("当前状态为：{}，正在执行状态B的动作", context.getState());
        context.setState(StateEnum.B);
    }
}

@Slf4j
class StateC implements State {

    @Override
    public void doAction(StatusModeContext context) {
        log.info("当前状态为：{}，正在执行状态C的动作", context.getState());
        context.setState(StateEnum.C);
    }
}
