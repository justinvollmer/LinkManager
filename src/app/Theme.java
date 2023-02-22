package app;

import config.Config;

public class Theme {

    public static String getTheme() throws Exception {
        String theme = Config.getProperties("theme");
        if (theme == null) {
            Config.setProperties("theme", "light");
            theme = Config.getProperties("theme");
        }
        return theme;
    }

}
