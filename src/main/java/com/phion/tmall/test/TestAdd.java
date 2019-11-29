package com.phion.tmall.test;

import java.sql.DriverManager;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

public class TestAdd {

	public static void main(String[] args) {
		// 准备分类测试数据：

		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		try (Connection c = (Connection) DriverManager.getConnection(
				"jdbc:mysql://localhost:3306/tmall?useUnicode=true&characterEncoding=utf8", "root", "root");
				Statement s = (Statement) c.createStatement();) {
			for (int i = 1; i <= 10; i++) {
				String sqlFormat = "insert into category values (null, '测试分类%d')";
				String sql = String.format(sqlFormat, i);
				s.execute(sql);
			}

			System.out.println("已经成功创建10条分类测试数据");

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
