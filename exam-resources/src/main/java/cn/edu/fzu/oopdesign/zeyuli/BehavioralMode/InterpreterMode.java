package cn.edu.fzu.oopdesign.zeyuli.BehavioralMode;

import lombok.extern.slf4j.Slf4j;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 解释器模式
 *
 * @author : 李泽聿
 * @since : 2026:01:19 16:30
 */
@Slf4j
public class InterpreterMode {
    public static void main() {
        Expression javaExpression = new JavaExpression();
        javaExpression.interpret("System.out.println(\"Hello World\")");
        try {
            javaExpression.interpret("Systems.out.println(\"Hello World\")");
        } catch (Exception e) {
            log.error("{}", e.getMessage());
        }
        Expression pythonExpression = new PythonExpression();
        pythonExpression.interpret("print(\"Hello World\")");
    }
}

interface Expression {
    String interpret(String context);
}

@Slf4j
class JavaExpression implements Expression {
    private static final String RULE = "^System\\.out\\.println\\((.*)\\)$";

    private static final Pattern PATTERN = Pattern.compile(RULE);

    @Override
    public String interpret(String context) {
        Matcher matcher = PATTERN.matcher(context);
        if (matcher.find()){
            log.info("JavaExpression: {}", matcher.group(1));
            return "JavaExpression: " + context;
        }
        throw new IllegalArgumentException("Invalid expression: " + context);
    }
}

@Slf4j
class PythonExpression implements Expression {
   private static final String RULE = "^print\\((.*)\\)$";
   private static final Pattern PATTERN = Pattern.compile(RULE);

   @Override
    public String interpret(String context) {
        Matcher matcher = PATTERN.matcher(context);
        if (matcher.find()){
            log.info("PythonExpression: {}", matcher.group(1));
            return "PythonExpression: " + context;
        }
        throw new IllegalArgumentException("Invalid expression: " + context);
    }
}
