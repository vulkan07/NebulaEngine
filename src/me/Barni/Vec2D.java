package me.Barni;

public class Vec2D {

    public float x,y;

    public Vec2D()
    {
        x=0;
        y=0;
    }

    void print()
    {
        System.out.println("Vec: " + x + ", " + y);
        //System.out.printf("Vec: %f, %f \n", x, y);
    }

    void decrease(float am)
    {
        if (Math.abs(x)-am < 0) x=0;
            else if (x != 0) x -= x > 0 ? am : am*-1;

        if (Math.abs(y)-am < 0) y=0;
            else if (y != 0) y -= y > 0 ? am : am*-1;
    }

    public Vec2D(float x, float y)
    {
        this.x = x;
        this.y = y;
    }

    void add(Vec2D b)
    {
        x += b.x;
        y += b.y;
    }
    void sub(Vec2D b)
    {
        x -= b.x;
        y -= b.y;
    }
    void mult(Vec2D b)
    {
        x *= b.x;
        y *= b.y;
    }

    void scale(float scalar)
    {
        x *= scalar;
        y *= scalar;
    }

    void div(Vec2D b)
    {
        x /= b.x;
        y /= b.y;
    }

    void limit(float max)
    {
        if (Math.abs(x) > max)
            if (x > 0)
                x = max;
            else
                x = max*-1;


        if (Math.abs(y) > max)
            if (y > 0)
                y = max;
            else
                y = max*-1;
    }


    float mag()
    {
        return (float) Math.sqrt( (x*x + y*y) );
    }
    void normalize()
    {
        x /= mag();
        y /= mag();
    }
}
