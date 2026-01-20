package cn.edu.fzu.oopdesign.zeyuli.CreatedMode;

import lombok.Setter;

/**
 * 原型模式
 *
 * @author : 李泽聿
 * @since : 2026:01:08 09:16
 */
public class PrototypeMode {
    static void main() {
        Document content = new Document("content");
        Document clone;
        try {
            clone = (Document) content.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
        System.out.println(clone.content);
    }
}

@Setter
class Document implements Cloneable{
    final String content;

    public Document(String content) {
        this.content = "content";
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}