package de.teamlapen.vampirism;

import java.util.HashMap;
import java.util.List;

import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChatComponentText;
import de.teamlapen.vampirism.coremod.CoreHandler;
import de.teamlapen.vampirism.entity.EntityVampireHunter;
import de.teamlapen.vampirism.entity.player.VampirePlayer;
import de.teamlapen.vampirism.util.Helper;
import de.teamlapen.vampirism.util.Logger;

/**
 * Basic command on which all other commands should depend on
 * 
 * @author Max
 * 
 */
public class TestCommand implements ICommand {
	public static void sendMessage(ICommandSender target, String message) {
		String[] lines = message.split("\\n");
		for (String line : lines) {
			target.addChatMessage(new ChatComponentText(line));
		}

	}

	private HashMap activePotionsMap;

	@Override
	public List addTabCompletionOptions(ICommandSender p_71516_1_, String[] p_71516_2_) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean canCommandSenderUseCommand(ICommandSender p_71519_1_) {
		return true;
	}

	@Override
	public int compareTo(Object arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List getCommandAliases() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getCommandName() {
		return "test";
	}

	@Override
	public String getCommandUsage(ICommandSender p_71518_1_) {
		return "/test";
	}

	public boolean isPotionActive(Potion p_70644_1_) {
		if (CoreHandler.shouldOverrideNightVision(this, p_70644_1_)) {
			return true;
		}
		return this.activePotionsMap.containsKey(Integer.valueOf(p_70644_1_.id));
	}

	@Override
	public boolean isUsernameIndex(String[] p_82358_1_, int p_82358_2_) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void processCommand(ICommandSender sender, String[] param) {
		if (sender instanceof EntityPlayer) {
			EntityPlayer p = (EntityPlayer) sender;
			List t=p.worldObj.getEntitiesWithinAABB(EntityVampireHunter.class, AxisAlignedBB.getBoundingBox(p.posX-10,p.posY-5,p.posZ-10,p.posX+10,p.posY+5,p.posZ+10));
			if(t!=null){
				for(Object o:t){
					EntityVampireHunter eh=(EntityVampireHunter)o;
					eh.setAgressive(!eh.isAgressive());
				}
			}
			// -----------------
			if (param.length > 0) {
				try {
					VampirePlayer.get(p).setLevel(Integer.parseInt(param[0]));
				} catch (NumberFormatException e) {
					Logger.e("Testcommand", param[0] + " is no Integer");
				}
			} else {
				VampirePlayer.get(p).levelUp();
			}

			if (VampirePlayer.get(p).getLevel() == 1) {
				sendMessage(sender, "You are a vampire now");
			}
		}

	}

}
