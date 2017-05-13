package com.example.rafal.tetris;

import android.graphics.Color;

import java.util.Random;

/**
 * Created by rafal on 07.05.17.
 */

public class PieceBuilder {


    //http://www.colorpicker.com/
    public static final int IColor = Color.parseColor("#96E1FA");
    public static final int JColor = Color.parseColor("#304FC9");
    public static final int LColor = Color.parseColor("#F7CB05");
    public static final int OColor = Color.parseColor("#FFFF0D");
    public static final int SColor = Color.parseColor("#6ED654");
    public static final int TColor = Color.parseColor("#C267DB");
    public static final int ZColor = Color.parseColor("#F54747");

    protected static Random rand = new Random();

    public static Piece I() {
        Piece piece = new Piece(4,4);
        piece.putCell(0,1,new Cell(PieceBuilder.IColor));
        piece.putCell(1,1,new Cell(PieceBuilder.IColor));
        piece.putCell(2,1,new Cell(PieceBuilder.IColor));
        piece.putCell(3,1,new Cell(PieceBuilder.IColor));
        return piece;
    }

    public static Piece J() {
        Piece piece = new Piece(3,3);
        piece.putCell(0, 0, new Cell(PieceBuilder.JColor));
        piece.putCell(0, 1, new Cell(PieceBuilder.JColor));
        piece.putCell(1, 1, new Cell(PieceBuilder.JColor));
        piece.putCell(2, 1, new Cell(PieceBuilder.JColor));
        return piece;
    }

    public static Piece L() {
        Piece piece = new Piece(3,3);
        piece.putCell(0, 1, new Cell(PieceBuilder.LColor));
        piece.putCell(1, 1, new Cell(PieceBuilder.LColor));
        piece.putCell(2, 1, new Cell(PieceBuilder.LColor));
        piece.putCell(2, 0, new Cell(PieceBuilder.LColor));
        return piece;
    }

    public static Piece O() {
        Piece piece= new Piece(4,3);
        piece.putCell(0, 1, new Cell(PieceBuilder.OColor));
        piece.putCell(0, 2, new Cell(PieceBuilder.OColor));
        piece.putCell(1, 1, new Cell(PieceBuilder.OColor));
        piece.putCell(1, 2, new Cell(PieceBuilder.OColor));
        return piece;
    }

    public static Piece S() {
        Piece piece = new Piece(3,3);
        piece.putCell(0, 1, new Cell(PieceBuilder.SColor));
        piece.putCell(1, 1, new Cell(PieceBuilder.SColor));
        piece.putCell(1, 0, new Cell(PieceBuilder.SColor));
        piece.putCell(2, 0, new Cell(PieceBuilder.SColor));
        return piece;
    }

    public static Piece T() {
        Piece piece = new Piece(3,3);
        piece.putCell(0, 1, new Cell(PieceBuilder.TColor));
        piece.putCell(1, 1, new Cell(PieceBuilder.TColor));
        piece.putCell(2, 1, new Cell(PieceBuilder.TColor));
        piece.putCell(1, 0, new Cell(PieceBuilder.TColor));
        return piece;
    }

    public static Piece Z() {
        Piece piece = new Piece(3,3);
        piece.putCell(0, 0, new Cell(PieceBuilder.ZColor));
        piece.putCell(1, 0, new Cell(PieceBuilder.ZColor));
        piece.putCell(1, 1, new Cell(PieceBuilder.ZColor));
        piece.putCell(2, 1, new Cell(PieceBuilder.ZColor));
        return piece;
    }

    public static Piece Random() {
        int num = PieceBuilder.rand.nextInt(7);
        if(num == 0) {
            return PieceBuilder.I();
        }
        else if(num == 1) {
            return PieceBuilder.J();
        }
        else if(num == 2) {
            return PieceBuilder.L();
        }
        else if(num == 3) {
            return PieceBuilder.O();
        }
        else if(num == 4) {
            return PieceBuilder.S();
        }
        else if(num == 5) {
            return PieceBuilder.T();
        }
        else if(num == 6) {
            return PieceBuilder.Z();
        }
        else {
            return null;
        }
    }
}