import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.*;

public class Main {

    public static final int DEFAULT_WIDTH = 1280;
    public static final int DEFAULT_HEIGHT = 720;
    public static final String DEFAULT_TITLE = "Game";

    public static void main(String[] args) {
        if (!GLFW.glfwInit()) {
            throw new IllegalStateException("GLFWの初期化に失敗しました。");
        }

        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MAJOR, 3);
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MINOR, 3);
        GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_PROFILE, GLFW.GLFW_OPENGL_CORE_PROFILE);

        long windowHwnd = GLFW.glfwCreateWindow(
                DEFAULT_WIDTH,
                DEFAULT_HEIGHT,
                DEFAULT_TITLE,
                0,
                0
        );

        if (windowHwnd == 0) {
            GLFW.glfwTerminate();
            throw new IllegalStateException("ウインドウの作成に失敗しました");
        }

        GLFW.glfwMakeContextCurrent(windowHwnd);
        GLFW.glfwSwapInterval(1);

        GL.createCapabilities();

        int vertexArray = InitVertex();
        int shader = InitShader();

        while (!GLFW.glfwWindowShouldClose(windowHwnd)) {
            GL11.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
            GL20.glUseProgram(shader);
            GL30.glBindVertexArray(vertexArray);
            GL11.glDrawElements(GL11.GL_TRIANGLES, 6, GL11.GL_UNSIGNED_INT, 0);
            GLFW.glfwSwapBuffers(windowHwnd);

            GLFW.glfwPollEvents();
        }

        GL30.glDeleteVertexArrays(vertexArray);
        GL20.glDeleteProgram(shader);

        GLFW.glfwDestroyWindow(windowHwnd);
        GLFW.glfwTerminate();
    }

    public static int InitVertex() {
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

        int vertexArray = GL30.glGenVertexArrays();
        GL30.glBindVertexArray(vertexArray);

        int vertexBuffer = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vertexBuffer);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, vertices, GL15.GL_STATIC_DRAW);

        int elementBuffer = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, elementBuffer);
        GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, index, GL15.GL_STATIC_DRAW);

        GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, 3 * Float.BYTES, 0);
        GL20.glEnableVertexAttribArray(0);

        return vertexArray;
    }

    public static int InitShader() {

        String vertexShaderSource = """
                #version 330 core

                layout (location = 0) in vec3 aPos;

                void main() {
                    gl_Position = vec4(aPos, 1.0);
                }
                """;

        String fragmentShaderSource = """
                #version 330 core

                out vec4 FragColor;

                void main() {
                    FragColor = vec4(1.0, 1.0, 1.0, 1.0);
                }
                """;

        int vertexShader = GL20.glCreateShader(GL20.GL_VERTEX_SHADER);
        GL20.glShaderSource(vertexShader, vertexShaderSource);
        GL20.glCompileShader(vertexShader);

        int fragmentShader = GL20.glCreateShader(GL20.GL_FRAGMENT_SHADER);
        GL20.glShaderSource(fragmentShader, fragmentShaderSource);
        GL20.glCompileShader(fragmentShader);

        int program = GL20.glCreateProgram();
        GL20.glAttachShader(program, vertexShader);
        GL20.glAttachShader(program, fragmentShader);
        GL20.glLinkProgram(program);

        GL20.glDeleteShader(vertexShader);
        GL20.glDeleteShader(fragmentShader);

        return program;
    }
}
