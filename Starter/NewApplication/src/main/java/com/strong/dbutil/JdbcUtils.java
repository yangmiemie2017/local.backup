package com.strong.dbutil;

import java.io.IOException;
import java.io.InputStream;  
import java.util.Properties;  

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;


import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * @ClassName: JdbcUtils
 * @Description: 数据库连接工具类
 * @author: Jerrik
 * @date: 2016-06-01 下午4:04:36
 */
public class JdbcUtils {
	private static JdbcUtils jdbcUtils;  
	private static ComboPooledDataSource ds = null;
	private static Properties p;  
	
	// 使用ThreadLocal存储当前线程中的Connection对象
	private static ThreadLocal<Connection> threadLocal = new ThreadLocal<Connection>();

	// 在静态代码块中创建数据库连接池
	static {
		InputStream is=Thread.currentThread().getContextClassLoader().getResourceAsStream("application.properties");
		p=new Properties();
		try {
			p.load(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			// 通过代码创建C3P0数据库连接池
			ds = new ComboPooledDataSource();
			ds.setDriverClass(p.getProperty("spring.datasource.driver-class-name"));
			ds.setJdbcUrl(p.getProperty("spring.datasource.url"));
			ds.setUser(p.getProperty("spring.datasource.username"));
			ds.setPassword(p.getProperty("spring.datasource.password"));
			ds.setInitialPoolSize(Integer.parseInt(p.getProperty("c3p0.pool.initialPoolSize")));
			ds.setMinPoolSize(Integer.parseInt(p.getProperty("c3p0.pool.minPoolSize")));
			ds.setMaxPoolSize(Integer.parseInt(p.getProperty("c3p0.pool.maxPoolSize")));
		} catch (Exception e) {
			throw new ExceptionInInitializerError(e);
		}
	}

	private JdbcUtils(){
		
	}
	public static JdbcUtils getJdbcUtils(){
		
		if(jdbcUtils==null){
			jdbcUtils=new JdbcUtils();
		}
		return jdbcUtils;
	} 	
	
	/**
	 * @Method: getConnection
	 * @Description: 从数据源中获取数据库连接
	 * @author: Jerrik
	 * @return Connection
	 * @throws SQLException
	 */
	public static Connection getConnection() throws SQLException {
		// 从当前线程中获取Connection
		Connection conn = threadLocal.get();
		if (conn == null) {
			conn = getDataSource().getConnection();
			threadLocal.set(conn);
		}
		return conn;
	}

	/**
	 * @Method: startTransaction
	 * @Description: 开启事务
	 * @author: Jerrik
	 *
	 */
	public static void startTransaction() {
		try {
			Connection conn = threadLocal.get();
			if (conn == null) {
				conn = getConnection();
				threadLocal.set(conn);
			}
			// 开启事务
			conn.setAutoCommit(false);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * @Method: rollback
	 * @Description:回滚事务
	 * @author: Jerrik
	 *
	 */
	public static void rollback() {
		try {
			// 从当前线程中获取Connection
			Connection conn = threadLocal.get();
			if (conn != null) {
				conn.rollback();
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * @Method: commit
	 * @Description:提交事务
	 * @author: Jerrik
	 *
	 */
	public static void commit() {
		try {
			// 从当前线程中获取Connection
			Connection conn = threadLocal.get();
			if (conn != null) {
				conn.commit();
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * @Method: close
	 * @Description:关闭数据库连接(注意，并不是真的关闭，而是把连接还给数据库连接池)
	 * @author: Jerrik
	 *
	 */
	public static void close() {
		try {
			// 从当前线程中获取Connection
			Connection conn = threadLocal.get();
			if (conn != null) {
				conn.close();
				threadLocal.remove();
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * @Method: getDataSource
	 * @Description: 获取数据源
	 * @author: Jerrik
	 * @return DataSource
	 */
	public static DataSource getDataSource() {
		return ds;
	}
	
	public static void main1(String[] args) throws SQLException {
		Connection conn = getDataSource().getConnection();
		System.out.println(conn);
	}
}