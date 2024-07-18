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
import app.music.dto.Genre;

public class GenreManager extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton searchButton, addButton, editDeleteButton, listButton; 
    private JTextField searchWordField;

    private GenreDao genreDao = new GenreDao();
    private FavoriteDao favoriteDao;  // FavoriteDao 필드 추가
    public GenreManager() {
        setLayout(new BorderLayout());
        this.favoriteDao = new FavoriteDao();
        tableModel = new DefaultTableModel(new Object[]{"Genre ID", "Genre Name"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(tableModel);

        listGenres(); 

        JPanel buttonPanel = new JPanel();
        addButton = new JButton("추가");
        editDeleteButton = new JButton("수정 & 삭제"); 
        listButton = new JButton("목록");

        buttonPanel.add(addButton);
        buttonPanel.add(editDeleteButton); 
        buttonPanel.add(listButton);

        Dimension textFieldSize = new Dimension(400, 28);
        searchWordField = new JTextField();
        searchWordField.setPreferredSize(textFieldSize);

        searchButton = new JButton("검색");

        JPanel searchPanel = new JPanel();
        searchPanel.add(new JLabel("장르 검색"));
        searchPanel.add(searchWordField);
        searchPanel.add(searchButton);

        add(new JScrollPane(table), BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        add(searchPanel, BorderLayout.NORTH);

        addButton.addActionListener(e -> {
            AddGenreDialog addDialog = new AddGenreDialog(this, tableModel);
            addDialog.setVisible(true);
        });

        editDeleteButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow >= 0) {
                EditGenreDialog editDialog = new EditGenreDialog(this, tableModel, selectedRow);
                editDialog.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "장르를 선택하세요.");
            }
        });

        listButton.addActionListener(e -> {
            listGenres();
        });

        searchButton.addActionListener(e -> {
            String searchWord = searchWordField.getText();
            if (!searchWord.isBlank()) {
                listGenres(searchWord);
            }
        });

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int selectedRow = table.convertRowIndexToModel(table.getSelectedRow());
                    if (selectedRow >= 0) {
                        EditGenreDialog editDialog = new EditGenreDialog(GenreManager.this, tableModel, selectedRow);
                        editDialog.setVisible(true);
                    }
                }
            }
        });
    }

    private void clearTable() {
        tableModel.setRowCount(0);
    }

    private void listGenres() {
        clearTable();
        List<Genre> genreList = genreDao.listGenres();
        for (Genre genre : genreList) {
            tableModel.addRow(new Object[]{genre.getGenre_id(), genre.getGenre_name()});
        }
    }

    private void listGenres(String searchWord) {
        clearTable();
        List<Genre> genreList = genreDao.listGenres(searchWord);
        for (Genre genre : genreList) {
            tableModel.addRow(new Object[]{genre.getGenre_id(), genre.getGenre_name()});
        }
    }

    Genre detailGenre(int genreId) {
        return genreDao.detailGenre(genreId);
    }

    public void deleteGenre(int genreId) {
    	favoriteDao.deleteFavoritesByGenreId(genreId); // 외래키로 인해 favorite 삭제 후 장르 삭제
    	
        int ret = genreDao.deleteGenre(genreId);
        if (ret == 1) {
            listGenres();
        }
    }

    public void insertGenre(Genre genre) {
        int ret = genreDao.insertGenre(genre);
        if (ret == 1) {
            listGenres();
        }
    }

    public void updateGenre(Genre genre) {
        int ret = genreDao.updateGenre(genre);
        if (ret == 1) {
            listGenres();
        }
    }
}
