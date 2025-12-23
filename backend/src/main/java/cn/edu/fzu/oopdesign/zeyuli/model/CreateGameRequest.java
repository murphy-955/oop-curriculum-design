package cn.edu.fzu.oopdesign.zeyuli.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateGameRequest {
    private String title;
    private String description;
    private String start_time;
    private Team master;
    private Team guest;
}