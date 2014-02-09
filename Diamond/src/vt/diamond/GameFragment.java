package vt.diamond;

import android.graphics.Typeface;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.ScrollView;
import android.widget.TextView;
import android.support.v4.app.Fragment;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.view.View;
import android.annotation.SuppressLint;
import android.os.Bundle;

@SuppressLint("NewApi")
public class GameFragment
    extends Fragment
{
    private static String question;
    //private TextView questions;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        String ques = getArguments().getString("question");
        //get correct view
        if (ques.equals("Drink")) {
            View view = inflater.inflate(R.layout.fragment_diamond, container, false);
            return view;
        }
        else {
            View view = null;

            if (ques.contains("Hot Seat")) {
                view = inflater.inflate(R.layout.fragment_hotseat, container, false);
            }
            else {
                view = inflater.inflate(R.layout.fragment_game, container, false);
            }

            Typeface tf = Typeface.createFromAsset(getActivity().getAssets(),
                "fonts/BebasNeue.otf");

            //set the text to the next question
            TextView questions = (TextView) view.findViewById(R.id.textView1);
            questions.setMaxWidth(1100);
            questions.setText(getArguments().getString("question"));
            questions.setTypeface(tf);

            return view;
        }

    }

//    public void setQuestion(String str) {
//        question = str;
//        questions.setText((CharSequence)question);
//    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    // ----------------------------------------------------------
    /**
     * Place a description of your method here.
     * @param question1
     * @return
     */
    public static GameFragment newInstance(String question1) {
        Bundle bundle = new Bundle();
        bundle.putString("question", question1);

        GameFragment pageFragment = new GameFragment();
        pageFragment.setArguments(bundle);
        return pageFragment;
    }
}
