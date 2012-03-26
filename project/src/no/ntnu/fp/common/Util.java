package no.ntnu.fp.common;

import java.util.Calendar;
import java.util.Date;

public class Util {
    public static Date dateFromString(String input){
        Calendar cal = Calendar.getInstance();
        int[] date = new int[3];
        for(int i = 0; i < date.length; i++){
            date[i] = Integer.parseInt(input.split("-")[i]);
        }
        cal.set(Calendar.YEAR, date[0]);
        cal.set(Calendar.MONTH, date[1]);
        cal.set(Calendar.DAY_OF_MONTH, date[2]);
        return cal.getTime();
    }

    public static String dateToString(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        String output = "%d-%d-%d";
        output = String.format(output, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
        return output;
    }

    public static Date dateTimeFromString(String input){
            Calendar cal = Calendar.getInstance();
            int[] date = new int[5];
            String[] d = combineArray(input.split(" ")[0].split("-"), input.split(" ")[1].split(":"));

            for(int i = 0; i < date.length; i++){
                date[i] = Integer.parseInt(d[i]);
            }
            cal.set(Calendar.YEAR, date[0]);
            cal.set(Calendar.MONTH, date[1]);
            cal.set(Calendar.DAY_OF_MONTH, date[2]);
            cal.set(Calendar.HOUR_OF_DAY, date[3]);
            cal.set(Calendar.MINUTE, date[4]);
            return cal.getTime();
        }

        public static String dateTimeToString(Date date){
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            String output = "%d-%d-%d %d:%s:00";
            output = String.format(output, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), 
            		cal.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.HOUR_OF_DAY), 
            		((""+cal.get(Calendar.MINUTE)).length() == 1) ? cal.get(Calendar.MINUTE)+"0" : ""+cal.get(Calendar.MINUTE));
            return output;
        }

    static String[] combineArray(String[] A, String[] B) {
        String[] C= new String[A.length+B.length];
        System.arraycopy(A, 0, C, 0, A.length);
        System.arraycopy(B, 0, C, A.length, B.length);
        return C;
    }

    public static String getMonthText(int month) {
        switch(month){
            case 0: return "Jan";
            case 1: return "Feb";
            case 2: return "Mar";
            case 3: return "Apr";
            case 4: return "May";
            case 5: return "Jun";
            case 6: return "Jul";
            case 7: return "Aug";
            case 8: return "Sep";
            case 9: return "Oct";
            case 10: return "Nov";
            case 11: return "Dec";
           
        }
            return "";
    }
    
    public static void print(String s )          { if(Constants.DEBUG) System.out.println(s); }
    public static void print(boolean s )         { if(Constants.DEBUG) System.out.println(s); }
    public static void print(int s )             { if(Constants.DEBUG) System.out.println(s); }
    public static void print(Object s )          { if(Constants.DEBUG) System.out.println(s); }
    public static void localPrint(String s )     { if(Constants.DEBUG&&!Constants.IS_SERVER) System.out.println(s); }
    public static void localPrint(boolean s )    { if(Constants.DEBUG&&!Constants.IS_SERVER) System.out.println(s); }
    public static void localPrint(int s )        { if(Constants.DEBUG&&!Constants.IS_SERVER) System.out.println(s); }
    public static void localPrint(Object s )     { if(Constants.DEBUG&&!Constants.IS_SERVER) System.out.println(s); }
}
