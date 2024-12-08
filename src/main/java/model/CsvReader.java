package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CsvReader {
    private final String folderPath = "src/main/java/data";

    /**
     * 
     *
     * @param keyword 
     * @return 
     */
    public List<KolRanking> readKolData(String keyword) {
        List<KolRanking> rankings = new ArrayList<>();
        File csvFile = new File(folderPath, keyword + ".csv");

        if (!csvFile.exists()) {
            return rankings; 
        }

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String line;
            boolean isFirstRow = true;

            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");

                // Skip header row
                if (isFirstRow) {
                    isFirstRow = false;
                    continue;
                }

                int rank = Integer.parseInt(values[0]);
                String username = values[1];
                double rankingPoint = Double.parseDouble(values[2]);
                rankings.add(new KolRanking(rank, username, rankingPoint));
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }

        return rankings;
    }
}
