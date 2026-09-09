import java.util.ArrayList;
import java.util.List;

public class ObjectManager {

    private static List<BaseObject> objectList = new ArrayList<>();

    public static void Init() {
        objectList.clear();
    }

    public static void InitAllObject() {
        for (BaseObject object : objectList) {
            if (object != null) {
                object.Init();
            }
        }
    }

    public static void AddObject(BaseObject object) {
        objectList.add(object);
    }

    public static void UpdateObjectManager() {
        for (BaseObject object : objectList) {
            if (object != null) {
                object.Update();
                object.Draw();
            }
        }
    }

    public static void ClearAllObject() {
        objectList.clear();
    }

}
