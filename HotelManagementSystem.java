import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

// --- Guest Class ---
class Guest {
    String name;
    int roomNumber;

    Guest(String name, int roomNumber) {
        this.name = name;
        this.roomNumber = roomNumber;
    }

    public String toString() {
        return "Name: " + name + ", Room: " + roomNumber;
    }
}

// --- Sign-Up Frame ---
class SignUpPage extends JFrame {
    private JTextField nameField = new JTextField(15);
    private JTextField emailField = new JTextField(15);
    private JPasswordField passwordField = new JPasswordField(15);
    private JButton signUpButton = new JButton("Sign Up");

    SignUpPage() {
        setTitle("Sign Up");
        setSize(350, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        add(new JLabel("Name:"));
        add(nameField);
        add(new JLabel("Email:"));
        add(emailField);
        add(new JLabel("Password:"));
        add(passwordField);
        add(signUpButton);

        signUpButton.addActionListener(e -> {
            String name = nameField.getText();
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());

            if (!name.isEmpty() && !email.isEmpty() && !password.isEmpty()) {
                // Sign up success
                JOptionPane.showMessageDialog(this, "Sign up successful!");
                this.dispose(); // close the sign-up window
                new HotelManagementSystem(name).setVisible(true); // open main system
            } else {
                JOptionPane.showMessageDialog(this, "Please fill in all fields.");
            }
        });
    }
}

// --- Hotel Management Frame ---
public class HotelManagementSystem extends JFrame {
    private ArrayList<Guest> guestList = new ArrayList<>();
    private JTextField nameField = new JTextField(15);
    private JTextField roomField = new JTextField(5);
    private JTextArea outputArea = new JTextArea(10, 30);

    public HotelManagementSystem(String signedInUserName) {
        setTitle("Hotel Management System - Welcome " + signedInUserName);
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        // Input Panel
        JPanel inputPanel = new JPanel();
        inputPanel.add(new JLabel("Guest Name:"));
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("Room #:"));
        inputPanel.add(roomField);

        JButton addButton = new JButton("Add Guest");
        JButton viewButton = new JButton("View Guests");
        JButton checkoutButton = new JButton("Check Out");

        inputPanel.add(addButton);
        inputPanel.add(viewButton);
        inputPanel.add(checkoutButton);
        add(inputPanel);

        // Output Area
        outputArea.setEditable(false);
        add(new JScrollPane(outputArea));

        // Add Guest Action
        addButton.addActionListener(e -> {
            String name = nameField.getText();
            String roomStr = roomField.getText();
            if (!name.isEmpty() && !roomStr.isEmpty()) {
                try {
                    int room = Integer.parseInt(roomStr);
                    guestList.add(new Guest(name, room));
                    outputArea.setText("Guest added: " + name + " in room " + room);
                    nameField.setText("");
                    roomField.setText("");
                } catch (NumberFormatException ex) {
                    outputArea.setText("Room number must be an integer.");
                }
            } else {
                outputArea.setText("Please enter both name and room number.");
            }
        });

        // View Guests Action
        viewButton.addActionListener(e -> {
            outputArea.setText("Guest List:\n");
            for (Guest g : guestList) {
                outputArea.append(g + "\n");
            }
        });

        // Check Out Action
        checkoutButton.addActionListener(e -> {
            String name = nameField.getText();
            if (!name.isEmpty()) {
                boolean removed = guestList.removeIf(g -> g.name.equalsIgnoreCase(name));
                if (removed) {
                    outputArea.setText("Guest checked out: " + name);
                } else {
                    outputArea.setText("Guest not found: " + name);
                }
            } else {
                outputArea.setText("Enter the guest name to check out.");
            }
        });
    }

    // Entry Point
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SignUpPage signUp = new SignUpPage();
            signUp.setVisible(true);
        });
    }
}
