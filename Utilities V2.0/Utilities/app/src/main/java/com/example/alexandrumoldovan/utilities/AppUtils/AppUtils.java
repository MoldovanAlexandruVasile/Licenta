package com.example.alexandrumoldovan.utilities.AppUtils;

import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.alexandrumoldovan.utilities.Common.ActivityLogIn;
import com.example.alexandrumoldovan.utilities.Models.Event;
import com.example.alexandrumoldovan.utilities.Models.Report;
import com.example.alexandrumoldovan.utilities.Models.User;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class AppUtils {

    public static Event getEventByID(Integer eventID) {
        for (Event localEvent : ActivityLogIn.events)
            if (localEvent.getID().equals(eventID))
                return localEvent;
        return null;
    }

    public static User getUserByID(Integer userID) {
        for (User localUser : ActivityLogIn.users)
            if (localUser.getID().equals(userID))
                return localUser;
        return null;
    }

    public static Report getLastReport(List<Report> reports, String utility) {
        String month = getCurrentMonth(false);
        Log.e("REPORT", month);
        if (month.equals("01")) {
            month = "12";
        } else month = String.valueOf(Integer.valueOf(month) - 1);
        Log.e("REPORT", month);
        month = getMonthString(month);
        Log.e("REPORT", month);
        for (Integer i = reports.size() - 1; i >= 0; i--) {
            Report localReport = reports.get(i);
            Log.e("REPORT", localReport.toString());
            Log.e("REPORT", month);
            Log.e("REPORT", String.valueOf(localReport.getMonth().startsWith(month)));
            if (localReport.getMonth().startsWith(month) &&
                    localReport.getUtility().equals(utility) &&
                    localReport.getUser().equals(ActivityLogIn.user.getID())) {
                Log.e("REPORT FOUND", localReport.toString());
                return localReport;
            }
        }
        return null;
    }

    public static Boolean wasInPast(String localDate) {
        String currentDate = getDate();
        String[] splitedLocal = localDate.split("-");
        String[] splitedCurrent = currentDate.split("-");
        Integer yearC = Integer.valueOf(splitedCurrent[0]);
        Integer yearL = Integer.valueOf(splitedLocal[0]);
        Integer monthC = Integer.valueOf(splitedCurrent[1]);
        Integer monthL = Integer.valueOf(splitedLocal[1]);
        Integer dayC = Integer.valueOf(splitedCurrent[2]);
        Integer dayL = Integer.valueOf(splitedLocal[2]);
        if (yearC > yearL)
            return true;
        else if (monthC > monthL && yearC >= yearL)
            return true;
        else if (dayC > dayL && monthC >= monthL && yearC >= yearL)
            return true;
        return false;
    }

    public static void setSpinnerCurrentDate(ArrayAdapter<String> adapter, Spinner spinner) {
        Integer spinnerPosition = adapter.getPosition(getCurrentMonth(true));
        spinner.setSelection(spinnerPosition);
    }

    public static String getDate() {
        Date now = new Date();
        return new SimpleDateFormat("yyyy-MM-dd").format(now);
    }

    public static String getCurrentMonth(Boolean toString) {
        Date now = new Date();
        String nowAsString = new SimpleDateFormat("yyyy-MM-dd").format(now);
        if (toString)
            return getMonthString(nowAsString.substring(5, 7));
        return nowAsString.substring(5, 7);
    }

    public static String getCurrentYear() {
        Date now = new Date();
        String nowAsString = new SimpleDateFormat("yyyy-MM-dd").format(now);
        return nowAsString.substring(0, 4);
    }

    public static String getCurrentDay() {
        Date now = new Date();
        String nowAsString = new SimpleDateFormat("yyyy-MM-dd").format(now);
        return nowAsString.substring(8);
    }

    private static String getMonthString(String month) {
        switch (month) {
            case "1": {
                return "January";
            }
            case "2": {
                return "February";
            }
            case "3": {
                return "March";
            }
            case "4": {
                return "April";
            }
            case "5": {
                return "May";
            }
            case "6": {
                return "June";
            }
            case "7": {
                return "July";
            }
            case "8": {
                return "August";
            }
            case "9": {
                return "September";
            }
            case "10": {
                return "October";
            }
            case "11": {
                return "November";
            }
            case "12": {
                return "December";
            }
        }
        return null;
    }

    public static String getMonthNumber(String month) {
        switch (month) {
            case "January": {
                return "01";
            }
            case "February": {
                return "02";
            }
            case "March": {
                return "03";
            }
            case "April": {
                return "04";
            }
            case "May": {
                return "05";
            }
            case "June": {
                return "06";
            }
            case "July": {
                return "07";
            }
            case "August": {
                return "08";
            }
            case "September": {
                return "09";
            }
            case "October": {
                return "10";
            }
            case "November": {
                return "11";
            }
            case "December": {
                return "12";
            }
        }
        return null;
    }
}
