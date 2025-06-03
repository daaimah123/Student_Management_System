package com.university.main;

import com.university.gui.MainFrame;
import com.university.util.DatabaseUtil;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class UniversityManagementApp {
    public static void main(String[] args) {
        // Initialize database (create tables if they don't exist)
        DatabaseUtil.initializeDatabase();

        // Set a more modern Look and Feel if available
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.err.println("Failed to set system Look and Feel: " + e.getMessage());
            // Continue with default L&F
        }
        
        SwingUtilities.invokeLater(() -> {
            MainFrame mainFrame = new MainFrame();
            mainFrame.setVisible(true);
        });
    }
}
