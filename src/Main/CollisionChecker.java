package Main;

import Entity.Entity;

public class CollisionChecker {
    GamePanel gp;
    public  CollisionChecker(GamePanel gp){
        this.gp = gp;

    }
    public void checkTile(Entity entity){
        int entityLeftWorldX = entity.worldX + entity.solidArea.x;
        int entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
        int entityTopWorldY = entity.worldY + entity.solidArea.y;
        int entityBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;

        int entityLeftCol = entityLeftWorldX/gp.tileSize;
        int entityRightCol = entityRightWorldX/gp.tileSize;
        int entityTopRow = entityTopWorldY/gp.tileSize;
        int entityBottomRow = entityBottomWorldY/gp.tileSize;

        int tileNum1, tileNum2;
        switch (entity.direction){
            case "up":
                entityTopRow = (entityTopWorldY-entity.speed)/gp.tileSize;
                tileNum1 = gp.tileM.mapTileNumber[entityLeftCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNumber[entityRightCol][entityTopRow];
                if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision){
                     entity.colissionOn = true;
                }
                break;
            case "down":
                entityBottomRow = (entityBottomWorldY-entity.speed)/gp.tileSize;
                tileNum1 = gp.tileM.mapTileNumber[entityLeftCol][entityBottomRow];
                tileNum2 = gp.tileM.mapTileNumber[entityRightCol][entityBottomRow];
                if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision){
                    entity.colissionOn = true;
                }
                break;
            case "left":
                entityLeftCol = (entityLeftWorldX-entity.speed)/gp.tileSize;
                tileNum1 = gp.tileM.mapTileNumber[entityLeftCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNumber[entityLeftCol][entityBottomRow];
                if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision){
                    entity.colissionOn = true;
                }
                break;
            case "right":
                entityRightCol = (entityRightWorldX-entity.speed)/gp.tileSize;
                tileNum1 = gp.tileM.mapTileNumber[entityRightCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNumber[entityRightCol][entityBottomRow];
                if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision){
                    entity.colissionOn = true;
                }
                break;
        }

    }
    public int checkObject(Entity entity, Boolean player){
        int index = 999;
        for (int i=0; i<gp.obj.length; i++){
            if (gp.obj[i]!= null){
                entity.solidArea.x += entity.worldX;
                entity.solidArea.y += entity.worldY;
                gp.obj[i].solidArea.x += gp.obj[i].worldX;
                gp.obj[i].solidArea.y += gp.obj[i].worldY;
                switch (entity.direction){
                    case "up":
                        entity.solidArea.y -= entity.speed;
                        break;
                    case "down":
                        entity.solidArea.y += entity.speed;
                        break;
                    case "left":
                        entity.solidArea.x -= entity.speed;
                        break;
                    case "right":
                        entity.solidArea.x += entity.speed;
                        break;
                }
                if (entity.solidArea.intersects(gp.obj[i].solidArea)){
                    if (gp.obj[i].collision){
                        entity.colissionOn = true;
                    }
                    if (player){
                        index=i;
                    }
                }
                entity.solidArea.x = entity.solidAreaDefautX;
                entity.solidArea.y = entity.solidAreaDefautY;
                gp.obj[i].solidArea.x = gp.obj[i].solidAreaDefautX;
                gp.obj[i].solidArea.y = gp.obj[i].solidAreaDefautY;
            }
        }
        return index;
    }
    public int checkEntity(Entity entity, Entity[] target){
        int index = 999;
        for (int i=0; i<target.length; i++){
            if (target[i] != null){
                entity.solidArea.x += entity.worldX;
                entity.solidArea.y += entity.worldY;
                target[i].solidArea.x += target[i].worldX;
                target[i].solidArea.y += target[i].worldY;
                switch (entity.direction){
                    case "up":
                        entity.solidArea.y -= entity.speed;
                        break;
                    case "down":
                        entity.solidArea.y += entity.speed;
                        break;
                    case "left":
                        entity.solidArea.x -= entity.speed;
                        break;
                    case "right":
                        entity.solidArea.x += entity.speed;
                        break;
                }
                if (entity.solidArea.intersects(target[i].solidArea)){
                    if (target[i] != entity){
                        entity.colissionOn = true;
                        index=i;
                    }
                }
                entity.solidArea.x = entity.solidAreaDefautX;
                entity.solidArea.y = entity.solidAreaDefautY;
                target[i].solidArea.x = target[i].solidAreaDefautX;
                target[i].solidArea.y = target[i].solidAreaDefautY;
            }
        }
        return index;
    }
    public boolean checkPlayer(Entity entity){
        boolean contactPlayer = false;
        entity.solidArea.x += entity.worldX;
        entity.solidArea.y += entity.worldY;
        gp.player.solidArea.x += gp.player.worldX;
        gp.player.solidArea.y += gp.player.worldY;
        switch (entity.direction){
            case "up": entity.solidArea.y -= entity.speed;break;
            case "down": entity.solidArea.y += entity.speed;break;
            case "left": entity.solidArea.x -= entity.speed;break;
            case "right": entity.solidArea.x += entity.speed;break;
        }
        if (entity.solidArea.intersects(gp.player.solidArea)){
            entity.colissionOn = true;
            contactPlayer = true;
        }
        entity.solidArea.x = entity.solidAreaDefautX;
        entity.solidArea.y = entity.solidAreaDefautY;
        gp.player.solidArea.x = gp.player.solidAreaDefautX;
        gp.player.solidArea.y = gp.player.solidAreaDefautY;

        return contactPlayer;
    }
}
