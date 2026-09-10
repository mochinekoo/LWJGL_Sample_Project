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
        ObjectManager.AddObject(new Box(1.0f, 0.0f, 0.0f, 1.0f));
    }

    @Override
    public void Update() {

    }

    @Override
    public void Draw() {
        GL11.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
        GL30.glUseProgram(Main.mainShader);
    }

    @Override
    public void Release() {

    }

}
