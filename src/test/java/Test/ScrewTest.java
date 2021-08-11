package Test;

import cn.smallbun.screw.core.Configuration;
import cn.smallbun.screw.core.engine.EngineConfig;
import cn.smallbun.screw.core.engine.EngineFileType;
import cn.smallbun.screw.core.engine.EngineTemplateType;
import cn.smallbun.screw.core.execute.DocumentationExecute;
import cn.smallbun.screw.core.process.ProcessConfig;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Collections;

@SpringBootTest(classes = ScrewTest.class)
class ScrewTest {

    @Test
    void generateDataSourceWord() {
        // 1.获取数据源
        DataSource dataSource = getDataSource();

        // 2.获取数据库文档生成配置（文件路径、文件类型）
        EngineConfig engineConfig = getEngineConfig();

        // 3.获取数据库表的处理配置，可忽略
        ProcessConfig processConfig = getProcessConfig();

        // 4.Screw 完整配置
        Configuration config = getScrewConfig(dataSource, engineConfig, processConfig);

        // 5.执行生成数据库文档
        new DocumentationExecute(config).execute();
    }

    private static DataSource getDataSource() {
        // 数据源
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setDriverClassName("com.mysql.jdbc.Driver");
        hikariConfig.setJdbcUrl("jdbc:mysql://127.0.0.1:3306/test");
        hikariConfig.setUsername("root");
        hikariConfig.setPassword("chimanloo1004");
        // 设置可以获取tables remarks信息
        hikariConfig.addDataSourceProperty("useInformationSchema", "true");
        hikariConfig.setMinimumIdle(2);
        hikariConfig.setMaximumPoolSize(5);
        return new HikariDataSource(hikariConfig);
    }

    private static EngineConfig getEngineConfig() {
        // 生成配置
        return EngineConfig.builder()
                // 生成文件路径
                .fileOutputDir("/Users/mac/IdeaProjects/CommonDemo/doc")
                // 打开目录
                .openOutputDir(true)
                // 文件类型
                .fileType(EngineFileType.HTML)
                // 生成模板实现
                .produceType(EngineTemplateType.freemarker)
                // 自定义文件名称
                .fileName("数据库结构文档").build();
    }

    private static ProcessConfig getProcessConfig() {
        return ProcessConfig.builder().build();
    }

    private static ProcessConfig getProcessConfig1() {
        return ProcessConfig.builder()
                // 指定只生成 blog 表
                .designatedTableName(new ArrayList<>(Collections.singletonList("blog")))
                .build();
    }

    private static ProcessConfig getProcessConfig2() {
        ArrayList<String> ignoreTableName = new ArrayList<>();
        // 忽略表名
        ignoreTableName.add("test_user");
        ignoreTableName.add("test_group");

        // 忽略表前缀
        ArrayList<String> ignorePrefix = new ArrayList<>();
        ignorePrefix.add("test_");

        // 忽略表后缀
        ArrayList<String> ignoreSuffix = new ArrayList<>();
        ignoreSuffix.add("_test");

        return ProcessConfig.builder()
                .ignoreTableName(ignoreTableName)
                .ignoreTablePrefix(ignorePrefix)
                .ignoreTableSuffix(ignoreSuffix)
                .build();
    }

    private static Configuration getScrewConfig(DataSource dataSource, EngineConfig engineConfig, ProcessConfig processConfig) {
        return Configuration.builder()
                // 版本
                .version("1.0.0")
                // 描述
                .description("数据库设计文档生成")
                // 数据源
                .dataSource(dataSource)
                // 生成配置
                .engineConfig(engineConfig)
                // 生成配置
                .produceConfig(processConfig)
                .build();
    }
}
