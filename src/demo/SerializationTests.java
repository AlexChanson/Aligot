package demo;
/**
import com.google.gson.Gson;
import core.Level;
import core.Spawn;
import physics.RigidBody;
import physics.Vector2D;
*/
/**
 * Don't mess with this, it ain't for kids -Alex
 */
/**
public class SerializationTests {
    public static void main(String[] args){
        Gson gson = new Gson();
        Level test = new Level("coucou", "vive les frites", "sprites/frites.png");
        test.addElement(new RigidBody(new Vector2D(5,6),152222,555), new RigidBody(new Vector2D(789,8),152222,555477));
        test.addSpawns(new Spawn(new Vector2D(13,458)));
        System.out.println(gson.toJson(test));
    }
}*/
