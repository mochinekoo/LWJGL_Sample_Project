import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.*;

import java.io.*;

public class Main {

    public static final int DEFAULT_WIDTH = 1280;
    public static final int DEFAULT_HEIGHT = 720;
    public static final String DEFAULT_TITLE = "Game";

    public static int mainShader = 0;

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

        ObjectManager.Init();
        SceneManager.Init();

        mainShader = InitShader();

        GL11.glViewport(0, 0, DEFAULT_WIDTH, DEFAULT_HEIGHT);

        while (!GLFW.glfwWindowShouldClose(windowHwnd)) {
            GL11.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
            GL30.glUseProgram(mainShader);

            BaseScene currentScene = SceneManager.GetCurrentScene();
            if (currentScene != null) {
                currentScene.Update();
                currentScene.Draw();
            }
            ObjectManager.UpdateObjectManager();

            GLFW.glfwSwapBuffers(windowHwnd);

            GLFW.glfwPollEvents();
        }

        GL20.glDeleteProgram(mainShader);

        GLFW.glfwDestroyWindow(windowHwnd);
        GLFW.glfwTerminate();
    }

    public static String LoadShader(String fileName) {
        File file = new File(fileName);
        if (!file.exists()) {
            return null;
        }
        if (!file.getName().contains(".glsl")) {
            return null;
        }

        StringBuilder builder = new StringBuilder();
        try (FileReader filereader = new FileReader(file);) {
            int data;
            while ((data = filereader.read()) != -1) {
                builder.append((char) data);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return builder.toString();
    }

    public static int InitShader() {
        String vertexShaderSource = LoadShader(Main.class.getResource("VertexShader.glsl").getPath());
        String fragmentShaderSource = LoadShader(Main.class.getResource("FragmentShader.glsl").getPath());

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
