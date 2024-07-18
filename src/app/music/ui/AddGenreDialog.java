package app.music.ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import app.music.dto.Genre;

public class AddGenreDialog extends JDialog {
    private JTextField genreIdField, genreNameField;
    private JButton addButton;

    public AddGenreDialog(GenreManager parent, DefaultTableModel tableModel) {
        setTitle("장르 추가");
        setSize(300, 150);
        setLayout(new BorderLayout());
        setLocationRelativeTo(parent);

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(2, 2));

        genreIdField = new JTextField();
        genreNameField = new JTextField();

        inputPanel.add(new JLabel("장르 ID"));
        inputPanel.add(genreIdField);
        inputPanel.add(new JLabel("장르 이름"));
        inputPanel.add(genreNameField);

        addButton = new JButton("추가");
        addButton.addActionListener(e -> {
            int genreId = Integer.parseInt(genreIdField.getText());
            String genreName = genreNameField.getText();

            parent.insertGenre(new Genre(genreId, genreName));

            dispose();
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);

        add(inputPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }
}
