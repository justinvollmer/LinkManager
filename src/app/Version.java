package app;

public class Version {
    private static int majorVersion = 2;
    private static int minorVersion = 2;
    private static int patchVersion = 0;

    public static String getSoftwareVersion() {
        return "v" + majorVersion + "." + minorVersion + "." + patchVersion;
    }
}
