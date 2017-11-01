package fr.adamaq01.autocam;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Adamaq01 on 31/10/2017.
 */
@SideOnly(value = Side.CLIENT)
public class CameraCommand implements ICommand {

    private Camera camera;
    private List<String> aliases;

    public CameraCommand(Camera camera) {
        this.camera = camera;
        this.aliases = new ArrayList<String>();
        this.aliases.add("cam");
    }

    @Override
    public String getName() {
        return "camera";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "camera <duration> <framerate>";
    }

    @Override
    public List<String> getAliases() {
        return this.aliases;
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        try {
            camera.start("video", args[0] != null ? Integer.valueOf(args[0]) : 10, args[1] != null ? Integer.valueOf(args[1]) : 60);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
        return sender instanceof EntityPlayer;
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        return null;
    }

    @Override
    public boolean isUsernameIndex(String[] args, int index) {
        return false;
    }

    @Override
    public int compareTo(ICommand o) {
        return 0;
    }
}
