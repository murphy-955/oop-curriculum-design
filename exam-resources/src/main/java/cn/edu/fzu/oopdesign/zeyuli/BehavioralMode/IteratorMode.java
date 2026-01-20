package cn.edu.fzu.oopdesign.zeyuli.BehavioralMode;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 迭代器模式
 *
 * @author : 李泽聿
 * @since : 2026:01:19 15:02
 */
@Slf4j
public class IteratorMode {
    public static void main() {
        ConcreteAggregation<Integer> aggregation = new ConcreteAggregation<>();
        aggregation.add(1);
        aggregation.add(2);
        aggregation.add(3);

        Iterator<Integer> iterator = aggregation.iterator();

        while (iterator.hasNext()) {
            log.info("{}", iterator.next());
        }
    }
}

interface Aggregation {
}

class ConcreteAggregation<t> implements Aggregation {
    private final List<t> list = new ArrayList<>();

    public void add(t t) {
        list.add(t);
    }

    public Iterator<t> iterator() {
        return new MyIteratorImpl<>(list);
    }
}

class MyIteratorImpl<t> implements Iterator<t> {
    private final List<t> list;

    private int index = 0;

    public MyIteratorImpl(List<t> list) {
        this.list = list;
    }

    @Override
    public t next() {
        if (hasNext()) {
            return list.get(index++);
        }
        throw new RuntimeException("没有下一个元素");
    }

    @Override
    public boolean hasNext() {
        return index < list.size();
    }
}