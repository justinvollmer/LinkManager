package app;

public class Version {
    private static int majorVersion = 1;
    private static int minorVersion = 2;
    private static int patchVersion = 1;

    public static String getSoftwareVersion() {
        return "v" + majorVersion + "." + minorVersion + "." + patchVersion;
    }
}
