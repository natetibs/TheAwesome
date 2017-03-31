package com.example.computerz.myapplication;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;

/*
created by Nathan Tibbetts on March 29, 2017
    I liked the look of CustomRect which was created by Dr. Nux, so I decided to go with it and created a pixelated pikachu. He is retro and adorable!

    The MainActivity has the listeners for the seekbar and the touchevents. It controls changing all the colors accordingly and the text when it appropriate

    it uses ColorsCaboom to implement the surface view in which everything is drawn

   redVal is the current red val, same with green and blue, colorVal is the combined color of the three
   elementNum is which part the user has pressed, either the background, the cheeks, the eyes, the mouth, or the outline
   redback, greenback, and blueback are specifically saved to make it easier to keep track of the background colors




 */

public class MainActivity extends AppCompatActivity implements View.OnTouchListener, SeekBar.OnSeekBarChangeListener {

    protected int redVal, greenVal, blueVal, colorVal, elementNum, redBack, greenBack, blueBack;

    protected ColorsCaboom colorz;
    protected TextView t; //controls the textview
    protected SeekBar redBar;
    protected SeekBar greenBar;
    protected SeekBar blueBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        colorz = (ColorsCaboom) findViewById(R.id.surfaceView);
        colorz.setOnTouchListener(this);

        t = (TextView)findViewById(R.id.textView7);
        t.setText("Background"); //initially all the sliders and the textview is set to the background because no object has been selected

        redBack = 255;
        greenBack = 64;
        blueBack = 129;
        redVal = 255;
        greenVal = 64;
        blueVal = 129;
        elementNum = -1; //element number -1 is background, 0 is outline, 1 is left cheek, 2 is right cheek, 3 is left eye, 4 is right eye, 5 is mouth
        // start with modifying background

        //initialize background color to hot pink
        colorVal = Color.rgb(redBack, greenBack, blueBack);
        colorz.setBackgroundColor(colorVal);
        t.setTextColor(colorVal); //the textcolor is going to match whatever color you are messing with

        //seek bars for each color
        redBar = (SeekBar) findViewById(R.id.redBar);
        redBar.setOnSeekBarChangeListener(this);
        redBar.setProgress(redBack);

        greenBar = (SeekBar) findViewById(R.id.greenBar);
        greenBar.setOnSeekBarChangeListener(this);
        greenBar.setProgress(greenBack);

        blueBar = (SeekBar) findViewById(R.id.blueBar);
        blueBar.setOnSeekBarChangeListener(this);
        blueBar.setProgress(blueBack);

    }

    /**
     * External Citation
     * Date: 28 March 2017
     * Problem: Needed to get the individual red, green, and blue ints from a color that contains a large int
     * Resource:
     * https://developer.android.com/reference/android/graphics/Color.html
     * Solution: I used the example code from this post to extract each color using binary arithmetic
     */

    public void setBar(int color) {
        int red = (color >> 16) & 0xff;
        int green = (color >> 8) & 0xff;
        int blue = (color) & 0xff;
        redBar.setProgress(red);
        greenBar.setProgress(green);
        blueBar.setProgress(blue);

    }

    //overloaded method so that we can take a big Color or individual red, green, blue elements
    public void setBar(int red, int green, int blue) {
        redBar.setProgress(red);
        greenBar.setProgress(green);
        blueBar.setProgress(blue);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        //first checks to see if the outline got the touch, if not, it moves on to left cheek, right cheek, and so on
        //if it gets the touch, it sets the elementNumber to itself so that later when the colors get changed, the program
        //will know which element's colors to change
        //the name, text color, and seek bars all get set when one of the objects get touched

        if (groupContainsPoint((int) event.getX(), (int) event.getY(), colorz.getOutline())) {
            elementNum = 0;
            setBar(colorz.getOutline().get(0).getColor());
            t.setText(colorz.getOutline().get(0).getName());
            t.setTextColor(colorz.getOutline().get(0).getColor());
        }
        else if(groupContainsPoint((int) event.getX(), (int) event.getY(), colorz.getLeftCheek())) {

            elementNum = 1;
            setBar(colorz.getLeftCheek().get(0).getColor());
            t.setText(colorz.getLeftCheek().get(0).getName());
            t.setTextColor(colorz.getLeftCheek().get(0).getColor());
        }
        else if(groupContainsPoint((int) event.getX(), (int) event.getY(), colorz.getRightCheek())) {

            elementNum = 2;
            setBar(colorz.getRightCheek().get(0).getColor());
            t.setText(colorz.getRightCheek().get(0).getName());
            t.setTextColor(colorz.getRightCheek().get(0).getColor());
        }
        else if(groupContainsPoint((int) event.getX(), (int) event.getY(), colorz.getLeftEye())) {

            elementNum = 3;
            setBar(colorz.getLeftEye().get(0).getColor());
            t.setText(colorz.getLeftEye().get(0).getName());
            t.setTextColor(colorz.getLeftEye().get(0).getColor());
        }
        else if(groupContainsPoint((int) event.getX(), (int) event.getY(), colorz.getRightEye())) {

            elementNum = 4;
            setBar(colorz.getRightEye().get(0).getColor());
            t.setText(colorz.getRightEye().get(0).getName());
            t.setTextColor(colorz.getRightEye().get(0).getColor());
        }
        else if(groupContainsPoint((int) event.getX(), (int) event.getY(), colorz.getMouth())) {

            elementNum = 5;
            setBar(colorz.getMouth().get(0).getColor());
            t.setText(colorz.getMouth().get(0).getName());
            t.setTextColor(colorz.getMouth().get(0).getColor());
        }
        else {
            elementNum = -1;
            setBar(redBack, greenBack, blueBack);
            t.setText("Background");
            t.setTextColor(Color.rgb(redBack, greenBack, blueBack));
            }

        return true;

    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

        //checks to see which bar got the change, and then changes values accordingly
        if (seekBar == redBar) {
            redVal = progress;
        }
        if (seekBar == greenBar) {
            greenVal = progress;
        }
        if (seekBar == blueBar) {
            blueVal = progress;
        }

        //penultimate color because it mixed rgb
        colorVal = Color.rgb(redVal, greenVal, blueVal);

        //now we use the elementNums to figure out which object was most recently touched, and change it accordingly
        if (elementNum == -1) {
            colorz.setBackgroundColor(colorVal);
            redBack = redVal;
            greenBack = greenVal;
            blueBack = blueVal;
            t.setTextColor(Color.rgb(redVal, greenVal, blueVal));
        }

        //since I am using pixel art, I need to color every pixel (or rectangle) in the section, hence the for loop
        if (elementNum == 0) {
            for (int i = 0; i < colorz.getOutline().size(); i++) {
                colorz.getOutline().get(i).setColor(colorVal);

            }
            t.setTextColor(Color.rgb(redVal, greenVal, blueVal));
            colorz.invalidate();

        }

        if (elementNum == 1) {
            for (int i = 0; i < colorz.getLeftCheek().size(); i++) {
                colorz.getLeftCheek().get(i).setColor(colorVal);

            }
            t.setTextColor(Color.rgb(redVal, greenVal, blueVal));
            colorz.invalidate();
        }

        if (elementNum == 2) {
            for (int i = 0; i < colorz.getRightCheek().size(); i++) {
                colorz.getRightCheek().get(i).setColor(colorVal);

            }
            t.setTextColor(Color.rgb(redVal, greenVal, blueVal));
            colorz.invalidate();

        }

        if (elementNum == 3) {
            for (int i = 0; i < colorz.getLeftEye().size(); i++) {
                colorz.getLeftEye().get(i).setColor(colorVal);

            }
            t.setTextColor(Color.rgb(redVal, greenVal, blueVal));
            colorz.invalidate();

        }

        if (elementNum == 4) {
            for (int i = 0; i < colorz.getRightEye().size(); i++) {
                colorz.getRightEye().get(i).setColor(colorVal);

            }
            t.setTextColor(Color.rgb(redVal, greenVal, blueVal));
            colorz.invalidate();
        }
        if (elementNum == 5) {
            for (int i = 0; i < colorz.getMouth().size(); i++) {
                colorz.getMouth().get(i).setColor(colorVal);

            }
            t.setTextColor(Color.rgb(redVal, greenVal, blueVal));
            colorz.invalidate();

        }
    }

    //had to override these methods to use the seekbar
    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    //checks entire group for the point of contact. Since there are many blocks to any given part
    //of the picture it has to go through each one to see if it got touched. If it did, it immediately
    //returns true
    public boolean groupContainsPoint(int x, int y, ArrayList<CustomRect> rectList){
        for(int i = 0; i < rectList.size(); i++){
            if(rectList.get(i).containsPoint(x, y)){
                return true;
            }
        }


        return false;
    }
}
