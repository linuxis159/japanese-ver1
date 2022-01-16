package com.study.japanese.function;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class DateSetting {

    public static String setDateFormat(Timestamp target){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String time = simpleDateFormat.format(target);
        return time;
    }



}
