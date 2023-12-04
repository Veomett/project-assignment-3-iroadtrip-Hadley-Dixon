// Assignment 3 - International Road Trip; Hadley Dixon; CS 245; Prof. Veomett 03

import java.util.*;
import java.io.*;

public class IRoadTrip {

    private Map<String, Map<String, Integer>> adjacencyList; // Graph of adjacent countries
    private Map<String, String> stateNameMap; // Map of country names



    public IRoadTrip (String [] args) {
        // Handle when invalid input
        if (args.length != 3) {
            System.err.println("Invalid input");
            System.err.println("Input: 'java IRoadTrip borders.txt capdist.csv state_name.tsv'");
            System.exit(1);
        }

        // Valid input
        adjacencyList = new HashMap<>(); // Initialize adjacent country graph
        stateNameMap = new HashMap<>(); // Initialize map of country names

        // TODO: Read in files

    }


    public int getDistance (String country1, String country2) {
        // Replace with your code
        return -1;
    }


    public List<String> findPath (String country1, String country2) {
        // Replace with your code
        return null;
    }


    public void acceptUserInput() {
        // Replace with your code
        System.out.println("IRoadTrip - skeleton");
    }


    public static void main(String[] args) {
        IRoadTrip a3 = new IRoadTrip(args);

        a3.acceptUserInput();
    }

}

