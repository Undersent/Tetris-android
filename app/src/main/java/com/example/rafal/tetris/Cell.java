package com.example.rafal.tetris;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by rafal on 07.05.17.
 */

/*
        * This class represents a single box
        */
public class Cell implements Parcelable{

    protected int myColor;
    protected int xPos;
    protected int yPos;

    public Cell(int myColor) {
        this.myColor = myColor;
        this.xPos = -1;
        this.yPos = -1;
    }

    public Cell(int x, int y, int Color){
        this.myColor = Color;
        this.xPos = x;
        this.yPos = y;
    }

    public String toString() {
        String result = "(";
        result += this.xPos;
        result += ",";
        result += ")";
        return result;
    }

    public int getColor() {
        return this.myColor;
    }

    public void setColor(int newColor) {
        this.myColor = newColor;
    }

    public int getXPosition() {
        return xPos;
    }

    public void setXPosition(int xPos) {
        this.xPos = xPos;
    }

    public int getYPosition() {
        return yPos;
    }

    public void setYPosition(int yPos) {
        this.yPos = yPos;
    }

    /**
     * Constructs a Question from a Parcel
     * @param parcel Source Parcel
     */
    public Cell (Parcel parcel) {
        this.myColor = parcel.readInt();
        this.xPos = parcel.readInt();
        this.yPos = parcel.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    // Required method to write to Parcel
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(myColor);
        dest.writeInt(xPos);
        dest.writeInt(yPos);
    }

    // Method to recreate a Question from a Parcel
    public static Creator<Cell> CREATOR = new Creator<Cell>() {

        @Override
        public Cell createFromParcel(Parcel source) {
            return new Cell(source);
        }

        @Override
        public Cell[] newArray(int size) {
            return new Cell[size];
        }

    };
}