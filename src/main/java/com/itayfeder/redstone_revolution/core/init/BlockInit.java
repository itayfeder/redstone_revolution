package com.itayfeder.redstone_revolution.core.init;

import com.itayfeder.redstone_revolution.RedstoneRevolutionMod;
import com.itayfeder.redstone_revolution.common.blocks.*;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BlockInit {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS,
            RedstoneRevolutionMod.MOD_ID);

    public static final RegistryObject<Block> BLUESTONE_WIRE = BLOCKS
            .register("bluestone_wire",
                    () -> new BluestoneWireBlock(AbstractBlock.Properties.create(Material.MISCELLANEOUS)
                            .doesNotBlockMovement().zeroHardnessAndResistance()));

    public static final RegistryObject<Block> QUARTZ_PRESSURE_PLATE = BLOCKS
            .register("quartz_pressure_plate",
                    () -> new QuartzPressurePlateBlock(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.QUARTZ)
                            .setRequiresTool().doesNotBlockMovement().hardnessAndResistance(0.5F)));

    public static final RegistryObject<Block> REDSTONE_WATCHER = BLOCKS
            .register("redstone_watcher",
                    () -> new RedstoneWatcherBlock(AbstractBlock.Properties.create(Material.ROCK).hardnessAndResistance(3.0F).setRequiresTool()));

    public static final RegistryObject<Block> WOODEN_VENTILATOR = BLOCKS
            .register("wooden_ventilator",
                    () -> new VentilatorBlock(VentilatorBlock.VentilatorType.WOOD, AbstractBlock.Properties.create(Material.WOOD, MaterialColor.WOOD).hardnessAndResistance(2.0F).sound(SoundType.WOOD).harvestTool(ToolType.PICKAXE)));

    public static final RegistryObject<Block> IRON_VENTILATOR = BLOCKS
            .register("iron_ventilator",
                    () -> new VentilatorBlock(VentilatorBlock.VentilatorType.IRON, AbstractBlock.Properties.create(Material.IRON, MaterialColor.IRON).hardnessAndResistance(3.0F).sound(SoundType.METAL).harvestTool(ToolType.PICKAXE)));

    public static final RegistryObject<Block> REDSTONE_RECIEVER = BLOCKS
            .register("redstone_reciever",
                    () -> new RedstoneRecieverBlock(AbstractBlock.Properties.create(Material.MISCELLANEOUS).zeroHardnessAndResistance().sound(SoundType.WOOD)));

    public static final RegistryObject<Block> WIRELESS_LEVER = BLOCKS
            .register("wireless_lever",
                    () -> new WirelessLeverBlock(AbstractBlock.Properties.create(Material.MISCELLANEOUS).doesNotBlockMovement().hardnessAndResistance(0.5F).sound(SoundType.WOOD)));

    public static final RegistryObject<Block> GOLDEN_DOOR = BLOCKS
            .register("golden_door",
                    () -> new GoldenDoorBlock(AbstractBlock.Properties.create(Material.IRON, MaterialColor.IRON).setRequiresTool().hardnessAndResistance(5.0F).sound(SoundType.METAL).notSolid()));

    public static final RegistryObject<Block> ROTATOR = BLOCKS
            .register("rotator",
                    () -> new RotatorBlock(AbstractBlock.Properties.create(Material.PISTON).hardnessAndResistance(1.5F).variableOpacity().setOpaque((state, reader, pos) -> {return false;}).setBlocksVision((state, reader, pos) -> {return false;})));

    public static final RegistryObject<Block> RANDOMIZER = BLOCKS
            .register("randomizer",
                    () -> new RandomizerBlock(AbstractBlock.Properties.create(Material.MISCELLANEOUS).zeroHardnessAndResistance().sound(SoundType.WOOD)));

}
