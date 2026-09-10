public abstract class BaseObject {

    private String name = "";
    protected String tag = null;
    protected Transform transform;

    public BaseObject(String name) {
        this.name = name;
        this.transform = new Transform();
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

    public Transform GetTransform() {
        return transform;
    }

    public void SetTransform(Transform transform) {
        this.transform = transform;
    }
}
