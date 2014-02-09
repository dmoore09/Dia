package vt.diamond;

import android.content.Context;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

// -------------------------------------------------------------------------
/**
 *  The user class knows the players preferences, favorite questions, age.
 *  When the app is closed the user file is written to disk
 *
 *  TODO limit on the filesize?? probably wont need it. Favorite Questions can
 *  be stored in the form of handles which would be file position in question
 *  bank
 *
 *  @author Dan
 *  @version Dec 12, 2013
 */
public class User
{
    private int age;
    private int[] questions;

    // ----------------------------------------------------------
    /**
     * Create a new User object. All that is required is the age of the user
     * other data will be collected as the user plays.
     * @param age of user
     */
    public User() {
        questions = new int[50];

        //opens up the new user file
        //set up user data

    }



}
