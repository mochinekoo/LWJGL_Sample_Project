import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.*;

import java.nio.FloatBuffer;

public class Box extends BaseObject {

    private float[] color;
    private int width, height;

    private int vertexArray;
    private int uniformBuffer;

    public Box() {
        super("Box");
        this.vertexArray = 0;
        this.width = 100;
        this.height = 100;
        this.color = new float[]{0.0f, 0.0f, 0.0f, 1.0f};
    }

    public Box(int width, int height, float r, float g, float b, float a) {
        super("Box");
        this.vertexArray = 0;
        this.color = new float[]{r, g, b, a};
        this.width = width;
        this.height = height;
    }

    @Override
    public void Init() {
        InitVertex();
        InitUniformBuffer();
    }

    @Override
    public void Update() {
        Matrix4f worldMatrix = transform.GetWorldMatrix();
        Matrix4f viewMatrix = new Matrix4f().identity();
        Matrix4f projectionMatrix = new Matrix4f()
                .ortho(
                        0.0f, 1280.0f,
                        720.0f, 0.0f,
                        -1.0f, 1.0f
                );

        Matrix4f wvpMatrix = new Matrix4f(projectionMatrix)
                .mul(viewMatrix)
                .mul(worldMatrix);

        int wvpLocation = GL20.glGetUniformLocation(Main.mainShader, "wvpMatrix");
        int diffuseLocation = GL20.glGetUniformLocation(Main.mainShader, "diffuse");

        FloatBuffer matrixBuffer = BufferUtils.createFloatBuffer(16);
        wvpMatrix.get(matrixBuffer);

        GL20.glUniformMatrix4fv(wvpLocation, false, matrixBuffer);
        GL20.glUniform4f(diffuseLocation, color[0], color[1], color[2], color[3]);
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
                0.0f, 0.0f, 0.0f,  // 左上
                width, 0.0f, 0.0f, // 右上
                0.0f, height, 0.0f,   // 右下
                width, height, 0.0f   // 右上
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

        GL15.glBufferData(GL31.GL_UNIFORM_BUFFER, 20 * Float.BYTES, GL15.GL_DYNAMIC_DRAW);
        GL30.glBindBufferBase(GL31.GL_UNIFORM_BUFFER, 0, this.uniformBuffer);

        GL15.glBindBuffer(GL31.GL_UNIFORM_BUFFER, 0);
    }

    public float[] GetColor() {
        return color;
    }

}
