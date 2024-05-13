/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Assignment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.Condition;


public class ConcurrentBagOfWords{

    static void processText(String text, Map<String, Integer> wordCounts) {
        String[] words = text.split("\\s+");
        for (String word : words) {
            wordCounts.put(word, wordCounts.getOrDefault(word, 0) + 1);
        }
    }

    public static void main(String[] args) {
        String filePath = "C:\\Users\\muham\\Desktop\\Studies\\WIF3011\\WIF3001\\Test.txt"; // Update with the path to your text file
        int numThreads = 3;
        System.out.println("it is in" + new File("Test.txt").getAbsolutePath());
        // Read text from file
        String[] texts = readTextFromFile(filePath);

        // Create a thread pool
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);

        // ConcurrentHashMap to store word counts
        Map<String, Integer> globalWordCounts = new ConcurrentHashMap<>();

        // Submit tasks to the thread pool
        for (String text : texts) {
            executor.submit(() -> processText(text, globalWordCounts));
        }

        // Shutdown the executor
        executor.shutdown();

        // Wait for all tasks to complete
        while (!executor.isTerminated()) {
            // Wait
        }

        // Output the final word counts
        for (Map.Entry<String, Integer> entry : globalWordCounts.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }

    static String[] readTextFromFile(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
            return sb.toString().split("\\n");
        } catch (IOException e) {
            e.printStackTrace();
            return new String[0]; // Return an empty array if an error occurs
        }
    }
}

