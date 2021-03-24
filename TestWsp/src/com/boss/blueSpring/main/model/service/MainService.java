package com.boss.blueSpring.main.model.service;

import static com.boss.blueSpring.common.JDBCTemplate.*;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import com.boss.blueSpring.board.model.vo.Board;
import com.boss.blueSpring.board.model.vo.PageInfo;
import com.boss.blueSpring.challenge.model.vo.Challenge;
import com.boss.blueSpring.challengecrtfd.model.vo.ChallengeCrtfd;
import com.boss.blueSpring.main.model.dao.MainDAO;
import com.boss.blueSpring.main.model.vo.MainPageInfo;
import com.boss.blueSpring.main.model.vo.MainSearch;
import com.boss.blueSpring.notice.model.vo.Notice;

public class MainService {
	
	private MainDAO dao = new MainDAO();
	
	/** [메인] 공지사항 조회 Service
	 * @return nList
	 * @throws Exception
	 */
	public List<Notice> selectMainNotice() throws Exception {
		Connection conn = getConnection();
		List<Notice> nList = dao.selectMainNotice(conn);
		close(conn);
		return nList;
	}

	/** [메인] 자유게시판 조회 Service
	 * @return bList
	 * @throws Exception
	 */
	public List<Board> selectMainBoard()  throws Exception{
		Connection conn = getConnection();
		List<Board> bList = dao.selectMainBoard(conn);
		close(conn);
		return bList;
	}
	
	/** [메인] 챌린지 조회 Service
	 * @return cList
	 * @throws Exception
	 */
	public List<Challenge> selectMainChallenge()  throws Exception{
		Connection conn = getConnection();
		List<Challenge> cList = dao.selectMainChallenge(conn);
		close(conn);
		return cList;
	}
	
	/** [메인] 챌린지 인증게시판 조회 Service
	 * @return crtList
	 * @throws Exception
	 */
	public List<ChallengeCrtfd> selectMainChallengeCrtfd()  throws Exception{
		Connection conn = getConnection();
		List<ChallengeCrtfd> crtList = dao.selectMainChallengeCrtfd(conn);
		close(conn);
		return crtList;
	}

	public MainPageInfo getPageInfo(Map<String, Object> map) throws Exception {
		Connection conn = getConnection();
		map.put("currentPage", 
				( map.get("currentPage") == null) ? 1 : Integer.parseInt((String)map.get("currentPage")) );
		int listCount = dao.getListCount(conn, (String)map.get("searchValue"));
		
		close(conn);
		
		return new MainPageInfo( (int)map.get("currentPage"), listCount);
		
	}

	public List<MainSearch> searchMainList(Map<String, Object> map, MainPageInfo mpInfo) throws Exception {
		Connection conn = getConnection();
		List<MainSearch> mList = dao.searchMainList(conn, mpInfo, (String)map.get("searchValue"));
		close(conn);
		
		return mList;
	}

	
	
	
	
	
}
