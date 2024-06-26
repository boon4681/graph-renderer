package engine.math;

public class Vector2D {
    public static interface Interpolate {
        public double run(double from, double to, double steps, double current);
    }

    private Interpolate LINEAR_INTERPOLATE = (f, t, d, T) -> {
        double s = T / d;
        return (1 - s) * f + s * t;
    };

    public double x, y;

    public Vector2D() {
        this(0, 0);
    }

    public Vector2D(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vector2D add(Vector2D other) {
        this.x += other.x;
        this.y += other.y;
        return this;
    }

    public Vector2D sub(Vector2D other) {
        this.x -= other.x;
        this.y -= other.y;
        return this;
    }

    public Vector2D mult(Vector2D other) {
        this.x *= other.x;
        this.y *= other.y;
        return this;
    }

    public Vector2D mult(double v) {
        this.x *= v;
        this.y *= v;
        return this;
    }

    public Vector2D div(Vector2D other) {
        this.x *= other.x;
        this.y *= other.y;
        return this;
    }

    public Vector2D div(double v) {
        this.x /= v;
        this.y /= v;
        return this;
    }

    public double mag() {
        return Math.sqrt(this.x * this.x + this.y * this.y);
    }

    public Vector2D normalize() {
        double mag = this.mag();
        double x = this.x / mag;
        double y = this.y / mag;
        return new Vector2D(x, y);
    }

    public double dot(Vector2D v) {
        return this.x * v.x + this.y * v.y;
    }

    public double cross(Vector2D v) {
        return this.x * v.y - this.y * v.x;
    }

    public double atan() {
        return Math.atan(this.y / this.x);
    }

    public Vector2D copy() {
        return new Vector2D(this.x, this.y);
    }

    public Vector2D linearInterpolate(Vector2D to, double steps, double step) {
        return new Vector2D(this.LINEAR_INTERPOLATE.run(this.x, to.x, steps, step),
                this.LINEAR_INTERPOLATE.run(this.y, to.y, steps, step));
    }

    public Vector2D interpolate(Interpolate interpolate, Vector2D to, double steps, double step) {
        return new Vector2D(interpolate.run(this.x, to.x, steps, step),
                interpolate.run(this.y, to.y, steps, step));
    }

    @Override
    public String toString() {
        return this.x + "," + this.y;
    }
}
