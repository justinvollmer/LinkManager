package app;

public class Version {
    private static int majorVersion = 1;
    private static int minorVersion = 1;
    private static int patchVersion = 0;

    public static String getSoftwareVersion() {
        return "v" + majorVersion + "." + minorVersion + "." + patchVersion;
    }
}
