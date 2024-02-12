package object;

import Entity.Entity;
import Main.GamePanel;

public class OBJ_Shield_Wood extends Entity {
    public OBJ_Shield_Wood(GamePanel gp){
        super(gp);
        name = "Shield Wood";
        down1 = setup("/Image/Object/shield_wood", gp.tileSize, gp.tileSize);
        defenseValue = 1;
        description = "["+name+"]/breakAn old Shield.";
    }
}
