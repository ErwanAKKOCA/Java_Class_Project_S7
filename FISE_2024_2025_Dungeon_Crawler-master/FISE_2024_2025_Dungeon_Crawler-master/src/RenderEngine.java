
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class RenderEngine extends JPanel implements Engine{
    public static boolean GAME_RUNNING = true ;


    private ArrayList<Displayable> renderList;
    private Timer renderTimer;

    public void setRenderTimer(Timer renderTimer) {
        this.renderTimer = renderTimer;
    }

    public RenderEngine(JFrame jFrame) {
        renderList = new ArrayList<>();
    }

    public void addToRenderList(Displayable displayable){
        if (!renderList.contains(displayable)){
            renderList.add(displayable);
        }
    }

    public void addToRenderList(ArrayList<Displayable> displayable){
        if (!renderList.contains(displayable)){
            renderList.addAll(displayable);
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        for (Displayable renderObject:renderList) {
            try {
                renderObject.draw(g);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        if(GameEngine.hero.lives<0){ //Game Over if the hero has no life left

            GAME_RUNNING=false;
            g.setColor(Color.WHITE);
            g.setFont(new Font("Comic Sans MS", Font.BOLD, 40));
            g.drawString("Game Over !", 100, 300);
            g.setFont(new Font("Comic Sans MS", Font.BOLD, 20));

            renderTimer.stop();
        }
        g.setColor(Color.WHITE);
        g.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
        g.drawString("Lives:"+GameEngine.hero.lives, 50, 50);
        
    }

    @Override
    public void update(){
        this.repaint();
    }

    

}
