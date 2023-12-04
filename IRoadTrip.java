// Assignment 3 - International Road Trip; Hadley Dixon; CS 245; Prof. Veomett 03

import java.util.*;
import java.io.*;

public class IRoadTrip {

    private Map<String, Map<String, Integer>> countryGraph; // Graph of adjacent countries
    private Map<String, String> countryNameMap; // Map of country names



    public IRoadTrip (String [] args) {
        // Handle when invalid input
        if (args.length != 3) {
            System.err.println("Invalid input");
            System.err.println("Input: 'java IRoadTrip borders.txt capdist.csv state_name.tsv'");
            System.exit(1);
        }

        // Valid input
        countryGraph = new HashMap<>(); // Initialize adjacent country graph
        countryNameMap = new HashMap<>(); // Initialize map of country names

        // Read in files
        txtRead(args[0]); // borders.txt
        csvRead(args[1]); // capdist.csv
        tsvRead(args[2]); // state_name.tsv
    }

    private void txtRead(String fileName) {
        try (Scanner scan = new Scanner(new File(fileName))) { // Scanner class: https://www.geeksforgeeks.org/different-ways-reading-text-file-java/
            while (scan.hasNextLine()) { // As long as a next line exists...
                String fileLine = scan.nextLine(); // read line by line
                String[] lineParts = fileLine.split("="); // split when '=' character
                String countryName = lineParts[0].trim(); // before '=' is country name

                if (!countryGraph.containsKey(countryName)) { // If the country is not already in our graph,
                    countryGraph.put(countryName, new HashMap<>()); // Add country to the graph
                }

                if (lineParts.length > 1) { // If the country has neighbors
                    String[] neighborArr = lineParts[1].split(";"); // Create an array of neighboring countries

                    for (String neighboringCountry : neighborArr) {
                        String[] neighborStats = neighboringCountry.trim().split(" "); // Split neighbor name from border distance
                        countryGraph.get(countryName).put(neighborStats[0], 0); // Default border distance 0
                    }
                }
            }
        } catch (Exception exception) { // Exceptions: https://www.tutorialspoint.com/javaexamples/exception_method.htm
            System.err.println(exception.getMessage());
            System.exit(1);
        }
    }

    // TODO: Read in .csv file
    private void csvRead(String fileName) {

    }

    // TODO: Read in .tsv file
    private void tsvRead(String fileName) {

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

