package com.itayfeder.redstone_revolution.core.init;

import com.itayfeder.redstone_revolution.RedstoneRevolutionMod;
import com.itayfeder.redstone_revolution.common.villagers.RandomTradeBuilder;
import net.minecraft.item.Items;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = RedstoneRevolutionMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class TradesInit {
    @SubscribeEvent
    public static void registerTrades(VillagerTradesEvent event){

        if(event.getType() == ProfessionInit.MECHANIC_PROFESSION.get())
        {
            event.getTrades().get(1).add((new RandomTradeBuilder(16, 10, 0.05F).setPrice(Items.REDSTONE, 27,57).setForSale(Items.EMERALD, 2, 6).build()));
            event.getTrades().get(1).add((new RandomTradeBuilder(16, 10, 0.05F).setPrice(Items.QUARTZ, 8,23).setForSale(Items.EMERALD, 3, 7).build()));
            event.getTrades().get(2).add((new RandomTradeBuilder(1, 10, 0.05F).setEmeraldPrice(24).setForSale(ItemInit.WRENCH.get(), 1, 1).build()));
            event.getTrades().get(2).add((new RandomTradeBuilder(12, 10, 0.05F).setEmeraldPrice(4).setForSale(Items.REPEATER, 1, 1).build()));
            event.getTrades().get(2).add((new RandomTradeBuilder(8, 10, 0.05F).setEmeraldPrice(8).setForSale(Items.COMPARATOR, 1, 1).build()));
            event.getTrades().get(3).add((new RandomTradeBuilder(3, 10, 0.05F).setEmeraldPrice(16).setForSale(Items.DISPENSER, 1, 1).build()));
            event.getTrades().get(3).add((new RandomTradeBuilder(3, 10, 0.05F).setEmeraldPrice(16).setForSale(Items.DROPPER, 1, 1).build()));
            event.getTrades().get(4).add((new RandomTradeBuilder(3, 10, 0.05F).setEmeraldPrice(18).setForSale(Items.OBSERVER, 1, 1).build()));
            event.getTrades().get(4).add((new RandomTradeBuilder(3, 10, 0.05F).setEmeraldPrice(18).setForSale(Items.HOPPER, 1, 1).build()));
            event.getTrades().get(5).add((new RandomTradeBuilder(5, 10, 0.05F).setEmeraldPrice(20).setForSale(Items.NOTE_BLOCK, 1, 1).build()));
            event.getTrades().get(5).add((new RandomTradeBuilder(5, 10, 0.05F).setEmeraldPrice(20).setForSale(Items.TNT, 1, 1).build()));
            event.getTrades().get(5).add((new RandomTradeBuilder(5, 10, 0.05F).setEmeraldPrice(16).setForSale(Items.REDSTONE_LAMP, 1, 1).build()));
        }
    }
}
