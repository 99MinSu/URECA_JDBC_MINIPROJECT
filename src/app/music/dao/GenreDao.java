package app.music.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import app.music.common.DBManager;
import app.music.dto.Genre;

public class GenreDao {

    private List<Genre> genres;

    public GenreDao() {
        this.genres = listGenres();
    }

    public int insertGenre(Genre genre) {
        int ret = -1;
        String sql = "insert into genre (genre_id, genre_name) values (?, ?)";

        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con = DBManager.getConnection();
            pstmt = con.prepareStatement(sql);

            pstmt.setInt(1, genre.getGenre_id());
            pstmt.setString(2, genre.getGenre_name());

            ret = pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBManager.releaseConnection(pstmt, con);
        }

        return ret;
    }

    public int updateGenre(Genre genre) {
        int ret = -1;
        String sql = "update genre set genre_name = ? where genre_id = ?";

        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con = DBManager.getConnection();
            pstmt = con.prepareStatement(sql);

            pstmt.setString(1, genre.getGenre_name());
            pstmt.setInt(2, genre.getGenre_id());

            ret = pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBManager.releaseConnection(pstmt, con);
        }

        return ret;
    }

    public int deleteGenre(int genreId) {
        int ret = -1;
        String sql = "delete from genre where genre_id = ?";

        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con = DBManager.getConnection();
            pstmt = con.prepareStatement(sql);

            pstmt.setInt(1, genreId);

            ret = pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBManager.releaseConnection(pstmt, con);
        }

        return ret;
    }

    public List<Genre> listGenres() {
        List<Genre> list = new ArrayList<>();
        String sql = "select * from genre";

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = DBManager.getConnection();
            pstmt = con.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                Genre genre = new Genre(
                    rs.getInt("genre_id"),
                    rs.getString("genre_name")
                );
                list.add(genre);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBManager.releaseConnection(rs, pstmt, con);
        }

        return list;
    }

    public List<Genre> listGenres(String searchWord) {
        List<Genre> list = new ArrayList<>();
        String sql = "select * from genre where genre_name like ?";

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = DBManager.getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, "%" + searchWord + "%");
            rs = pstmt.executeQuery();

            while (rs.next()) {
                Genre genre = new Genre(
                    rs.getInt("genre_id"),
                    rs.getString("genre_name")
                );
                list.add(genre);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBManager.releaseConnection(rs, pstmt, con);
        }

        return list;
    }

    public Genre detailGenre(int genreId) {
        Genre genre = null;
        String sql = "select * from genre where genre_id = ?";

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = DBManager.getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, genreId);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                genre = new Genre(
                    rs.getInt("genre_id"),
                    rs.getString("genre_name")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBManager.releaseConnection(rs, pstmt, con);
        }

        return genre;
    }

    public Genre getGenreByName(String genreName) {
        for (Genre genre : genres) {
            if (genre.getGenre_name().equals(genreName)) {
                return genre;
            }
        }
        return null;  
    }
}
