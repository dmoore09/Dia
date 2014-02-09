package vt.diamond;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.RadioGroup;
import android.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.annotation.SuppressLint;
import android.content.Intent;
import java.util.ArrayList;
import android.os.Vibrator;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import android.view.MenuItem;
import android.view.Menu;
import java.util.Random;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;

public class GameActivityPlain
    extends FragmentActivity
{
    private ViewPager myViewPager;
    private PagerAdapter adapter;
    private static int numberPages = 50;
    private static int cardNum = -1;
    //reset every time a new card is grabbed
    private long curTime = 0;
    //used as a benchmark to see how much time has elapsed
    private long oldTime = 0;
    private Question[] questionsf;
    static Vibrator v;


    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        getActionBar().setDisplayShowHomeEnabled(false);
        getActionBar().setDisplayShowTitleEnabled(false);
        getActionBar().setDisplayShowCustomEnabled(true);

        v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        Bundle b= this.getIntent().getExtras();
        boolean[] pref = b.getBooleanArray("pref");

        String wyrLvl;
        String sexLvl;
        int sexNum;
        int wyrNum;

        if (pref[0]) {
            sexLvl = "questions/sexHot.txt";
            sexNum = 65;
            wyrLvl = "questions/wyrHot.txt";
            wyrNum = 21;
        }
        else {
            sexLvl = "questions/questionsBeta.txt";
            sexNum = 98;
            wyrLvl = "questions/wyr.txt";
            wyrNum = 30;
        }
        //pull 100 random questions from disk, if the user has favorites
        //then make sure those are incorporated
        questionsf = null;
        try
        {
            questionsf = createQuestionBank(wyrLvl, sexLvl, wyrNum, sexNum);
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);

        myViewPager = (ViewPager) findViewById(R.id.pager);
        adapter = new PagerAdapter
            (getSupportFragmentManager(), questionsf);
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
            cardNum = index;
            //time cards
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
    public Question[] createQuestionBank(String wyrLvl, String sexLvl, int wyrAmount, int sexAmount) throws IOException {

        Question[] questions = new Question[52];

        BufferedReader wyr = null;
        BufferedReader sex = null;
        BufferedReader favorites = null;
        BufferedReader myQuestions = null;
        //loop through bool array and open file pointers
        //for each desired question type

            wyr = new BufferedReader(new BufferedReader(
                new InputStreamReader(getAssets().open(wyrLvl))));
            wyr.mark(10000);


        String favQ = null;

            favorites = new BufferedReader(
                new InputStreamReader(openFileInput("usr.dat")));
            favQ = favorites.readLine();


        String myQ = null;

            //open up user questions
            myQuestions = new BufferedReader(
                new InputStreamReader(openFileInput("usrQues.txt")));
            myQ = myQuestions.readLine();


         sex = new BufferedReader(new BufferedReader(
               new InputStreamReader(getAssets().open(sexLvl))));
         sex.mark(10000);

        //generate a random number from 0-51 for choosing cards
        Random randomGenerator = new Random();
        int cur = randomGenerator.nextInt(50);
        int wyrNum = 0;
        int sexNum = 0;


        //array to keep track of what questions have been used to duplicates wont
        //be added
        ArrayList<Integer> questionsUsedSex = new ArrayList<Integer>();
        ArrayList<Integer> questionsUsedWYR = new ArrayList<Integer>();

        //populate the array with questions
        for (int i = 0; i < 50; i++) {
            //favorite question
            if (i % 5 == 0 && favorites != null && favQ != null) {
                Question q = new Question(favQ, 0);
                questions[i] = q;
                favQ = favorites.readLine();
            }
            //write your own question
            //TODO make sure it does not repeat
            if (i % 6 == 0 && myQuestions != null && myQ != null) {
                Question q = new Question(myQ, 0);
                questions[i] = q;
                myQ = myQuestions.readLine();
            }
            //drink card
            else if (cur <= 10 && i > 1) {
                Question q = new Question("Drink", wyrNum);
                questions[i] = q;
            }
            //hot seat
            else if (cur > 10 && cur <= 20) {
                Question q = new Question("Hot Seat: " + getRandomQuestion(sex, questionsUsedSex, sexAmount).getContent(), wyrNum);
                questions[i] = q;
                wyrNum++;
            }
            //would you rather
            else if (cur > 20 && cur <= 30 && wyr != null) {
                questions[i] = getRandomQuestion(wyr, questionsUsedWYR, wyrAmount);
            }

            else {
                questions[i] = getRandomQuestion(sex, questionsUsedSex, sexAmount);
                sexNum++;
            }
            cur = randomGenerator.nextInt(51);
        }
        if (wyr != null) {
            wyr.close();
        }

        sex.close();
        return questions;
    }



    /**
     * gets a random question from the text file
     * @param file to get the line from
     * @param prevQues array representing line numbers that cant be used
     * @param size amount of lines in file
     * @return new question
     */
    public Question getRandomQuestion(BufferedReader file, ArrayList<Integer> prevQues, int size) {

        Random randomGenerator = new Random();
        int cur = randomGenerator.nextInt(size);
        //check to see if the question has been used before
        for (int i = 0; i < prevQues.size(); i++) {
            if (cur == prevQues.get(i)) {
                cur = randomGenerator.nextInt(size);
                i--;
            }
        }
        prevQues.add(cur);

        try
        {
            //move file pointer
            for (int i = 0; i < cur; i++) {
                file.readLine();
            }
            Question q = new Question(file.readLine(), 0);
            file.reset();
            return q;
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;

    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.


        //getMenuInflater().inflate(R.menu.main, menu);

        ActionBar actionBar = getActionBar();
        View mActionBarView = getLayoutInflater().inflate(R.layout.gamebar, null);
        actionBar.setCustomView(mActionBarView);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        return true;
    }

    public boolean favIt(View view) {
        //set user data so that the question is a favorite
        //get user data
        BufferedWriter favs = null;
        BufferedReader favRead = null;
        try
        {
            favs = new BufferedWriter(new OutputStreamWriter(openFileOutput("usr.dat", Context.MODE_APPEND)));

            favRead = new BufferedReader(new InputStreamReader(openFileInput("usr.dat")));

            String nextLn = favRead.readLine();

            while(nextLn != null) {
                System.out.println("here");
                if (nextLn.equals(questionsf[cardNum].getContent())) {
                    return true;
                }
                nextLn = favRead.readLine();
            }

            if (!questionsf[cardNum].equals("Drink")) {
                favs.append(questionsf[cardNum].getContent());
                favs.newLine();
                favs.flush();
                favs.close();
            }
        }
        catch (IOException e)
        {
                // TODO Auto-generated catch block
                e.printStackTrace();
        };

      //prompt the user for input
        LayoutInflater li = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);;
        View promptsView = li.inflate(R.layout.fav_notification, null);

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
                    //TODO nothing.... Do I need this?
                }
              });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();

        return true;
    }

    public void home(View view) {
        Intent newGamePage = new Intent(this, MainActivity.class);
        startActivity(newGamePage);
        finish();
    }

// ----------------------------------------------------------

    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        switch (item.getItemId()) {
        //mark question as a favorite
        case R.id.favorite:


            //set user data so that the question is a favorite
            //get user data
            BufferedWriter favs = null;
            BufferedReader favRead = null;
            try
            {
                favs = new BufferedWriter(new OutputStreamWriter(openFileOutput("usr.dat", Context.MODE_APPEND)));

                favRead = new BufferedReader(new InputStreamReader(openFileInput("usr.dat")));

                String nextLn = favRead.readLine();

                while(nextLn != null) {
                    System.out.println("here");
                    if (nextLn.equals(questionsf[cardNum].getContent())) {
                        return true;
                    }
                    nextLn = favRead.readLine();
                }

                if (!questionsf[cardNum].equals("Drink")) {
                    favs.append(questionsf[cardNum].getContent());
                    favs.newLine();
                    favs.flush();
                    favs.close();
                }
            }
            catch (IOException e)
            {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
            };
            return true;
            //home button brings user back to the home page
        case R.id.home:
            Intent newGamePage = new Intent(this, MainActivity.class);
            startActivity(newGamePage);
            finish();
            return true;
        default:
            return true;
        }
    }

    /**
     * make sure the favorites bank does not contain duplicate questions
     */
    public boolean contains(String ques, BufferedReader file) {
        String nextLn = null;
        try
        {
            nextLn = file.readLine();
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        while(nextLn != null) {
            if (nextLn == ques) {
                return true;
            }
        }
        return false;
    }
}
