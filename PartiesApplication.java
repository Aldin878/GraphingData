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

public class PartiesApplication extends Application {

    @Override
    public void start(Stage window) {
        NumberAxis xAxis = new NumberAxis(1968, 2008, 4);
        NumberAxis yAxis = new NumberAxis();
        ArrayList<String> data = getData();
        ArrayList<Integer> years = getYears(data);

        xAxis.setLabel("Year");
        yAxis.setLabel("Relative support (%)");

        LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle("Relative support in the years 1968-2008");
        
        

    }

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
        
        for(int i = 1; i < 12; i++) {
            years.add(Integer.valueOf(data.get(i)));
        }
        
        
        return years;
    }
    
}
