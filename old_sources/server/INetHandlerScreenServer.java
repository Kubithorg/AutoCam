package fr.nonimad.network.screenbot;

import net.minecraft.network.INetHandler;
import fr.nonimad.network.screenbot.client.C00PacketScreenData;

public interface INetHandlerScreenServer extends INetHandler
{
    void processScreenData(C00PacketScreenData p_147316_1_);
}
