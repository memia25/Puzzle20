package com.example.puzzle20;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Random;

/**
 * author: Griselda Memia
 * description: This class creates a gridView that displays 16 buttons for the game to be played.
 * When program is launched the button board is made, then the buttons are scrambled and the boardSize of the board are set.
 * In this case it will be a 4x4 grid
 * date: 02/27/2022
 */

public class MainActivity extends AppCompatActivity {
    //no grid class, do a 2d array on ints, row and column stored here
    //transformations, move them around
    // then check if the game is done

    // inside the square, only one variable, the value

    private static final int columns=4;
    private static final int boardSize=columns*columns;
    public static final String up="up";
    public static final String down="down";
    public static final String left="left";
    public static final String right="right";
    private static String[] squareArr; //array for the numbers inside the squares
    private static int colWidth;
    private static int colHeight;
    private static MyGridView myGrid; //instance of MyGridView class

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeBoard();
        randomize();
        setBoardSize();
    }
    /**
     * This method moves the squares 
     * @param context view
     * @param direction  2,4,8,6 for down,left,right,up respectively
     * @param currSquare Square that is clicked and will be dragged to another location
     */
    public static void moveSquares(Context context, String direction, int currSquare ){

        if(currSquare == 0) {
            if (direction.equals(right)) switchSquares(context, currSquare, 1);
            else if (direction.equals(down)) switchSquares(context, currSquare, columns);
            else Toast.makeText(context, "This move is not possible.", Toast.LENGTH_SHORT).show();
            
        } else if (currSquare == columns - 1) {
            if (direction.equals(left)) switchSquares(context, currSquare, -1);
            else if (direction.equals(down)) switchSquares(context, currSquare, columns);
            else Toast.makeText(context, "This move is not possible.", Toast.LENGTH_SHORT).show();
        
        } else if (currSquare > columns - 1 && currSquare < boardSize - columns &&
    currSquare % columns == 0) {
            if (direction.equals(up)) switchSquares(context, currSquare, -columns);
            else if (direction.equals(right)) switchSquares(context, currSquare, 1);
            else if (direction.equals(down)) switchSquares(context, currSquare, columns);
            else Toast.makeText(context, "Invalid move", Toast.LENGTH_SHORT).show();

        } else if (currSquare == columns * 2 - 1 || currSquare == columns * 3 - 1) {
            if (direction.equals(up)) switchSquares(context, currSquare, -columns);
            else if (direction.equals(left)) switchSquares(context, currSquare, -1);
            else if (direction.equals(down)) {


                if (currSquare <= boardSize - columns - 1) switchSquares(context, currSquare,
                        columns);
                else Toast.makeText(context, "Invalid move", Toast.LENGTH_SHORT).show();
            } else Toast.makeText(context, "Invalid move", Toast.LENGTH_SHORT).show();


        } else if (currSquare == boardSize - columns) {
            if (direction.equals(up)) switchSquares(context, currSquare, -columns);
            else if (direction.equals(right)) switchSquares(context, currSquare, 1);
            else Toast.makeText(context, "Invalid move", Toast.LENGTH_SHORT).show();


        } else if (currSquare < boardSize - 1 && currSquare > boardSize - columns) {
            if (direction.equals(up)) switchSquares(context, currSquare, -columns);
            else if (direction.equals(left)) switchSquares(context, currSquare, -1);
            else if (direction.equals(right)) switchSquares(context, currSquare, 1);
            else Toast.makeText(context, "Invalid move", Toast.LENGTH_SHORT).show();


        } else {
            if (direction.equals(up)) switchSquares(context, currSquare, -columns);
            else if (direction.equals(left)) switchSquares(context, currSquare, -1);
            else if (direction.equals(right)) switchSquares(context, currSquare, 1);
            else switchSquares(context, currSquare, columns);
        }
    }

    /**
     * This method randomizes squares
     */
    private void randomize() {
        int squareIndex;
        String tempSquare;
        Random rng = new Random();

        for (int i = squareArr.length - 1; i > 0; i--) {
            squareIndex = rng.nextInt(i+1);
            tempSquare = squareArr[squareIndex];
            squareArr[squareIndex] = squareArr[i];
            squareArr[i] = tempSquare;
        }
    }


    /**
     * This method switches squares 
     * @param context The view tht is referenced
     * @param currLoc location of the current square
     * @param nextLoc location of the square that switches
     */
    private static void switchSquares(Context context,int currLoc  ,int nextLoc){
        String newLoc= squareArr[currLoc + nextLoc];
        squareArr[currLoc + nextLoc]=squareArr[currLoc];
        squareArr[currLoc]=newLoc;
        drawBoard(context);

        if (gameOver()) {
            Toast.makeText(context, "Game Over.", Toast.LENGTH_SHORT).show();
        }
    }
    /**
     * This method checks if the puzzle is solved
     * @return returns a boolean true if all squares are in the correct order.
     */
    public static boolean gameOver(){
        boolean isGameOver=false;
        for (int i=0; i< squareArr.length; i++){
            if (squareArr[i].equals(String.valueOf(i))){
                isGameOver=true;
            }
            else {
                isGameOver=false;
                break;
            }
        }
        return isGameOver;
    }
    /**
     * The methods initializeBoard and drawBoard create the board with buttons and connect main to layout in XML
     */
    private void initializeBoard() {
        myGrid = findViewById(R.id.myGrid);
        myGrid.setNumColumns(columns);

        squareArr = new String[boardSize];
        for (int i = 0; i < boardSize; i++) {
            squareArr[i] = String.valueOf(i);
        }
    }

    private static void drawBoard(Context context) {
        ArrayList<Button> squares = new ArrayList<>();
        Button button;

        for (int i = 0; i < squareArr.length; i++) {
            button = new Button(context);
            if (squareArr[i].equals("0")) {
                button.setBackgroundResource(R.drawable.square1);
            } else if (squareArr[i].equals("1")) {
                button.setBackgroundResource(R.drawable.square2);
            } else if (squareArr[i].equals("2")) {
                button.setBackgroundResource(R.drawable.square3);
            } else if (squareArr[i].equals("3") ) {
                button.setBackgroundResource(R.drawable.square4);
            } else if (squareArr[i].equals("4")) {
                button.setBackgroundResource(R.drawable.square5);
            } else if (squareArr[i].equals("5")) {
                button.setBackgroundResource(R.drawable.square6);
            } else if (squareArr[i].equals("6")) {
                button.setBackgroundResource(R.drawable.square7);
            } else if (squareArr[i].equals("7")) {
                button.setBackgroundResource(R.drawable.square8);
            } else if (squareArr[i].equals("8")) {
                button.setBackgroundResource(R.drawable.square9);
            } else if (squareArr[i].equals("9")) {
                button.setBackgroundResource(R.drawable.square10);
            } else if (squareArr[i].equals("10")) {
                button.setBackgroundResource(R.drawable.square11);
            } else if (squareArr[i].equals("11")) {
                button.setBackgroundResource(R.drawable.square12);
            } else if (squareArr[i].equals("12")) {
                button.setBackgroundResource(R.drawable.square13);
            } else if (squareArr[i].equals("13")) {
                button.setBackgroundResource(R.drawable.square14);
            } else if (squareArr[i].equals("14")) {
                button.setBackgroundResource(R.drawable.square15);
            } else if (squareArr[i].equals("15")) {
                button.setBackgroundResource(R.drawable.blank);
            }

            squares.add(button);
        }
        myGrid.setAdapter(new MyGridAdapter(squares, colWidth, colHeight));
    }

    /**
     * This method sets the layout.
     * Reference: https://developer.android.com/reference/android/view/ViewTreeObserver
     */
    private void setBoardSize() {
        ViewTreeObserver observer = myGrid.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                myGrid.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                //get boardSize of the View so it can be divided by the number of col
                int viewWidth = myGrid.getMeasuredWidth();
                int viewHeight = myGrid.getMeasuredHeight();

                //make board have proportional spaces for each square
                colWidth = viewWidth / columns;
                colHeight = viewHeight / columns;

                int statusHeight = getStatusHeight(getApplicationContext());
                int neededHeight = viewHeight - statusHeight;

                drawBoard(getApplicationContext());
            }
        });
    }
    
    
    /**
     * This is for formatting the screen
     * Reference:
     * https://stackoverflow.com/questions/3407256/height-of-status-bar-in-android
     */
    private int getStatusHeight(Context context) {
        int height = 0;
        int viewId = context.getResources().getIdentifier("statusHeight", "size", "android");

        if (viewId > 0) {
            height = context.getResources().getDimensionPixelSize(viewId);
        }
        return height;
    }
}