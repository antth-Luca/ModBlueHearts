package io.github.antthluca.blue_hearts.api;

import com.google.gson.JsonObject;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.CompletableFuture;

public abstract class LanguagesProvider implements DataProvider{
    private final List<Map<String, String>> datas = new ArrayList<>();
    private final PackOutput output;

    protected final String modid;
    protected final List<String> locales;

    public LanguagesProvider(PackOutput output, String modid, List<String> locales) {
        this.output = output;
        this.modid = modid;
        this.locales = List.copyOf(locales);

        for (int l = 0; l < this.locales.size(); l++) {
            this.datas.add(new TreeMap<>());
        }
    }

    protected abstract void addTranslations();

    @Override
    public CompletableFuture<?> run(CachedOutput cache) {
        addTranslations();

        List<CompletableFuture<?>> futures = new ArrayList<>();

        for (int l = 0; l < this.locales.size(); l++) {
            Map<String, String> data = this.datas.get(l);
            if (data.isEmpty()) continue;

            Path target = this.output.getOutputFolder(PackOutput.Target.RESOURCE_PACK)
                    .resolve(this.modid)
                    .resolve("lang")
                    .resolve(this.locales.get(l) + ".json");

            futures.add(save(cache, data, target));
        }

        return CompletableFuture.allOf(futures.toArray(CompletableFuture[]::new));
    }

    @Override
    public String getName() {
        return "Languages for mod" + this.modid + ": " + this.locales;
    }

    private CompletableFuture<?> save(CachedOutput cache, Map<String, String> data, Path target) {
        JsonObject json = new JsonObject();
        data.forEach(json::addProperty);
        return DataProvider.saveStable(cache, json, target);
    }

    protected void addCommand(String key, String... descriptions) {
        add("command.%s.%s".formatted(this.modid, key), descriptions);
    }

    protected void addAdvancement(String key, String[] titles, String[] descriptions) {
        String base = "advancement.%s.%s.".formatted(this.modid, key);
        add(base + "title", titles);
        add(base + "description", descriptions);
    }

    protected void addItem(Item key, String... names) {
        add(key.getDescriptionId(), names);
    }

    protected void addBlock(Block key, String... names) {
        add(key.getDescriptionId(), names);
    }

    protected void addMobEffect(MobEffect key, String... names) {
        add(key.getDescriptionId(), names);
    }

    protected void add(String key, String... values) {
        if (values.length < this.locales.size())
            throw new IllegalStateException("Key '%s' has %d values but there are %d locales"
                    .formatted(key, values.length, this.locales.size()));

        for (int v = 0; v < values.length; v++) {
            if (this.datas.get(v).put(key, values[v]) != null) {
                throw new IllegalStateException("Duplicate translation key %s for locale %s"
                        .formatted(key, this.locales.get(v)));
            }
        }
    }
}
