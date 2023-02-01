package PolynomialPackage;

public class Element {

    //each element has a natural power and an integer coefficient
    public Integer power;
    public Double coefficient;

    //constructor function for the elements
    public Element(Integer power, Double coefficient) {

        this.power = power;
        this.coefficient = coefficient;
    }

    public void round(){

        //snippet used for rounding a double value
        //to 2 decimals only
        //code from stackoverflow
        //https://stackoverflow.com/questions/2808535/round-a-double-to-2-decimal-places
        int factor = (int) Math.pow(10, 2);
        this.coefficient = this.coefficient * factor;
        long tmp = Math.round(this.coefficient);
        this.coefficient = (double) tmp / factor;
    }
}
