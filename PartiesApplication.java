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

public class PartiesApplication extends Application {

    @Override
    public void start(Stage window) {
        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();
        ArrayList<String> data = new ArrayList<>();
        
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
}
