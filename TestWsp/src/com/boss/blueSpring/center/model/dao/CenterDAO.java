package com.boss.blueSpring.center.model.dao;

import static com.boss.blueSpring.common.JDBCTemplate.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.boss.blueSpring.center.model.vo.Center;

public class CenterDAO {
	private Statement stmt = null;
	private PreparedStatement pstmt = null;
	private ResultSet rset = null;
	
	
	/** 센터 목록 조회 DAO
	 * @param conn
	 * @param condition1
	 * @param condition2
	 * @return cList
	 * @throws Exception
	 */
	public List<Center> selectCenterList(Connection conn, String condition1, String condition2) throws Exception {
		List<Center> cList = null;
		
		String query = "SELECT * FROM CENTER WHERE" + condition1 + condition2;
		
		try {
			stmt = conn.createStatement();
			
			rset = stmt.executeQuery(query);
			
			cList = new ArrayList<Center>();
			
			while(rset.next()) {
				Center center = new Center(
						rset.getInt("CENTER_NO"),
						rset.getString("CENTER_CLA"),
						rset.getString("CENTER_AREA1"), 
						rset.getString("CENTER_AREA2"), 
						rset.getString("CENTER_NM"),
						rset.getString("CENTER_TEL"), 
						rset.getString("CENTER_URL"), 
						rset.getString("CENTER_ADDR"));
				cList.add(center);
			}
			
		} finally {
			close(rset);
			close(stmt);
		}
		
		return cList;
	}

}
