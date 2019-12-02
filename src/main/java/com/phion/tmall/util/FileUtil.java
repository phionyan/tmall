package com.phion.tmall.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

import com.phion.tmall.config.ResourceConfigAdapter;

public class FileUtil {
	
	public static String getResourcesPath() {
		String path = System.getProperty("user.dir")+"\\src\\main\\resources\\";
		return path;
	}
	
	/**
	 * 获取项目名
	 * @return
	 */
	public static String getWorkareaPath() {
		URL url = ResourceConfigAdapter.class.getClassLoader().getResource("application.properties");
		File file = new File(url.getFile());
		String path = getProperties(file.getAbsolutePath(),"server.context-path").substring(1);
		System.out.println(path);
		return path;
	}

	/**
	 * 读取配置文件
	 * @param filePath
	 * @param keyWord
	 * @return
	 */
	public static String getProperties(String filePath, String keyWord) {
		Properties prop = new Properties();
		String value = null;
		try {
			// 通过输入缓冲流进行读取配置文件
			InputStream InputStream = new BufferedInputStream(new FileInputStream(new File(filePath)));
			// 加载输入流
			prop.load(InputStream);
			// 根据关键字获取value值
			value = prop.getProperty(keyWord);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return value;
	}
}
