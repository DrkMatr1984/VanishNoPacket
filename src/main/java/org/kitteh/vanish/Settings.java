package org.kitteh.vanish;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.Set;

public final class Settings {
    private static boolean enablePermTest;
    private static String fakeQuit;
    private static String fakeJoin;
    private static boolean autoFakeJoinSilent;
    private static boolean worldChangeCheck;
    private static int lightningEffectCount;
    private static String chatChannel;
    private static boolean usePluginChannels;
    private static String pluginChannel;

    private static final int confVersion = 6; // Tracking config version
    
    public static boolean isUsePluginChannels() {
        return usePluginChannels;
    }
    
    public static String getPluginChannel() {
        return pluginChannel;
    }
    public static boolean getAutoFakeJoinSilent() {
        return Settings.autoFakeJoinSilent;
    }

    public static boolean getEnablePermTest() {
        return Settings.enablePermTest;
    }

    public static String getFakeJoin() {
        return Settings.fakeJoin;
    }

    public static String getFakeQuit() {
        return Settings.fakeQuit;
    }

    public static int getLightningCount() {
        return Settings.lightningEffectCount;
    }

    public static boolean getWorldChangeCheck() {
        return Settings.worldChangeCheck;
    }
    
    public static String getChatChannel() {
    	return Settings.chatChannel;
    }

    static void freshStart(VanishPlugin plugin) {
        final FileConfiguration config = plugin.getConfig();
        config.options().copyDefaults(true);
        final int ver = config.getInt("configVersionDoNotTouch.SeriouslyThisWillEraseYourConfig", 0);
        if (ver != Settings.confVersion) {
            plugin.getLogger().info("Attempting to update your configuration. Check to make sure it's ok");
            if (ver < 1) {
                config.set("hooks.spoutcraft", config.getBoolean("spoutcraft.enable", true));
                config.set("spoutcraft.enable", null);
                config.set("spoutcraft", null);
            }
            if ((ver <= 1) || config.contains("permtest.enable")) {
                final boolean permtest = config.getBoolean("permtest.enable", false);
                config.set("permtest.enable", null);
                config.set("permtest", permtest);
                config.set("enableColoration", null);
                config.set("enableTabControl", null);
                config.set("effects.lightning.count", 30);
            }
            final boolean updates = config.getBoolean("updates.check", true);
            config.set("updates.check", null);
            config.set("checkupdates", updates);
        }
        if ((ver <= 3)) {
            if ((ver <= 4)) {
                config.set("colornametags", true);
            }
            if ((ver <= 5)) {
            	config.set("chatchannel", "VanishNP");
            }
            if((ver <= 6)){
                config.set("usePluginChannels", true);
                config.set("pluginChannel","vnp:status");
            }
            config.set("configVersionDoNotTouch.SeriouslyThisWillEraseYourConfig", Settings.confVersion);
            plugin.saveConfig();
        }
        Settings.enablePermTest = config.getBoolean("permtest", false);
        Settings.fakeJoin = config.getString("fakeannounce.join", "%p joined the game.").replace("&&", String.valueOf(ChatColor.COLOR_CHAR));
        Settings.fakeQuit = config.getString("fakeannounce.quit", "%p left the game.").replace("&&", String.valueOf(ChatColor.COLOR_CHAR));
        Settings.autoFakeJoinSilent = config.getBoolean("fakeannounce.automaticforsilentjoin", false);
        Settings.worldChangeCheck = config.getBoolean("permissionsupdates.checkonworldchange", false);
        Settings.lightningEffectCount = config.getInt("effects.lightning.count", 30);
        if (Settings.lightningEffectCount < 1) {
            Settings.lightningEffectCount = 1;
        }
        Settings.chatChannel = config.getString("chatchannel");
        if (config.getBoolean("debug", false)) {
            Debuggle.itsGoTime(plugin);
        } else {
            Debuggle.nah();
        }
        Settings.usePluginChannels = plugin.getConfig().getBoolean("usePluginChannels", true);
        Settings.pluginChannel = config.getString("pluginChannel","vnp:status");
    }
}