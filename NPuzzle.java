

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.geometry.Pos;

/**
 * Write a description of JavaFX class NPuzzle here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
    public class NPuzzle extends Application 
{ 
    
    
        private Board board;
        private int currentSize = 3; 

        @Override 
        public void start(Stage stage) 
    {    
        VBox root = new VBox(10);
        root.setAlignment(Pos.CENTER);
        
        
        board = new Board(currentSize);  
        HBox controls = new HBox(10);
        controls.setAlignment(Pos.CENTER);
        Button resetBtn = new Button("Reset");
        resetBtn.setOnAction(e -> board.resetBoard());
        Button resize = new Button("Resize (4x4)");
        resize.setOnAction(e -> {
            
            currentSize = (currentSize == 3) ? 4 : 3;
            resize.setText("Resize (" + (currentSize == 3 ? "4x4" : "3x3") + ")");
            
            
            root.getChildren().remove(board);
            board = new Board(currentSize);
            root.getChildren().add(board);
            stage.sizeToScene(); 
        });
        controls.getChildren().addAll(resetBtn, resize);
        root.getChildren().addAll(controls, board);

        Scene scene = new Scene(root);  
        stage.setTitle("NPuzzle Game"); 
        stage.setScene(scene); 
        stage.show(); 
    }  
    
}