package com.itayfeder.redstone_revolution.core.init;

import com.itayfeder.redstone_revolution.RedstoneRevolutionMod;
import com.itayfeder.redstone_revolution.common.villagers.mechanic.MechanicProfession;
import com.itayfeder.redstone_revolution.common.villagers.mechanic.PointOfInterestMechanicType;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.village.PointOfInterestType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Mod.EventBusSubscriber(modid = RedstoneRevolutionMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ProfessionInit {

    public static final DeferredRegister<PointOfInterestType> POINTS_OF_INTEREST = DeferredRegister.create(ForgeRegistries.POI_TYPES,
            RedstoneRevolutionMod.MOD_ID);

    public static final DeferredRegister<VillagerProfession> PROFESSIONS = DeferredRegister.create(ForgeRegistries.PROFESSIONS,
            RedstoneRevolutionMod.MOD_ID);

    public static final RegistryObject<PointOfInterestType> MECHANIC_POI = POINTS_OF_INTEREST.register("mechanic_poi",
            () -> new PointOfInterestMechanicType());

    public static final RegistryObject<VillagerProfession> MECHANIC_PROFESSION = PROFESSIONS.register("mechanic",
            () -> new MechanicProfession(MECHANIC_POI.get()));

    @SubscribeEvent
    public static void registerProfession(RegistryEvent.Register<VillagerProfession> event) {
        injectWorkstation();
    }

    private static Method injectWorkstation;
    static void injectWorkstation()
    {
        try
        {

            injectWorkstation = PointOfInterestType.class.getDeclaredMethod("registerBlockStates", PointOfInterestType.class);
            injectWorkstation.setAccessible(true);
            injectWorkstation.invoke(MECHANIC_POI.get(), MECHANIC_POI.get());



        }
        catch (NoSuchMethodException | SecurityException | InvocationTargetException | IllegalAccessException e)
        {
            try
            {
                injectWorkstation = PointOfInterestType.class.getDeclaredMethod("func_221052_a", PointOfInterestType.class);
                injectWorkstation.setAccessible(true);
                injectWorkstation.invoke(MECHANIC_POI.get(), MECHANIC_POI.get());
                e.printStackTrace();
            }
            catch (NoSuchMethodException | SecurityException | InvocationTargetException | IllegalAccessException er) {

                er.printStackTrace();

            }
        }
    }
}
