/*
 * Created by JFormDesigner on Sun Jan 09 08:11:03 MSK 2022
 */

package com.compamy.gui;

import java.awt.event.*;

import com.formdev.flatlaf.FlatLightLaf;

import java.awt.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * @author David Zhenetl
 */
public class StartForm extends JFrame {
    public StartForm() {
        this.setTitle("SQLiteBrowser");
        this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/logo.png")));
        initComponents();
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
            SwingUtilities.updateComponentTreeUI(this);
        } catch (UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        }
    }

    private void pickDb(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Database", "db");
        fileChooser.setFileFilter(filter);
        int returnVal = fileChooser.showSaveDialog(this);
        if (returnVal != JFileChooser.APPROVE_OPTION) return;
        AppForm.startFile = fileChooser.getSelectedFile().toPath();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        pickDb = new JButton();

        //======== this ========
        setResizable(false);
        var contentPane = getContentPane();
        contentPane.setLayout(new CardLayout(150, 50));

        //---- pickDb ----
        pickDb.setText("Pick database");
        pickDb.setMaximumSize(new Dimension(50, 50));
        pickDb.setMinimumSize(new Dimension(40, 40));
        pickDb.setPreferredSize(new Dimension(200, 50));
        pickDb.addActionListener(e -> pickDb(e));
        contentPane.add(pickDb, "card1");
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JButton pickDb;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
