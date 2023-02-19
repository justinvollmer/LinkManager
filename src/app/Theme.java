package app;

import config.Config;

import javax.swing.*;
import java.awt.*;

public class Theme {

    private static String getTheme() throws Exception {
        String theme = Config.getProperties("theme");
        if (theme == null) {
            Config.setProperties("theme", "light");
            theme = Config.getProperties("theme");
        }
        return theme;
    }

    public static void updateTheme(Object dialog) throws NullPointerException, IllegalArgumentException {
        if (dialog == null) {
            throw new NullPointerException("Error updating theme: dialog not found");
        }
        if (!(dialog instanceof JFrame) && !(dialog instanceof JDialog)) {
            throw new IllegalArgumentException("Error updating theme: dialog is invalid");
        }

        String theme;
        try {
            theme = getTheme();
        } catch (Exception e) {
            throw new IllegalArgumentException("Error updating theme: could not reach property file");
        }


        if (theme.equalsIgnoreCase("light")) {
            ((RootPaneContainer) dialog).getContentPane().setBackground(Color.LIGHT_GRAY);
            ((RootPaneContainer) dialog).getContentPane().setForeground(Color.BLACK);
        } else if (theme.equalsIgnoreCase("dark")) {
            ((RootPaneContainer) dialog).getContentPane().setBackground(Color.DARK_GRAY);
            ((RootPaneContainer) dialog).getContentPane().setForeground(Color.LIGHT_GRAY);
        } else {
            throw new IllegalArgumentException("Error updating theme: theme is invalid");
        }
    }
}
