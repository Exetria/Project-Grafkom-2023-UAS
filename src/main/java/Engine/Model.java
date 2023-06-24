package Engine;

import org.joml.Vector2f;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;

public class Model {

    public List<Vector3f> vertices = new ArrayList<>();
    public List<Vector3f> normals = new ArrayList<>();
    public List<Vector2f> textures = new ArrayList<>();
    public List<Vector2f> lineTextures = new ArrayList<>();
    public List<Face> faces = new ArrayList<>();

    public Model()
    {

    }
}
