package vt.diamond;


import android.widget.RadioGroup;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.CheckBox;
import android.widget.EditText;
import android.view.LayoutInflater;
import java.io.IOException;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.app.AlertDialog;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import android.content.Context;
import java.io.FileOutputStream;
import android.view.MenuItem;
import android.graphics.Typeface;
import android.widget.TextView;
import android.view.View;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class MainActivity
    extends Activity
{

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getActionBar().hide();

        //first time a user opens the app create a new user an user
        //disk file
        createOrLoadUser();


    }


    // ----------------------------------------------------------
    /**
     * creates a new user or loads an old one
     */
    public void createOrLoadUser(){

        //create a new user or load an old one
        String FILENAME = "usr.dat";
        String FILENAME2 = "usrQues.txt";
        FileInputStream userFile = null;
        FileInputStream userFile2 = null;
        try
        {
            userFile = openFileInput(FILENAME);
        }
        catch (FileNotFoundException e1)
        {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        try
        {
            userFile2 = openFileInput(FILENAME2);
        }
        catch (FileNotFoundException e1)
        {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        if (userFile2 == null) {
            try
            {
                FileOutputStream fos1 = openFileOutput(FILENAME2, Context.MODE_PRIVATE);
                try
                {
                    fos1.flush();
                    fos1.close();
                }
                catch (IOException e)
                {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                //first time the user opened the activity start tutorial
                Intent tutorial = new Intent(this, TutorialActivity.class);
                startActivity(tutorial);

            }
            catch (FileNotFoundException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        else {
            System.out.println("FILE exists");
        }
        //On first open create a new file
        if (userFile == null){
            try
            {

                FileOutputStream fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
                try
                {
                    fos.flush();
                    fos.close();
                }
                catch (IOException e)
                {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
            catch (FileNotFoundException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        //otherwise read user data from the old file
        else {
            System.out.println("FILE exists");
        }


    }

    // ----------------------------------------------------------
    /**
     * Starts a new game
     */
    public void newGame(View view) {

        final boolean[] xOrPg = new boolean[2];
        xOrPg[0] = true;
        xOrPg[1] = false;

        //prompt the user for input
        LayoutInflater li = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);;
        View promptsView = li.inflate(R.layout.x_or_pg, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);

        // set prompts.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);

        final RadioGroup rating = (RadioGroup) promptsView
            .findViewById(R.id.radioGroupDialog);
        rating.getChildAt(0).setSelected(true);

        rating.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                switch(checkedId)
                {
                case R.id.radioDialog:
                    xOrPg[0] = true;
                    xOrPg[1] = false;
                    System.out.println("x-rated");
                    break;
                case R.id.radioDialog1:
                    xOrPg[1] = true;
                    xOrPg[0] = false;
                    System.out.println("pg-rated");
                    break;
                }

            }

        });

        // set dialog message
        alertDialogBuilder
            .setCancelable(false)
            .setPositiveButton("OK",
              new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog,int id) {

                    System.out.println("starting game!");
                    //TODO change once we get configure game in
                    Intent mainPage = new Intent(getBaseContext(), GameActivityPlain.class);
                    Bundle b = new Bundle();
                    b.putBooleanArray("pref", xOrPg);
                    mainPage.putExtras(b);
                    startActivity(mainPage);
                }
              })
            .setNegativeButton("Cancel",
              new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog,int id) {
                dialog.cancel();
                xOrPg[1] = false;
                xOrPg[0] = false;
                }
              });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }

    /**
     * Starts a new game
     */
    public void howTo(View view) {
        Intent howTo = new Intent(this, HowToPlay.class);
        startActivity(howTo);
    }

    /**
     * opens the settings page
     */
    public void settings(View view) {
        Intent settings = new Intent(this, SettingsActivity.class);
        startActivity(settings);
    }


}
