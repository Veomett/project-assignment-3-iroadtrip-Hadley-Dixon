// Project 3 - International Road Trip; Hadley Dixon; Prof. Veomett; CS 245 03

import java.util.*;
import java.io.*;

// TODO: Javadoc
public class IRoadTrip {

    // See source (1) in README.md
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
        tsvRead(args[2]); // state_name.tsv
        csvRead(args[1]); // capdist.csv



    }

    // TODO: Javadoc
    // Creates a graph of all the countries in borders.txt and their associated adjacent countries
    private void txtRead(String file) {
        try (Scanner scan = new Scanner(new File(file))) { // See source (2) in README.md
            while (scan.hasNextLine()) { // As long as a next line exists...
                String fileLine = scan.nextLine(); // read line by line
                String[] lineParts = fileLine.split("= "); // REGEX: split at '=', limited by 2 parts
                String countryName = lineParts[0].trim(); // before '=' is country name
                if (!countryGraph.containsKey(countryName)) { // If the country is not already in our graph,
                    countryGraph.put(countryName, new HashMap<>()); // Add country to the graph
                }

                if (lineParts.length > 1) { // If the country has neighbors
                    String[] neighborArr = lineParts[1].split(";"); // Create an array of neighboring countries; REGEX: split at ";"

                    for (String neighboringCountry : neighborArr) {
                        String[] neighborStats = neighboringCountry.trim().split("\\s+(?=\\d)", 2); // REGEX: split at the first occurrence of digits; Neighbor name and capital distance
                        countryGraph.get(countryName).put(neighborStats[0], 0); // Default capital distance 0
                    }
                }
            }

            System.out.println("Country Graph: " + countryGraph); // TEST

        } catch (Exception exception) { // See source (3) in README.md
            System.err.println(exception.getMessage());
            System.exit(1);
        }
    }

    // TODO: Javadoc
    // Creates a map of country names and their unique 3-letter IDs corresponding to 2020
    private void tsvRead(String file) {
        try (Scanner scan = new Scanner(new File(file))) { // See source (5) in README.md
            if (scan.hasNextLine()) { // Read in next line if exists
                scan.nextLine();
            }

            while (scan.hasNextLine()) { // As long as a next line exists...
                String fileLine = scan.nextLine();
                String[] lineParts = fileLine.split("\t"); // REGEX: Split at tab
                if (lineParts.length == 5 && Objects.equals(lineParts[4], "2020-12-31")) {
                    String encodedName = lineParts[1].trim(); // The encoded name for a country (eg. JAM)
                    String decodedName = lineParts[2].trim(); // The decoded name for a country (eg. Jamaica)
                    countryNameMap.put(encodedName, decodedName);
                }
            }

            System.out.println("Country Name Map: " + countryNameMap); // TEST

        } catch (Exception exception) {
            System.err.println(exception.getMessage());
            System.exit(1);
        }
    }

    // TODO: Javadoc
    private void csvRead(String file) {
        try (Scanner scan = new Scanner(new File(file))) { // See source (4) in README.md
            if (scan.hasNextLine()) { // Read in next line if exists
                scan.nextLine();
            }
            while (scan.hasNextLine()) { // As long as a next line exists...
                String fileLine = scan.nextLine();
                String[] lineParts = fileLine.split(","); // REGEX: Split at ","
                // Get country name from the unique 3-letter ID only if this is a valid ID in countryNameMap
                String countryA = getCountry(lineParts[1].trim()); // Unique ID for country A
                String countryB = getCountry(lineParts[3].trim()); // Unique ID for country B
                int capitalDistance = Integer.parseInt(lineParts[5].trim()); // Distance between capitals of country A and country B in km


                System.out.println("countryA: " + countryA);
                System.out.println("countryB: " + countryB);
                System.out.println("distance: " + capitalDistance);

                // If country A and country B are non-null, valid names, and adjacent...
                if (countryA != null && countryB != null) {
                    System.out.println("countryA in graph: " + countryChecker(countryA));
                    System.out.println("countryB in graph: " + countryChecker(countryB));
                    if (countryChecker(countryA) != null && countryChecker(countryB) != null) {
                        if (countryGraph.containsKey(countryA) && countryGraph.get(countryA).containsKey(countryB)) {
                            // Add capital distances to the respective (undirected) graph
                            //System.out.println("START");
                            countryGraph.get(countryA).put(countryB, capitalDistance);
                            //System.out.println("FIRST_GRAB");
                            // TODO: I try to get Czech republic node in countryGraph, and assign 162 to the distance between Austria and Czech republic, but it only exists as Czechia in my graph
                            countryGraph.get(countryB).put(countryA, capitalDistance); // TODO: ERROR
                            //System.out.println("SECOND_GRAB");

                        }
                    }
                }

                // System.out.println("END");
                // System.out.println();
            }

            System.out.println("Country graph with distances now: " + countryGraph);

        } catch (Exception exception) {
            System.err.println(exception.getMessage());
            System.exit(1);
        }
    }

    // TODO: Javadoc
    // Helper method to get a country's unique 3-letter ID
    private String getCountry(String country) {
        if (countryNameMap.containsKey(country)) {
            return countryNameMap.get(country);
        } return null;
    }

    // TODO: getDistance
    // TODO: Javadoc
    public int getDistance (String country1, String country2) {
        // Replace with your code
        return -1;
    }


    // TODO: findPath
    // TODO: Javadoc
    public List<String> findPath (String country1, String country2) {
        // Replace with your code
        return null;
    }

    // TODO: Javadoc
    // Helper function to navigate different references to the same country without hardcode
    private String countryChecker(String country) {

        // Iterate through all valid country names (keys) in graph
        for (String validCountry : countryGraph.keySet()) {
            // Check if the input country is an exact match or a substring of a valid country in the graph
            if (validCountry.equals(country)) {
                //System.out.println("in country graph"); // TEST
                return validCountry; // the input country is valid
            } else if (Objects.equals(country, "Dominican Republic")) { // Edge case
                return "Dominican Republic";
            } else if (validCountry.contains(country) || country.contains(validCountry)) {
                return validCountry;
            }
        }

        // Iterate through the keys (encoded names) in countryNameMap
        for (String encodedName : countryNameMap.keySet()) {
            // Check if the input country is an exact match with a valid encoded country in the map
            if (encodedName.equals(country)) {
                System.out.println("in name map"); // TEST
                return country; // the input country is valid
            }
        }

        //System.out.println("FALSE"); // TEST
        return null;
    }

    /*// TODO: Javadoc
    public void acceptUserInput() {
        Scanner scan = new Scanner(System.in); // See source (6) in README.md

        boolean exit = false;

        while (!exit) {
            System.out.print("Enter the name of the first country (type EXIT to quit): ");
            String country1 = scan.nextLine().trim();

            if (country1.equalsIgnoreCase("EXIT")) {
                exit = true;
            } else {
                boolean validCountry1 = countryChecker(country1);

                if (!validCountry1) {
                    System.out.println("Invalid country name. Please enter a valid country name.");
                    continue;
                }

                System.out.print("Enter the name of the second country (type EXIT to quit): ");
                String country2 = scan.nextLine().trim();

                if (country2.equalsIgnoreCase("EXIT")) {
                    exit = true;
                } else {
                    boolean validCountry2 = countryChecker(country2);

                    if (!validCountry2) {
                        System.out.println("Invalid country name. Please enter a valid country name.");
                        continue;
                    }

                    // TODO: Find path between 2 valid countries
                    *//*List<String> travelPath = findPath(country1, country2);

                    if (!travelPath.isEmpty()) { // Valid travel path between countries
                        System.out.println("Route from " + country1 + " to " + country2 + ":");
                        for (String country : travelPath) { // Step through countries along path
                            System.out.println("* " + country);
                        }
                    } else { // No valid travel path between countries
                        System.out.println("No path found between " + country1 + " and " + country2);
                    }*//*
                }
            }
            scan.close();
        }
    }*/

    // TODO: Javadoc
    // Main code provided
    public static void main (String[] args){
        IRoadTrip a3 = new IRoadTrip(args);
        // a3.acceptUserInput();
    }
}

