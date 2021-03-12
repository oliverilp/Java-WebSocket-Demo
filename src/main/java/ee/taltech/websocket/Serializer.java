package ee.taltech.websocket;

import com.google.gson.Gson;

// This class uses generics so you don't have to create a separate encode/decode methods for each data class.
// https://javadoc.pages.taltech.ee/oop/oop-generics.html
public class Serializer<T> {
    private final Class<T> typeParameterClass;
    private final Gson gson = new Gson();

    public Serializer(Class<T> typeParameterClass) {
        this.typeParameterClass = typeParameterClass;
    }

    public String encode(T object) {
        return gson.toJson(object);
    }

    public T decode(String string) {
        return gson.fromJson(string, typeParameterClass);
    }
}
