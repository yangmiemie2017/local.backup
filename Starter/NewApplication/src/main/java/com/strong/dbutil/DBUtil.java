package com.strong.dbutil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.BeanProcessor;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayHandler;
import org.apache.commons.dbutils.handlers.ArrayListHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ColumnListHandler;
import org.apache.commons.dbutils.handlers.KeyedHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.strong.common.Data;

/**
 * 利用DBUtils对JDBC一次完整的封装
 * @author YangJie
 */
public class DBUtil {
	private static final QueryRunner runner = new QueryRunner();
    private static final String key="table";
    
	// 打开数据库连接（type: MySQL，Oracle，SQLServer）
	public static Connection openConn(String type, String host, String port, String name, String username,
			String password) {
		Connection conn = null;
		try {
			String driver;
			String url;
			if (type.equalsIgnoreCase("MySQL")) {
				driver = "com.mysql.jdbc.Driver";
				url = "jdbc:mysql://" + host + ":" + port + "/" + name;
			} else if (type.equalsIgnoreCase("Oracle")) {
				driver = "oracle.jdbc.driver.OracleDriver";
				url = "jdbc:oracle:thin:@" + host + ":" + port + ":" + name;
			} else if (type.equalsIgnoreCase("SQLServer")) {
				driver = "com.microsoft.jdbc.sqlserver.SQLServerDriver";
				url = "jdbc:sqlserver://" + host + ":" + port + ";databaseName=" + name;
			} else {
				throw new RuntimeException();
			}
			Class.forName(driver);
			conn = DriverManager.getConnection(url, username, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}

	// 关闭数据库连接
	public static void closeConn(Connection conn) {
		try {
			if (conn != null) {
				conn.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 查询（返回Array结果）
	public static Object[] queryArray(Connection conn, String sql, Object... params) {
		Object[] result = null;
		try {
			result = runner.query(conn, sql, new ArrayHandler(), params);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	// 查询（返回ArrayList结果）
	public static List<Object[]> queryArrayList(Connection conn, String sql, Object... params) {
		List<Object[]> result = null;
		try {
			result = runner.query(conn, sql, new ArrayListHandler(), params);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	// 查询（返回Map结果）
	public static Map<String, Object> queryMap(Connection conn, String sql, Object... params) {
		Map<String, Object> result = null;
		try {
			result = runner.query(conn, sql, new MapHandler(), params);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	// 查询（返回MapList结果）
	public static List<Map<String, Object>> queryMapList(Connection conn, String sql, Object... params) {
		List<Map<String, Object>> result = null;
		try {
			result = runner.query(conn, sql, new MapListHandler(), params);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	// 查询（返回Bean结果）
	public static <T> T queryBean(Class<T> cls, Map<String, String> map, Connection conn, String sql, Object... params) {
		T result = null;
		try {
			if (MapUtil.isNotEmpty(map)) {
				result = runner.query(conn, sql,
						new BeanHandler<T>(cls, new BasicRowProcessor(new BeanProcessor(map))), params);
			} else {
				result = runner.query(conn, sql, new BeanHandler<T>(cls), params);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	// 查询（返回BeanList结果）
	public static <T> List<T> queryBeanList(Class<T> cls, Map<String, String> map, Connection conn, String sql,
			Object... params) {
		List<T> result = null;
		try {
			if (MapUtil.isNotEmpty(map)) {
				result = runner.query(conn, sql, new BeanListHandler<T>(cls, new BasicRowProcessor(new BeanProcessor(
						map))), params);
			} else {
				result = runner.query(conn, sql, new BeanListHandler<T>(cls), params);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	// 查询指定列名的值（单条数据）
	public static <T> T queryColumn(String column, Connection conn, String sql, Object... params) {
		T result = null;
		try {
			result = runner.query(conn, sql, new ScalarHandler<T>(column), params);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	// 查询指定列名的值（多条数据）
	public static <T> List<T> queryColumnList(String column, Connection conn, String sql, Object... params) {
		List<T> result = null;
		try {
			result = runner.query(conn, sql, new ColumnListHandler<T>(column), params);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	// 查询指定列名对应的记录映射
	public static <T> Map<T, Map<String, Object>> queryKeyMap(String column, Connection conn, String sql,
			Object... params) {
		Map<T, Map<String, Object>> result = null;
		try {
			result = runner.query(conn, sql, new KeyedHandler<T>(column), params);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	// 更新（包括UPDATE、INSERT、DELETE，返回受影响的行数）
	public static int update(Connection conn, String sql, Object... params) {
		int result = 0;
		try {
			result = runner.update(conn, sql, params);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public static Data execute(Connection conn, String sql, Object... params) 
			throws SQLException {
		Data data=null;

		if (conn == null) {
			throw new SQLException("Null connection");
		}

		if (sql == null) {
			throw new SQLException("Null SQL statement");
		}

		PreparedStatement stmt = null;

		try {
			stmt = conn.prepareStatement(sql);
			runner.fillStatement(stmt, params);
			boolean isResultSet = stmt.execute();
			if(isResultSet){
				data=new Data();
			}
			
			ResultSet rs = null;
		    int count = 0;

		    while(true) {
		        if(isResultSet) {
		            rs = stmt.getResultSet();
		            List<LinkedHashMap<String, Object>> rowList=new ArrayList<LinkedHashMap<String, Object>>();
		            while(rs.next()) {
		            	rowList.add(toMap(rs));
		            }
		            data.data.put(getKey(count), rowList);
		            rs.close();
		        } else {
		            if(stmt.getUpdateCount() == -1) {
		                break;
		            }
		            System.out.printf("Result {} is just a count: {}", count, stmt.getUpdateCount());
		        } 

		        count ++;
		        isResultSet = stmt.getMoreResults();
		    }


		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return data;
	}

	private static String getKey(int count) {
		if (count == 0) {
			return key;
		}
		return key + count;
	}
	
    public static LinkedHashMap<String, Object> toMap(ResultSet rs) throws SQLException {
    	LinkedHashMap<String, Object> result = new LinkedHashMap<String, Object>();
        ResultSetMetaData rsmd = rs.getMetaData();
        int cols = rsmd.getColumnCount();

        for (int i = 1; i <= cols; i++) {
            String columnName = rsmd.getColumnLabel(i);
            if (null == columnName || 0 == columnName.length()) {
              columnName = rsmd.getColumnName(i);
            }
            columnName = columnName.toLowerCase();
            result.put(columnName, rs.getObject(i));
        }

        return result;
    }	
    

}