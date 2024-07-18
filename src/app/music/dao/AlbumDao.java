package app.music.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import app.music.common.DBManager;
import app.music.dto.Album;

public class AlbumDao {
    // 아트스트 이름과 장르 이름을 보여주고 싶어서 join 했습니다.
    public List<Album> listAlbums(){
        List<Album> list = new ArrayList<>();
        String sql = "select a.album_id, a.artist_id, a.genre_id, a.album_name, a.release_date, ar.artist_name, g.genre_name " 
                   + "from album a " 
                   + "join artist ar on a.artist_id = ar.artist_id "
                   + "join genre g on a.genre_id = g.genre_id ";
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = DBManager.getConnection();			
			pstmt = con.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
            while (rs.next()) {
                Album album = new Album(
                    rs.getInt("album_id"),
                    rs.getInt("artist_id"),
                    rs.getInt("genre_id"),
                    rs.getString("album_name"),
                    rs.getDate("release_date"),
                    rs.getString("artist_name"),
                    rs.getString("genre_name")
                );
                list.add(album);
            }
            
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			DBManager.releaseConnection(rs, pstmt, con);
		}
        return list;
    }
    // 아트스트 이름 또는 장르 이름 또는 앨범 이름 검색
    public List<Album> listAlbums(String searchWord){
        List<Album> list = new ArrayList<>();
        String sql = "select a.album_id, a.artist_id, a.genre_id, a.album_name, a.release_date, ar.artist_name, g.genre_name " 
                   + "from album a " 
                   + "join artist ar on a.artist_id = ar.artist_id "
                   + "join genre g on a.genre_id = g.genre_id "
                   + "where a.album_name LIKE ? or g.genre_name like ? or ar.artist_name like ? ";
        
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = DBManager.getConnection();			
			pstmt = con.prepareStatement(sql);
            pstmt.setString(1, "%" + searchWord + "%");
            pstmt.setString(2, "%" + searchWord + "%");
            pstmt.setString(3, "%" + searchWord + "%");
            
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Album album = new Album(
                	rs.getInt("album_id"),
                	rs.getInt("artist_id"),
                	rs.getInt("genre_id"),
                	rs.getString("album_name"),
                	rs.getDate("release_date"),
                	rs.getString("artist_name"),
                	rs.getString("genre_name")
                );
                list.add(album);
            }
            
            
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			DBManager.releaseConnection(rs, pstmt, con);
		}
		
		return list;
    }

    public int insertAlbum(Album newAlbum) {
    	int ret = -1;
        String sql = "INSERT INTO album (artist_id, genre_id, album_name, release_date) VALUES (?, ?, ?, ?)";      
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = DBManager.getConnection();			
			pstmt = con.prepareStatement(sql);
			
            pstmt.setInt(1, newAlbum.getArtist_id());
            pstmt.setInt(2, newAlbum.getGenre_id());
            pstmt.setString(3, newAlbum.getAlbum_name());
            pstmt.setDate(4, newAlbum.getRelease_date());

            ret = pstmt.executeUpdate();

		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			DBManager.releaseConnection(pstmt, con);
		}

        return ret;
    }

    public Album getAlbumById(Integer albumId) {
        Album album = null;
        String sql = "select a.album_id, a.artist_id, a.genre_id, a.album_name, a.release_date, ar.artist_name, g.genre_name "
                   + "from album a "
                   + "join artist ar on a.artist_id = ar.artist_id "
                   + "join genre g on a.genre_id = g.genre_id "
                   + "where a.album_id = ?";
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = DBManager.getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, albumId);

            rs = pstmt.executeQuery();
            if (rs.next()) {
                album = new Album(
                    rs.getInt("album_id"),
                    rs.getInt("artist_id"),
                    rs.getInt("genre_id"),
                    rs.getString("album_name"),
                    rs.getDate("release_date"),
                    rs.getString("artist_name"),
                    rs.getString("genre_name")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBManager.releaseConnection(rs, pstmt, con);
        }

        return album;
    }

    public int updateAlbum(Album album) {
        int ret = -1;
        String sql = "UPDATE album SET artist_id = ?, genre_id = ?, album_name = ?, release_date = ? WHERE album_id = ?";
        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con = DBManager.getConnection();
            pstmt = con.prepareStatement(sql);

            pstmt.setInt(1, album.getArtist_id());
            pstmt.setInt(2, album.getGenre_id());
            pstmt.setString(3, album.getAlbum_name());
            pstmt.setDate(4, album.getRelease_date());
            pstmt.setInt(5, album.getAlbum_id());

            ret = pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBManager.releaseConnection(pstmt, con);
        }

        return ret;
    }

    public int deleteAlbum(Integer albumId) {
        int ret = -1;
        String sql = "DELETE FROM album WHERE album_id = ?";
        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con = DBManager.getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, albumId);

            ret = pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBManager.releaseConnection(pstmt, con);
        }

        return ret;
    }
}
