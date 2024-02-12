package Main;

import Entity.Bullet;
import Entity.Monster_Slime;
import Entity.NPC_OldMan;
import object.OBJ_Boots;
import object.OBJ_Chest;
import object.OBJ_Door;
import object.OBJ_Key;

public class AssetSetter {
    GamePanel gp;
    public AssetSetter(GamePanel gp){
            this.gp = gp;

    }
    public void setObject(){
        gp.obj[0] = new OBJ_Key(gp);
        gp.obj[0].worldX = 23 * gp.tileSize;
        gp.obj[0].worldY = 7 * gp.tileSize;

        gp.obj[1] = new OBJ_Key(gp);
        gp.obj[1].worldX = 23 * gp.tileSize;
        gp.obj[1].worldY = 40 * gp.tileSize;

        gp.obj[2] = new OBJ_Key(gp);
        gp.obj[2].worldX = 38 * gp.tileSize;
        gp.obj[2].worldY = 8 * gp.tileSize;

        gp.obj[3] = new OBJ_Door(gp);
        gp.obj[3].worldX = 10 * gp.tileSize;
        gp.obj[3].worldY = 12 * gp.tileSize;

        gp.obj[4] = new OBJ_Door(gp);
        gp.obj[4].worldX = 8 * gp.tileSize;
        gp.obj[4].worldY = 28 * gp.tileSize;

        gp.obj[5] = new OBJ_Door(gp);
        gp.obj[5].worldX = 12 * gp.tileSize;
        gp.obj[5].worldY = 22 * gp.tileSize;

        gp.obj[6] = new OBJ_Chest(gp);
        gp.obj[6].worldX = 10 * gp.tileSize;
        gp.obj[6].worldY = 7 * gp.tileSize;

        gp.obj[7] = new OBJ_Boots(gp);
        gp.obj[7].worldX = 37 * gp.tileSize;
        gp.obj[7].worldY = 42 * gp.tileSize;

        gp.obj[8] = new OBJ_Door(gp);
        gp.obj[8].worldX = 28 * gp.tileSize;
        gp.obj[8].worldY = 17 * gp.tileSize;
        gp.obj[8].collision = false;
    }

    public void setNPC(){
        gp.npc[0] = new NPC_OldMan(gp);
        gp.npc[0].worldX = gp.tileSize * 21;
        gp.npc[0].worldY = gp.tileSize * 21;

        gp.npc[1] = new NPC_OldMan(gp);
        gp.npc[1].worldX = gp.tileSize * 37;
        gp.npc[1].worldY = gp.tileSize * 12;
    }

    public void setMonster(){
        gp.monster[0] = new Monster_Slime(gp);
        gp.monster[0].worldX = gp.tileSize * 23;
        gp.monster[0].worldY = gp.tileSize * 26;

        gp.monster[1] = new Monster_Slime(gp);
        gp.monster[1].worldX = gp.tileSize * 37;
        gp.monster[1].worldY = gp.tileSize * 18;

        gp.monster[2] = new Monster_Slime(gp);
        gp.monster[2].worldX = gp.tileSize * 23;
        gp.monster[2].worldY = gp.tileSize * 36;

        gp.monster[3] = new Monster_Slime(gp);
        gp.monster[3].worldX = gp.tileSize * 23;
        gp.monster[3].worldY = gp.tileSize * 37;

        gp.monster[4] = new Monster_Slime(gp);
        gp.monster[4].worldX = gp.tileSize * 24;
        gp.monster[4].worldY = gp.tileSize * 7;

        gp.monster[5] = new Monster_Slime(gp);
        gp.monster[5].worldX = gp.tileSize * 8;
        gp.monster[5].worldY = gp.tileSize * 27;

        gp.monster[6] = new Monster_Slime(gp);
        gp.monster[6].worldX = gp.tileSize * 12;
        gp.monster[6].worldY = gp.tileSize * 24;

        gp.monster[7] = new Monster_Slime(gp);
        gp.monster[7].worldX = gp.tileSize * 22;
        gp.monster[7].worldY = gp.tileSize * 7;
    }

    public void setBullet(){

        gp.bullet[0] = new Bullet(gp);
        gp.bullet[0].worldX = gp.tileSize * 0;
        gp.bullet[0].worldY = gp.tileSize * 0;

        gp.bullet[1] = new Bullet(gp);
        gp.bullet[1].worldX = gp.tileSize * 0;
        gp.bullet[1].worldY = gp.tileSize * 0;

    }
}
