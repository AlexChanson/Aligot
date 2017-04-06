package editor

import com.google.gson.Gson
import core.Level


class Load {
    static Level level(String path){
        String s = new File(path).getText("UTF-8")
        Gson gson = new Gson()
        Level result = gson.fromJson(s, Level.class)
        return result
    }

}
