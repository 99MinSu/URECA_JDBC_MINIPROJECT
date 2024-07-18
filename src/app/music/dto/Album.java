package app.music.dto;

import java.sql.Date;

public class Album {
    private int album_id;
    private int artist_id;
    private int genre_id;
    private String album_name;
    private Date release_date;
    private String artist_name; 
    private String genre_name; 

    public Album() {}

    public Album(int artist_id, int genre_id, String album_name, Date release_date) {
        this.artist_id = artist_id;
        this.genre_id = genre_id;
        this.album_name = album_name;
        this.release_date = release_date;
    }

    public Album(int album_id, int artist_id, int genre_id, String album_name, Date release_date, String artist_name, String genre_name) {
        this.album_id = album_id;
        this.artist_id = artist_id;
        this.genre_id = genre_id;
        this.album_name = album_name;
        this.release_date = release_date;
        this.artist_name = artist_name;
        this.genre_name = genre_name;
    }

    public int getAlbum_id() {
        return album_id;
    }

    public void setAlbum_id(int album_id) {
        this.album_id = album_id;
    }

    public int getArtist_id() {
        return artist_id;
    }

    public void setArtist_id(int artist_id) {
        this.artist_id = artist_id;
    }

    public int getGenre_id() {
        return genre_id;
    }

    public void setGenre_id(int genre_id) {
        this.genre_id = genre_id;
    }

    public String getAlbum_name() {
        return album_name;
    }

    public void setAlbum_name(String album_name) {
        this.album_name = album_name;
    }

    public Date getRelease_date() {
        return release_date;
    }

    public void setRelease_date(Date release_date) {
        this.release_date = release_date;
    }

    public String getArtist_name() {
        return artist_name;
    }

    public void setArtist_name(String artist_name) {
        this.artist_name = artist_name;
    }

    public String getGenre_name() {
        return genre_name;
    }

    public void setGenre_name(String genre_name) {
        this.genre_name = genre_name;
    }

    @Override
    public String toString() {
        return "Album [album_id=" + album_id + ", artist_id=" + artist_id + ", genre_id=" + genre_id + ", album_name="
                + album_name + ", release_date=" + release_date + "]";
    }
}
