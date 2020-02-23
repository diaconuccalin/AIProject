import javax.swing.*;

class SizeChoiceUI extends JFrame {
    SizeChoiceUI() {
        int w = 300;
        int h = 140;

        setLayout(null);
        setSize(w, h);
        setTitle("Size choice");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JLabel jLabel = new JLabel("Choose the board size:");
        JTextField jTextField = new JTextField(1);
        JButton jButton = new JButton("Submit");

        jButton.addActionListener(e -> {
            try {
                int size = Integer.parseInt(jTextField.getText());
                if (size < 2)
                    size = 2;
                new MainUI(size);
                dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(jButton,
                        "Only numbers as input!");
            }
        });

        jLabel.setBounds(20, 10, 200, 30);
        jTextField.setBounds(220, 10, 40, 30);
        jButton.setBounds(70, 55, 90, 30);

        add(jLabel);
        add(jTextField);
        add(jButton);

        setVisible(true);
    }
}
