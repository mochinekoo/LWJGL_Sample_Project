public abstract class BaseObject {

    private String name = "";
    private String tag = null;

    public BaseObject(String name) {
        this.name = name;
    }

    public abstract void Init();
    public abstract void Update();
    public abstract void Draw();
    public abstract void Release();

    public String GetName() {
        return name;
    }

    public String GetTag() {
        return tag;
    }

    public void SetTag(String tag) {
        this.tag = tag;
    }
}
