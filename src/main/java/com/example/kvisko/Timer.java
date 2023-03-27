package com.example.kvisko;

import javafx.application.Platform;
import javafx.scene.control.Label;
import org.apache.commons.lang3.time.StopWatch;

public class Timer extends Thread {

    private static final int SECONDS = 60;

    public static boolean isStarted = false;

    Label timeLabel;

    public Timer(Label label) {
        timeLabel = label;
    }

    StopWatch stopWatch = new StopWatch();

    boolean timeIsUp = false;

    @Override
    public void run() {
        stopWatch.start();
        while (!timeIsUp) {
            long time = SECONDS - stopWatch.getTime() / 1000;

            Platform.runLater(() -> {
                timeLabel.setText(String.valueOf(time));
                if (time == 0) {
                    timeLabel.setText(String.valueOf("Vrijeme isteklo!"));
                    timeIsUp = true;
                }
            });
            try {
                sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void restart() {
        stopWatch.reset();
        stopWatch.start();
    }
}
