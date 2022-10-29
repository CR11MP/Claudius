package me.crimp.claudius.mod.modules;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.crimp.claudius.Claudius;
import me.crimp.claudius.event.events.ClientEvent;
import me.crimp.claudius.event.events.Render2DEvent;
import me.crimp.claudius.event.events.Render3DEvent;
import me.crimp.claudius.mod.Feature;
import me.crimp.claudius.mod.modules.client.HUD;
import me.crimp.claudius.mod.setting.Bind;
import me.crimp.claudius.mod.setting.Setting;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.common.MinecraftForge;

import java.util.ArrayList;
import java.util.List;

public abstract class Module extends Feature {
    private final String description;
    private final Category category;
    public Setting<Boolean> enabled = this.register(new Setting<Boolean>("Enabled", false));
    public Setting<Boolean> drawn = this.register(new Setting<Boolean>("Drawn", true, "Drawn/"));
    public Setting<Bind> bind = this.register(new Setting<Bind>("Keybind", new Bind(-1)));
    public Setting<String> displayName;
    public boolean hasListener;
    public boolean alwaysListening;
    public boolean hidden;
    public float arrayListOffset = 0.0f;
    public float arrayListVOffset = 0.0f;
    public float offset;
    public float vOffset;
    public boolean sliding;

    public Module(String name, String description, Category category, boolean hasListener, boolean hidden, boolean alwaysListening) {
        super(name);
        this.displayName = this.register(new Setting<>("DisplayName", name));
        this.description = description;
        this.category = category;
        this.hasListener = hasListener;
        this.hidden = hidden;
        this.alwaysListening = alwaysListening;
    }


    public boolean isSliding() {
        return this.sliding;
    }

    public void onEnable() {
    }

    public void onDisable() {
    }

    public void onToggle() {
    }

    public void onLoad() {
    }

    public void onTick() {
    }

    public void onLogin() {}

    public void onLogout() {}

    public void onUpdate() {}

    public void onRender2D(Render2DEvent event) {}

    public void onRender3D(Render3DEvent event) {}

    public void onUnload() {}

    public String getDisplayInfo() {
        return null;
    }

    public boolean isOn() {
        return this.enabled.getValue();
    }

    public boolean isOff() {
        return !this.enabled.getValue();
    }

    public void setEnabled(boolean enabled) {
        if (enabled) this.enable();
        else this.disable();
    }

    public void enable() {
        this.enabled.setValue(Boolean.TRUE);
        this.onToggle();
        this.onEnable();
        if (HUD.getInstance().notifyToggles.getValue()) {
            TextComponentString text = new TextComponentString(Claudius.commandManager.getClientMessage() + " " + this.getDisplayName() + " Enabled.");
            Module.mc.ingameGUI.getChatGUI().printChatMessageWithOptionalDeletion(text, 1);
        }
        if (this.isOn() && this.hasListener && !this.alwaysListening) {
            MinecraftForge.EVENT_BUS.register(this);
        }
    }

    public void disable() {
        if (this.hasListener && !this.alwaysListening) {
            MinecraftForge.EVENT_BUS.unregister(this);
        }
        this.enabled.setValue(false);
        if (HUD.getInstance().notifyToggles.getValue()) {
            TextComponentString text = new TextComponentString(Claudius.commandManager.getClientMessage() + " " + this.getDisplayName() + " Disabled.");
            Module.mc.ingameGUI.getChatGUI().printChatMessageWithOptionalDeletion(text, 1);
        }
        this.onToggle();
        this.onDisable();
    }

    public void toggle() {
        ClientEvent event = new ClientEvent(!this.isEnabled() ? 1 : 0, this);
        MinecraftForge.EVENT_BUS.post(event);
        if (!event.isCanceled()) {
            this.setEnabled(!this.isEnabled());
        }
    }

    public String getDisplayName() {
        return this.displayName.getValue();
    }


    public String getDescription() {
        return this.description;
    }

    public boolean isDrawn() {
        return this.drawn.getValue();
    }

    public Category getCategory() {
        return this.category;
    }


    public Bind getBind() {
        return this.bind.getValue();
    }

    public boolean listening() {
        return this.hasListener && this.isOn() || this.alwaysListening;
    }

    public String getFullArrayString() {
        return this.getDisplayName() + ChatFormatting.GRAY + (this.getDisplayInfo() != null ? " [" + ChatFormatting.WHITE + this.getDisplayInfo() + ChatFormatting.GRAY + "]" : "");
    }

    public enum Category {
        Misc("Misc"),
        Exploit("Exploit"),
        Pvp("Pvp"),
        Render("Render"),
        Fixes("Fixes"),
        Movement("Movement"),
        Client("Client");

        private final String name;

        Category(String name) {
            this.name = name;
        }

        public static int amountPerCategory(Category category) {
            List<Module> categoryModules = new ArrayList<>();
            for (Module module : Claudius.INSTANCE.moduleManager.modules) {
                if (module.getCategory().equals(category)) {
                    categoryModules.add(module);
                }
            }
            return categoryModules.size();
        }

        public static Category getCategoryFromString(String id) {
            Category finalCategory = null;
            for (Category category : Category.values()) {
                if (category.toString().equalsIgnoreCase(id)) {
                    finalCategory = category;
                    break;
                }
            }
            return finalCategory;
        }

        public String getName() {
            return this.name;
        }
    }
}

