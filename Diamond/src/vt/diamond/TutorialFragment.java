package vt.diamond;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.TextView;
import android.support.v4.app.Fragment;

// -------------------------------------------------------------------------
/**
 *  fragment used for tutorial view pager
 *
 *  @author Dan
 *  @version Jan 2, 2014
 */
public class TutorialFragment
    extends Fragment
{
    private static String question;
    //private TextView questions;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

            View view = inflater.inflate(R.layout.fragment_game, container, false);

            //get the number of the photo
            int photoNum = savedInstanceState.getInt("pic");

//            //set the photo
//            if (photoNum == 0) {
//                view.setBackground(background);
//            }
//            else if (photoNum == 1) {
//                view.setBackground(background);
//            }
//            else if (photoNum == 2) {
//                view.setBackground(background);
//            }
//            else if (photoNum == 3) {
//                view.setBackground(background);
//            }
//            else if (photoNum == 4) {
//                view.setBackground(background);
//            }

            return view;
     }



    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    // ----------------------------------------------------------
    /**
     * Place a description of your method here.
     * @param photo number of photo
     * @return new fragment
     */
    public static GameFragment newInstance(int photo) {
        Bundle bundle = new Bundle();
        bundle.putInt("pic", photo);

        GameFragment pageFragment = new GameFragment();
        pageFragment.setArguments(bundle);
        return pageFragment;
    }
}
