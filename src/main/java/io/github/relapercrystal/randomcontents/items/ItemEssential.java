package io.github.relapercrystal.randomcontents.items;

import java.util.List;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class ItemEssential extends Item {
    public ItemEssential(Settings settings) {
        super(settings);
    }
    
    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity playerEntity, Hand hand)
    {
        ItemStack stackInHand = playerEntity.getStackInHand(hand);
        if(world.isClient)
        {
            playerEntity.playSound(SoundEvents.BLOCK_GLASS_BREAK, 1.0F, 1.0F);
        }
        else
        {
            playerEntity.sendMessage(new TranslatableText("item.randomcontents.essential.break"));
            int count = playerEntity.getStackInHand(hand).getCount();
            if(count != 0)
            {
                stackInHand.setCount(count - 1);
            }
        }
        
        return new TypedActionResult<>(ActionResult.SUCCESS, playerEntity.getStackInHand(hand));
    }

    @Override
    public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext) {
        tooltip.add(new TranslatableText("item.randomcontents.essential.tooltip"));
    }
}