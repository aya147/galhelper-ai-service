package com.galhelper.dialogue;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * ClassName: AiDialogueApplication
 * Package: com.galhelper.dialogue
 * Description:
 *
 * @author aya
 * @date 2026/1/9 - 16:55
 * @project galhelper-ai-service
 */
@MapperScan("com.galhelper.dialogue.mapper")
@Slf4j
@SpringBootApplication
public class AiDialogueApplication {

    private static final Logger LG = LoggerFactory.getLogger(AiDialogueApplication.class);
    public static void main(String[] args) throws UnknownHostException {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(AiDialogueApplication.class, args);
        LG.info("galhelper的AI对话 服务运行中。。。");

        Environment env = applicationContext.getEnvironment();
        String ip = InetAddress.getLocalHost().getHostAddress();
        String port = env.getProperty("server.port");
        String path = env.getProperty("server.servlet.context-path");
        log.info("\n----------------------------------------------------------\n\t" +
                "\t\tAI对话系统 \n\t" +
                "Local: \t\thttp://localhost:" + port + path + "/\n\t" +
                "External: \thttp://" + ip + ":" + port + path + "/\n\t" +
                "Swagger-UI: \thttp://" + ip + ":" + port + path + "/doc.html#/home\n\t" +
                "Swagger-UI: \thttp://localhost:" + port + path + "/doc.html#/home\n" +
                "----------------------------------------------------------");
    }

}
