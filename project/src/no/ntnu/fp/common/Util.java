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
        System.out.println(cal.getTime().toString());
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
            cal.set(Calendar.HOUR, date[3]);
            cal.set(Calendar.MINUTE, date[4]);
            System.out.println(cal.getTime().toString());
            return cal.getTime();
        }

        public static String dateTimeToString(Date date){
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            String output = "%d-%d-%d %d:%d:00";
            output = String.format(output, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.HOUR), cal.get(Calendar.MINUTE));
            return output;
        }

    static String[] combineArray(String[] A, String[] B) {
        String[] C= new String[A.length+B.length];
        System.arraycopy(A, 0, C, 0, A.length);
        System.arraycopy(B, 0, C, A.length, B.length);
        return C;
    }
}
