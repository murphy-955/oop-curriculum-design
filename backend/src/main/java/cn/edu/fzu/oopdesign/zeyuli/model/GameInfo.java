package cn.edu.fzu.oopdesign.zeyuli.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 *
 * @author : 李泽聿
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GameInfo {
    private String uuid;
    private String title;
    private String description;
    private String startTime;
    private Team master;
    private Team guest;
    private int masterScore;
    private int guestScore;
}