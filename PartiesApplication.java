package application;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import java.util.Scanner;
import java.nio.file.Paths;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.LineChart;
import java.util.HashMap;

public class PartiesApplication extends Application {

    @Override
    public void start(Stage window) {
        NumberAxis xAxis = new NumberAxis(1968, 2008, 4);
        NumberAxis yAxis = new NumberAxis();
        ArrayList<String> data = getData();
        ArrayList<Integer> years = getYears(data);
        ArrayList<String> parties = getParties(data);
        HashMap<String, HashMap<Integer, Double>> partySupport = getPartySupport(years, data);

        xAxis.setLabel("Year");
        yAxis.setLabel("Relative support (%)");

        LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle("Relative support in the years 1968-2008");
        
        System.out.println(partySupport.get("SDP"))
;    }

    public static void main(String[] args) {
        launch(PartiesApplication.class);
    }

    public static ArrayList<String> getData() {
        ArrayList<String> holder = new ArrayList<>();
        String[] pieces;

        try (Scanner scanner = new Scanner(Paths.get("partiesdata.tsv"))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();

                pieces = line.split("\t");

                holder.addAll(Arrays.asList(pieces));

            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        return holder;
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

    public static HashMap<String, HashMap<Integer, Double>> getPartySupport(ArrayList<Integer> years, ArrayList<String> data) {
        HashMap<Integer, Double> values = new HashMap<>();
        HashMap<String, HashMap<Integer, Double>> partySupport = new HashMap<>();

        for (int i = 0; i < years.size(); i++) {
            for (int j = 13; j < data.size(); j+= 12) {
                if (data.get(j).equals("-")) {
                    continue;
                }
                values.put(years.get(i), Double.valueOf(data.get(j)));
            }
        }

        for (int i = 12; i < data.size(); i+= 12) {
            partySupport.put(data.get(i), values);
        }

        return partySupport;
    }

}
