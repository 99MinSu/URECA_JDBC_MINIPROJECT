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

import app.music.dao.AlbumDao;
import app.music.dao.ArtistDao;
import app.music.dao.GenreDao;
import app.music.dto.Album;
import app.music.dto.Artist;
import app.music.dto.Genre;

public class AddAlbumDialog extends JDialog {
    private JTextField albumNameField, releaseDateField;
    private JComboBox<String> artistComboBox, genreComboBox;
    private JButton addButton;

    private AlbumDao albumDao;
    private ArtistDao artistDao;
    private GenreDao genreDao;

    public AddAlbumDialog(AlbumManager parent, DefaultTableModel tableModel) {
        setTitle("앨범 추가 대화상자");
        setSize(300, 250);
        setLayout(new BorderLayout());
        setLocationRelativeTo(parent);

        albumDao = new AlbumDao();
        artistDao = new ArtistDao();
        genreDao = new GenreDao();

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(4, 2));

        albumNameField = new JTextField();
        releaseDateField = new JTextField();

        artistComboBox = new JComboBox<>();
        List<Artist> artistList = artistDao.listArtists();
        for (Artist artist : artistList) {
            artistComboBox.addItem(artist.getArtist_name());
        }

        genreComboBox = new JComboBox<>();
        List<Genre> genreList = parent.getGenreDao().listGenres();
        for (Genre genre : genreList) {
            genreComboBox.addItem(genre.getGenre_name());
        }

        inputPanel.add(new JLabel("아티스트"));
        inputPanel.add(artistComboBox);
        inputPanel.add(new JLabel("장르"));
        inputPanel.add(genreComboBox);
        inputPanel.add(new JLabel("앨범 이름"));
        inputPanel.add(albumNameField);
        inputPanel.add(new JLabel("발매일 (yyyy-MM-dd)"));
        inputPanel.add(releaseDateField);


        JPanel buttonPanel = new JPanel();
        addButton = new JButton("추가");
        buttonPanel.add(addButton);

        add(inputPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        addButton.addActionListener(e -> {
            String albumName = albumNameField.getText();
            String releaseDateStr = releaseDateField.getText();
            String selectedArtistName = (String) artistComboBox.getSelectedItem();
            String selectedGenreName = (String) genreComboBox.getSelectedItem();

            Artist selectedArtist = artistDao.getArtistByName(selectedArtistName);
            Genre selectedGenre = parent.getGenreDao().getGenreByName(selectedGenreName);

            Album newAlbum = new Album();
            newAlbum.setAlbum_name(albumName);
            newAlbum.setRelease_date(java.sql.Date.valueOf(releaseDateStr));
            newAlbum.setArtist_id(selectedArtist.getArtist_id());
            newAlbum.setGenre_id(selectedGenre.getGenre_id());

            int result = albumDao.insertAlbum(newAlbum);
            if (result == 1) {
                tableModel.addRow(new Object[]{newAlbum.getAlbum_id(), selectedArtist.getArtist_name(), selectedGenre.getGenre_name(), newAlbum.getAlbum_name(), newAlbum.getRelease_date()});
            } 
            
            dispose();
        });
        

    }

}
