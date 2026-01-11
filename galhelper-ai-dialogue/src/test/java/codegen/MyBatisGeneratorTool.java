package codegen;
/**
 * MyBatis代码生成器 根据数据库表一键生成所有代码  并自带两个接口
 */

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.exception.InvalidConfigurationException;
import org.mybatis.generator.exception.XMLParserException;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 代码生成entity，mapper，xml
 */
public class MyBatisGeneratorTool {
    public static void main(String[] args) {
        URL resource = Thread.currentThread().getContextClassLoader().getResource("");
        System.out.println(resource.getPath());
        List<String> warnings = new ArrayList<String>();
        boolean overwrite = true;
        File configFile = new File(resource.getPath().replaceAll("classes/java/test", "") + "generator/generatorConfig.xml");
        ConfigurationParser cp = new ConfigurationParser(warnings);
        Configuration config = null;
        try {
            config = cp.parseConfiguration(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XMLParserException e) {
            e.printStackTrace();
        }
        DefaultShellCallback callback = new DefaultShellCallback(overwrite);
        MyBatisGenerator myBatisGenerator = null;
        try {
            myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
        }
        try {
            myBatisGenerator.generate(null);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (String warning : warnings) {
            System.err.println("MBG Warning: " + warning);
        }
    }
}