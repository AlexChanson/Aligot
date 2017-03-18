package physics;

/**
 * Created by Blackcraps on 18/03/2017.
 */
public class testVector2D {

    public static void main(String[] args) {
        Vector2D v1 = new Vector2D(1,1);
        Vector2D v2 = new Vector2D(1,2);
        System.out.println("V1");
        v1.printCoordinates();
        System.out.println("V2");
        v2.printCoordinates();

        double distance = v1.distanceTo(v2);
        System.out.println("Distance entre v1 et v2 : " + distance);

        double distanceSquared = v1.distanceToSquared(v2);
        System.out.println("Distance au carré entre v1 et v2 : " + distanceSquared);

        double norme = v1.norm();
        System.out.println("La norme de v1 est : " + norme);

        double normeSquared = v1.normSquared();
        System.out.println("La norme au carré de v1 est : " + normeSquared);
    }
}
