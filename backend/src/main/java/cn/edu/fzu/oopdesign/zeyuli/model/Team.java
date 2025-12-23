package cn.edu.fzu.oopdesign.zeyuli.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Team {
    private String name;
    private String logo;
    private List<Member> members;
}