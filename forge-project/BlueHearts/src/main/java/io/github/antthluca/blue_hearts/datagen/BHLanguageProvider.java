package io.github.antthluca.blue_hearts.datagen;

import io.github.antthluca.blue_hearts.BlueHearts;
import io.github.antthluca.blue_hearts.api.LanguagesProvider;
import io.github.antthluca.blue_hearts.init.InitBlocks;
import io.github.antthluca.blue_hearts.init.InitMobEffects;
import io.github.antthluca.blue_hearts.init.InitFoods;
import io.github.antthluca.blue_hearts.init.InitItems;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;

import java.util.List;

public class BHLanguageProvider extends LanguagesProvider {
    public BHLanguageProvider(PackOutput output) {
        super(output, BlueHearts.MODID, List.of("en_us", "pt_br"));
    }

    @Override
    protected void addTranslations() {
        add("itemGroup." + BlueHearts.MODID, "Blue Hearts", "Blue Hearts");

        addAdvancement("acquire_vital_fruit",
            new String[]{"Perpetuation of species", "Perpetuação de espécie"},
            new String[]{"Find a bush and pick a Vital Fruit.", "Encontre um arbusto e colha um Fruto Vital."});
        addAdvancement("eat_lazuli_apple",
            new String[]{"Watch your teeth!", "Cuidado com seus dentes!"},
            new String[]{"Taste the two layers of ore in a Lazuli Apple and feel the power.", "Deguste as duas camadas de minério de uma Maçã Lazulada e sinta o poder."});

        addCommand("invalid_number", "The number of hearts must be positive or zero.", "O número de corações deve ser positivo ou zero.");
        addCommand("capped_number", "The number of hearts was capped to 10.", "O número de corações foi limitado a 10.");
        addCommand("invalid_players", "No valid players found for the given selector.", "Nenhum jogador válido foi encontrado para o seletor fornecido.");
        addCommand("success.add", "Added %s blue hearts to %s.", "Adicionados %s corações azuis para %s.");
        addCommand("success.rm", "Removed %s blue hearts from %s", "Removidos %s corações azuis de %s.");
        addCommand("success.set", "Set blue hearts of %s to %s.", "Definido os corações azuis de %s como %s.");

        add("item.%s.common.hold_shift".formatted(BlueHearts.MODID), "Hold down the §6Shift§r to view the item's abilities.", "Mantenha o §6Shift§r pressionado para ver as habilidades do item.");
        add("item.%s.common.abilities".formatted(BlueHearts.MODID), "Abilities", "Habilidades");

        addItem(InitItems.BLUE_BLOOD_POTION.get(), "Blue Blood Potion", "Poção de Sangue Azul");
        addItem(InitItems.ORANGE_ANTIDOTE_POTION.get(), "Orange Antidote Potion", "Poção de Antídoto Laranja");
        addItem(InitItems.BLUE_EYE.get(), "Blue Eye", "Olho Azul");
        addItem(InitItems.VITAL_SAP.get(), "Vital Sap", "Seiva Vital");
        addItem(InitItems.CRYING_CHARM.get(), "Revitalizing Crying Charm", "Amuleto do Choro Revitalizante");
        addItemLore(InitItems.CRYING_CHARM.get(), "This amulet brought by pain, cry eternally, tears of Blue Blood.", "Este amuleto trazido pela dor, chora eternamente, lágrimas de Sangue Azul.");
        addItemAbilities(InitItems.CRYING_CHARM.get(), new String[]{"From time to time, it supplies 0.5 blue hearts with its tear.", "De tempo em tempo, ele abastece 0.5 corações azuis com sua lágrima."});
        addItem(InitItems.PETRIFIED_BLUE_BLOOD.get(), "Petrified Blue Blood", "Sangue Azul Petrificado");
        addItemLore(InitItems.PETRIFIED_BLUE_BLOOD.get(), "This drop of petrified Blue Blood protects its kind.", "Essa gota de Sangue Azul petrificada protege seus semelhantes.");
        addItemAbilities(InitItems.PETRIFIED_BLUE_BLOOD.get(), new String[]{"When you take damage to your blue hearts, you gain 6 armor.", "Quando sofrer dano nos corações azuis, receba 6 de armadura."});
        addItem(InitItems.HEART_MARBLEMAROON.get(), "Heart of Marblemaroon", "Coração de Marblemaroon");
        addItemLore(InitItems.HEART_MARBLEMAROON.get(), "This piece of maroon marble drains the wearer's health and replaces it with Blue Blood.", "Esse pedaço de mármore marrom-avermelhado esvazia a saúde do portador e substitui por Sangue Azul.");
        addItemAbilities(InitItems.HEART_MARBLEMAROON.get(), new String[]{"Replace the red hearts with blue hearts.", "Substitui corações vermelhos por corações azuis."});
        addItem(InitItems.MYST_BLUE_GEM.get(), "Mysterious Blue Gem", "Jóia Azul Misteriosa");
        addItemLore(InitItems.MYST_BLUE_GEM.get(), "The effect of this jewel is not pleasant, but its product is restorative. Use §6Right Click§r.", "O efeito dessa jóia não é agradável, mas seu produto é restaurador. Use §6Clique Direito§r.");
        addItem(InitFoods.VITAL_FRUIT.get(), "Vital Fruit", "Fruto Vital");
        addItem(InitFoods.LAZULI_APPLE.get(), "Lazuli Apple", "Maçã Lazulada");

        addBlock(InitBlocks.VITAL_BUSH.get(), "Vital Bush", "Arbusto Vital");

        addMobEffect(InitMobEffects.BLUE_BLOOD.get(), "Blue Blood Effect", "Efeito de Sangue Azul");
        addMobEffect(InitMobEffects.ORANGE_ANTIDOTE.get(), "Orange Antidote Effect", "Efeito de Antídoto Laranja");
        addMobEffect(InitMobEffects.CRYING_CHARM.get(), "Crying Charm", "Encanto Chorão");
    }

    protected void addItemLore(Item key, String... lores) {
        add(key.getDescriptionId() + ".lore", lores);
    }

    protected void addItemAbilities(Item key, String[]... abilities) {
        String baseKey = key.getDescriptionId() + ".ability";

        for (int a = 1; a <= abilities.length; a++) {
            String[] ability = abilities[a - 1];

            add(baseKey + a, ability);
        }
    }
}
