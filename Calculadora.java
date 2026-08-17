import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Calculadora extends JFrame implements ActionListener {

    private final JTextField display;
    private double firstOperand = 0;
    private String operator = "";
    private boolean isNewOp = true;

    public Calculadora() {
        setTitle("Calculadora");
        setSize(320, 420);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); 
        setLayout(new BorderLayout(10, 10));

        
        display = new JTextField("0");
        display.setFont(new Font("Arial", Font.BOLD, 28));
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setEditable(false);
        add(display, BorderLayout.NORTH);

        
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 4, 5, 5));

        String[] buttons = {
            "7", "8", "9", "/",
            "4", "5", "6", "*",
            "1", "2", "3", "-",
            "0", "C", "=", "+"
        };

        for (String text : buttons) {
            JButton button = new JButton(text);
            button.setFont(new Font("Arial", Font.BOLD, 18));
            button.addActionListener(this);
            panel.add(button);
        }

        add(panel, BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();

        
        if (cmd.charAt(0) >= '0' && cmd.charAt(0) <= '9') {
            if (isNewOp) {
                display.setText("");
                isNewOp = false;
            }
            display.setText(display.getText() + cmd);
        } 
        
        else if (cmd.equals("C")) {
            display.setText("0");
            firstOperand = 0;
            operator = "";
            isNewOp = true;
        } 
       
        else if (cmd.equals("=")) {
            if (!operator.isEmpty()) {
                try {
                    double secondOperand = Double.parseDouble(display.getText());
                    double result = 0;

                    switch (operator) {
                        case "+": result = firstOperand + secondOperand; break;
                        case "-": result = firstOperand - secondOperand; break;
                        case "*": result = firstOperand * secondOperand; break;
                        case "/":
                            if (secondOperand != 0) {
                                result = firstOperand / secondOperand;
                            } else {
                                display.setText("Erro");
                                isNewOp = true;
                                return;
                            }
                            break;
                    }

                    display.setText(String.valueOf(result));
                    isNewOp = true;
                    operator = "";
                } catch (NumberFormatException ex) {
                    display.setText("Erro");
                }
            }
        } 
        
        else {
            try {
                firstOperand = Double.parseDouble(display.getText());
                operator = cmd;
                isNewOp = true;
            } catch (NumberFormatException ex) {
                display.setText("Erro");
            }
        }
    }

    public static void main(String[] args) {
        // Garante que a interface seja criada na Thread correta do Swing
        SwingUtilities.invokeLater(() -> {
            new Calculadora().setVisible(true);
        });
    }
}
