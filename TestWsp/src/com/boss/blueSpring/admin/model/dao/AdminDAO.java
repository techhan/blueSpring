package com.boss.blueSpring.admin.model.dao;

import static com.boss.blueSpring.common.JDBCTemplate.*;


import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

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

public class AdminDAO {
	
	private Statement stmt = null;
	private PreparedStatement pstmt = null;
	private ResultSet rset = null;
	
	private Properties prop = null;

	
	public AdminDAO() {
		String fileName = AdminDAO.class.getResource("/com/boss/blueSpring/sql/admin/admin-query.xml").getPath();
		try {
			prop = new Properties();
			prop.loadFromXML(new FileInputStream(fileName)); 
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// ************************************************************************* 자유게시판
	
   /** [자유게시판] 관리 : 전체 게시글 수 반환 DAO
    * @param conn
    * @return listCount
    * @throws Exception
    */
	public int getListCount(Connection conn) throws Exception {
      int listCount = 0;
      String query = prop.getProperty("getAdminBoardListCount");
      try {
         stmt = conn.createStatement();
         rset = stmt.executeQuery(query);
         if(rset.next()) {
            listCount = rset.getInt(1);
         }
      } finally {
         close(rset);
         close(stmt);
      }
      return listCount;
   }


	/** [자유게시판] 관리 : 게시글 목록 조회 DAO
	 * @param conn
	 * @param pInfo
	 * @return aList
	 * @throws Exception
	 */
	public List<Board> selectAdminList(Connection conn, PageInfo pInfo) throws Exception {
		List<Board> aList = null;
		String query = prop.getProperty("selectAdminBoardList");
	      try {
	          int startRow = (pInfo.getCurrentPage() - 1) * pInfo.getLimit() + 1;
	          int endRow = startRow + pInfo.getLimit() -  1;
	          pstmt = conn.prepareStatement(query);
	          pstmt.setInt(1, startRow);
	          pstmt.setInt(2,  endRow);
	          rset = pstmt.executeQuery();
	          aList = new ArrayList<Board>();
	             while(rset.next()) {
	                   Board board = new Board(
	                		   			rset.getInt("BRD_NO"), 
	                		   			rset.getString("BRD_TITLE"),
	                		   			rset.getString("MEM_ID"),
	                		   			rset.getString("BRD_DEL_FL"));
	                   aList.add(board);
	                }
	       } finally {
	          close(rset);
	          close(pstmt);
	       }
		
		return aList;
		
		
	}

	
	// ************************************************************************* 신고
	
	   /** [신고] 관리 : 전체 게시글 수 반환 DAO
	    * @param conn
	    * @return listCount
	    * @throws Exception
	    */
		public int getReportListCount(Connection conn) throws Exception {
	      int listCount = 0;
	      String query = prop.getProperty("getReportListCount");
	      
	      try {
	         stmt = conn.createStatement();
	         rset = stmt.executeQuery(query);
	         if(rset.next()) {
	            listCount = rset.getInt(1);
	         }
	      } finally {
	         close(rset);
	         close(stmt);
	      }
	      return listCount;
	   }
	
	/** [신고] 관리 : 목록 조회 DAO
	 * @param conn
	 * @param rpInfo
	 * @return rList
	 * @throws Exception
	 */
	public List<Report> selectReportList(Connection conn, ReportPageInfo rpInfo) throws Exception {
		List<Report> rList = null;
		String query = prop.getProperty("selectReportList");
		try {
	          int startRow = (rpInfo.getCurrentPage() - 1) * rpInfo.getLimit() + 1;
	          int endRow = startRow + rpInfo.getLimit() -  1;
	          pstmt = conn.prepareStatement(query);
	          pstmt.setInt(1, startRow);
	          pstmt.setInt(2,  endRow);
	          rset = pstmt.executeQuery();
	          rList = new ArrayList<Report>();
	             while(rset.next()) {
	                   Report report = new Report(
	                		   				rset.getInt("REPORT_NO"),
	                		   				rset.getString("REPORT_TYPE"),
	                		   				rset.getInt("BRD_NO"),
	                		   				rset.getInt("REPORT_CATE_NO"),	                		   				
	                		   				rset.getString("MEM_ID"),
	                		   				rset.getString("TARGET_ID"),
	                		   				rset.getString("MEM_BLACKLIST"));
	                   rList.add(report);
	                   System.out.println(rset.getString("TARGET_ID"));
	                   System.out.println(rset.getString("MEM_ID"));
	                }
		} finally {
	        close(rset);
	        close(pstmt);
		}
		return rList;
	}
	
   /** [신고] 페이지 상세조회 DAO
    * @param reportNo
    * @return report
    * @throws Exception
    */
	public Report selectReport(Connection conn, int reportNo) throws Exception {
		Report report = null;
		String query = prop.getProperty("selectReportPagetList");
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, reportNo);
			rset = pstmt.executeQuery();
			if (rset.next()) {
				report = new Report();
				report.setReportNo(rset.getInt("REPORT_NO"));
				report.setReportType(rset.getString("REPORT_TYPE"));
				report.setBoardNo(rset.getInt("BRD_NO"));			
				report.setMemberId(rset.getString("MEM_ID"));
				report.setReportCategoryNo(rset.getInt("REPORT_CATE_NO"));
				report.setReportContent(rset.getString("REPORT_CONTENT"));
			}
		} finally {
			close(rset);
			close(pstmt);
		}
		return report;
	}
	
	// ************************************************************************* 센터
	
   /** [센터] 관리 : 전체 게시글 수 반환 DAO
    * @param conn
    * @return listCount
    * @throws Exception	
    */
	public int getCenterListCount(Connection conn) throws Exception {
		int listCount = 0;
		String query = prop.getProperty("getCenterListCount");
		try {
			stmt = conn.createStatement();
			rset = stmt.executeQuery(query);
			if (rset.next()) {
				listCount = rset.getInt(1);
			}
		} finally {
			close(rset);
			close(stmt);
		}
		return listCount;
	}

	/** [센터] 관리 : 목록 조회 DAO
	 * @param conn
	 * @param cpInfo
	 * @return cList
	 * @throws Exception
	 */
	public List<Center> selectCenterList(Connection conn, CenterPageInfo cpInfo) throws Exception {
		List<Center> cList = null;
		String query = prop.getProperty("selectCenterList");
		try {
			int startRow = (cpInfo.getCurrentPage() - 1) * cpInfo.getLimit() + 1;
			int endRow = startRow + cpInfo.getLimit() - 1;
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);
			rset = pstmt.executeQuery();
			cList = new ArrayList<Center>();
			while (rset.next()) {
				Center center = new Center(
						rset.getInt("CENTER_NO"),
						rset.getString("CENTER_CLA"),
						rset.getString("CENTER_AREA1"), 
						rset.getString("CENTER_AREA2"), 
						rset.getString("CENTER_NM"),
						rset.getString("CENTER_TEL"), 
						rset.getString("CENTER_URL"), 
						rset.getString("CENTER_DEL_FL").charAt(0));
				cList.add(center);
			}
		} finally {
			close(rset);
			close(pstmt);
		}
		return cList;
	}

   /** [센터] 페이지 상세조회 DAO
    * @param centerNo
    * @return center
    * @throws Exception
    */
	public Center selectCenter(Connection conn, int centerNo) throws Exception {
		Center center = null;
		String query = prop.getProperty("selectCenterPagetList");
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, centerNo);
			rset = pstmt.executeQuery();
			if (rset.next()) {
				center = new Center();
				center.setCenterNo(rset.getInt("CENTER_NO"));
				center.setCenterCla(rset.getString("CENTER_CLA"));			
				center.setCenterArea1(rset.getString("CENTER_AREA1"));
				center.setCenterArea2(rset.getString("CENTER_AREA2"));
				center.setCenterName(rset.getString("CENTER_NM"));
				center.setCenterTel(rset.getString("CENTER_TEL"));
				center.setCenterUrl(rset.getString("CENTER_URL"));
				center.setCenterAddr(rset.getString("CENTER_ADDR"));
				center.setCenterAddrDtl(rset.getString("CENTER_ADDR_DTL"));
				center.setCenterDeleteFl(rset.getString("CENTER_DEL_FL").charAt(0));
			}
		} finally {
			close(rset);
			close(pstmt);
		}
		return center;
	}
	
	
	/** [센터등록] 기관명 중복 체크 DAO
	 * @param conn
	 * @param centerName
	 * @return result
	 * @throws Exception
	 */
	public int centerNameDubCheck(Connection conn, String centerName) throws Exception {
		int result = 0;
		String query = prop.getProperty("centerNameDubCheck");
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, centerName);
			rset = pstmt.executeQuery();
			if(rset.next()) {
				result = rset.getInt(1);
			}
		} finally {
			close(rset);
			close(pstmt);
		}
		return result;
	}

	
	/** [센터등록] 기관 등록 DAO
	 * @param center
	 * @return result
	 * @throws Exception
	 */
	public int centerAdd(Connection conn, Center center) throws Exception {
		int result = 0;
		String query = prop.getProperty("centerAdd");
		
		try {
			pstmt = conn.prepareStatement(query);
		
			pstmt.setString(1, center.getCenterCla());
			pstmt.setString(2, center.getCenterArea1());
			pstmt.setString(3, center.getCenterArea2());
			pstmt.setString(4, center.getCenterName());
			pstmt.setString(5, center.getCenterTel());
			pstmt.setString(6, center.getCenterUrl());
			pstmt.setString(7, center.getCenterAddr());
			pstmt.setString(8, center.getCenterAddrDtl());
			result = pstmt.executeUpdate();
		}finally {
			close(pstmt);
		}
		return result;
	}
	
	/** [센터수정] 기관 수정 DAO
	 * @param conn
	 * @param center
	 * @return result
	 * @throws Exception
	 */
	public int updateCenter(Connection conn, Center center) throws Exception {
		int result = 0;
		String query = prop.getProperty("updateCenter");
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, center.getCenterArea1());
			pstmt.setString(2, center.getCenterArea2());
			pstmt.setString(3, center.getCenterTel());
			pstmt.setString(4, center.getCenterUrl());
			pstmt.setString(5, center.getCenterAddr());
			pstmt.setString(6, center.getCenterAddrDtl());
			pstmt.setInt(7, center.getCenterNo());
			result = pstmt.executeUpdate();
		} finally {
			close(pstmt);
		}
		return result;
	}
	
	
	// ************************************************************************* 회원정보
	
   /** [회원정보] 관리 : 전체 게시글 수 반환 DAO
    * @param conn
    * @return listCount
    * @throws Exception	
    */
	public int getMemberListCount(Connection conn) throws Exception {
		int listCount = 0;
		String query = prop.getProperty("getMemberListCount");
		try {
			stmt = conn.createStatement();
			rset = stmt.executeQuery(query);
			if (rset.next()) {
				listCount = rset.getInt(1);
			}
		} finally {
			close(rset);
			close(stmt);
		}
		return listCount;
	}
	
	/** [회원정보] 관리 : 목록 조회 DAO
	 * @param conn
	 * @param mpInfo
	 * @return mList
	 * @throws Exception
	 */
	public List<Member> selectMemberList(Connection conn, MemberPageInfo mpInfo) throws Exception {
		List<Member> mList = null;
		String query = prop.getProperty("selectMemberList");
		try {
			int startRow = (mpInfo.getCurrentPage() - 1) * mpInfo.getLimit() + 1;
			int endRow = startRow + mpInfo.getLimit() - 1;
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);
			rset = pstmt.executeQuery();
			mList = new ArrayList<Member>();
			while (rset.next()) {
					Member member = new Member(
							rset.getInt("MEM_NO"),
							rset.getString("MEM_ID"),
							rset.getString("MEM_NICKNAME"), 
							rset.getString("MEM_NM"),
							rset.getDate("MEM_BIRTH"),
							rset.getString("MEM_GENDER").charAt(0), 
							rset.getString("MEM_PHONE"), 
							rset.getString("MEM_ADDR"),
							rset.getDate("MEM_JOINED"),
							rset.getString("MEM_SCSN_FL").charAt(0),
							rset.getString("MEM_BLACKLIST").charAt(0),
							rset.getString("MEM_LEVEL").charAt(0));
							mList.add(member);
					};
		} finally {
			close(rset);
			close(pstmt);
		}
		return mList;
	}

	// ************************************************************************* 블랙리스트
	
   /** [블랙리스트] 관리 : 전체 게시글 수 반환 DAO
    * @param conn
    * @return listCount
    * @throws Exception	
    */
	public int getBlackListCount(Connection conn) throws Exception {
		int listCount = 0;
		String query = prop.getProperty("getBlackListCount");
		try {
			stmt = conn.createStatement();
			rset = stmt.executeQuery(query);
			if (rset.next()) {
				listCount = rset.getInt(1);
			}
		} finally {
			close(rset);
			close(stmt);
		}
		return listCount;
	}

	
	/** [블랙리스트] 관리 : 목록 조회 DAO
	 * @param conn
	 * @param bpInfo
	 * @return bkList
	 * @throws Exception
	 */
	public List<Member> selectBlackList(Connection conn, BlacklistPageInfo bpInfo) throws Exception {
		List<Member> bkList = null;
		String query = prop.getProperty("selectBlackList");
		try {
			int startRow = (bpInfo.getCurrentPage() - 1) * bpInfo.getLimit() + 1;
			int endRow = startRow + bpInfo.getLimit() - 1;
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);
			rset = pstmt.executeQuery();
			bkList = new ArrayList<Member>();
			while (rset.next()) {
					Member member = new Member(
							rset.getInt("MEM_NO"),
							rset.getString("MEM_ID"),
							rset.getString("MEM_NICKNAME"), 
							rset.getString("MEM_NM"),
							rset.getDate("MEM_BIRTH"),
							rset.getString("MEM_GENDER").charAt(0), 
							rset.getString("MEM_PHONE"),
							rset.getDate("MEM_JOINED"),
							rset.getString("MEM_SCSN_FL").charAt(0),
							rset.getString("MEM_BLACKLIST").charAt(0),
							rset.getString("MEM_LEVEL").charAt(0));
							bkList.add(member);
					};
		} finally {
			close(rset);
			close(pstmt);
		}
		return bkList;
	}
	
	// ************************************************************************* 챌린지
	
   /** [챌린지] 관리 : 전체 게시글 수 반환 DAO
    * @param conn
    * @return listCount
    * @throws Exception
    */
	public int getChallListCount(Connection conn) throws Exception {
		int listCount = 0;
		String query = prop.getProperty("getChallListCount");
		try {
			stmt = conn.createStatement();
			rset = stmt.executeQuery(query);
			if (rset.next()) {
				listCount = rset.getInt(1);
			}
		} finally {
			close(rset);
			close(stmt);
		}
		return listCount;
	}
	
	/** [챌린지] 관리 : 목록 조회 DAO
	 * @param conn
	 * @param chpInfo
	 * @return chList
	 * @throws Exception
	 */
	public List<Challenge> selectChallList(Connection conn, ChallPageInfo chpInfo) throws Exception {
		List<Challenge> chList = null;
		String query = prop.getProperty("selectChallList");
		try {
			int startRow = (chpInfo.getCurrentPage() - 1) * chpInfo.getLimit() + 1;
			int endRow = startRow + chpInfo.getLimit() - 1;
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);
			rset = pstmt.executeQuery();
			chList = new ArrayList<Challenge>();
			while (rset.next()) {
				Challenge challenge = new Challenge(
							rset.getInt("CHLNG_NO"),
							rset.getString("CHLNG_TITLE"),
							rset.getString("MEM_ID"), 
							rset.getString("CHLNG_FL").charAt(0));
							chList.add(challenge);
					};
		} finally {
			close(rset);
			close(pstmt);
		}
		return chList;
	}

	// ************************************************************************* 챌린지 인증게시판
	
   /** [챌린지] 관리 : 전체 게시글 수 반환 DAO
    * @param conn
    * @return listCount
    * @throws Exception
    */
	public int getChallCrtfdListCount(Connection conn) throws Exception {
		int listCount = 0;
		String query = prop.getProperty("getChallCrtfdListCount");
		try {
			stmt = conn.createStatement();
			rset = stmt.executeQuery(query);
			if (rset.next()) {
				listCount = rset.getInt(1);
			}
		} finally {
			close(rset);
			close(stmt);
		}
		return listCount;
	}

	
	/** [챌린지 인증게시판] 관리 : 목록 조회 DAO
	 * @param conn
	 * @param crtpInfo
	 * @return crtList
	 * @throws Exception
	 */
	public List<ChallengeCrtfd> selectChallCrtfdList(Connection conn, ChallCrtfdPageInfo crtpInfo) throws Exception {
		List<ChallengeCrtfd> crtList = null;
		String query = prop.getProperty("selectChallCrtfdList");
		try {
			int startRow = (crtpInfo.getCurrentPage() - 1) * crtpInfo.getLimit() + 1;
			int endRow = startRow + crtpInfo.getLimit() - 1;
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);
			rset = pstmt.executeQuery();
			crtList = new ArrayList<ChallengeCrtfd>();
			while (rset.next()) {
				ChallengeCrtfd challengeCrtfd = new ChallengeCrtfd(
							rset.getInt("CHLNG_NO"),
							rset.getInt("CHLNG_BRD_NO"),
							rset.getString("CHLNG_BRD_TITLE"),
							rset.getString("MEM_ID"), 
							rset.getString("CHLNG_BRD_DEL_FL").charAt(0));
							crtList.add(challengeCrtfd);
					};
		} finally {
			close(rset);
			close(pstmt);
		}
		return crtList;
	}


}
