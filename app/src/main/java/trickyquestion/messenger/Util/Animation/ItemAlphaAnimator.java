package trickyquestion.messenger.Util.Animation;

import android.view.animation.AlphaAnimation;
import android.view.View;


public class ItemAlphaAnimator {

    public static void setFadeAnimation(final View view,final int duration) {
        AlphaAnimation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(duration);
        view.startAnimation(anim);
    }
}