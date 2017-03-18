package physics;

/**
 * Fait par Alexandre le 18/03/2017.
 */
public class SphereTest {
    public static void main(String[] args){
        Sphere s = new Sphere(new Vector2D(1,3));
        System.out.println(s.volume());
    }
}
