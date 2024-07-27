//Hind Hussein 1202416
package com.example.compilerfinalproject;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) {

        LL1Table ll1Table = new LL1Table(); //identify table of LL1 with the terminals and nonterminals with their production rules
        for (int i=0; i<ll1Table.LL1ParsingTable.length; i++){ //loop through the rows of the table
            for(int j=0; j<ll1Table.LL1ParsingTable[i].length; j++) { //loop through the columns of the table
                if(ll1Table.LL1ParsingTable[i][j] != null) {  //if its null then do not print it
                    System.out.println(ll1Table.LL1ParsingTable[i][j]); //otherwise if its not null then print it
                }
                else {
                    System.out.println(); //if it is null then just print nothing
                }
            }
        }

        Button load = new Button("Upload a File"); //button for the load
        load.setFont(Font.font("Times New Roman", FontWeight.BOLD, 18)); //making the font times new roman, bold and with a size of 18
        load.setStyle("-fx-border-color: #000000; " + "-fx-border-radius: 30px;" +
                "-fx-background-radius: 30px;" + "-fx-border-width: 4px"); //making the color black, small radius corners, and the boarder width larger
        load.setTextFill(Color.BLACK); //make the text black

        Button nonTerminalButton = new Button("Non-Terminal"); //button for the nonterminals
        nonTerminalButton.setFont(Font.font("Times New Roman", FontWeight.BOLD, 18)); //making the font times new roman, bold and with a size of 18
        nonTerminalButton.setStyle("-fx-border-color: #000000; " + "-fx-border-radius: 30px;" +
                "-fx-background-radius: 30px;" + "-fx-border-width: 4px"); //making the color black, small radius corners, and the boarder width larger
        nonTerminalButton.setTextFill(Color.BLACK); //make the text black

        Button terminalButton = new Button("Terminal"); //button for the terminals
        terminalButton.setFont(Font.font("Times New Roman", FontWeight.BOLD, 18)); //making the font times new roman, bold and with a size of 18
        terminalButton.setStyle("-fx-border-color: #000000; " + "-fx-border-radius: 30px;" +
                "-fx-background-radius: 30px;" + "-fx-border-width: 4px"); //making the color black, small radius corners, and the boarder width larger
        terminalButton.setTextFill(Color.BLACK); //make the text black

        TextArea textArea = new TextArea(); //textArea to show the code, terminals, or the nonterminals
        textArea.setFont(Font.font("Times New Roman", FontWeight.BOLD, 20)); //making the font times new roman, bold and with a size of 20
        textArea.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(4)))); //making the color black, small radius corners, and the boarder width larger
        textArea.setMaxSize(600, 10000); //making the maximum size for the textArea on x=600 and y=10000
        textArea.setPrefSize(600, 600); //making the preferred size for the textArea on x and y = 600
        textArea.setTranslateX(150); //pushing the textArea a little to the 150 on x
        textArea.setEditable(false); //making it not editable so the user can edit it

        Label labelForButton = new Label(); //label used to display to the user
        labelForButton.setFont(Font.font("Times New Roman", FontWeight.BOLD, 18)); //making the font times new roman, bold and with a size of 18
        labelForButton.setTranslateX(250); //pushing the label a little to the 250 on x
        labelForButton.setTranslateY(10); //pushing the label a little to the 10 on y


        FileChooser fileChooser = new FileChooser(); //a filechooser for when the user decides to choose a file to check if its parsed successfully or not
        fileChooser.setTitle("Open My Files");

        load.setOnAction(e->{
            textArea.clear(); //clear textArea
            labelForButton.setText(""); //clear label
            File selectedFile = fileChooser.showOpenDialog(stage); //choose the file and store into file known as selectedFile
            try {
                labelForButton.setStyle("-fx-text-fill: black;"); //make the label black
                LL1Scanner ll1Scanner = new LL1Scanner(selectedFile); //calling on the LL1Scanner class and giving it the file that the user selected
                LL1Parser ll1Parser = new LL1Parser(); //calling on the LL1Parser class
                //System.out.println(ll1Parser.parserLL1(ll1Scanner.scan()));
                labelForButton.setText(ll1Parser.parserLL1(ll1Scanner.scan())); //outputting the output given from LL1Parser into the label to display the results of their code
                Scanner scanner = new Scanner(selectedFile); //scan the file
                while(scanner.hasNext()) { //while the scanner is not empty
                    String line = scanner.nextLine(); //input each line into the string known as line
                    textArea.appendText(line + "\n"); //display on the textArea where after each line, it provides a new line
                }

            } catch (NullPointerException ex){ //in the case that the user does not choose a file and it strikes a nullpointer exception
                labelForButton.setStyle("-fx-text-fill: red;"); //make the text red
                labelForButton.setText("Make Sure a File was Chosen"); //inform user they should choose a file in order for the process to work
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        });

        // Non-terminal on UI
        nonTerminalButton.setOnAction(e->{
            textArea.clear(); //clear the textArea
            labelForButton.setStyle("-fx-text-fill: black;"); //make the label black
            labelForButton.setText("The Non-Terminals in this System are:"); //inform user of what the nonterminals are
            for(int i=0; i<ll1Table.getNonTerminals().length -1; i++){ //a for loop that loops around all the nonterminals
                textArea.appendText((i+1) + ": "  + ll1Table.getNonTerminals()[i] + "\n"); //show all the nonterminals on the textArea
            }
        });


        // terminal on UI
        terminalButton.setOnAction(e->{
            textArea.clear(); //clear the textArea
            labelForButton.setStyle("-fx-text-fill: black;"); //make the label black
            labelForButton.setText("The Terminals in this System are:"); //inform user of what the terminals are
            for(int i=0; i<ll1Table.getTerminals().length; i++){ //a for loop that loops around all the terminals
                textArea.appendText((i+1) + ": "  + ll1Table.getTerminals()[i] + "\n"); //show all the terminals on the textArea
            }

        });

        HBox buttonsBox = new HBox(20); //Hbox for all the buttons spaced at 20
        buttonsBox.setAlignment(Pos.TOP_CENTER); //make the buttons positioned at top and center
        buttonsBox.getChildren().addAll(load, nonTerminalButton, terminalButton); //add the load, nonterminal and terminal buttons

        VBox sceneBox = new VBox(10); //Vbox for all the components spaced at 10
        sceneBox.getChildren().addAll(buttonsBox, labelForButton, textArea); //add the buttons, label, and textAREA


        Scene scene = new Scene(sceneBox, 900, 900); //make the vbox the scene, at 900 x and 900 y
        stage.setTitle("Compiler: LL1 Project!"); //make the title of project say "Compiler: LL1 Project!"
        stage.setScene(scene); //make the stage have the scene set as whats displayed in line 125
        stage.show(); //show the stage
    }

    public static void main(String[] args) {
        launch();
    }
}