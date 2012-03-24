package no.ntnu.fp.common;


public class Constants {
    public final static int CLIENT_PORT = 1234;
    public final static int SERVER_PORT = 4321;

    public final static boolean use_server = true;
    public final static boolean use_local_server = true;



    public static String getDbUrl(){
        if (use_local_server) return "jdbc:mysql://mysql.stud.ntnu.no/sondrele_fp";
        return "jdbc:mysql://localhost/fp";
    }

    public static String getDbUsername(){
        if (use_local_server) return "sondrele_fp";
        return "fp";
    }

    public static String getDbPassword(){
        if (use_local_server) return "fellesp1";
        return "superfppassord";
    }

    public static String getServerUrl() {
        if (use_local_server) return "localhost";
        return "lkng.me";
    }
}
