package object;

import Entity.Entity;
import Main.GamePanel;

public class OBJ_Sword_Normal extends Entity {
    public OBJ_Sword_Normal(GamePanel gp){
        super(gp);
        name = "Sword Normal";
        down1 = setup("/Image/Object/sword_normal", gp.tileSize, gp.tileSize);
        attackValue = 4;
        description = "["+name+"]/breakAn old Sword.";
    }
}
