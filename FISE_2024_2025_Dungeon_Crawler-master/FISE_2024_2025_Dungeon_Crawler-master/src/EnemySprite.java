import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class EnemySprite extends SolidSprite {
    private double timeBetweenFrame = 250;
    private Direction direction = Direction.EAST;
    private final int spriteSheetNumberOfColumn = 10; // because 10 images in a line
    private final int spriteSheetNumberOfColumn_freeze = 4;
    public static boolean spell_activated= true;
    private long Starting_time;
    public EnemySprite(double x, double y, Image image, double width, double height) {
        super(x, y, image, width, height);
    }

    public void update(ArrayList<Sprite> environment) {
        // Static enemy: No movement logic needed.
    }

    Timer timer_freeze = new Timer( 2000, new ActionListener() { // Spell duration
        @Override
        public void actionPerformed(ActionEvent arg0) {
            GameEngine.space_pressed_enemy_near = false; // After casted spell, go back to false
            GameEngine.hero.lives--;
        }
    });

    @Override
    public void draw(Graphics g) throws IOException {
        int index= (int) (System.currentTimeMillis()/timeBetweenFrame%spriteSheetNumberOfColumn); //basic walk

        if(spell_activated){ // if spell activated, get time at this point
        Starting_time=System.currentTimeMillis();
           spell_activated= false;}
        long elapsed_time = System.currentTimeMillis() - Starting_time; //time elapsed after spell activated
        int index_freeze= (int) (elapsed_time/(2*timeBetweenFrame)%spriteSheetNumberOfColumn_freeze);
        index_freeze = 3 - index_freeze;
//Notice that it start from small mountain to bigger one.

        System.out.println(index_freeze);

        if (GameEngine.space_pressed_enemy_near){ //The enemy suffer from the spell, animation is launched
            System.out.println("Spell is launched");
            Image Image_freeze = ImageIO.read(new File("FISE_2024_2025_Dungeon_Crawler-master/img/heroTileSheetLowResEnemyfreezed.png"));
            g.drawImage(Image_freeze,(int) x-20, (int) y, (int) (x+60),(int) (y+70),
                    (int) (index_freeze*80), 0,
                    (int) ((index_freeze+1)*80), (int)(70),null);
            timer_freeze.setRepeats(false); // Only execute once it remove one live
            timer_freeze.start();

        }
        else{ //the animation of enemy walk's
            g.drawImage(image,(int) x, (int) y, (int) (x+width),(int) (y+height),
                    (int) (index*this.width), (int) (direction.getFrameLineNumber()*height),
                    (int) ((index+1)*this.width), (int)((direction.getFrameLineNumber()+1)*this.height),null);

        }
    }
}
