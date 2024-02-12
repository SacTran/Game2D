package Main;

import Entity.Entity;
import Entity.Player;
import Tile.TileManager;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class GamePanel extends JPanel implements Runnable {
    //Screen setting
    final int originalTileSize = 16; // 16x16
    final int scale = 3;
    public final int tileSize = originalTileSize * scale;//48
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol;
    public final int screenHeight = tileSize * maxScreenRow;

    public final int maxWorldCol=50;
    public final int maxWorldRow=50;

    //FPS
    int FPS = 60;
    //SYSTEM SETTING
    TileManager tileM = new TileManager(this);
    public KeyHandler keyH = new KeyHandler(this);
    public ClickHandler clickH = new ClickHandler(this);
    Sound music = new Sound();
    Sound se = new Sound();
    public UI ui = new UI(this);
    public CollisionChecker coChecker = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);
    Thread gameThread;
    public EventHandler eventHandler = new EventHandler(this);

    //ENTITY AND OBJECT
    public Player player = new Player(this, keyH, clickH);
    public Entity obj[] = new Entity[10];
    public Entity npc[] = new Entity[10];
    public Entity monster[] = new Entity[20];
    public Entity bullet[] = new Entity[120];
    ArrayList<Entity> entityList = new ArrayList<>();

    //GAME STATE
    public int gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialogueState = 3;
    public final int characterState = 4;


    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);

    }

    public void setupGame(){
        aSetter.setObject();
        aSetter.setNPC();
        aSetter.setMonster();
        //aSetter.setBullet();
        //playMusic(0);
        gameState = titleState;
    }

    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
//        double drawInterval = 1000000000/FPS;
//        double nextDrawTime = System.nanoTime() + drawInterval;
//        while (gameThread !=null) {
//
//            // 1. Update information
//            update();
//            // 2. Draw the screen for game
//            repaint();
//
//            double remainingTime = nextDrawTime - System.nanoTime();
//            remainingTime = remainingTime/1000000;
//            try {
//                if (remainingTime<0){
//                    remainingTime = 0;
//                }
//                Thread.sleep((long) remainingTime);
//                nextDrawTime += drawInterval;
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//        }

        double drawInterval = 1000000000/FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer=0;
        int drawCount=0;
        while (gameThread != null){
            currentTime = System.nanoTime();
            delta += (currentTime -lastTime)/drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;
            if (delta>=1){
                update();
                repaint();
                delta--;
                drawCount++;
            }
            if (timer>=1000000000){
                timer=0;
                drawCount=0;
            }
        }
    }

    public void update(){
        if (gameState == playState){
            //PLAYER
            player.update();
            //NPC
            for (int i=0; i<npc.length; i++){
                if (npc[i] != null){
                    npc[i].update();
                }
            }
            //MONSTER
            for (int i= 0; i<monster.length; i++){
                if (monster[i] != null){
                    if (monster[i].alive && !monster[i].dying) monster[i].update();
                    if (!monster[i].alive) monster[i] = null;
                }
            }
            //BULLET
            for (int i=0; i<bullet.length; i++){
                if (bullet[i]!= null){
                    if (!bullet[i].bulletHit) bullet[i].update();
                    if (bullet[i].bulletHit || bullet[i].colissionOn){
                        if (bullet[i].bulletHit){
                            this.playSE(8);
                        }
                        bullet[i].worldX = 0;
                        bullet[i].worldY = 0;
                        bullet[i].bulletHit = false;
                    }
                }
            }
        }
        if (gameState == pauseState){

        }

    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        //TITLE SCREEN
        if (gameState == titleState){
            ui.draw(g2);
        }
        //OTHER
        else {
            //TILE
            tileM.draw(g2);
            //ENTITY
            for (int i=0; i< npc.length; i++){
                if (npc[i] != null){
                    entityList.add(npc[i]);
                }
            }
            for (int i= 0; i<obj.length; i++){
                if (obj[i] != null){
                    entityList.add(obj[i]);
                }
            }
            for (int i= 0; i<monster.length; i++){
                if (monster[i] != null){
                    entityList.add(monster[i]);
                }
            }
            for (int i=0; i<bullet.length; i++){
                if (bullet[i]!= null){
                    entityList.add(bullet[i]);
                }
            }
            entityList.add(player);
            //SORT
            Collections.sort(entityList, new Comparator<Entity>() {
                @Override
                public int compare(Entity e1, Entity e2){
                    int result = Integer.compare(e1.worldY, e2.worldY);
                    return result;
                }
            });
            //ENTITY DRAW
            for (int i= 0; i< entityList.size(); i++){
                entityList.get(i).draw(g2);
            }
            //EMPLY ENTITY
            entityList.clear();

            //UI
            ui.draw(g2);
        }
        g2.dispose();

    }
    public void playMusic(int i){
        music.setFile(i);
        music.play();
        music.loop();
    }
    public void stopMusic(){
        music.stop();
    }
    public void playSE(int i){
        se.setFile(i);
        se.play();
    }
}
