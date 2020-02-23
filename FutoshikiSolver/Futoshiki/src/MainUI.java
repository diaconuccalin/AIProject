import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

class MainUI extends JFrame {
    private static void updateButton(ActionEvent e, boolean flag) {
        JButton sourceButton = (JButton) e.getSource();
        String aux = sourceButton.getText();

        if (flag) {
            if (aux.compareTo("") == 0)
                sourceButton.setText("<");
            else if (aux.compareTo("<") == 0)
                sourceButton.setText(">");
            else
                sourceButton.setText("");
        } else {
            if (aux.compareTo("") == 0)
                sourceButton.setText("∧");
            else if (aux.compareTo("∧") == 0)
                sourceButton.setText("∨");
            else
                sourceButton.setText("");
        }
    }

    MainUI(int size) {
        int w = size * 100 + 80;
        int h = size * 100;

        setLayout(null);
        setSize(w, h);
        setTitle("Futoshiki");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JTextField[][] jTextFields = new JTextField[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                jTextFields[i][j] = new JTextField(1);
                jTextFields[i][j].setBounds((j + 1) * 100 - 90,
                        (i + 1) * 100 - 90, 45, 45);
                add(jTextFields[i][j]);
            }
        }

        JButton[][] jButtons = new JButton[size][size - 1];
        JButton[][] jButtons1 = new JButton[size - 1][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (j < size - 1) {
                    jButtons[i][j] = new JButton("");
                    jButtons[i][j].setBounds((j + 1) * 100 - 40,
                            (i + 1) * 100 - 90, 45, 45);

                    jButtons[i][j].addActionListener(e -> updateButton(e, true));

                    add(jButtons[i][j]);
                }

                if (i < size - 1) {
                    jButtons1[i][j] = new JButton("");
                    jButtons1[i][j].setBounds((j + 1) * 100 - 90,
                            (i + 1) * 100 - 40, 45, 45);

                    jButtons1[i][j].addActionListener(e -> updateButton(e, false));

                    add(jButtons1[i][j]);
                }
            }
        }

        JButton submitButton = new JButton("Submit");
        submitButton.setBounds(size * 100 - 40, 10, 100, 25);

        submitButton.addActionListener(e -> {
            List<Integer[]> given = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    String aux = jTextFields[i][j].getText();

                    if (!aux.isBlank()) {
                        try {
                            int val = Integer.parseInt(aux);
                            if (val < 1)
                                val = 1;
                            else if (val > size)
                                val = size;
                            Integer[] toAdd = {i, j, val};
                            given.add(toAdd);
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(submitButton,
                                    "Only numbers as input!");
                        }
                    }
                }
            }

            List<Integer[]> lessThan = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    if (j < size - 1) {
                        String buttonText = jButtons[i][j].getText();
                        if (buttonText.compareTo("<") == 0) {
                            Integer[] toAdd = {i, j, i, j + 1};
                            lessThan.add(toAdd);
                        } else if (buttonText.compareTo(">") == 0) {
                            Integer[] toAdd = {i, j + 1, i, j};
                            lessThan.add(toAdd);
                        }
                    }

                    if (i < size - 1) {
                        String buttonText = jButtons1[i][j].getText();
                        if (buttonText.compareTo("∧") == 0) {
                            Integer[] toAdd = {i, j, i + 1, j};
                            lessThan.add(toAdd);
                        } else if (buttonText.compareTo("∨") == 0) {
                            Integer[] toAdd = {i + 1, j, i, j};
                            lessThan.add(toAdd);
                        }
                    }
                }
            }

            AuxiliaryMethods.writeToFile(size, given, lessThan);
            AuxiliaryMethods.run();
            Integer[][] info = AuxiliaryMethods.getInfo(size);

            if (info[0][0] == -1) {
                JOptionPane.showMessageDialog(submitButton, "Unsolvable!");
            } else {
                for (int i = 0; i < size; i++) {
                    for (int j = 0; j < size; j++) {
                        jTextFields[i][j].setText(info[i][j] + "");
                    }
                }
            }
        });

        add(submitButton);

        JButton backButton = new JButton("Back");
        backButton.setBounds(size * 100 - 40, 40, 100, 25);
        backButton.addActionListener(actionEvent -> {
            new SizeChoiceUI();
            dispose();
        });
        add(backButton);

        setVisible(true);
    }
}
