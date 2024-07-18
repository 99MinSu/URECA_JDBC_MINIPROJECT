package app.music.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import app.music.common.DBManager;
import app.music.dto.Favorite;

public class FavoriteDao {

    public int insertFavorite(Favorite favorite) {
        int ret = -1;
        String sql = "insert into favorite (member_id, genre_id) values (?, ?)";

        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con = DBManager.getConnection();
            pstmt = con.prepareStatement(sql);

            pstmt.setInt(1, favorite.getMember_id());
            pstmt.setInt(2, favorite.getGenre_id());

            ret = pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBManager.releaseConnection(pstmt, con);
        }

        return ret;
    }

    public List<Favorite> listFavorites() {
        List<Favorite> list = new ArrayList<>();
        String sql = "select f.member_id, f.genre_id, m.username, g.genre_name "
                   + "from favorite f "
                   + "join member m on f.member_id = m.member_id "
                   + "join genre g on f.genre_id = g.genre_id";

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = DBManager.getConnection();
            pstmt = con.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                Favorite favorite = new Favorite();
                favorite.setMember_id(rs.getInt("member_id"));
                favorite.setGenre_id(rs.getInt("genre_id"));
                favorite.setMember_name(rs.getString("username"));
                favorite.setGenre_name(rs.getString("genre_name"));
                list.add(favorite);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBManager.releaseConnection(rs, pstmt, con);
        }

        return list;
    }

    public Favorite getFavoriteByMemberAndGenre(int memberId, int genreId) {
        Favorite favorite = null;
        String sql = "select f.member_id, f.genre_id, m.username, g.genre_name "
                   + "from favorite f "
                   + "join member m on f.member_id = m.member_id "
                   + "join genre g on f.genre_id = g.genre_id "
                   + "where f.member_id = ? and f.genre_id = ?";

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = DBManager.getConnection();
            pstmt = con.prepareStatement(sql);

            pstmt.setInt(1, memberId);
            pstmt.setInt(2, genreId);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                favorite = new Favorite();
                favorite.setMember_id(rs.getInt("member_id"));
                favorite.setGenre_id(rs.getInt("genre_id"));
                favorite.setMember_name(rs.getString("username"));
                favorite.setGenre_name(rs.getString("genre_name"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBManager.releaseConnection(rs, pstmt, con);
        }

        return favorite;
    }

    public List<Favorite> listFavorites(String searchWord) {
        List<Favorite> list = new ArrayList<>();
        String sql = "select f.member_id, f.genre_id, m.username, g.genre_name "
                   + "from favorite f "
                   + "join member m on f.member_id = m.member_id "
                   + "join genre g on f.genre_id = g.genre_id "
                   + "where m.username like ? or g.genre_name like ?";

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = DBManager.getConnection();
            pstmt = con.prepareStatement(sql);

            pstmt.setString(1, "%" + searchWord + "%");
            pstmt.setString(2, "%" + searchWord + "%");
            rs = pstmt.executeQuery();

            while (rs.next()) {
                Favorite favorite = new Favorite();
                favorite.setMember_id(rs.getInt("member_id"));
                favorite.setGenre_id(rs.getInt("genre_id"));
                favorite.setMember_name(rs.getString("username"));
                favorite.setGenre_name(rs.getString("genre_name"));
                list.add(favorite);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBManager.releaseConnection(rs, pstmt, con);
        }

        return list;
    }

    public int deleteFavoritesByMemberId(int memberId) {
        int ret = -1;
        String sql = "delete from favorite where member_id = ?";

        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con = DBManager.getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, memberId);

            ret = pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBManager.releaseConnection(pstmt, con);
        }

        return ret;
    }

    public int deleteFavoritesByGenreId(int genreId) {
        int ret = -1;
        String sql = "delete from favorite where genre_id = ?";

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

    public Favorite getFavoriteByMemberId(int memberId) {
        Favorite favorite = null;
        String sql = "select f.member_id, f.genre_id, m.username, g.genre_name "
                   + "from favorite f "
                   + "join member m on f.member_id = m.member_id "
                   + "join genre g on f.genre_id = g.genre_id "
                   + "where f.member_id = ?";

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = DBManager.getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, memberId);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                favorite = new Favorite();
                favorite.setMember_id(rs.getInt("member_id"));
                favorite.setGenre_id(rs.getInt("genre_id"));
                favorite.setMember_name(rs.getString("username"));
                favorite.setGenre_name(rs.getString("genre_name"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBManager.releaseConnection(rs, pstmt, con);
        }

        return favorite;
    }

    public void updateFavorite(Favorite favorite) {
        String sql = "update favorite set genre_id = ? where member_id = ?";

        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con = DBManager.getConnection();
            pstmt = con.prepareStatement(sql);

            pstmt.setInt(1, favorite.getGenre_id());
            pstmt.setInt(2, favorite.getMember_id());

            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBManager.releaseConnection(pstmt, con);
        }
    }
}
