package object;

import Entity.Entity;
import Main.GamePanel;

public class OBJ_Heart extends Entity {
    public OBJ_Heart(GamePanel gp){
        super(gp);
        name = "Heart";
        image = setup("/Image/Object/heart_blank", gp.tileSize, gp.tileSize);
        image1 = setup("/Image/Object/heart_half", gp.tileSize, gp.tileSize);
        image2 = setup("/Image/Object/heart_full", gp.tileSize, gp.tileSize);

    }
}
