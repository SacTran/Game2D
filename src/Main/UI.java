package Main;

import Entity.Entity;
import object.OBJ_Heart;
import object.OBJ_Key;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;

public class UI {
    GamePanel gp;
    Graphics2D g2;
    Font maruMonica, purisaB, arial_40, arial_80B, arial_40B, arial_32;
    public int commandNum = 1;
    public int titleScreenState = 0;
    BufferedImage keyImage, heart_full, heart_haft, heart_blank, bulletImage;
    public Boolean messageOn = false;
    public String message = "";
    int messageCount = 0;
    public Boolean gameFinished = false;
    public Boolean playerDied = false;
    public String currentDiagues = "";
    double playTime;
    DecimalFormat decimalFormat = new DecimalFormat("#0.00");
    public int slotCol = 0;
    public int slotRow = 0;
    public UI(GamePanel gp){
        this.gp = gp;
        arial_40 = new Font("Arial", Font.PLAIN, 24);
        arial_32 = new Font("Arial", Font.PLAIN, 32);
        arial_80B = new Font("Arial",Font.BOLD, 80);
        arial_40B = new Font("Arial", Font.BOLD, 40);
        try {
            InputStream is = getClass().getResourceAsStream("/Image/Monica-Allcaps-Font/Monica Allcaps Regular.otf");
            maruMonica = Font.createFont(Font.TRUETYPE_FONT, is);
            is = getClass().getResourceAsStream("/Image/Monica-Allcaps-Font/Monica Allcaps Stencil.otf");
            purisaB = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (FontFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        OBJ_Key key = new OBJ_Key(gp);
        keyImage = key.down1;

        //CREATE BULLET OBJECT
        Entity bl = new Entity(gp);
        bulletImage = bl.down1;


        //CREATE HEART OBJECT
        Entity heart = new OBJ_Heart(gp);
        heart_blank = heart.image;
        heart_haft = heart.image1;
        heart_full = heart.image2;
    }
    public void showMesseger(String text){
        messageOn=true;
        message = text;
    }
    public void draw(Graphics2D g2){
        if (playerDied == true){
            String text;
            int textLength,x,y;


            g2.setFont(arial_40B);
            g2.setColor(Color.RED);
            text = "You has been Died!!!";
            textLength = (int)g2.getFontMetrics().getStringBounds(text,g2).getWidth();

            x = gp.screenWidth/2 - textLength/2;
            y = gp.screenHeight/2 + (gp.tileSize*2);
            g2.drawString(text, x, y);


            gp.gameThread = null;
        }
        if (gameFinished ==true){
            g2.setFont(arial_40);
            g2.setColor(Color.white);
            String text;
            int textLength,x,y;

            text = "You found the Treasure";
            textLength = (int)g2.getFontMetrics().getStringBounds(text,g2).getWidth();

            x = gp.screenWidth/2 - textLength/2;
            y = gp.screenHeight/2 - (gp.tileSize*3);
            g2.drawString(text, x, y);
            g2.drawString("Time:" + decimalFormat.format(playTime), gp.tileSize*11, 65);

            g2.setFont(arial_80B);
            g2.setColor(Color.yellow);
            text = "Congratulations";
            textLength = (int)g2.getFontMetrics().getStringBounds(text,g2).getWidth();

            x = gp.screenWidth/2 - textLength/2;
            y = gp.screenHeight/2 + (gp.tileSize*2);
            g2.drawString(text, x, y);


            gp.gameThread = null;

        }
        else {
            g2.setFont(arial_40);
            g2.setColor(Color.white);
            g2.drawImage(keyImage, gp.tileSize/2, gp.tileSize/2,gp.tileSize,gp.tileSize,null );
            g2.drawString("x" + gp.player.hasKey,75, 65);
//            g2.drawImage(bulletImage, gp.tileSize/2, gp.tileSize*23,gp.tileSize,gp.tileSize,null );
//            g2.drawString("x" + gp.player.hasBullet,440, 65);
            if (gp.gameState == gp.playState){
                playTime += (double)1/60;
            }
            g2.drawString("Time:" + decimalFormat.format(playTime), gp.tileSize*11, 65);

            if (messageOn) {
                g2.setFont(g2.getFont().deriveFont(30F));
                g2.drawString(message, gp.tileSize/2, gp.tileSize*5);
                messageCount++;
                if (messageCount>150){
                    messageOn=false;
                    messageCount=0;
                }
            }
        }
        this.g2 = g2;
        g2.setFont(purisaB);
        g2.setColor(Color.WHITE);

        //TITLE STATE
        if (gp.gameState == gp.titleState){
            titleDrawScreen();
        }

        //PLAY STATE
        if (gp.gameState == gp.playState){
            //Do playstate stuff later
            //gp.playMusic(0);
            drawPlayerLife();
        }

        //PAUSE STATE
        if (gp.gameState == gp.pauseState){
            drawPlayerLife();
            pauseDrawScreen();
            gp.stopMusic();
        }

        //DIALOGUE STATE
        if (gp.gameState == gp.dialogueState){
            drawPlayerLife();
            dialoguesDrawScreen();
        }
        //CHARACTER STATE
        if (gp.gameState == gp.characterState){
            drawCharacterScreen();
            drawInventory();
        }

    }

    public void drawCharacterScreen() {
        //CREATE SUB SCREEN
        final int frameX = gp.tileSize *2;
        final int frameY = gp.tileSize;
        final int frameWidth = gp.tileSize *5;
        final int frameHeight = gp.tileSize * 10;
        drawSubScreen(frameX,frameY,frameWidth,frameHeight);
        //TEXT
        g2.setColor(Color.WHITE);
        g2.setFont(arial_32);
        int textX = frameX + 20;
        int textY = frameY + gp.tileSize;
        final int lineHeight = 35;
        //DRAW TEXT
        g2.drawString("Level", textX, textY);
        textY += lineHeight;
        g2.drawString("Life",textX,textY);
        textY += lineHeight;
        g2.drawString("Strength",textX,textY);
        textY += lineHeight;
        g2.drawString("Dexterity",textX,textY);
        textY += lineHeight;
        g2.drawString("Attack",textX,textY);
        textY += lineHeight;
        g2.drawString("Defense",textX,textY);
        textY += lineHeight;
        g2.drawString("Exp",textX,textY);
        textY += lineHeight;
        g2.drawString("Bullet",textX,textY);
        textY += lineHeight;
        g2.drawString("Next Level",textX,textY);
        textY += lineHeight;
        g2.drawString("Coin",textX,textY);
        textY += lineHeight;
        g2.drawString("Weapon",textX,textY+20);
        textY += lineHeight;
        g2.drawString("Shield",textX,textY+25);
        //VALUE
        int tailX = (frameX + frameWidth) - 30;
        textY = frameY + gp.tileSize;
        String value;
        value = String.valueOf(gp.player.level);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX,textY);

        textY += lineHeight;
        value = String.valueOf(gp.player.life + "/" + gp.player.maxLife);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX,textY);

        textY += lineHeight;
        value = String.valueOf(gp.player.strength);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX,textY);

        textY += lineHeight;
        value = String.valueOf(gp.player.dexterity);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX,textY);

        textY += lineHeight;
        value = String.valueOf(gp.player.attack);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX,textY);

        textY += lineHeight;
        value = String.valueOf(gp.player.defense);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX,textY);

        textY += lineHeight;
        value = String.valueOf(gp.player.exp);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX,textY);

        textY += lineHeight;
        value = String.valueOf(gp.player.hasBullet);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX,textY);

        textY += lineHeight;
        value = String.valueOf(gp.player.nextLevelExp);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX,textY);

        textY += lineHeight;
        value = String.valueOf(gp.player.coin);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX,textY);

        textY += lineHeight;
        g2.drawImage(gp.player.currentWeapon.down1, tailX - gp.tileSize, textY-20, null);
        textY += lineHeight;
        g2.drawImage(gp.player.currentShield.down1,tailX - gp.tileSize, textY-15, null);


    }
    public void drawInventory(){

        int frameX = gp.tileSize*9;
        int frameY = gp.tileSize;
        int frameWidth = gp.tileSize*6;
        int frameHeight = gp.tileSize*5;
        drawSubScreen(frameX,frameY,frameWidth,frameHeight);

        //SLOT
        final int slotXStart = frameX + 20;
        final int slotYStart = frameY + 20;
        int slotX = slotXStart;
        int slotY = slotYStart;
        int slotSize = gp.tileSize + 3;

        //DRAW PLAYER'S ITEM
        for (int i = 0; i < gp.player.inventory.size(); i++){
            g2.drawImage(gp.player.inventory.get(i).down1, slotX, slotY, null);
            slotX += slotSize;
            if (i == 4 || i == 9 || i == 14){
                slotX = slotXStart;
                slotY += slotSize;
            }
        }

        //CURSOR
        int cursorX = slotXStart + (slotSize * slotCol);
        int cursorY = slotYStart + (slotSize * slotRow);
        int cursorWidth = gp.tileSize;
        int cursorHeight = gp.tileSize;

        //DRAW CURSOR
        g2.setColor(Color.WHITE);
        g2.setStroke(new BasicStroke(3));
        g2.drawRoundRect(cursorX,cursorY,cursorWidth,cursorHeight,10,10);

        //DESCRIPTION FRAME
        int dFrameX = frameX;
        int dFrameY = frameY + frameHeight;
        int dFrameWidth = frameWidth;
        int dFrameHeight = gp.tileSize*3;
        drawSubScreen(dFrameX,dFrameY,dFrameWidth,dFrameHeight);
        //DRAW DISCRIPTION TEXT
        int textX = dFrameX + 20;
        int textY = dFrameY + gp.tileSize;
        g2.setFont(g2.getFont().deriveFont(28F));

        int itemIdex = getItemIndexOnSlot();
        if (itemIdex < gp.player.inventory.size()){
            for (String line: gp.player.inventory.get(itemIdex).description.split("/break")){
                g2.drawString(line,textX,textY);
                textY += 32;
            }
        }

    }

    public int getItemIndexOnSlot(){
        int itemIndex = slotCol + (slotRow * 5);
        return itemIndex;
    }

    public void drawPlayerLife() {

        int x = gp.tileSize*3;
        int y = gp.tileSize/2;
        int i = 0;

        while (i < gp.player.maxLife/2){
            g2.drawImage(heart_blank, x, y, null);
            i++;
            x += gp.tileSize;
        }

        x = gp.tileSize*3;
        y = gp.tileSize/2;
        i = 0;
        while (i < gp.player.life){
            g2.drawImage(heart_haft, x,y,null);
            i++;
            if (i < gp.player.life){
                g2.drawImage(heart_full, x, y, null);
            }
            i++;
            x += gp.tileSize;
        }

    }

    public void titleDrawScreen() {
        g2.setColor(new Color(70,120,100));
        g2.fillRect(0,0, gp.screenWidth, gp.screenHeight);
        //TITLE NAME
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 40F));
        String text = "ADVENTURE GAMING GUNNER";
        int x = getXforCenterText(text);
        int y = gp.tileSize * 2;
        //SHADOW
        g2.setColor(Color.BLACK);
        g2.drawString(text, x+3, y+3);
        //MAIN COLOR
        g2.setColor(Color.WHITE);
        g2.drawString(text, x, y);
        //BLUE IMAGE
        x = gp.screenWidth/2 - gp.tileSize;
        y += gp.tileSize;
        g2.drawImage(gp.player.down1,x,y,gp.tileSize*2, gp.tileSize*2, null);
        //MENU
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 40F));
        g2.setFont(arial_40B);
        
        if (titleScreenState == 0){
            g2.setColor(Color.BLACK);
            text = "NEW GAME";
            x = getXforCenterText(text);
            y += gp.tileSize *4;
            g2.drawString(text,x+2,y+2);
            g2.setColor(Color.WHITE);
            g2.drawString(text,x,y);
            if (commandNum == 1){
                g2.drawString(">",x - gp.tileSize, y);
            }

            g2.setColor(Color.BLACK);
            text = "LOAD GAME";
            x = getXforCenterText(text);
            y += gp.tileSize * 3/2;
            g2.drawString(text,x+2,y+2);
            g2.setColor(Color.WHITE);
            g2.drawString(text,x,y);
            if (commandNum == 2){
                g2.drawString(">",x - gp.tileSize, y);
            }

            g2.setColor(Color.BLACK);
            text = "EXIT";
            x = getXforCenterText(text);
            y += gp.tileSize * 3/2;
            g2.drawString(text,x+2,y+2);
            g2.setColor(Color.WHITE);
            g2.drawString(text,x,y);
            if (commandNum == 3){
                g2.drawString(">",x - gp.tileSize, y);
            }
        } else if (titleScreenState == 1) {
            //CLASS SELECT
            g2.setFont(g2.getFont().deriveFont(42F));
            g2.setColor(Color.WHITE);

            text = "Select your class:";
            x = getXforCenterText(text);
            y += gp.tileSize*3;
            g2.drawString(text, x, y);


            text = "Fighter";
            x = getXforCenterText(text);
            y += gp.tileSize;
            g2.drawString(text, x, y);
            if (commandNum == 1){
                g2.drawString(">",x - gp.tileSize, y);
            }

            text = "Thief";
            x = getXforCenterText(text);
            y += gp.tileSize;
            g2.drawString(text, x, y);
            if (commandNum == 2){
                g2.drawString(">",x - gp.tileSize, y);
            }

            text = "Sorcerer";
            x = getXforCenterText(text);
            y += gp.tileSize;
            g2.drawString(text, x, y);
            if (commandNum == 3){
                g2.drawString(">",x - gp.tileSize, y);
            }

            text = "Back";
            x = getXforCenterText(text);
            y += gp.tileSize;
            g2.drawString(text, x, y);
            if (commandNum == 4){
                g2.drawString(">",x - gp.tileSize, y);
            }

        }


    }

    public void pauseDrawScreen(){
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 80F));
        String text = "PAUSE";
        int x = getXforCenterText(text);
        int y = gp.screenHeight/2;
        g2.drawString(text,x,y);
    }
    public void dialoguesDrawScreen(){
        int x = gp.tileSize*2;
        int y = gp.tileSize/2;
        int width = gp.screenWidth - (gp.tileSize*4);
        int height = gp.tileSize*4;

        drawSubScreen(x,y,width,height);

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 24F));
        x += gp.tileSize;
        y += gp.tileSize;
        for (String line : currentDiagues.split("break/")){
            g2.drawString(line, x, y);
            y += 40;
        }

    }

    public void drawSubScreen(int x, int y, int width, int height) {
        Color c = new Color(0,0,0,210);
        g2.setColor(c);
        g2.fillRoundRect(x,y,width,height,35,35);

        c = new Color(255,255,255);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x+5,y+5, width-10, height-10, 25,25);
    }

    public int getXforCenterText(String text){
        int length = (int) g2.getFontMetrics().getStringBounds(text,g2).getWidth();
        int x = gp.screenWidth/2 - length/2;
        return x;
    }
    public int getXforAlignToRightText(String text, int y){
        int length = (int) g2.getFontMetrics().getStringBounds(text,g2).getWidth();
        int x = y - length;
        return x;
    }
}
