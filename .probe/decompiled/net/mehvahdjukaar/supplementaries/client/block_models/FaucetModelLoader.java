package net.mehvahdjukaar.supplementaries.client.block_models;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.mehvahdjukaar.moonlight.api.client.model.CustomGeometry;
import net.mehvahdjukaar.moonlight.api.client.model.CustomModelLoader;
import net.minecraft.client.resources.model.BakedModel;

public class FaucetModelLoader implements CustomModelLoader {

    @Override
    public CustomGeometry deserialize(JsonObject json, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonElement model = json.get("model");
        JsonElement liquid = json.get("liquid");
        return (modelBaker, spriteGetter, transform, location) -> {
            BakedModel g = CustomModelLoader.parseModel(model, modelBaker, spriteGetter, transform, location);
            BakedModel l = CustomModelLoader.parseModel(liquid, modelBaker, spriteGetter, transform, location);
            return new FaucetBakedModel(g, l, transform);
        };
    }
}