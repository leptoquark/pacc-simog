package it.avlp.simog.util;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 *
 * @author Rajiv
 */
public class DateEvaluatorFactory {

 /* differencial types */
    public static final String KEY_YEAR    = "year";
    public static final String KEY_MONTH   = "month";
    public static final String KEY_WEEK    = "week";
    public static final String KEY_DAY     = "day";
    public static final String KEY_HOUR    = "hour";
    public static final String KEY_MINUTE  = "minute";
    public static final String KEY_SECOND  = "second";

    /*************************************************************************************************************
     * Ottiene le differenze delle date
     * 
     * @param lhsOperandObj Object
     * @param rhsOperandObj Object
     * @param localeObj Object
     * @param timeZoneObj Object
     * @param differencialType String
     * @return int
     */
    public static int getDateTimeDifference(Object lhsOperandObj, Object rhsOperandObj, Object localeObj, Object timeZoneObj, String differencialType){
        long lhsOperand = 0;
        long rhsOperand = 0;
        Locale locale   = null;
        TimeZone timezone = null;
        
        if(lhsOperandObj instanceof Date){
            lhsOperand = ((Date)lhsOperandObj).getTime();
        }else if(lhsOperandObj instanceof Timestamp){
            lhsOperand = ((Timestamp)lhsOperandObj).getTime();
        }else if(lhsOperandObj instanceof java.lang.Long){
            lhsOperand = (Long)lhsOperandObj;
        }
        
        Date lhsDate = new Date(lhsOperand);
        
        if(rhsOperandObj instanceof Date){
            rhsOperand = ((Date)rhsOperandObj).getTime();
        }else if(rhsOperandObj instanceof Timestamp){
            rhsOperand = ((Timestamp)rhsOperandObj).getTime();
        }else if(rhsOperandObj instanceof java.lang.Long){
            rhsOperand = (Long)rhsOperandObj;
        }
        
        Date rhsDate = new Date(rhsOperand);
        
        if(localeObj instanceof Locale){
            locale = (Locale)localeObj;
        } else {
        	String language = Locale.getDefault().getLanguage();
            String country =  Locale.getDefault().getCountry();
        	locale = new Locale( "\"" + language + "\"" + "," +  "\"" + country + "\"" );
        }
        
        if(timeZoneObj instanceof String){
            String timezoneStr = (String) timeZoneObj;
            timezone = TimeZone.getTimeZone(timezoneStr);
        }else {
            timezone =  TimeZone.getDefault();
        }
        
        // set the dateformat
        DateFormat dateformat = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT, locale);
        
        // set the timezone and locale
        Calendar lhsCalender = Calendar.getInstance(timezone, locale);
        lhsCalender.setTime(lhsDate);
        Calendar rhsCalender = Calendar.getInstance(timezone, locale);
        rhsCalender.setTime(rhsDate);
        
        // get DST and Zone offset
        long lhsDateVal = lhsDate.getTime() + lhsCalender.get(Calendar.ZONE_OFFSET) + lhsCalender.get(Calendar.DST_OFFSET) ;
        long rhsDateVal = rhsDate.getTime() + rhsCalender.get(Calendar.ZONE_OFFSET) + rhsCalender.get(Calendar.DST_OFFSET) ;
        
        // seconds
        int lhsSecs = (int)(lhsDateVal/ 1000);
        int rhsSecs = (int)(rhsDateVal/ 1000);
        
        // minutes
        int lhsMins = (int)(lhsDateVal/ 60000); //60*1000 (s*ms)
        int rhsMins = (int)(rhsDateVal/ 60000); //60*1000 (s*ms)
        
        // hours
        int lhsHrs = (int) (lhsDateVal / 3600000); //60*60*1000 (m*s*ms)
        int rhsHrs = (int) (rhsDateVal / 3600000);
        
        // days
        int lhsDays = lhsHrs/24;
        int rhsDays = rhsHrs/24;
                
        // date difference calculation (rhs - lhs = &lt;ans&gt;)
        int secondsDifference   = rhsSecs - lhsSecs;
        int minutesDifference   = rhsMins - lhsMins;
        int hourDifference      = rhsHrs -  lhsHrs;
        int dayDifference       = rhsDays - lhsDays;
        int weekOffset          = (rhsCalender.get(Calendar.DAY_OF_WEEK) - lhsCalender.get(Calendar.DAY_OF_WEEK))<0 ? 1 : 0;
        int weekDifference      = dayDifference/7 + weekOffset;
        int yearDifference      = rhsCalender.get(Calendar.YEAR) - lhsCalender.get(Calendar.YEAR);
        int monthDifference     = yearDifference * 12 + rhsCalender.get(Calendar.MONTH) - lhsCalender.get(Calendar.MONTH);
        
        // difference excluding weekends
        int dayDiffExclWeekends     = getDateDiffExcluWeekends(lhsCalender, rhsCalender, lhsDate, rhsDate); 
        int hourDiffExclWeekends    = dayDiffExclWeekends * 24;
        int minutesDiffExclWeekends = hourDiffExclWeekends * 3600000;
        int secondsDiffExclWeekends = minutesDiffExclWeekends * 1000;
       
        // difference excluding sundays
        int dayDiffExclSundays      = getDateDiffExcluSundays(lhsCalender, rhsCalender, lhsDate, rhsDate);  
        int hourDiffExclSundays     = dayDiffExclSundays * 24;
        int minutesDiffExclSundays  = hourDiffExclSundays * 3600000;
        int secondsDiffExclSundays  = minutesDiffExclSundays * 1000;
        
        if(differencialType.equals(DateEvaluatorFactory.KEY_YEAR)){
            return new Integer(yearDifference);
        }else if(differencialType.equals(DateEvaluatorFactory.KEY_MONTH)){
            return new Integer(monthDifference);
        }else if(differencialType.equals(DateEvaluatorFactory.KEY_WEEK)){
            return new Integer(weekDifference);
        }else if(differencialType.equals(DateEvaluatorFactory.KEY_DAY)){
            return new Integer(dayDifference);
        }else if(differencialType.equals(DateEvaluatorFactory.KEY_HOUR)){
            return new Integer(hourDifference);
        }else if(differencialType.equals(DateEvaluatorFactory.KEY_MINUTE)){
            return new Integer(minutesDifference);
        }else if(differencialType.equals(DateEvaluatorFactory.KEY_SECOND)){
            return new Integer(secondsDifference);
        }
        
        return 0;
    }
    
    
    /*******************************************************************************************************
     * calculate number of dates excluding sunday and saturday
     * 
     * param lhsCalender Calendar
     * param rhsCalender Calendar
     * param lhsDate Date
     * param rhsDate Date
     * return int
     */
    private static int getDateDiffExcluWeekends(Calendar lhsCalender, Calendar rhsCalender, Date lhsDate, Date rhsDate){
        lhsCalender.setTime(lhsDate);
        rhsCalender.setTime(rhsDate);
        rhsCalender.add(Calendar.DATE, 1); // Needed to test the rhsCalender value else it will stop at rhsCalender-1
        int result = 0;
        while (lhsCalender.before(rhsCalender)) {
            if (lhsCalender.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY && lhsCalender.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY)
                result++;
            lhsCalender.add(Calendar.DATE,1);
        }
        return result;
    }
    
    /*************************************************************************************************
     * calculate number of dates excluding sunday
     * 
     * param lhsCalender Calendar
     * param rhsCalender Calendar
     * param lhsDate Date
     * param rhsDate Date
     * return int
     */
    private static int getDateDiffExcluSundays(Calendar lhsCalender, Calendar rhsCalender, Date lhsDate, Date rhsDate){
        lhsCalender.setTime(lhsDate);
        rhsCalender.setTime(rhsDate);
        
        rhsCalender.add(Calendar.DATE, 1); // Needed to test the rhsCalender value else it will stop at rhsCalender-1
        int result = 0;
        while (lhsCalender.before(rhsCalender)) {
            if (lhsCalender.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY)
                result++;
            lhsCalender.add(Calendar.DATE,1);
        }
        return result;
    }

 public static void main(String args[]){
       String language = Locale.getDefault().getLanguage();
       String country =  Locale.getDefault().getCountry();
       
       getDateTimeDifference(new Date(), new Long("1248954023000") , 
    		   new Locale( "\"" + language + "\"" + "," +  "\"" + country + "\"" )  ,   
    		   TimeZone.getDefault().getDisplayName().toString() ,
    		   KEY_MONTH);
  }
}