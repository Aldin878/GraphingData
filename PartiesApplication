package application;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import java.util.Scanner;
import java.nio.file.Paths;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class PartiesApplication extends Application {
    ArrayList<String> holder = new ArrayList<>();
    String [] pieces;
    
    @Override
    public void start(Stage window) {

        try (Scanner scanner = new Scanner(Paths.get("partiesdata.tsv"))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                
                pieces = line.split("\t");
              
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        
        
        for(int i = 0; i < pieces.length; i++) {
            holder.add(pieces[i]);
        }
        
        System.out.println(holder.get(0));
        
    }

    public static void main(String[] args) {

    }

}
