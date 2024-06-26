package Part2;

import java.util.*;

public class AppLocale {
    private static final String strMsg = "MyBundle";
    private static Locale loc = Locale.getDefault();
    private static ResourceBundle res =
            ResourceBundle.getBundle(AppLocale.strMsg, AppLocale.loc);

    static Locale getLocale() {
        return AppLocale.loc;
    }

    static void set (Locale loc) {
        AppLocale.loc = loc;
        res = ResourceBundle.getBundle(AppLocale.strMsg, AppLocale.loc);
    }

    static ResourceBundle getBundle() {
        return AppLocale.res;
    }

    static String getString (String key) {
        return AppLocale.res.getString(key);
    }

    public static final String id="id";
    public static final String allTrees = "allTrees";
    public static final String creationDate = "creationDate";
    public static final String type = "type";
    public static final String treeSort = "treeSort";
    public static final String age = "age";
    public static final String fruiting = "fruiting";
    public static final String needReplace = "needReplace";
    public static final String noNeedReplace = "noNeedReplace";
}
