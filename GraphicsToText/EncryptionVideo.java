package GraphicsToText;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

public class EncryptionVideo {
    private JFrame frame;
    private JTextField videoPathTextField;
    private JTextField txtPathTextField;

    public EncryptionVideo() {
        createGUI();
    }

    private void createGUI() {
        frame = new JFrame("Video Encryption");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setSize(750, 400);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setBackground(Color.BLACK);

        // Top Panel
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(2, 2));
        topPanel.setBackground(Color.BLACK);

        JButton selectVideoButton = new JButton("Select Video File");
        selectVideoButton.setForeground(Color.WHITE);
        selectVideoButton.setBackground(Color.BLACK);
        selectVideoButton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 3));
        selectVideoButton.setFocusPainted(false);
        selectVideoButton.setHorizontalAlignment(SwingConstants.CENTER);
        selectVideoButton.setVerticalAlignment(SwingConstants.CENTER);
        selectVideoButton.setPreferredSize(new Dimension(120, 30));
        selectVideoButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                selectVideoButton.setBackground(Color.WHITE);
                selectVideoButton.setForeground(Color.BLACK);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                selectVideoButton.setBackground(Color.BLACK);
                selectVideoButton.setForeground(Color.WHITE);
            }
        });
        selectVideoButton.addActionListener(new SelectVideoButtonListener());
        topPanel.add(selectVideoButton);

        videoPathTextField = new JTextField(30);
        videoPathTextField.setEditable(false);
        videoPathTextField.setBackground(Color.BLACK);
        videoPathTextField.setForeground(Color.WHITE);
        topPanel.add(videoPathTextField);

        JButton selectOutputButton = new JButton("Select Output TXT File");
        selectOutputButton.setForeground(Color.WHITE);
        selectOutputButton.setBackground(Color.BLACK);
        selectOutputButton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 3));
        selectOutputButton.setFocusPainted(false);
        selectOutputButton.setHorizontalAlignment(SwingConstants.CENTER);
        selectOutputButton.setVerticalAlignment(SwingConstants.CENTER);
        selectOutputButton.setPreferredSize(new Dimension(120, 30));
        selectOutputButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                selectOutputButton.setBackground(Color.WHITE);
                selectOutputButton.setForeground(Color.BLACK);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                selectOutputButton.setBackground(Color.BLACK);
                selectOutputButton.setForeground(Color.WHITE);
            }
        });
        selectOutputButton.addActionListener(new SelectOutputButtonListener());
        topPanel.add(selectOutputButton);

        txtPathTextField = new JTextField(30);
        txtPathTextField.setEditable(false);
        txtPathTextField.setBackground(Color.BLACK);
        txtPathTextField.setForeground(Color.WHITE);
        topPanel.add(txtPathTextField);

        frame.add(topPanel, BorderLayout.NORTH);

        // Bottom Panel
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout());
        bottomPanel.setBackground(Color.BLACK);

        JButton encryptButton = new JButton("Encrypt Video");
        encryptButton.setForeground(Color.WHITE);
        encryptButton.setBackground(Color.BLACK);
        encryptButton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 3));
        encryptButton.setFocusPainted(false);
        encryptButton.setHorizontalAlignment(SwingConstants.CENTER);
        encryptButton.setVerticalAlignment(SwingConstants.CENTER);
        encryptButton.setPreferredSize(new Dimension(120, 30));
        encryptButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                encryptButton.setBackground(Color.WHITE);
                encryptButton.setForeground(Color.BLACK);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                encryptButton.setBackground(Color.BLACK);
                encryptButton.setForeground(Color.WHITE);
            }
        });
        encryptButton.addActionListener(new EncryptButtonListener());
        bottomPanel.add(encryptButton);

        JButton endProgramButton = new JButton("End Program");
        endProgramButton.setForeground(Color.WHITE);
        endProgramButton.setBackground(Color.BLACK);
        endProgramButton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 3));
        endProgramButton.setFocusPainted(false);
        endProgramButton.setHorizontalAlignment(SwingConstants.CENTER);
        endProgramButton.setVerticalAlignment(SwingConstants.CENTER);
        endProgramButton.setPreferredSize(new Dimension(120, 30));
        endProgramButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                endProgramButton.setBackground(Color.WHITE);
                endProgramButton.setForeground(Color.BLACK);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                endProgramButton.setBackground(Color.BLACK);
                endProgramButton.setForeground(Color.WHITE);
            }
        });
        endProgramButton.addActionListener(new EndProgramButtonListener());
        bottomPanel.add(endProgramButton);

        frame.add(bottomPanel,BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    private class SelectVideoButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            int returnValue = fileChooser.showOpenDialog(frame);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                videoPathTextField.setText(selectedFile.getAbsolutePath());
            } else {
                videoPathTextField.setText("No file selected");
            }
        }
    }

    private class SelectOutputButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            int returnValue = fileChooser.showSaveDialog(frame);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                txtPathTextField.setText(selectedFile.getAbsolutePath() + ".txt");
            } else {
                txtPathTextField.setText("No file selected");
            }
        }
    }

    private class EncryptButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String videoPath = videoPathTextField.getText();
            String txtPath = txtPathTextField.getText();
            if (videoPath.isEmpty() || txtPath.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please select both video file and output TXT file");
                return;
            }
            try {
                encryptVideo(videoPath, txtPath);
                JOptionPane.showMessageDialog(frame, "Video encrypted successfully");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(frame, "Error encrypting video: " + ex.getMessage());
            }
        }
    }

    private class EndProgramButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }

    private static void encryptVideo(String videoPath, String txtPath) throws IOException {
        byte[] videoData = Files.readAllBytes(Paths.get(videoPath));
        String base64Encoded = Base64.getEncoder().encodeToString(videoData);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(txtPath))) {
            writer.write(base64Encoded);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new EncryptionVideo();
            }
        });
    }
}