package app.music.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import app.music.common.DBManager;
import app.music.dto.Member;

public class MemberDao {
	
	public int insertMember(Member member) {
		int ret = -1;
		String sql = "insert into member (member_id, username, email, phone, password) values (?, ?, ?, ?, ?)";
		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = DBManager.getConnection();			
			pstmt = con.prepareStatement(sql);
			
			pstmt.setInt(1, member.getMember_id());
			pstmt.setString(2, member.getUsername());
			pstmt.setString(3, member.getEmail());
			pstmt.setString(4, member.getPhone());
			pstmt.setString(5, member.getPassword());
			
			ret = pstmt.executeUpdate();
			
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			DBManager.releaseConnection(pstmt, con);
		}
		
		return ret;
	}
	
	public int updateMember(Member member) {
		int ret = -1;
		String sql = "update member set username = ?, email = ?, phone = ?, password = ? where member_id = ?";
		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = DBManager.getConnection();			
			pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, member.getUsername());
			pstmt.setString(2, member.getEmail());
			pstmt.setString(3, member.getPhone());
			pstmt.setString(4, member.getPassword());
			pstmt.setInt(5, member.getMember_id());
			
			ret = pstmt.executeUpdate();
			
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			DBManager.releaseConnection(pstmt, con);
		}
		
		return ret;
	}

	public int deleteMember(int memberId) {
		int ret = -1;
		String sql = "delete from member where member_id = ?";
		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = DBManager.getConnection();			
			pstmt = con.prepareStatement(sql);
			
			pstmt.setInt(1, memberId);
			
			ret = pstmt.executeUpdate();
			
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			DBManager.releaseConnection(pstmt, con);
		}
		
		return ret;
	}

	public List<Member> listMembers(){
		List<Member> list = new ArrayList<>();
		String sql = "select * from member";
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = DBManager.getConnection();			
			pstmt = con.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			while(rs.next()) {
				Member member = new Member(
					rs.getInt("member_id"),
					rs.getString("username"),
					rs.getString("email"),
					rs.getString("phone"),
					rs.getString("password")
				);
				list.add(member);
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			DBManager.releaseConnection(rs, pstmt, con);
		}
		
		return list;
	}
	
	public List<Member> listMembers(String searchWord){
		List<Member> list = new ArrayList<>();
		
		String sql = "select * from member where username like ?; ";
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = DBManager.getConnection();			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, "%" + searchWord + "%");
			
			rs = pstmt.executeQuery();
			while(rs.next()) {
				Member member = new Member(
					rs.getInt("member_id"),
					rs.getString("username"),
					rs.getString("email"),
					rs.getString("phone"),
					rs.getString("password")
				);
				list.add(member);
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			DBManager.releaseConnection(rs, pstmt, con);
		}
		
		return list;
	}
	
	public Member detailMember(int memberId) {
		Member member = null;
		String sql = "select * from member where member_id = ?";
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = DBManager.getConnection();			
			pstmt = con.prepareStatement(sql);
			
			pstmt.setInt(1, memberId);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				member = new Member(
					rs.getInt("member_id"),
					rs.getString("username"),
					rs.getString("email"),
					rs.getString("phone"),
					rs.getString("password")
				);
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			DBManager.releaseConnection(rs, pstmt, con);
		}
		
		return member;
	}
}
