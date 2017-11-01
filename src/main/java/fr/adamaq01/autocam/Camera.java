package fr.adamaq01.autocam;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.Sys;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.videoio.VideoWriter;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by Adamaq01 on 31/10/2017.
 */
@SideOnly(value = Side.CLIENT)
public class Camera {

    private boolean shouldCapture = false;
    private int duration = 0;
    private int framerate = 0;
    private long startTime = 0L;

    private VideoWriter videoWriter;
    private File file;

    private BufferedImage lastImage = null;

    public Camera() {
        this.videoWriter = new VideoWriter();
    }

    public void start(String file, int duration, int framerate) throws IOException {
        this.file = File.createTempFile(file, ".avi");
        this.videoWriter.open(this.file.getAbsolutePath(), VideoWriter.fourcc('M', 'J', 'P', 'G'), framerate, new Size(Minecraft.getMinecraft().displayWidth, Minecraft.getMinecraft().displayHeight));
        this.duration = duration;
        this.framerate = framerate;
        this.startTime = Sys.getTime();
        this.shouldCapture = true;

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                while (Sys.getTime() - startTime < getDuration() * 1000 && shouldCapture == true) {
                    try {
                        long start = Sys.getTime();
                        if(lastImage != null)
                            capture(lastImage);
                        try {
                            if(start + (1000/getFramerate()) - Sys.getTime() > 0)
                                Thread.sleep(start + (1000/getFramerate()) - Sys.getTime());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    stop();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };

        Thread captureThread = new Thread(runnable);
        captureThread.setName("Capture Thread");
        captureThread.start();
    }

    public void stop() throws IOException {
        this.shouldCapture = false;
        this.duration = 0;
        this.framerate = 0;
        this.startTime = 0L;
        this.videoWriter.release();
        if(file != null) file.renameTo(new File(Minecraft.getMinecraft().mcDataDir.getAbsolutePath() + File.separator + file.getName()));
        this.file = null;
        this.lastImage = null;
    }

    public void capture(BufferedImage image) throws IOException {
        this.videoWriter.write(imageToMat(image));
    }

    public boolean shouldCapture() {
        return shouldCapture;
    }

    public int getFramerate() {
        return framerate;
    }

    public int getDuration() {
        return duration;
    }

    public void setLastImage(BufferedImage lastImage) {
        this.lastImage = lastImage;
    }

    protected Mat imageToMat(BufferedImage in) {
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
