public abstract class BaseScene {

    private final String name;

    public BaseScene(String name) {
        this.name = name;
    }

    public abstract void Init();
    public abstract void Update();
    public abstract void Draw();
    public abstract void Release();

    public String GetName() {
        return name;
    }
}
