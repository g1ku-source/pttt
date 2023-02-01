package PolynomialPackage;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

//class for creating operations with 2 polynomials
public class Operations {

    private Polynomial firstPolynomial;
    private final Polynomial secondPolynomial;

    //constructor consists of 2 polynomials
    public Operations(Polynomial polynomial1, Polynomial polynomial2) {

        this.firstPolynomial = polynomial1;
        this.secondPolynomial = polynomial2;
    }

    //function for the sum of 2 polynomials
    public Polynomial sum() {

        Polynomial summedPolynomial = new Polynomial();

        int index1 = 0;
        int index2 = 0;

        //get the elements of every polynomial
        ArrayList<Element> firstPolynomialElements = firstPolynomial.getElements();
        ArrayList<Element> secondPolynomialElements = secondPolynomial.getElements();

        while (index1 <= firstPolynomial.size && index2 <= secondPolynomial.size) {

            //case 1: the element's power at the position index1 from the first polynomial is smaller than the element's power of the second polynomial at the position index2
            //then we add the element from the second polynomial and increment index2
            if (firstPolynomialElements.get(index1).power < secondPolynomialElements.get(index2).power) {

                summedPolynomial.addElement(secondPolynomialElements.get(index2));
                index2++;
            } else {

                //just like the first case, but reverse first with second
                if (firstPolynomialElements.get(index1).power > secondPolynomialElements.get(index2).power) {

                    summedPolynomial.addElement(firstPolynomialElements.get(index1));
                    index1++;
                }
                //if the power is the same, then add the coefficients and the power remains the same
                else {

                    summedPolynomial.addElement(new Element(firstPolynomialElements.get(index1).power, firstPolynomialElements.get(index1).coefficient + secondPolynomialElements.get(index2).coefficient));
                    index1++;
                    index2++;
                }
            }
        }
        //if there are elements left in the first or the second polynomial
        while (index1 <= firstPolynomial.size) {

            summedPolynomial.addElement(firstPolynomialElements.get(index1));
            index1++;
        }
        while (index2 <= secondPolynomial.size) {

            summedPolynomial.addElement(secondPolynomialElements.get(index2));
            index2++;
        }
        //returning the Polynomial
        return summedPolynomial;
    }

    //function for the difference of 2 polynomials
    public Polynomial difference(){

        Polynomial polynomial = new Polynomial();

        int index1 = 0;
        int index2 = 0;

        //get the elements of every polynomial
        ArrayList<Element> firstPolynomialElements = firstPolynomial.getElements();
        ArrayList<Element> secondPolynomialElements = secondPolynomial.getElements();

        while(index1 <= firstPolynomial.size && index2 <= secondPolynomial.size){

            //if the first polynomial has a greater power than the second one, then add the element from the first polynomial into the result one
            if(firstPolynomialElements.get(index1).power > secondPolynomialElements.get(index2).power){

                polynomial.addElement(firstPolynomialElements.get(index1));
                index1++;
            }
            else{
                //exactly like the case above, the only difference is adding the negative coefficient of the second one in the result
                if(firstPolynomialElements.get(index1).power < secondPolynomialElements.get(index2).power)
                    polynomial.addElement(new Element(secondPolynomialElements.get(index2).power, secondPolynomialElements.get(index2).coefficient * (-1)));

                else{
                    //if the coefficients are equal, then don't add anything
                    if(!secondPolynomialElements.get(index2).coefficient.equals(firstPolynomialElements.get(index1).coefficient))
                        polynomial.addElement(new Element(secondPolynomialElements.get(index2).power, firstPolynomialElements.get(index1).coefficient - secondPolynomialElements.get(index2).coefficient));

                    index1++;
                }
                index2++;
            }
        }
        //if there are more elements in any of the lists
        //enter the loop and add them in the result list
        while(index1 <= firstPolynomial.size){

            polynomial.addElement(firstPolynomialElements.get(index1));
            index1++;
        }

        while(index2 <= secondPolynomial.size){

            polynomial.addElement(new Element(secondPolynomialElements.get(index2).power, secondPolynomialElements.get(index2).coefficient * -1));
            index2++;
        }

        return polynomial;
    }

    //function for the multiplication of the 2 polynomials
    public Polynomial multiplication(){

        Polynomial polynomial = new Polynomial();

        //getting the lists of elements from the 2 polynomial
        ArrayList<Element> firstPolynomialElements = firstPolynomial.getElements();
        ArrayList<Element> secondPolynomialElements = secondPolynomial.getElements();

        //variables for power
        int power;
        double coefficient;

        //AVL where the elements will be stored
        Node root = new Node(new Element(-1, null));

        for(Element firstElements : firstPolynomialElements){

            for(Element secondElements : secondPolynomialElements){

                power = firstElements.power + secondElements.power;
                coefficient = firstElements.coefficient * secondElements.coefficient;

                if(root.element.power == -1)
                    root.element = new Element(power, coefficient);

                else
                    root = root.insert(root, new Element(power, coefficient));
            }
        }

        //setting the list of the polynomial
        polynomial.setElements(root.list(root));
        return polynomial;
    }

    public List<Polynomial> division(){

        //storage area of the reminder and quotient
        List<Polynomial> polynomialList = new LinkedList<Polynomial>();

        //the resulting polynomials
        Polynomial quotient = new Polynomial();

        //if the first polynomial has a degree
        //which is smaller than the second one
        //then the quotient will be null and
        //the reminder will be the first polynomial
        if(firstPolynomial.getDegree() < secondPolynomial.getDegree()){

            polynomialList.add(quotient);
            polynomialList.add(firstPolynomial);

            return polynomialList;
        }

        //if the division can be performed
        int secondPower = secondPolynomial.getElements().get(0).power;

        while(firstPolynomial.getElements().size() > 0 && firstPolynomial.getElements().get(0).power >= secondPower){

            //getting the power and the coefficient of the new element
            //which is added in quotient
            int power = firstPolynomial.getElements().get(0).power - secondPower;
            double coefficient = firstPolynomial.getElements().get(0).coefficient / secondPolynomial.getElements().get(0).coefficient;

            quotient.addElement(new Element(power, coefficient));

            //an auxiliary polynomial which is used for subtraction
            Polynomial polynomial1 = new Polynomial();

            for (Element element : secondPolynomial.getElements()){

                //this polynomial will contain the elements from the second one
                //but all the elements will be multiplied by the last element added in quotient
                polynomial1.addElement(new Element(element.power + power, element.coefficient * coefficient));
            }
            //performing the subtraction
            Operations operations = new Operations(firstPolynomial, polynomial1);
            firstPolynomial = operations.difference();
        }
        //the remainder will be the first polynomial
        polynomialList.add(quotient);

        //add the remainder if it is != 0
        if(firstPolynomial.getElements().size() != 0)
            polynomialList.add(firstPolynomial);

        return polynomialList;
    }
}
