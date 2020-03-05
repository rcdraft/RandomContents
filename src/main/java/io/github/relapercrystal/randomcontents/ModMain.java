/*
	Random Contents
    Copyright (C) 2019  RelaperCrystal

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <https://www.gnu.org/licenses/>.
*/
package io.github.relapercrystal.randomcontents;

import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import org.apache.logging.log4j.LogManager;

import io.github.relapercrystal.randomcontents.blocks.BlockEssential;
import io.github.relapercrystal.randomcontents.blocks.InvisibleLightSource;
import io.github.relapercrystal.randomcontents.items.HandGrinder;
import io.github.relapercrystal.randomcontents.items.ItemEssential;
// import io.github.relapercrystal.randomcontents.items.materials.ChargedDiamond;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
// import net.minecraft.item.PickaxeItem;

public class ModMain implements ModInitializer {

    public static final ItemGroup CRYSTAL_GROUP = FabricItemGroupBuilder.build(new Identifier("randomcontents","crystal_group"), () -> new ItemStack(Items.CRAFTING_TABLE));    
    public static final ItemEssential ESSENTIAL = new ItemEssential(new Item.Settings().group(CRYSTAL_GROUP));
    public static final Item BURNABLE_ESSENTIAL = new Item(new Item.Settings().group(CRYSTAL_GROUP));
    public static final BlockEssential ESSENTIAL_BLOCK = new BlockEssential(FabricBlockSettings.of(Material.GLASS).build().strength(0.3F, 0.3F).nonOpaque());
    public static final Block COLOR_PANEL = new Block(FabricBlockSettings.of(Material.WOOL).build());
    public static final HandGrinder HAND_GRINDER = new HandGrinder(new Item.Settings().group(CRYSTAL_GROUP).maxDamage(300));
    public static final Item ENHANCED_GOLD_INGOT = new Item(new Item.Settings().group(CRYSTAL_GROUP).recipeRemainder(Items.GOLD_INGOT));
    public static final InvisibleLightSource INVISIBLE_LIGHT = new InvisibleLightSource(FabricBlockSettings.of(Material.GLASS).nonOpaque().noCollision().breakInstantly().lightLevel(14).build());
    public static final Item COPY_ENGERY_PROCESSOR = new Item(new Item.Settings().group(CRYSTAL_GROUP));
    public static final Item ENHANCED_GOLD_WIRE = new Item(new Item.Settings().group(CRYSTAL_GROUP));
    public static final Item COPY_ENGERY_RECEIVER = new Item(new Item.Settings().group(CRYSTAL_GROUP));
    public static final Item CHARGED_BOW = new Item(new Item.Settings().maxDamage(684).group(CRYSTAL_GROUP));
    // public static final PickaxeItem CHARGED_DIAMOND_PICKAXE = new PickaxeItem(ChargedDiamond);

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		LogManager.getLogger().info("RandomContents, started");
        InitItem();
        InitBlock();

        // Register Burnable Essential as Fuel
        FuelRegistry.INSTANCE.add(BURNABLE_ESSENTIAL, 10000);
    }

    // Register Blocks
    public void InitBlock()
    {
        Registry.register(Registry.BLOCK, new Identifier("randomcontents", "essential_block"), ESSENTIAL_BLOCK);
        Registry.register(Registry.ITEM, new Identifier("randomcontents", "essential_block"), new BlockItem(ESSENTIAL_BLOCK, new Item.Settings().group(CRYSTAL_GROUP)));
        Registry.register(Registry.BLOCK, new Identifier("randomcontents", "color_panel"), COLOR_PANEL);
        Registry.register(Registry.ITEM, new Identifier("randomcontents", "color_panel"), new BlockItem(COLOR_PANEL, new Item.Settings().group(CRYSTAL_GROUP)));
        Registry.register(Registry.BLOCK, new Identifier("randomcontents", "invisible_light_source"), INVISIBLE_LIGHT);
        Registry.register(Registry.ITEM, new Identifier("randomcontents", "invisible_light_source"), new BlockItem(INVISIBLE_LIGHT, new Item.Settings().group(CRYSTAL_GROUP)));
    }

    public void InitItem()
    {
        Registry.register(Registry.ITEM, new Identifier("randomcontents", "essential"), ESSENTIAL);
        Registry.register(Registry.ITEM, new Identifier("randomcontents", "burnable_essential"), BURNABLE_ESSENTIAL);
        Registry.register(Registry.ITEM, new Identifier("randomcontents", "hand_grinder"), HAND_GRINDER);
        Registry.register(Registry.ITEM, new Identifier("randomcontents", "enhanced_gold_ingot"), ENHANCED_GOLD_INGOT);
        Registry.register(Registry.ITEM, new Identifier("randomcontents", "copy_engery_processor"), COPY_ENGERY_PROCESSOR);
        Registry.register(Registry.ITEM, new Identifier("randomcontents", "enhanced_gold_wire"), ENHANCED_GOLD_WIRE);
        Registry.register(Registry.ITEM, new Identifier("randomcontents", "copy_engery_receiver"), COPY_ENGERY_RECEIVER);
        // Registry.register(Registry.ITEM, new Identifier("randomcontents", "charged_bow"), CHARGED_BOW);
    }
}
