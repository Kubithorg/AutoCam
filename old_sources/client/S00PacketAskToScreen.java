package fr.nonimad.network.screenbot.server;

import java.io.IOException;

import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.Vec3;

public class S00PacketAskToScreen extends Packet
{
	public Vec3 pos;
	public float yaw;
	public float pitch;

    public S00PacketAskToScreen() {}

    public S00PacketAskToScreen(Vec3 pos, float yaw, float pitch)
    {
        this.pos = pos;
        this.yaw = yaw;
        this.pitch = pitch;
    }

    /**
     * Reads the raw packet data from the data stream.
     */
    public void readPacketData(PacketBuffer p) throws IOException
    {
    	this.pos = Vec3.createVectorHelper(p.readDouble(), p.readDouble(), p.readDouble());
    			
    	this.yaw = p.readFloat();
    	this.pitch = p.readFloat();
    }

    /**
     * Writes the raw packet data to the data stream.
     */
    public void writePacketData(PacketBuffer p) throws IOException
    {
    	p.writeDouble(pos.xCoord);
    	p.writeDouble(pos.yCoord);
    	p.writeDouble(pos.zCoord);
    	
    	p.writeFloat(yaw);
    	p.writeFloat(pitch);
    }

    public void processPacket(INetHandlerPlayClient netHandler)
    {
    	netHandler.handleAskToScreen(this);
    }

    /**
     * If true, the network manager will process the packet immediately when received, otherwise it will queue it for
     * processing. Currently true for: Disconnect, LoginSuccess, KeepAlive, ServerQuery/Info, Ping/Pong
     */
    public boolean hasPriority()
    {
        return true;
    }

    public void processPacket(INetHandler netHandler)
    {
        this.processPacket((INetHandlerPlayClient)netHandler);
    }
}
