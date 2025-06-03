package com.university.main;

import com.university.gui.MainFrame;
import com.university.util.DatabaseUtil;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class UniversityManagementApp {
    public static void main(String[] args) {
        // I'm initializing the database first thing - this creates tables if they don't exist
        DatabaseUtil.initializeDatabase();

        // I'm trying to set a nicer Look and Feel if available
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.err.println("Failed to set system Look and Feel: " + e.getMessage());
            // I'll just continue with the default L&F if this fails
        }
        
        // I'm using SwingUtilities.invokeLater to ensure the GUI is created on the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            MainFrame mainFrame = new MainFrame();
            mainFrame.setVisible(true);
        });
    }
}
