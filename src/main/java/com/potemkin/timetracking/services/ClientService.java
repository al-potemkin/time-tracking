package com.potemkin.timetracking.services;

import com.potemkin.timetracking.entities.ActivityStatus;
import com.potemkin.timetracking.entities.Tracking;
import com.potemkin.timetracking.entities.User;
import com.potemkin.timetracking.timer.Time;

import java.util.List;

public class ClientService {
    private volatile static ClientService instance;

    private ClientService() {

    }

    /**
     * Singleton realization with "Double Checked Locking & Volatile" principle for high performance and thread safety.
     *
     * @return - an instance of the class.
     */
    public static ClientService getInstance() {
        if (instance == null) {
            synchronized (ClientService.class) {
                if (instance == null) {
                    return instance = new ClientService();
                }
            }
        }
        return instance;
    }

    /**
     * Method for setting parameters to Time Tracking.
     *
     * @param tracking - the tracking entity which will be updated.
     * @return - an instance of the class.
     */
    public Tracking setUpTime(Tracking tracking) {
        Time.getInstance().setStartTime(tracking.getTimeStart());
        Time.getInstance().setDifference(tracking.getDifferenceTime());
        Time.getInstance().stop();
        tracking.setElapsedTime(Time.getInstance().getElapsedTime());
        tracking.setTimeStop(Time.getInstance().getStopTime());
        tracking.setDifferenceTime(Time.getInstance().getDifference());
        return tracking;
    }

    /**
     * Method for setting difference Time Tracking.
     *
     * @param tracking - the tracking entity which will be updated.
     * @return - an instance of the class.
     */
    public Tracking setUpDifferenceTime(Tracking tracking) {
        Time.getInstance().setStartTime(tracking.getTimeStart());
        Time.getInstance().setDifference(tracking.getDifferenceTime());
        Time.getInstance().stop();
        tracking.setTimeStop(Time.getInstance().getStopTime());
        tracking.setTimeStart(tracking.getTimeStart()+(Time.getInstance().getDifference()-tracking.getDifferenceTime()));
        tracking.setDifferenceTime(Time.getInstance().getDifference());

        return tracking;
    }

    /**
     * The method for checking duplication status of tracking user.
     *
     * @param trackingUser - the user that we tracking.
     * @param trackingList - the tracking list which we have to check for duplication status.
     * @return - boolean  check value.
     */
    public boolean ifUserHasNoOpenActivity(User trackingUser, List<Tracking> trackingList) {
        boolean flag = true;
        for (Tracking tracking : trackingList) {
            if (trackingUser.equals(tracking.getUser())) {
                if (tracking.getStatus() == ActivityStatus.IN_PROGRESS) {
                    flag = false;
                }
            }
        }
        return flag;
    }

    /**
     * This method return the tracking with status "in progress".
     *
     * @param trackingList - the tracking list which we have to check for status "in progress".
     * @return - tracking object.
     */
    public Tracking getActiveTracking(List<Tracking> trackingList) {
        Tracking activeTracking = null;
        for (Tracking tracking : trackingList) {
            if (tracking.getStatus() == ActivityStatus.IN_PROGRESS) {
                activeTracking = tracking;
            }
        }
        return activeTracking;
    }
}
