package Main;

import java.awt.*;

public class EventHandler {
    GamePanel gp;
    EventRect eventRect[][];
    int previousEventX, previousEventY;
    Boolean canTouchEvent = true;
    public EventHandler(GamePanel gp){
        this.gp = gp;
        eventRect = new EventRect[gp.maxWorldCol][gp.maxWorldRow];
        int col = 0;
        int row = 0;
        while (col < gp.maxWorldCol && row < gp.maxWorldRow){

            eventRect[col][row] = new EventRect();
            eventRect[col][row].x = 23;
            eventRect[col][row].y = 23;
            eventRect[col][row].width = 2;
            eventRect[col][row].height = 2;
            eventRect[col][row].eventRectDefaultX = eventRect[col][row].x;
            eventRect[col][row].eventRectDefaultY = eventRect[col][row].y;

            col++;
            if (col == gp.maxWorldCol){
                col = 0;
                row++;
            }

        }


    }
    public void checkEvent(){
        //CHECK EVENT
        int xDistance = Math.abs(gp.player.worldX - previousEventX);
        int yDistance = Math.abs(gp.player.worldY - previousEventY);
        int distance = Math.max(xDistance, yDistance);
        if (distance > gp.tileSize){
            canTouchEvent = true;
        }
        if (canTouchEvent){
            if (hit(28,15, "up")){
                //Event happens
                damagePit(28,15, false);
            }
            if (hit(23,19, "any")){
                //Event happens
                damagePit(23,19, true);
            }

            if (hit(23,12,"up")){
                healingPool(23, 12);
            }
        }

        if (hit(28,17, "down")){
            //Event happens
            telePort(28, 17, false);
        }

    }

    public void telePort(int col, int row, Boolean done) {
        gp.ui.showMesseger("Teleport !!");
        gp.player.worldX = gp.tileSize*37;
        gp.player.worldY = gp.tileSize*10;
        eventRect[col][row].eventDone = done;
    }

    public void damagePit(int col, int row, Boolean done) {
        if (gp.player.life > 0) gp.player.life--;
        if (gp.player.life == 0){
            gp.ui.playerDied = true;
            gp.stopMusic();
        }
        gp.ui.showMesseger("You fall to Pit");
        gp.playSE(5);
        eventRect[col][row].eventDone = done;
        canTouchEvent = false;
    }

    public void healingPool(int col, int row){
        gp.ui.showMesseger("Your life has been recovered!");
        gp.playSE(6);
        //gp.ui.currentDiagues = "You drink the water. break/ Your life has been recovered!";
        if (gp.player.life < gp.player.maxLife){
            gp.player.life +=1;
        }
        //eventRect[col][row].eventDone = true;
        canTouchEvent = false;
    }

    public boolean hit(int col, int row, String reqDirection){
        boolean hit = false;
        gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
        gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;
        eventRect[col][row].x = col * gp.tileSize + eventRect[col][row].x;
        eventRect[col][row].y = row * gp.tileSize + eventRect[col][row].y;
        if (gp.player.solidArea.intersects(eventRect[col][row]) && eventRect[col][row].eventDone == false) {
            if (gp.player.direction.contentEquals(reqDirection) || reqDirection.contentEquals("any")){
                hit = true;
                previousEventX = gp.player.worldX;
                previousEventY = gp.player.worldY;
            }
        }
        gp.player.solidArea.x = gp.player.solidAreaDefautX;
        gp.player.solidArea.y = gp.player.solidAreaDefautY;
        eventRect[col][row].x = eventRect[col][row].eventRectDefaultX;
        eventRect[col][row].y = eventRect[col][row].eventRectDefaultY;

        return hit;
    }
}
