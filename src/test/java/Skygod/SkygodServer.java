package skygod;

import net.minestom.server.MinecraftServer;
import net.minestom.server.extras.MojangAuth;
import net.minestom.server.extras.optifine.OptifineSupport;
import net.minestom.server.network.ConnectionManager;
import skygod.events.Events;

public class SkygodServer {
    public static void main(String[] args) {
    	
        // Initialization
        MinecraftServer minecraftServer = MinecraftServer.init();
        
        // Register events
        ConnectionManager connectionManager = MinecraftServer.getConnectionManager();
        connectionManager.addPlayerInitialization(player -> {Events.registerPlayerEvents(player);});
        
        // Allow Optifine clients
        OptifineSupport.enable();
        
        // Enable Mojang Auth
        MojangAuth.init();
        
        
        // Testing Area
        // Do testing code here
        
        
        // Start the server
        minecraftServer.start("localhost", 25565);
    }
}