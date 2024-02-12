package Entity;

import Main.GamePanel;

public class Bullet extends Entity{
    GamePanel gp;
    public Bullet(GamePanel gp){
        super(gp);
        this.gp = gp;
        name = "Bullet";
        direction = "down";
        collision = false;
        speed = 12;
        maxLife = 6;
        life = maxLife;
        type = 3;

        solidArea.x = 3;
        solidArea.y = 18;
        solidArea.width = 42;
        solidArea.height = 15;
        solidAreaDefautX = solidArea.x;
        solidAreaDefautY = solidArea.y;

        getBulletImage();

    }

    public void getBulletImage() {
        up1 = setup("/Image/Bullet/bullet_up", gp.tileSize, gp.tileSize);
        up2 = setup("/Image/Bullet/bullet_up", gp.tileSize, gp.tileSize);
        down1 = setup("/Image/Bullet/bullet_down", gp.tileSize, gp.tileSize);
        down2 = setup("/Image/Bullet/bullet_down", gp.tileSize, gp.tileSize);
        left1 = setup("/Image/Bullet/bullet_left", gp.tileSize, gp.tileSize);
        left2 = setup("/Image/Bullet/bullet_left", gp.tileSize, gp.tileSize);
        right1 = setup("/Image/Bullet/bullet_right", gp.tileSize, gp.tileSize);
        right2 = setup("/Image/Bullet/bullet_right", gp.tileSize, gp.tileSize);
    }

    @Override
    public void setAction(){
        int i = gp.coChecker.checkEntity(this, gp.monster);
        if (i!=999) {
            if (!gp.monster[i].invincible){
                gp.monster[i].life -= 2;
                gp.monster[i].invincible = true;
                if (gp.monster[i].life <= 0){
                    gp.monster[i] = null;
                }
            }
            bulletHit = true;
        }
    }
}
