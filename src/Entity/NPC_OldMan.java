package Entity;

import Main.GamePanel;

import java.util.Random;

public class NPC_OldMan extends Entity{

    public NPC_OldMan(GamePanel gp){
        super(gp);

        direction = "down";
        speed = 1;
        solidArea.x = 0;
        solidArea.y = 16;
        solidArea.width = 48;
        solidArea.height = 32;
        solidAreaDefautX = solidArea.x;
        solidAreaDefautY = solidArea.y;

        getImage();
        setDialogue();

    }
    public void getImage(){

        up1 = setup("/Image/NPC/oldman_up_1", gp.tileSize, gp.tileSize);
        up2 = setup("/Image/NPC/oldman_up_2", gp.tileSize, gp.tileSize);
        down1 = setup("/Image/NPC/oldman_down_1", gp.tileSize, gp.tileSize);
        down2 = setup("/Image/NPC/oldman_down_2", gp.tileSize, gp.tileSize);
        left1 = setup("/Image/NPC/oldman_left_1", gp.tileSize, gp.tileSize);
        left2 = setup("/Image/NPC/oldman_left_2", gp.tileSize, gp.tileSize);
        right1 = setup("/Image/NPC/oldman_right_1", gp.tileSize, gp.tileSize);
        right2 = setup("/Image/NPC/oldman_right_2", gp.tileSize, gp.tileSize);
    }

    @Override
    public void setAction() {
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
        int bulletIndex = gp.coChecker.checkEntity(this, gp.bullet);
        if (bulletIndex != 999){
            gp.bullet[bulletIndex] = null;
        }

    }
    public void setDialogue(){
        dialogues[0] = "Hello BUM BUM!";
        dialogues[1] = "This is 2D games. break/ The finish setter";
        dialogues[2] = "Are you ready?";
        dialogues[3] = "Let's go nhe BUM";
    }
    public void speak(){
        super.speak();
    }
}
