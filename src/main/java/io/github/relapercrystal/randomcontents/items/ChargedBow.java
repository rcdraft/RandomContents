// Probably contains code from net.minecraft.item.bow
// I'll do my best to provide an clean-room, and I do not use copy-paste
package io.github.relapercrystal.randomcontents.items;

import java.util.function.Consumer;
import java.util.function.Predicate;

import io.github.relapercrystal.randomcontents.ModMain;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;

public class ChargedBow extends Item {
    public ChargedBow(Settings settings) {
        super(settings);
        this.addPropertyGetter(new Identifier("randomcontents", "pull"), (stack, world, entity) -> {
            if(entity == null)
            {
                return 0.0F;
            }
            else
            {
                return entity.getActiveItem().getItem() != ModMain.CHARGED_BOW ? 0.0F : (float)(stack.getMaxUseTime() - entity.getItemUseTimeLeft()) / 20.0F;
            }
        });
        this.addPropertyGetter(new Identifier("randomcontents", "pulling"), (stack, world, entity) -> {
            return entity != null && entity.isUsingItem() && entity.getActiveItem() == stack ? 1.0F : 0.0F;
        });
    }

    @Override
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks)
    {
        if (user instanceof PlayerEntity) {
            PlayerEntity playerEntity = (PlayerEntity)user;
            boolean infinity = playerEntity.abilities.creativeMode || EnchantmentHelper.getLevel(Enchantments.INFINITY, stack) > 0;
            ItemStack arrow = playerEntity.getArrowType(stack);
            if (!arrow.isEmpty() || infinity)
            {
                if(arrow.isEmpty()) {
                    arrow = new ItemStack(Items.ARROW);
                }
            }

            int useTime = this.getMaxUseTime(stack);
            float progress = getPullProgress(useTime);

            if((double)progress >= 0.1D){
                boolean isArrow = infinity && arrow.getItem() == Items.ARROW;
                if(!world.isClient)
                {
                    ArrowItem arrowItem = (ArrowItem)((ArrowItem)(arrow.getItem() instanceof ArrowItem ? arrow.getItem() : Items.ARROW));
                    ProjectileEntity project = arrowItem.createArrow(world, arrow, playerEntity);
                    project.setProperties(playerEntity, playerEntity.pitch, playerEntity.yaw, 0.0F, progress * 4.0F, 1.0F);

                    if (progress == 1.0F) 
                    {
                        project.setCritical(true);
                    }

                    int enchant = EnchantmentHelper.getLevel(Enchantments.POWER, stack);
                    if(enchant > 0)
                    {
                        project.setDamage(project.getDamage() + (double)enchant * 0.6D + 0.5D);
                    }

                    int punch = EnchantmentHelper.getLevel(Enchantments.PUNCH, stack);
                    if(punch > 0)
                    {
                        project.setPunch(punch + 1);
                    }

                    if(EnchantmentHelper.getLevel(Enchantments.FLAME, stack) > 0)
                    {
                        project.setOnFireFor(150);
                    }

                    stack.damage(1, (LivingEntity)playerEntity, (Consumer)((p) -> {
                        ((LivingEntity) p).sendToolBreakStatus(playerEntity.getActiveHand());
                     }));
                    
                     if (isArrow || playerEntity.abilities.creativeMode && (arrow.getItem() == Items.SPECTRAL_ARROW || arrow.getItem() == Items.TIPPED_ARROW)) 
                     {
                        project.pickupType = ProjectileEntity.PickupPermission.CREATIVE_ONLY;
                     }

                     world.spawnEntity(project);
                }
                world.playSound((PlayerEntity)null, playerEntity.getX(), playerEntity.getY(), playerEntity.getZ(), SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.PLAYERS, 1.0F, 1.0f / (RANDOM.nextFloat() * 0.4F + 1.2F));

                if (!isArrow && !playerEntity.abilities.creativeMode) {
                    arrow.decrement(1);
                    if (arrow.isEmpty()) {
                       playerEntity.inventory.removeOne(arrow);
                    }
                 }
            }
        }
    }

    @Override
    public int getMaxUseTime(ItemStack stack)
    {
        return 73000;
    }

    private float getPullProgress(int useTime) {
        float f = (float)useTime / 20.0F;
        f = (f * f + f * 2.0F) / 3.0F;
        if (f > 1.0F) 
        {
            f = 1.0F;
        }

        return f;
    }

    @Override
    public UseAction getUseAction(ItemStack stack)
    {
        return UseAction.BOW;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand)
    {
        ItemStack stack = user.getStackInHand(hand);
        boolean isArrow = !user.getArrowType(stack).isEmpty();
        if(!user.abilities.creativeMode && !isArrow)
        {
            return TypedActionResult.fail(stack);
        }
        else
        {
            user.setCurrentHand(hand);
            return TypedActionResult.consume(stack);
        }
    }
}