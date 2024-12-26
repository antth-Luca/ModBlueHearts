package io.github.antthluca.blue_hearts.handlers;

import java.util.Optional;

import org.apache.commons.lang3.tuple.ImmutableTriple;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.CuriosApi;

public class CurioItemsHandler {
    public static boolean hasCurio(final LivingEntity entity, final Item curio) {
		@SuppressWarnings("deprecation")
        final Optional<ImmutableTriple<String, Integer, ItemStack>> data = CuriosApi.getCuriosHelper().findEquippedCurio(curio, entity);
		return data.isPresent();
	}
}
