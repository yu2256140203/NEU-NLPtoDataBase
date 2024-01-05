package com.example.ntb_springboot.config;

import com.alibaba.dashscope.aigc.generation.Generation;
import com.alibaba.dashscope.aigc.generation.GenerationOutput;
import com.alibaba.dashscope.aigc.generation.GenerationResult;
import com.alibaba.dashscope.aigc.generation.models.QwenParam;
import com.alibaba.dashscope.common.Message;
import com.alibaba.dashscope.exception.ApiException;
import com.alibaba.dashscope.exception.InputRequiredException;
import com.alibaba.dashscope.exception.NoApiKeyException;
import com.alibaba.dashscope.utils.Constants;

import java.util.List;


public class qwenConfig {

    public static String prompt="";

    public static String  callWithMessage(String question) throws NoApiKeyException, ApiException, InputRequiredException {
        Generation gen = new Generation();
        String content = null;
        QwenParam params = QwenParam.builder().model("qwen-max")
                .prompt(prompt+question)
                .seed(1234)
                .topP(0.8)
                .resultFormat("message")
                .enableSearch(false)
                .maxTokens(1500)
                .temperature((float)1.0)
                .repetitionPenalty((float)1.0)
                .build();

        GenerationResult result = gen.call(params);

        GenerationOutput output = result.getOutput (); //获取output对象
        List<GenerationOutput.Choice> choices = output.getChoices (); //获取choices列表
        if (choices != null && !choices.isEmpty ()) { //检查choices是否为空或空列表
            GenerationOutput.Choice choice = choices.get (0);
            Message message = choice.getMessage (); //获取message对象
            if (message != null) { //检查message是否为空
                content = message.getContent ();
                if (content != null) {
                    System.out.println (content); //打印content字符串
                }
            }
        }

        System.out.println(result);
        return content;
    }

    public static String question(String quetion){
        Constants.apiKey="sk-xxxxxxxxxxxxxxxxxxx";
        prompt = "当前我的数据库表student的结构是：id,name,age和class，我希望你能为我生成一句sql语句，用来（除了sql语句之外，严禁回复我其他的任何消息，我只要sql格式的语句）";
        String result = null;
        try {
            result = callWithMessage(quetion);

        } catch (ApiException | NoApiKeyException | InputRequiredException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    public static String database_to_nlp(String databaseResult){
        Constants.apiKey="sk-a6d7ccf346f849d9822b5e2d2361484a";
        prompt = "当前我的数据库表student的结构是：id,name,age和class，下面我会给你一段字符串，这段字符串是数据库返回给我的查询结果，我希望你能为我针对下面的这段字符串，将它转换成自然语言，像朋友一样为我们解释出来这段话的信息，但是不要有出现回复我这个问题的字样，所生成的答案只和字符串有关（请直接解释这段字符串，严禁说其他的话”）：";
        String result = null;
        try {
            result = callWithMessage(databaseResult);
        } catch (ApiException | NoApiKeyException | InputRequiredException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }


}