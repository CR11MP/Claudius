package me.crimp.claudius.mod.gui.components.items.button;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.crimp.claudius.Claudius;
import me.crimp.claudius.mod.gui.ClickGui;
import me.crimp.claudius.mod.gui.components.Component;
import me.crimp.claudius.mod.gui.components.items.Item;
import me.crimp.claudius.mod.modules.Module;
import me.crimp.claudius.mod.modules.client.ClickGuiModule;
import me.crimp.claudius.mod.modules.text.BetterWatermark;
import me.crimp.claudius.mod.setting.Bind;
import me.crimp.claudius.mod.setting.Setting;
import me.crimp.claudius.utils.ColorUtil;
import me.crimp.claudius.utils.RenderUtil;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.init.SoundEvents;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

public class ModuleButton extends Button {
    private final Module module;
    private List<Item> items = new ArrayList<>();
    private boolean subOpen;

    public ModuleButton(Module module) {
        super(module.getName());
        this.module = module;
        this.initSettings();
    }

    public static void drawCompleteImage(float posX, float posY, int width, int height) {
        GL11.glPushMatrix();
        GL11.glTranslatef(posX, posY, 0.0f);
        GL11.glBegin(7);
        GL11.glTexCoord2f(0.0f, 0.0f);
        GL11.glVertex3f(0.0f, 0.0f, 0.0f);
        GL11.glTexCoord2f(0.0f, 1.0f);
        GL11.glVertex3f(0.0f, (float) height, 0.0f);
        GL11.glTexCoord2f(1.0f, 1.0f);
        GL11.glVertex3f((float) width, (float) height, 0.0f);
        GL11.glTexCoord2f(1.0f, 0.0f);
        GL11.glVertex3f((float) width, 0.0f, 0.0f);
        GL11.glEnd();
        GL11.glPopMatrix();
    }

    public void initSettings() {
        ArrayList<Item> newItems = new ArrayList<>();
        if (!this.module.getSettings().isEmpty()) {
            for (Setting setting : this.module.getSettings()) {
                if (setting.getValue() instanceof Boolean && !setting.getName().equals("Enabled")) {
                    newItems.add(new BooleanButton(setting));
                }
                if (setting.getValue() instanceof Bind && !setting.getName().equalsIgnoreCase("Keybind") && !this.module.getName().equalsIgnoreCase("Hud")) {
                    newItems.add(new BindButton(setting));
                }
                if ((setting.getValue() instanceof String || setting.getValue() instanceof Character) && !setting.getName().equalsIgnoreCase("displayName")) {
                    newItems.add(new StringButton(setting));
                }
                if (setting.isNumberSetting() && setting.hasRestriction()) {
                    newItems.add(new Slider(setting));
                    continue;
                }
                if (!setting.isEnumSetting()) continue;
                newItems.add(new EnumButton(setting));
            }
        }
        newItems.add(new BindButton((Setting<Bind>) this.module.getSettingByName("Keybind")));
        this.items = newItems;
    }

    //mc.getTextureManager().bindTexture();
    // ModuleButton.drawCompleteImage(this.x - 1.5f + (float) this.width - 7.4f, this.y - 2.2f - (float) ClickGui.getClickGui().getTextOffset(), 8, 8);

    @Override
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        if (!this.items.isEmpty()) {
            if (ClickGuiModule.INSTANCE.Cross.getValue()) {
                if (this.subOpen) {
                    if (this.module.isEnabled()) {
                        Claudius.textManager.drawStringWithShadow("-", this.x - 1.5f + this.width - 7.4f, this.y - 2.2f - ClickGui.getClickGui().getTextOffset(), -1);
                    } else {
                        Claudius.textManager.drawStringWithShadow(ChatFormatting.GRAY + "-", this.x - 1.5f + this.width - 7.4f, this.y - 2.2f - ClickGui.getClickGui().getTextOffset(), -1);
                    }
                } else if (this.module.isEnabled()) {
                    Claudius.textManager.drawStringWithShadow("+", this.x - 1.5f + this.width - 7.4f, this.y - 2.2f - ClickGui.getClickGui().getTextOffset(), -1);
                } else {
                    Claudius.textManager.drawStringWithShadow(ChatFormatting.GRAY + "+", this.x - 1.5f + this.width - 7.4f, this.y - 2.2f - ClickGui.getClickGui().getTextOffset(), -1);
                }
            }
            if (this.subOpen) {
                float height = 1.0f;
                for (final Item item : this.items) {
                    ++Component.counter1[0];
                    if (!item.isHidden()) {
                        item.setLocation(this.x + 1.0f, this.y + (height += 15.0f));
                        item.setHeight(15);
                        item.setWidth(this.width - 9);
                        item.drawScreen(mouseX, mouseY, partialTicks);
                    }
                    item.update();
                }
            }
        }
        String a = module.getDescription().split(" ",1).toString();
        String b = module.getDescription().split(" ",1).toString();
        String c = module.getDescription().split(" ",1).toString();
        String d = module.getDescription().split(" ",1).toString();
        String e = module.getDescription().split(" ",1).toString();
        String f = module.getDescription().split(" ",1).toString();
        String g = module.getDescription().split(" ",1).toString();
        String h = module.getDescription().split(" ",1).toString();
        String i = module.getDescription().split(" ",1).toString();
        String j = module.getDescription().split(" ",1).toString();
        String k = module.getDescription().split(" ",1).toString();

        
        if (isHovering(mouseX, mouseY)) {
            Claudius.textManager.drawString(a, mouseX+10, mouseY, ColorUtil.toRGBA(0, 0, 0, 255), false);
            Claudius.textManager.drawString(b, mouseX+10, mouseY + 5, ColorUtil.toRGBA(0, 0, 0, 255), false);
            Claudius.textManager.drawString(c, mouseX+10, mouseY + 10, ColorUtil.toRGBA(0, 0, 0, 255), false);
            Claudius.textManager.drawString(d, mouseX+10, mouseY + 15, ColorUtil.toRGBA(0, 0, 0, 255), false);
            Claudius.textManager.drawString(e, mouseX+10, mouseY + 20, ColorUtil.toRGBA(0, 0, 0, 255), false);
            Claudius.textManager.drawString(f, mouseX+10, mouseY, ColorUtil.toRGBA(0, 0, 0, 255), false);
            Claudius.textManager.drawString(g, mouseX+10, mouseY, ColorUtil.toRGBA(0, 0, 0, 255), false);
            Claudius.textManager.drawString(h, mouseX+10, mouseY, ColorUtil.toRGBA(0, 0, 0, 255), false);
            Claudius.textManager.drawString(i, mouseX+10, mouseY, ColorUtil.toRGBA(0, 0, 0, 255), false);
            Claudius.textManager.drawString(j, mouseX+10, mouseY, ColorUtil.toRGBA(0, 0, 0, 255), false);
            Claudius.textManager.drawString(k, mouseX+10, mouseY, ColorUtil.toRGBA(0, 0, 0, 255), false);
           
        }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        if (!this.items.isEmpty()) {
            if (mouseButton == 1 && this.isHovering(mouseX, mouseY)) {
                this.subOpen = !this.subOpen;
                mc.getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0f));
            }
            if (this.subOpen) {
                for (Item item : this.items) {
                    if (item.isHidden()) continue;
                    item.mouseClicked(mouseX, mouseY, mouseButton);
                }
            }
        }
    }

    @Override
    public void onKeyTyped(char typedChar, int keyCode) {
        super.onKeyTyped(typedChar, keyCode);
        if (!this.items.isEmpty() && this.subOpen) {
            for (Item item : this.items) {
                if (item.isHidden()) continue;
                item.onKeyTyped(typedChar, keyCode);
            }
        }
    }

    @Override
    public int getHeight() {
        if (this.subOpen) {
            int height = 14;
            for (Item item : this.items) {
                if (item.isHidden()) continue;
                height += item.getHeight() + 1;
            }
            return height + 2;
        }
        return 14;
    }

    public Module getModule() {
        return this.module;
    }

    @Override
    public void toggle() {
        this.module.toggle();
    }

    @Override
    public boolean getState() {
        return this.module.isEnabled();
    }
}

