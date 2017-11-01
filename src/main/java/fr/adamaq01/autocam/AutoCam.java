package fr.adamaq01.autocam;

import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.opencv.core.Core;

@Mod(modid = AutoCam.MODID, version = AutoCam.VERSION, clientSideOnly = true)
@SideOnly(value = Side.CLIENT)
public class AutoCam {

    public static final String MODID = "autocam";
    public static final String VERSION = "1.0";

    private Camera camera;

    public AutoCam() {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        this.camera = new Camera();
    }

    @EventHandler
    public void preInit(final FMLPreInitializationEvent e) {
    	MinecraftForge.EVENT_BUS.register(new ACEventHandler(camera));
    }

    @EventHandler
    public void serverLoad(FMLServerStartingEvent event) {
        event.registerServerCommand(new CameraCommand(camera));
    }
}
