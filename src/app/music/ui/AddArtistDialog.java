package app.music.ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import app.music.dto.Artist;

public class AddArtistDialog extends JDialog {
    private JTextField artistIdField, artistNameField;
    private JButton addButton;

    public AddArtistDialog(ArtistManager parent, DefaultTableModel tableModel) {
        setTitle("아티스트 추가");
        setSize(300, 150);
        setLayout(new BorderLayout());
        setLocationRelativeTo(parent);

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(2, 2));

        artistIdField = new JTextField();
        artistNameField = new JTextField();

        inputPanel.add(new JLabel("아티스트 ID"));
        inputPanel.add(artistIdField);
        inputPanel.add(new JLabel("아티스트 이름"));
        inputPanel.add(artistNameField);

        addButton = new JButton("추가");
        addButton.addActionListener(e -> {
            int artistId = Integer.parseInt(artistIdField.getText());
            String artistName = artistNameField.getText();

            parent.insertArtist(new Artist(artistId, artistName));

            dispose();
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);

        add(inputPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }
}
