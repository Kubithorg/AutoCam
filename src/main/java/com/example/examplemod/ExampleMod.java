package com.example.examplemod;

import java.io.File;
import java.io.IOException;

import org.opencv.core.Core;
import org.opencv.core.Size;
import org.opencv.videoio.VideoWriter;

import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = ExampleMod.MODID, version = ExampleMod.VERSION)
public class ExampleMod
{
    public static final String MODID = "examplemod";
    public static final String VERSION = "1.0";
    
    public static VideoWriter vidWriter;
    
    @EventHandler
    public void preInit(final FMLPreInitializationEvent e)
    {
    	System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    	MinecraftForge.EVENT_BUS.register(new ACEventHandler());
    }
    
    @EventHandler
    public void init(final FMLInitializationEvent e)
    {
    	Minecraft mc = Minecraft.getMinecraft();
    	vidWriter = new VideoWriter();
		boolean success = vidWriter.open("D:\\tmp\\test.avi",
    			VideoWriter.fourcc('M', 'J', 'P', 'G'),
    			30,
    			new Size(mc.displayWidth, mc.displayHeight));
    	if (!success) {
    		System.out.println("[AutoCam] Error !");
    		FMLCommonHandler.instance().exitJava(1, false);
    	}
        System.out.println("[AutoCam] Init done");
    }
}
