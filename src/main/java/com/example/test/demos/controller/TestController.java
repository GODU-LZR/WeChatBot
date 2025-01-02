package com.example.test.demos.controller;



import com.example.test.demos.servicer.impl.TextChatServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
@Tag(name = "AI 测试接口", description = "测试 AI 对话功能")
public class TestController {

    @Autowired
    private TextChatServiceImpl chatService;

    @Operation(summary = "测试 AI 对话", description = "通过 GET 请求测试 AI 对话功能")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "成功获取 AI 回复"),
            @ApiResponse(responseCode = "400", description = "参数错误"),
            @ApiResponse(responseCode = "500", description = "服务器内部错误")
    })
    @GetMapping("/chat")
    public String testChat(
            @Parameter(description = "用户名") @RequestParam String username,
            @Parameter(description = "输入内容") @RequestParam String content
    ) {
        try {
           // return textAiService.generateAIResponse(username, content);
            return chatService.generateTextAIResponse(username,content);
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
}

