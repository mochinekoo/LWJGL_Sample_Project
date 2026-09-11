import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.*;

import java.nio.FloatBuffer;

public class Triangle extends BaseObject {

    private float[] color;
    private Transform[] vertexTriangle;

    private int vertexArray;

    public Triangle(float[] color, Transform[] vertexTriangle) {
        super("Triangle");
        this.vertexArray = 0;
        this.vertexTriangle = vertexTriangle;
        this.color = color;
    }

    @Override
    public void Init() {
        InitVertex();
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
        GL11.glDrawElements(GL11.GL_TRIANGLES, 3, GL11.GL_UNSIGNED_INT, 0);
    }

    @Override
    public void Release() {
        GL30.glDeleteVertexArrays(vertexArray);
    }

    public void InitVertex() {
        Vector3f topVertex = vertexTriangle[0].location;
        Vector3f leftVertex = vertexTriangle[1].location;
        Vector3f rightVertex = vertexTriangle[2].location;
        float[] vertices = {
                topVertex.x, topVertex.y, 0.0f,  // 上
                leftVertex.x, leftVertex.y, 0.0f, // 左下
                rightVertex.x, rightVertex.y, 0.0f,   // 右下
        };

        int[] index = {
                0, 1, 2
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

    public float[] GetColor() {
        return color;
    }
}
