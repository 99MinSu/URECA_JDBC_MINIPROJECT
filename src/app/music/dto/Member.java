package app.music.dto;

public class Member {
    private Integer member_id;
    private String username;
    private String email;
    private String phone;
    private String password;
	private Genre favoriteGenre;

    public Member() {}

    public Member(Integer member_id, String username, String email, String phone, String password) {
        this.member_id = member_id;
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.password = password;
    }
    
    public Member(Integer member_id, String username, String email, String phone, String password, Genre favoriteGenre) {
        this.member_id = member_id;
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.favoriteGenre = favoriteGenre;
    }

    public Integer getMember_id() {
        return member_id;
    }

    public void setMember_id(Integer member_id) {
        this.member_id = member_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public Genre getFavoriteGenre() {
        return favoriteGenre;
    }
    
    public void setFavoriteGenre(Genre favoriteGenre) {
        this.favoriteGenre = favoriteGenre;
    }
    
	@Override
	public String toString() {
		return "Member [member_id=" + member_id + ", username=" + username + ", email=" + email + ", phone=" + phone
				+ ", password=" + password + "]";
	}	
}