package vt.diamond;

import android.widget.RadioGroup;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.CheckBox;
import java.io.IOException;
import java.io.InputStream;
import android.content.Intent;
import android.view.View;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TextView;
import android.annotation.SuppressLint;
import android.app.Activity;

// -------------------------------------------------------------------------
/**
 *  allow players to choose what types of questions they would like to have
 *  and create a bank with a random selection of those types of questions
 *
 *  @author Dan
 *  @version Dec 14, 2013
 */
public class ConfigureGame
    extends Activity
{
    private boolean[] desiredTypes;
    private CheckBox wyr;
    private CheckBox sex;


    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        getActionBar().hide();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.gameconfig);
        desiredTypes = new boolean[7];
        desiredTypes[1] = true;
        desiredTypes[0] = true;
        desiredTypes[2] = true;
        desiredTypes[3] = true;
        //hotness level
        desiredTypes[4] = true;
        desiredTypes[5] = false;
        desiredTypes[6] = false;

        //get references to check boxes
        wyr = (CheckBox) findViewById (R.id.checkBox2);
        sex = (CheckBox) findViewById (R.id.checkBox3);
        CheckBox drugs = (CheckBox) findViewById (R.id.checkBox4);
        CheckBox life = (CheckBox) findViewById (R.id.checkBox1);
        CheckBox my = (CheckBox) findViewById (R.id.checkBox6);
        CheckBox fav = (CheckBox) findViewById (R.id.checkBox5);

        wyr.setChecked(true);
        my.setChecked(true);
        fav.setChecked(true);
        sex.setChecked(true);
        life.setChecked(true);
        drugs.setChecked(true);

        RadioGroup hotness = (RadioGroup) findViewById (R.id.radioGroup1);

        hotness.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                TextView text = (TextView) findViewById (R.id.explain);
                switch(checkedId)
                {
                case R.id.radio0:
                    text.setText("Hotness level: low, questions that you would could answer around your parents");
                    desiredTypes[5] = false;
                    desiredTypes[4] = true;
                    desiredTypes[6] = false;
                    break;
                case R.id.radio2:
                    text.setText("Hotness level: medium, a good mix of appropriate and innapropiate questions");
                    desiredTypes[5] = true;
                    desiredTypes[4] = false;
                    desiredTypes[6] = false;
                    break;
                case R.id.radio1:
                    text.setText("Hotness level: high, not reccomended for families");
                    desiredTypes[5] = false;
                    desiredTypes[4] = false;
                    desiredTypes[6] = true;
                    break;
                }

            }

        });



        wyr.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton arg0, boolean arg1)
            {
                if (arg0.isChecked()) {
                    desiredTypes[0] = true;
                }
                else {
                    desiredTypes[0] = false;
                }

            }
          });

        sex.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton arg0, boolean arg1)
            {
                if (arg0.isChecked()) {
                    desiredTypes[1] = true;
                }
                else {
                    //true by default in case user doesnt click any questions
                    desiredTypes[1] = true;
                }

            }
          });

        my.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton arg0, boolean arg1)
            {
                if (arg0.isChecked()) {
                    desiredTypes[3] = true;
                }
                else {
                    //true by default in case user doesnt click any questions
                    desiredTypes[3] = true;
                }

            }
          });

        fav.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton arg0, boolean arg1)
            {
                if (arg0.isChecked()) {
                    desiredTypes[2] = true;
                }
                else {
                    //true by default in case user doesnt click any questions
                    desiredTypes[2] = true;
                }

            }
          });
    }



    /**
     * Starts a new game when start button is pressed
     * @param view start button
     */
    public void start(View view) {
        Bundle b= new Bundle();
        b.putBooleanArray("pref", desiredTypes);
        Intent mainPage = new Intent(this, GameActivity.class);
        mainPage.putExtras(b);
        startActivity(mainPage);
        finish();
    }

}
