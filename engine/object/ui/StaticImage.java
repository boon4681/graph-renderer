package engine.object.ui;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;

import engine.math.Vector2D;
import engine.object.interaction.Interactable;
import engine.renderer.Panel;
import engine.utils.Image;

public class StaticImage implements Interactable {
    private double id = Math.random();
    private BufferedImage image;
    public int x;
    public int y;
    public int w;
    public int h;

    public StaticImage(String image, int x, int y, int w, int h) {
        this(new File(image), x, y, w, h);
    }

    public StaticImage(File image, int x, int y, int w, int h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.image = Image.loadImage(image);
    }

    @Override
    public void render(Panel parent, Graphics2D g) {
        g.drawImage(image, this.x, this.y, this.w, this.h, null);
    }

    @Override
    public double getId() {
        return this.id;
    }

    @Override
    public Vector2D getLocation() {
        return new Vector2D(this.x, this.y);
    }

    @Override
    public boolean testCollision(double zoom, Vector2D p) {
        return false;
    }
}
