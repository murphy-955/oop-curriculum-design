package cn.edu.fzu.oopdesign.zeyuli.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 *
 *
 * @author : 李泽聿
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GameEvent {
    private String team;
    private List<String> members;
    private String action;
    private int score;
    private String time;
}