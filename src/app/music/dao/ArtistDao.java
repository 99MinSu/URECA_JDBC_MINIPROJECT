package app.music.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import app.music.common.DBManager;
import app.music.dto.Artist;

public class ArtistDao {
    private List<Artist> artists;
    
    public ArtistDao() {
        // 생성자에서 artists 리스트 초기화
        this.artists = listArtists(); // 혹은 다른 방법으로 초기화
    }

    public int insertArtist(Artist artist) {
        int ret = -1;
        String sql = "insert into artist (artist_id, artist_name) values (?, ?)";

		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = DBManager.getConnection();			
			pstmt = con.prepareStatement(sql);

            pstmt.setInt(1, artist.getArtist_id());
            pstmt.setString(2, artist.getArtist_name());

            ret = pstmt.executeUpdate();

		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			DBManager.releaseConnection(pstmt, con);
		}

        return ret;
    }

    public int updateArtist(Artist artist) {
        int ret = -1;
        String sql = "update artist set artist_name = ? where artist_id = ?";

		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = DBManager.getConnection();			
			pstmt = con.prepareStatement(sql);

            ret = pstmt.executeUpdate();

		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			DBManager.releaseConnection(pstmt, con);
		}
		
		return ret;
    }

    public int deleteArtist(int artistId) {
        int ret = -1;
        String sql = "delete from artist where artist_id = ?";

		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = DBManager.getConnection();			
			pstmt = con.prepareStatement(sql);

            pstmt.setInt(1, artistId);

            ret = pstmt.executeUpdate();

		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			DBManager.releaseConnection(pstmt, con);
		}
		
		return ret;
    }

    public List<Artist> listArtists() {
        List<Artist> list = new ArrayList<>();
        String sql = "select * from artist";

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = DBManager.getConnection();			
			pstmt = con.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
            while (rs.next()) {
                Artist artist = new Artist(
                    rs.getInt("artist_id"),
                    rs.getString("artist_name")
                );
                list.add(artist);
            }

		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			DBManager.releaseConnection(rs, pstmt, con);
		}
		
		return list;
    }

    public List<Artist> listArtists(String searchWord) {
        List<Artist> list = new ArrayList<>();
        String sql = "select * from artist where artist_name like ?";

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = DBManager.getConnection();			
			pstmt = con.prepareStatement(sql);
            pstmt.setString(1, "%" + searchWord + "%");

            rs = pstmt.executeQuery();
            while (rs.next()) {
                Artist artist = new Artist(
                		rs.getInt("artist_id"),
                		rs.getString("artist_name")
                );
                list.add(artist);               
            }

		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			DBManager.releaseConnection(rs, pstmt, con);
		}
		
		return list;
	}

    public Artist detailArtist(int artistId) {
        Artist artist = null;
        String sql = "select * from artist where artist_id = ?";

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = DBManager.getConnection();			
			pstmt = con.prepareStatement(sql);

            pstmt.setInt(1, artistId);
            rs = pstmt.executeQuery();
             if (rs.next()) {
                artist = new Artist(
                    rs.getInt("artist_id"),
                    rs.getString("artist_name")
                );
                
            }

		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			DBManager.releaseConnection(rs, pstmt, con);
		}
		
        return artist;
    }
    
    public Artist getArtistByName(String artistName) {
        for (Artist artist : artists) {
            if (artist.getArtist_name().equals(artistName)) {
                return artist;
            }
        }
        return null;  // 해당 아티스트가 없는 경우 null 반환 혹은 예외 처리
    }
}
