package com.hpe.apppulse.openapi.Utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.http.HttpResponse;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

/**
 * Created by Meir Ron on 11/12/2015.
 */
public class JsonUtils {

    public static class ResponseJsonReader<T> {
        public T readFromJson(HttpResponse response, Class<T> clazz) throws IOException {
            InputStream is = response.getEntity().getContent();
            try (Reader reader = new InputStreamReader(is)) {
                Gson gson = new GsonBuilder().create();
                return gson.fromJson(reader, clazz);
            } catch (Exception e) {
                System.err.printf("Failed to create deserialize token result for getToken%s%s%n",
                        System.lineSeparator(), e.getMessage());
                throw e;
            }
        }
    }
}
