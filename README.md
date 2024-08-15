# 유레카에서 진행한 개인 미니 프로젝트 입니다     
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
mysql connector jar 파일 Classpath에 추가    

### 1. 회원 등록할 때는 장르에 있는 데이터를 가져와 콤보박스를 선택해서 즐겨찾기에 저장된다.    
![캡처](https://github.com/user-attachments/assets/0a151f1a-265c-45fc-b7c4-cb567b3a1ff7)
### 2. 장르  
![캡처2](https://github.com/user-attachments/assets/f0f38b49-6d42-49bf-accb-0f69f4fa048d)
### 3. 즐겨찾기는 회원 등록 때 설정되고 수정 및 삭제도 회원 관리에서 하도록 목록만 볼 수 있게 만들었다. 
![캡처4](https://github.com/user-attachments/assets/8e727a07-fac4-4704-a05a-b6c1244ee611)
### 4. 앨범은 데이터에 있는 아티스트와 장르를 선택해서 등록할 수 있게 만들었다.
![캡처3](https://github.com/user-attachments/assets/52a14870-8a41-4c0e-a63c-4a8fc2376c8f)
### 5. 아티스트
![캡처5](https://github.com/user-attachments/assets/13b6ce37-18b8-4b60-9b97-599207b7f888)

## 개선사항
외래키에 대해 더 공부해야되고    
다른 사람들 발표를 보니 StringBuilder 사용, 디자인 패턴 사용 등 다양한 방법이 있었다.    
여러 개 관리를 똑같이만 만들었는데 하나를 잡고 다양한 방법을 써봐야겠다.    
추가적으로 설계를 어떻게 구현하고 백엔드적으로 어떤 방법이 더 효율적인 지 고민하는 시간을 많이 가져야겠다.
