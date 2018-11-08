package com.motiolabs.android.myfast.utils;

/**
 * Store Constants for the NewsFeed app.
 */

public class Constants {

    /**
     * Create a private constructor because no one should ever create a {@link Constants} object.
     */
    private Constants() {
    }

    public final static String fast_server = "http://api.fast.or.id/";
    public final static String server_addr = fast_server + "index.php/api/";
    public final static String url_login = server_addr + "login";

    public final static String log_app = "fast";

    public final static String fast_shared = "fastsharedpref";
    public final static String spref_pversi = "param_versi";
    public final static String spref_ouid = "fast_uid";
    public final static String spref_osession = "fast_session";
    public final static String spref_name = "fast_name";
    public final static String spref_email = "fast_email";
    public final static String spref_avatar = "fast_avatar";

}
