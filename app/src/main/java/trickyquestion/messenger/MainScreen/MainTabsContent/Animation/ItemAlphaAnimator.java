package trickyquestion.messenger.MainScreen.MainTabsContent.Animation;

import android.view.View;
import android.view.animation.AlphaAnimation;

public class ItemAlphaAnimator {

    public static void setFadeAnimation(final View view) {
        AlphaAnimation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(50);
        view.startAnimation(anim);
    }
}
