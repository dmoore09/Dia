/*
 * Copyright 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package vt.diamond;

import java.io.OutputStreamWriter;
import java.io.FileWriter;
import java.io.BufferedOutputStream;
import java.io.RandomAccessFile;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.InputStream;
import java.io.BufferedWriter;
import android.content.Context;
import android.widget.EditText;
import android.content.DialogInterface;
import android.app.AlertDialog;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * This sample demonstrates how to use system-provided, automatic layout transitions. Layout
 * transitions are animations that occur when views are added to, removed from, or changed within
 * a {@link ViewGroup}.
 *
 * <p>In this sample, the user can add rows to and remove rows from a vertical
 * {@link android.widget.LinearLayout}.</p>
 */
public class LayoutChangesActivity extends Activity {
    /**
     * The container view which has layout change animations turned on. In this sample, this view
     * is a {@link android.widget.LinearLayout}.
     */
    private ViewGroup mContainerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout_changes);
        getActionBar().setDisplayShowHomeEnabled(false);
        getActionBar().setDisplayShowTitleEnabled(false);

        mContainerView = (ViewGroup) findViewById(R.id.container);

        try
        {
            getQuestions();
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.activity_layout_changes, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // Navigate "up" the demo structure to the launchpad activity.
                // See http://developer.android.com/design/patterns/navigation.html for more.
                NavUtils.navigateUpTo(this, new Intent(this, MainActivity.class));
                return true;

            case R.id.action_add_item:
                // Hide the "empty" view since there is now at least one item in the list.


                //prompt the user for input
                LayoutInflater li = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);;
                View promptsView = li.inflate(R.layout.enter_text, null);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        this);

                // set prompts.xml to alertdialog builder
                alertDialogBuilder.setView(promptsView);

                final EditText userInput = (EditText) promptsView
                    .findViewById(R.id.editTextDialogUserInput);

                // set dialog message
                alertDialogBuilder
                    .setCancelable(false)
                    .setPositiveButton("OK",
                      new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,int id) {
                        // get user input and set it to result
                        // edit text
                            if (!userInput.getText().toString().equals("")) {
                            try
                            {
                                addItem(userInput.getText().toString(), true);
                            }
                            catch (FileNotFoundException e)
                            {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                            catch (IOException e)
                            {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                            }
                        }
                      })
                    .setNegativeButton("Cancel",
                      new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,int id) {
                        dialog.cancel();
                        }
                      });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void addItem(String question, boolean write) throws IOException {
        findViewById(android.R.id.empty).setVisibility(View.GONE);
        // Instantiate a new "row" view.
        final ViewGroup newView = (ViewGroup) LayoutInflater.from(this).inflate(
                R.layout.list_item_example, mContainerView, false);

        // Set the text in the new row to a random country.
        ((TextView) newView.findViewById(android.R.id.text1)).setText(
                question);

        if (write) {
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(openFileOutput("usrQues.txt", Context.MODE_APPEND)));
            writer.append(question);
            writer.newLine();
            writer.flush();
            writer.close();
        }
        // Set a click listener for the "X" button in the row that will remove the row.
        newView.findViewById(R.id.delete_button).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

              //remove question from local stoarage
                String removeString = ((TextView)newView.findViewById
                    (android.R.id.text1)).getText().toString();

                  BufferedReader myQues = null;
                  String nextln = null;
                  try
                  {
                      myQues = new BufferedReader(
                          new InputStreamReader(openFileInput("usrQues.txt")));
                      nextln = myQues.readLine();
                  }
                  catch (FileNotFoundException e)
                  {
                      // TODO Auto-generated catch block
                      e.printStackTrace();
                  }
                  catch (IOException e)
                  {
                      // TODO Auto-generated catch block
                      e.printStackTrace();
                  }

                  //buffer to hold new contents of the file
                  StringBuffer contents = new StringBuffer();


                  //copy file
                  while (nextln != null) {

                      if (!removeString.equals(nextln)) {
                          contents.append(nextln + "\n");
                      }
                      try
                      {
                          nextln = myQues.readLine();
                      }
                      catch (IOException e)
                      {
                          // TODO Auto-generated catch block
                          e.printStackTrace();
                      }
                  }

                  //rewrite the file
                  BufferedWriter myQuesNew = null;
                  try
                  {
                      myQuesNew = new BufferedWriter(
                          new OutputStreamWriter(openFileOutput("usrQues.txt", Context.MODE_PRIVATE)));
                      myQuesNew.write(contents.toString());
                      myQuesNew.flush();
                      myQuesNew.close();
                      myQues.close();
                  }
                  catch (FileNotFoundException e)
                  {
                      // TODO Auto-generated catch block
                      e.printStackTrace();
                  }
                  catch (IOException e)
                  {
                      // TODO Auto-generated catch block
                      e.printStackTrace();
                  }


                // Remove the row from its parent (the container view).
                // Because mContainerView has android:animateLayoutChanges set to true,
                // this removal is automatically animated.
                mContainerView.removeView(newView);

                // If there are no rows remaining, show the empty view.
                if (mContainerView.getChildCount() == 0) {
                    findViewById(android.R.id.empty).setVisibility(View.VISIBLE);
                }
            }
        });

        // Because mContainerView has android:animateLayoutChanges set to true,
        // adding this view is automatically animated.
        mContainerView.addView(newView, 0);
    }

    /**
     * A list of user questions.
     */
    private static String[] Questions = new String[60];

    /**
     * on start of the activity get all the users questions
     * @throws IOException
     */
    public void getQuestions() throws IOException {

        BufferedReader myQuestions = null;
        try
        {
            myQuestions = new BufferedReader(
                new InputStreamReader(openFileInput("usrQues.txt")));
        }
        catch (FileNotFoundException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        if (myQuestions != null) {
            String curQues = myQuestions.readLine();

            while (curQues != null) {
                addItem(curQues, false);
                System.out.println(curQues);
                curQues = myQuestions.readLine();

            }
            myQuestions.close();
        }



    }
}
