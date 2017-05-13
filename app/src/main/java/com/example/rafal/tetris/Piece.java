package com.example.rafal.tetris;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by rafal on 07.05.17.
 */

public class Piece extends Grid implements Parcelable {

    protected Grid parent;
    protected int xPos;
    protected int yPos;

    public Piece(int columns, int rows) {
        super(columns,rows);
        this.xPos = -1;
        this.yPos = -1;
    }

    public Piece(Parcel parcel){
        super(parcel);
        Grid x = parcel.readParcelable(null);
       // Log.d("tag","usuwam"+x.grid.toString());
       // x.grid.remove(x.grid.size()-1);
        //x.grid.remove(x.grid.size()-2);
        //x.grid.remove(x.grid.size()-3);
        //x.grid.remove(x.grid.size()-4);
       // Log.d("tag","bbb"+x.grid.toString());
        this.parent = x;
        this.xPos = -1;
        this.yPos = -1;
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags){
        dest.writeParcelable(parent,0);
        dest.writeInt(-1);
        dest.writeInt(-1);
    }

    // Method to recreate a Question from a Parcel
    public static Creator<Piece> CREATOR = new Creator<Piece>() {

        @Override
        public Piece createFromParcel(Parcel source) {
            return new Piece(source);
        }

        @Override
        public Piece[] newArray(int size) {
            return new Piece[size];
        }

    };

    //do not set the cell position, this should be set by the parent grid
    @Override
    public void putCell(int X, int Y, Cell cell) {
        if(X < 0 || X >= this.columns || Y < 0 || Y >= this.rows) {
            return;
        }
        this.grid.get(X).set(Y, cell);
    }

    //attempt to insert this piece into a larger grid
    //returns true if successful, else false
    public boolean insertIntoGrid(int X, int Y, Grid parent) {

        //ensure that every spot we need is empty and is on the grid
        for(int i = 0; i < this.rows; i++) {
            for(int j = 0; j < this.columns; j++) {
                if(this.getCellAt(i, j) != null) {
                    if(X+i < 0 || X+i >= parent.getWidth() ||
                            Y+ j < 0 || Y+ j >= parent.getHeight() ||
                            parent.getCellAt(X+i,Y+ j) != null) {
                        return false;
                    }
                }
            }
        }

        //go ahead and insert the
        for(int i = 0; i < this.rows; i++) {
            for(int j = 0; j < this.columns; j++) {
                Cell nextCell = this.getCellAt(i,j);
                if(nextCell != null) {
                    parent.putCell(X + i, Y + j, nextCell);
                }
            }
        }

        this.xPos = X;
        this.yPos = Y;

        this.parent = parent;
        return true;
    }

    public void removeFromGrid() {
        if(this.parent == null) return;
        for(int i = 0; i < this.rows; i++) {
            for(int j = 0; j < this.columns; j++) {
                Cell nextCell = this.getCellAt(i,j);
                if(nextCell != null) {
                    parent.removeCell(nextCell.getXPosition(), nextCell.getYPosition());
                }
            }
        }
        this.parent = null;
        this.xPos = -1;
        this.yPos = -1;
    }

    public boolean shiftDown() {
        Grid myParent = this.parent;
        int X = this.xPos;
        int Y = this.yPos;
        this.removeFromGrid();
        boolean result = this.insertIntoGrid(X, Y+1, myParent);
        //if we fail to move it down then put it back where it was
        if(!result) {
            this.insertIntoGrid(X, Y, myParent);
        }
        return result;
    }

    public boolean shiftRight() {
        Grid myParent = this.parent;
        int X = this.xPos;
        int Y = this.yPos;
        this.removeFromGrid();
        boolean result = this.insertIntoGrid(X+1, Y, myParent);
        //if we fail to move it then put it back where it was
        if(!result) {
            this.insertIntoGrid(X, Y, myParent);
        }
        return result;
    }

    public boolean shiftLeft() {
        Grid myParent = this.parent;
        int X = this.xPos;
        int Y = this.yPos;
        this.removeFromGrid();
        boolean result = this.insertIntoGrid(X-1, Y, myParent);
        //if we fail to move it down then put it back where it was
        if(!result) {
            this.insertIntoGrid(X, Y, myParent);
        }
        return result;
    }

    public boolean rotateCounterClockwise() {
        //rotation doesn't mean anything if it is not a square
        if(this.columns != this.rows) {
            return true;
        }

        Grid myParent = this.parent;
        int X = this.xPos;
        int Y = this.yPos;
        this.removeFromGrid();

        ////take the transpose
        ArrayList<ArrayList<Cell>> transpose = new ArrayList<>();
        for(int i = 0; i < this.columns; i++) {
            ArrayList<Cell> nextColumn = new ArrayList<>();
            for(int j = 0; j < this.rows; j++) {
                nextColumn.add(null);
            }
            transpose.add(nextColumn);
        }
        for(int i = 0; i < this.columns; i++) {
            for(int j = 0; j < this.rows; j++) {
                transpose.get(i).set(j, this.grid.get(j).get(i));
            }
        }

        //reverse each row
        ArrayList<ArrayList<Cell>> rotated = new ArrayList<>();
        for(int i = 0; i < this.columns; i++) {
            ArrayList<Cell> nextColumn = new ArrayList<>();
            for(int j = 0; j < this.rows; j++) {
                nextColumn.add(null);
            }
            rotated.add(nextColumn);
        }
        for(int i = 0; i < this.columns; i++) {
            for(int j = 0; j < this.rows; j++) {
                rotated.get(i).set(j, transpose.get(i).get(this.columns - j - 1));
            }
        }

        ArrayList<ArrayList<Cell>> oldGrid = this.grid;

        this.grid = rotated;

        boolean result = this.insertIntoGrid(X, Y, myParent);

        //If we fail we can't give up so easily, try a "wall kick"
        //http://tetris.wikia.com/wiki/Wall_kick
        if(!result) {
            //shift 1 to the right
            result = this.insertIntoGrid(X+1, Y, myParent);
            if(!result) {
                //shift 2 to the right
                result = this.insertIntoGrid(X+2, Y, myParent);
                if(!result) {
                    //shift 1 to the left
                    result = this.insertIntoGrid(X-1, Y, myParent);
                    if(!result) {
                        //shift 2 to the left
                        result = this.insertIntoGrid(X-2, Y, myParent);
                    }
                }
            }
        }

        //if we fail to move it down then put it back where it was
        if(!result) {
            this.grid = oldGrid;
            this.insertIntoGrid(X, Y, myParent);
        }
        return result;
    }

    public boolean rotateClockwise() {
        //rotation doesn't mean anything if it is not a square
        if(this.columns != this.rows) {
            return true;
        }

        Grid myParent = this.parent;
        int X = this.xPos;
        int Y = this.yPos;
        this.removeFromGrid();

        ////take the transpose
        ArrayList<ArrayList<Cell>> transpose = new ArrayList<>();
        for(int i = 0; i < this.columns; i++) {
            ArrayList<Cell> nextColumn = new ArrayList<>();
            for(int j = 0; j < this.rows; j++) {
                nextColumn.add(null);
            }
            transpose.add(nextColumn);
        }
        for(int i = 0; i < this.columns; i++) {
            for(int j = 0; j < this.rows; j++) {
                transpose.get(i).set(j, this.grid.get(j).get(i));
            }
        }

        //reverse each column
        ArrayList<ArrayList<Cell>> rotated = new ArrayList<>();
        for(int i = 0; i < this.columns; i++) {
            ArrayList<Cell> nextColumn = new ArrayList<>();
            for(int j = 0; j < this.rows; j++) {
                nextColumn.add(null);
            }
            rotated.add(nextColumn);
        }
        for(int i = 0; i < this.columns; i++) {
            for(int j = 0; j < this.rows; j++) {
                rotated.get(i).set(j, transpose.get(this.columns - i - 1).get(j));
            }
        }

        ArrayList<ArrayList<Cell>> oldGrid = this.grid;

        this.grid = rotated;

        boolean result = this.insertIntoGrid(X, Y, myParent);

        //If we fail we can't give up so easily, try a "wall kick"
        //http://tetris.wikia.com/wiki/Wall_kick
        if(!result) {
            //shift 1 to the right
            result = this.insertIntoGrid(X+1, Y, myParent);
            if(!result) {
                //shift 2 to the right
                result = this.insertIntoGrid(X+2, Y, myParent);
                if(!result) {
                    //shift 1 to the left
                    result = this.insertIntoGrid(X-1, Y, myParent);
                    if(!result) {
                        //shift 2 to the left
                        result = this.insertIntoGrid(X-2, Y, myParent);
                    }
                }
            }
        }

        //if we fail to move it down then put it back where it was
        if(!result) {
            this.grid = oldGrid;
            this.insertIntoGrid(X, Y, myParent);
        }
        return result;

    }

    //attempt to move down as far as possible
    public void zoomDown() {
        boolean result = true;
        while(result) {
            result = this.shiftDown();
        }
    }



}
