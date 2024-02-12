package Entity;

import Main.ClickHandler;
import Main.GamePanel;
import Main.KeyHandler;
import object.OBJ_Key;
import object.OBJ_Shield_Wood;
import object.OBJ_Sword_Normal;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.security.Key;
import java.util.ArrayList;

public class Player extends Entity{
    KeyHandler keyH;
    ClickHandler clickH;
    public final int screenX;
    public final int screenY;

    public int hasKey = 0;
    public int hasBullet;
    public ArrayList<Entity> inventory = new ArrayList<>();
    public final int maxInventorySize = 20;
    public Player(GamePanel gp, KeyHandler keyH, ClickHandler clickH){
        super(gp);
        this.keyH = keyH;
        this.clickH = clickH;

        screenX = gp.screenWidth/2 - (gp.tileSize/2);
        screenY = gp.screenHeight/2 - (gp.tileSize/2);

        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 16;
        solidAreaDefautX = solidArea.x;
        solidAreaDefautY = solidArea.y;
        solidArea.width = 32;
        solidArea.height = 32;

        attackArea.width = 36;
        attackArea.height = 36;


        setDefaultValue();
        getPlayerImage();
        getPlayerAttackImage();
        setItem();
    }



    public void setDefaultValue(){
        worldX = gp.tileSize*23;
        worldY = gp.tileSize*21;
        speed = 4;
        direction = "down";
        //PLAYER STATUS
        level = 1;
        maxLife = 6;
        life = maxLife;
        hasBullet = 60;
        strength = 1;
        dexterity = 1;
        exp = 0;
        nextLevelExp = 5;
        coin = 0;
        currentWeapon = new OBJ_Sword_Normal(gp);
        currentShield = new OBJ_Shield_Wood(gp);
        attack = getAttack();
        defense = getDefense();
    }

    public void setItem(){
        inventory.add(currentWeapon);
        inventory.add(currentShield);
        inventory.add(new OBJ_Key(gp));
    }

    public int getDefense() {
        return defense = dexterity* currentShield.defenseValue;
    }

    public int getAttack() {
        return attack = strength* currentWeapon.attackValue;
    }

    public void getPlayerImage(){

        up1 = setup("/Image/etity_image/boy_up_1", gp.tileSize, gp.tileSize);
        up2 = setup("/Image/etity_image/boy_up_2", gp.tileSize, gp.tileSize);
        down1 = setup("/Image/etity_image/boy_down_1", gp.tileSize, gp.tileSize);
        down2 = setup("/Image/etity_image/boy_down_2", gp.tileSize, gp.tileSize);
        left1 = setup("/Image/etity_image/boy_left_1", gp.tileSize, gp.tileSize);
        left2 = setup("/Image/etity_image/boy_left_2", gp.tileSize, gp.tileSize);
        right1 = setup("/Image/etity_image/boy_right_1", gp.tileSize, gp.tileSize);
        right2 = setup("/Image/etity_image/boy_right_2", gp.tileSize, gp.tileSize);
    }

    public void getPlayerAttackImage(){
        attackUp1 = setup("/Image/etity_image/boy_attack_up_1", gp.tileSize, gp.tileSize*2);
        attackUp2 = setup("/Image/etity_image/boy_attack_up_2", gp.tileSize, gp.tileSize*2);
        attackDown1 = setup("/Image/etity_image/boy_attack_down_1", gp.tileSize, gp.tileSize*2);
        attackDown2 = setup("/Image/etity_image/boy_attack_down_2", gp.tileSize, gp.tileSize*2);
        attackLeft1 = setup("/Image/etity_image/boy_attack_left_1", gp.tileSize*2, gp.tileSize);
        attackLeft2 = setup("/Image/etity_image/boy_attack_left_2", gp.tileSize*2, gp.tileSize);
        attackRight1 = setup("/Image/etity_image/boy_attack_right_1", gp.tileSize*2, gp.tileSize);
        attackRight2 = setup("/Image/etity_image/boy_attack_right_2", gp.tileSize*2, gp.tileSize);
    }
    public void update(){
        if (attacking){
            attacking();
        }
        else {
            if (life == 0){
                gp.ui.playerDied = true;
                gp.stopMusic();
            }
            else {
                if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed || keyH.enterPressed || keyH.spacePressed){
                    if (keyH.upPressed) {
                        direction = "up";
                    } else if (keyH.downPressed) {
                        direction = "down";
                    } else if (keyH.leftPressed) {
                        direction = "left";
                    } else if (keyH.rightPressed){
                        direction = "right";
                    }
                    //CHECK TILE COLLISION
                    colissionOn = false;
                    gp.coChecker.checkTile(this);
                    //CHECK OBJECT COLLISION
                    int objIndex = gp.coChecker.checkObject(this, true);
                    pickObject(objIndex);
                    //CHECK NPC COLLISION
                    int npcIndex = gp.coChecker.checkEntity(this, gp.npc);
                    interactNPC(npcIndex);
                    //CHECK MONSTER
                    int monsterIndex = gp.coChecker.checkEntity(this, gp.monster);
                    contactMonster(monsterIndex);
                    //CHECK BULLET
                    int bulletIndex = gp.coChecker.checkEntity(this, gp.bullet);
                    clearBullet(bulletIndex);
                    //CHECK EVENT
                    gp.eventHandler.checkEvent();
                    //CHECK COLLECTION IS FALSE, PLAYER CAN MOVE
                    if (!colissionOn && !gp.keyH.enterPressed) {
                        switch (direction){
                            case "up": worldY -= speed; break;
                            case "down": worldY += speed; break;
                            case "left": worldX -= speed; break;
                            case "right": worldX += speed; break;
                        }
                    }
                    gp.keyH.enterPressed = false;

                    spiteCount++;
                    if (spiteCount>12){
                        if (spiteNum==1) spiteNum=2;
                        else if (spiteNum==2) {
                            spiteNum=1;
                        }
                        spiteCount=0;
                    }
                }
                if (invincible){
                    invincibleCounter++;
                    if (invincibleCounter > 60){
                        invincibleCounter = 0;
                        invincible = false;
                    }
                }
            }
        }


    }

    public void attacking() {

        spiteCount++;
        if (spiteCount <= 5) spiteNum = 1;
        if (spiteCount > 5 && spiteCount <=25) {
            spiteNum = 2;

            //Save the current X, Y
            int currentWorldX = worldX;
            int currentWorldY = worldY;
            int solidAreaWidth = solidArea.width;
            int solidAreaHeight = solidArea.height;

            //Adjust Player AttactArea
            switch (direction){
                case "up": worldY -= attackArea.height;break;
                case "down": worldY += attackArea.height;break;
                case "left": worldX -= attackArea.width;break;
                case "right": worldX += attackArea.width;break;
            }
            solidArea.width = attackArea.width;
            solidArea.height = attackArea.height;

            //Check Hit
            int monsterIndex = gp.coChecker.checkEntity(this, gp.monster);
            damageMonster(monsterIndex);

            //After Check
            worldX = currentWorldX;
            worldY = currentWorldY;
            solidArea.width = solidAreaWidth;
            solidArea.height = solidAreaHeight;

        }
        if (spiteCount > 25) {
            spiteNum = 1;
            spiteCount = 0;
            attacking = false;
        }

    }

    public void damageMonster(int i) {
        if (i!=999) {
            if (!gp.monster[i].invincible){
                gp.playSE(7);
                gp.monster[i].life -= 1;
                gp.monster[i].invincible = true;
                gp.monster[i].damageReaction();
                if (gp.monster[i].life <= 0){
                    gp.monster[i].dying = true;
                }
            }
        }
    }

    public void clearBullet(int i) {
        if (i!=999){
            gp.bullet[i] = null;
        }
    }

    public void contactMonster(int i) {
        if (i!=999){
            if (!invincible){
                if (life > 0){
                    gp.playSE(8);
                    life -=1;
                    invincible = true;
                }
                else {
                    gp.ui.playerDied = true;
                    gp.stopMusic();
                }

            }
        }
    }

    public void interactNPC(int i) {
        if (gp.keyH.enterPressed) {
            if (i != 999) {
                gp.gameState = gp.dialogueState;
                gp.npc[i].speak();
            }
        }
        if (gp.keyH.spacePressed)
        {
            gp.playSE(9);
            attacking = true;
        }
    }

    public void pickObject(int i) {
        if (i!=999){
            String objectName = gp.obj[i].name;
            switch (objectName){
                case "Key":
                    gp.playSE(1);
                    hasKey++;
                    gp.obj[i] = null;
                    gp.ui.showMesseger("You got a Key");
                    break;
                case "Door":
                    if (hasKey>0){
                        gp.playSE(3);
                        hasKey--;
                        gp.obj[i] = null;
                        gp.ui.showMesseger("You opened the door!");
                    }
                    else gp.ui.showMesseger("You need a Key");
                    break;
                case "Chest":
                    gp.playSE(4);
                    gp.obj[i] = null;
                    gp.ui.showMesseger("MINH TRI Victory!");
                    gp.ui.gameFinished = true;
                    gp.stopMusic();
                    break;
                case "Boots":
                    gp.playSE(2);
                    speed +=2;
                    gp.obj[i] = null;
                    gp.ui.showMesseger("Speed faster!");
                    break;
            }

        }
    }

    public void draw(Graphics2D g2){
        BufferedImage image = null;
        int tempScreenX = screenX;
        int tempScreenY = screenY;

        switch (direction){
            case "up":
                if (!attacking){
                    if (spiteNum==1) image = up1;
                    if (spiteNum==2) image = up2;
                }
                if (attacking){
                    tempScreenY = screenY - gp.tileSize;
                    if (spiteNum==1) image = attackUp1;
                    if (spiteNum==2) image = attackUp2;
                }
                break;
            case "down":
                if (!attacking){
                    if (spiteNum==1) image = down1;
                    if (spiteNum==2) image = down2;
                }
                if (attacking){
                    if (spiteNum==1) image = attackDown1;
                    if (spiteNum==2) image = attackDown2;
                }
                break;
            case "left":
                if (!attacking){
                    if (spiteNum==1) image = left1;
                    if (spiteNum==2) image = left2;
                }
                if (attacking){
                    tempScreenX = screenX - gp.tileSize;
                    if (spiteNum==1) image = attackLeft1;
                    if (spiteNum==2) image = attackLeft2;
                }
                break;
            case "right":
                if (!attacking){
                    if (spiteNum==1) image = right1;
                    if (spiteNum==2) image = right2;
                }
                if (attacking){
                    if (spiteNum==1) image = attackRight1;
                    if (spiteNum==2) image = attackRight2;
                }
                break;
        }
        if (invincible){
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
        }
        g2.drawImage(image, tempScreenX, tempScreenY, null);
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
    }
}
