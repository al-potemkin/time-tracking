package com.potemkin.timetracking.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Description: This class describes all user's activities.
 * <p>
 */
public class Activity extends BaseEntity implements Serializable {
    public static List<String> activityNameList = new ArrayList<>();
    private Integer activityId;
    private String activityName;

    public Activity() {
    }

    public Activity(Integer activityId, String activityName) {
        this.activityId = activityId;
        this.activityName = activityName;
    }

    public Integer getActivityId() {
        return activityId;
    }

    public void setActivityId(Integer activityId) {
        this.activityId = activityId;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Activity activity = (Activity) o;

        if (activityId != null ? !activityId.equals(activity.activityId) : activity.activityId != null) return false;
        return activityName != null ? activityName.equals(activity.activityName) : activity.activityName == null;
    }

    @Override
    public int hashCode() {
        int result = activityId != null ? activityId.hashCode() : 0;
        result = 31 * result + (activityName != null ? activityName.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Activity{" +
                "activityId=" + activityId +
                ", activityName='" + activityName + '\'' +
                '}';
    }

}

