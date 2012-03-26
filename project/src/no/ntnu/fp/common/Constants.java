package no.ntnu.fp.common;




public class Constants {

    public final static boolean DEBUG = true;

    public final static int CLIENT_PORT = 3333;
    public final static int SERVER_PORT = 4321;

    public final static boolean IS_SERVER = false;
    public final static boolean USE_SERVER = true;
    public final static boolean USE_LOCAL_SERVER = true;



    public static String getDbUrl(){
        if (USE_LOCAL_SERVER) return "jdbc:mysql://mysql.stud.ntnu.no/sondrele_fp";
        return "jdbc:mysql://localhost/fp";
    }

    public static String getDbUsername(){
        if (USE_LOCAL_SERVER) return "sondrele_fp";
        return "fp";
    }

    public static String getDbPassword(){
        if (USE_LOCAL_SERVER) return "fellesp1";
        return "superfppassord";
    }

    public static String getServerUrl() {
        if (USE_LOCAL_SERVER) return "localhost";
        return "lkng.me";
    }
}
