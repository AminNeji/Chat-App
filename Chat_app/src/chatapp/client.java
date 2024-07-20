package chatapp;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.function.Consumer;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

public class client extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField textField;
    private clientchat client;
    private JTextPane messagePane;
    private StyledDocument doc;
    private String name;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new client().setVisible(true);
        });
    }

    /**
     * Create the frame.
     */
    public client() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 363, 497);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        name = JOptionPane.showInputDialog(this, "Enter your name:", "Name Entry", JOptionPane.PLAIN_MESSAGE);
        this.setTitle(name + " - Chat App");

        setContentPane(contentPane);
        contentPane.setLayout(null);

        JPanel panel = new JPanel();
        panel.setBounds(0, 0, 347, 458);
        contentPane.add(panel);
        panel.setLayout(null);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 24, 327, 385);
        panel.add(scrollPane);

        messagePane = new JTextPane();
        messagePane.setEditable(false);
        scrollPane.setViewportView(messagePane);
        doc = messagePane.getStyledDocument();

        textField = new JTextField();
        textField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String msg = name + ": " + textField.getText();
                appendMessage(msg, true);
                client.sendMessage(msg);
                textField.setText("");
            }
        });
        textField.setBounds(10, 416, 327, 31);
        panel.add(textField);
        textField.setColumns(10);

        try {
            Consumer<String> onMessageReceived = this::appendMessageFromServer;
            this.client = new clientchat("127.0.0.1", 2000, onMessageReceived);
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error connecting to the server", "Connection error",
                    JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }

    private void appendMessageFromServer(String message) {
        if (!message.startsWith(name + ":")) {
            appendMessage(message, false);
        }
    }

    private void appendMessage(String message, boolean isOwnMessage) {
        SimpleAttributeSet attributes = new SimpleAttributeSet();
        StyleConstants.setAlignment(attributes, isOwnMessage ? StyleConstants.ALIGN_RIGHT : StyleConstants.ALIGN_LEFT);
        StyleConstants.setForeground(attributes, isOwnMessage ? Color.BLUE : Color.BLACK);
        try {
            int length = doc.getLength();
            doc.insertString(length, message + "\n", attributes);
            doc.setParagraphAttributes(length, message.length(), attributes, false);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }
}
