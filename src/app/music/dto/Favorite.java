package app.music.dto;

public class Favorite {
    private int member_id;
    private int genre_id;
    private String member_name;
    private String genre_name;

    public Favorite() {}

    public Favorite(int member_id, int genre_id, String member_name, String genre_name) {
        this.member_id = member_id;
        this.genre_id = genre_id;
        this.member_name = member_name;
        this.genre_name = genre_name;
    }
    
    public Favorite(int memberId, int genreId) {
        this.member_id = memberId;
        this.genre_id = genreId;
    }

    public int getMember_id() {
        return member_id;
    }

    public void setMember_id(int member_id) {
        this.member_id = member_id;
    }

    public int getGenre_id() {
        return genre_id;
    }

    public void setGenre_id(int genre_id) {
        this.genre_id = genre_id;
    }

    public String getMember_name() {
        return member_name;
    }

    public void setMember_name(String member_name) {
        this.member_name = member_name;
    }

    public String getGenre_name() {
        return genre_name;
    }

    public void setGenre_name(String genre_name) {
        this.genre_name = genre_name;
    }
}
