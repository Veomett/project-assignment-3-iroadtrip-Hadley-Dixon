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
        tsvRead(args[2]); // state_name.tsv
        txtRead(args[0]); // borders.txt
        csvRead(args[1]); // capdist.csv
    }

    // TODO: Javadoc
    // Helper function to navigate different references to the same country
    private String transform(String inputCountry) {
        if (Objects.equals(inputCountry, "Surinam")) {
            return "Suriname";
        } else if (Objects.equals(inputCountry, "Bosnia-Herzegovina")) {
            return "Bosnia and Herzegovina";
        } else if (Objects.equals(inputCountry, "Burkina Faso (Upper Volta)")) {
            return "Burkina Faso";
        } else if (Objects.equals(inputCountry, "United States of America")) {
            return "United States";
        } else if (Objects.equals(inputCountry, "Myanmar (Burma)")) {
            return "Burma";
        } else if (Objects.equals(inputCountry, "Cape Verde")) {
            return "Cabo Verde";
        } else if (Objects.equals(inputCountry, "Cambodia (Kampuchea)")) {
            return "Cambodia";
        } else if (Objects.equals(inputCountry, "Congo, Democratic Republic of (Zaire)")) {
            return "Congo, Democratic Republic of the";
        } else if (Objects.equals(inputCountry, "Congo")) {
            return "Congo, Republic of the";
        } else if (Objects.equals(inputCountry, "Czech Republic")) {
            return "Czechia";
        } else if (Objects.equals(inputCountry, "Swaziland")) {
            return "Eswatini";
        } else if (Objects.equals(inputCountry, "The Gambia") || Objects.equals(inputCountry, "Gambia")) {
            return "Gabon";
        } else if (Objects.equals(inputCountry, "German Federal Republic")) {
            return "Germany";
        } else if (Objects.equals(inputCountry, "Iran (Persia)")) {
            return "Iran";
        } else if (Objects.equals(inputCountry, "(Italy/Sardinia")) {
            return "Italy";
        } else if (Objects.equals(inputCountry, "Korea, People's Republic of") || Objects.equals(inputCountry, "North Korea")) {
            return "Korea, North";
        } else if (Objects.equals(inputCountry, "Korea, Republic of")) {
            return "Korea, South";
        } else if (Objects.equals(inputCountry, "Kyrgyz Republic")) {
            return "Kyrgyzstan";
        } else if (Objects.equals(inputCountry, "Macedonia (Former Yugoslav Republic of)")) {
            return "North Macedonia";
        } else if (Objects.equals(inputCountry, "Cote D’Ivoire")) {
            return "Cote d'Ivoire";
        } else if (Objects.equals(inputCountry, "Rumania")) {
            return "Romania";
        } else if (Objects.equals(inputCountry, "Russia (Soviet Union)")) {
            return "Russia";
        } else if (Objects.equals(inputCountry, "Tanzania/Tanganyika")) {
            return "Tanzania";
        } else if (Objects.equals(inputCountry, "East Timor")) {
            return "Timor-Leste";
        } else if (Objects.equals(inputCountry, "Turkey (Ottoman Empire)")) {
            return "Turkey (Turkiye)";
        } else if (Objects.equals(inputCountry, "UAE")) {
            return "Turkey (United Arab Emirates)";
        } else if (Objects.equals(inputCountry, "Vietnam, Democratic Republic of")) {
            return "Vietnam";
        } else if (Objects.equals(inputCountry, "Yemen (Arab Republic of Yemen)")) {
            return "Yemen";
        } else if (Objects.equals(inputCountry, "UK")) {
            return "United Kingdom";
        }
        return inputCountry;
    }

    // TODO: Javadoc
    // Creates a map of country names and their unique 3-letter IDs corresponding to 2020, but only if the country is present in both state_name,csv and borders.txt (that way we can identify the 3-letter unique ID for capdist application, and not waste time
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
                    String transformedDecodedName = transform(decodedName);
                    countryNameMap.put(transformedDecodedName, encodedName);

                }
            }

            System.out.println("Country Name Map: " + countryNameMap); // TEST

        } catch (Exception exception) {
            System.err.println(exception.getMessage());
            System.exit(1);
        }
    }

    // TODO: Javadoc
    // Creates a graph of the countries in borders.txt (which we have 2020 3-letter ID from in state_name) and their associated adjacent countries
    private void txtRead(String file) {
        try (Scanner scan = new Scanner(new File(file))) { // See source (2) in README.md
            while (scan.hasNextLine()) { // As long as a next line exists...
                String fileLine = scan.nextLine(); // read line by line
                String[] lineParts = fileLine.split("= "); // REGEX: split at '=', limited by 2 parts
                String countryName = lineParts[0].trim(); // before '=' is country name
                if (!countryGraph.containsKey(countryName) && countryNameMap.containsKey(countryName)) { // If the country is not already in our graph, and it has a 3-letter ID tag
                    countryGraph.put(countryName, new HashMap<>()); // Add country to the graph

                    if (lineParts.length > 1) { // If the country has neighbors
                        String[] neighborArr = lineParts[1].split(";"); // Create an array of neighboring countries; REGEX: split at ";"

                        for (String neighboringCountry : neighborArr) {
                            String[] neighborStats = neighboringCountry.trim().split("\\s+(?=\\d)", 2); // REGEX: split at the first occurrence of digits; Neighbor name and capital distance
                            String testerNeighbor = transform(neighborStats[0]);
                            if (countryNameMap.containsKey(testerNeighbor)) {
                                countryGraph.get(countryName).put(testerNeighbor, 0); // Default capital distance 0
                            }
                        }
                    }
                }
            }

        } catch (Exception exception) { // See source (3) in README.md
            System.err.println(exception.getMessage());
            System.exit(1);
        }
    }

    // TODO: Javadoc
    // Helper method to get a country's unique 3-letter ID
    private String getCountry(String ID) {
        for (String country : countryNameMap.keySet()) {
            if (Objects.equals(countryNameMap.get(country), ID)) {
                return country;
            }
        } return null;
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
                String idCountryA = lineParts[1].trim(); // Unique ID for country A
                String idCountryB = lineParts[3].trim(); // Unique ID for country B
                int capitalDistance = Integer.parseInt(lineParts[5].trim()); // Distance between capitals of country A and country B in km

                if (idCountryA.equals("UK")) {
                    idCountryA = "UKG";
                } else if (idCountryB.equals("UK")) {
                    idCountryB = "UKG";
                }

                // valid names, and adjacent...
                String countryA = getCountry(idCountryA);
                String countryB = getCountry(idCountryB);

                if (countryGraph.containsKey(countryA) && countryGraph.get(countryA).containsKey(countryB)) {
                    // Add capital distances to the respective graph
                    countryGraph.get(countryA).put(countryB, capitalDistance);
                    countryGraph.get(countryB).put(countryA, capitalDistance);
                }
            }
            System.out.println("Country graph with distances now: " + countryGraph);

        } catch (Exception exception) {
            System.err.println(exception.getMessage());
            System.exit(1);
        }
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
    public void acceptUserInput() {
        Scanner scan = new Scanner(System.in); // See source (6) in README.md

        boolean exit = false;

        while (!exit) {
            System.out.print("Enter the name of the first country (type EXIT to quit): ");
            String country1 = scan.nextLine().trim();

            if (country1.equalsIgnoreCase("EXIT")) {
                exit = true;
            } else {

                if (!countryGraph.containsKey(country1)) {
                    System.out.println("Invalid country name. Please enter a valid country name.");
                    continue;
                }

                if (country1.equals("Kosovo") || country1.equals("South Sudan") ) {
                    System.out.println("Valid country in graph, however no available data in distance file.");
                    System.out.println("Please enter a different country name.");
                    continue;
                }

                System.out.print("Enter the name of the second country (type EXIT to quit): ");
                String country2 = scan.nextLine().trim();

                if (country2.equalsIgnoreCase("EXIT")) {
                    exit = true;
                } else {

                    if (!countryGraph.containsKey(country2)) {
                        System.out.println("Invalid country name. Please enter a valid country name.");
                        continue;
                    }

                    if (country2.equals("Kosovo") || country2.equals("South Sudan") ) {
                        System.out.println("Valid country in graph, however no available data in distance file.");
                        System.out.println("Please enter a different country name.");
                        continue;
                    }

                    // TODO: Find path between 2 valid countries
                    /*List<String> travelPath = findPath(country1, country2);

                    if (!travelPath.isEmpty()) { // Valid travel path between countries
                        System.out.println("Route from " + country1 + " to " + country2 + ":");
                        for (String country : travelPath) { // Step through countries along path
                            System.out.println("* " + country);
                        }
                    } else { // No valid travel path between countries
                        System.out.println("No path found between " + country1 + " and " + country2);
                    }*/
                }
            }
            scan.close();
        }
    }

    // TODO: Javadoc
    // Main code provided
    public static void main (String[] args){
        IRoadTrip a3 = new IRoadTrip(args);
        a3.acceptUserInput();
    }
}

