package com.example.rafal.tetris;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by rafal on 07.05.17.
 */

public class Grid implements Parcelable {
    protected int columns;
    protected int rows;
    public ArrayList<ArrayList<Cell>> grid;

    public Grid(int columns, int rows) {

        this.columns = columns;
        this.rows = rows;

        //tworzy kratke
        this.grid = new ArrayList<>();
        for(int i = 0; i < this.columns; i++) {
            ArrayList<Cell> nextColumn = new ArrayList<>();
            for(int j = 0; j < this.rows; j++) {
                nextColumn.add(null);
            }
            this.grid.add(nextColumn);
        }

    }

    public int getWidth() {
        return this.columns;
    }

    public int getHeight() {
        return this.rows;
    }

    //takie jak getCellAt(X,Y), usuwa rowniez z grida
    public Cell extractCellAt(int X, int Y) {
        if(X < 0 || X >= this.columns || Y < 0 || Y >= this.rows) {
            return null;
        }
        Cell result = this.grid.get(X).get(Y);
        this.grid.get(X).set(Y, null);
        return result;
    }

    //return cell at a position (X,Y)
    public Cell getCellAt(int X, int Y) {
        if(X < 0 || X >= this.columns || Y < 0 || Y >= this.rows) {
            return null;
        }
        return this.grid.get(X).get(Y);
    }

    //wrzuca komorke na kratke
    public void putCell(int X, int Y, Cell cell) {
        if(X < 0 || X >= this.columns || Y < 0 || Y >= this.rows) {
            return;
        }
        cell.setXPosition(X);
        cell.setYPosition(Y);
        this.grid.get(X).set(Y, cell);
    }


    //sprawdza czy jest full row
    //zwraca -1 jezeli nie
    public int getFirstFullRow() {
        for(int j = 0; j < this.rows; j++) {
            boolean fullRow = true;
            for(int i = 0; i < this.columns; i++) {
                if(this.grid.get(i).get(j) == null) {
                    fullRow = false;
                    break;
                }
            }
            if(fullRow) {
                return j;
            }
        }
        return -1;
    }

    //usuwa komorke
    public void removeCell(int X, int Y) {
        if(X < 0 || X >= this.columns || Y < 0 || Y >= this.rows) {
            return;
        }
        Cell theCell = this.grid.get(X).get(Y);
        if(theCell != null) {
            this.grid.get(X).set(Y, null);
            theCell.setYPosition(-1);
            theCell.setXPosition(-1);
        }
    }


    //usuwa rzad i daje wszystko nizej
    public void deleteRow(int row) {
        for(int i = 0; i < this.columns; i++) {
            this.removeCell(i, row);
        }
        for(int i = 0; i < this.columns; i++) {
            for(int j = row; j >= 0; j--) {
                Cell cellToMove = this.extractCellAt(i, j - 1);
                if(cellToMove != null) {
                    this.putCell(i, j, cellToMove);
                }
            }
        }
    }

    //usun wszystko
    public void clear() {
        for(int i = 0; i < this.columns; i++) {
            for(int j = 0; j < this.rows; j++) {
                this.grid.get(i).set(j, null);
            }
        }
    }

    private ArrayList<Cell> allCells = new ArrayList<>();

    public ArrayList<Cell> getAllCells(){
        for(int i = 0; i < this.columns; i++) {
            for(int j = 0; j < this.rows; j++) {

                allCells.add(getCellAt(i,j));
                System.out.println("Dodaje cell "+ getCellAt(i,j).xPos+" "+ getCellAt(i,j).yPos);
            }
        }
        return allCells;
    }

    public Grid(Parcel parcel){

        this.columns = parcel.readInt();
        this.rows = parcel.readInt();
        this.grid = parcel.readArrayList(null);

    }

    @Override
    public int describeContents(){
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeInt(columns);
        dest.writeInt(rows);
        //grid.remove(grid.size()-1);
        dest.writeList(grid);
    }

    // Method to recreate a Question from a Parcel
    public static Creator<Grid> CREATOR = new Creator<Grid>() {

        @Override
        public Grid createFromParcel(Parcel source) {
            return new Grid(source);
        }

        @Override
        public Grid[] newArray(int size) {
            return new Grid[size];
        }

    };

}

