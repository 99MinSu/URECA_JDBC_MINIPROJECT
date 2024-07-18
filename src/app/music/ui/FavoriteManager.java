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
import app.music.dto.Favorite;

public class FavoriteManager extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton searchButton, addButton, editButton, listButton;
    private JTextField searchWordField;

    private FavoriteDao favoriteDao = new FavoriteDao();

    public FavoriteManager() {
        setLayout(new BorderLayout());

        tableModel = new DefaultTableModel(new Object[]{"회원", "좋아하는 장르"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(tableModel);

        listFavorites();

        Dimension textFieldSize = new Dimension(400, 28);
        searchWordField = new JTextField();
        searchWordField.setPreferredSize(textFieldSize);

        searchButton = new JButton("검색");

        JPanel searchPanel = new JPanel();
        searchPanel.add(new JLabel("검색어"));
        searchPanel.add(searchWordField);
        searchPanel.add(searchButton);

        listButton = new JButton("목록");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(listButton);

        add(searchPanel, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        searchButton.addActionListener(e -> {
            String searchWord = searchWordField.getText();
            if (!searchWord.isBlank()) {
                listFavorites(searchWord);
            }
        });

        listButton.addActionListener(e -> listFavorites());

    }

    private void clearTable() {
        tableModel.setRowCount(0);
    }

    private void listFavorites() {
        clearTable();
        List<Favorite> favoriteList = favoriteDao.listFavorites();
        for (Favorite favorite : favoriteList) {
            tableModel.addRow(new Object[]{favorite.getMember_name(), favorite.getGenre_name()});
        }
    }

    private void listFavorites(String searchWord) {
        clearTable();
        List<Favorite> favoriteList = favoriteDao.listFavorites(searchWord);
        for (Favorite favorite : favoriteList) {
            tableModel.addRow(new Object[]{favorite.getMember_name(), favorite.getGenre_name()});
        }
    }

    Favorite detailFavorite(int memberId, int genreId) {
        return favoriteDao.getFavoriteByMemberAndGenre(memberId, genreId);
    }

}
