package io.github.antthluca.blue_hearts.recipes;

import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionContents;
import net.neoforged.neoforge.common.brewing.IBrewingRecipe;

public class BetterBrewingRecipe implements IBrewingRecipe {
    private final Holder<Potion> input;
    private final Item ingredient;
    private final Item output;

    public BetterBrewingRecipe(Holder<Potion> input, Item ingredient, Item output) {
        this.input = input;
        this.ingredient = ingredient;
        this.output = output;
    }

    @Override
    public boolean isInput(ItemStack input) {
        if (input == null || input.isEmpty()) return false;

        PotionContents contents = input.get(DataComponents.POTION_CONTENTS);
        if (contents != null) {
            Potion potion = contents.potion().get().value();
            return contents.potion().map(holder -> this.input.is(holder.value())).orElse(false);
        }

        return false;
    }

    @Override
    public boolean isIngredient(ItemStack ingredient) {
        return ingredient != null && ingredient.getItem() == this.ingredient;
    }

    @Override
    public ItemStack getOutput(ItemStack input, ItemStack ingredient) {
        return this.isInput(input) && this.isIngredient(ingredient) ? new ItemStack(this.getOutput()) : ItemStack.EMPTY;
    }

    public Item getOutput() {
        return this.output;
    }
}
