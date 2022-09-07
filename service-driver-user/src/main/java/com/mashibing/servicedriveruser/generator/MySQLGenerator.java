package com.mashibing.servicedriveruser.generator;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.Collections;

/**
 * 自动生成代码工具类
 * @author xcy
 * @date 2022/9/7 - 8:38
 */
public class MySQLGenerator {
	public static void main(String[] args) {
		String url = "jdbc:mysql://localhost:3306/service-driver-user?characterEncoding=utf-8&serverTimezone=GMT%2B8";
		String username = "root";
		String password = "root";
		FastAutoGenerator.create(url, username, password)
				//全局配置
				.globalConfig(builder -> {
					builder.author("xcy").fileOverride().outputDir("G:\\online-taxi-pubilc\\service-driver-user\\src\\main\\java");
				})
				.packageConfig(builder -> {
					builder.parent("com.mashibing.servicedriveruser").pathInfo(Collections.singletonMap(OutputFile.mapperXml,
							"G:\\online-taxi-pubilc\\service-driver-user\\src\\main\\java\\com\\mashibing\\servicedriveruser\\mapper"));
				})
				//生成策略
				.strategyConfig(builder -> {
					builder.addInclude("car");
				})
				//模板引擎
				.templateEngine(new FreemarkerTemplateEngine())
				.execute();
	}
}
