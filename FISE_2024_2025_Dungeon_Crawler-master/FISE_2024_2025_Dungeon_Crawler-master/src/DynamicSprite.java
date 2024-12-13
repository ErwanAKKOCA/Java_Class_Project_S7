import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class DynamicSprite extends SolidSprite{
    private Direction direction = Direction.EAST;
    private double speed = 5;
    private double timeBetweenFrame = 250;
    private boolean isWalking =true;
    private final int spriteSheetNumberOfColumn = 10;
    private static final double COLLISION_DISTANCE = 10.0;
    int lives=3;
    public static double X_distance_w_enemy;
    public static double Y_distance_w_enemy;

    public DynamicSprite(double x, double y, Image image, double width, double height) {
        super(x, y, image, width, height);
    }

    private boolean isMovingPossible(ArrayList<Sprite> environment){
        Rectangle2D.Double moved = new Rectangle2D.Double();
        switch(direction){
            case EAST: moved.setRect(super.getHitBox().getX()+speed,super.getHitBox().getY(),
                                    super.getHitBox().getWidth(), super.getHitBox().getHeight());
                break;
            case WEST:  moved.setRect(super.getHitBox().getX()-speed,super.getHitBox().getY(),
                    super.getHitBox().getWidth(), super.getHitBox().getHeight());
                break;
            case NORTH:  moved.setRect(super.getHitBox().getX(),super.getHitBox().getY()-speed,
                    super.getHitBox().getWidth(), super.getHitBox().getHeight());
                break;
            case SOUTH:  moved.setRect(super.getHitBox().getX(),super.getHitBox().getY()+speed,
                    super.getHitBox().getWidth(), super.getHitBox().getHeight());
                break;
        }

        for (Sprite s : environment){
            if ((s instanceof SolidSprite) && (s!=this)){
                if (((SolidSprite) s).intersect(moved)){
                    return false;
                }
            }
        }
        return true;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    private void move(){
        switch (direction){
            case NORTH -> {
                this.y-=speed;
            }
            case SOUTH -> {
                this.y+=speed;
            }
            case EAST -> {
                this.x+=speed;
            }
            case WEST -> {
                this.x-=speed;
            }
        }
    }

    private void checkProximityToEnemies(ArrayList<Sprite> environment) {
        for (Sprite s : environment) {
            if (s instanceof EnemySprite) {
                EnemySprite enemy = (EnemySprite) s;

                // Check if player intersects with enemy
                if (super.getHitBox().intersects(enemy.getHitBox())) {
                    if (lives > 0) {
                        lives--; // Decrease life on collision
                        System.out.println("Collision with enemy! Lives left: " + lives);
                    }
                } else {
                    // Check the distance between the player and enemy
                    double dx = this.x - enemy.x;
                    double dy = this.y - enemy.y;
                    double distance = Math.sqrt(dx * dx + dy * dy);


                    //System.out.println("Distance to enemy: " + distance);
                }
            }
        }
    }

                    public void moveIfPossible(ArrayList<Sprite> environment){
        if (isMovingPossible(environment)||lives==-1){//(isMovingPossible(environment)||lives==-1){
            move();
        }

         X_distance_w_enemy = Math.abs(GameEngine.hero.x-GameEngine.enemy.x);
        Y_distance_w_enemy = Math.abs(GameEngine.hero.y - GameEngine.enemy.y);
        if(X_distance_w_enemy<10 && Y_distance_w_enemy<10||GameEngine.hero.lives==0){
            timer.setRepeats(false); // Only execute once it remove one live
            timer.start();
            System.out.println("Player near enemy! x:" +Math.abs(GameEngine.hero.x-GameEngine.enemy.x) +"y:" + Math.abs(GameEngine.hero.y - GameEngine.enemy.y));
        }


        //System.out.println("hero.lives");

    }
    Timer timer = new Timer(1000, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent arg0) {
            GameEngine.hero.lives--;
        }
    });

    @Override
    public void draw(Graphics g) {
        int index= (int) (System.currentTimeMillis()/timeBetweenFrame%spriteSheetNumberOfColumn);

        g.drawImage(image,(int) x, (int) y, (int) (x+width),(int) (y+height),
                (int) (index*this.width), (int) (direction.getFrameLineNumber()*height),
                (int) ((index+1)*this.width), (int)((direction.getFrameLineNumber()+1)*this.height),null);
    }
}
