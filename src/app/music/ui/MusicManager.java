package app.music.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MusicManager extends JFrame {
    private JPanel memberPanel;
    private JPanel genrePanel;
    private JPanel favoritePanel;
    private JPanel albumPanel;
    private JPanel artistPanel; // 아티스트 패널 추가
    private JPanel mainPanel;

    public MusicManager() {
        setTitle("음악 관리 프로그램");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create MenuBar
        JMenuBar menuBar = new JMenuBar();

        JMenu memberMenu = new JMenu("회원 관리");
        JMenu genreMenu = new JMenu("장르 관리");
        JMenu favoriteMenu = new JMenu("즐겨찾기 목록");
        JMenu albumMenu = new JMenu("앨범 목록");
        JMenu artistMenu = new JMenu("아티스트 관리"); // 아티스트 관리 메뉴 추가
        
        menuBar.add(memberMenu); // 메뉴바에 멤버 관리 메뉴 추가
        menuBar.add(genreMenu); // 메뉴바에 장르 관리 메뉴 추가
        menuBar.add(favoriteMenu);  // 메뉴바에 즐겨찾기 목록 추가
        menuBar.add(albumMenu);  // 메뉴바에 앨범 목록 메뉴 추가
        menuBar.add(artistMenu); // 메뉴바에 아티스트 관리 메뉴 추가
        setJMenuBar(menuBar);

        // 패널들 생성
        memberPanel = new MemberManager();
        genrePanel = new GenreManager();
        favoritePanel = new FavoriteManager();
        albumPanel = new AlbumManager();
        artistPanel = new ArtistManager(); // 아티스트 패널 추가
        
        // CardLayOut 추가
        mainPanel = new JPanel(new CardLayout());
        mainPanel.add(memberPanel, "Member");
        mainPanel.add(genrePanel, "Genre");
        mainPanel.add(favoritePanel, "Favorite");
        mainPanel.add(albumPanel, "Album");
        mainPanel.add(artistPanel, "Artist"); 
        

        add(mainPanel);
        
        memberMenu.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                showPanel("Member");
            }
        });

        genreMenu.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                showPanel("Genre");
            }
        });

        favoriteMenu.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                showPanel("Favorite");
            }
        });

        albumMenu.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                showPanel("Album");
            }
        });
        
        artistMenu.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                showPanel("Artist");
            }
        });

        showPanel("Member");
    }

    private void showPanel(String panelName) {
        CardLayout cardLayout = (CardLayout) mainPanel.getLayout();
        cardLayout.show(mainPanel, panelName);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MusicManager().setVisible(true);
        });
    }
}
