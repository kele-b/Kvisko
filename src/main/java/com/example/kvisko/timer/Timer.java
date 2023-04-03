package com.example.kvisko.timer;

import com.example.kvisko.controllers.HomeController;
import javafx.application.Platform;
import javafx.scene.control.Label;
import org.apache.commons.lang3.time.StopWatch;

public class Timer extends Thread {

    private static final int SECONDS = 60;

    Label timeLabel;

    StopWatch stopWatch = new StopWatch();

    boolean timeIsUp = false;

    boolean timeEnd = false;

    public Timer(Label label) {
        timeLabel = label;
    }

   private HomeController homeController;

    public HomeController getHomeController() {
        return homeController;
    }

    public void setHomeController(HomeController homeController) {
        this.homeController = homeController;
    }

    @Override
    public void run() {
        stopWatch.start();
        while (!timeIsUp && !timeEnd) {
            long time = SECONDS - stopWatch.getTime() / 1000;

            Platform.runLater(() -> {
                timeLabel.setText(String.valueOf(time));
                if (time == 0) {
                    timeLabel.setText(String.valueOf("isteklo!"));
                    timeIsUp = true;
                    homeController.endOfQuiz(true);
                }
            });
            try {
                sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void restart() {
        stopWatch.reset();
        stopWatch.start();
    }

    public void setTimeIsUp(boolean timeIsUp) {
        this.timeIsUp = timeIsUp;
    }

    public boolean isTimeIsUp() {
        return timeIsUp;
    }

    public boolean isTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(boolean timeEnd) {
        this.timeEnd = timeEnd;
    }
}
