package io.github.antthluca.blue_hearts.handlers;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.SlotResult;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;
import top.theillusivec4.curios.api.type.inventory.IDynamicStackHandler;

import java.util.Optional;

public class CurioItemsHandler {
    public static boolean hasCurio(final LivingEntity entity, final Item curio) {
        return CuriosApi.getCuriosInventory(entity)
                .map(inv -> inv.isEquipped(curio))
                .orElse(false);
    }

    public static ItemStack removeCurio(final LivingEntity entity, final Item curio) {
        Optional<ICuriosItemHandler> curiosHandler = CuriosApi.getCuriosInventory(entity).resolve();
        if (curiosHandler.isEmpty()) return ItemStack.EMPTY;

        ICuriosItemHandler handler = curiosHandler.get();

        Optional<SlotResult> found = handler.findFirstCurio(curio);
        if (found.isEmpty()) return ItemStack.EMPTY;

        SlotContext slotCtx = found.get().slotContext();

        Optional<ICurioStacksHandler> stacksHandler = handler.getStacksHandler(slotCtx.identifier());
        if (stacksHandler.isEmpty()) return ItemStack.EMPTY;

        IDynamicStackHandler stacks = stacksHandler.get().getStacks();
        ItemStack stackInSlot = stacks.getStackInSlot(slotCtx.index());
        stacks.setStackInSlot(slotCtx.index(), ItemStack.EMPTY);

        return stackInSlot;
    }
}
