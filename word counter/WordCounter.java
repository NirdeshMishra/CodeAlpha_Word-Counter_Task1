import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class WordCounter extends JFrame {

    // GUI Components
    JTextArea textArea;  // To enter the text
    JLabel wordCountLabel;  // Label to show word count
    JButton clearButton, saveButton;  // Buttons to clear the text area and save file

    public WordCounter() {
        // Set up the frame
        setTitle("Word Counter");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);  // Center the window

        // Create and set layout for the main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        // Text Area for input
        textArea = new JTextArea();
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);

        // Add a DocumentListener to automatically count words as user types
        textArea.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateWordCount();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateWordCount();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateWordCount();
            }
        });

        // Button to clear text area
        clearButton = new JButton("Clear");
        clearButton.addActionListener(e -> {
            textArea.setText("");  // Clear the text area
            wordCountLabel.setText("Word Count: 0");  // Reset word count label
        });

        // Button to save the text area content to a file
        saveButton = new JButton("Save");
        saveButton.addActionListener(e -> saveToFile());

        // Label to display word count
        wordCountLabel = new JLabel("Word Count: 0");

        // Add the text area to a scroll pane
        JScrollPane scrollPane = new JScrollPane(textArea);

        // Panel for buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(clearButton);
        buttonPanel.add(saveButton);

        // Add components to the main panel
        mainPanel.add(scrollPane, BorderLayout.CENTER);  // Text input area in the center
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);  // Buttons at the bottom
        mainPanel.add(wordCountLabel, BorderLayout.NORTH);  // Word count at the top

        // Add the main panel to the frame
        add(mainPanel);

        setVisible(true);  // Show the frame
    }

    // Method to count words and update the word count label
    private void updateWordCount() {
        String text = textArea.getText();  // Get the text from the text area
        int wordCount = countWords(text);  // Count words in the text
        wordCountLabel.setText("Word Count: " + wordCount);  // Update the label with the word count
    }

    // Method to count words in a given text
    private int countWords(String text) {
        if (text == null || text.isEmpty()) {
            return 0;
        }
        String[] words = text.trim().split("\\s+");  // Split the text by spaces
        return words.length;  // Return the number of words
    }

    // Method to save the text content to a file
    private void saveToFile() {
        JFileChooser fileChooser = new JFileChooser();
        int option = fileChooser.showSaveDialog(this);  // Show save dialog
        if (option == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try (FileWriter writer = new FileWriter(file)) {
                writer.write(textArea.getText());  // Write the text area content to file
                JOptionPane.showMessageDialog(this, "File saved successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error saving file!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void main(String[] args) {
        // Launch the application
        SwingUtilities.invokeLater(() -> new WordCounter());
    }
}
