package fr.adamaq01.autocam;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ScreenShotHelper;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(value = Side.CLIENT)
public class ACEventHandler {

    private Camera camera;

    public ACEventHandler(Camera camera) {
        this.camera = camera;
    }

    @SubscribeEvent
    public void tick(TickEvent.RenderTickEvent event) {
        if(!camera.shouldCapture()) {
            return;
        }
        if (event.phase == TickEvent.Phase.START) {
            return;
        }
        camera.setLastImage(ScreenShotHelper.createScreenshot(Minecraft.getMinecraft().displayWidth, Minecraft.getMinecraft().displayHeight, Minecraft.getMinecraft().getFramebuffer()));
    }
}
