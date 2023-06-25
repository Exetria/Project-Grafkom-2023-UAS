package Engine;

import org.joml.Vector2f;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;

public class Model {

    public List<Vector3f> vertices = new ArrayList<>();
    public List<Vector3f> normals = new ArrayList<>();
    public List<Vector3f> sortedVertices = new ArrayList<>();
    public List<Vector3f> sortedNormals = new ArrayList<>();
    public List<Vector2f> textures = new ArrayList<>();
    public List<Vector2f> lineTextures = new ArrayList<>();
    public List<Face> faces = new ArrayList<>();

    public Model()
    {

    }

    public void sortVertex()
    {
        for (Face face : faces)
        {
            Vector3f n1 = normals.get((int) face.normal.x - 1);
            sortedNormals.add(n1);

            Vector3f v1 = vertices.get((int) face.vertex.x - 1);
            sortedVertices.add(v1);

            Vector3f n2 = normals.get((int) face.normal.y - 1);
            sortedNormals.add(n2);

            Vector3f v2 = vertices.get((int) face.vertex.y - 1);
            sortedVertices.add(v2);

            Vector3f n3 = normals.get((int) face.normal.z - 1);
            sortedNormals.add(n3);

            Vector3f v3 = vertices.get((int) face.vertex.z - 1);
            sortedVertices.add(v3);
        }
    }
}
