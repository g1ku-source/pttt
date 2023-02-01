package PolynomialPackage;

import java.util.ArrayList;

public class Polynomial {

    //every Polynomial has an "array" of elements
    private ArrayList<Element> elements = new ArrayList<Element>();

    //can add only powers of x which are smaller than the last one introduced
    private Integer lastPower;
    public int size;

    public Polynomial() {

        this.size = -1;
        this.lastPower = null;
    }

    //getters
    public Integer getLastPower() {

        return lastPower;
    }

    public Integer getDegree(){

        return this.elements.get(0).power;
    }

    public ArrayList<Element> getElements() {

        return this.elements;
    }

    public void setElements(ArrayList<Element> list){

        elements = list;
    }

    public void setLastPower(Integer lastPower) {
        this.lastPower = lastPower;
    }

    //function for adding a new element into the polynomial
    public void addElement(Element element) {

        if(element.coefficient == 0)
            return ;

        this.elements.add(element);
        this.lastPower = element.power;

        this.size++;
    }

    //function for derivative operation of the polynomial
    public Polynomial derivative() {

        Polynomial newPolynomial = new Polynomial();

        for (Element element : this.elements)

            //check if power is 0
            //if it is 0 => the element was a constant, so the derivative will be 0
            if (element.power != 0)
                newPolynomial.addElement(new Element(element.power - 1, element.coefficient * element.power));

        return newPolynomial;
    }

    //function for the integration operation
    public Polynomial integral() {

        Polynomial newPolynomial = new Polynomial();

        for (Element element : this.elements)
            newPolynomial.addElement(new Element(element.power + 1, element.coefficient / (element.power + 1)));

        return newPolynomial;
    }

    public String polynomialToString() {

        boolean firstElement = true;

        StringBuilder result = new StringBuilder();

        for(Element element : this.getElements()){

            if(firstElement){

                result = new StringBuilder();
                firstElement = false;

            }
            else{

                if(element.coefficient > 0)
                    result.append("+");

            }
            //this if statement check whether the element coefficient is integer
            //if it is then the statement is true
            if ((element.coefficient == Math.floor(element.coefficient)) && !Double.isInfinite(element.coefficient)) {
                int coefficient = element.coefficient.intValue();
                result.append(Integer.toString(coefficient));
            }
            //else round the number to 2 decimals only
            else {

                element.round();
                result.append(element.coefficient.toString());
            }

            result.append("x^");
            result.append(element.power.toString());
        }

        return result.toString();
    }
}
