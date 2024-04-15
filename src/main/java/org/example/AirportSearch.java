package org.example;

import java.io.*;
import java.util.*;

public class AirportSearch {

    private Map<String, List<Integer>> index = new HashMap<>();

    public AirportSearch(String airportsFile, int searchColumn) {
        long startTime = System.currentTimeMillis();
        buildIndex(airportsFile, searchColumn);
        long endTime = System.currentTimeMillis();
        System.out.println("Building binary tree took: " + (endTime - startTime) + " milliseconds");
    }

    private void buildIndex(String airportsFile, int searchColumn) {
        List<String> fileTxt = readTxt("input.txt");
        try (FileInputStream fis = new FileInputStream(airportsFile);
             InputStreamReader isr = new InputStreamReader(fis);
             BufferedReader reader = new BufferedReader(isr)) {
            String line;
            int row = 0;

            while ((line = reader.readLine()) != null) {
                String[] columns = line.split(",");
                if (columns.length > searchColumn) {
                    String key = columns[searchColumn].trim().replace("\"", "");
                    if (!index.containsKey(key)) {
                        index.put(key, new ArrayList<>());
                    }
                    index.get(key).add(row);
                }
                row++;
            }

            System.out.println(index);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void search() {
        Map<String, List<Integer>> result = new HashMap<>();
        List<String> fileTxt = readTxt("input.txt");
        long startTime = System.currentTimeMillis();
        for (String key : index.keySet()) {
            for (String string : fileTxt) {
                if (key.startsWith(string)) {
                   result.put(key, index.get(key));
                }
            }
        }
        long endTime = System.currentTimeMillis();
        System.out.println("Building binary tree took: " + (endTime - startTime) + " milliseconds");
    }

    public List<String> readTxt (String filename){
        List<String> fileLines = new ArrayList<>();

        try {
            File file = new File(filename);
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                fileLines.add(line);
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        }
        return fileLines;
    }

    public static void main(String[] args) {
        if (args.length < 8
                || !args[0].equals("--data")
                || !args[2].equals("--indexed-column-id")
                || !args[4].equals("--input-file")
                || !args[6].equals("--output-file")) {
            System.out.println("Usage: java AirportSearch --data airports.csv --indexed-column-id 3 --input-file input-path-to-file.txt --output-file output-path-to-file.json");
            return;
        }

        String airportsFile = args[1];
        int searchColumn = Integer.parseInt(args[3]);
        String inputFile = args[5];
        String outputFile = args[7];

        AirportSearch airportSearch = new AirportSearch(airportsFile, searchColumn);
        System.out.println(airportSearch.readTxt(inputFile));
        airportSearch.search();


    }
}
