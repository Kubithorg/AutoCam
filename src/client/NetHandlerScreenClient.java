package fr.nonimad.network.screenbot;

import io.netty.util.concurrent.GenericFutureListener;

import java.io.File;
import java.nio.IntBuffer;
import java.nio.file.Files;

import net.minecraft.client.Minecraft;
import net.minecraft.network.EnumConnectionState;
import net.minecraft.network.NetworkManager;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.ScreenShotHelper;
import fr.nonimad.network.screenbot.client.C00PacketScreenData;
import fr.nonimad.network.screenbot.server.S00PacketAskToScreen;

public class NetHandlerScreenClient implements INetHandlerScreenClient {
    private final Minecraft mc;
    private final NetworkManager networkManager;

    private static IntBuffer pixelBuffer;
    private static int[] pixelValues;
    
    public NetHandlerScreenClient(NetworkManager nm, Minecraft mc)
    {
        this.mc = mc;
        this.networkManager = nm;
    }

	@Override
	public void handleAskToScreen(S00PacketAskToScreen p) {
		//Minecraft.getMinecraft().thePlayer.setPositionAndRotation(p.pos.xCoord, p.pos.yCoord, p.pos.zCoord, p.yaw, p.pitch);

		ScreenShotHelper.saveScreenshot(mc.mcDataDir, "serverScreen", mc.displayWidth, mc.displayHeight, mc.getFramebuffer());
		File screen = new File(mc.mcDataDir, "screenshots/serverScreen.png");
		
		try {
			byte[] data = Files.readAllBytes(screen.toPath());
			C00PacketScreenData packet = new C00PacketScreenData(0, data, true);
			this.networkManager.scheduleOutboundPacket(packet, new GenericFutureListener[0]);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		screen.delete();
		
	    /*String screenName = null;
	    int requestedWidthInPixels = 0;
	    int requestedHeightInPixels = 0;
	    Framebuffer frameBuffer = null;

        if (OpenGlHelper.isFramebufferEnabled())
        {
            requestedWidthInPixels = frameBuffer.framebufferTextureWidth;
            requestedHeightInPixels = frameBuffer.framebufferTextureHeight;
        }

        int var6 = requestedWidthInPixels * requestedHeightInPixels;

        if (pixelBuffer == null || pixelBuffer.capacity() < var6)
        {
            pixelBuffer = BufferUtils.createIntBuffer(var6);
            pixelValues = new int[var6];
        }

        GL11.glPixelStorei(GL11.GL_PACK_ALIGNMENT, 1);
        GL11.glPixelStorei(GL11.GL_UNPACK_ALIGNMENT, 1);
        pixelBuffer.clear();

        if (OpenGlHelper.isFramebufferEnabled())
        {
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, frameBuffer.framebufferTexture);
            GL11.glGetTexImage(GL11.GL_TEXTURE_2D, 0, GL12.GL_BGRA, GL12.GL_UNSIGNED_INT_8_8_8_8_REV, pixelBuffer);
        }
        else
        {
            GL11.glReadPixels(0, 0, requestedWidthInPixels, requestedHeightInPixels, GL12.GL_BGRA, GL12.GL_UNSIGNED_INT_8_8_8_8_REV, pixelBuffer);
        }

        pixelBuffer.get(pixelValues);
        TextureUtil.func_147953_a(pixelValues, requestedWidthInPixels, requestedHeightInPixels);
        
        BufferedImage var7 = null;

        if (OpenGlHelper.isFramebufferEnabled())
        {
            var7 = new BufferedImage(frameBuffer.framebufferWidth, frameBuffer.framebufferHeight, 1);
            int var8 = frameBuffer.framebufferTextureHeight - frameBuffer.framebufferHeight;

            for (int var9 = var8; var9 < frameBuffer.framebufferTextureHeight; ++var9)
            {
                for (int var10 = 0; var10 < frameBuffer.framebufferWidth; ++var10)
                {
                    var7.setRGB(var10, var9 - var8, pixelValues[var9 * frameBuffer.framebufferTextureWidth + var10]);
                }
            }
        }
        else
        {
            var7 = new BufferedImage(requestedWidthInPixels, requestedHeightInPixels, 1);
            var7.setRGB(0, 0, requestedWidthInPixels, requestedHeightInPixels, pixelValues, 0, requestedWidthInPixels);
        }
        
        File var12;

        if (screenName == null)
        {
            var12 = getTimestampedPNGFileForDirectory(var5);
        }
        else
        {
            var12 = new File(var5, screenName);
        }

        ImageIO.write(var7, "png", var12);
        ChatComponentText var13 = new ChatComponentText(var12.getName());
        var13.getChatStyle().setChatClickEvent(new ClickEvent(ClickEvent.Action.OPEN_FILE, var12.getAbsolutePath()));
        var13.getChatStyle().setUnderlined(Boolean.valueOf(true));*/
	}

	@Override
	public void onDisconnect(IChatComponent chat) {}

	@Override
	public void onConnectionStateTransition(EnumConnectionState p_147232_1_, EnumConnectionState p_147232_2_) {}

	@Override
	public void onNetworkTick() {}
}
