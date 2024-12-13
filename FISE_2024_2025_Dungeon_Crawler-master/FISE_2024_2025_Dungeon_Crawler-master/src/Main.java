import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class Main {

    static JFrame displayZoneFrame;

    RenderEngine renderEngine;
    GameEngine gameEngine;
    PhysicEngine physicEngine;

    public Main() throws Exception{


        displayZoneFrame = new JFrame("Java Labs");
        displayZoneFrame.setSize(400,600);
        displayZoneFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);

        DynamicSprite hero = new DynamicSprite(200,300,
                ImageIO.read(new File("FISE_2024_2025_Dungeon_Crawler-master/img/heroTileSheetLowRes.png")),48,50);

        EnemySprite enemy = new EnemySprite(200,350,
                ImageIO.read(new File("FISE_2024_2025_Dungeon_Crawler-master/img/heroTileSheetLowResEnemy.png")),48,50);

        renderEngine = new RenderEngine(displayZoneFrame);
        physicEngine = new PhysicEngine();
        gameEngine = new GameEngine(hero);
        gameEngine = new GameEngine(enemy);
        Timer renderTimer = new Timer(50,(time)-> renderEngine.update());
        Timer gameTimer = new Timer(50,(time)-> gameEngine.update());
        Timer physicTimer = new Timer(50,(time)-> physicEngine.update());

        renderTimer.start();
        gameTimer.start();
        physicTimer.start();

        renderEngine.setRenderTimer(renderTimer);
        displayZoneFrame.getContentPane().add(renderEngine);
        displayZoneFrame.setVisible(true);


        Playground level = new Playground("FISE_2024_2025_Dungeon_Crawler-master/data/level1.txt");
        //SolidSprite testSprite = new DynamicSprite(100,100,test,0,0);
        renderEngine.addToRenderList(level.getSpriteList());
        renderEngine.addToRenderList(hero);
        renderEngine.addToRenderList(enemy); //same features as hero
        physicEngine.addToMovingSpriteList(hero);
        physicEngine.setEnvironment(level.getSolidSpriteList());


        displayZoneFrame.addKeyListener(gameEngine);

    }

    public static void main (String[] args) throws Exception {
	// write your code here
        Main main = new Main();




    }
}
