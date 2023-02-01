package InterfacePackage;
import PolynomialPackage.Element;
import PolynomialPackage.Node;
import PolynomialPackage.Operations;
import PolynomialPackage.Polynomial;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainGUI extends JFrame {

    private Integer defaultWidth;
    private Integer defaultHeight;

    //the 2 polynomials
    protected Polynomial first = new Polynomial();
    protected Polynomial second = new Polynomial();

    //elements of the GUI
    //labels
    protected final JLabel firstPolynomialLabel = new JLabel("First Polynomial:");
    protected final JTextField firstPolynomialTextField = new JTextField("");
    protected final JLabel secondPolynomialLabel = new JLabel("Second Polynomial:");
    protected final JTextField secondPolynomialTextField = new JTextField("");

    //buttons
    private final JButton addToFirst = new JButton("Add element");
    private final JButton addToSecond = new JButton("Add element");
    private final JButton sum = new JButton("Sum");
    private final JButton difference = new JButton("Difference");
    private final JButton multiplication = new JButton("Multiplication");
    private final JButton division = new JButton("Division");
    private final JButton integralFirst = new JButton("First Integral");
    private final JButton integralSecond = new JButton("Second Integral");
    private final JButton derivativeFirst = new JButton("First Derivative");
    private final JButton derivativeSecond  = new JButton("Second Derivative");
    private final JButton restart = new JButton("Restart");

    //constructor and also function for creating the GUI
    public MainGUI(){

        //initializing the window
        getScreen();
        windowInit();
        label();
        button();
    }

    //function for parsing the string and get the polynomial
    private Polynomial getPolynomial(String expression) {

        Node node = new Node(new Element(-1, 0.0));
        Pattern pattern = Pattern.compile("([+-]?[^-+]+)");
        Matcher matcher = pattern.matcher(expression);
        while (matcher.find()) {

            char[] string = matcher.group(1).toCharArray();
            int power = 0;
            double coefficient = 0.0;
            boolean positive;

            positive = string[0] != '-';

            int i;

            if (string[0] == '-' || string[0] == '+')
                i = 1;
            else
                i = 0;

            while (string[i] != 'x') {

                coefficient = coefficient * 10 + string[i] - '0';
                i++;
            }

            while (string[i] != '^')
                i++;

            i++;
            while (i < string.length) {

                power = power * 10 + string[i] - '0';
                i++;
            }

            if (!positive)
                coefficient *= -1;
            if (node.element.power == -1) {

                node.element = new Element(power, coefficient);
            } else {

                node = node.insert(node, new Element(power, coefficient));
            }
        }

        Polynomial polynomial = new Polynomial();
        ArrayList<Element> arrayList = node.list(node);

        for (Element element : arrayList)
            polynomial.addElement(element);


        return polynomial;
    }

    private void getScreen(){

        Toolkit toolkit = Toolkit.getDefaultToolkit();

        defaultHeight = toolkit.getScreenSize().height - 75;
        defaultWidth = toolkit.getScreenSize().width - 75;
    }

    //function for the Labels
    private void label(){

        firstPolynomialLabel.setBounds(10, 10, defaultWidth / 4, defaultHeight / 6);
        this.add(firstPolynomialLabel);

        secondPolynomialLabel.setBounds(10, 60, defaultWidth / 4, defaultHeight / 6);
        this.add(secondPolynomialLabel);

        firstPolynomialTextField.setBounds(200, 60, defaultWidth - 400, 30);
        firstPolynomialTextField.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                first = getPolynomial(firstPolynomialTextField.getText());
            }
        });
        this.add(firstPolynomialTextField);

        secondPolynomialTextField.setBounds(200, 130, defaultWidth - 400, 30);
        this.add(secondPolynomialTextField);
        secondPolynomialTextField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                second = getPolynomial(secondPolynomialTextField.getText());
            }
        });
    }

    //function for showing the result of the specific operation
    private void resultPanel(Polynomial polynomial){

        if(polynomial.getElements().size() == 0)
            JOptionPane.showMessageDialog(null, "0");
        else
            JOptionPane.showMessageDialog(null, polynomial.polynomialToString());
    }

    //adding all the buttons and adding Action Listeners to each button
    private void button(){

        division.setBounds(700, defaultHeight / 16 + 300, 150, 100);
        this.add(division);
        division.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                if(first.size < 0 || second.size < 0)
                    JOptionPane.showMessageDialog(null, "Enter 2 polynomials");
                else {

                    if(first.getElements().get(0).power < second.getElements().get(0).power){

                        JOptionPane.showMessageDialog(null, "the reminder is: " + first.polynomialToString());
                        return ;
                    }
                    Operations operations = new Operations(first, second);
                    List<Polynomial> results = operations.division();

                    if(results.size() == 1)
                        JOptionPane.showMessageDialog(null, "the quotient is: " + results.get(0).polynomialToString() + "\nand the reminder is 0");
                    else
                        JOptionPane.showMessageDialog(null, "the quotient is: " + results.get(0).polynomialToString() + "\nand the reminder is: " + results.get(1).polynomialToString());
                }
            }
        });

        integralSecond.setBounds(1550, defaultHeight / 16 + 300, 150, 100);
        this.add(integralSecond);
        integralSecond.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                if(second.size < 0)
                    JOptionPane.showMessageDialog(null, "Enter a polynomial");
                else {

                    resultPanel(second.integral());
                }
            }
        });

        integralFirst.setBounds(1350, defaultHeight / 16 + 300, 150, 100);
        this.add(integralFirst);
        integralFirst.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                if(first.size < 0)
                    JOptionPane.showMessageDialog(null, "Enter a polynomial");
                else {

                    resultPanel(first.integral());
                }
            }
        });

        derivativeSecond.setBounds(1100, defaultHeight / 16 + 300, 200, 100);
        this.add(derivativeSecond);
        derivativeSecond.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                if(second.size < 0)
                    JOptionPane.showMessageDialog(null, "Enter a polynomial");
                else {

                    resultPanel(second.derivative());
                }
            }
        });

        derivativeFirst.setBounds(900, defaultHeight / 16 + 300, 150, 100);
        this.add(derivativeFirst);
        derivativeFirst.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                if(first.size < 0)
                    JOptionPane.showMessageDialog(null, "Enter a polynomial");
                else {

                    resultPanel(first.derivative());
                }
            }
        });

        multiplication.setBounds(500, defaultHeight / 16 + 300, 150, 100);
        this.add(multiplication);
        multiplication.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                if(first.size < 0 || second.size < 0)
                    JOptionPane.showMessageDialog(null, "Enter 2 polynomials");
                else {

                    Operations operations = new Operations(first, second);
                    resultPanel(operations.multiplication());
                }
            }
        });

        difference.setBounds(300, defaultHeight / 16 + 300, 150, 100);
        this.add(difference);
        difference.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                if(first.size < 0 || second.size < 0)
                    JOptionPane.showMessageDialog(null, "Enter 2 polynomials");
                else {

                    Operations operations = new Operations(first, second);
                    resultPanel(operations.difference());
                }
            }
        });

        sum.setBounds(100, defaultHeight / 16 + 300, 150, 100);
        this.add(sum);
        sum.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                if(first.size < 0 || second.size < 0)
                    JOptionPane.showMessageDialog(null, "Enter 2 polynomials");
                else {

                    Operations operations = new Operations(first, second);
                    resultPanel(operations.sum());
                }
            }
        });

        addToFirst.setBounds(defaultWidth - 200, 85, 200, 20);
        addToFirst.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                if(first.getLastPower() == null || first.getLastPower() != 0)
                    new PopUp(MainGUI.this, false);
                else
                    JOptionPane.showMessageDialog(null, "Can not add if the last element was a constant");
            }
        });
        this.add(addToFirst);

        addToSecond.setBounds(defaultWidth - 200, 135, 200, 20);
        addToSecond.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                if(second.getLastPower() == null || second.getLastPower() != 0)
                    new PopUp(MainGUI.this, true);
                else
                    JOptionPane.showMessageDialog(null, "Can not add if the last element was a constant");
            }
        });
        this.add(addToSecond);

        restart.setBounds(defaultWidth - 200, defaultHeight - 100, 100, 50);
        restart.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                first = new Polynomial();
                second = new Polynomial();

                firstPolynomialTextField.setText("");
                secondPolynomialTextField.setText("");
            }
        });
        this.add(restart);
    }

    //window initialisation
    private void windowInit(){

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        setLayout(null);
        setResizable(false);
        setSize(defaultWidth, defaultHeight);
        setTitle("Polynomial Calculator");
    }
}
