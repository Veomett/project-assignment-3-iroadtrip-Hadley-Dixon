// Assignment 3 - International Road Trip; Hadley Dixon; CS 245; Prof. Veomett 03

import java.util.*;
import java.io.*;

public class IRoadTrip {

    private Map<String, Map<String, Integer>> countryGraph; // Graph of adjacent countries
    private Map<String, String> countryNameMap; // Map of country names


    // TODO: Javadoc
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
        // TODO: tsvRead(args[2]); // state_name.tsv
    }

    // TODO: Javadoc
    private void txtRead(String file) {
        try (Scanner scan = new Scanner(new File(file))) { // Scanner on .txt: https://www.geeksforgeeks.org/different-ways-reading-text-file-java/
            while (scan.hasNextLine()) { // As long as a next line exists...
                String fileLine = scan.nextLine(); // read line by line
                String[] lineParts = fileLine.split("="); // REGEX: split at '='
                String countryName = lineParts[0].trim(); // before '=' is country name

                if (!countryGraph.containsKey(countryName)) { // If the country is not already in our graph,
                    countryGraph.put(countryName, new HashMap<>()); // Add country to the graph
                }

                if (lineParts.length > 1) { // If the country has neighbors
                    String[] neighborArr = lineParts[1].split(";"); // Create an array of neighboring countries; REGEX: split at ";"

                    for (String neighboringCountry : neighborArr) {
                        String[] neighborStats = neighboringCountry.trim().split(" "); // REGEX: split at " "; Neighbor name and border distance
                        countryGraph.get(countryName).put(neighborStats[0], 0); // Default border distance 0
                    }
                }
            }
        } catch (Exception exception) { // Exceptions: https://www.tutorialspoint.com/javaexamples/exception_method.htm
            System.err.println(exception.getMessage());
            System.exit(1);
        }
    }

    // TODO: Javadoc
    // TODO: Read in .csv file
    private void csvRead(String file) {
        try (Scanner scan = new Scanner(new File(file))) { // Scanner on .csv: https://www.youtube.com/watch?v=rj6vyIn90zk
            if (scan.hasNextLine()) { // Read in next line if exists
                scan.nextLine();
            }

            while (scan.hasNextLine()) { // As long as a next line exists...
                String fileLine = scan.nextLine();
                String[] lineParts = fileLine.split(","); // REGEX: Split at ","
                // TODO: Build a getCountry method
                String countryA = getCountry(lineParts[1].trim()); // Unique ID for country A
                String countryB = getCountry(lineParts[3].trim()); // Unique ID for country B
                int capitalDistance = Integer.parseInt(lineParts[5].trim()); // Distance between capitals of country A and country B in km

                // If country A and country B are adjacent...
                if (countryGraph.containsKey(countryA) && countryGraph.get(countryA).containsKey(countryB)) {
                    // Add capital distances to the respective (undirected) graph
                    countryGraph.get(countryA).put(countryB, capitalDistance);
                    countryGraph.get(countryB).put(countryA, capitalDistance);
                }
            }
        } catch (Exception exception) {
            System.err.println(exception.getMessage());
            System.exit(1);
        }
    }

    // TODO: Javadoc
    // Helper method to get unique country ID, used in
    private String getCountry(String country) {
        if (countryNameMap.containsKey(country)) {
            return countryNameMap.get(country);
        } return null;
    }

    // TODO: Javadoc
    private void tsvRead(String file) {
        try (Scanner scan = new Scanner(new File(file))) { // Scanner .tsv: https://codepal.ai/code-generator/query/0pkdvNiV/java-program-read-student-information
            if (scan.hasNextLine()) { // Read in next line if exists
                scan.nextLine();
            }

            while (scan.hasNextLine()) { // As long as a next line exists...
                String fileLine = scan.nextLine();
                String[] lineParts = fileLine.split("\t"); // REGEX: Split at tab
                String encodedName = lineParts[1].trim(); // The encoded name for a country (eg. JAM)
                String decodedName = lineParts[2].trim(); // The decoded name for a country (eg. Jamaica)
                countryNameMap.put(encodedName, decodedName);
            }
        } catch (Exception exception) {
            System.err.println(exception.getMessage());
            System.exit(1);
        }
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

