package trickyquestion.messenger.util.timer;

import android.os.CountDownTimer;

public class SimpleCountDownTimer extends CountDownTimer {

    private final long millisInFuture;
    private final long countDownInterval;
    private CountDownTimerAction action;

    public SimpleCountDownTimer(final long millisInFuture, final long countDownInterval) {
        super(millisInFuture, countDownInterval);
        this.millisInFuture = millisInFuture;
        this.countDownInterval = countDownInterval;
    }

    public void startTimer() {
        action.onStart();
        super.start();
    }

    public void cancelTimer() {
        action.onCancel();
        super.cancel();
    }

    @Override
    public void onTick(long millisUntilFinished) {
        int progressValue = (int) (( millisInFuture - millisUntilFinished ) * 100 / millisInFuture);
        int secondsUntilFinished = (int) (millisUntilFinished / 1_000) + 1;
        action.onProgress(progressValue, secondsUntilFinished);
    }

    @Override
    public void onFinish() {
        action.onFinish();
    }

    public void setAction(CountDownTimerAction action) {
        this.action = action;
    }

    public interface CountDownTimerAction {
        void onStart();
        void onCancel();
        void onProgress(long progressValue, long secondsUntilFinished);
        void onFinish();
    }
}
