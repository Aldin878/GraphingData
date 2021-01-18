package sample;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.NumberAxis;
import java.util.HashMap;

import java.util.Arrays;
import java.util.Scanner;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception{




    }


    public static void main(String[] args) {
        ArrayList<String> data = new ArrayList<>();
        ArrayList<String> holder = new ArrayList<>();
        ArrayList<Integer> intHolder = new ArrayList<>();
        ArrayList<Double> doubleHolder = new ArrayList<>();

        data = getData();
        holder = getParties(data);
        doubleHolder = getRatings(data);
        
        
        
    }

    public static ArrayList<String> getData() {
        ArrayList<String> data = new ArrayList<>();
        String line;


        try(Scanner scanner = new Scanner(Paths.get("partiesdata.tsv"))) {
            while(scanner.hasNextLine()) {
                line = scanner.nextLine();
                String[] pieces = line.split("\t");
                data.addAll(Arrays.asList(pieces));

            }
        } catch(Exception e) {
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

        for(int i = 13; i < data.size(); i++) {
            if(count == 11) {
                count = 0;
                continue;
            }

            if(data.get(i).equals("-")) {
                count++;
                continue;
            }

            ratingData.add(Double.valueOf(data.get(i)));
            count++;
        }

        return ratingData;
    }

    }
