package sample;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.NumberAxis;

import java.util.*;
import java.nio.file.Paths;


public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        ArrayList<String> file = new ArrayList<>();
        ArrayList<String> parties = new ArrayList<>();
        ArrayList<Integer> years = new ArrayList<>();
        ArrayList<Double> rating = new ArrayList<>();
        HashMap<String, HashMap<Integer, Double>> partyYearData = new HashMap<>();

        file = getData();
        parties = getParties(file);
        years = getYears(file);
        rating = getRatings(file);

        partyYearData = graphData(rating, parties, years);

        for(Map.Entry<String, HashMap<Integer,Double>> entry : partyYearData.entrySet()) {
            System.out.println(entry);
        }
    }


    public static void main(String[] args) {
        launch(Main.class);
    }

    public static ArrayList<String> getData() {
        ArrayList<String> data = new ArrayList<>();
        String line;


        try (Scanner scanner = new Scanner(Paths.get("partiesdata.tsv"))) {
            while (scanner.hasNextLine()) {
                line = scanner.nextLine();
                String[] pieces = line.split("\t");

                for (int i = 0; i < pieces.length; i++) {
                    if (pieces[i].equals("-")) {
                        pieces[i] = "0";
                    }
                }

                data.addAll(Arrays.asList(pieces));
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        return data;
    }

    public static ArrayList<Integer> getYears(ArrayList<String> data) {
        ArrayList<Integer> years = new ArrayList<>();

        for (int i = 1; i < 12; i++) {
            years.add(Integer.valueOf(data.get(i)));
        }

        return years;
    }

    public static ArrayList<String> getParties(ArrayList<String> data) {
        ArrayList<String> parties = new ArrayList<>();

        for (int i = 12; i < data.size(); i += 12) {
            parties.add(data.get(i));
        }

        return parties;
    }

    public static ArrayList<Double> getRatings(ArrayList<String> data) {
        ArrayList<Double> ratingData = new ArrayList<>();
        int count = 0;

        for (int i = 13; i < data.size(); i++) {
            if (count == 11) {
                count = 0;
                continue;
            }

            if (data.get(i).equals("-")) {
                count++;
                continue;
            }

            ratingData.add(Double.valueOf(data.get(i)));
            count++;
        }

        return ratingData;
    }

    public static HashMap<String, HashMap<Integer, Double>> graphData(ArrayList<Double> ratingData, ArrayList<String> parties, ArrayList<Integer> years) {
      HashMap<String, HashMap<Integer,Double>> partyYearData = new HashMap<>();
      HashMap<Integer,Double> partyData = new HashMap<>();
      int yearCount = 0;
      int ratingCount = 0;
      int partyCount = 0;

      while(partyCount < parties.size()) {
          partyData.put(years.get(yearCount), ratingData.get(ratingCount));
          yearCount++;
          ratingCount++;

          if(yearCount == 11) {
              partyYearData.put(parties.get(partyCount), partyData);
              partyCount++;
              yearCount = 0;
              partyData = new HashMap<>();
          }
      }

        return partyYearData;
    }
}
