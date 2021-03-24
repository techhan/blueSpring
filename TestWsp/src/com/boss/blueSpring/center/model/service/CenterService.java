package com.boss.blueSpring.center.model.service;

import static com.boss.blueSpring.common.JDBCTemplate.*;

import java.sql.Connection;
import java.util.List;

import com.boss.blueSpring.center.model.dao.CenterDAO;
import com.boss.blueSpring.center.model.vo.Center;

public class CenterService {

	private CenterDAO dao = new CenterDAO();
	
	/** 센터 목록 조회 Service
	 * @param guguns
	 * @param sido
	 * @return cList
	 * @throws Exception
	 */
	public List<Center> selectCenterList(String guguns, String sido) throws Exception {
		Connection conn = getConnection();
		
		
		String condition1 = " CENTER_AREA1 LIKE '%' || '" + sido + "' || '%'";
		String condition2 = " AND (" + guguns + ")";
		
		List<Center> cList = dao.selectCenterList(conn, condition1, condition2);
		
		close(conn);
		
		return cList;
	}	
	

}
