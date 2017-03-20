package physics

/**
 * Fait par Alexandre le 18/03/2017.
 */
class Vector2DTest extends GroovyTestCase {

    void testAdd() {
        Vector2D v1 = new Vector2D(1,1)
        Vector2D v2 = new Vector2D(1,2)
        Vector2D v3 = v1.add(v2)
        assertEquals(2, v3.getX())
        assertEquals(3, v3.getY())
    }

    void testAdd1() {

    }

    void testRotate() {

    }
}
