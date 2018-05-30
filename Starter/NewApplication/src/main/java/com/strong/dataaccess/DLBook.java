package com.strong.dataaccess;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.strong.dbutil.DBUtil;
import com.strong.dbutil.JdbcUtils;
import com.strong.common.Data;

public class DLBook {

	public Data getBooks(String test) throws SQLException {
		String sql = "select * from `user` where id<3; select * from book;";
		Data data = DBUtil.execute(JdbcUtils.getConnection(), sql);

		return data;
	}
}
