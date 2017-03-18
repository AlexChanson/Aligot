package physics

class Sphere {
    double radius
    Vector2D director

    Sphere(Vector2D director) {
        this.director = director
        radius = director.norm()
    }

    double volume(){
        return (4/3) * Math.PI * Math.pow(radius,3)
    }
}
