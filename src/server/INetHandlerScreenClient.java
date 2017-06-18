package fr.nonimad.network.screenbot;

import net.minecraft.network.INetHandler;
import fr.nonimad.network.screenbot.server.S00PacketAskToScreen;

public interface INetHandlerScreenClient extends INetHandler
{
    void handleAskToScreen(S00PacketAskToScreen p_147389_1_);
}
