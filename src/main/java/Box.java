import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.*;

import java.nio.FloatBuffer;

public class Box extends BaseObject {

    private float[] color;

    private int vertexArray;
    private int uniformBuffer;

    public Box() {
        super("Box");
        this.vertexArray = 0;
        this.color = new float[]{0.0f, 0.0f, 0.0f, 1.0f};
    }

    public Box(float r, float g, float b, float a) {
        super("Box");
        this.vertexArray = 0;
        this.color = new float[]{r, g, b, a};
    }

    @Override
    public void Init() {
        InitVertex();
        InitUniformBuffer();
    }

    @Override
    public void Update() {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(4);
        buffer.put(color[0]);
        buffer.put(color[1]);
        buffer.put(color[2]);
        buffer.put(color[3]);
        buffer.flip();

        GL15.glBindBuffer(GL31.GL_UNIFORM_BUFFER, uniformBuffer);
        GL15.glBufferSubData(GL31.GL_UNIFORM_BUFFER, 0, buffer);
        GL15.glBindBuffer(GL31.GL_UNIFORM_BUFFER, 0);
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

    public void InitUniformBuffer() {
        this.uniformBuffer = GL15.glGenBuffers();
        GL15.glBindBuffer(GL31.GL_UNIFORM_BUFFER, this.uniformBuffer);

        GL15.glBufferData(GL31.GL_UNIFORM_BUFFER, 4 * Float.BYTES, GL15.GL_DYNAMIC_DRAW);
        GL30.glBindBufferBase(GL31.GL_UNIFORM_BUFFER, 0, this.uniformBuffer);

        GL15.glBindBuffer(GL31.GL_UNIFORM_BUFFER, 0);
    }

    public float[] GetColor() {
        return color;
    }

}
