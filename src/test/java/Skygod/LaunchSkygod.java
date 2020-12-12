package skygod;

import net.minestom.server.Bootstrap;

// To launch with VM arguments:
// -Dminestom.extension.indevfolder.classes=build/classes/java/main/ -Dminestom.extension.indevfolder.resources=build/resources/main/
public class LaunchSkygod {
    public static void main(String[] args) {
        Bootstrap.bootstrap("skygod.SkygodServer", args);
    }
}