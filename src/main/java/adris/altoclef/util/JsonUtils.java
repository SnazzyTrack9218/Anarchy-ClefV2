package adris.altoclef.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class JsonUtils {

    public static final Gson gson = new Gson();
    public static final Gson prettyGson = new GsonBuilder().setPrettyPrinting().create();

    private JsonUtils() {
    }

    public static JsonElement parse(String json) {
        return JsonParser.parseString(json);
    }
}
