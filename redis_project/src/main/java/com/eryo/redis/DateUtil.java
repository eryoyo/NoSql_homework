package com.eryo.redis;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 将某个时间点所在的小时划分出来，便于计数到某个小时
 */
public class DateUtil {

    public static String[] StringFormat(String timeStamp) {
        //匹配任何空白字符，包括空格、制表符、换页符,即日期之间用空白字符分隔
        String[] res = timeStamp.split("\\s+");
        //确认某个时间所在的小时，便于后面在计数的时候记到相应的日期下
        SimpleDateFormat strToDate = new SimpleDateFormat("yyyyMMddHHmm");
        SimpleDateFormat dateToStr = new SimpleDateFormat("yyyyMMddHH00");
        for(int i = 0; i < res.length; i++)
        {
            try {
                Date date = strToDate.parse(res[i]);
                res[i] = dateToStr.format(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return res;
    }
}
