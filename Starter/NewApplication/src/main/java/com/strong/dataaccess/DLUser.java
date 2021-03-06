package com.strong.dataaccess;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.sql.Connection;
import java.sql.PreparedStatement;

import com.strong.dbutil.DBUtil;
import com.strong.dbutil.JdbcUtils;
import com.strong.common.Data;
import com.strong.model.entity.User;

public class DLUser {

	public static User getUser(String userName,String password) throws SQLException {
		String sql ="select * from `user` u inner join user_role  ur on u.ID=ur.user_id inner join role r on ur.user_role_id=r.id where user_name=? and user_pwd=?";
		Connection conn=JdbcUtils.getConnection();
		Data data = DBUtil.execute(conn, sql, new Object[]{userName,password});
	
		User user=null;
		if(data.hasData()){
			user=new User();
			user.setId(data.getTableField("ID"));
			user.setUsername(data.getTableField("USER_NAME"));
			user.setPassword(data.getTableField("USER_PWD"));
			user.setStatus(data.getTableField("ACTIVATE"));		
			user.setUserAccesses(data.getTableField("accesses"));					
			}
		return user;
	}

	public static User getUser(String userName) throws SQLException {
		String sql = "select * from `user` where user_name=? ";
		Connection conn=JdbcUtils.getConnection();

		Data data = DBUtil.execute(conn, sql, new Object[]{userName});
		
		User user=new User();
		if(data.hasData()){
			user.setUsername(data.getTableField("USER_NAME"));
			user.setPassword(data.getTableField("USER_PWD"));			
		}
		return user;
	}
	
	public static boolean insertUser(User user) throws SQLException {
		String sql = "insert into `user`(user_name, user_pwd) values(?,?) ";
		Connection conn=JdbcUtils.getConnection();
		
		return DBUtil.update(conn, sql, new Object[]{user.getUsername(),user.getPassword()})>0;
	}
	
}

