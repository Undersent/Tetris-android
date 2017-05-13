package com.example.rafal.tetris;

/**
 * Created by rafal on 07.05.17.
 */

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class Painting extends View {

    Paint paint;

    public Grid grid;
    Piece currentPiece;
    SecondPiece nextPiecePanel;

    TextView levelView;
    TextView rowView;
    TextView scoreView;
    static int level =1;
    static int rows;
    static int score;
    static int time = 700;
    static boolean gameOver = false;

    public Painting(Context context) {
        super(context);
        init();
    }

    public Painting(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Painting(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }



    private void init(){
        paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setStrokeWidth(10);
        paint.setStyle(Paint.Style.STROKE);
        grid = new Grid(10,23);// 10,23
        System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");

        

        Runnable runnable = new Runnable()
        {
            @Override
            public void run()
            {
                while(true){
                    try {
                        Thread.sleep(time);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Painting.this.post(new Runnable(){
                        @Override
                        public void run(){
                            if(currentPiece==null)
                                touchDown();
                            else
                            {
                                boolean result = currentPiece.shiftDown();
                                if(!result)
                                    touchDown();
                            }
                            Painting.this.invalidate();
                        }
                    });
                }
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        super.onDraw(canvas);

        int width = getWidth();
        int height = getHeight();
        float blockWidth = width/10.0f;
        float blockHeight = height/20.0f;
        //Log.d("hello", "parameters " + blockWidth + "and" + blockHeight);

        //this.paint.setColor(Color.WHITE);
        this.paint.setStyle(Paint.Style.FILL);
        //canvas.drawPaint(this.paint);
        canvas.drawColor(0x9F000000);


        for(int i = 0; i < 10; i++)
            for(int j2 = 3; j2<23; j2++){
                this.paint.setColor(Color.GRAY);
                int j = j2-3;
                this.paint.setStrokeWidth(4);
                canvas.drawLine(i*blockWidth,0,i*blockWidth,height,this.paint);
                if(i==9){
                    canvas.drawLine(10*blockWidth,0,10*blockWidth,height,this.paint);
                }
                canvas.drawLine(0,j*blockHeight,width,j*blockHeight,this.paint);
                if(j2==22){
                    canvas.drawLine(0,20*blockHeight,width,20*blockHeight,this.paint);
                }


                this.paint.setColor(Color.BLACK);
                Cell cell = grid.getCellAt(i, j2);

                if(cell!=null){
                    this.paint.setColor(cell.getColor());
                    canvas.drawRect(i*blockWidth, j*blockHeight, i*blockWidth + blockWidth, j*blockHeight + blockHeight, this.paint);

                    this.paint.setColor(Color.BLACK);
                    this.paint.setStrokeWidth(4);
                    canvas.drawLine(i*blockWidth, j*blockHeight, i*blockWidth, j*blockHeight + blockHeight, this.paint);
                    canvas.drawLine(i*blockWidth, j*blockHeight, i*blockWidth + blockWidth, j*blockHeight, this.paint);
                    canvas.drawLine(i*blockWidth + blockWidth, j*blockHeight, i*blockWidth + blockWidth, j*blockHeight + blockHeight, this.paint);
                    canvas.drawLine(i*blockWidth, j*blockHeight + blockHeight, i*blockWidth + blockWidth, j*blockHeight + blockHeight, this.paint);

                    //Log.d("hello", "drawing a piece at " + i*blockWidth + "and" + j*blockHeight);
                }
            }
        //this.paint.setColor(Color.BLACK);
        //canvas.drawRect(100, 100, 100, 100, this.paint);
    }



    public void levelUp()
    {
        if(gameOver)
            return;
        level++;
        time *= 0.8;
        updateViews();

    }

    public void updateViews()
    {
        if(levelView!=null)
            levelView.setText("" + level);
        if(rowView!=null)
            rowView.setText(""+rows);
        if(scoreView!=null)
            scoreView.setText(""+score);
    }

    public void reset()
    {
        level = 1;
        rows = 0;
        score = 0;
        grid.clear();
        time = 1000;
        gameOver = false;
        updateViews();
    }

    public void touchDown(){
        if(gameOver)
        {
            Painting.this.invalidate();
            return;
        }
        currentPiece = nextPiecePanel.newNextPiece();
        //updateNextPanel();
        gameOver = !currentPiece.insertIntoGrid(4, 0, grid);
        if(gameOver)
        {
            Toast.makeText(getContext(), "GAME OVER",
                    Toast.LENGTH_LONG).show();
        }


        int fullRow = grid.getFirstFullRow();
        while(fullRow >= 0)
        {
            grid.deleteRow(fullRow);
            rows++;
            score += level;
            if(rows%5==0)
            {
                level++;
                time *= 0.8;
            }

            fullRow = grid.getFirstFullRow();
        }
        updateViews();
        Painting.this.invalidate();
    }



    //private int stateToSave;



    @Override
    public Parcelable onSaveInstanceState() {
        //begin boilerplate code that allows parent classes to save state
        Parcelable superState = super.onSaveInstanceState();

        SavedState ss = new SavedState(superState);
        //end
        Log.d("ccccc","cccccccc");
        ss.stateToSave = this.grid;
        ss.pieceToSave = this.currentPiece;


        return ss;
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        //begin boilerplate code so parent classes can restore state
        if(!(state instanceof SavedState)) {
            super.onRestoreInstanceState(state);
            return;
        }

        SavedState ss = (SavedState)state;
        super.onRestoreInstanceState(ss.getSuperState());
        //end
        //ss.stateToSave.grid.remove(ss.stateToSave.grid.size()-1);
        this.grid = ss.stateToSave;
        this.currentPiece = ss.pieceToSave;

    }

    static class SavedState extends BaseSavedState {
        Grid stateToSave;
        Piece pieceToSave;


        SavedState(Parcelable superState) {
            super(superState);
        }

        private SavedState(Parcel in) {
            super(in);

            this.stateToSave = in.readParcelable(null);
            this.pieceToSave = in.readParcelable(null);
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeParcelable(this.stateToSave,0);
            out.writeParcelable(this.pieceToSave,0);
            /*
             ArrayList<ArrayList<Cell>> gridToSave=null;
            for(int i=0;i<stateToSave.grid.size()-1;i++){
                gridToSave.add(stateToSave.grid.get(i));
            }
            stateToSave.grid = gridToSave;
            out.writeParcelable(this.stateToSave,0);
             */
        }

        //required field that makes Parcelables from a Parcel
        public static final Parcelable.Creator<SavedState> CREATOR =
                new Parcelable.Creator<SavedState>() {
                    public SavedState createFromParcel(Parcel in) {
                        return new SavedState(in);
                    }
                    public SavedState[] newArray(int size) {
                        return new SavedState[size];
                    }
                };
    }


}