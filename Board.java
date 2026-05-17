

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.geometry.Pos;

/**
 * Write a description of JavaFX class Board here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
    public class Board extends GridPane
    {
        private Cell[][] board;
        private int size;
        private final int NORTH = 0, EAST = 1, SOUTH = 2, WEST = 3;
        
        private final static String STYLE_CELL = "-fx-background-radius: 0; -fx-font-size: 30; -fx-background-color:LightSkyBlue; -fx-border-color:Black;";
        private final static String STYLE_EMPTY_CELL = "-fx-background-radius: 0; -fx-font-size: 30; -fx-background-color:White; -fx-border-color:Black;";
    
        public Board(int size)
        {
            this.size = size;
            this.setAlignment(Pos.CENTER);
            initialize();
        }
    
        private void initialize() {
            this.getChildren().clear();
            board = new Cell[size][size];
            for (int y = 0; y < size; y++) {
                for (int x = 0; x < size; x++) {
                    // Last cell is empty
                    if (!(x == size - 1 && y == size - 1))
                        board[x][y] = new Cell("" + (y * size + x + 1), x, y);
                    else
                        board[x][y] = new Cell("", x, y);
    
                    board[x][y].setOnAction(this::processClick);
                    board[x][y].setPrefSize(80, 80);
                    board[x][y].setStyle(board[x][y].isEmpty() ? STYLE_EMPTY_CELL : STYLE_CELL);
                    
                    add(board[x][y], x, y);
                }
            }
            shuffle();
        }
    
        public void resetBoard() {
            initialize();
        }
        
        private void shuffle() {
            int xEmpty = size - 1, yEmpty = size - 1;
            for (int i = 0; i < 500; i++) {
                int direction = (int)(Math.random() * 4);
                if (direction == NORTH && yEmpty - 1 >= 0) {
                    swap(xEmpty, yEmpty, xEmpty, yEmpty - 1);
                    yEmpty--;
                } else if (direction == EAST && xEmpty + 1 < size) {
                    swap(xEmpty, yEmpty, xEmpty + 1, yEmpty);
                    xEmpty++;                
                } else if (direction == SOUTH && yEmpty + 1 < size) {
                    swap(xEmpty, yEmpty, xEmpty, yEmpty + 1);
                    yEmpty++;                
                } else if (direction == WEST && xEmpty - 1 >= 0) {
                    swap(xEmpty, yEmpty, xEmpty - 1, yEmpty);
                    xEmpty--;                                
                }
            }
        }
    
        public void processClick(ActionEvent event) {
            Cell clicked = (Cell)event.getSource();
            int x = clicked.getX();
            int y = clicked.getY();
            int result = isNeighborEmpty(x, y);
            
            if (result == NORTH) swap(x, y - 1, x, y);
            else if (result == EAST) swap(x + 1, y, x, y);
            else if (result == SOUTH) swap(x, y + 1, x, y);
            else if (result == WEST) swap(x - 1, y, x, y);
        }
        
        private int isNeighborEmpty(int x, int y) {
            if (y - 1 >= 0 && board[x][y-1].isEmpty()) return NORTH;
            if (x + 1 < size && board[x+1][y].isEmpty()) return EAST;
            if (y + 1 < size && board[x][y+1].isEmpty()) return SOUTH;
            if (x - 1 >= 0 && board[x-1][y].isEmpty()) return WEST;
            return -1;
        }
        
        private void swap(int x0, int y0, int x1, int y1) {
            Cell cell0 = board[x0][y0]; // Usually the empty one during shuffle
            Cell cell1 = board[x1][y1];
            
            this.getChildren().removeAll(cell0, cell1);
            
            cell0.setX(x1); cell0.setY(y1);
            cell1.setX(x0); cell1.setY(y0);
            
            board[x1][y1] = cell0;
            board[x0][y0] = cell1;
            
            this.add(cell0, x1, y1);
            this.add(cell1, x0, y0);
        }
            
            /* the 2nd idea: swapping the contents and styles of two cells
             *
            // swap contents
            String tmpContent = nonEmptyCell.getContent();
            emptyCell.setContent(tmpContent);
            nonEmptyCell.setContent("");
            
            // swap styles
            emptyCell.setStyle(Cell.STYLE_CELL);
            nonEmptyCell.setStyle(Cell.STYLE_EMPTY_CELL);
            
            // positions should not be swaped; 
            //emptyCell.setX(x1); emptyCell.setY(y1);
            //nonEmptyCell.setX(x0); nonEmptyCell.setY(y0);
            */
            
            //System.out.println("Board: swap: emptyCell:" + 
            //    nonEmptyCell.getX() + ", " + nonEmptyCell.getY() + 
            //    ", nonEmptyCell: " + emptyCell.getX() + ", " + emptyCell.getY());
        
    }
