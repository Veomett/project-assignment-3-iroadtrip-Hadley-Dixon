// Project 3 - International Road Trip; Hadley Dixon; Prof. Veomett; CS 245 03

import java.util.*;
import java.io.*;

/**
 *  IRoadTrip class implements the shortest path trip between two countries. Sources available in README.md
 */
public class IRoadTrip {

    private Map<String, Map<String, Integer>> countryGraph; // Graph of adjacent countries
    private Map<String, String> countryNameMap; // Map of country names


    /**
     * Constructor for the IRoadTrip class. Method reads the files and prepares to execute the implementation to
     * respond to requests. The implementation must halt on any failure here.
     * @param args String array that contains the names of the files as provides to the main.
     */
    public IRoadTrip (String [] args) {
        // Handle when invalid input
        if (args.length != 3) {
            System.err.println("Invalid input");
            System.err.println("Input: 'java IRoadTrip borders.txt capdist.csv state_name.tsv'");
            System.exit(1);
        }

        countryGraph = new HashMap<>(); // Initialize adjacent country graph
        countryNameMap = new HashMap<>(); // Initialize map of country names

        // Read in files
        tsvRead(args[2]); // state_name.tsv
        txtRead(args[0]); // borders.txt
        csvRead(args[1]); // capdist.csv
    }

    /**
     * Method accounts for different references to the same country and transforms country name to its 'official' title.
     * @param inputCountry String initial country name
     * @return String 'official' country name. See README.md for the 'official' name requirements
     */
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
        return inputCountry; // Returns official country name
    }

    /**
     * Method reads 'state_name.tsv' and creates a map of country names and their unique 3-letter IDs.
     * See README.md for details on country specifications.
     * @param file String file name
     */
    private void tsvRead(String file) {
        try (Scanner scan = new Scanner(new File(file))) {
            // Read in next line if exists
            if (scan.hasNextLine()) {
                scan.nextLine();
            }

            while (scan.hasNextLine()) { // Next line exists
                String fileLine = scan.nextLine();
                String[] lineParts = fileLine.split("\t"); // Split country name from country ID. REGEX: Split at tab
                if (lineParts.length == 5 && Objects.equals(lineParts[4], "2020-12-31")) { // Extract only 2020 data
                    String encodedName = lineParts[1].trim(); // The encoded name for a country (eg. JAM)
                    String decodedName = lineParts[2].trim(); // The decoded name for a country (eg. Jamaica)
                    String transformedDecodedName = transform(decodedName); // Transform country name to official title
                    countryNameMap.put(transformedDecodedName, encodedName); // Assign key:value in country name map

                }
            }

            // TESTING
            System.out.println("If 3-letter country ID from 2020 is present in 'state_name.tsv', country is added to map of valid country names");
            System.out.println(countryNameMap);
            System.out.println();

        } catch (Exception exception) {
            System.err.println(exception.getMessage());
            System.exit(1);
        }
    }

    /**
     * Method reads 'borders.txt' and creates a graph of countries and their adjacent nations.
     * See README.md for details on country specifications.
     * @param file String filename
     */
    private void txtRead(String file) {
        try (Scanner scan = new Scanner(new File(file))) {
            while (scan.hasNextLine()) { // Next line exists
                String fileLine = scan.nextLine();
                String[] lineParts = fileLine.split("= "); // Split country name from adjacent countries. REGEX: split at '=', limited by 2 parts
                String countryName = lineParts[0].trim(); // Country name precedes '='
                if (!countryGraph.containsKey(countryName) && countryNameMap.containsKey(countryName)) { // If the country is not already in our graph, and it has a 3-letter ID tag
                    countryGraph.put(countryName, new HashMap<>()); // Add country to the graph

                    if (lineParts.length > 1) { // The country has adjacent countries
                        String[] neighborArr = lineParts[1].split(";"); // Split differing adjacent countries from one another.  REGEX: split at ";"

                        for (String neighboringCountry : neighborArr) { // Loop through adjacent countries
                            String[] neighborStats = neighboringCountry.trim().split("\\s+(?=\\d)", 2); // Split name of adjacent country from border distance. REGEX: split at the first occurrence of digits
                            String testerNeighbor = transform(neighborStats[0]); // Translate country name to 'official' title
                            if (countryNameMap.containsKey(testerNeighbor)) { // Country is a valid country in our country name map
                                countryGraph.get(countryName).put(testerNeighbor, 0); // Set default capital distance 0
                            }
                        }
                    }
                }
            }

            // TEST
            System.out.println("If country in 'borders.txt' is present in the map of valid country names, country is added to a graph of adjacent countries (default distance between capitals is 0)");
            System.out.println(countryGraph);
            System.out.println();

        } catch (Exception exception) {
            System.err.println(exception.getMessage());
            System.exit(1);
        }
    }

    /**
     * Method gets a country's unique 3-letter ID from the country name map.
     * @param ID String 3-letter country ID
     * @return Official name of country corresponding to input ID
     */
    private String getCountry(String ID) {
        // Loop through valid countries in country name map
        for (String country : countryNameMap.keySet()) {
            if (Objects.equals(countryNameMap.get(country), ID)) { // ID matches an ID of one of the valid countries
                return country; // Return 'official' country name
            }
        } return null;
    }

    /**
     * Method reads 'capdist.csv' and extracts distances between capitals of 2 adjacent countries, and adds it to
     * existing adjacent country graph.
     * Map is generated with countries that meet the following requirements are added to map, sequentially:
     * (1) Country 3-letter ID is specified in 'state_name.tsv'
     * (2) Country also exists in 'border.txt'
     * (3) Country found in both previous files, has 2020 distance data in 'capdist.csv'
     * @param file String file name
     */
    private void csvRead(String file) {
        try (Scanner scan = new Scanner(new File(file))) {
            if (scan.hasNextLine()) { // Read in next line if exists
                scan.nextLine();
            }
            while (scan.hasNextLine()) { // Next line exists
                String fileLine = scan.nextLine();
                String[] lineParts = fileLine.split(","); // REGEX: Split at ","
                String idCountryA = lineParts[1].trim(); // Unique ID for country A
                String idCountryB = lineParts[3].trim(); // Unique ID for country B
                int capitalDistance = Integer.parseInt(lineParts[4].trim()); // Distance between capitals of country A and country B in km

                // Hardcode to handle edge cases
                if (idCountryA.equals("UK")) {
                    idCountryA = "UKG";
                } else if (idCountryB.equals("UK")) {
                    idCountryB = "UKG";
                }

                // Get the 'official' country name from country name map
                String countryA = getCountry(idCountryA);
                String countryB = getCountry(idCountryB);

                // Graph of adjacent countries contains country A and country B is an adjacent country to country A
                if (countryGraph.containsKey(countryA) && countryGraph.get(countryA).containsKey(countryB)) {
                    // Add the distance between capitals of country A and country B to the graph of adjacent countries
                    countryGraph.get(countryA).put(countryB, capitalDistance);
                    countryGraph.get(countryB).put(countryA, capitalDistance);
                }
            }

            // TEST
            System.out.println("If distance data is present in 'capdist.csv', distance between capitals of adjacent countries is adjusted in graph");
            System.out.println(countryGraph);
            System.out.println();

        } catch (Exception exception) {
            System.err.println(exception.getMessage());
            System.exit(1);
        }
    }

    /**
     * Method returns -1 if the countries don't share a border, and return the distance between the two countries'
     * capitals if they do share a border.
     * @param country1 String name of a country
     * @param country2 String name of a country
     * @return int distance between the capitals of country1 and country2, -1 otherwise
     */
    public int getDistance(String country1, String country2) {
        // TODO: getDistance
        return -1;
    }

    /**
     * Method returns the path that Dijkstra's algorithm finds, which is the shortest path between two countries,
     * starting in a capital, ending in a capital, and going through the capital of any intermittent countries. Method
     * reports distance along the path.
     * @param country1 String name of a country
     * @param country2 String name of a country
     * @return The shortest from country1 to country2 through respective capitals along the path.
     */
    public List<String> findPath(String country1, String country2) {
        // TODO: findPath
        return null;
    }

    /**
     * Method allows a user to interact with your implementation on the console. Receives and validates the names of
     * two countries from a user. Method does not accept invalid names. Once two valid country names have been entered
     * by the user, method prints the path between those countries if such a path exists.
     */
    public void acceptUserInput() {
        Scanner scan = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            System.out.print("Enter the name of the first country (type EXIT to quit): "); // Prompt user for first country
            String country1 = scan.nextLine().trim();

            if (country1.equalsIgnoreCase("EXIT")) { // User exits program
                exit = true;
            } else {

                if (!countryGraph.containsKey(country1)) { // Input country is an invalid country
                    System.out.println("Invalid country name. Please enter a valid country name.");
                    System.out.println("See README.md for valid country requirements");
                    continue;
                }

                // Handle edge case. See README.md for valid country requirements.
                if (country1.equals("Kosovo") || country1.equals("South Sudan")) {
                    System.out.println("Valid country, but no available distance data.");
                    System.out.println("Please enter a new country name.");
                    continue;
                }

                System.out.print("Enter the name of the second country (type EXIT to quit): "); // Prompt user for first country
                String country2 = scan.nextLine().trim();

                if (country2.equalsIgnoreCase("EXIT")) { // User exits program
                    exit = true;
                } else {

                    if (!countryGraph.containsKey(country2)) { // Input country is an invalid country
                        System.out.println("Invalid country name. Please enter a valid country name.");
                        System.out.println("See README.md for valid country requirements");
                        continue;
                    }

                    // Handle edge case. See README.md for valid country requirements.
                    if (country2.equals("Kosovo") || country2.equals("South Sudan")) {
                        System.out.println("Valid country, but no available distance data.");
                        System.out.println("Please enter a different new name.");
                        continue;
                    }

                    // Find path between 2 valid countries
                    List<String> travelPath = findPath(country1, country2);

                    if (!travelPath.isEmpty()) { // A valid travel path between country 1 and country 1 exists
                        System.out.println("Route from " + country1 + " to " + country2 + ":");
                        int totalDistance = 0; // Distance counter
                        for (int i = 0; i < travelPath.size() - 1; i++) {
                            String currentCountry = travelPath.get(i);
                            String nextCountry = travelPath.get(i + 1);
                            int distance = countryGraph.get(currentCountry).get(nextCountry); // Extract distance between capitals
                            totalDistance += distance; // Increment total distance
                            System.out.println("* " + currentCountry + " --> " + nextCountry + " (" + distance + " km.)"); // Output travel path
                        }
                        System.out.println("Total distance: " + totalDistance + " km."); // Output total distance traveled
                    } else { // No valid travel path between country 1 and country 1 exists
                        System.out.println("No path found between " + country1 + " and " + country2);
                    }
                }
            }
        } scan.close(); // Close scanner after the loop
    }

    /**
     * Main code provided; IRoadTrip class will be called through the main function as follows:
     * 'java IRoadTrip borders.txt capdist.csv state_name.tsv'
     * @param args String array of file names.
     */
    public static void main (String[] args){
        IRoadTrip a3 = new IRoadTrip(args);
        a3.getDistance("USF", "My House"); // TEST
        a3.getDistance("France", "Spain"); // TEST
        a3.getDistance("Canada", "Panama"); // TEST
        a3.acceptUserInput();
    }
}

