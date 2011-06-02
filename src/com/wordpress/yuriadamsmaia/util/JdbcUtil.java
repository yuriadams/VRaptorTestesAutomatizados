package com.wordpress.yuriadamsmaia.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JdbcUtil {
	private Connection conexao = null;
	private String urlBanco = null;
	private String userName = null;
	private String userPassword = null;
	private String jdbcDriver = null;

	private static String URL_BANCO_MYSQL = "jdbc:mysql://127.0.0.1/test";
	private static String DRIVER_MYSQL = "com.mysql.jdbc.Driver";

	/**
	 * Construtor da classe de conexão.
	 * 
	 */
	public JdbcUtil() {
		super();
		userName = "root";
		userPassword = "root";
		urlBanco = URL_BANCO_MYSQL;
		jdbcDriver = DRIVER_MYSQL;
	}

	public Connection getConnection() {
		try {
			if (conexao == null) {
				Class.forName(jdbcDriver);
				conexao = DriverManager.getConnection(urlBanco, userName,
						userPassword);
			} else if (conexao.isClosed()) {
				conexao = null;
				return getConnection();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conexao;
	}

	public void closeConnection() {
		if (conexao != null) {
			try {
				conexao.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
