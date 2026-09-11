import org.joml.Vector3f;
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
        Transform top = new Transform();
        top.location = new Vector3f(100, 100, 0);
        Transform left = new Transform();
        left.location = new Vector3f(0, 150, 0);
        Transform right = new Transform();
        right.location = new Vector3f(200, 150, 0);
        ObjectManager.AddObject(new Triangle(new float[]{1.0f, 1.0f, 1.0f, 1.0f}, new Transform[]{top ,left, right}));
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
