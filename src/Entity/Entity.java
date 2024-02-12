package Entity;

import Main.GamePanel;
import Main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Entity {
    GamePanel gp;
    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    public BufferedImage bulletImage,image, image1, image2;
    public BufferedImage attackUp1, attackUp2, attackDown1, attackDown2, attackLeft1, attackLeft2, attackRight1, attackRight2;
    public String dialogues[] = new String[20];
    public Rectangle solidArea = new Rectangle(0,0,48,48);
    public Rectangle attackArea = new Rectangle(0,0,0,0);
    public int solidAreaDefautX, solidAreaDefautY;
    public Boolean collision = false;


    //STATE
    public int worldX, worldY;
    public String direction = "down";
    public int spiteNum = 1;
    public int dialogueIndex = 0;
    public int hasBullet = 120;
    public boolean colissionOn = false;
    public boolean invincible = false;
    public boolean attacking = false;
    public boolean alive = true;
    public boolean dying = false;
    public boolean hpBarOn = false;
    public boolean bulletHit = false;

    //COUNTER
    public int spiteCount = 0;
    public int invincibleCounter = 0;
    public int actionLockCounter = 0;
    public int dyingCounter = 0;
    public int barCounter = 0;

    //CHARACTER STATUS
    public int maxLife, life;
    public String name;
    public int type;
    public int speed;
    public int level, strength, dexterity, attack, defense, exp, nextLevelExp, coin;
    public Entity currentWeapon, currentShield;

    //ITEM ATTRIBUTES
    public int attackValue, defenseValue;
    public String description = "";


    public Entity(GamePanel gp){
        this.gp = gp;
    }
    public void setAction(){};
    public void damageReaction(){};


    public void speak(){
        if (dialogues[dialogueIndex] == null) dialogueIndex = 0;
        gp.ui.currentDiagues = dialogues[dialogueIndex];
        dialogueIndex++;
        switch (gp.player.direction){
            case "up":
                direction = "down";
                break;
            case "down":
                direction = "up";
                break;
            case "left":
                direction = "right";
                break;
            case "right":
                direction = "left";
                break;
        }
    };
    public void update(){

        setAction();
        colissionOn = false;
        gp.coChecker.checkTile(this);
        gp.coChecker.checkObject(this, false);
        gp.coChecker.checkEntity(this, gp.npc);
        gp.coChecker.checkEntity(this, gp.monster);
        int bulletIndex = gp.coChecker.checkEntity(this, gp.bullet);
        if (bulletIndex != 999){
            gp.bullet[bulletIndex] = null;
        }
        boolean contactPlayer = gp.coChecker.checkPlayer(this);

        if (contactPlayer && this.type ==2){
            if (gp.player.invincible == false){
                gp.playSE(8);
                gp.player.life -=1;
                gp.player.invincible = true;
            }
        }

        if (colissionOn==false){
            switch (direction){
                case "up": worldY -= speed; break;
                case "down": worldY += speed; break;
                case "left": worldX -= speed; break;
                case "right": worldX += speed; break;
            }

        }

        spiteCount++;
        if (spiteCount>12){
            if (spiteNum==1) spiteNum=2;
            else if (spiteNum==2) {
                spiteNum=1;
            }
            spiteCount=0;
        }
        if (invincible == true){
            invincibleCounter++;
            if (invincibleCounter > 40){
                invincibleCounter = 0;
                invincible = false;
            }
        }
    };

    public void draw(Graphics2D g2){
        BufferedImage image = null;
        int screenX = worldX -  gp.player.worldX + gp.player.screenX;
        int screenY = worldY -  gp.player.worldY + gp.player.screenY;

        if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                worldY - gp.tileSize < gp.player.worldY + gp.player.screenY){

            switch (direction){
                case "up":
                    if (spiteNum==1) image = up1;
                    if (spiteNum==2) image = up2;
                    break;
                case "down":
                    if (spiteNum==1) image = down1;
                    if (spiteNum==2) image = down2;
                    break;
                case "left":
                    if (spiteNum==1) image = left1;
                    if (spiteNum==2) image = left2;
                    break;
                case "right":
                    if (spiteNum==1) image = right1;
                    if (spiteNum==2) image = right2;
                    break;
            }
            //Monster HeadBar
            double onScale = (double) gp.tileSize/maxLife;
            double hpBarValue = onScale * life;
            if (type == 2  && hpBarOn){
                g2.setColor(new Color(35,35,35));
                g2.fillRect(screenX - 1, screenY - 16, gp.tileSize + 2, 12);
                g2.setColor(new Color(255,0,30));
                g2.fillRect(screenX,screenY - 15, (int) hpBarValue, 10);

                barCounter++;
                if (barCounter>400){
                    barCounter = 0;
                    hpBarOn = false;
                }
            }

            if (invincible == true){
                barCounter = 0;
                hpBarOn = true;
                changeAlpha(g2, 0.3f);
            }
            if (dying || bulletHit){
                dyingAnimation(g2);
            }
            g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
            changeAlpha(g2,1f);

        }
    }

    public void dyingAnimation(Graphics2D g2) {
        dyingCounter++;
        int i= 5;
        if (dyingCounter<= i) changeAlpha(g2, 0f);
        if (dyingCounter>i && dyingCounter<=i*2) changeAlpha(g2, 1f);
        if (dyingCounter>i*2 && dyingCounter<=i*3) changeAlpha(g2, 0f);
        if (dyingCounter>i*3 && dyingCounter<=i*4) changeAlpha(g2, 1f);
        if (dyingCounter>i*4 && dyingCounter<=i*5) changeAlpha(g2, 0f);
        if (dyingCounter>i*5 && dyingCounter<=i*6) changeAlpha(g2, 1f);
        if (dyingCounter>i*6 && dyingCounter<=i*7) changeAlpha(g2, 0f);
        if (dyingCounter>i*7 && dyingCounter<=i*8) changeAlpha(g2, 1f);
        if (dyingCounter>=i*8){
            dying = false;
            alive = false;
        }
    }

    public void changeAlpha(Graphics2D g2, float v) {
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, v));
    }

    public BufferedImage setup(String imagePath, int width, int height) {
        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;
        try {
            image = ImageIO.read(getClass().getResourceAsStream(imagePath + ".png"));
            image = uTool.scaleImage(image, width, height);
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return image;
    }

}
