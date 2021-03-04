package com.potemkin.timetracking.dao.daofactory;

import com.potemkin.timetracking.dao.interfacesdao.ActivityDAO;
import com.potemkin.timetracking.dao.interfacesdao.TrackingDAO;
import com.potemkin.timetracking.dao.interfacesdao.UserDAO;
import com.potemkin.timetracking.dao.mysqldaoimpl.ActivityDAOImpl;
import com.potemkin.timetracking.dao.mysqldaoimpl.TrackingDAOImpl;
import com.potemkin.timetracking.dao.mysqldaoimpl.UserDAOImpl;

public class MySqlDaoFactory extends DaoFactory {

    @Override
    public ActivityDAO getActivityDao() {
        return ActivityDAOImpl.getInstance();
    }

    @Override
    public TrackingDAO getTrackingDao() {
        return TrackingDAOImpl.getInstance();
    }

    @Override
    public UserDAO getUserDao() {
        return UserDAOImpl.getInstance();
    }

}
