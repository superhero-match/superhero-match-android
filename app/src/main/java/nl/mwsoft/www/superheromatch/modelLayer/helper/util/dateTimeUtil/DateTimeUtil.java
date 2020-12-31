/*
  Copyright (C) 2019 - 2020 MWSOFT
  This program is free software: you can redistribute it and/or modify
  it under the terms of the GNU General Public License as published by
  the Free Software Foundation, either version 3 of the License, or
  (at your option) any later version.
  This program is distributed in the hope that it will be useful,
  but WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  GNU General Public License for more details.
  You should have received a copy of the GNU General Public License
  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package nl.mwsoft.www.superheromatch.modelLayer.helper.util.dateTimeUtil;

import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DateTimeUtil {

    public DateTimeUtil() {
    }

    public String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

    public String convertFromUtcToLocal(String utc) {
        try {
            DateFormat utcFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            utcFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date date = utcFormat.parse(utc);
            utcFormat.setTimeZone(TimeZone.getDefault());
            return utcFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getUtcTime() {
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        f.setTimeZone(TimeZone.getTimeZone("UTC"));
        return f.format(new Date());
    }

    public String getMessageCreated(String fullDate) {
        String date = "";
        String[] parts;
        String part1;
        String part2 = "";
        if (fullDate != null && !fullDate.equals("")) {
            Log.d("tShoot", "fullDate --> " + fullDate);
            parts = fullDate.split(" ");
            if (parts.length == 1) {
                parts = fullDate.split("T");
            }
            part1 = parts[0]; // 2017-05-27
            part2 = parts[1]; // 12:05:41
            String[] newParts = part2.split(":");
            part2 = newParts[0].concat(":").concat(newParts[1]);
            String[] dateParts = part1.split("-");
            String[] currDate = getDateTime().split(" ");
            String[] currDateParts = currDate[0].split("-");
            if (Integer.parseInt(currDateParts[2]) != Integer.parseInt(dateParts[2])) {
                date = part1;
            } else {
                date = part2;
            }
        }

        return date;
    }

    // 1 minute = 60 seconds
    // 1 hour = 60 x 60 = 3600
    // 1 day = 3600 x 24 = 86400
    public boolean isOlderThanOneDay(Date startDate, Date endDate) {
        //milliseconds
        long difference = endDate.getTime() - startDate.getTime();

        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;

        long elapsedDays = difference / daysInMilli;

        return elapsedDays >= 1L;
    }
}
