package com.lichi.springbootbase;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.fill.Column;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @Description: mybatis-plus code generator
 * @author: lychee
 * @Date: 2022/12/27 08:50
 * @Version: 1.0
 * @Since: 2022/12/27
 */
public class CodeGenerator {



    public static void main(String[] args) {
        FastAutoGenerator.create("jdbc:mysql://localhost:3306/springbootbase?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=Asia/Shanghai"
                        ,"root","123456")
                // 全局配置
                .globalConfig((scanner, builder) -> builder.author(scanner.apply("请输入作者名称:"))
                        .outputDir(System.getProperty("user.dir") + "/src/main/java"))
                // 包配置
                .packageConfig((scanner, builder) -> builder.parent(scanner.apply("请输入包名:")))
                .packageConfig((scanner,builder) -> builder.moduleName(scanner.apply("请输入模块名:")))
                .packageConfig(builder -> builder.entity("entity").service("service").serviceImpl("service.impl").controller("controller"))
                .packageConfig(builder -> builder.pathInfo(Collections.singletonMap(OutputFile.xml, System.getProperty("user.dir") + "/src/main/resources/mapper")))
                // 策略配置
                .strategyConfig((scanner,builder) -> builder.addTablePrefix(scanner.apply("请输入需要表前缀:")))
                .strategyConfig((scanner, builder) -> builder.addInclude(getTables(scanner.apply("请输入表名，多个英文逗号分隔？所有输入 all")))

                        .controllerBuilder().enableRestStyle().enableHyphenStyle()
                        .entityBuilder().enableLombok().addTableFills(
                                new Column("create_time", FieldFill.INSERT),
                                new Column("update_time", FieldFill.INSERT_UPDATE)
                        )
                        .logicDeleteColumnName("is_deleted")
                        .idType(IdType.AUTO).build())

                /*
                    模板引擎配置，默认 Velocity 可选模板引擎 Beetl 或 Freemarker
                   .templateEngine(new BeetlTemplateEngine())
                   .templateEngine(new FreemarkerTemplateEngine())
                 */

                .execute();
    }

    protected static List<String> getTables(String tables) {
        return "all".equals(tables) ? Collections.emptyList() : Arrays.asList(tables.split(","));
    }
}
