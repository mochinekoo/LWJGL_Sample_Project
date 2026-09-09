import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;

public class Main {

    public static final int DEFAULT_WIDTH = 1280;
    public static final int DEFAULT_HEIGHT = 720;
    public static final String DEFAULT_TITLE = "Game";

    public static void main(String[] args) {
        if (!GLFW.glfwInit()) {
            throw new IllegalStateException("GLFWの初期化に失敗しました。");
        }

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

        GL.createCapabilities();

        while (!GLFW.glfwWindowShouldClose(windowHwnd)) {
            GLFW.glfwSwapBuffers(windowHwnd);

            GLFW.glfwPollEvents();
        }

        GLFW.glfwDestroyWindow(windowHwnd);
        GLFW.glfwTerminate();
    }
}
