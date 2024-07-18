# 유레카에서 진행한 미니 개인 프로젝트 입니다     
### 음악 관리 시스템 JDBC + SWING 사용해서 CRUD 구현   

### ERD
![Music](https://github.com/user-attachments/assets/ab44df23-b327-4e40-8fd1-d8dd25faff42)


### MySQL 데이터베이스 생성
```sql
CREATE TABLE Artist (
    artist_id BIGINT PRIMARY KEY,
    artist_name VARCHAR(30) NOT NULL
);
CREATE TABLE Genre (
    genre_id BIGINT PRIMARY KEY,
    genre_name VARCHAR(30) NOT NULL
);
CREATE TABLE Album (
    album_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    artist_id BIGINT,
    genre_id BIGINT,
    album_name VARCHAR(30) NOT NULL,
    release_date DATE,
    FOREIGN KEY (artist_id) REFERENCES Artist(artist_id),
    FOREIGN KEY (genre_id) REFERENCES Genre(genre_id)
);

CREATE TABLE Member (
    member_id BIGINT PRIMARY KEY,
    username VARCHAR(30) NOT NULL,
    email VARCHAR(40) NOT NULL,
    phone VARCHAR(30) NOT NULL,
    password VARCHAR(30) NOT NULL
);

CREATE TABLE Favorite (
    member_id BIGINT,
    genre_id BIGINT,
    PRIMARY KEY (member_id, genre_id),
    FOREIGN KEY (member_id) REFERENCES Member(member_id),
    FOREIGN KEY (genre_id) REFERENCES Genre(genre_id)
);
```
### 데이터 베이스 연결 부분
![캡처](https://github.com/user-attachments/assets/f570c2ce-b1d3-4ea3-abd5-df2617c1d508)

## 개선사항
수정하고 추가할 부분 많음
