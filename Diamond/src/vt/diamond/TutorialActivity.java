package vt.diamond;

import android.app.ActionBar;
import android.view.View;
import android.widget.TextView;
import android.view.WindowManager;
import android.graphics.Bitmap;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Random;
import android.app.Activity;

// -------------------------------------------------------------------------
/**
 *  Interactive tutorial loads on first start up, can be used again by
 *  going to settings
 *
 *  @author Dan
 *  @version Jan 2, 2014
 */
public class TutorialActivity
    extends FragmentActivity
{
    private ViewPager myViewPager;
    private PagerAdapter adapter;
    private static int numberPages = 9;
    private static int cardNum = -1;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        getActionBar().setDisplayShowHomeEnabled(false);
        getActionBar().setDisplayShowTitleEnabled(false);
        getActionBar().setDisplayShowCustomEnabled(true);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);

        Question[] questionsf = new Question[5];


        myViewPager = (ViewPager) findViewById(R.id.pager);
        try
        {
            adapter = new PagerAdapter
                (getSupportFragmentManager(), createQuestionBank());
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        myViewPager.setAdapter(adapter);
        myViewPager.setPageTransformer(true, new ZoomOutPageTransformer());
        myViewPager.setOffscreenPageLimit(15);

        myViewPager.getCurrentItem();
    }

    @Override
    public void onBackPressed() {
        if (myViewPager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        } else {
            // Otherwise, select the previous step.
            myViewPager.setCurrentItem(myViewPager.getCurrentItem() - 1);
        }
    }

    // Feeds Pages into ViewPager
    private static class PagerAdapter extends FragmentPagerAdapter {

        private Question[] questions;

        //create a new pagerAdapter
        public PagerAdapter(FragmentManager fm, Question[] questions2)   {
            super(fm);
            questions = questions2;
        }

        //create new instance of PageFragment with test text
        @Override
        public Fragment getItem(int index) {
            //moving to next card
            cardNum++;

            return GameFragment.newInstance(questions[index].getContent());


        }

        //set the number of pages to be displayed
        @Override
        public int getCount() {
            return numberPages;
        }

        /**
         * get current item
         * @return card num
         */
        public int getCurr() {
            return cardNum;
        }
    }

    /**
     * create a question bank based on users specifications in
     * Distribution of the questions:
     *
     * Drink Cards: 25 percent chance on average 13 cards (0-12)
     *
     * Hot seat: Same as an ace 4/52 (13-16)
     *
     * Would You Rather: equivalant to a face card 12/52 (17-28)
     *
     * Sex/Life/Drugs: rest of cards (29-51)
     *
     * game configuration activity
     * @param pref array that tells us what types of questions the user wants
     * @return question bank
     * @throws IOException
     */
    public Question[] createQuestionBank() throws IOException {

        Question[] questions = new Question[9];

        BufferedReader tutorialTxt = null;
        tutorialTxt = new BufferedReader(new BufferedReader(
               new InputStreamReader(getAssets().open("questions/tutorial.txt"))));

        //populate the array with questions
        for (int i = 0; i < 9; i++) {
            Question ques = new Question(tutorialTxt.readLine(), 0);
            questions[i] = ques;
        }
        return questions;
    }

    public boolean onCreateOptionsMenu(Menu menu){
        // Inflate the menu; this adds items to the action bar if it is present.
        ActionBar actionBar = getActionBar();
        View mActionBarView = getLayoutInflater().inflate(R.layout.gamebar, null);
        actionBar.setCustomView(mActionBarView);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        return true;
    }


    public boolean favIt(View view) {
        return true;
    }

    public void home(View view) {
        Intent newGamePage = new Intent(this, MainActivity.class);
        startActivity(newGamePage);
        finish();
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        switch (item.getItemId()) {
        //mark question as a favorite
        case R.id.favorite:
            return true;
            //home button brings user back to the home page
        case R.id.home:
            Intent main = new Intent(this, MainActivity.class);
            startActivity(main);
            finish();
            return true;
        default:
            return true;
        }
    }

// ----------------------------------------------------------
}
