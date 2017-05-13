package com.example.rafal.tetris;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Painting game;


    //public static Grid grid = new Grid(10, 23);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageButton left = (ImageButton) findViewById(R.id.leftButton);
        ImageButton right = (ImageButton) findViewById(R.id.rightButton);
        ImageButton rotateR = (ImageButton) findViewById(R.id.rotateR);
        ImageButton RotateLeft = (ImageButton) findViewById(R.id.rotateLeft);
        ImageButton down = (ImageButton) findViewById(R.id.downButton);
        ImageButton reset = (ImageButton) findViewById(R.id.resetButton);
        game = (Painting) findViewById(R.id.gamePanel);
        game.nextPiecePanel = (SecondPiece) findViewById(R.id.nextPiecePanel);
        game.levelView = (TextView) findViewById(R.id.level);
        //game.rowView = (TextView) findViewById(R.id.rows);
        game.scoreView = (TextView) findViewById(R.id.score);

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                game.reset();
            }
        });
        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(game.currentPiece!=null)
                    game.currentPiece.shiftLeft();
                game.invalidate();
            }
        });
        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(game.currentPiece!=null)
                    game.currentPiece.shiftRight();
                game.invalidate();
            }
        });
        rotateR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(game.currentPiece!=null)
                    game.currentPiece.rotateClockwise();
                game.invalidate();
            }
        });
        RotateLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(game.currentPiece!=null)
                    game.currentPiece.rotateCounterClockwise();
                game.invalidate();
            }
        });
        down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(game.currentPiece!=null)
                    game.currentPiece.zoomDown();
                //game.invalidate();
                game.touchDown();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;

    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if(id == R.id.action_name){
            game.levelUp();
            return true;
        }
        return super.onOptionsItemSelected(item);

    }
   /* public static Grid getGrid(){
        if(isGameRestore>0) {
            return grid;
        }else
            grid = new Grid(10, 23);
        return grid;
    }

    protected void onSaveInstanceState(Bundle icicle) {
        super.onSaveInstanceState(icicle);
        //icicle.putLong("param", value);
        //grid = getGrid();
        isGameRestore++;

        System.out.println("aaaaaa "+grid.toString());
        ArrayList<Cell> allCells;
        ArrayList<Integer> colors = new ArrayList<>();
        ArrayList<Integer> xPos = new ArrayList<>();
        ArrayList<Integer> yPos = new ArrayList<>();
        allCells = grid.getAllCells();
        for(Cell cell : allCells){
            colors.add(cell.getColor());
            xPos.add(cell.getXPosition());
            yPos.add(cell.getYPosition());
        }
        icicle.putIntegerArrayList("colors", colors);
        icicle.putIntegerArrayList("xPos", xPos);
        icicle.putIntegerArrayList("yPos", yPos);
        icicle.putInt("restore", isGameRestore);
    }

    protected void onRestoreInstanceState(Bundle icicle) {
        ArrayList<Integer> colors = new ArrayList<>();
        ArrayList<Integer> xPos = new ArrayList<>();
        ArrayList<Integer> yPos = new ArrayList<>();
        if (icicle != null){
            colors = icicle.getIntegerArrayList("colors");
            xPos = icicle.getIntegerArrayList("xPos");
            yPos = icicle.getIntegerArrayList("yPos");
            isGameRestore = icicle.getInt("restore");
        }

        for(int i =0; i<colors.size();i++){
            Cell cell = new Cell(colors.get(i));
            grid.putCell(xPos.get(i), yPos.get(i), cell);
        }

    }
*/


}
