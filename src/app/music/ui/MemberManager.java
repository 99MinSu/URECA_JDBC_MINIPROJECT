package app.music.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import app.music.dao.FavoriteDao;
import app.music.dao.GenreDao;
import app.music.dao.MemberDao;
import app.music.dto.Favorite;
import app.music.dto.Genre;
import app.music.dto.Member;

public class MemberManager extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton searchButton, addButton, editButton, listButton;
    private JTextField searchWordField;

    private MemberDao memberDao = new MemberDao();
    private GenreDao genreDao;
    private FavoriteDao favoriteDao;  // FavoriteDao 필드 추가
    
    public MemberManager() {
        setLayout(new BorderLayout());
        this.genreDao = new GenreDao();
        this.favoriteDao = new FavoriteDao();
        tableModel = new DefaultTableModel(new Object[]{"Member ID", "Username", "Email", "Phone", "Password"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(tableModel);

        listMembers();

        Dimension textFieldSize = new Dimension(400, 28);
        searchWordField = new JTextField();
        searchWordField.setPreferredSize(textFieldSize);

        searchButton = new JButton("검색");

        JPanel searchPanel = new JPanel();
        searchPanel.add(new JLabel("사용자명 검색"));
        searchPanel.add(searchWordField);
        searchPanel.add(searchButton);

        addButton = new JButton("추가");
        editButton = new JButton("수정 & 삭제");
        listButton = new JButton("목록");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(listButton);

        add(searchPanel, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        searchButton.addActionListener(e -> {
            String searchWord = searchWordField.getText();
            if (!searchWord.isBlank()) {
                listMembers(searchWord);
            }
        });

        addButton.addActionListener(e -> {
            AddMemberDialog addDialog = new AddMemberDialog(this, tableModel);
            addDialog.setVisible(true);
        });

        editButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow >= 0) {
                EditMemberDialog editDialog = new EditMemberDialog(this, tableModel, selectedRow);
                editDialog.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "회원을 선택하세요.");
            }
        });

        listButton.addActionListener(e -> listMembers());

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int selectedRow = table.getSelectedRow();
                    if (selectedRow >= 0) {
                        EditMemberDialog editDialog = new EditMemberDialog(MemberManager.this, tableModel, selectedRow);
                        editDialog.setVisible(true);
                    }
                }
            }
        });
    }

    private void clearTable() {
        tableModel.setRowCount(0);
    }

    private void listMembers() {
        clearTable();
        List<Member> memberList = memberDao.listMembers();
        for (Member member : memberList) {
            tableModel.addRow(new Object[]{member.getMember_id(), member.getUsername(), member.getEmail(), member.getPhone(), member.getPassword()});
        }
    }

    private void listMembers(String searchWord) {
        clearTable();
        List<Member> memberList = memberDao.listMembers(searchWord);
        for (Member member : memberList) {
            tableModel.addRow(new Object[]{member.getMember_id(), member.getUsername(), member.getEmail(), member.getPhone(), member.getPassword()});
        }
    }
    
    Member detailMember(int memberId) {
        return memberDao.detailMember(memberId);
    }

    void insertMember(Member member) {
        int ret = memberDao.insertMember(member);
        if (ret == 1) {
            listMembers();
        }
    }

    void updateMember(Member member) {
        int ret = memberDao.updateMember(member);
        if (ret == 1) {
            listMembers();
        }
    }

    void deleteMember(int memberId) {
        
        favoriteDao.deleteFavoritesByMemberId(memberId); // 외래키로 인해 favorite 삭제 후 멤버 삭제
        int ret = memberDao.deleteMember(memberId);
        if (ret == 1) {
            listMembers();
        }
    }

    void updateMemberFavoriteGenre(int memberId, Genre genre) {
        Favorite favorite = favoriteDao.getFavoriteByMemberId(memberId);
        if (favorite != null) {
            favorite.setGenre_id(genre.getGenre_id());
            favoriteDao.updateFavorite(favorite);
        } else {
            favorite = new Favorite();
            favorite.setMember_id(memberId);
            favorite.setGenre_id(genre.getGenre_id());
            favoriteDao.insertFavorite(favorite);
        }
    }

    public GenreDao getGenreDao() {
        return genreDao;
    }

    public FavoriteDao getFavoriteDao() {
        return favoriteDao;
    }

    public void insertFavorite(Favorite favorite) {
        favoriteDao.insertFavorite(favorite);
    }

}
