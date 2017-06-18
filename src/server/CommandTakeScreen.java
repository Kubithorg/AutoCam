package fr.nonimad.command;

import com.mojang.authlib.GameProfile;

import fr.nonimad.network.screenbot.NetHandlerScreenServer;
import fr.nonimad.network.screenbot.server.S00PacketAskToScreen;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Vec3;
import net.minecraft.world.WorldSettings.GameType;
import net.minecraft.world.storage.WorldInfo;

public class CommandTakeScreen extends CommandBase
{
    public String getCommandName()
    {
        return "takescreen";
    }

    /**
     * Return the required permission level for this command.
     */
    public int getRequiredPermissionLevel()
    {
        return 2;
    }

    public String getCommandUsage(ICommandSender commandSender)
    {
        return "Usage : pseudo x y z yaw pitch";
    }

    public void processCommand(ICommandSender commandSender, String[] args)
    {
    	if (args.length != 6)
        {
            throw new WrongUsageException("Usage : pseudo x y z yaw pitch", new Object[0]);
        }
        else
        {
        	/*for(int i = 0; i < 360; i += 30) {
        		String[] newArgs = args.clone();
        		newArgs[4] = String.valueOf(i);
        		MinecraftServer.getServer().screenQueue.add(newArgs);
        	}*/
        	MinecraftServer.getServer().screenQueue.add(args);
        }
    }
}
