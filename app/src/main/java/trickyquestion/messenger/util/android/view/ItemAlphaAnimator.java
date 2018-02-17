package trickyquestion.messenger.util.android.view;

import android.view.animation.AlphaAnimation;
import android.view.View;


public class ItemAlphaAnimator {

    public static void setFadeAnimation(final View view, final int duration) {
        final AlphaAnimation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(duration);
        view.startAnimation(anim);
    }
}