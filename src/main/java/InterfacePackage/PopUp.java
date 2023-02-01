package InterfacePackage;

import PolynomialPackage.Element;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PopUp extends JFrame {

    private final JTextField powerTextField = new JTextField("Enter the power");
    private final JTextField coefficientTextField = new JTextField("Enter a coefficient");
    private final JComboBox comboBox = new JComboBox();

    private Integer power;
    private Integer coefficient;

    public PopUp(final MainGUI mainGui, final boolean polynomial){

        init();
        if(!polynomial) {

            if (mainGui.first.size < 0) {

                powerTextField.setBounds(10, 10, 180, 40);
                this.add(powerTextField);
            } else {

                for (int i = 0; i < mainGui.first.getLastPower(); i++)
                    comboBox.addItem(i);

                comboBox.setBounds(10, 10, 180, 40);
                this.add(comboBox);
            }
        }
        else {

            if(mainGui.second.size < 0){

                powerTextField.setBounds(10, 10, 180, 40);
                this.add(powerTextField);
            }
            else{

                for(int i = 0; i < mainGui.second.getLastPower(); i ++)
                    comboBox.addItem(i);

                comboBox.setBounds(10, 10, 180, 40);
                this.add(comboBox);
            }
        }

        coefficientTextField.setBounds(10, 60, 180, 40);
        this.add(coefficientTextField);

        JButton addButton = new JButton("Add");
        addButton.setBounds(10, 110, 90, 40);
        this.add(addButton);

        JButton exitButton = new JButton("Exit");
        exitButton.setBounds(100, 110, 90, 40);
        this.add(exitButton);

        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                dispose();
            }
        });

        addButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                try {

                    if(!polynomial){

                        if(mainGui.first.size < 0){

                            power = Integer.parseInt(powerTextField.getText());
                            mainGui.first.setLastPower(power);
                        }
                        else
                            power = Integer.parseInt(comboBox.getSelectedItem().toString());

                        coefficient = Integer.parseInt(coefficientTextField.getText());
                        mainGui.first.addElement(new Element(power, coefficient.doubleValue()));
                        mainGui.firstPolynomialTextField.setText(mainGui.first.polynomialToString());
                    }
                    else {

                        if(mainGui.second.size < 0){

                            power = Integer.parseInt(powerTextField.getText());
                            mainGui.second.setLastPower(power);
                        }
                        else
                            power = Integer.parseInt(comboBox.getSelectedItem().toString());

                        coefficient = Integer.parseInt(coefficientTextField.getText());
                        mainGui.second.addElement(new Element(power, coefficient.doubleValue()));
                        mainGui.secondPolynomialTextField.setText(mainGui.second.polynomialToString());
                    }
                    dispose();
                }
                catch (NumberFormatException exception){

                    JOptionPane.showMessageDialog(null, "Enter a valid number");
                }
            }
        });
    }

    private void init(){

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        setLayout(null);
        setResizable(false);
        setSize(200, 200);

        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - this.size().width) / 2);
        int y = (int) ((dimension.getHeight() + this.size().height) / 2);
        this.setLocation(x, y);
    }
}
