package object;

import Entity.Entity;
import Main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Key extends Entity {
    public OBJ_Key(GamePanel gp){
        super(gp);
        name = "Key";
        down1 = setup("/Image/Object/key", gp.tileSize, gp.tileSize);
        description = "["+name+"]/breakit open the door.";
    }
}
