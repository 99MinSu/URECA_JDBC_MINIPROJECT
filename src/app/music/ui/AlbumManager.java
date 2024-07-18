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

import app.music.dao.AlbumDao;
import app.music.dao.GenreDao;
import app.music.dto.Album;

public class AlbumManager extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton searchButton, listButton, editDeleteButton, addButton;
    private JTextField searchWordField;

    private AlbumDao albumDao = new AlbumDao();
    private GenreDao genreDao = new GenreDao();

    public AlbumManager() {
        setLayout(new BorderLayout());

        tableModel = new DefaultTableModel(new Object[]{"앨범 ID", "아티스트 이름", "장르 이름", "앨범 이름", "발매일"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(tableModel);

        listAlbums();

        Dimension textFieldSize = new Dimension(400, 28);
        searchWordField = new JTextField();
        searchWordField.setPreferredSize(textFieldSize);

        searchButton = new JButton("검색");
        editDeleteButton = new JButton("수정 & 삭제"); 
        addButton = new JButton("추가");

        JPanel searchPanel = new JPanel();
        searchPanel.add(new JLabel("검색"));
        searchPanel.add(searchWordField);
        searchPanel.add(searchButton);

        listButton = new JButton("목록");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(editDeleteButton); 
        buttonPanel.add(listButton);

        add(searchPanel, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        searchButton.addActionListener(e -> {
            String searchWord = searchWordField.getText();
            if (!searchWord.isEmpty()) {
                listAlbums(searchWord);
            } else {
                listAlbums();
            }
        });
        listButton.addActionListener(e -> listAlbums());

        addButton.addActionListener(e -> {
            AddAlbumDialog addDialog = new AddAlbumDialog(this, tableModel);
            addDialog.setVisible(true);
        });
        
        editDeleteButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow >= 0) {
                EditAlbumDialog editDialog = new EditAlbumDialog(this, tableModel, selectedRow);
                editDialog.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "앨범을 선택하세요.");
            }
        });

        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int row = table.getSelectedRow();
                    if (row != -1) {
                        EditAlbumDialog editDialog = new EditAlbumDialog(AlbumManager.this, tableModel, row);
                        editDialog.setVisible(true);
                    }
                }
            }
        });
    }

    private void clearTable() {
        tableModel.setRowCount(0);
    }

    private void listAlbums() {
        clearTable();
        List<Album> albumList = albumDao.listAlbums();
        for (Album album : albumList) {
            tableModel.addRow(new Object[]{album.getAlbum_id(), album.getArtist_name(), album.getGenre_name(), album.getAlbum_name(), album.getRelease_date()});
        }
    }

    private void listAlbums(String searchWord) {
        clearTable();
        List<Album> albumList = albumDao.listAlbums(searchWord);
        for (Album album : albumList) {
            tableModel.addRow(new Object[]{album.getAlbum_id(), album.getArtist_name(), album.getGenre_name(), album.getAlbum_name(), album.getRelease_date()});
        }
    }

    public GenreDao getGenreDao() {
        return genreDao;
    }

    public Album detailAlbum(Integer albumId) {
        return albumDao.getAlbumById(albumId);
    }

    public void updateAlbum(Album album) {
        albumDao.updateAlbum(album);
        listAlbums();
    }

    public void deleteAlbum(Integer albumId) {
        albumDao.deleteAlbum(albumId);
        listAlbums();
    }
}
