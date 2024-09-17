package com.bufigol.utils;

import java.io.*;
import java.util.ArrayList;

public class ManejoDeArchivos {

    private static boolean isFirstCall = true;


    public static ArrayList<String[]> readCSV(String filePath, String separator) {
        ArrayList<String[]> lines = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(separator);
                lines.add(values);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return lines;
    }
    public static void appendToCSV(String filePath, String separator, String[] data) {
        try (FileWriter fw = new FileWriter(filePath, true);
             PrintWriter pw = new PrintWriter(fw)) {
            if (isFirstCall) {
                isFirstCall = false; // Set the flag to false after the first call
                pw.println();
            }
            pw.println(String.join(separator, data));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
