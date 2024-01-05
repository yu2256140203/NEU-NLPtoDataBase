package com.example.ntb_springboot.controller;// 导入相关的类和注解
import com.example.ntb_springboot.config.qwenConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;



@RestController
public class SqlController {

    // 注入JdbcTemplate对象，用于执行SQL语句
    @Autowired
    private JdbcTemplate jdbcTemplate;
    public static List<Map<String, Object>> result;
    // 定义一个GET方法，接收一个sql参数，返回执行结果
    @GetMapping("/sql")
    public Result executeSql(@RequestParam String order) {
        // 直接使用jdbcTemplate的queryForList方法，传入sql参数，返回结果
        order = qwenConfig.question(order);
        result = jdbcTemplate.queryForList(order);
        Result result1 = new Result();
        result1.result = result;
        System.out.println(result.toString());
        result1.database_to_nlp = qwenConfig.database_to_nlp(result.toString());
        return result1;
    }
}
