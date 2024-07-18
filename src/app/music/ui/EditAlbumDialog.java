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

import app.music.dto.Album;

public class EditAlbumDialog extends JDialog {
    private JTextField albumIdField, albumNameField, artistField, genreField, releaseDateField;
    private JButton updateButton, deleteButton;

    public EditAlbumDialog(AlbumManager parent, DefaultTableModel tableModel, int rowIndex) {
        setTitle("앨범 수정 및 삭제");
        setSize(300, 250);
        setLayout(new BorderLayout());
        setLocationRelativeTo(parent);

        Integer albumId = (Integer) tableModel.getValueAt(rowIndex, 0);
        Album album = parent.detailAlbum(albumId);

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(5, 2));

        albumIdField = new JTextField(String.valueOf(album.getAlbum_id()));
        albumIdField.setEditable(false);
        albumNameField = new JTextField(album.getAlbum_name());
        artistField = new JTextField(album.getArtist_name());
        genreField = new JTextField(album.getGenre_name());
        releaseDateField = new JTextField(String.valueOf(album.getRelease_date()));

        inputPanel.add(new JLabel("앨범 ID"));
        inputPanel.add(albumIdField);
        inputPanel.add(new JLabel("앨범 이름"));
        inputPanel.add(albumNameField);
        inputPanel.add(new JLabel("아티스트 이름"));
        inputPanel.add(artistField);
        inputPanel.add(new JLabel("장르 이름"));
        inputPanel.add(genreField);
        inputPanel.add(new JLabel("발매일"));
        inputPanel.add(releaseDateField);

        updateButton = new JButton("수정");
        deleteButton = new JButton("삭제");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);

        updateButton.addActionListener(e -> {
            int ret = JOptionPane.showConfirmDialog(this, "수정하시겠습니까?", "수정 확인", JOptionPane.YES_NO_OPTION);
            if (ret == JOptionPane.YES_OPTION) {
                String albumName = albumNameField.getText();
                String artistName = artistField.getText();
                String genreName = genreField.getText();
                java.sql.Date releaseDate = java.sql.Date.valueOf(releaseDateField.getText());
                
                Album updatedAlbum = new Album(album.getAlbum_id(), album.getArtist_id(), album.getGenre_id(), albumName, releaseDate, artistName, genreName);
                parent.updateAlbum(updatedAlbum);
                dispose();
            }
        });

        deleteButton.addActionListener(e -> {
            int ret = JOptionPane.showConfirmDialog(this, "정말 삭제하시겠습니까?", "삭제 확인", JOptionPane.YES_NO_OPTION);
            if (ret == JOptionPane.YES_OPTION) {
                parent.deleteAlbum(album.getAlbum_id());
                dispose();
            }
        });

        add(inputPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }
}
