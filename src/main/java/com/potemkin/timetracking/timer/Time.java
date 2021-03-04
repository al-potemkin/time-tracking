package com.potemkin.timetracking.timer;

import java.util.concurrent.TimeUnit;

public class Time {
    private volatile static Time instance;

    private Time() {
    }

    /**
     * Singleton realization with "Double Checked Locking & Volatile" principle for high performance and thread safety.
     *
     * @return - an instance of the class.
     */
    public static Time getInstance() {
        if (instance == null) {
            synchronized (Time.class) {
                if (instance == null) {
                    instance = new Time();
                }
            }
        }
        return instance;
    }

    /**
     * Time that elapsed from startTime in string representation for rendering on jsp page.
     */
    private String elapsedTime;
    /**
     * Time that elapsed from startTime in milliseconds.
     */
    private long difference = 0;
    /**
     * Time startTime.
     */
    private long startTime = 0;

    /**
     * Time stopTime.
     */
    private long stopTime = 0;

    /**
     * Start ticking, resets the time.
     */
    public final void start() {
        startTime = System.currentTimeMillis();
    }

    /**
     * Stop ticking.
     */
    public final void stop() {
        stopTime = System.currentTimeMillis() + difference;
        elapsedTime();
    }

    /**
     * Calculates time elapsed.
     *
     * @return the time elapsed between startTime and stopTime as String in milliseconds.
     */
    public void elapsedTime() {
        difference = (stopTime - startTime); // in ms
        elapsedTime = String.format("%02d:%02d:%02d",
                TimeUnit.MILLISECONDS.toHours(difference),
                TimeUnit.MILLISECONDS.toMinutes(difference) -
                        TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(difference)),
                TimeUnit.MILLISECONDS.toSeconds(difference) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(difference)));
    }

    public String getElapsedTime() {
        return elapsedTime;
    }

    public void setElapsedTime(String elapsedTime) {
        this.elapsedTime = elapsedTime;
    }

    public Long getDifference() {
        return difference;
    }

    public void setDifference(Long difference) {
        this.difference = difference;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getStopTime() {
        return stopTime;
    }

    public void setStopTime(Long stopTime) {
        this.stopTime = stopTime;
    }
}
