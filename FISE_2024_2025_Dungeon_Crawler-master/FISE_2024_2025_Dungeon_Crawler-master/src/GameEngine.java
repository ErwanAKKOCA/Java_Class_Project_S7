import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameEngine implements Engine, KeyListener {
    static DynamicSprite hero;
    static EnemySprite enemy;
    public static boolean space_pressed_enemy_near;
    static boolean cooldown_spell_boolean=true;
    public GameEngine(DynamicSprite hero) {
        this.hero = hero;
    }
    public GameEngine(EnemySprite enemy) {
        this.enemy = enemy;
    }
    @Override
    public void update() {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch(e.getKeyCode()){
            case KeyEvent.VK_UP :
                hero.setDirection(Direction.NORTH);
                break;
            case KeyEvent.VK_DOWN:
                hero.setDirection(Direction.SOUTH);
                break;
            case KeyEvent.VK_LEFT:
                hero.setDirection(Direction.WEST);
                break;
            case KeyEvent.VK_RIGHT:
                hero.setDirection(Direction.EAST);
                break;
            case KeyEvent.VK_SPACE: //When space is pressed and an enemy is near (<100 pixels ) then cast the spell
                if(cooldown_spell_boolean) {
                    space_pressed_enemy_near = false;
                    EnemySprite.spell_activated= true; //Starting point (in time) of the spell
                    if (DynamicSprite.X_distance_w_enemy < 100 && DynamicSprite.Y_distance_w_enemy < 100 || GameEngine.hero.lives == 0) {

                        space_pressed_enemy_near = true;
                        cooldown_spell_boolean = false;
                        cooldown_spell.setRepeats(false); // Only execute once it remove one live
                        cooldown_spell.start();
                    }
                    System.out.println("Spell Cast " + space_pressed_enemy_near);
                }

        }
    }
    Timer cooldown_spell = new Timer(4000, new ActionListener() { //spell cooldown cannot launch spell if time is bellow
        @Override
        public void actionPerformed(ActionEvent arg0) {
            cooldown_spell_boolean=true;
        }
    });
    @Override
    public void keyReleased(KeyEvent e) {

    }
}
