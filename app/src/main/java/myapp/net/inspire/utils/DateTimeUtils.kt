package myapp.net.inspire.utils

import android.net.ParseException
import android.text.TextUtils
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern
import kotlin.collections.ArrayList




/**
 * Created by Alucard on 2/9/2019.
 */
class DateTimeUtils {

    companion object {

        fun getCurrentDay(): String {
            var calendar = Calendar.getInstance()
            var dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
            when (dayOfWeek) {
                Calendar.MONDAY -> return "Mo"
                Calendar.TUESDAY -> return "Tu"
                Calendar.WEDNESDAY -> return "We"
                Calendar.THURSDAY -> return "Th"
                Calendar.FRIDAY -> return "Fr"
                Calendar.SATURDAY -> return "Sa"
                Calendar.SUNDAY -> return "Su"
                else -> {
                    return ""
                }


            }
        }

        fun getFutureNotification(): String {
            var calendar = Calendar.getInstance()
            var dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
            when (dayOfWeek) {
                Calendar.MONDAY -> return "MONDAY"
                Calendar.TUESDAY -> return "TUESDAY"
                Calendar.WEDNESDAY -> return "WEDNESDAY"
                Calendar.THURSDAY -> return "THURSDAY"
                Calendar.FRIDAY -> return "FRIDAY"
                Calendar.SATURDAY -> return "SATURDAY"
                Calendar.SUNDAY -> return "SUNDAY"
                else -> {
                    return ""
                }


            }
        }

        fun getTomorrowDay(): Int {
            var calendar = Calendar.getInstance()
            when (calendar.get(Calendar.DAY_OF_WEEK)) {
                Calendar.MONDAY -> return 2
                Calendar.TUESDAY -> return 3
                Calendar.WEDNESDAY -> return 4
                Calendar.THURSDAY -> return 5
                Calendar.FRIDAY -> return 6
                Calendar.SATURDAY -> return 7
                Calendar.SUNDAY -> return 1
                else -> {
                    return 0
                }


            }
        }


        fun getYesterDay(): String {
            var calendar = Calendar.getInstance()
            calendar.set(Calendar.DATE, -1)
            var dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
            when (dayOfWeek) {
                Calendar.MONDAY -> return "Mo"
                Calendar.TUESDAY -> return "Tu"
                Calendar.WEDNESDAY -> return "We"
                Calendar.THURSDAY -> return "Th"
                Calendar.FRIDAY -> return "Fr"
                Calendar.SATURDAY -> return "Sa"
                Calendar.SUNDAY -> return "Su"
                else -> {
                    return ""
                }


            }
        }


        fun getFullDateTiem(time: String): Date {
            try {
                var newTime: String? = null
                var tokens = time.split(" ")
//                if (tokens[1].equals("AM", true)) {
//                    newTime = tokens[0] + " a.m."
//                } else {
//                    newTime = tokens[0] + " p.m."
//                }
                val sdf = SimpleDateFormat("h:mm aa")
                var fullDateTime = time.toLowerCase()
                val d = sdf.parse(fullDateTime)
                println("Time:: " + d)
                return d!!
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
            return null!!
        }

        fun getCurrentDayInt(currentDay: String): Int {
            when (currentDay) {
                "Su" -> return 1
                "Mo" -> return 2
                "Tu" -> return 3
                "We" -> return 4
                "Th" -> return 5
                "Fr" -> return 6
                "Sa" -> return 7
                else -> {
                    return 0
                }

            }
        }


        fun getAMPM(hour: Int): String {
            return if (hour > 11) "PM" else "AM"
        }

        fun get24HrsTo12Hrs(time: String): String? {
            try {
                val sdf = SimpleDateFormat("HH:mm")
                val dateObj = sdf.parse(time)
                System.out.println(dateObj)
                val convertTime = SimpleDateFormat("K:mm").format(dateObj)

                return convertTime
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return null
        }

        fun convert24hrsTo12HrsNap(time: String): String? {
            try {
                val sdf = SimpleDateFormat("HH:mm")
                val dateObj = sdf.parse(time)
                System.out.println(dateObj)
                val convertTime = SimpleDateFormat("h:mm aa").format(dateObj)
//                var tokens=convertTime.split(" ")
//                var newTime:String?=null
//                if(tokens[1].equals("A.M.",true)){
//                   newTime=tokens[0]+" AM"
//                }else{
//                    newTime=tokens[0]+" PM"
//                }

                return convertTime!!.toUpperCase()
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return null
        }

        fun convert12HrsTo24Hrs(time: String): String {
            try {
                if (time.equals("00") || TextUtils.isEmpty(time!!)) {
                    return "00"
                } else {
                    var hrs = time.split(":")[0]
                    var newHrs: String? = null
//                    if (hrs.toInt() < 10) {
//                        newHrs = "0" + hrs + ":" + time.split(":")[1]
//                    } else {
//                        newHrs = time
//                    }
                    val inFormat = SimpleDateFormat("h:mm aa")
                    val outFormat = SimpleDateFormat("HH:mm")
                    val time24 = outFormat.format(inFormat.parse(time))
                    return time24
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return ""
        }


        fun calculateTimeBack(time1: String, time2: String): String {
            try {
                var calendar = Calendar.getInstance()
                var hours1 = time1.split(":")[0]
                var tokens1 = time1.split(":")[1].split(" ")
                var minutes1 = tokens1[0]
                var tokens = time2.split(":")

                var hours2 = tokens[0]
//                var minutes2 =(tokens[1].toInt() + hours2.toInt() * 60)

                calendar.set(Calendar.HOUR, (hours1.toInt() - hours2.toInt()))
                calendar.set(Calendar.MINUTE, (minutes1.toInt() - tokens[1].toInt()))
                if (tokens1[1].toString().equals("AM", true)) {
                    calendar.set(Calendar.AM_PM, Calendar.AM)

                } else {
                    calendar.set(Calendar.AM_PM, Calendar.PM)

                }
//                calendar.set(Calendar.MINUTE, -minutes2)
                val sdf = SimpleDateFormat("HH:mm")
                val currentDateandTime = sdf.format(calendar.time)
                var am_pm = getAMPM(currentDateandTime.split(":")[0].toInt())
//            var am_pm=calendar.get(Calendar.AM_PM)
                var timback = convert24hrsTo12HrsNap(currentDateandTime)
//                if (timback!!.split(":")[0].toInt() == 0) {
//                    var newTime: String? = null
//                    if ((timback!!.split(":")[0].toInt()) < 10) {
//                        newTime = "0" + (timback!!.split(":")[0])
//
//                    } else {
//                        newTime = "12:" + timback!!.split(":")[1]
//
//                    }
//                    var am_pm = getAMPM(newTime.split(":")[0].toInt())
//                    return newTime + " " + am_pm
//                }
//                var result = timback + " " + am_pm
//                println("hrs:: " + result)

                return timback!!
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return null!!
        }

        fun calculateTimeBackProgress(time1: Date, time2: Date): String {
            val mills = time1.getTime() - time2.getTime()
            val hours = Math.abs(mills / (1000 * 60 * 60))
            val mins = Math.abs(mills / (1000 * 60) % 60).toInt()

            val diff = hours.toString() + ":" + mins
            return diff!!
        }


        fun calculateEatByDinner(dose1: String): String {
            try {
                var calendar = Calendar.getInstance()
                var hours1 = dose1.split(":")[0]
                var tokens = dose1.split(":")[1].split(" ")
                var minutes1 = tokens[0]

//                var minutes2 = Math.abs( 2* 60) -(minutes1.toInt()+(hours1.toInt()*60))
                calendar.set(Calendar.HOUR, (hours1.toInt() - 2))
//            calendar.set(Calendar.MINUTE, minutes1.toInt())

                calendar.set(Calendar.MINUTE, minutes1.toInt())
                if (tokens[1].equals("AM", true)) {
                    calendar.set(Calendar.AM_PM, Calendar.AM)
                } else {
                    calendar.set(Calendar.AM_PM, Calendar.PM)
                }
                val sdf = SimpleDateFormat("HH:mm")
                val currentDateandTime = sdf.format(calendar.time)

                var am_pm = getAMPM(currentDateandTime.split(":")[0].toInt())
                var timback = convert24hrsTo12HrsNap(currentDateandTime)
//                if (timback!!.split(":")[0].toInt() == 0) {
//                    var newTime = "12:" + timback!!.split(":")[1]
//                    var am_pm = getAMPM(newTime.split(":")[0].toInt())
//                    return newTime + " " + am_pm
//                }
//                var result = timback + " " + "PM"
//                println("hrs:: " + result)

                return timback!!

            } catch (e: Exception) {
                e.printStackTrace()
            }
            return null!!
        }

        fun is24Hrs(time: String): Boolean {
            val pattern: Pattern
            val matcher: Matcher

            val TIME24HOURS_PATTERN = "([01]?[0-9]|2[0-3]):[0-5][0-9]"
            pattern = Pattern.compile(TIME24HOURS_PATTERN)
            matcher = pattern.matcher(time)
            return matcher.matches()
        }

        fun getTodayTomorrowDate(): List<String> {
            var dates = ArrayList<String>()
            var calendar = Calendar.getInstance()

            var todayDate = calendar.time

            calendar.add(Calendar.DAY_OF_YEAR, 1)

            var tomorrowDate = calendar.time

            val dateFormat = SimpleDateFormat("M-d-yyyy")

            val todayAsString = dateFormat.format(todayDate)
            val tomorrowAsString = dateFormat.format(tomorrowDate)
            var tokens = todayAsString.split("-")
            var tMonth = tokens[0]
            var tDate = tokens[1]

            var tomorrowTokens = tomorrowAsString.split("-")
            var tomMonth = tomorrowTokens[0]
            var tomDate = tomorrowTokens[1]
            val sdf = SimpleDateFormat("EEEE")
            val today = sdf.format(todayDate)
            val tomorrow = sdf.format(tomorrowDate)

            dates.add(0, today + " " + tMonth + "/" + tDate)
            dates.add(1, tomorrow + " " + tomMonth + "/" + tomDate)
            return dates
        }


        fun getCurrentDate(): String {
            val sdf = SimpleDateFormat("MM/dd/yyyy h:mm:ss")
            val currentDateandTime = sdf.format(Date())
            return currentDateandTime!!
        }

        fun getCurrentTimeAlarm(): String {
            val sdf = SimpleDateFormat("h:mm a")
            val currentDateandTime = sdf.format(Date())
            return currentDateandTime!!
        }

        fun getCurrentTimeForNote(): String {
            val sdf = SimpleDateFormat("h:mm a")
            val currentDateandTime = sdf.format(Date())
            return currentDateandTime!!.toUpperCase()
        }

        fun getCurrentDateTimeforEdsSeverity(): String {
            val sdf = SimpleDateFormat("yyyy-MM-dd")
            val currentDateandTime = sdf.format(Date())
            return currentDateandTime!! + " 01:01"
        }


        fun getCurrentDateWithoutTimeForPlan(): String {
            val sdf = SimpleDateFormat("dd/MM/yyyy")
            val currentDateandTime = sdf.format(Date())
            return currentDateandTime!!
        }

        fun getCurrentDateTimeWithAmPm(): Date {
            val sdf = SimpleDateFormat("h:mm a")
            val currentDateandTime = sdf.format(Date())
            val sdf1 = SimpleDateFormat("h:mm a")
            val d = sdf1.parse(currentDateandTime)
            return d!!
        }

        fun getMonth(month: Int): String {
            when (month) {
                0 -> return "January"
                1 -> return "February"
                2 -> return "March"
                3 -> return "April"
                4 -> return "May"
                5 -> return "June"
                6 -> return "July"
                7 -> return "August"
                8 -> return "September"
                9 -> return "October"
                10 -> return "November"
                11 -> return "Decemver"
                else -> {
                    return ""
                }


            }
        }

        fun getCurrentDayFull(dayOfWeek: String): String {
            var format1 = SimpleDateFormat("dd/MM/yyyy")
            var date1 = format1.parse(dayOfWeek)
            var format2 = SimpleDateFormat("EEEE")
            return format2.format(date1)
        }

        fun compareTwoTime(saveTime: String): Boolean {
            var sTimeFormat = SimpleDateFormat("hh:mm a").parse(saveTime)
            var sCalendar = Calendar.getInstance()
            sCalendar.time = sTimeFormat
            val sdf = SimpleDateFormat("hh:mm a")
            val currentDateandTime = sdf.format(Date())
            var cTimeFormat = SimpleDateFormat("hh:mm a").parse(currentDateandTime)
            var cCalendar = Calendar.getInstance()
            cCalendar.time = cTimeFormat

            if (cCalendar.time.before(sCalendar.time)) {
                return true
            }
            if (cCalendar.time.after(sCalendar.time)) {
                return false
            }

            if (cCalendar.time.equals(sCalendar.time)) {
                return true
            }
            return false
        }

        fun increaseTimeByOne(time: String): String? {
            try {
                var tokens = time.split(":")
                var hrs = tokens[0].toInt()
                var tMins = tokens[1].split(" ")
                var mins = tMins[0].toInt()
                var am_pm = tMins[1]
                val calendar = Calendar.getInstance()
                calendar.set(Calendar.HOUR, hrs)
                calendar.set(Calendar.MINUTE, mins)
                if (am_pm.equals("AM", true)) {
                    calendar.set(Calendar.AM_PM, Calendar.AM)
                } else {
                    calendar.set(Calendar.AM_PM, Calendar.PM)
                }
                calendar.add(Calendar.HOUR, 1)
                var minutes = calendar.get(Calendar.MINUTE)

                val currentTime = (if (calendar.get(Calendar.HOUR) == 0) 12 else
                    if (calendar.get(Calendar.HOUR).toString().length == 1) ("0" + calendar.get(Calendar.HOUR))
                    else calendar.get(Calendar.HOUR).toString()).toString() + ":" +
                        (if (minutes < 10) ("0" + minutes) else minutes) + " " + if (calendar.get(Calendar.AM_PM) == 1) "PM" else "AM"
                var format = SimpleDateFormat("hh:mm aa")
                var formatter = SimpleDateFormat("h:mm aa")
                return formatter.format(format.parse(currentTime)).toUpperCase()
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return null
        }

        fun decreaseTimeByOne(time: String): String? {
            try {
                var tokens = time.split(":")
                var hrs = tokens[0].toInt()
                var tMins = tokens[1].split(" ")
                var mins = tMins[0].toInt()
                var am_pm = tMins[1]
                val calendar = Calendar.getInstance()
                calendar.set(Calendar.HOUR, hrs)
                calendar.set(Calendar.MINUTE, mins)
                if (am_pm.equals("AM", true)) {
                    calendar.set(Calendar.AM_PM, Calendar.AM)
                } else {
                    calendar.set(Calendar.AM_PM, Calendar.PM)
                }
                calendar.add(Calendar.HOUR, -1)
                var minutes = calendar.get(Calendar.MINUTE)

                val currentTime = (if (calendar.get(Calendar.HOUR) == 0) 12 else
                    if (calendar.get(Calendar.HOUR).toString().length == 1) ("0" + calendar.get(Calendar.HOUR))
                    else calendar.get(Calendar.HOUR).toString()).toString() + ":" +
                        (if (minutes < 10) ("0" + minutes) else minutes) + " " + if (calendar.get(Calendar.AM_PM) == 1) "PM" else "AM"
                var format = SimpleDateFormat("hh:mm aa")
                var formatter = SimpleDateFormat("h:mm aa")
                return formatter.format(format.parse(currentTime)).toUpperCase()
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return null

        }

        fun add30DaysForRefillEmpty(startDate: String): String {
            try {
                val sdf = SimpleDateFormat("yyyy-MM-dd")
                val c = Calendar.getInstance()
                try {
                    c.time = sdf.parse(startDate)
                } catch (e: ParseException) {
                    e.printStackTrace()
                }

                c.add(Calendar.DATE, 30)  // number of days to add, can also use Calendar.DAY_OF_MONTH in place of Calendar.DATE
                val sdf1 = SimpleDateFormat("EEEE MMMM dd")
                val output = sdf1.format(c.time)
                return output!!
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return null!!
        }

        fun convert30DaysEmpty(emptyDate: String): String {
            try {
                val sdf1 = SimpleDateFormat("EEEE MMMM dd")
                var format1 = SimpleDateFormat("dd/MM/yyyy")
                val result = format1.format(sdf1.parse(emptyDate))
                return result!!
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return null!!
        }

        fun convertDateFormat(date: String): String {
            try {
                val sdf1 = SimpleDateFormat("EEEE MMMM dd")
                var format1 = SimpleDateFormat("dd/MM/yyyy")
                val result = sdf1.format(format1.parse(date))
                return result!!
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return null!!
        }


        fun getDayNumberSuffix(day: Int): String {
            if (day >= 11 && day <= 13) {
                return "th"
            }
            when (day % 10) {
                1 -> return "st"
                2 -> return "nd"
                3 -> return "rd"
                else -> return "th"
            }
        }

        fun comparePassedDateWithCurrentDate(savedDate: String, currentDate: String): Int {

            var sTokens = savedDate.split("/")
            var sDay = sTokens[0]
            var sMonth = sTokens[1]
            var sYear = sTokens[2]

            var cTokens = currentDate.split("/")
            var cDay = cTokens[0]
            var cMonth = cTokens[1]
            var cYear = cTokens[2]


            val c = Calendar.getInstance()

            c.set(Calendar.YEAR, cYear.toInt())
            c.set(Calendar.MONTH, cMonth.toInt())
            c.set(Calendar.DAY_OF_MONTH, cDay.toInt())

            val today = c.time


            c.set(Calendar.YEAR, sYear.toInt())
            c.set(Calendar.MONTH, sMonth.toInt())
            c.set(Calendar.DAY_OF_MONTH, sDay.toInt())

            val savedDate = c.time
            if (savedDate.before(today)) {
                return 1
            }
            if (savedDate.equals(today)) {
                return 0
            }
            return -1

        }

        fun timeGapBetweenNaps(firstNap:String,secondNap:String):Int{
            try {
                val simpleDateFormat = SimpleDateFormat("h:mm aa")
                var date1 = simpleDateFormat.parse(firstNap)
                var date2 = simpleDateFormat.parse(secondNap)

                val difference = date2.getTime() - date1.getTime()
                var days = (difference / (1000 * 60 * 60 * 24)).toInt()
                var hours = ((difference - 1000 * 60 * 60 * 24 * days) / (1000 * 60 * 60)) as Int
                var min = (difference - 1000 * 60 * 60 * 24 * days - 1000 * 60 * 60 * hours) as Int / (1000 * 60)
                hours = if (hours < 0) -hours else hours

                if (date1.after(date2) && hours >=1) {
                    return hours
                } else {
                    return -1
                }
            }catch (e:Exception){
                e.printStackTrace()
                return -1
            }

        }

         fun calculateTimeInMills(previousNap: String,currentNap:String): Boolean {
            var parser = SimpleDateFormat("h:mm aa")
            var savedTime = parser.parse(currentNap)
            var systemTime = parser.parse(previousNap)
            if (savedTime.after(systemTime)) {
                return true
            }

            return false
        }

    }


}