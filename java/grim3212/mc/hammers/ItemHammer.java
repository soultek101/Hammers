package grim3212.mc.hammers;

import grim3212.mc.core.Grim3212Core;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.world.World;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;

public class ItemHammer extends Item {

	public ItemHammer(ToolMaterial toolMaterial) {
		super();
		maxStackSize = 1;
		setCreativeTab(Grim3212Core.tabGrimStuff);
		this.setMaxDamage(toolMaterial.getMaxUses());
	}

	public boolean onBlockStartBreak(ItemStack itemstack, int i, int j, int k, EntityPlayer entityplayer) {
		World world = entityplayer.worldObj;
		int damage = 0;

		Side side = FMLCommonHandler.instance().getEffectiveSide();
		if (side == Side.SERVER && !entityplayer.capabilities.isCreativeMode) {
			entityplayer.addStat(StatList.mineBlockStatArray[world.getBlock(i, j, k).getIdFromBlock(world.getBlock(i, j, k))], 1);
			entityplayer.addExhaustion(0.025F);
			world.playAuxSFX(2001, i, j, k, world.getBlock(i, j, k).getIdFromBlock(world.getBlock(i, j, k)) + (world.getBlockMetadata(i, j, k) << 12));
			if (itemstack.getItemDamage() <= itemstack.getMaxDamage()) {
				world.setBlockToAir(i, j, k);
			}
			itemstack.damageItem(1, entityplayer);
			if (itemstack.getItemDamage() >= itemstack.getMaxDamage()) {
				entityplayer.destroyCurrentEquippedItem();
				world.playSoundAtEntity(entityplayer, "random.break", 1.0F, 1.0F);
				return false;
			}
			return true;
		} else if (side == Side.CLIENT) {
			return true;
		} else {
			return false;
		}

	}

	@Override
	public boolean canHarvestBlock(Block par1Block, ItemStack itemStack) {
		return true;
	}

	public boolean isFull3D() {
		return true;
	}

	@Override
	public float getDigSpeed(ItemStack itemstack, Block block, int metadata) {
		return 80F;
	}
}
