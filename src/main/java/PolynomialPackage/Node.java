package PolynomialPackage;

import java.util.ArrayList;
import java.util.Stack;

//using avl tree for multiplication because
//the search and the insertion will be
//O(log n*m) where n and m are the number of
//elements in the first and second polynomial
public class Node {

    public Element element;
    public Node left;
    public Node right;
    public Integer height;

    //constructor

    public Node(Element element){

        this.element = element;
        this.left = null;
        this.right = null;

        this.height = 1;
    }
    //helping functions

    //function for getting the height of a node
    //if the node is null then the value will be 0
    private int getHeight(Node node){

        if(node == null)
            return 0;

        return node.height;
    }

    //function for getting the maximum between 2 numbers
    private int max(int first, int second){

        return Math.max(first, second);
    }

    //function for balancing the tree
    private int balance(Node node){

        if(node == null)
            return 0;

        return getHeight(node.left) - getHeight(node.right);
    }

    //left rotation
    private Node leftRotation(Node node){

        Node newNode = node.right;
        Node node2 = newNode.left;

        newNode.left = node;
        node.right = node2;

        node.height = max(getHeight(node.left), getHeight(node.right)) + 1;
        newNode.height = max(getHeight(newNode.left), getHeight(newNode.right)) + 1;

        return newNode;
    }

    //right rotation
    private Node rightRotation(Node node){

        Node newNode = node.left;
        Node node2 = newNode.right;

        newNode.right = node;
        node.left = node2;

        node.height = max(getHeight(node.left), getHeight(node.right)) + 1;
        newNode.height = max(getHeight(newNode.left), getHeight(newNode.right)) + 1;

        return newNode;
    }

    //function for insertion
    //which is a basic BST insert and after that the
    //tree will be balanced
    public Node insert(Node node, Element element){

        //basic BST insertion
        if (node == null)
            return (new Node(element));

        if (element.power < node.element.power)
            node.left = insert(node.left, element);

        else if (element.power > node.element.power)
            node.right = insert(node.right, element);

        else
            node.element.coefficient += element.coefficient;

        node.height = 1 + max(getHeight(node.left), getHeight(node.right));

        int balanced = balance(node);

        //rotation cases for the avl balanced tree
        if(balanced > 1){

            if(node.left.element.power < element.power){

                 node.left = leftRotation(node.left);
                 return rightRotation(node);
            }

            if(node.left.element.power > element.power)
                return rightRotation(node);
        }

        if(balanced < -1){

            if(element.power > node.right.element.power)
                return leftRotation(node);

            if(element.power < node.right.element.power){

                node.right = rightRotation(node.right);
                return leftRotation(node);
            }
        }

        return node;
    }

    //iterative way to get all the elements in a decreasing order
    public ArrayList<Element> list(Node root) {

        ArrayList<Element> list = new ArrayList<Element>();

        //using a stack
        Stack<Node> stack = new Stack<Node>();
        Node node = root;

        //if there are not any nodes in the left subtree OR the stack has no elements inside, then stop
        while (node != null || stack.size() > 0) {

            //going to the right as much as possible
            while (node != null) {

                stack.push(node);
                node = node.right;
            }
            //popping from stack and adding in the resulting list
            //of elements
            node = stack.pop();

            list.add(node.element);
            node = node.left;
        }
        //returning the list
        return list;
    }
}
