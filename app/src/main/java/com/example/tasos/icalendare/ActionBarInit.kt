package com.example.tasos.icalendare

/**
 * Einai upeu8unh gia na topot8etisi kai ru8mish thn ActionBar se ka8e kainourgia activity
 */

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Handler
import android.support.v7.app.ActionBar
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView

import com.example.tasos.icalendare.NewEvent.AddEventActivity
import com.example.tasos.icalendare.Settings.SettingsActivity
import com.example.tasos.icalendare.ViewEvent.ViewEventActivity
import com.nightonke.boommenu.Animation.BoomEnum
import com.nightonke.boommenu.BoomButtons.ButtonPlaceEnum
import com.nightonke.boommenu.BoomButtons.HamButton
import com.nightonke.boommenu.BoomButtons.OnBMClickListener
import com.nightonke.boommenu.BoomButtons.TextOutsideCircleButton
import com.nightonke.boommenu.BoomMenuButton
import com.nightonke.boommenu.ButtonEnum
import com.nightonke.boommenu.Piece.PiecePlaceEnum

import java.util.Random

class ActionBarInit(internal var mActionBar: ActionBar?, internal var context: Context) {
    internal var DELAY_GIA_NA_KSEKINHSH_ACTIVITY = 200
    internal var XRONOS_EKTELESIS_EFE = 1500

    fun initActionBar() {

        //ActionBar mActionBar = getSupportActionBar();

        assert(mActionBar != null)
        mActionBar!!.setDisplayShowHomeEnabled(false)
        mActionBar!!.setDisplayShowTitleEnabled(false)
        val mInflater = LayoutInflater.from(context)

        val actionBar = mInflater.inflate(R.layout.custom_action_bar, null)
        val mTitleTextView = actionBar.findViewById<View>(R.id.title_text) as TextView
        mTitleTextView.setText(R.string.app_name)
        mActionBar!!.customView = actionBar
        mActionBar!!.setDisplayShowCustomEnabled(true)
        (actionBar.parent as Toolbar).setContentInsetsAbsolute(0, 0)

        //BoomMenuButton leftBmb = (BoomMenuButton) actionBar.findViewById(R.id.action_bar_left_bmb);
        val rightBmb = actionBar.findViewById<View>(R.id.action_bar_right_bmb) as BoomMenuButton


        /*
        leftBmb.setButtonEnum(ButtonEnum.TextOutsideCircle);
        leftBmb.setPiecePlaceEnum(PiecePlaceEnum.DOT_9_1);
        leftBmb.setButtonPlaceEnum(ButtonPlaceEnum.SC_9_1);
        leftBmb.setBoomEnum(BoomEnum.RANDOM);
        leftBmb.setDuration(XRONOS_EKTELESIS_EFE);
        for (int i = 0; i < leftBmb.getPiecePlaceEnum().pieceNumber(); i++)
            leftBmb.addBuilder(new TextOutsideCircleButton.Builder().normalImageRes(R.drawable.ic_attachment_black_24dp));
*/

        rightBmb.buttonEnum = ButtonEnum.Ham
        rightBmb.piecePlaceEnum = PiecePlaceEnum.HAM_4
        rightBmb.buttonPlaceEnum = ButtonPlaceEnum.HAM_4

        rightBmb.boomEnum = BoomEnum.PARABOLA_2
        rightBmb.setDuration(XRONOS_EKTELESIS_EFE.toLong())
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
        val rand = Random()
        val mainActivity = HamButton.Builder()
                .shadowEffect(true)
                .normalText(context.resources.getString(R.string.main_activity))
                .normalImageRes(R.drawable.ic_home_black_24dp)
                .normalColor(Color.rgb(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255)))
                .listener {
                    val i = Intent(context, MainActivity::class.java)
                    //Delay gia na paizei swsta to efe
                    val handler = Handler()
                    handler.postDelayed({
                        // yourMethod();
                        context.startActivity(i)
                    }, DELAY_GIA_NA_KSEKINHSH_ACTIVITY.toLong())
                }
        rightBmb.addBuilder(mainActivity)

        val eventActivity = HamButton.Builder()
        eventActivity.shadowEffect(true)
        eventActivity.normalText(context.resources.getString(R.string.add_event))
        eventActivity.normalImageRes(R.drawable.ic_event_black_24dp)
        eventActivity.normalColor(Color.rgb(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255)))
        eventActivity.listener {
            val i = Intent(context, AddEventActivity::class.java)

            //Delay gia na paizei swsta to efe

            val handler = Handler()
            handler.postDelayed({
                // yourMethod();
                context.startActivity(i)
            }, DELAY_GIA_NA_KSEKINHSH_ACTIVITY.toLong())
        }
        rightBmb.addBuilder(eventActivity)

        val viewEventActivity = HamButton.Builder()
        viewEventActivity.shadowEffect(true)
        viewEventActivity.normalText(context.getString(R.string.view_event))
        viewEventActivity.normalImageRes(R.drawable.ic_event_black_24dp)
        viewEventActivity.normalColor(Color.rgb(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255)))
        viewEventActivity.listener {
            val i = Intent(context, ViewEventActivity::class.java)

            //Delay gia na paizei swsta to efe

            val handler = Handler()
            handler.postDelayed({
                // yourMethod();
                context.startActivity(i)
            }, DELAY_GIA_NA_KSEKINHSH_ACTIVITY.toLong())
        }
        rightBmb.addBuilder(viewEventActivity)

        val settingsActivity = HamButton.Builder()
        settingsActivity.shadowEffect(true)
        settingsActivity.normalText(context.resources.getString(R.string.settings_activity))
        settingsActivity.normalImageRes(R.drawable.ic_settings_black_24dp)
        settingsActivity.normalColor(Color.rgb(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255)))
        settingsActivity.listener {
            val i = Intent(context, SettingsActivity::class.java)


            //Delay gia na paizei swsta to efe

            val handler = Handler()
            handler.postDelayed({
                // yourMethod();
                context.startActivity(i)
            }, DELAY_GIA_NA_KSEKINHSH_ACTIVITY.toLong())
        }
        rightBmb.addBuilder(settingsActivity)


        /*
        for (int i = 0; i < bmb.getButtonPlaceEnum().buttonNumber(); i++) {
            bmb.addBuilder(new HamButton.Builder()
                    .normalImageRes(R.drawable.ic_attachment_black_24dp));
        }

*/


    }
}
