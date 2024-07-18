package app.music.ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

import app.music.dto.Favorite;
import app.music.dto.Genre;
import app.music.dto.Member;

public class AddMemberDialog extends JDialog {
    private JTextField memberIdField, usernameField, emailField, phoneField, passwordField;
    private JButton addButton;
    private JComboBox<String> genreComboBox;

    public AddMemberDialog(MemberManager parent, DefaultTableModel tableModel) {
        setTitle("회원 등록 대화상자");
        setSize(300, 250);
        setLayout(new BorderLayout());
        setLocationRelativeTo(parent);

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(7, 2));

        memberIdField = new JTextField();
        usernameField = new JTextField();
        emailField = new JTextField();
        phoneField = new JTextField();
        passwordField = new JTextField();

        genreComboBox = new JComboBox<>();
        List<Genre> genreList = parent.getGenreDao().listGenres();
        for (Genre genre : genreList) {
            genreComboBox.addItem(genre.getGenre_name());
        }

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
        addButton = new JButton("추가");
        buttonPanel.add(addButton);

        add(inputPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        addButton.addActionListener(e -> {
            int memberId = Integer.parseInt(memberIdField.getText());
            String username = usernameField.getText();
            String email = emailField.getText();
            String phone = phoneField.getText();
            String password = passwordField.getText();
            String selectedGenreName = (String) genreComboBox.getSelectedItem();

            Genre selectedGenre = parent.getGenreDao().getGenreByName(selectedGenreName);

            Member newMember = new Member(memberId, username, email, phone, password);
            newMember.setFavoriteGenre(selectedGenre);

            parent.insertMember(newMember);

            parent.insertFavorite(new Favorite(memberId, selectedGenre.getGenre_id()));
            

            dispose();
        });
    }
}
