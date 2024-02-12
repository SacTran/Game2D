package Main;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ClickHandler extends Frame implements MouseListener {
    GamePanel gp;
    public ClickHandler(GamePanel gp){
        this.gp = gp;
        gp.addMouseListener(this);

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (gp.gameState == gp.playState && e.getClickCount() >= 2){
            gp.aSetter.setBullet();
            gp.player.hasBullet--;
            int i = 0;
            gp.playSE(3);
            switch (gp.player.direction){
                case "up":
                    gp.bullet[i].direction = "up";
                    gp.bullet[i].worldX = gp.player.worldX;
                    gp.bullet[i].worldY = gp.player.worldY - gp.tileSize;
                    break;
                case "down":
                    gp.bullet[i].direction = "down";
                    gp.bullet[i].worldX = gp.player.worldX;
                    gp.bullet[i].worldY = gp.player.worldY +gp.tileSize;
                    break;
                case "left":
                    gp.bullet[i].direction = "left";
                    gp.bullet[i].worldX = gp.player.worldX - gp.tileSize;
                    gp.bullet[i].worldY = gp.player.worldY ;
                    break;
                case "right":
                    gp.bullet[i].direction = "right";
                    gp.bullet[i].worldX = gp.player.worldX + gp.tileSize;
                    gp.bullet[i].worldY = gp.player.worldY;
                    break;
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        System.out.println("Mouse pressed; # of clicks: "
                + e.getClickCount());

    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
