package Entity;

import Main.GamePanel;

import java.util.Random;

public class Monster_Slime extends Entity{
    public Monster_Slime(GamePanel gp){
        super(gp);
        name = "Slime";
        speed = 2;
        maxLife = 20;
        life = maxLife;
        type = 2;

        solidArea.x = 3;
        solidArea.y = 18;
        solidArea.width = 42;
        solidArea.height = 30;
        solidAreaDefautX = solidArea.x;
        solidAreaDefautY = solidArea.y;

        getImage();
    }
    public void getImage(){
        up1 = setup("/Image/monster/slime_down1", gp.tileSize, gp.tileSize);
        up2 = setup("/Image/monster/slime_down2", gp.tileSize, gp.tileSize);
        down1 = setup("/Image/monster/slime_down1", gp.tileSize, gp.tileSize);
        down2 = setup("/Image/monster/slime_down2", gp.tileSize, gp.tileSize);
        left1 = setup("/Image/monster/slime_down1", gp.tileSize, gp.tileSize);
        left2 = setup("/Image/monster/slime_down2", gp.tileSize, gp.tileSize);
        right1 = setup("/Image/monster/slime_down1", gp.tileSize, gp.tileSize);
        right2 = setup("/Image/monster/slime_down2", gp.tileSize, gp.tileSize);
    }
    public void setAction(){
        actionLockCounter++;
        if (actionLockCounter == 120){
            Random random = new Random();
            int i= random.nextInt(100)+1;
            if (i<=25) direction = "up";
            if (i>25 && i<=50) direction = "down";
            if (i>50 && i<=75) direction = "left";
            if (i>75) direction = "right";
            actionLockCounter = 0;
        }
    }

    @Override
    public void damageReaction() {
        actionLockCounter = 0;
        direction = gp.player.direction;
    }
}
