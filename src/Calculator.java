import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Calculator extends JFrame implements ActionListener {
    private JTextField display;
    double num1, num2, result;
    char operator;

    public Calculator() {
        setTitle("Calculator");
        setSize(500, 700);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        display = new JTextField("0");
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setFont(new Font("Arial", Font.BOLD, 50));
        display.setPreferredSize(new Dimension(500, 120));
        display.setEditable(false);
        add(display, BorderLayout.NORTH);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 5, 5, 5));

        JPanel p = new JPanel();
        p.setLayout(new GridLayout(5, 4));

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
            button.setFont(new Font("Arial", Font.BOLD, 20));
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
        } else if (input.equals("c")) {
            display.setText("0");
            num1 = num2 = result = 0;
        } else if (input.equals("<-")) {
            String text = display.getText();
            if (text.length() > 1) {
                display.setText(text.substring(0, text.length() - 1));
            }
        } else if (input.equals("=")) {
            try {
                double result = evaluate(display.getText());
                if (result == (long) result) {
                    display.setText(String.valueOf((long) result));
                } else {
                    display.setText(String.valueOf(result));
                }
            } catch (Exception ex) {
                display.setText("Error");
            }
        } else {
            // 연산자 입력 (+, -, ×, ÷)
            String text = display.getText();
            if (text.endsWith("+") || text.endsWith("-") || text.endsWith("×") || text.endsWith("÷")) {
                // 연산자가 중복 입력되지 않도록
                display.setText(text.substring(0, text.length() - 1) + input);
            } else {
                display.setText(text + input);
            }
        }
    }
    private double evaluate(String expression) {
        expression = expression.replace("×", "*").replace("÷", "/");
        expression = expression.replaceAll("\\s+", ""); // 공백 제거
        try {
            javax.script.ScriptEngineManager manager = new javax.script.ScriptEngineManager();
            javax.script.ScriptEngine engine = manager.getEngineByName("JavaScript");
            return((Number) engine.eval(expression)).doubleValue();
        } catch (Exception ex) {
            throw new RuntimeException("Invalid");
        }
    }

    public static void main(String[] args) {
        new Calculator();
    }
}
