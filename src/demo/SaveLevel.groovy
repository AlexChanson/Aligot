package demo

import com.google.gson.Gson
import core.Level
import generator.LevelGen

class SaveLevel {
    static void main(String[] args){
        LevelGen lgen = new LevelGen(4242, [1920,1080] as int[])
        Level lvl = lgen.create()
        Gson gson = new Gson()
        def str = gson.toJson(lvl)
        println str
    }
}
