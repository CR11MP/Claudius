package me.crimp.claudius.mod.modules.fake;

import me.crimp.claudius.mod.command.Command;
import me.crimp.claudius.mod.modules.Module;
import net.minecraft.client.Minecraft;

public class AntiChainPop extends Module {
    public AntiChainPop() {
        super("AntiChainPop", "AntiChainPop", Category.EXPLOIT, true, false, false);
    }

    @Override
    public void onEnable() {
        String serverName = Minecraft.getMinecraft().currentServerData.serverIP;
        if (mc.isSingleplayer()) {
            Command.sendSilentMessage("single");
        }

        else {
            Command.sendMessage("if ur on " + serverName + " this wont help u");
        }
    }
}