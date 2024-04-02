package engine.object.ui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;

import engine.math.Vector2D;
import engine.object.interaction.Hoverable;
import engine.object.ui.Button.OnHoverEvent;
import engine.renderer.Panel;
import engine.utils.Graphic;
import engine.utils.Image;

public class ImageButton extends Hoverable {
    private double id = Math.random();
    public int x;
    public int y;
    public int w = 100;
    public int h = 35;
    public BufferedImage image;
    public OnClickEvent onClicked;
    public OnHoverEvent onEntered;

    public boolean focus = false;

    public interface OnClickEvent {
        public void on();
    }

    public ImageButton(String image) {
        this(new File(image), 0, 0, 100, 35);
    }

    public ImageButton(File image, int x, int y) {
        this(image, x, y, 100, 35);
    }

    public ImageButton(String image, int x, int y, int w, int h) {
        this(new File(image), x, y, w, h);
    }

    public ImageButton(File image, int x, int y, int w, int h) {
        this.image = Image.loadImage(image);
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    @Override
    public void render(Panel parent, Graphics2D g) {
        if (this.hover) {
            g.setColor(Color.GRAY);
            parent.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            g.drawRoundRect(x, y, this.w, this.h, 10, 10);
            g.drawImage(image, this.x, this.y, this.w, this.h, null);
            // g.setStroke(new BasicStroke(2));
        }
        if (this.focus) {
            g.setColor(Color.LIGHT_GRAY);
            g.fillRoundRect(x, y, w, h, 10, 10);
            g.setColor(Color.BLACK);
            g.drawRoundRect(x, y, this.w, this.h, 10, 10);
            g.drawImage(image, this.x, this.y, this.w, this.h, null);
        } else {
            g.setColor(Color.BLACK);
            g.drawRoundRect(x, y, this.w, this.h, 10, 10);
            g.drawImage(image, this.x, this.y, this.w, this.h, null);
        }
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
    public boolean testCollision(double zoom, Vector2D mouse) {
        if (mouse.x >= this.x && mouse.y >= this.y && mouse.x <= this.w + this.x && mouse.y <= this.h + this.y) {
            return true;
        }
        return false;
    }

    @Override
    public void onMouseLeave(Panel parent, Vector2D p) {
        super.onMouseLeave(parent, p);
        parent.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
    }

    @Override
    public void onClick(Panel parent, Vector2D p) {
        if (this.onClicked != null) {
            this.onClicked.on();
        }
        super.onClick(parent, p);
    }

    @Override
    public void onMouseEnter(Panel parent, Vector2D p) {
        super.onMouseEnter(parent, p);
        if (this.onEntered != null) {
            this.onEntered.on();
        }
    }
}
