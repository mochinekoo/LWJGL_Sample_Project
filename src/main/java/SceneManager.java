import java.util.HashMap;
import java.util.Map;

public class SceneManager {

    private static Map<String, BaseScene> sceneMap = new HashMap<>();
    private static BaseScene currentScene = null;

    public static void Init() {
        sceneMap.clear();
        AddScene(new DebugScene());

        ChangeScene("DebugScene");
    }

    public static void AddScene(BaseScene addScene) {
        sceneMap.put(addScene.GetName(), addScene);
    }

    public static BaseScene GetCurrentScene() {
        return currentScene;
    }

    public static boolean ChangeScene(String name) {
        if (sceneMap.containsKey(name)) {
            ObjectManager.ClearAllObject();
            currentScene = sceneMap.get(name);
            currentScene.Init();
            ObjectManager.InitAllObject();
            return true;
        }
        return false;
    }
}
