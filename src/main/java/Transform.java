import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Transform {

    public Vector3f location;
    public Vector3f velocity;
    public Vector3f rotation;
    public Vector3f scale;

    public Transform() {
        this.location = new Vector3f(0.0f, 0.0f, 0.0f);
        this.velocity = new Vector3f(0.0f, 0.0f, 0.0f);;
        this.rotation = new Vector3f(0.0f, 0.0f, 0.0f);
        this.scale = new Vector3f(0.0f, 0.0f, 0.0f);
    }

    public Matrix4f GetWorldMatrix() {
        return new Matrix4f()
                .translate(location.x, location.y, location.z)
                .rotateX((float)Math.toRadians(rotation.x))
                .rotateY((float)Math.toRadians(rotation.y))
                .rotateZ((float)Math.toRadians(rotation.z))
                .scale(1.0f, 1.0f, 1.0f);
    }
}
