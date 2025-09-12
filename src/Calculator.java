import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Calculator extends JFrame implements ActionListener {
    private JTextField display;
    JTextField tf;
    double num1, num2, result;
    char operator;

    public Calculator() {
        setTitle("Calculator");
        setSize(500, 700);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        display = new JTextField("0");
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setFont(new Font("Arial", Font.BOLD, 30));
        display.setPreferredSize(new Dimension(500, 30));
        display.setEditable(false);
        add(display, BorderLayout.NORTH);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 4, 5, 5));

        tf = new JTextField();
        add(tf, BorderLayout.NORTH);

        JPanel p = new JPanel();
        p.setLayout(new GridLayout(4, 4));

        String[] buttons = {
                "c", " ", " ", "<-",
                "7", "8", "9", "÷",
                "4", "5", "6", "×",
                "1", "2", "3", "-",
                "0", ".", "=", "+"
        };

        for (String text : buttons) {
            JButton button = new JButton(text);
            button.addActionListener(this);
            p.add(button);
        }

        add(p, BorderLayout.CENTER);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String input = e.getActionCommand();

        if (input.matches("[0-9]") || input.equals(".")) {
            if (display.getText().equals("0")) {
                display.setText(input);
            } else {
                display.setText(display.getText() + input);
            }
        } else if (input.equals("C")) {
            display.setText("0");
            num1 = num2 = result = 0;
        } else if (input.equals("<-")) {
            String text = display.getText();
            if (text.length() > 1) {
                display.setText(text.substring(0, text.length() - 1));
            }
        } else if (input.equals("=")) {
            num2 = Double.parseDouble(display.getText());
            switch (operator) {
                case '+':
                    result = num1 + num2;
                    break;
                case '-':
                    result = num1 - num2;
                    break;
                case '×':
                    result = num1 * num2;
                    break;
                case '÷':
                    if (num2 == 0) {
                        display.setText("Error");
                        return;
                    }
                    result = num1 / num2;
                    break;
            }
        } else {
            num1 = Double.parseDouble(display.getText());
            operator = input.charAt(0);
            display.setText("0");
        }
    }

    public static void main(String[] args) {
        new Calculator();
    }
}
