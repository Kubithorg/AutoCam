package fr.nonimad.network.screenbot.client;

import java.io.IOException;

import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayServer;

public class C00PacketScreenData extends Packet
{
	public int dataSize;
	public byte[] data;
	public boolean isTheEnd;

    public C00PacketScreenData(int dataSize, byte[] data, boolean isTheEnd)
    {
        this.data = data;
        this.dataSize = data.length;
        this.isTheEnd = isTheEnd;
    }

    /**
     * Reads the raw packet data from the data stream.
     */
    public void readPacketData(PacketBuffer p) throws IOException
    {
        this.dataSize = p.readInt();
        this.data = p.readBytes(dataSize).array();
        this.isTheEnd = p.readBoolean();
    }

    /**
     * Writes the raw packet data to the data stream.
     */
    public void writePacketData(PacketBuffer p) throws IOException
    {
    	p.writeInt(this.dataSize);
        p.writeBytes(this.data);
        p.writeBoolean(this.isTheEnd);
    }

    public void processPacket(INetHandlerPlayServer netHandler)
    {
    	netHandler.processScreenData(this);
    }

    public void processPacket(INetHandler netHandler)
    {
        this.processPacket((INetHandlerPlayServer)netHandler);
    }
}
