package com.yungnickyoung.minecraft.yungsapi.io;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.yungnickyoung.minecraft.yungsapi.api.world.randomize.BlockStateRandomizer;
import com.yungnickyoung.minecraft.yungsapi.api.world.randomize.ItemRandomizer;
import com.yungnickyoung.minecraft.yungsapi.json.BlockStateAdapter;
import com.yungnickyoung.minecraft.yungsapi.json.BlockStateRandomizerAdapter;
import com.yungnickyoung.minecraft.yungsapi.json.ItemAdapter;
import com.yungnickyoung.minecraft.yungsapi.json.ItemRandomizerAdapter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.state.BlockState;

public class JSON {

    public static Gson gson;

    private JSON() {
    }

    public static String toJsonString(Object object) {
        return toJsonString(object, gson);
    }

    public static String toJsonString(Object object, Gson gson) {
        return gson.toJson(object);
    }

    public static void createJsonFileFromObject(Path path, Object object) throws IOException {
        createJsonFileFromObject(path, object, gson);
    }

    public static void createJsonFileFromObject(Path path, Object object, Gson gson) throws IOException {
        String jsonString = gson.toJson(object);
        Files.write(path, jsonString.getBytes(), new OpenOption[0]);
    }

    public static <T> T loadObjectFromJsonFile(Path path, Class<T> objectClass) throws IOException {
        return loadObjectFromJsonFile(path, objectClass, gson);
    }

    public static <T> T loadObjectFromJsonFile(Path path, Class<T> objectClass, Gson gson) throws IOException {
        Reader reader = Files.newBufferedReader(path);
        T jsonObject = (T) gson.fromJson(reader, objectClass);
        reader.close();
        return jsonObject;
    }

    public static GsonBuilder newGsonBuilder() {
        return new GsonBuilder();
    }

    static {
        GsonBuilder gsonBuilder = newGsonBuilder();
        gsonBuilder.registerTypeHierarchyAdapter(BlockState.class, new BlockStateAdapter());
        gsonBuilder.registerTypeHierarchyAdapter(BlockStateRandomizer.class, new BlockStateRandomizerAdapter());
        gsonBuilder.registerTypeHierarchyAdapter(Item.class, new ItemAdapter());
        gsonBuilder.registerTypeHierarchyAdapter(ItemRandomizer.class, new ItemRandomizerAdapter());
        gsonBuilder.setPrettyPrinting();
        gsonBuilder.disableHtmlEscaping();
        gson = gsonBuilder.create();
    }
}