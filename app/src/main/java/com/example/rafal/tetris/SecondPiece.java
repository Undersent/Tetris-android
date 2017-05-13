package com.example.rafal.tetris;

/**
 * Created by rafal on 07.05.17.
 */

import android.view.View;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;


public class SecondPiece extends View {

    Paint paint;
    Grid grid;
    Piece nextPiece;

    public SecondPiece(Context context) {
        super(context);
        init();
    }

    public SecondPiece(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SecondPiece(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init(){
        paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setStrokeWidth(10);
        paint.setStyle(Paint.Style.STROKE);
        grid = new Grid(5, 3);
        newNextPiece();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        super.onDraw(canvas);

        int width = getWidth();
        int height = getHeight();
        float pieceWidth = width/5.0f-5;
        float pieceHeight = height/3.0f-20;
        Log.d("hello", "DRAWINGGGGGGG");

        //this.paint.setColor(Color.WHITE);
        this.paint.setStyle(Paint.Style.FILL);
        //canvas.drawPaint(this.paint);
        canvas.drawColor(0x00000000);

        this.paint.setColor(Color.WHITE);
        //canvas.drawRect(0, 0, 100, 100, this.paint);


        for(int i = 0; i < 5; i++)
            for(int j = 0; j<3; j++){
                Cell cell = grid.getCellAt(i, j);
                if(cell!=null){
                    //Before you can call any drawing methods, though, it's necessary to create a Paint object.

                    this.paint.setColor(cell.getColor());
                    canvas.drawRect(i*pieceWidth, j*pieceHeight, i*pieceWidth + pieceWidth, j*pieceHeight + pieceHeight, this.paint);

                    this.paint.setColor(Color.WHITE);
                    this.paint.setStrokeWidth(4);
                    //Lines on window
                    canvas.drawLine(i*pieceWidth, j*pieceHeight, i*pieceWidth, j*pieceHeight + pieceHeight, this.paint);
                    canvas.drawLine(i*pieceWidth, j*pieceHeight, i*pieceWidth + pieceWidth, j*pieceHeight, this.paint);
                    canvas.drawLine(i*pieceWidth + pieceWidth, j*pieceHeight, i*pieceWidth + pieceWidth, j*pieceHeight + pieceHeight, this.paint);
                    canvas.drawLine(i*pieceWidth, j*pieceHeight + pieceHeight, i*pieceWidth + pieceWidth, j*pieceHeight + pieceHeight, this.paint);

                }
            }



    }

    //Notice that setShowText calls invalidate() and requestLayout().
    // These calls are crucial to ensure that the view behaves reliably.
    // You have to invalidate the view after any change to its properties that might change its appearance,
    // so that the system knows that it needs to be redrawn.
    public Piece newNextPiece()
    {
        if(nextPiece!=null)
            nextPiece.removeFromGrid();
        Piece returnPiece = nextPiece;
        nextPiece = PieceBuilder.Random();
        nextPiece.insertIntoGrid(1, 0, grid);
        this.invalidate();
        return returnPiece;
    }


}


