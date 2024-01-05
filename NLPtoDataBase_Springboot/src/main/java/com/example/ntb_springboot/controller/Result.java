package com.example.ntb_springboot.controller;

import java.util.AbstractList;
import java.util.List;
import java.util.Map;

public class Result {
    public List<Map<String, Object>> result=null;

    public String database_to_nlp = "";
    public Result(){
        this.result = new AbstractList<Map<String, Object>>() {
            @Override
            public Map<String, Object> get(int i) {
                return null;
            }

            @Override
            public int size() {
                return 0;
            }
        };
        this.database_to_nlp = "";
    }



}
