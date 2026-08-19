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
        setTitle("Calculadora Basics");
        setSize(330, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

       
        getContentPane().setBackground(new Color(28, 28, 30));

     
        display = new JTextField("0");
        display.setFont(new Font("SansSerif", Font.BOLD, 38));
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setEditable(false);
        display.setBackground(new Color(28, 28, 30));
        display.setForeground(Color.WHITE);
        display.setBorder(BorderFactory.createEmptyBorder(25, 20, 15, 20)); // Espaçamento interno
        add(display, BorderLayout.NORTH);

        
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 4, 10, 10));
        panel.setBackground(new Color(28, 28, 30));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 15, 20, 15));

        String[] buttons = {
                "7", "8", "9", "/",
                "4", "5", "6", "*",
                "1", "2", "3", "-",
                "0", "C", "=", "+"
        };

        for (String text : buttons) {
            JButton button = new JButton(text);
            button.setFont(new Font("SansSerif", Font.BOLD, 22));
            button.setFocusPainted(false);   // Remove o quadrado de foco
            button.setBorderPainted(false);  // Linha de borda limpa
            button.setOpaque(true);

            
            if ("/*-+=".contains(text)) {
                button.setBackground(new Color(255, 149, 0)); 
                button.setForeground(Color.WHITE);
            } else if (text.equals("C")) {
                button.setBackground(new Color(165, 165, 165)); 
                button.setForeground(Color.BLACK);
            } else {
                button.setBackground(new Color(50, 50, 54));    
                button.setForeground(Color.WHITE);
            }

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
        } else if (cmd.equals("C")) {
            display.setText("0");
            firstOperand = 0;
            operator = "";
            isNewOp = true;
        } else if (cmd.equals("=")) {
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

                    
                    if (result % 1 == 0) {
                        display.setText(String.valueOf((long) result));
                    } else {
                        display.setText(String.valueOf(result));
                    }

                    isNewOp = true;
                    operator = "";
                } catch (NumberFormatException ex) {
                    display.setText("Erro");
                }
            }
        } else {
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
        SwingUtilities.invokeLater(() -> {
            new Calculadora().setVisible(true);
        });
    }
}
