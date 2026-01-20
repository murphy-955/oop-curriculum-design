package cn.edu.fzu.oopdesign.zeyuli.BehavioralMode;

import lombok.extern.slf4j.Slf4j;

/**
 * 访问者模式
 *
 * @author : 李泽聿
 * @since : 2026:01:19 15:28
 */
public class VisitorMode {
    public static void main(String[] args) {
        FzuJob javaDev = new JavaDev();
        FzuJob cppDev = new CppDev();

        FzuLeader fzuLeader = new FzuLeader();
        javaDev.accept(fzuLeader);
        cppDev.accept(fzuLeader);
    }
}

interface FzuJob {
    void accept(Visitor visitor);
}

@Slf4j
class JavaDev implements FzuJob {
    @Override
    public void accept(Visitor visitor) {
        // 双分派：this会传递给对应的visit方法
        visitor.visit(this);
    }

    public void codeInJava() {
        log.info("java 开发人员正在写代码");
    }
}

@Slf4j
class CppDev implements FzuJob {
    @Override
    public void accept(Visitor visitor) {
        // 双分派：this会传递给对应的visit方法
        visitor.visit(this);
    }

    public void codeInCpp() {
        log.info("C++ 开发人员正在写代码");
    }
}

interface Visitor {
    void visit(JavaDev javaDev);

    void visit(CppDev cppDev);
}

@Slf4j
class FzuLeader implements Visitor {
    @Override
    public void visit(JavaDev javaDev) {
        // 访问者对特定元素的操作
        javaDev.codeInJava();
        log.info("Fzu Leader 访问了 JavaDev");
    }

    @Override
    public void visit(CppDev cppDev) {
        // 访问者对特定元素的操作
        cppDev.codeInCpp();
        log.info("Fzu Leader 访问了 CppDev");
    }
}
