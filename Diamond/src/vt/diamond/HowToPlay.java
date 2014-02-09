package vt.diamond;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.widget.RadioGroup;
import android.content.Intent;
import android.view.View;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TextView;
import android.app.Activity;

// -------------------------------------------------------------------------
/**
 *  Write a one-sentence summary of your class here.
 *  Follow it with additional details about its purpose, what abstraction
 *  it represents, and how to use it.
 *
 *  @author Dan
 *  @version Dec 12, 2013
 */
public class HowToPlay
    extends Activity
{
    TextView start;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.howto);
        start = (TextView)findViewById(R.id.start);
    }

    public void gameStarter(View view)
    {
            final boolean[] xOrPg = new boolean[2];

            //prompt the user for input
            LayoutInflater li = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);;
            View promptsView = li.inflate(R.layout.x_or_pg, null);

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    this);

            // set prompts.xml to alertdialog builder
            alertDialogBuilder.setView(promptsView);

            final RadioGroup rating = (RadioGroup) promptsView
                .findViewById(R.id.radioGroupDialog);

            // set dialog message
            alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK",
                  new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        if (rating.getChildAt(1).isSelected()) {
                            xOrPg[0] = true;
                            xOrPg[1] = false;
                            System.out.println("x-rated");
                        }
                        else {
                            xOrPg[1] = true;
                            xOrPg[0] = false;
                            System.out.println("pg-rated");
                        }

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


}
