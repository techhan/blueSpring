package com.boss.blueSpring.admin.model.service;

import static com.boss.blueSpring.common.JDBCTemplate.*;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import com.boss.blueSpring.admin.model.dao.AdminSearchDAO;
import com.boss.blueSpring.admin.model.vo.BlacklistPageInfo;
import com.boss.blueSpring.admin.model.vo.CenterPageInfo;
import com.boss.blueSpring.admin.model.vo.ChallCrtfdPageInfo;
import com.boss.blueSpring.admin.model.vo.ChallPageInfo;
import com.boss.blueSpring.admin.model.vo.MemberPageInfo;
import com.boss.blueSpring.admin.model.vo.ReportPageInfo;
import com.boss.blueSpring.board.model.vo.Board;
import com.boss.blueSpring.board.model.vo.PageInfo;
import com.boss.blueSpring.center.model.vo.Center;
import com.boss.blueSpring.challenge.model.vo.Challenge;
import com.boss.blueSpring.challengecrtfd.model.vo.ChallengeCrtfd;
import com.boss.blueSpring.member.model.vo.Member;
import com.boss.blueSpring.report.model.vo.Report;

public class AdminSearchService {
	
	private AdminSearchDAO dao = new AdminSearchDAO();
	
	// ************************************************************************* 자유게시판

	/** [자유게시판] 관리 : 검색 내용이 포함된 페이징 처리 정보 생성 Service
	 * @param map
	 * @return pInfo
	 * @throws Exception
	 */
	public PageInfo getPageInfo(Map<String, Object> map) throws Exception {
		Connection conn = getConnection();
		map.put("currentPage", 
				( map.get("currentPage") == null) ? 1 : Integer.parseInt((String)map.get("currentPage")) );
		String condition = createCondition(map);
		int listCount = dao.getListCount(conn, condition);
		close(conn);
		return new PageInfo( (int)map.get("currentPage"), listCount);
	}

	
	/** [자유게시판] 관리 : 검색 조건에 따라 SQL에 사용될 조건문을 조합하는 메소드
	 * @param map
	 * @return
	 */
	private String createCondition(Map<String, Object> map) {
		String condition = null;
		String searchKey = (String)map.get("searchKey");
		String searchValue = (String)map.get("searchValue");
		switch(searchKey) {
		case "no" : 
			condition = " BRD_NO LIKE '%' || '" + searchValue + "' || '%' ";
			break;

		case "title" : 
			condition = " BRD_TITLE LIKE '%' || '" + searchValue + "' || '%' ";
			break;
			
		case "writer" : 
			condition = " MEM_ID LIKE '%' || '" + searchValue + "' || '%' ";
			break;

		case "status" : 
			condition = " BRD_DEL_FL = " + "'" + searchValue + "'";                    
			break;
		}
		return condition;
	}
	
	/** [자유게시판] 관리 : 검색 게시글 목록 리스트 조회 Service
	 * @param map
	 * @param pInfo 
	 * @return aList
	 * @throws Exception
	 */
	public List<Board> searchBoardList(Map<String, Object> map, PageInfo pInfo) throws Exception {
		Connection conn = getConnection();
		String condition = createCondition(map);
		List<Board> aList = dao.searchBoardList(conn, pInfo, condition);
		close(conn);
		return aList;
	}

	// ************************************************************************* 신고
	
	/** [신고] 관리 : 검색 내용이 포함된 페이징 처리 정보 생성 Service
	 * @param map
	 * @return pInfo
	 * @throws Exception
	 */
	public ReportPageInfo getPageInfoReport(Map<String, Object> map) throws Exception {
		Connection conn = getConnection();
		map.put("currentPage", 
				( map.get("currentPage") == null) ? 1 : Integer.parseInt((String)map.get("currentPage")) );
		String condition = createReportCondition(map);
		int listCount = dao.getListCountReport(conn, condition);
		close(conn);
		return new ReportPageInfo( (int)map.get("currentPage"), listCount);
	}
	
	/** [신고] 관리 : 검색 조건에 따라 SQL에 사용될 조건문을 조합하는 메소드
	 * @param map
	 * @return
	 */
	private String createReportCondition(Map<String, Object> map) {
		String condition = null;
		String searchKey = (String)map.get("searchKey");
		String searchValue = (String)map.get("searchValue");
		switch(searchKey) {
		case "reportNo" : 
			condition = "REPORT_NO LIKE '%' || '" + searchValue + "' || '%' ";
			break;

		case "reportType" : 
			condition = "REPORT_TYPE LIKE '%' || '" + searchValue + "' || '%' ";
			break;

		case "brdNo" : 
			condition = "BRD_NO LIKE '%' || '" + searchValue + "' || '%' ";                   
			break;
		}
		return condition;
	}


	/** [신고] 관리 : 신고 목록 검색 Service
	 * @param map
	 * @param rpInfo
	 * @return rList;
	 * @throws Exception
	 */
	public List<Report> searchReportList(Map<String, Object> map, ReportPageInfo rpInfo) throws Exception {
		Connection conn = getConnection();
		String condition = createReportCondition(map);
		List<Report> rList = dao.searchReportList(conn, rpInfo, condition);
		close(conn);
		return rList;
	}

	// ************************************************************************* 센터
	
	/** [센터] 관리 : 검색 내용이 포함된 페이징 처리 정보 생성 Service
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public CenterPageInfo getPageInfoCenter(Map<String, Object> map) throws Exception {
		Connection conn = getConnection();
		map.put("currentPage", 
				( map.get("currentPage") == null) ? 1 : Integer.parseInt((String)map.get("currentPage")) );
		String condition = createCenterCondition(map);
		int listCount = dao.getListCountCenter(conn, condition);
		close(conn);
		return new CenterPageInfo( (int)map.get("currentPage"), listCount);
	}

	/** [센터] 관리 : 검색 조건에 따라 SQL에 사용될 조건문을 조합하는 메소드
	 * @param map
	 * @return
	 */
	private String createCenterCondition(Map<String, Object> map) throws Exception {
		String condition = null;
		String searchKey = (String)map.get("searchKey");
		String searchValue = (String)map.get("searchValue");
		switch(searchKey) {
		case "centerNo" : 
			condition = " CENTER_NO LIKE '%' || '" + searchValue + "' || '%' ";
			break;

		case "centerCla" : 
			condition = " CENTER_CLA LIKE '%' || '" + searchValue + "' || '%' ";
			break;
			
		case "centerArea1" : 
			condition = " CENTER_AREA1 LIKE '%' || '" + searchValue + "' || '%' ";
			break;

		case "centerArea2" : 
			condition = " CENTER_AREA2 LIKE '%' || '" + searchValue + "' || '%' ";
			break;

		case "centerNm" : 
			condition = " CENTER_NM LIKE '%' || '" + searchValue + "' || '%' ";
			break;
		}
		return condition;
	}

	/** [센터] : 센터 목록 검색 Service
	 * @param map
	 * @param cpInfo
	 * @return cList;
	 * @throws Exception
	 */
	public List<Center> searchCenterList(Map<String, Object> map, CenterPageInfo cpInfo) throws Exception {
		Connection conn = getConnection();
		String condition = createCenterCondition(map);
		List<Center> cList = dao.searchCenterList(conn, cpInfo, condition);
		close(conn);
		return cList;
	}

	// ************************************************************************* 회원정보
	
	/** [회원정보] 관리 : 검색 내용이 포함된 페이징 처리 정보 생성 Service
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public MemberPageInfo getPageInfoMember(Map<String, Object> map) throws Exception {
		Connection conn = getConnection();
		map.put("currentPage", 
				( map.get("currentPage") == null) ? 1 : Integer.parseInt((String)map.get("currentPage")) );
		String condition = createMemberCondition(map);
		int listCount = dao.getListCountMember(conn, condition);
		close(conn);
		return new MemberPageInfo( (int)map.get("currentPage"), listCount);
	}

	/** [회원정보] 관리 : 검색 조건에 따라 SQL에 사용될 조건문을 조합하는 메소드
	 * @param map
	 * @return
	 */
	private String createMemberCondition(Map<String, Object> map) throws Exception {
		String condition = null;
		String searchKey = (String)map.get("searchKey");
		String searchValue = (String)map.get("searchValue");
		switch(searchKey) {
		case "memNo" : 
			condition = " MEM_NO LIKE '%' || '" + searchValue + "' || '%' ";
			break;

		case "memId" : 
			condition = " MEM_ID LIKE '%' || '" + searchValue + "' || '%' ";
			break;
			
		case "memNick" : 
			condition = " MEM_NICKNAME LIKE '%' || '" + searchValue + "' || '%' ";
			break;
		}
		return condition;
	}

	/** [회원정보] : 회원정보 목록 검색 Service
	 * @param map
	 * @param mpInfo
	 * @return mList;
	 * @throws Exception
	 */
	public List<Member> searchMemberList(Map<String, Object> map, MemberPageInfo mpInfo) throws Exception {
		Connection conn = getConnection();
		String condition = createMemberCondition(map);
		List<Member> mList = dao.searchMemberList(conn, mpInfo, condition);
		close(conn);
		return mList;
	}

	// ************************************************************************* 블랙리스트
	
	/** [블랙리스트] 관리 : 검색 내용이 포함된 페이징 처리 정보 생성 Service
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public BlacklistPageInfo getPageInfoBlack(Map<String, Object> map) throws Exception {
		Connection conn = getConnection();
		map.put("currentPage",
				(map.get("currentPage") == null) ? 1 : Integer.parseInt((String) map.get("currentPage")));
		String condition = createBlacklistCondition(map);
		int listCount = dao.getBlackListCount(conn, condition);
		close(conn);
		return new BlacklistPageInfo((int) map.get("currentPage"), listCount);
	}
	
	/** [블랙리스트] 관리 : 검색 조건에 따라 SQL에 사용될 조건문을 조합하는 메소드
	 * @param map
	 * @return
	 */
	private String createBlacklistCondition(Map<String, Object> map) throws Exception {
		String condition = null;
		String searchKey = (String)map.get("searchKey");
		String searchValue = (String)map.get("searchValue");
		switch(searchKey) {
		case "bkNo" : 
			condition = " MEM_NO LIKE '%' || '" + searchValue + "' || '%' ";
			break;

		case "bkId" : 
			condition = " MEM_ID LIKE '%' || '" + searchValue + "' || '%' ";
			break;
			
		case "bkNick" : 
			condition = " MEM_NICKNAME LIKE '%' || '" + searchValue + "' || '%' ";
			break;
		}
		return condition;
	}


	/** [블랙리스트] : 블랙리스트 목록 검색 Service
	 * @param map
	 * @param bpInfo
	 * @return bkList;
	 * @throws Exception
	 */
	public List<Member> searchBlackList(Map<String, Object> map, BlacklistPageInfo bpInfo) throws Exception {
		Connection conn = getConnection();
		String condition = createBlacklistCondition(map);
		List<Member> bkList = dao.searchBlackList(conn, bpInfo, condition);
		close(conn);
		return bkList;
	}


	// ************************************************************************* 챌린지
	
	/** [챌린지] 관리 : 검색 내용이 포함된 페이징 처리 정보 생성 Service
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public ChallPageInfo getPageInfoChall(Map<String, Object> map) throws Exception {
		Connection conn = getConnection();
		map.put("currentPage",
				(map.get("currentPage") == null) ? 1 : Integer.parseInt((String) map.get("currentPage")));
		String condition = createChallCondition(map);
		
		map.put("condition", condition);
		
		int listCount = dao.getChallListCount(conn, condition);
		close(conn);
		return new ChallPageInfo((int) map.get("currentPage"), listCount);
	}
	

	/** [챌린지] 관리 : 검색 조건에 따라 SQL에 사용될 조건문을 조합하는 메소드
	 * @param map
	 * @return
	 */
	private String createChallCondition(Map<String, Object> map) throws Exception {
		String condition = null;
		String searchKey = (String)map.get("searchKey");
		String searchValue = (String)map.get("searchValue");
		switch(searchKey) {
		case "challNo" : 
			condition = " CHLNG_NO LIKE '%' || '" + searchValue + "' || '%' ";
			break;

		case "challTitle" : 
			condition = " CHLNG_TITLE LIKE '%' || '" + searchValue + "' || '%' ";
			break;
			
		case "challId" : 
			condition = " MEM_ID LIKE '%' || '" + searchValue + "' || '%' ";
			break;
			
		case "challFl" : 
			condition = " CHLNG_FL = " + "'" + searchValue + "'";
			break;
		}
		return condition;
	}

	/** [챌린지] : 챌린지 목록 검색 Service
	 * @param map
	 * @param chpInfo
	 * @return chList;
	 * @throws Exception
	 */
	public List<Challenge> searchChallList(Map<String, Object> map, ChallPageInfo chpInfo) throws Exception {
		Connection conn = getConnection();
		String condition = createChallCondition(map);
		List<Challenge> chList = dao.searchChallList(conn, chpInfo, condition);
		close(conn);
		return chList;
	}


	
	// ************************************************************************* 챌린지 인증게시판
	
	/** [챌린지 인증게시판] 관리 : 검색 내용이 포함된 페이징 처리 정보 생성 Service
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public ChallCrtfdPageInfo getPageInfoChallCrtfd(Map<String, Object> map) throws Exception {
		Connection conn = getConnection();
		map.put("currentPage",
				(map.get("currentPage") == null) ? 1 : Integer.parseInt((String) map.get("currentPage")));
		String condition = createChallCrtfdCondition(map);
		int listCount = dao.getChallCrtfdListCount(conn, condition);
		close(conn);
		return new ChallCrtfdPageInfo((int) map.get("currentPage"), listCount);
	}

	
	/** [챌린지 인증게시판] 관리 : 검색 조건에 따라 SQL에 사용될 조건문을 조합하는 메소드
	 * @param map
	 * @return
	 */
	private String createChallCrtfdCondition(Map<String, Object> map) throws Exception {
		String condition = null;
		String searchKey = (String)map.get("searchKey");
		String searchValue = (String)map.get("searchValue");
		switch(searchKey) {
		case "challNo" : 
			condition = " CHLNG_NO LIKE '%' || '" + searchValue + "' || '%' ";
			break;

		case "crtfdNo" : 
			condition = " CHLNG_BRD_NO LIKE '%' || '" + searchValue + "' || '%' ";
			break;
			
		case "crtfdId" : 
			condition = " MEM_ID LIKE '%' || '" + searchValue + "' || '%' ";
			break;
			
		case "crtfdFl" : 
			condition = " CHLNG_BRD_DEL_FL = " + "'" + searchValue + "'";
			break;
		}
		return condition;
	}


	/** [챌린지 인증게시판] : 챌린지 목록 검색 Service
	 * @param map
	 * @param crtpInfo
	 * @return crtList;
	 * @throws Exception
	 */
	public List<ChallengeCrtfd> searchChallCrtfdList(Map<String, Object> map, ChallCrtfdPageInfo crtpInfo) throws Exception {
		Connection conn = getConnection();
		String condition = createChallCrtfdCondition(map);
		List<ChallengeCrtfd> crtList = dao.searchChallCrtfdList(conn, crtpInfo, condition);
		close(conn);
		return crtList;
	}
	
	
	
	
	

}
