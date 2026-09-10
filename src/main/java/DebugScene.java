import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

public class DebugScene extends BaseScene {

    public DebugScene() {
        super("DebugScene");
    }

    @Override
    public void Init() {
        ObjectManager.AddObject(new Box(200, 100,1.0f, 0.0f, 0.0f, 1.0f));
    }

    @Override
    public void Update() {

    }

    @Override
    public void Draw() {
        GL30.glUseProgram(Main.mainShader);
    }

    @Override
    public void Release() {

    }

}
