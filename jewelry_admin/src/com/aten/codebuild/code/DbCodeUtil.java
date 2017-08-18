package com.aten.codebuild.code;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DbCodeUtil {
	
	public static void main(String[] args) {
		new DbCodeUtil().getTableMsgByTableName("t_brand");
	}

	/**
	 * @author linjunqin
	 * @Description 通过表名返回数据库的表的字段名，字段类型，长度等信息
	 * @param tableName
	 *            表名
	 * @date 2016-12-16 下午4:38:43
	 */
	public static List getTableMsgByTableName(String tableName) {
		/*String sql = "select column_name,data_type ,data_length,data_precision,data_scale,nullable from user_tab_columns"
			+ " where table_name='"+tableName+"'";*/	
		String sql ="SHOW FULL FIELDS FROM "+tableName;
		System.out.println(sql);
		List tabList = sqlRun(sql);
		return tabList;
	}

	/**
	 * @author linjunqin
	 * @Description 执行sql
	 * @param sql
	 *            sql语句
	 * @return
	 * @date 2016-12-16 下午4:43:53
	 */
	public static List sqlRun(String sql) {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		List<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			 String url = "jdbc:mysql://192.168.1.230:3306/yszb_dev?"
		                + "user=yszb&password=at0594&useUnicode=true&characterEncoding=UTF8";
			conn = DriverManager.getConnection(url, "yszb", "at0594");// 连接数据库，yfqc代表帐户,at0594代表密码
			//conn = DriverManager.getConnection(url, "root", "123456");// 本地数据库
			
			/*Class.forName("oracle.jdbc.driver.OracleDriver");// oracle驱动程序
			//String url = "jdbc:oracle:thin:@10.25.45.243:1521:yunpay";// 数据库地址与实例名
			//conn = DriverManager.getConnection(url, "yfqc", "goQdDmd8WQ5EyigH");// 开发库
			String url = "jdbc:oracle:thin:@192.168.1.230:1521:yunpay";// @localhost为服务器名，yunpay为数据库实例名
			conn = DriverManager.getConnection(url, "linjq", "at0594");// 连接数据库，yfqc代表帐户,at0594代表密码
			*/
			
			stmt = conn.createStatement();// 执行sql
			rs = stmt.executeQuery(sql);// 执行查询
			if (rs != null) {
				System.out.println(rs);
				while (rs.next()) {// 使当前记录指针定位到记录集的第一条记录
					HashMap<String,String> hashMap =new HashMap<String,String>();
					//表数据
					/*System.out.println(rs.getString("Field") + " "
							+ rs.getString("Type") + " "
							+ rs.getString("Null") + " "
							+ rs.getString("Comment") + " "
							+ rs.getString("Default"));*/
					//列名
					//类型与长度处理
					String type = rs.getString("Type").toLowerCase();
					String length = "20000";
					if(type.indexOf("(")>-1){
						length= type.substring(type.indexOf("(")+1, type.indexOf(")"));
						type = type.substring(0, type.indexOf("("));
					}
					//是否为空的处理
					String is_null = "Y";
					if(rs.getString("Null").toLowerCase().equals("no")){
						is_null ="N";
					}
					hashMap.put("column_name", rs.getString("Field").toLowerCase());
					hashMap.put("column_comment", rs.getString("Comment").toLowerCase());
					hashMap.put("data_type",type);
					hashMap.put("data_length", length);
					hashMap.put("null", is_null);//Y 可以为空，N不为空
					list.add(hashMap);
				}
			}
			//输出列表内容
			System.out.println(list);

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				// 关闭数据库，结束进程
				rs.close();
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private List rsToList(ResultSet rs) throws SQLException {
		if (rs == null)
			return Collections.EMPTY_LIST;
		ResultSetMetaData md = rs.getMetaData(); // 得到结果集(rs)的结构信息，比如字段数、字段名等
		int columnCount = md.getColumnCount(); // 返回此 ResultSet 对象中的列数
		List list = new ArrayList();
		Map rowData = new HashMap();
		while (rs.next()) {
			rowData = new HashMap(columnCount);
			for (int i = 1; i <= columnCount; i++) {
				rowData.put(md.getColumnName(i), rs.getObject(i));
			}
			list.add(rowData);
			System.out.println("list:" + list.toString());
		}
		return list;
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	// 1代表当前记录的第一个字段的值，可以写成字段名。
	// 2代表当前记录的第二个字段的值，可以写成字段名。

	// 添加数据用executeUpdate
	// stmt.executeUpdate("insert into ss values(7,'张学友')");

	// 修改数据用executeUpdate
	// stmt.executeUpdate("update ss set name = '张曼玉' where id = 5");

	// 删除 数据用executeUpdate
	// stmt.executeUpdate("delete from ss where id = 6");

}