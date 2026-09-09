import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

public class Box extends BaseObject {

    private int vertexArray;

    public Box() {
        super("Box");
        this.vertexArray = 0;
    }

    @Override
    public void Init() {
        InitVertex();
    }

    @Override
    public void Update() {

    }

    @Override
    public void Draw() {
        GL30.glBindVertexArray(vertexArray);
        GL11.glDrawElements(GL11.GL_TRIANGLES, 6, GL11.GL_UNSIGNED_INT, 0);
    }

    @Override
    public void Release() {
        GL30.glDeleteVertexArrays(vertexArray);
    }

    public void InitVertex() {
        float[] vertices = {
                -0.5f, 0.5f, 0.0f,
                -0.5f, -0.5f, 0.0f,
                0.5f, 0.5f, 0.0f,
                0.5f, -0.5f, 0.0f
        };

        int[] index = {
                0, 1, 2,
                2, 1, 3
        };

        this.vertexArray = GL30.glGenVertexArrays();
        GL30.glBindVertexArray(vertexArray);

        int vertexBuffer = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vertexBuffer);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, vertices, GL15.GL_STATIC_DRAW);

        int elementBuffer = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, elementBuffer);
        GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, index, GL15.GL_STATIC_DRAW);

        GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, 3 * Float.BYTES, 0);
        GL20.glEnableVertexAttribArray(0);
    }
}
