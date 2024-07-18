package app.music.ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import app.music.dto.Genre;

public class EditGenreDialog extends JDialog {
    private JTextField genreIdField, genreNameField;
    private JButton updateButton, deleteButton;

    public EditGenreDialog(GenreManager parent, DefaultTableModel tableModel, int rowIndex) {
        setTitle("장르 수정 및 삭제");
        setSize(300, 150);
        setLayout(new BorderLayout());
        setLocationRelativeTo(parent);

        Integer genreId = (Integer) tableModel.getValueAt(rowIndex, 0);
        Genre genre = parent.detailGenre(genreId);

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(2, 2));

        genreIdField = new JTextField(String.valueOf(genre.getGenre_id()));
        genreIdField.setEditable(false);
        genreNameField = new JTextField(genre.getGenre_name());

        inputPanel.add(new JLabel("장르 ID"));
        inputPanel.add(genreIdField);
        inputPanel.add(new JLabel("장르 이름"));
        inputPanel.add(genreNameField);

        updateButton = new JButton("수정");
        deleteButton = new JButton("삭제");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);

        updateButton.addActionListener(e -> {
            int ret = JOptionPane.showConfirmDialog(this, "수정하시겠습니까?", "수정 확인", JOptionPane.YES_NO_OPTION);
            if (ret == JOptionPane.YES_OPTION) {
                String genreName = genreNameField.getText();
                parent.updateGenre(new Genre(genre.getGenre_id(), genreName));
                dispose();
            }
        });

        deleteButton.addActionListener(e -> {
            int ret = JOptionPane.showConfirmDialog(this, "정말 삭제하시겠습니까?", "삭제 확인", JOptionPane.YES_NO_OPTION);
            if (ret == JOptionPane.YES_OPTION) {
                parent.deleteGenre(genre.getGenre_id());
                dispose();
            }
        });

        add(inputPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }
}
