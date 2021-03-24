package com.boss.blueSpring.mypage.model.service;

import static com.boss.blueSpring.common.JDBCTemplate.*;

import java.sql.Connection;
import java.util.List;

import com.boss.blueSpring.board.model.vo.Board;
import com.boss.blueSpring.board.model.vo.PageInfo;
import com.boss.blueSpring.challenge.model.vo.Attachment;
import com.boss.blueSpring.challenge.model.vo.Challenge;
import com.boss.blueSpring.challengecrtfd.model.vo.ChallengeCrtfd;
import com.boss.blueSpring.comment.model.vo.Comment;
import com.boss.blueSpring.member.model.dao.MemberDAO;
import com.boss.blueSpring.member.model.vo.Member;
import com.boss.blueSpring.mypage.model.dao.MypageDAO;
import com.boss.blueSpring.notice.model.vo.Notice;

public class MypageService {
	
	private MypageDAO dao = new MypageDAO();

	/** 내 정보 수정 확인용 비밀번호 Service
	 * @param memberNo
	 * @param pwd
	 * @return result
	 * @throws Exception
	 */
	public int myInfoPwCheck(int memberNo, String pwd) throws Exception{
		Connection conn = getConnection();
		int result = dao.myInfoPwCheck(conn, memberNo, pwd);
		close(conn);
		return result;
	}

	/** 내 정보 수정 - 비밀번호 변경 Service
	 * @param newPw
	 * @param memNo
	 * @return result
	 * @throws Exception 
	 */
	public int changePw(String newPw, int memNo) throws Exception{
		Connection conn = getConnection();
		int result = dao.changePw(conn, newPw, memNo);
		if(result > 0) commit(conn);
		else rollback(conn);
		return result;
	}

	
	/** 내 정보 수정 Service
	 * @param member
	 * @return result
	 * @throws Exception
	 */
	public int updateMember(Member member) throws Exception{
		Connection conn = getConnection();
		int result = dao.updateMember(conn, member);
		if(result > 0) commit(conn);
		else rollback(conn);
		return result;
	}

	/** 닉네임 중복 체크 Service
	 * @param nickname
	 * @return result
	 * @throws Exception
	 */
	public int nicknameDubCheck(String nickname) throws Exception {
		Connection conn = getConnection();
		int result = dao.nicknameDubCheck(conn, nickname);
		close(conn);
		return result;
	}

	/** 회원 탈퇴 Service
	 * @param pwd
	 * @param memNo
	 * @return result
	 * @throws Exception
	 */
	public int updateStatus(String pwd, int memNo) throws Exception{
		Connection conn = getConnection();
		
		int result = dao.myInfoPwCheck(conn, memNo, pwd);
		
		if(result > 0) {
			result = dao.updateStatus(conn, memNo);
			
			if(result > 0) commit(conn);
			else rollback(conn);
			} else {
				result = -1;
			}
		
		close(conn);
		return result;
	}

	
	/** 페이징 처리를 위한 값 계산 Service
	 * @param cp
	 * @return pInfo
	 * @throws Exception
	 */
	public PageInfo getPageInfo(String cp, String memId) throws Exception{
		Connection conn = getConnection();
		
		int currentPage = cp == null ? 1 : Integer.parseInt(cp);
		
		int listCount = dao.getListCount(conn, memId); 
		
		close(conn);
		
		return new PageInfo(currentPage, listCount);
	}

	/** 게시글 목록 조회 Service
	 * @param pInfo
	 * @param memId 
	 * @return bList
	 * @throws Exception
	 */
	public List<Board> selectBoardList(PageInfo pInfo, String cn, String memId) throws Exception {
		Connection conn = getConnection();
		List<Board> bList = dao.selectBoardList(conn, pInfo, cn, memId);
		close(conn);
		return bList;
	}

	/** 댓글 목록 조회 Service
	 * @param parentBoardNo
	 * @param memId
	 * @return cList
	 * @throws Exception
	 */
	public List<Comment> selectList(PageInfo pInfo, String cn, String memId) throws Exception {
		Connection conn = getConnection();
		List<Comment> cList = dao.selectList(conn, pInfo, cn, memId);
		close(conn);
		return cList;
	}

	/** 댓글 페이징 처리를 위한 값 계산 Service
	 * @param cp
	 * @param memId
	 * @return pInfo
	 * @throws Exception
	 */
	public PageInfo getCommentPageInfo(String cp, String memId) throws Exception {
		Connection conn = getConnection();
		
		int currentPage = cp == null ? 1 : Integer.parseInt(cp);
		
		int listCount = dao.getCommentPageInfo(conn, memId); 
		
		close(conn);
		
		return new PageInfo(currentPage, listCount);
	}

	
	/** 내가 참여한 챌린지 수 계산 Service
	 * @param cp
	 * @param memId
	 * @return pInfo
	 * @throws Exception
	 */
	public PageInfo getMyChallengeCount(String cp, String memId) throws Exception{
		Connection conn = getConnection();
		
		int currentPage = cp == null ? 1 : Integer.parseInt(cp);

		int listCount = dao.getMyChallengeCount(conn, memId);
		
		close(conn);
	
		return new PageInfo(currentPage, listCount);

	}

	/** 내 챌린지 목록 조회 Service
	 * @param pInfo
	 * @param sort
	 * @param memId
	 * @return list
	 * @throws Exception
	 */
	public List<Challenge> selectChallengeList(PageInfo pInfo, String sort, String memId) throws Exception {
		Connection conn = getConnection();
		
		 sort = sort == null ? "" : sort;
		

		List<Challenge> list = dao.selectChallengeList(conn, pInfo, memId);  
		
		close(conn);
		return list;
	}

	/** 썸네일 목록 조회 Service
	 * @param pInfo
	 * @return fList
	 * @throws Exception
	 */
	public List<Attachment> selectThumbnailList(PageInfo pInfo) throws Exception {

	      Connection conn = getConnection();
	      
	      List<Attachment> fList = dao.selectThumbnailList(conn, pInfo);
	      
	      close(conn);
	      
	      return fList;
	}

	/** 챌린지 인증게시글 계산 Service
	 * @param cp
	 * @param memId
	 * @return pInfo
	 * @throws Exception
	 */
	public PageInfo getcrtfdPageInfo(String cp, String memId) throws Exception{
		Connection conn = getConnection();
		int currentPage = cp == null ? 1 : Integer.parseInt(cp);
		int listCount = dao.getcrtfdPageInfo(conn, memId); 
		close(conn);
		return new PageInfo(currentPage, listCount);
	}

	/** 챌린지 인증게시글 조회 Service
	 * @param pInfo
	 * @param cn
	 * @param memId
	 * @return 
	 * @throws Exception
	 */
	public List<ChallengeCrtfd> selectcrtfdList(PageInfo pInfo, String cn, String memId) throws Exception{
		Connection conn = getConnection();
		List<ChallengeCrtfd> bList = dao.selectcrtfdList(conn, pInfo, cn, memId);
		close(conn);
		return bList;
	}

	/** 마이페이지 메인 내가 쓴 게시글
	 * @param memId
	 * @return bList
	 * @throws Exception
	 */
	public List<Board> selectMainBoard(String memId) throws Exception {
		Connection conn = getConnection();
		List<Board> bList = dao.selectMainBoard(conn, memId);
		close(conn);
		return bList;
	}

	/** 마이페이지 메인 내가 쓴 댓글
	 * @param memNo
	 * @return cList
	 * @throws Exception
	 */
	public List<Comment> selectMainComment(String memId) throws Exception{
		Connection conn = getConnection();
		List<Comment> cList = dao.selectMainComment(conn, memId);
		close(conn);
		return cList;
	}

	/** 마이페이지 역대 챌린지 조회
	 * @param memNo
	 * @return acList
	 * @throws Exception
	 */
	public List<ChallengeCrtfd> selectMainacList(String memId) throws Exception {
		Connection conn = getConnection();
		List<ChallengeCrtfd> acList = dao.selectMainacList(conn, memId);
		close(conn);
		return acList;
	}

	/** 현재 진행중인 챌린지 조회  Service
	 * @param memId
	 * @return nc
	 * @throws Exception
	 */
	public Challenge nowChallenge(String memId) throws Exception{
		Connection conn = getConnection();
		Challenge nc = dao.nowChallenge(conn, memId);
		close(conn);
		return nc;
	}

	/** 현재 참여중인 챌린지 썸네일 조회 Service
	 * @param memId
	 * @return thumbnail
	 * @throws Exception
	 */
	public Attachment nowThumbnail(String memId) throws Exception {
		Connection conn = getConnection();
		Attachment thumbnail = dao.nowThumbnail(conn, memId);
		close(conn);
		return thumbnail;
	}

	/** 참여중인 챌린지 달성률
	 * @param memNo
	 * @param challengeNo
	 * @return result
	 * @throws Exception
	 */
	public int progressBar(int memNo, int challengeNo) throws Exception{
		Connection conn = getConnection();
		int result = dao.progressBar(conn, memNo, challengeNo);
		close(conn);
		return result;
	}





	

}
