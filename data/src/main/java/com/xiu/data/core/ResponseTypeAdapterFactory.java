package com.xiu.data.core;


import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

/**
 * @author wangyinxiu
 */
public class ResponseTypeAdapterFactory implements TypeAdapterFactory {


    private static final String CODE = "code";
    private static final String CODE_SUCCESS = "000000";
    private static final String DATA = "data";
    private static final String MSG = "msg";

    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
        final TypeAdapter<T> delegateAdapter = gson.getDelegateAdapter(this, type);
        final TypeAdapter<JsonElement> jsonElementAdapter = gson.getAdapter(JsonElement.class);

        return new TypeAdapter<T>() {
            @Override
            public void write(JsonWriter out, T value) throws IOException {
                delegateAdapter.write(out, value);
            }

            @Override
            public T read(JsonReader in) throws IOException {
                JsonElement jsonElement = jsonElementAdapter.read(in);
                if (jsonElement.isJsonObject()) {
                    JsonObject jsonObject = jsonElement.getAsJsonObject();
                    if (jsonObject.has(CODE) && jsonObject.has(DATA) && jsonObject.get(DATA).isJsonObject()) {
                        if (CODE_SUCCESS.equals(jsonObject.get(CODE).getAsString())) {
                            return delegateAdapter.fromJsonTree(jsonObject.get(DATA));
                        } else {
                            throw new IOException(jsonObject.get(CODE).getAsString() + Network.DILIMIT + jsonObject.get(MSG).getAsString());
                        }
                    }
                }
                return delegateAdapter.fromJsonTree(jsonElement);
            }
        }.nullSafe();
    }


}
