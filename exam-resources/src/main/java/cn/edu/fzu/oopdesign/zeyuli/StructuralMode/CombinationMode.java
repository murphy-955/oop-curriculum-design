package cn.edu.fzu.oopdesign.zeyuli.StructuralMode;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * 组合模式
 *
 * @author : 李泽聿
 * @since : 2026:01:20 08:35
 */
public class CombinationMode {
    public static void main() {
        Component root = new GroupComponent("root");
        Component child1 = new LeafComponent("child1");
        Component child2 = new LeafComponent("child2");

        root.add(child1);
        root.add(child2);
        System.out.println(root.getCount());
        System.out.println(root.getChild("child1").getName());
        System.out.println(root.getChild("child2").getName());
    }
}

@AllArgsConstructor
@Getter
abstract class Component {
    private String name;

    public abstract void add(Component component);

    public abstract Component getChild(String name);

    public abstract int getCount();
}

class GroupComponent extends Component {
    private final List<Component> children = new ArrayList<>();

    public GroupComponent(String name) {
        super(name);
    }

    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public void add(Component component) {
        children.add(component);
    }

    @Override
    public Component getChild(String name) {
        for (Component child : children) {
            Component component = child.getChild(name);
            if (component != null) {
                return component;
            }
        }
        throw new NullPointerException("没有这个元素");
    }

    @Override
    public int getCount() {
        int res = 0;
        for (Component child : children) {
            res += child.getCount();
        }
        return res;
    }
}

class LeafComponent extends Component {
    public LeafComponent(String name) {
        super(name);
    }

    @Override
    public int getCount() {
        return 1;
    }

    @Override
    public void add(Component component) {
        throw new UnsupportedOperationException("叶子节点不能添加子节点");
    }

    @Override
    public Component getChild(String name) {
        if (name.equals(this.getName())) {
            return this;
        }
        return null;
    }
}
