package com.example.examplemod;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import org.opencv.core.CvType;
import org.opencv.core.Mat;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ScreenShotHelper;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class ACEventHandler {
	public static int counter = 0;
	public static int finalMaxCount = 600;

	@SubscribeEvent
	public void renderTick(final TickEvent.RenderTickEvent e) {
		if (counter < finalMaxCount) {
			Minecraft mc = Minecraft.getMinecraft();
			// ITextComponent text = ScreenShotHelper.saveScreenshot(mc.mcDataDir,
			// mc.displayWidth, mc.displayHeight, mc.getFramebuffer());
			BufferedImage image = ScreenShotHelper.createScreenshot(mc.displayWidth, mc.displayHeight,
					mc.getFramebuffer());
			ExampleMod.vidWriter.write(img2Mat(image));
			counter++;
		} else if (counter == finalMaxCount) {
			System.out.println("Counter run out !");
			ExampleMod.vidWriter.release();
			counter++;
		}
	}

	protected Mat img2Mat(BufferedImage in) {
		Mat out;
		byte[] data;
		int r, g, b;

		if (in.getType() == BufferedImage.TYPE_INT_RGB) {
			out = new Mat(in.getHeight(), in.getWidth(), CvType.CV_8UC3);
			data = new byte[in.getWidth() * in.getHeight() * (int) out.elemSize()];
			int[] dataBuff = in.getRGB(0, 0, in.getWidth(), in.getHeight(), null, 0, in.getWidth());
			for (int i = 0; i < dataBuff.length; i++) {
				data[i * 3] = (byte) ((dataBuff[i] >> 0) & 0xFF);
				data[i * 3 + 1] = (byte) ((dataBuff[i] >> 8) & 0xFF);
				data[i * 3 + 2] = (byte) ((dataBuff[i] >> 16) & 0xFF);
			}
		} else {
			out = new Mat(in.getHeight(), in.getWidth(), CvType.CV_8UC1);
			data = new byte[in.getWidth() * in.getHeight() * (int) out.elemSize()];
			int[] dataBuff = in.getRGB(0, 0, in.getWidth(), in.getHeight(), null, 0, in.getWidth());
			for (int i = 0; i < dataBuff.length; i++) {
				r = (byte) ((dataBuff[i] >> 0) & 0xFF);
				g = (byte) ((dataBuff[i] >> 8) & 0xFF);
				b = (byte) ((dataBuff[i] >> 16) & 0xFF);
				data[i] = (byte) ((0.21 * r) + (0.71 * g) + (0.07 * b));
			}
		}
		out.put(0, 0, data);
		return out;
	}
}
