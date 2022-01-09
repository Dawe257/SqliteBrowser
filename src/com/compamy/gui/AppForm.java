/*
 * Created by JFormDesigner on Sun Jan 09 06:05:13 MSK 2022
 */

package com.compamy.gui;

import java.awt.event.*;
import com.compamy.db_client.Conn;
import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatLightLaf;

import java.awt.*;
import java.nio.file.Path;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

/**
 * @author David Zhenetl
 */
public class AppForm extends JFrame {
    public static Conn connection;
    public static Image icon;
    public static Path startFile;

    public AppForm() {
        StartForm startForm = new StartForm();
        startForm.setVisible(true);

        while (startFile == null) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        startForm.dispose();

        this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/logo.png")));
        icon = this.getIconImage();
        initComponents();

        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
            SwingUtilities.updateComponentTreeUI(this);
        } catch (UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        }
        this.setTitle(startFile.getFileName() + " - SQLiteBrowser");
        connection = new Conn(startFile.toAbsolutePath().toString());
        DefaultTableModel model = new DefaultTableModel(0, 0);
        table1.setModel(model);
        model.addColumn("Table");
        connection.getAllTables().forEach(x -> model.addRow(new Object[] {x}));
    }

    private void fileOpen(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Database", "db", "sqlite", "sqlite3");
        fileChooser.setFileFilter(filter);
        int returnVal = fileChooser.showSaveDialog(this);
        if (returnVal != JFileChooser.APPROVE_OPTION) return;
        Path db = fileChooser.getSelectedFile().toPath();
        this.setTitle(db.getFileName() + " - SQLiteBrowser");
        connection = new Conn(db.toAbsolutePath().toString());
        DefaultTableModel model = new DefaultTableModel(0, 0);
        table1.setModel(model);
        model.addColumn("Table");
        connection.getAllTables().forEach(x -> model.addRow(new Object[] {x}));
    }

    private void table1MouseClicked(MouseEvent e) {
        int row = table1.rowAtPoint(e.getPoint());
        String tableName = (String) table1.getModel().getValueAt(row, 0);
        ArrayList<String> columns = connection.getColumnNamesByTable(tableName);
        DefaultTableModel model = new DefaultTableModel(0, 0);
        table2.setModel(model);
        columns.forEach(model::addColumn);
        ArrayList<String[]> data = connection.getData(tableName);
        data.forEach(model::addRow);
    }

    private void themeDark(ActionEvent e) {
        try {
            UIManager.setLookAndFeel(new FlatDarculaLaf());
            SwingUtilities.updateComponentTreeUI(this);
        } catch (UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        }
    }

    private void themeLight(ActionEvent e) {
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
            SwingUtilities.updateComponentTreeUI(this);
        } catch (UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        }
    }

    private void helpAbout(ActionEvent e) {
        Image scaledImage = icon.getScaledInstance(70, 70,  Image.SCALE_FAST);
        JOptionPane.showMessageDialog(this,
                "SQLiteBrowser\nhttps://github.com/Dawe257",
                "About program",
                JOptionPane.INFORMATION_MESSAGE,
                new ImageIcon(scaledImage));
    }

    private void fileExit(ActionEvent e) {
        connection.disconnect();
        this.dispose();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        menuBar1 = new JMenuBar();
        menuFile = new JMenu();
        fileOpen = new JMenuItem();
        fileExit = new JMenuItem();
        menuView = new JMenu();
        viewTheme = new JMenu();
        themeDark = new JMenuItem();
        themeLight = new JMenuItem();
        menu3 = new JMenu();
        helpAbout = new JMenuItem();
        scrollPane1 = new JScrollPane();
        table1 = new JTable();
        scrollPane2 = new JScrollPane();
        table2 = new JTable();

        //======== this ========
        setMinimumSize(new Dimension(800, 600));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("SQLBrowser");
        setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        var contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        //======== menuBar1 ========
        {

            //======== menuFile ========
            {
                menuFile.setText("File");

                //---- fileOpen ----
                fileOpen.setText("Open");
                fileOpen.addActionListener(this::fileOpen);
                menuFile.add(fileOpen);
                menuFile.addSeparator();

                //---- fileExit ----
                fileExit.setText("Exit");
                fileExit.addActionListener(this::fileExit);
                menuFile.add(fileExit);
            }
            menuBar1.add(menuFile);

            //======== menuView ========
            {
                menuView.setText("View");

                //======== viewTheme ========
                {
                    viewTheme.setText("Theme");

                    //---- themeDark ----
                    themeDark.setText("Dark");
                    themeDark.addActionListener(this::themeDark);
                    viewTheme.add(themeDark);

                    //---- themeLight ----
                    themeLight.setText("Light");
                    themeLight.addActionListener(this::themeLight);
                    viewTheme.add(themeLight);
                }
                menuView.add(viewTheme);
            }
            menuBar1.add(menuView);

            //======== menu3 ========
            {
                menu3.setText("Help");

                //---- helpAbout ----
                helpAbout.setText("About");
                helpAbout.addActionListener(this::helpAbout);
                menu3.add(helpAbout);
            }
            menuBar1.add(menu3);
        }
        setJMenuBar(menuBar1);

        //======== scrollPane1 ========
        {
            scrollPane1.setMinimumSize(new Dimension(1280, 720));

            //---- table1 ----
            table1.setAutoCreateRowSorter(true);
            table1.setPreferredScrollableViewportSize(new Dimension(200, 400));
            table1.setFocusable(false);
            table1.setCellSelectionEnabled(true);
            table1.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    table1MouseClicked(e);
                }
            });
            scrollPane1.setViewportView(table1);
        }
        contentPane.add(scrollPane1, BorderLayout.WEST);

        //======== scrollPane2 ========
        {

            //---- table2 ----
            table2.setFocusable(false);
            table2.setAutoCreateRowSorter(true);
            scrollPane2.setViewportView(table2);
        }
        contentPane.add(scrollPane2, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JMenuBar menuBar1;
    private JMenu menuFile;
    private JMenuItem fileOpen;
    private JMenuItem fileExit;
    private JMenu menuView;
    private JMenu viewTheme;
    private JMenuItem themeDark;
    private JMenuItem themeLight;
    private JMenu menu3;
    private JMenuItem helpAbout;
    private JScrollPane scrollPane1;
    private JTable table1;
    private JScrollPane scrollPane2;
    private JTable table2;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
