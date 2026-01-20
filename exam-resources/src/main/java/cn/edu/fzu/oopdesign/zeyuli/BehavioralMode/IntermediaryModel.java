package cn.edu.fzu.oopdesign.zeyuli.BehavioralMode;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * 中介者模式
 *
 * @author : 李泽聿
 * @since : 2026:01:19 17:00
 */
public class IntermediaryModel {
    public static void main() {
        Mediator mediator = new MediatorImpl();
        FriendImpl friend1 = new FriendImpl("张三");
        FriendImpl friend2 = new FriendImpl("李四");
        FriendImpl friend3 = new FriendImpl("王五");

        mediator.register(friend1);
        mediator.register(friend2);
        mediator.register(friend3);

        mediator.send("张三发来的信息", friend1);
        mediator.send("李四发来的信息", friend2);
        mediator.send("王五发来的信息", friend3);
    }
}

interface Mediator {
    void register(Friend friend);

    void send(String message, Friend friend);
}

class MediatorImpl implements Mediator {
    private static final Map<String, Object> FRIEND_MAP = new HashMap<>();

    @Override
    public void register(Friend friend) {
        FRIEND_MAP.put(friend.getClass().getSimpleName(), friend);
    }

    @Override
    public void send(String message, Friend friend) {
        friend.receive(message);
    }
}

interface Friend {
    void receive(String message);
}

@Slf4j
@AllArgsConstructor
class FriendImpl implements Friend {
    private final String name;

    @Override
    public void receive(String message) {
        System.out.println(name + "收到信息：" + message);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
