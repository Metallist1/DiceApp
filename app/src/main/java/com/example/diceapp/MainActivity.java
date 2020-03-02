package com.example.diceapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> diceRollHistory = new ArrayList<String>();
    private Spinner dropdownList;
    private LinearLayout diceLayout;
    private ArrayList<String> lastRoll = new ArrayList<>();

    Random randomGenerator ;
    int totalDiceCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpTheGame();

        findViewById(R.id.btn_roll).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewRoll();
            }
        });

        findViewById(R.id.next_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent myIntent = new Intent(view.getContext(), SecondActivity.class); //Create intent
                myIntent.putExtra("EXTRA_HISTORY_DATA", diceRollHistory); //Add Extra info
                startActivityForResult(myIntent, 0); //Wait for results
            }

        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                diceRollHistory = data.getStringArrayListExtra("HISTORY_BACK"); // Replace history with new one. Its used for clear function only
            }
        }
    }

    private void addNewRoll(){
        lastRoll.clear();
        String newAddition = "";
        for(int i = 0 ; i <totalDiceCount ; i++) {
            int rolledNumber = rollDice();
            changeDice(i , rolledNumber);
            lastRoll.add(Integer.toString(rolledNumber));
            if(i + 1 <totalDiceCount) {
                newAddition = newAddition + Integer.toString(rolledNumber) + " - ";
            }else{
                newAddition = newAddition + Integer.toString(rolledNumber);
            }
        }

        diceRollHistory.add(newAddition);

    }




    private void setUpTheGame(){
        randomGenerator = new Random(); //Set up random for generating value for dice
        totalDiceCount = 0; // Set up initial dice count. For rolling in history
        setUpDropdown();

        diceLayout = (LinearLayout) findViewById(R.id.diceLayout);

//setUpExtraDice();
    }



    private void setUpDropdown(){

        dropdownList = findViewById(R.id.dropdown_diceNum);

        ArrayList<Integer> options=new ArrayList<Integer>();

        options.add(1);
        options.add(2);
        options.add(3);
        options.add(4);
        options.add(5);
        options.add(6);

        ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(this,android.R.layout.simple_spinner_item,options);
        dropdownList.setAdapter(adapter); // Set up adapter to dropdown

        dropdownList.setSelection(options.indexOf(2)); // Set up initial count


        dropdownList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                totalDiceCount= (Integer) dropdownList.getSelectedItem();
                setUpExtraDice();
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });
    }

    private void setUpExtraDice(){
        diceLayout.removeAllViews();
        for(int x=0;x<totalDiceCount;x++) {
            ImageView image = new ImageView(MainActivity.this);

            image.setImageDrawable(getResources().getDrawable(getResources().getIdentifier("@drawable/dice1", null, getPackageName())));
            image.setId(x);
            image.setLayoutParams(new LinearLayout.LayoutParams(200, 200));
            image.setScaleType(ImageView.ScaleType.FIT_CENTER);
            diceLayout.addView(image);

        }
    }

    private void setUpExtraHistoryDice(ArrayList<String> listas){
        diceLayout.removeAllViews();
        for(int x=0;x<listas.size();x++) {


            ImageView image = new ImageView(MainActivity.this);

            String uri = "@drawable/dice"+ listas.get(x);  // where myresource (without the extension) is the file

            int imageResource = getResources().getIdentifier(uri, null, getPackageName());

            Drawable res = getResources().getDrawable(imageResource);
            image.setImageDrawable(res);
           image.setId(x);
            image.setLayoutParams(new LinearLayout.LayoutParams(200, 200));
            image.setScaleType(ImageView.ScaleType.FIT_CENTER);
            diceLayout.addView(image);

        }
    }

    private int rollDice(){
        return randomGenerator.nextInt((5)+1)+1;
    }

    private void changeDice(int currentDice , int changeToWhichSide){
        ImageView image = (ImageView) findViewById(getResources().getIdentifier(Integer.toString(currentDice), "id", getPackageName()));

        String uri = "@drawable/dice"+ changeToWhichSide;  // where myresource (without the extension) is the file

        int imageResource = getResources().getIdentifier(uri, null, getPackageName());

        Drawable res = getResources().getDrawable(imageResource);
        image.setImageDrawable(res);
    }


    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        System.out.println("Lol");
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putStringArrayList("History", diceRollHistory);
        savedInstanceState.putStringArrayList("LastRoll", lastRoll);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        System.out.println("KK");
        super.onRestoreInstanceState(savedInstanceState);
        diceRollHistory = savedInstanceState.getStringArrayList("History");
        lastRoll = savedInstanceState.getStringArrayList("LastRoll");
        System.out.println(lastRoll);
setUpExtraHistoryDice(lastRoll);

    }
}
