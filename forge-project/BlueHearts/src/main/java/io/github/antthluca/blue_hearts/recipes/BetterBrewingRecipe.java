package io.github.antthluca.blue_hearts.recipes;

import net.minecraft.core.Holder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraftforge.common.brewing.IBrewingRecipe;

public class BetterBrewingRecipe implements IBrewingRecipe {
    private final Potion input;
    private final Item ingredient;
    private final Item output;

    public BetterBrewingRecipe(Potion input, Item ingredient, Item output) {
        this.input = input;
        this.ingredient = ingredient;
        this.output = output;
    }

    @Override
    public boolean isInput(ItemStack input) {
        return input != null && PotionUtils.getPotion(input) == this.input;
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
