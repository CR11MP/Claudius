package me.crimp.claudius;

//import me.crimp.claudius.CapeShit.CapeManager;
import me.crimp.claudius.mod.modules.client.RPC;
import me.crimp.claudius.managers.*;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.Display;

@Mod(modid = Claudius.MODID, name = Claudius.MODNAME, version = Claudius.MODVER)
public class Claudius {
    public static final String MODID = "claudius";
    public static final String MODNAME = "claudius";
    public static final String MODVER = "1.0";
    public static final Logger LOGGER = LogManager.getLogger("claudius");
    public static CommandManager commandManager;
    public static ModuleManager moduleManager;
    public static ColorManager colorManager;
    public static HoleManager holeManager;
    public static InventoryManager inventoryManager;
    public static RotationManager rotationManager;
    public static PositionManager positionManager;
    public static SpeedManager speedManager;
    public static ReloadManager reloadManager;
    public static FileManager fileManager;
    public static ConfigManager configManager;
    public static ServerManager serverManager;
    public static EventManager eventManager;
    public static TextManager textManager;
    @Mod.Instance
    public static Claudius INSTANCE;
    private static boolean unloaded;

    static {
        unloaded = false;
    }

    public static void load() {
        LOGGER.info("Loading claudius by Crimp");
        unloaded = false;
        if (reloadManager != null) {
            reloadManager.unload();
            reloadManager = null;
        }
        textManager = new TextManager();
        commandManager = new CommandManager();
        moduleManager = new ModuleManager();
        rotationManager = new RotationManager();
        eventManager = new EventManager();
        speedManager = new SpeedManager();
        inventoryManager = new InventoryManager();
        serverManager = new ServerManager();
        fileManager = new FileManager();
        colorManager = new ColorManager();
        positionManager = new PositionManager();
        configManager = new ConfigManager();
        holeManager = new HoleManager();
        LOGGER.info("Managers loaded.");
        moduleManager.init();
        LOGGER.info("Modules loaded.");
        configManager.init();
        eventManager.init();
        LOGGER.info("EventManager loaded.");
        textManager.init(true);
        moduleManager.onLoad();
        LOGGER.info("claudius successfully loaded!\n");
    }

    public static void unload(boolean unload) {
        LOGGER.info("\n\nUnloading claudius by Crimp");
        if (unload) {
            reloadManager = new ReloadManager();
            reloadManager.init(commandManager != null ? commandManager.getPrefix() : "!");
        }
        Claudius.onUnload();
        eventManager = null;
        speedManager = null;
        holeManager = null;
        positionManager = null;
        rotationManager = null;
        configManager = null;
        commandManager = null;
        colorManager = null;
        serverManager = null;
        fileManager = null;
        inventoryManager = null;
        moduleManager = null;
        textManager = null;
        //cape = null;
        LOGGER.info("claudius unloaded!\n");
    }

    public static void reload() {
        Claudius.unload(false);
        Claudius.load();
    }

    public static void onUnload() {
        if (!unloaded) {
            eventManager.onUnload();
            moduleManager.onUnload();
            configManager.saveConfig(Claudius.configManager.config.replaceFirst("claudius/", ""));
            moduleManager.onUnloadPost();
            unloaded = true;
        }
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        LOGGER.info("F" +
                "uckyWucky - Crimp");
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        Display.setTitle("Minecraft 1.12.2");
        Claudius.load();
        }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        if (moduleManager.getModuleByClass(RPC.class).isEnabled()) {
            DiscordPresence.stop();
            DiscordPresence.start();
            Display.setTitle("Minecraft 1.12.2");
        }
}
}

