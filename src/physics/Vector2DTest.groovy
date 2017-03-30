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

    void testRotate() {
        Vector2D v = new Vector2D(0, 1)

        v.rotate(Math.PI/2)
        assertEquals(-1, v.getX(), 10e-6)
        assertEquals(0, v.getY(), 10e-6)

        Vector2D v2 = new Vector2D(15, 45)
        double temp = v2.norm()
        v2.rotate(Math.PI*1.256)
        assertEquals(temp, v2.norm(), 10e-6)
    }
}
