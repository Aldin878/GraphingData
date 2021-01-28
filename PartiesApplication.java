package sample;

import javafx.application.Application;
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
        NumberAxis xAxis = new NumberAxis(1964, 2012,4);
        NumberAxis yAxis = new NumberAxis();
        LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);
        ArrayList<String> file;
        ArrayList<String> parties;
        ArrayList<Integer> years;
        ArrayList<Double> rating;
        HashMap<String, HashMap<Integer, Double>> values;

        xAxis.setLabel("Year");
        yAxis.setLabel("Relative support (%)");

        file = getData();
        parties = getParties(file);
        years = getYears(file);
        rating = getRatings(file);
        values = graphData(rating, parties, years);

        HashMap<String, HashMap<Integer, Double>> finalValues = values;
        
        values.keySet().stream().forEach(party -> {
            XYChart.Series data = new XYChart.Series();
            data.setName(party);
            finalValues.get(party).entrySet().stream().forEach(pair -> {
                {
                    data.getData().add(new XYChart.Data(pair.getKey(), pair.getValue()));
                }});
            
                    lineChart.getData().add(data);
        });

        Scene view = new Scene(lineChart, 640, 480);
        stage.setScene(view);
        stage.show();
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
