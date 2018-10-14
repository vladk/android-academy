package ru.kononov.vlad.exercise4;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.lang.ref.WeakReference;

public class MainActivity extends AppCompatActivity {
    private RunnableLeg stepLeft;
    private RunnableLeg stepRight;
    private TextView stepTextView;

    @Override
    public void onStart() {
        super.onStart();
        stepLeft = new LeftLeg(1, this);
        stepRight = new RightLeg(2, this);
        new Thread(stepLeft).start();
        new Thread(stepRight).start();

        stepTextView = findViewById(R.id.step_id);
    }

    void updateTextView(String text) {
        runOnUiThread(new TextUpdateRunnable(stepTextView, text));
    }

    @Override
    public void onStop() {
        super.onStop();
        stepLeft.stop();
        stepRight.stop();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}

class TextUpdateRunnable implements Runnable {

    private final WeakReference<TextView> textView;
    private final String text;

    TextUpdateRunnable(TextView textView, String text) {
        this.textView = new WeakReference(textView);
        this.text = text;
    }

    @Override
    public void run() {
        textView.get().setText(text);
    }
}

abstract class RunnableLeg implements Runnable {
    boolean isRunning = true;
    int stepId;
    static volatile int prevStep = -1;
    final Object stepLock = new Object();
    WeakReference<MainActivity> mainActivity;

    RunnableLeg(int id, MainActivity mainActivity) {
        this.mainActivity = new WeakReference(mainActivity);
        stepId = id;
    }

    protected void setStep(int stepId) {
        synchronized (stepLock) {
            prevStep = stepId;
        }
    }

    void stop() {
        isRunning = false;
    }
}

class LeftLeg extends RunnableLeg {
    LeftLeg(int id, MainActivity mainActivity) {
        super(id, mainActivity);
    }

    @Override
    public void run() {
        while (isRunning) {
            if (prevStep == stepId) continue;
            System.out.println("Left step");
            this.mainActivity.get().updateTextView("Left step");
            setStep(stepId);
        }
    }
}

class RightLeg extends RunnableLeg {
    RightLeg(int id, MainActivity mainActivity) {
        super(id, mainActivity);
    }

    @Override
    public void run() {
        while (isRunning) {
            if (prevStep == stepId) continue;
            System.out.println("Right step");
            this.mainActivity.get().updateTextView("Right step");
            setStep(stepId);
        }
    }
}
