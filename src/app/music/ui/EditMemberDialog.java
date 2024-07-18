package app.music.ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import app.music.dto.Genre;
import app.music.dto.Member;

public class EditMemberDialog extends JDialog {
    private JTextField memberIdField, usernameField, emailField, phoneField, passwordField;
    private JButton updateButton, deleteButton;
    private JComboBox<String> genreComboBox;

    public EditMemberDialog(MemberManager parent, DefaultTableModel tableModel, int rowIndex) {
        setTitle("회원 수정 대화상자");
        setSize(300, 250);
        setLayout(new BorderLayout());
        setLocationRelativeTo(parent); // 부모 창 가운데 배치

        // 선택된 회원의 회원 ID로 회원 테이블에서 조회
        Integer memberId = (Integer) tableModel.getValueAt(rowIndex, 0);
        Member member = parent.detailMember(memberId);

        // 입력 패널
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(7, 2));

        // 필드
        memberIdField = new JTextField(String.valueOf(memberId)); // 정수 -> 문자열
        memberIdField.setEditable(false);
        usernameField = new JTextField(member.getUsername());
        emailField = new JTextField(member.getEmail());
        phoneField = new JTextField(member.getPhone());
        passwordField = new JTextField(member.getPassword());

        // 장르 콤보박스 설정
        genreComboBox = new JComboBox<>();
        List<Genre> genreList = parent.getGenreDao().listGenres();
        for (Genre genre : genreList) {
            genreComboBox.addItem(genre.getGenre_name());
        }
        
        // 기존 회원의 선호하는 장르를 선택하도록 설정
        Genre favoriteGenre = member.getFavoriteGenre();
        if (favoriteGenre != null) {
            genreComboBox.setSelectedItem(favoriteGenre.getGenre_name());
        }

        // 버튼
        updateButton = new JButton("수정");
        deleteButton = new JButton("삭제");

        // 레이블과 필드 추가
        inputPanel.add(new JLabel("회원 ID"));
        inputPanel.add(memberIdField);
        inputPanel.add(new JLabel("사용자명"));
        inputPanel.add(usernameField);
        inputPanel.add(new JLabel("이메일"));
        inputPanel.add(emailField);
        inputPanel.add(new JLabel("전화번호"));
        inputPanel.add(phoneField);
        inputPanel.add(new JLabel("비밀번호"));
        inputPanel.add(passwordField);
        inputPanel.add(new JLabel("선호하는 장르"));
        inputPanel.add(genreComboBox);

        JPanel buttonPanel = new JPanel();

        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);

        add(inputPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        updateButton.addActionListener(e -> {
            int ret = JOptionPane.showConfirmDialog(this, "수정할까요?", "수정 확인", JOptionPane.YES_NO_OPTION);
            if (ret == JOptionPane.YES_OPTION) {
                String username = usernameField.getText();
                String email = emailField.getText();
                String phone = phoneField.getText();
                String password = passwordField.getText();
                String selectedGenreName = (String) genreComboBox.getSelectedItem();

                // 장르 이름으로부터 Genre 객체
                Genre selectedGenre = parent.getGenreDao().getGenreByName(selectedGenreName);

                // 선택된 회원 정보를 업데이트
                Member updatedMember = new Member(memberId, username, email, phone, password, selectedGenre);
                parent.updateMember(updatedMember);

                // 회원의 선호하는 장르도 업데이트
                parent.updateMemberFavoriteGenre(memberId, selectedGenre);

                dispose(); 
            }
        });

        // 삭제 버튼 액션 리스너
        deleteButton.addActionListener(e -> {
            int ret = JOptionPane.showConfirmDialog(this, "삭제할까요?", "삭제 확인", JOptionPane.YES_NO_OPTION);
            if (ret == JOptionPane.YES_OPTION) {
                parent.deleteMember(memberId); // 회원 삭제
                dispose(); 
            }
        });
    }
}
