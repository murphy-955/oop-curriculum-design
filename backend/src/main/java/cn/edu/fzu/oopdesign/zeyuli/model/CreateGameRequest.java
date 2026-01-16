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
public class CreateGameRequest {
    private String title;
    private String description;
    private String startTime;
    private Team master;
    private Team guest;
}