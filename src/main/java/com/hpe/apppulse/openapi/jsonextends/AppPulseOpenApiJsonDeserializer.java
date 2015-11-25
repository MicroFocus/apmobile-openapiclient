package com.hpe.apppulse.openapi.jsonextends;

import com.google.gson.*;
import com.hpe.apppulse.openapi.v1.bl.beans.OpenApiBean;

import java.lang.reflect.Type;

/**
 * Created by Meir Ron on 9/1/2015.
 */
public class AppPulseOpenApiJsonDeserializer implements JsonDeserializer<OpenApiBean> {
    private static final String CLASSNAME = "className";

    @Override
    public OpenApiBean deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
            Gson gson = new GsonBuilder().create();

            final JsonObject container = (JsonObject) json;
            JsonPrimitive prim = container.getAsJsonPrimitive(CLASSNAME);
            String className = prim.getAsString();
            Class<OpenApiBean> klass;
            try {
                klass = (Class<OpenApiBean>)Class.forName(className);
            } catch (ClassNotFoundException e) {
                System.err.printf("Failed to deserialize received samples bean: %s%s%s%n", container.toString(),
                        System.lineSeparator(), e.getMessage());
                throw new JsonParseException(e.getMessage());
            }
            return gson.fromJson(container.toString(), klass);
    }
}
