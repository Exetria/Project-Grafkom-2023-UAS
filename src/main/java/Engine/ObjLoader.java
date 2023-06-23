package Engine;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.assimp.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class ObjLoader
{
    List<String> lines;
    public List<Vector3f> vertices = new ArrayList<>();
    public ArrayList<Vector3f> normals = new ArrayList<>();
    public ArrayList<Vector2f> textures = new ArrayList<>();
    public ArrayList<Integer> indicies = new ArrayList<>();
    AIScene scene;

    public static Model loadModel(File file) throws FileNotFoundException, IOException
    {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        Model result = new Model();
        String[] splitted;
        String line;
        float x, y, z;

        while((line = reader.readLine()) != null)
        {
            if(line.startsWith("v "))
            {
                splitted = line.split("\\s+");
                x = Float.valueOf(splitted[1]);
                y = Float.valueOf(splitted[2]);
                z = Float.valueOf(splitted[3]);
                result.vertices.add(new Vector3f(x, y, z));
            }
            else if(line.startsWith("vt "))
            {
                splitted = line.split("\\s+");
                x = Float.valueOf(splitted[1]);
                y = Float.valueOf(splitted[2]);
                result.texture.add(new Vector2f(x, y));
            }
            else if(line.startsWith("vn "))
            {
                splitted = line.split("\\s+");
                x = Float.valueOf(splitted[1]);
                y = Float.valueOf(splitted[2]);
                z = Float.valueOf(splitted[3]);
                result.normals.add(new Vector3f(x, y, z));
//                System.out.println(x + " " + y + " " + z);
            }
            else if(line.startsWith("f "))
            {
                splitted = line.split("\\s+");

                result.vertexIndices.add(Integer.parseInt(splitted[1].split("/")[0])-1);
                result.vertexIndices.add(Integer.parseInt(splitted[2].split("/")[0])-1);
                result.vertexIndices.add(Integer.parseInt(splitted[3].split("/")[0])-1);

//                result.indices.add(Integer.parseInt(splitted[1].split("/")[1]));
//                result.indices.add(Integer.parseInt(splitted[2].split("/")[1]));
//                result.indices.add(Integer.parseInt(splitted[3].split("/")[1]));

                result.normalIndices.add(Integer.parseInt(splitted[1].split("/")[2])-1);
                result.normalIndices.add(Integer.parseInt(splitted[2].split("/")[2])-1);
                result.normalIndices.add(Integer.parseInt(splitted[3].split("/")[2])-1);

//                System.out.println(result.indices.get(result.indices.size()-1));
            }
        }
        reader.close();
        result.sortNormal();
        result.sortVertices();
        return result;
    }

    public ObjLoader(String fileName)
    {
        //fbx
        scene = Assimp.aiImportFile(fileName, Assimp.aiProcess_Triangulate | Assimp.aiProcess_FlipUVs);
        loadFbxFiles();
    }

    public void loadFbxFiles(){
        int numMeshes = scene.mNumMeshes();
        for (int x = 0; x <numMeshes; x++) { //kalo ada beberapa model
            AIMesh mesh = AIMesh.create(scene.mMeshes().get(x));

            // vertices
            AIVector3D.Buffer verticesBuffer = mesh.mVertices();
            int numVertices = mesh.mNumVertices();


            for (int i = 0; i < numVertices; i++) {
                AIVector3D vertex = verticesBuffer.get(i);
                Vector3f verticesVec = new Vector3f(vertex.x(), vertex.y(), vertex.z());
                vertices.add(verticesVec);
            }

            //  normal
            AIVector3D.Buffer normalsBuffer = mesh.mNormals();
            int numNormals = mesh.mNumVertices();

            for (int i = 0; i < numNormals; i++) {
                AIVector3D vertex = normalsBuffer.get(i);
                Vector3f verticesVec = new Vector3f(vertex.x(), vertex.y(), vertex.z());
                normals.add(verticesVec);
            }

            //indices
            AIFace.Buffer facesBuffer = mesh.mFaces();
            int numFaces = mesh.mNumFaces();

            for (int i = 0; i < numFaces; i++) {
                AIFace face = facesBuffer.get(i);
                int numIndices = face.mNumIndices();
                for (int j = 0; j < numIndices; j++) {
                    int index = face.mIndices().get(j);
                    indicies.add(index);
                }
            }
        }

    }
}
