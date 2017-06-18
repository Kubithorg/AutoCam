package fr.nonimad.network.screenbot;

import fr.nonimad.network.screenbot.client.C00PacketScreenData;
import io.netty.buffer.ByteBuf;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import net.minecraft.network.EnumConnectionState;
import net.minecraft.network.NetworkManager;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.IChatComponent;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NetHandlerScreenServer implements INetHandlerScreenServer {
	private static final Logger logger = LogManager.getLogger();
	private static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss");
	
	private final MinecraftServer serverController;

    private ByteBuf data;
    
    public NetHandlerScreenServer(MinecraftServer sc)
    {
        this.serverController = sc;
    }

	@Override
	public void processScreenData(C00PacketScreenData p) {
		//data.writeBytes(p.data);
		if(p.isTheEnd) {
			File dir = new File("screenshots");
			dir.mkdir();
			
			try {
				Files.write(getTimestampedPNGFileForDirectory(new File("screenshots")).toPath(), p.data);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			/*try
	        {
				File serverDir = new File(serverController.getFolderName(), "screenshots");
				serverDir.mkdir();
	            
				File screenFile;

				String fileName = null;
	            if (fileName == null)
	            	screenFile = getTimestampedPNGFileForDirectory(serverDir);
	            else
	            	screenFile = new File(serverDir, fileName);

	            ImageIO.write(var7, "png", var12);
	            ChatComponentText var13 = new ChatComponentText(var12.getName());
	            var13.getChatStyle().setChatClickEvent(new ClickEvent(ClickEvent.Action.OPEN_FILE, var12.getAbsolutePath()));
	            var13.getChatStyle().setUnderlined(Boolean.valueOf(true));
	            
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
	            var13.getChatStyle().setUnderlined(Boolean.valueOf(true));
	        }
	        catch (Exception var11)
	        {
	            logger.warn("Couldn\'t save screenshot", var11);
	            return;
	        }*/
		}
	}

	@Override
	public void onDisconnect(IChatComponent chat) {}

	@Override
	public void onConnectionStateTransition(EnumConnectionState p_147232_1_, EnumConnectionState p_147232_2_) {}

	@Override
	public void onNetworkTick() {}
	
    public static File getTimestampedPNGFileForDirectory(File p_74290_0_)
    {
        String var2 = dateFormat.format(new Date()).toString();
        int var3 = 1;

        while (true)
        {
            File var1 = new File(p_74290_0_, var2 + (var3 == 1 ? "" : "_" + var3) + ".png");

            if (!var1.exists())
            {
                return var1;
            }

            ++var3;
        }
    }
}
