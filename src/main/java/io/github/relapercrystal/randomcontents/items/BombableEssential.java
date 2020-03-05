package io.github.relapercrystal.randomcontents.items;

import java.util.List;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion.DestructionType;

public class BombableEssential extends Item {
    public BombableEssential(Settings settings) {
        super(settings);
    }
    
    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity playerEntity, Hand hand)
    {
        ItemStack stackInHand = playerEntity.getStackInHand(hand);
        if(!world.isClient)
        {
            world.createExplosion((Entity)null, playerEntity.getX(), playerEntity.getBodyY(0.0721D), playerEntity.getZ(), 2.2F, DestructionType.BREAK);
            playerEntity.sendMessage(new TranslatableText("item.randomcontents.bombable_essential.break"));
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
        tooltip.add(new TranslatableText("item.randomcontents.bombable_essential.tooltip1"));
        tooltip.add(new TranslatableText("item.randomcontents.bombable_essential.tooltip2"));
    }
}