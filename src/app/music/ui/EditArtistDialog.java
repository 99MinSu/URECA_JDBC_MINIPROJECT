package app.music.ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import app.music.dto.Artist;

public class EditArtistDialog extends JDialog {
    private JTextField artistIdField, artistNameField;
    private JButton updateButton, deleteButton;

    public EditArtistDialog(ArtistManager parent, DefaultTableModel tableModel, int rowIndex) {
        setTitle("아티스트 수정 및 삭제");
        setSize(300, 150);
        setLayout(new BorderLayout());
        setLocationRelativeTo(parent);

        Integer artistId = (Integer) tableModel.getValueAt(rowIndex, 0);
        Artist artist = parent.detailArtist(artistId);

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(2, 2));

        artistIdField = new JTextField(String.valueOf(artist.getArtist_id()));
        artistIdField.setEditable(false);
        artistNameField = new JTextField(artist.getArtist_name());

        inputPanel.add(new JLabel("아티스트 ID"));
        inputPanel.add(artistIdField);
        inputPanel.add(new JLabel("아티스트 이름"));
        inputPanel.add(artistNameField);

        updateButton = new JButton("수정");
        deleteButton = new JButton("삭제");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);

        updateButton.addActionListener(e -> {
            int ret = JOptionPane.showConfirmDialog(this, "수정하시겠습니까?", "수정 확인", JOptionPane.YES_NO_OPTION);
            if (ret == JOptionPane.YES_OPTION) {
                String artistName = artistNameField.getText();
                parent.updateArtist(new Artist(artist.getArtist_id(), artistName));
                dispose();
            }
        });

        deleteButton.addActionListener(e -> {
            int ret = JOptionPane.showConfirmDialog(this, "정말 삭제하시겠습니까?", "삭제 확인", JOptionPane.YES_NO_OPTION);
            if (ret == JOptionPane.YES_OPTION) {
                parent.deleteArtist(artist.getArtist_id());
                dispose();
            }
        });

        add(inputPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }
}
