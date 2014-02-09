package vt.diamond;
import android.widget.TextView;
import android.widget.SimpleAdapter;
import android.content.Intent;
import android.widget.AdapterView.OnItemClickListener;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.os.Bundle;
import android.app.Activity;

// -------------------------------------------------------------------------
/**
 *  Settings page allows users to edit favorite and their own questions
 *
 *  @author Dan
 *  @version Dec 29, 2013
 */
public class SettingsActivity
    extends Activity
{

    TextView my;
    TextView fav;

    protected void onCreate(Bundle savedInstanceState)
    {

        getActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
    }

    // ----------------------------------------------------------
    /**
     * start my questions activity
     */
    public void myQues(View view) {
                Intent myQuestions = new Intent(this, LayoutChangesActivity.class);
                startActivity(myQuestions);
    }


    // ----------------------------------------------------------
    /**
     * start my favorites activity
     */
    public void myFav(View view) {
                Intent myFavorites = new Intent(this, LayoutChangesActivityFavorites.class);
                startActivity(myFavorites);
    }


    /**
     * start my favorites activity
     */
    public void tutorial(View view) {
                Intent tutorial = new Intent(this, TutorialActivity.class);
                startActivity(tutorial);
    }

    }
