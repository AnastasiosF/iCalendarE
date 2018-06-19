package com.example.tasos.icalendare;
/**
 * Einai upeu8unh gia na topot8etisi kai ru8mish thn ActionBar se ka8e kainourgia activity
 */

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.tasos.icalendare.NewEvent.AddEventActivity;
import com.example.tasos.icalendare.Settings.SettingsActivity;
import com.example.tasos.icalendare.ViewEvent.ViewEventActivity;
import com.nightonke.boommenu.Animation.BoomEnum;
import com.nightonke.boommenu.BoomButtons.ButtonPlaceEnum;
import com.nightonke.boommenu.BoomButtons.HamButton;
import com.nightonke.boommenu.BoomButtons.OnBMClickListener;
import com.nightonke.boommenu.BoomButtons.TextOutsideCircleButton;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.ButtonEnum;
import com.nightonke.boommenu.Piece.PiecePlaceEnum;

import java.util.Random;

public class ActionBarInit {

    ActionBar mActionBar;
    Context context;
    int DELAY_GIA_NA_KSEKINHSH_ACTIVITY = 200;
    int XRONOS_EKTELESIS_EFE = 100;

    public ActionBarInit(ActionBar mActionBar, Context context) {
        this.mActionBar = mActionBar;
        this.context = context;
    }

    public void initActionBar() {

        //ActionBar mActionBar = getSupportActionBar();

        assert mActionBar != null;
        mActionBar.setDisplayShowHomeEnabled(false);
        mActionBar.setDisplayShowTitleEnabled(false);
        LayoutInflater mInflater = LayoutInflater.from(context);

        View actionBar = mInflater.inflate(R.layout.custom_action_bar, null);
        TextView mTitleTextView = (TextView) actionBar.findViewById(R.id.title_text);
        mTitleTextView.setText(R.string.app_name);
        mActionBar.setCustomView(actionBar);
        mActionBar.setDisplayShowCustomEnabled(true);
        ((Toolbar) actionBar.getParent()).setContentInsetsAbsolute(0, 0);

        BoomMenuButton leftBmb = (BoomMenuButton) actionBar.findViewById(R.id.action_bar_left_bmb);
        BoomMenuButton rightBmb = (BoomMenuButton) actionBar.findViewById(R.id.action_bar_right_bmb);


        leftBmb.setButtonEnum(ButtonEnum.TextOutsideCircle);
        leftBmb.setPiecePlaceEnum(PiecePlaceEnum.DOT_9_1);
        leftBmb.setButtonPlaceEnum(ButtonPlaceEnum.SC_9_1);
        leftBmb.setBoomEnum(BoomEnum.RANDOM);
        leftBmb.setDuration(XRONOS_EKTELESIS_EFE);
        for (int i = 0; i < leftBmb.getPiecePlaceEnum().pieceNumber(); i++)
            leftBmb.addBuilder(new TextOutsideCircleButton.Builder().normalImageRes(R.drawable.ic_attachment_black_24dp));


        rightBmb.setButtonEnum(ButtonEnum.Ham);
        rightBmb.setPiecePlaceEnum(PiecePlaceEnum.HAM_4);
        rightBmb.setButtonPlaceEnum(ButtonPlaceEnum.HAM_4);

        rightBmb.setBoomEnum(BoomEnum.PARABOLA_2);
        rightBmb.setDuration(XRONOS_EKTELESIS_EFE);
        /*
        for (int i = 0; i < rightBmb.getPiecePlaceEnum().pieceNumber(); i++) {
            rightBmb.addBuilder(new HamButton.Builder().normalImageRes(R
                    .drawable.ic_attachment_black_24dp)
                    .shadowEffect(true)
                    .subHighlightedText("SubHighlightedText")
                    .subNormalText("SubNormalText")
                    .normalText("NormalText")
                    .listener(new OnBMClickListener() {
                        @Override
                        public void onBoomButtonClick(int index) {
                            Toast.makeText(context, "Action enabled", Toast.LENGTH_SHORT).show();
                        }
                    }));
        }
        */
        Random rand = new Random();
        HamButton.Builder mainActivity = new HamButton.Builder()
                .shadowEffect(true)
                .normalText(context.getResources().getString(R.string.main_activity))
                .normalImageRes(R.drawable.ic_home_black_24dp)
                .normalColor(Color.rgb(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255)))
                .listener(new OnBMClickListener() {
                    @Override
                    public void onBoomButtonClick(int index) {
                        final Intent i = new Intent(context, MainActivity.class);
                        //Delay gia na paizei swsta to efe
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            public void run() {
                                // yourMethod();
                                context.startActivity(i);

                            }
                        }, DELAY_GIA_NA_KSEKINHSH_ACTIVITY);

                    }
                });
        rightBmb.addBuilder(mainActivity);

        HamButton.Builder eventActivity = new HamButton.Builder();
        eventActivity.shadowEffect(true);
        eventActivity.normalText("Add new event");
        eventActivity.normalImageRes(R.drawable.ic_event_black_24dp);
        eventActivity.normalColor(Color.rgb(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255)));
        eventActivity.listener(new OnBMClickListener() {
            @Override
            public void onBoomButtonClick(int index) {
                final Intent i = new Intent(context, AddEventActivity.class);

                //Delay gia na paizei swsta to efe

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        // yourMethod();
                        context.startActivity(i);
                    }
                }, DELAY_GIA_NA_KSEKINHSH_ACTIVITY);

            }
        });
        rightBmb.addBuilder(eventActivity);

        HamButton.Builder viewEventActivity = new HamButton.Builder();
        viewEventActivity.shadowEffect(true);
        viewEventActivity.normalText("View event");
        viewEventActivity.normalImageRes(R.drawable.ic_event_black_24dp);
        viewEventActivity.normalColor(Color.rgb(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255)));
        viewEventActivity.listener(new OnBMClickListener() {
            @Override
            public void onBoomButtonClick(int index) {
                final Intent i = new Intent(context, ViewEventActivity.class);

                //Delay gia na paizei swsta to efe

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        // yourMethod();
                        context.startActivity(i);
                    }
                }, DELAY_GIA_NA_KSEKINHSH_ACTIVITY);

            }
        });
        rightBmb.addBuilder(viewEventActivity);

        HamButton.Builder settingsActivity = new HamButton.Builder();
        settingsActivity.shadowEffect(true);
        settingsActivity.normalText(context.getResources().getString(R.string.settings_activity));
        settingsActivity.normalImageRes(R.drawable.ic_settings_black_24dp);
        settingsActivity.normalColor(Color.rgb(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255)));
        settingsActivity.listener(new OnBMClickListener() {
            @Override
            public void onBoomButtonClick(int index) {
                final Intent i = new Intent(context, SettingsActivity.class);


                //Delay gia na paizei swsta to efe

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        // yourMethod();
                        context.startActivity(i);

                    }
                }, DELAY_GIA_NA_KSEKINHSH_ACTIVITY);

            }
        });
        rightBmb.addBuilder(settingsActivity);




/*
        for (int i = 0; i < bmb.getButtonPlaceEnum().buttonNumber(); i++) {
            bmb.addBuilder(new HamButton.Builder()
                    .normalImageRes(R.drawable.ic_attachment_black_24dp));
        }

*/


    }
}
