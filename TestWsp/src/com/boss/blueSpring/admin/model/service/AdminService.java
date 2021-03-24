package com.boss.blueSpring.admin.model.service;

import static com.boss.blueSpring.common.JDBCTemplate.*;

import java.sql.Connection;
import java.util.List;

import com.boss.blueSpring.admin.model.dao.AdminDAO;
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
import com.boss.blueSpring.notice.model.vo.Notice;
import com.boss.blueSpring.report.model.vo.Report;

public class AdminService {
	
	private AdminDAO dao = new AdminDAO();

	
	// ************************************************************************* 자유게시판
	
   /** [자유게시판] 관리 : 페이징 처리를 위한 값 계산 Service
    * @param cp
    * @return PageInfo(currentPage, listCount)
    * @throws Exception
    */
	public PageInfo getPageInfo(String cp) throws Exception {

		Connection conn = getConnection();
		int currentPage = cp == null ? 1 : Integer.parseInt(cp);
		int listCount = dao.getListCount(conn);
		close(conn);
		return new PageInfo(currentPage, listCount);
	}

	/** [자유게시판] 관리 : 게시글 목록 조회 Service
	 * @param pInfo
	 * @return aList
	 * @throws Exception
	 */
	public List<Board> selectAdminList(PageInfo pInfo) throws Exception {
		Connection conn = getConnection();
		List<Board> aList = dao.selectAdminList(conn, pInfo);
		close(conn);
		return aList;
	}

	// ************************************************************************* 신고
	
	/** [신고] 관리 : 페이징 처리를 위한 값 계산 Service
	 * @param cp
	 * @return PageInfo(currentPage, listCount)
	 * @throws Exception
	 */
	public ReportPageInfo ReportPageInfo(String cp) throws Exception {
		Connection conn = getConnection();
		int currentPage = cp == null ? 1 : Integer.parseInt(cp);
		int listCount = dao.getReportListCount(conn);
		close(conn);
		return new ReportPageInfo(currentPage, listCount);
	}
	
	/** [신고] 관리 : 목록 조회 Service
	 * @param rpInfo
	 * @return rList
	 * @throws Exception
	 */
	public List<Report> selectReportList(ReportPageInfo rpInfo) throws Exception {
		Connection conn = getConnection();
		List<Report> rList = dao.selectReportList(conn, rpInfo);
		close(conn);
		return rList;
	}
	
	/** [신고] 페이지 상세조회 Service
	    * @param reportNo
	    * @return report
	    * @throws Exception
	    */
		public Report selectReport(int reportNo) throws Exception {
			Connection conn = getConnection();
			Report report = dao.selectReport(conn, reportNo);
			return report;
		}

	
	// ************************************************************************* 센터
	
	/** [센터] 관리 : 페이징 처리를 위한 값 계산 Service
	 * @param cp
	 * @return
	 * @throws Exception
	 */
	public CenterPageInfo CenterPageInfo(String cp) throws Exception {
		Connection conn = getConnection();
		int currentPage = cp == null ? 1 : Integer.parseInt(cp);
		int listCount = dao.getCenterListCount(conn);
		close(conn);
		return new CenterPageInfo(currentPage, listCount);
	}

	/** [센터] 관리 : 목록 조회 Service
	 * @param cpInfo
	 * @return cList
	 * @throws Exception
	 */
	public List<Center> selectCenterList(CenterPageInfo cpInfo) throws Exception {
		Connection conn = getConnection();
		List<Center> cList = dao.selectCenterList(conn, cpInfo);
		close(conn);
		return cList;
	}
	
	/** [센터] 페이지 상세조회 Service
	 * @param centerNo
	 * @return center
	 * @throws Exception
	 */
	public Center selectCenter(int centerNo) throws Exception {
		Connection conn = getConnection();
		Center center = dao.selectCenter(conn, centerNo);
		return center;
	}

	/** [센터등록] 기관명 중복 체크 Service
	 * @param centerName
	 * @return result
	 * @throws Exception
	 */
	public int centerNameDubCheck(String centerName) throws Exception {
		Connection conn = getConnection();
		int result = dao.centerNameDubCheck(conn, centerName);
		close(conn);
		return result;
	}

	/** [센터등록] 기관 등록 Service
	 * @param center
	 * @return result
	 * @throws Exception
	 */
	public int centerAdd(Center center) throws Exception {
		Connection conn = getConnection();
		int result = dao.centerAdd(conn, center);
		if(result > 0) commit(conn);
		else commit(conn);
		close(conn);
		return result;
	}
	
	/** [센터수정] 기관 수정 Service
	 * @param center
	 * @return result
	 * @throws Exception
	 */
	public int updateCenter(Center center) throws Exception {
		Connection conn = getConnection();
		int result = dao.updateCenter(conn, center);
		if(result > 0) commit(conn);
		else rollback(conn);
		return result;
	}

	
	// ************************************************************************* 회원정보
	
	/** [회원정보] 관리 : 페이징 처리를 위한 값 계산 Service
	 * @param cp
	 * @return
	 * @throws Exception
	 */
	public MemberPageInfo MemberPageInfo(String cp) throws Exception {
		Connection conn = getConnection();
		int currentPage = cp == null ? 1 : Integer.parseInt(cp);
		int listCount = dao.getMemberListCount(conn);
		close(conn);
		
		return new MemberPageInfo(currentPage, listCount);
	}

	/** [회원정보] 관리 : 목록 조회 Service
	 * @param mpInfo
	 * @return mList
	 * @throws Exception
	 */
	public List<Member> selectMemberList(MemberPageInfo mpInfo) throws Exception {
		Connection conn = getConnection();
		List<Member> mList = dao.selectMemberList(conn, mpInfo);
		close(conn);
		return mList;
		
	}
	
	// ************************************************************************* 블랙리스트
	
	/** [블랙리스트] 관리 : 페이징 처리를 위한 값 계산 Service
	 * @param cp
	 * @return
	 * @throws Exception
	 */
	public BlacklistPageInfo BlacklistPageInfo(String cp) throws Exception {
		Connection conn = getConnection();
		int currentPage = cp == null ? 1 : Integer.parseInt(cp);
		int listCount = dao.getBlackListCount(conn);
		close(conn);
		
		return new BlacklistPageInfo(currentPage, listCount);
	}

	/** [블랙리스트] 관리 : 목록 조회 Service
	 * @param bpInfo
	 * @return bkList
	 * @throws Exception
	 */
	public List<Member> selectBlackList(BlacklistPageInfo bpInfo) throws Exception {
		Connection conn = getConnection();
		List<Member> bkList = dao.selectBlackList(conn, bpInfo);
		close(conn);
		return bkList;
	}
	
	// ************************************************************************* 챌린지 관리
	
	/** [챌린지] 관리 : 페이징 처리를 위한 값 계산 Service
	 * @param cp
	 * @return
	 * @throws Exception
	 */
	public ChallPageInfo ChallPageInfo(String cp) throws Exception {
		Connection conn = getConnection();
		int currentPage = cp == null ? 1 : Integer.parseInt(cp);
		int listCount = dao.getChallListCount(conn);
		close(conn);
		
		return new ChallPageInfo(currentPage, listCount);
	}
	
	/** [챌린지] 관리 : 목록 조회 Service
	 * @param pInfo
	 * @return chList
	 * @throws Exception
	 */
	public List<Challenge> selectChallList(ChallPageInfo chpInfo) throws Exception {
		Connection conn = getConnection();
		List<Challenge> chList = dao.selectChallList(conn, chpInfo);
		close(conn);
		return chList;
	}

	// ************************************************************************* 챌린지 인증게시판 관리
	
	/** [챌린지 인증게시판] 관리 : 페이징 처리를 위한 값 계산 Service
	 * @param cp
	 * @return
	 * @throws Exception
	 */
	public ChallCrtfdPageInfo ChallCrtfdPageInfo(String cp) throws Exception {
		Connection conn = getConnection();
		int currentPage = cp == null ? 1 : Integer.parseInt(cp);
		int listCount = dao.getChallCrtfdListCount(conn);
		close(conn);
		
		return new ChallCrtfdPageInfo(currentPage, listCount);
	}

	/** [챌린지 인증게시판] 관리 : 목록 조회 Service
	 * @param crtpInfo
	 * @return crtList
	 * @throws Exception
	 */
	public List<ChallengeCrtfd> selectChallCrtfdList(ChallCrtfdPageInfo crtpInfo) throws Exception {
		Connection conn = getConnection();
		List<ChallengeCrtfd> crtList = dao.selectChallCrtfdList(conn, crtpInfo);
		close(conn);
		return crtList;
	}
	
	
	
	
	
}
