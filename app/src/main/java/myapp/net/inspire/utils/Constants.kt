package myapp.net.inspire.utils

/**
 * Created by QPay on 2/12/2019.
 */
class Constants {

    companion object {
        const val SHARED_PREFS_NAME = "inspire+"
        const val PLAN_SETUP = "plan_setup"
        const val WAKEUP_TIME = "wakeup_time"
        const val DOSETWO_WAKEUP = "dose_two_wakeup"
        const val TIME_BETWEEN_DOSES = "time_between_doses"
        const val DOSE_2 = "dose_2"
        const val DOSE_1 = "dose_1"
        const val EAT_DINNER_BY = "eat_dinner_by"
        const val NAP_1 = "nap_1"
        const val NAP_1_INTERVAL = "nap_1_interval"
        const val NAP_2 = "nap_2"
        const val NAP_2_INTERVAL = "nap_2_interval"
        const val NAP_3 = "nap_3"
        const val NAP_3_INTERVAL = "nap_3_interval"
        const val IS_EATBY_REMINDER = "is_eatby_reminder"
        const val IS_DOSE1_REMINDER = "is_dose1_reminder"
        const val IS_NAP_REMINDER = "is_nap_reminder"
        const val DOSE1_ALARM_SOUND = "dose1_alarm_sound"
        const val DOSE2_ALARM_SOUND = "dose2_alarm_sound"
        const val WAKE_ALARM_SOUND = "wake_alarm_sound"
        const val NAP_START_ALARM_SOUND = "nap_start_alarm_sound"
        const val NAP_END_ALARM_SOUND = "nap_end_alarm_sound"
        const val IS_SCHEDULE_PLAN = "is_schedule_plan"
        const val IS_PLAN_SETUP = "is_plan_setup"
        const val IS_PROGRESS_SETUP = "is_progress_setup"
        const val IS_PROGRESS_SETUP_CATAPLEXY = "is_progress_setup_cataplexy"
        const val START_DATE_REFILL = "start_date_refill"
        const val END_DATE_REFILL = "end_date_refill"
        const val IS_REFILL_REMINDER = "IS_REFILL_REMINDER"
        const val IS_FIRST_TIME = "is_first_time"
        const val IS_FIRST_TIME_LEARN = "is_first_time_learn"
        const val IS_FIRST_TIME_PLAN = "is_first_time_plan"
        const val IS_FIRST_TIME_TRACK = "is_first_time_track"
        const val IS_DEFAULT_NOTE_SAVE = "is_default_note_save"
        const val IS_IN_PROGRESS_EDS = "is_in_progress_eds"
        const val NOTIFICATION_HANDLER = "notification_handler"
        const val PROGRESS_TRACK = "progress_track"
        const val DAYS_10="10_days"

        const val IS_CURRENTLY_SETUP_PLAN="currently_setup_plan"

        //database
        const val DATABASE = "inspire_plus"


        //notification id

        const val REFILL_NOTIFICATION_ID = 100
        const val REFILL_BOARDCAST_ID = 99



        //request code for Pending Intent
        //SUNDAY
        const val SUNDAY_WAKE_REQUEST_CODE = 101
        const val SUNDAY_EATBY_REQUEST_CODE = 102
        const val SUNDAY_DOSE1_REQUEST_CODE = 103
        const val SUNDAY_DOSE2_REQUEST_CODE = 104
        const val SUNDAY_NAP1_REQUEST_CODE = 105
        const val SUNDAY_NAP2_REQUEST_CODE = 106
        const val SUNDAY_NAP3_REQUEST_CODE = 107


        //MONDAY
        const val MONDAY_WAKE_REQUEST_CODE = 108
        const val MONDAY_EATBY_REQUEST_CODE = 109
        const val MONDAY_DOSE1_REQUEST_CODE = 200
        const val MONDAY_DOSE2_REQUEST_CODE = 201
        const val MONDAY_NAP1_REQUEST_CODE = 202
        const val MONDAY_NAP2_REQUEST_CODE = 203
        const val MONDAY_NAP3_REQUEST_CODE = 204

        //TUESDAY
        const val TUESDAY_WAKE_REQUEST_CODE = 205
        const val TUESDAY_EATBY_REQUEST_CODE = 206
        const val TUESDAY_DOSE1_REQUEST_CODE = 207
        const val TUESDAY_DOSE2_REQUEST_CODE = 208
        const val TUESDAY_NAP1_REQUEST_CODE = 209
        const val TUESDAY_NAP2_REQUEST_CODE = 300
        const val TUESDAY_NAP3_REQUEST_CODE = 301


        //WEDNESDAY
        const val WEDNESDAY_WAKE_REQUEST_CODE = 302
        const val WEDNESDAY_EATBY_REQUEST_CODE = 303
        const val WEDNESDAY_DOSE1_REQUEST_CODE = 304
        const val WEDNESDAY_DOSE2_REQUEST_CODE = 305
        const val WEDNESDAY_NAP1_REQUEST_CODE = 306
        const val WEDNESDAY_NAP2_REQUEST_CODE = 307
        const val WEDNESDAY_NAP3_REQUEST_CODE = 308


        //THURSDAY
        const val THURSDAY_WAKE_REQUEST_CODE = 309
        const val THURSDAY_EATBY_REQUEST_CODE = 400
        const val THURSDAY_DOSE1_REQUEST_CODE = 401
        const val THURSDAY_DOSE2_REQUEST_CODE = 402
        const val THURSDAY_NAP1_REQUEST_CODE = 403
        const val THURSDAY_NAP2_REQUEST_CODE = 404
        const val THURSDAY_NAP3_REQUEST_CODE = 405


        //FRIDAY
        const val FRIDAY_WAKE_REQUEST_CODE = 406
        const val FRIDAY_EATBY_REQUEST_CODE = 407
        const val FRIDAY_DOSE1_REQUEST_CODE = 408
        const val FRIDAY_DOSE2_REQUEST_CODE = 409
        const val FRIDAY_NAP1_REQUEST_CODE = 500
        const val FRIDAY_NAP2_REQUEST_CODE = 501
        const val FRIDAY_NAP3_REQUEST_CODE = 502


        //SATURDAY
        const val SATURDAY_WAKE_REQUEST_CODE = 503
        const val SATURDAY_EATBY_REQUEST_CODE = 504
        const val SATURDAY_DOSE1_REQUEST_CODE = 505
        const val SATURDAY_DOSE2_REQUEST_CODE = 506
        const val SATURDAY_NAP1_REQUEST_CODE = 507
        const val SATURDAY_NAP2_REQUEST_CODE = 508
        const val SATURDAY_NAP3_REQUEST_CODE = 509

        //one hour before eatby

        const val SUNDAY_EATBY_ONE_HOUR_REQUEST_CODE=510
        const val MONDAY_EATBY_ONE_HOUR_REQUEST_CODE=511
        const val TUESDAY_EATBY_ONE_HOUR_REQUEST_CODE=512
        const val WEDNESDAY_EATBY_ONE_HOUR_REQUEST_CODE=513
        const val THURSDAY_EATBY_ONE_HOUR_REQUEST_CODE=514
        const val FRIDAY_EATBY_ONE_HOUR_REQUEST_CODE=515
        const val SATURDAY_EATBY_ONE_HOUR_REQUEST_CODE=516


        //notification ID for Notification

        //SUNDAY
        const val SUNDAY_WAKE_NOTIFICATION_ID = 11
        const val SUNDAY_EATBY_NOTIFICATION_ID = 12
        const val SUNDAY_DOSE1_NOTIFICATION_ID = 13
        const val SUNDAY_DOSE2_NOTIFICATION_ID = 14
        const val SUNDAY_NAP1_NOTIFICATION_ID = 15
        const val SUNDAY_NAP2_NOTIFICATION_ID = 16
        const val SUNDAY_NAP3_NOTIFICATION_ID = 17

        //MONDAY
        const val MONDAY_WAKE_NOTIFICATION_ID = 18
        const val MONDAY_EATBY_NOTIFICATION_ID = 19
        const val MONDAY_DOSE1_NOTIFICATION_ID = 20
        const val MONDAY_DOSE2_NOTIFICATION_ID = 21
        const val MONDAY_NAP1_NOTIFICATION_ID = 22
        const val MONDAY_NAP2_NOTIFICATION_ID = 23
        const val MONDAY_NAP3_NOTIFICATION_ID = 24

        //TUESDAY
        const val TUESDAY_WAKE_NOTIFICATION_ID = 25
        const val TUESDAY_EATBY_NOTIFICATION_ID = 26
        const val TUESDAY_DOSE1_NOTIFICATION_ID = 27
        const val TUESDAY_DOSE2_NOTIFICATION_ID = 28
        const val TUESDAY_NAP1_NOTIFICATION_ID = 29
        const val TUESDAY_NAP2_NOTIFICATION_ID = 30
        const val TUESDAY_NAP3_NOTIFICATION_ID = 31


        //WEDNESDAY
        const val WEDNESDAY_WAKE_NOTIFICATION_ID = 32
        const val WEDNESDAY_EATBY_NOTIFICATION_ID = 33
        const val WEDNESDAY_DOSE1_NOTIFICATION_ID = 34
        const val WEDNESDAY_DOSE2_NOTIFICATION_ID = 35
        const val WEDNESDAY_NAP1_NOTIFICATION_ID = 36
        const val WEDNESDAY_NAP2_NOTIFICATION_ID = 37
        const val WEDNESDAY_NAP3_NOTIFICATION_ID = 38


        //THURSDAY
        const val THURSDAY_WAKE_NOTIFICATION_ID = 39
        const val THURSDAY_EATBY_NOTIFICATION_ID = 40
        const val THURSDAY_DOSE1_NOTIFICATION_ID = 41
        const val THURSDAY_DOSE2_NOTIFICATION_ID = 42
        const val THURSDAY_NAP1_NOTIFICATION_ID = 43
        const val THURSDAY_NAP2_NOTIFICATION_ID = 44
        const val THURSDAY_NAP3_NOTIFICATION_ID = 45


        //FRIDAY
        const val FRIDAY_WAKE_NOTIFICATION_ID = 46
        const val FRIDAY_EATBY_NOTIFICATION_ID = 47
        const val FRIDAY_DOSE1_NOTIFICATION_ID = 48
        const val FRIDAY_DOSE2_NOTIFICATION_ID = 49
        const val FRIDAY_NAP1_NOTIFICATION_ID = 50
        const val FRIDAY_NAP2_NOTIFICATION_ID = 51
        const val FRIDAY_NAP3_NOTIFICATION_ID = 52


        //SATURDAY
        const val SATURDAY_WAKE_NOTIFICATION_ID = 53
        const val SATURDAY_EATBY_NOTIFICATION_ID = 54
        const val SATURDAY_DOSE1_NOTIFICATION_ID = 55
        const val SATURDAY_DOSE2_NOTIFICATION_ID = 56
        const val SATURDAY_NAP1_NOTIFICATION_ID = 57
        const val SATURDAY_NAP2_NOTIFICATION_ID = 58
        const val SATURDAY_NAP3_NOTIFICATION_ID = 59

        //one hour before eatby notification id

        const val SUNDAY_EATBY_ONE_HOUR_NOTIFICATION_ID=60
        const val MONDAY_EATBY_ONE_HOUR_NOTIFICATION_ID=61
        const val TUESDAY_EATBY_ONE_HOUR_NOTIFICATION_ID=62
        const val WEDNESDAY_EATBY_ONE_HOUR_NOTIFICATION_ID=63
        const val THURSDAY_EATBY_ONE_HOUR_NOTIFICATION_ID=64
        const val FRIDAY_EATBY_ONE_HOUR_NOTIFICATION_ID=65
        const val SATURDAY_EATBY_ONE_HOUR_NOTIFICATION_ID=66


        fun getNotificationId(): HashMap<String, Int> {
            var notificationMap = HashMap<String, Int>()
            notificationMap.put("SUNDAY_WAKE_NOTIFICATION_ID", SUNDAY_WAKE_NOTIFICATION_ID)
            notificationMap.put("SUNDAY_EATBY_NOTIFICATION_ID", SUNDAY_EATBY_NOTIFICATION_ID)
            notificationMap.put("SUNDAY_DOSE1_NOTIFICATION_ID", SUNDAY_DOSE1_NOTIFICATION_ID)
            notificationMap.put("SUNDAY_DOSE2_NOTIFICATION_ID", SUNDAY_DOSE2_NOTIFICATION_ID)
            notificationMap.put("SUNDAY_NAP1_NOTIFICATION_ID", SUNDAY_NAP1_NOTIFICATION_ID)
            notificationMap.put("SUNDAY_NAP2_NOTIFICATION_ID", SUNDAY_NAP2_NOTIFICATION_ID)
            notificationMap.put("SUNDAY_NAP3_NOTIFICATION_ID", SUNDAY_NAP3_NOTIFICATION_ID)

            notificationMap.put("MONDAY_WAKE_NOTIFICATION_ID", MONDAY_WAKE_NOTIFICATION_ID)
            notificationMap.put("MONDAY_EATBY_NOTIFICATION_ID", MONDAY_EATBY_NOTIFICATION_ID)
            notificationMap.put("MONDAY_DOSE1_NOTIFICATION_ID", MONDAY_DOSE1_NOTIFICATION_ID)
            notificationMap.put("MONDAY_DOSE2_NOTIFICATION_ID", MONDAY_DOSE2_NOTIFICATION_ID)
            notificationMap.put("MONDAY_NAP1_NOTIFICATION_ID", MONDAY_NAP1_NOTIFICATION_ID)
            notificationMap.put("MONDAY_NAP2_NOTIFICATION_ID", MONDAY_NAP2_NOTIFICATION_ID)
            notificationMap.put("MONDAY_NAP3_NOTIFICATION_ID", MONDAY_NAP3_NOTIFICATION_ID)

            notificationMap.put("TUESDAY_WAKE_NOTIFICATION_ID", TUESDAY_WAKE_NOTIFICATION_ID)
            notificationMap.put("TUESDAY_EATBY_NOTIFICATION_ID", TUESDAY_EATBY_NOTIFICATION_ID)
            notificationMap.put("TUESDAY_DOSE1_NOTIFICATION_ID", TUESDAY_DOSE1_NOTIFICATION_ID)
            notificationMap.put("TUESDAY_DOSE2_NOTIFICATION_ID", TUESDAY_DOSE2_NOTIFICATION_ID)
            notificationMap.put("TUESDAY_NAP1_NOTIFICATION_ID", TUESDAY_NAP1_NOTIFICATION_ID)
            notificationMap.put("TUESDAY_NAP2_NOTIFICATION_ID", TUESDAY_NAP2_NOTIFICATION_ID)
            notificationMap.put("TUESDAY_NAP3_NOTIFICATION_ID", TUESDAY_NAP3_NOTIFICATION_ID)

            notificationMap.put("WEDNESDAY_WAKE_NOTIFICATION_ID", WEDNESDAY_WAKE_NOTIFICATION_ID)
            notificationMap.put("WEDNESDAY_EATBY_NOTIFICATION_ID", WEDNESDAY_EATBY_NOTIFICATION_ID)
            notificationMap.put("WEDNESDAY_DOSE1_NOTIFICATION_ID",WEDNESDAY_DOSE1_NOTIFICATION_ID)
            notificationMap.put("WEDNESDAY_DOSE2_NOTIFICATION_ID", WEDNESDAY_DOSE2_NOTIFICATION_ID)
            notificationMap.put("WEDNESDAY_NAP1_NOTIFICATION_ID", WEDNESDAY_NAP1_NOTIFICATION_ID)
            notificationMap.put("WEDNESDAY_NAP2_NOTIFICATION_ID", WEDNESDAY_NAP2_NOTIFICATION_ID)
            notificationMap.put("WEDNESDAY_NAP3_NOTIFICATION_ID", WEDNESDAY_NAP3_NOTIFICATION_ID)

            notificationMap.put("THURSDAY_WAKE_NOTIFICATION_ID", THURSDAY_WAKE_NOTIFICATION_ID)
            notificationMap.put("THURSDAY_EATBY_NOTIFICATION_ID",THURSDAY_EATBY_NOTIFICATION_ID)
            notificationMap.put("THURSDAY_DOSE1_NOTIFICATION_ID",THURSDAY_DOSE1_NOTIFICATION_ID)
            notificationMap.put("THURSDAY_DOSE2_NOTIFICATION_ID", THURSDAY_DOSE2_NOTIFICATION_ID)
            notificationMap.put("THURSDAY_NAP1_NOTIFICATION_ID", THURSDAY_NAP1_NOTIFICATION_ID)
            notificationMap.put("THURSDAY_NAP2_NOTIFICATION_ID", THURSDAY_NAP2_NOTIFICATION_ID)
            notificationMap.put("THURSDAY_NAP3_NOTIFICATION_ID", THURSDAY_NAP3_NOTIFICATION_ID)

            notificationMap.put("FRIDAY_WAKE_NOTIFICATION_ID", FRIDAY_WAKE_NOTIFICATION_ID)
            notificationMap.put("FRIDAY_EATBY_NOTIFICATION_ID",FRIDAY_EATBY_NOTIFICATION_ID)
            notificationMap.put("FRIDAY_DOSE1_NOTIFICATION_ID",FRIDAY_DOSE1_NOTIFICATION_ID)
            notificationMap.put("FRIDAY_DOSE2_NOTIFICATION_ID", FRIDAY_DOSE2_NOTIFICATION_ID)
            notificationMap.put("FRIDAY_NAP1_NOTIFICATION_ID", FRIDAY_NAP1_NOTIFICATION_ID)
            notificationMap.put("FRIDAY_NAP2_NOTIFICATION_ID", FRIDAY_NAP2_NOTIFICATION_ID)
            notificationMap.put("FRIDAY_NAP3_NOTIFICATION_ID", FRIDAY_NAP3_NOTIFICATION_ID)


            notificationMap.put("SATURDAY_WAKE_NOTIFICATION_ID", SATURDAY_WAKE_NOTIFICATION_ID)
            notificationMap.put("SATURDAY_EATBY_NOTIFICATION_ID",SATURDAY_EATBY_NOTIFICATION_ID)
            notificationMap.put("SATURDAY_DOSE1_NOTIFICATION_ID",SATURDAY_DOSE1_NOTIFICATION_ID)
            notificationMap.put("SATURDAY_DOSE2_NOTIFICATION_ID", SATURDAY_DOSE2_NOTIFICATION_ID)
            notificationMap.put("SATURDAY_NAP1_NOTIFICATION_ID", SATURDAY_NAP1_NOTIFICATION_ID)
            notificationMap.put("SATURDAY_NAP2_NOTIFICATION_ID", SATURDAY_NAP2_NOTIFICATION_ID)
            notificationMap.put("SATURDAY_NAP3_NOTIFICATION_ID", SATURDAY_NAP3_NOTIFICATION_ID)

            return notificationMap
        }

        fun getRequestCodeMap():HashMap<String,Int>{
            var requestCodeMap = HashMap<String, Int>()
            requestCodeMap.put("SUNDAY_WAKE_REQUEST_CODE", SUNDAY_WAKE_REQUEST_CODE)
            requestCodeMap.put("SUNDAY_EATBY_REQUEST_CODE", SUNDAY_EATBY_REQUEST_CODE)
            requestCodeMap.put("SUNDAY_DOSE1_REQUEST_CODE", SUNDAY_DOSE1_REQUEST_CODE)
            requestCodeMap.put("SUNDAY_DOSE2_REQUEST_CODE", SUNDAY_DOSE2_REQUEST_CODE)
            requestCodeMap.put("SUNDAY_NAP1_REQUEST_CODE", SUNDAY_NAP1_REQUEST_CODE)
            requestCodeMap.put("SUNDAY_NAP2_REQUEST_CODE", SUNDAY_NAP2_REQUEST_CODE)
            requestCodeMap.put("SUNDAY_NAP3_REQUEST_CODE", SUNDAY_NAP3_REQUEST_CODE)

            requestCodeMap.put("MONDAY_WAKE_REQUEST_CODE", MONDAY_WAKE_REQUEST_CODE)
            requestCodeMap.put("MONDAY_EATBY_REQUEST_CODE", MONDAY_EATBY_REQUEST_CODE)
            requestCodeMap.put("MONDAY_DOSE1_REQUEST_CODE", MONDAY_DOSE1_REQUEST_CODE)
            requestCodeMap.put("MONDAY_DOSE2_REQUEST_CODE", MONDAY_DOSE2_REQUEST_CODE)
            requestCodeMap.put("MONDAY_NAP1_REQUEST_CODE", MONDAY_NAP1_REQUEST_CODE)
            requestCodeMap.put("MONDAY_NAP2_REQUEST_CODE", MONDAY_NAP2_REQUEST_CODE)
            requestCodeMap.put("MONDAY_NAP3_REQUEST_CODE", MONDAY_NAP3_REQUEST_CODE)

            requestCodeMap.put("TUESDAY_WAKE_REQUEST_CODE", TUESDAY_WAKE_REQUEST_CODE)
            requestCodeMap.put("TUESDAY_EATBY_REQUEST_CODE", TUESDAY_EATBY_REQUEST_CODE)
            requestCodeMap.put("TUESDAY_DOSE1_REQUEST_CODE", TUESDAY_DOSE1_REQUEST_CODE)
            requestCodeMap.put("TUESDAY_DOSE2_REQUEST_CODE", TUESDAY_DOSE2_REQUEST_CODE)
            requestCodeMap.put("TUESDAY_NAP1_REQUEST_CODE", TUESDAY_NAP1_REQUEST_CODE)
            requestCodeMap.put("TUESDAY_NAP2_REQUEST_CODE", TUESDAY_NAP2_REQUEST_CODE)
            requestCodeMap.put("TUESDAY_NAP3_REQUEST_CODE", TUESDAY_NAP3_REQUEST_CODE)

            requestCodeMap.put("WEDNESDAY_WAKE_REQUEST_CODE", WEDNESDAY_WAKE_REQUEST_CODE)
            requestCodeMap.put("WEDNESDAY_EATBY_REQUEST_CODE", WEDNESDAY_EATBY_REQUEST_CODE)
            requestCodeMap.put("WEDNESDAY_DOSE1_REQUEST_CODE", WEDNESDAY_DOSE1_REQUEST_CODE)
            requestCodeMap.put("WEDNESDAY_DOSE2_REQUEST_CODE", WEDNESDAY_DOSE2_REQUEST_CODE)
            requestCodeMap.put("WEDNESDAY_NAP1_REQUEST_CODE", WEDNESDAY_NAP1_REQUEST_CODE)
            requestCodeMap.put("WEDNESDAY_NAP2_REQUEST_CODE", WEDNESDAY_NAP2_REQUEST_CODE)
            requestCodeMap.put("WEDNESDAY_NAP3_REQUEST_CODE", WEDNESDAY_NAP3_REQUEST_CODE)

            requestCodeMap.put("THURSDAY_WAKE_REQUEST_CODE", THURSDAY_WAKE_REQUEST_CODE)
            requestCodeMap.put("THURSDAY_EATBY_REQUEST_CODE", THURSDAY_EATBY_REQUEST_CODE)
            requestCodeMap.put("THURSDAY_DOSE1_REQUEST_CODE", THURSDAY_DOSE1_REQUEST_CODE)
            requestCodeMap.put("THURSDAY_DOSE2_REQUEST_CODE", THURSDAY_DOSE2_REQUEST_CODE)
            requestCodeMap.put("THURSDAY_NAP1_REQUEST_CODE", THURSDAY_NAP1_REQUEST_CODE)
            requestCodeMap.put("THURSDAY_NAP2_REQUEST_CODE", THURSDAY_NAP2_REQUEST_CODE)
            requestCodeMap.put("THURSDAY_NAP3_REQUEST_CODE", THURSDAY_NAP3_REQUEST_CODE)

            requestCodeMap.put("FRIDAY_WAKE_REQUEST_CODE", FRIDAY_WAKE_REQUEST_CODE)
            requestCodeMap.put("FRIDAY_EATBY_REQUEST_CODE", FRIDAY_EATBY_REQUEST_CODE)
            requestCodeMap.put("FRIDAY_DOSE1_REQUEST_CODE", FRIDAY_DOSE1_REQUEST_CODE)
            requestCodeMap.put("FRIDAY_DOSE2_REQUEST_CODE", FRIDAY_DOSE2_REQUEST_CODE)
            requestCodeMap.put("FRIDAY_NAP1_REQUEST_CODE", FRIDAY_NAP1_REQUEST_CODE)
            requestCodeMap.put("FRIDAY_NAP2_REQUEST_CODE", FRIDAY_NAP2_REQUEST_CODE)
            requestCodeMap.put("FRIDAY_NAP3_REQUEST_CODE", FRIDAY_NAP3_REQUEST_CODE)

            requestCodeMap.put("SATURDAY_WAKE_REQUEST_CODE", SATURDAY_WAKE_REQUEST_CODE)
            requestCodeMap.put("SATURDAY_EATBY_REQUEST_CODE", SATURDAY_EATBY_REQUEST_CODE)
            requestCodeMap.put("SATURDAY_DOSE1_REQUEST_CODE", SATURDAY_DOSE1_REQUEST_CODE)
            requestCodeMap.put("SATURDAY_DOSE2_REQUEST_CODE", SATURDAY_DOSE2_REQUEST_CODE)
            requestCodeMap.put("SATURDAY_NAP1_REQUEST_CODE", SATURDAY_NAP1_REQUEST_CODE)
            requestCodeMap.put("SATURDAY_NAP2_REQUEST_CODE", SATURDAY_NAP2_REQUEST_CODE)
            requestCodeMap.put("SATURDAY_NAP3_REQUEST_CODE", SATURDAY_NAP3_REQUEST_CODE)

            return requestCodeMap
        }
    }
}