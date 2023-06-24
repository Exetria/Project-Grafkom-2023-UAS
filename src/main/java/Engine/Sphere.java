package Engine;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//import static org.lwjgl.opengl.GL15C.*;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;

public class Sphere extends Circle
{
    List<Integer> index;
    ArrayList<Vector2f> texture;
    ArrayList<Vector3f> normal;
    ArrayList<Vector3f> normalVert;

    int ibo, nbo, stackCount, sectorCount;
    double cpz;
    float radiusX, radiusY, radiusZ;

    public Sphere(List<ShaderModuleData> shaderModuleDataList, List<Vector3f> vertices, Vector4f color, double rx, double ry, double rz, double cpx, double cpy, double cpz, int option)
    {
        super(shaderModuleDataList, vertices, color, rx, cpx, cpy);
        this.cpz = cpz;
        this.radiusX = (float) rx;
        this.radiusY = (float) ry;
        this.radiusZ = (float) rz;

        this.stackCount = 18;
        this.sectorCount = 36;

        createBoxVertices();

        /*{
            if(option == 1)
            {
                createSphere();
            }
            else if (option == 2)
            {
                createBox();
            }
            else if (option == 3)
            {
                createCylinder();
            }
            else if (option == 4)
            {
                createEllipsoid();
            }
            else if (option == 5)
            {
                create1SideHyperboloid();
            }
            else if (option == 6)
            {
                create2SideHyperboloid();
            }
            else if (option == 7)
            {
                createEllipticCone();
            }
            else if (option == 8)
            {
                createEllipticParaboloid();
            }
            else if (option == 9)
            {
                createHyperboloidParaboloid();
            }
        }*/

        setupVAOVBO();
    }

    public Sphere(List<ShaderModuleData> shaderModuleDataList, List<Vector3f> vertices, Vector4f color, String filePath)
    {
        super(shaderModuleDataList, vertices, color, 0, 0, 0);

        File f = new File(filePath);
        Model m = new Model();
        try
        {
            m = ObjLoader.loadModel(f);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        this.vertices = m.sortedVertices;
        this.texture = m.texture;
        this.index = m.vertexIndices;
        this.normal = m.sortedNormals;

//        for (Vector3f i: this.vertices)
//        {
//            System.out.println(i.x + " " + i.y + " " + i.z);
//            System.out.println(i);
//        }


//        System.out.println("VERTICES");
//        for (Vector3f vert:this.vertices)
//        {
//            System.out.println(vert.x + " " + vert.y + " " + vert.z);
//        }

//        System.out.println("NORMAL");
//        for (Vector3f vert:this.normal)
//        {
//            System.out.print(vert.x + " " + vert.y + " " + vert.z + "  ");
//        }
//        System.out.println();
//        System.out.println("New NORMAL");
//        for (Vector3f vert:this.newNormal)
//        {
//            System.out.print(vert.x + " " + vert.y + " " + vert.z + "  ");
//        }

//        System.out.println("VERTINDEX");
//        for (int ind:m.vertexIndices)
//        {
//            System.out.println(ind);
//        }

//        System.out.println("NORMINDEX");
//        for (int ind:m.normalIndices)
//        {
//            System.out.println(ind + " " + m.normals.get(ind).x + " " + m.normals.get(ind).y + " " + m.normals.get(ind).z);
//        }
//
//        System.out.println("NORMAL FINAL");
//        for (Vector3f vert:this.normal)
//        {
//            System.out.println(vert.x + " " + vert.y + " " + vert.z);
//        }

        setupVAOVBO();
    }

    public Sphere(List<ShaderModuleData> shaderModuleDataList, List<Vector3f> vertices, Vector4f color, String filePath, boolean fbx)
    {
        super(shaderModuleDataList, vertices, color, 0, 0, 0);

        ObjLoader m = new ObjLoader(filePath);

        this.vertices = m.vertices;
        this.index = m.indicies;
        this.normal = m.normals;

        System.out.println("PJG" + index.size());
        for (Vector3f i: this.vertices)
        {
            System.out.println(i.x + " " + i.y + " " + i.z);
//            System.out.println(i);
        }

        setupVAOVBO();
    }

    public void translateObject(float offsetX, float offsetY, float offsetZ)
    {
        model = new Matrix4f().translate(offsetX, offsetY, offsetZ).mul(new Matrix4f(model));
        cpx += offsetX;
        cpy += offsetY;
        cpz += offsetZ;

        for (Objects i: childObjects) {
            i.translateObject(offsetX, offsetY, offsetZ);
        }
    }

    public void rotateObject(float degree, float offsetX, float offsetY, float offsetZ)
    {
        //offset x, y, sama z itu maksudnya rotasi terhadap sumbunya misal z=1 berarti rotasi thd sb z
        model = new Matrix4f().rotate((float)(Math.toRadians(degree)), offsetX, offsetY, offsetZ).mul(new Matrix4f(model));

        float newcpx =(float) (cpx * Math.cos((double) degree) - cpy * Math.sin((double) degree));
        float newcpy =(float) (cpx * Math.sin((double) degree) + cpy * Math.cos((double) degree));

        cpx = newcpx;
        cpy = newcpy;

        for (Objects i: childObjects)
        {
            i.rotateObject(degree, offsetX, offsetY, offsetZ);
//            ((Sphere)i).rotateObjectOnPoint(degree, offsetX, offsetY, offsetZ, (float)((Sphere) i).getCpx(), (float)((Sphere) i).getCpy());

        }
    }

    public void rotateObjectOnPoint(float degree, float offsetX, float offsetY, float offsetZ, float rotateX, float rotateY, float rotateZ)
    {
        translateObject(-rotateX, -rotateY, -rotateZ);

        model = new Matrix4f().rotate((float)(Math.toRadians(degree)), offsetX, offsetY, offsetZ).mul(new Matrix4f(model));

        float newcpx =(float) (cpx * Math.cos((double) (float)(Math.toRadians(degree))) - cpy * Math.sin((double) (float)(Math.toRadians(degree))));
        float newcpy =(float) (cpx * Math.sin((double) (float)(Math.toRadians(degree))) + cpy * Math.cos((double) (float)(Math.toRadians(degree))));

        cpx = newcpx;
        cpy = newcpy;

        translateObject(rotateX, rotateY, rotateZ);

        for (Objects i: childObjects)
        {
            ((Sphere)i).rotateObjectOnPoint(degree, offsetX, offsetY, offsetZ, rotateX, rotateY, rotateZ);
        }
    }

    public void centralize()
    {
        createSphere();
        setupVAOVBO();
    }

    public void returnPosition()
    {
        translateObject((float) cpx, (float) cpy, (float) cpz);
    }

    public void setupVAOVBO()
    {
        super.setupVAOVBO();

        //set nbo
        nbo = glGenBuffers();
        ibo = glGenBuffers();

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER,ibo);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER,Utils.listoInt(index),GL_STATIC_DRAW);
        glBindBuffer(GL_ARRAY_BUFFER, nbo);
        glBufferData(GL_ARRAY_BUFFER, Utils.listoFloat(normal), GL_STATIC_DRAW);

//        uniformsMap.createUniform("lightColor");
//        uniformsMap.createUniform("lightPos");
    }

    public void drawSetup(Camera camera, Projection projection){
        super.drawSetup(camera,projection);
        // Bind VBO
        glEnableVertexAttribArray(1);
        glBindBuffer(GL_ARRAY_BUFFER, nbo);
        glVertexAttribPointer(1, 3,
                GL_FLOAT,
                false,
                0, 0);
        //directional Light
        uniformsMap.setUniform("dirLight.direction", new Vector3f(-0.2f,-1.0f,-0.3f));
        uniformsMap.setUniform("dirLight.ambient", new Vector3f(0.05f,0.05f,0.05f));
        uniformsMap.setUniform("dirLight.diffuse", new Vector3f(0.4f,0.4f,0.4f));
        uniformsMap.setUniform("dirLight.specular", new Vector3f(0.5f,0.5f,0.5f));
        //posisi pointLight
        Vector3f[] _pointLightPositions =
                {
                new Vector3f(0.7f, 0.2f, 2.0f),
                new Vector3f(2.3f, -3.3f, -4.0f),
                new Vector3f(-4.0f, 2.0f, -12.0f),
                new Vector3f(0.0f, 0.0f, -3.0f)
                };
        for(int i = 0;i< _pointLightPositions.length;i++)
        {
            uniformsMap.setUniform("pointLights["+ i +"].position",_pointLightPositions[i]);
            uniformsMap.setUniform("pointLights["+ i +"].ambient", new Vector3f(0.05f,0.05f,0.05f));
            uniformsMap.setUniform("pointLights["+ i +"].diffuse", new Vector3f(0.8f,0.8f,0.8f));
            uniformsMap.setUniform("pointLights["+ i +"].specular", new Vector3f(1.0f,1.0f,1.0f));
            uniformsMap.setUniform("pointLights["+ i +"].constant",1.0f );
            uniformsMap.setUniform("pointLights["+ i +"].linear", 0.09f);
            uniformsMap.setUniform("pointLights["+ i +"].quadratic", 0.032f);

        }

        //spotlight
        uniformsMap.setUniform("spotLight.position",camera.getPosition());
        uniformsMap.setUniform("spotLight.direction",camera.getDirection());
        uniformsMap.setUniform("spotLight.ambient",new Vector3f(0.0f,0.0f,0.0f));
        uniformsMap.setUniform("spotLight.diffuse",new Vector3f(1.0f,1.0f,1.0f));
        uniformsMap.setUniform("spotLight.specular",new Vector3f(1.0f,1.0f,1.0f));
        uniformsMap.setUniform("spotLight.constant",1.0f);
        uniformsMap.setUniform("spotLight.linear",0.09f);
        uniformsMap.setUniform("spotLight.quadratic",0.032f);
        uniformsMap.setUniform("spotLight.cutOff",(float)Math.cos(Math.toRadians(12.5f)));
        uniformsMap.setUniform("spotLight.outerCutOff",(float)Math.cos(Math.toRadians(12.5f)));

        uniformsMap.setUniform("viewPos",camera.getPosition());
    }

    public void draw(Camera camera, Projection projection)
    {
        drawSetup(camera, projection);
        glLineWidth(1);
        glPointSize(0);
        glDrawElements(GL_TRIANGLES, index.size(),GL_UNSIGNED_INT,0);
        for (Objects i: childObjects)
        {
            i.draw(camera, projection);
        }
    }

    //method draw pake kamera + child tapi gambarnya pake garis
    public void draw(Camera camera, Projection projection, boolean lineOrTriangle)
    {
        drawSetup(camera, projection);
        glLineWidth(1);
        glPointSize(0);

        if(lineOrTriangle)
        {
            glDrawElements(GL_TRIANGLES, index.size(),GL_UNSIGNED_INT,0);
        }
        else
        {
            glDrawElements(GL_TRIANGLES, index.size(),GL_UNSIGNED_INT,0);
        }

        for (Objects i: childObjects)
        {
            i.draw(camera, projection);
        }
    }

    public void createSphere()
    {
        vertices.clear();
        ArrayList<Vector3f> temp = new ArrayList<>();

        for(double v = -Math.PI/2; v<= Math.PI/2; v+=Math.PI/60){
            for(double u = -Math.PI; u<= Math.PI; u+=Math.PI/60){
                float x = radiusX * (float)(Math.cos(v) * Math.cos(u));
                float y = radiusY * (float)(Math.cos(v) * Math.sin(u));
                float z = radiusZ * (float)(Math.sin(v));
                temp.add(new Vector3f(x,y,z));
            }
        }
        vertices = temp;
    }

    public void createBox(){
        Vector3f temp = new Vector3f();
        ArrayList<Vector3f> tempVertices = new ArrayList<>();
        //TITIK 1
        temp.x = (float) cpx - radiusX / 2.0f;
        temp.y = (float) cpy + radiusY / 2.0f;
        temp.z = (float) cpz - radiusZ / 2.0f;
        tempVertices.add(temp);
        temp = new Vector3f();
        //TITIK 2
        temp.x = (float) cpx + radiusX / 2.0f;
        temp.y = (float) cpy + radiusY / 2.0f;
        temp.z = (float) cpz - radiusZ / 2.0f;
        tempVertices.add(temp);
        temp = new Vector3f();
        //TITIK 3
        temp.x = (float) cpx + radiusX / 2.0f;
        temp.y = (float) cpy - radiusY / 2.0f;
        temp.z = (float) cpz - radiusZ / 2.0f;
        tempVertices.add(temp);
        temp = new Vector3f();
        //TITIK 4
        temp.x = (float) cpx - radiusX / 2.0f;
        temp.y = (float) cpy - radiusY / 2.0f;
        temp.z = (float) cpz - radiusZ / 2.0f;
        tempVertices.add(temp);
        temp = new Vector3f();
        //TITIK 5
        temp.x = (float) cpx - radiusX / 2.0f;
        temp.y = (float) cpy + radiusY / 2.0f;
        temp.z = (float) cpz + radiusZ / 2.0f;
        tempVertices.add(temp);
        temp = new Vector3f();
        //TITIK 6
        temp.x = (float) cpx + radiusX / 2.0f;
        temp.y = (float) cpy + radiusY / 2.0f;
        temp.z = (float) cpz + radiusZ / 2.0f;
        tempVertices.add(temp);
        temp = new Vector3f();
        //TITIK 7
        temp.x = (float) cpx + radiusX / 2.0f;
        temp.y = (float) cpy - radiusY / 2.0f;
        temp.z = (float) cpz + radiusZ / 2.0f;
        tempVertices.add(temp);
        temp = new Vector3f();
        //TITIK 8
        temp.x = (float) cpx - radiusX / 2.0f;
        temp.y = (float) cpy - radiusY / 2.0f;
        temp.z = (float) cpz + radiusZ / 2.0f;
        tempVertices.add(temp);
        temp = new Vector3f();
        vertices.clear();
        //kotak yg sisi belakang
        vertices.add(tempVertices.get(0));
        vertices.add(tempVertices.get(1));
        vertices.add(tempVertices.get(2));
        vertices.add(tempVertices.get(3));
        //kotak yg sisi depan
        vertices.add(tempVertices.get(4));
        vertices.add(tempVertices.get(5));
        vertices.add(tempVertices.get(6));
        vertices.add(tempVertices.get(7));
        //kotak yg sisi kiri
        vertices.add(tempVertices.get(0));
        vertices.add(tempVertices.get(4));
        vertices.add(tempVertices.get(7));
        vertices.add(tempVertices.get(3));
        //kotak yg sisi kanan
        vertices.add(tempVertices.get(1));
        vertices.add(tempVertices.get(5));
        vertices.add(tempVertices.get(6));
        vertices.add(tempVertices.get(2));
        //kotak yg sisi atas
        vertices.add(tempVertices.get(0));
        vertices.add(tempVertices.get(1));
        vertices.add(tempVertices.get(5));
        vertices.add(tempVertices.get(4));
        //kotak yg sisi bawah
        vertices.add(tempVertices.get(3));
        vertices.add(tempVertices.get(2));
        vertices.add(tempVertices.get(7));
        vertices.add(tempVertices.get(6));

        normal = new ArrayList<>(Arrays.asList(
                //belakang
                new Vector3f(0.0f,0.0f,-1.0f),
                new Vector3f(0.0f,0.0f,-1.0f),
                new Vector3f(0.0f,0.0f,-1.0f),
                new Vector3f(0.0f,0.0f,-1.0f),
                //depan
                new Vector3f(0.0f,0.0f,1.0f),
                new Vector3f(0.0f,0.0f,1.0f),
                new Vector3f(0.0f,0.0f,1.0f),
                new Vector3f(0.0f,0.0f,1.0f),
                //kiri
                new Vector3f(-1.0f,0.0f,0.0f),
                new Vector3f(-1.0f,0.0f,0.0f),
                new Vector3f(-1.0f,0.0f,0.0f),
                new Vector3f(-1.0f,0.0f,0.0f),
                //kanan
                new Vector3f(1.0f,0.0f,0.0f),
                new Vector3f(1.0f,0.0f,0.0f),
                new Vector3f(1.0f,0.0f,0.0f),
                new Vector3f(1.0f,0.0f,0.0f),
                //atas
                new Vector3f(0.0f,1.0f,0.0f),
                new Vector3f(0.0f,1.0f,0.0f),
                new Vector3f(0.0f,1.0f,0.0f),
                new Vector3f(0.0f,1.0f,0.0f),
                //bawah
                new Vector3f(0.0f,-1.0f,0.0f),
                new Vector3f(0.0f,-1.0f,0.0f),
                new Vector3f(0.0f,-1.0f,0.0f),
                new Vector3f(0.0f,-1.0f,0.0f)
        ));
    }

    public void createBoxVertices()
    {
        Vector3f temp = new Vector3f();
        ArrayList<Vector3f> tempVertices = new ArrayList<>();
        //TITIK 0
        temp.x = (float) cpx - radiusX / 2.0f;
        temp.y = (float) cpy + radiusY / 2.0f;
        temp.z = (float) cpz - radiusZ / 2.0f;
        tempVertices.add(temp);
        temp = new Vector3f();
        //TITIK 1
        temp.x = (float) cpx + radiusX / 2.0f;
        temp.y = (float) cpy + radiusY / 2.0f;
        temp.z = (float) cpz - radiusZ / 2.0f;
        tempVertices.add(temp);
        temp = new Vector3f();
        //TITIK 2
        temp.x = (float) cpx + radiusX / 2.0f;
        temp.y = (float) cpy - radiusY / 2.0f;
        temp.z = (float) cpz - radiusZ / 2.0f;
        tempVertices.add(temp);
        temp = new Vector3f();
        //TITIK 3
        temp.x = (float) cpx - radiusX / 2.0f;
        temp.y = (float) cpy - radiusY / 2.0f;
        temp.z = (float) cpz - radiusZ / 2.0f;
        tempVertices.add(temp);
        temp = new Vector3f();
        //TITIK 4
        temp.x = (float) cpx - radiusX / 2.0f;
        temp.y = (float) cpy + radiusY / 2.0f;
        temp.z = (float) cpz + radiusZ / 2.0f;
        tempVertices.add(temp);
        temp = new Vector3f();
        //TITIK 5
        temp.x = (float) cpx + radiusX / 2.0f;
        temp.y = (float) cpy + radiusY / 2.0f;
        temp.z = (float) cpz + radiusZ / 2.0f;
        tempVertices.add(temp);
        temp = new Vector3f();
        //TITIK 6
        temp.x = (float) cpx + radiusX / 2.0f;
        temp.y = (float) cpy - radiusY / 2.0f;
        temp.z = (float) cpz + radiusZ / 2.0f;
        tempVertices.add(temp);
        temp = new Vector3f();
        //TITIK 7
        temp.x = (float) cpx - radiusX / 2.0f;
        temp.y = (float) cpy - radiusY / 2.0f;
        temp.z = (float) cpz + radiusZ / 2.0f;
        tempVertices.add(temp);
        temp = new Vector3f();

        vertices.clear();
        vertices.add(tempVertices.get(0));
        vertices.add(tempVertices.get(1));
        vertices.add(tempVertices.get(3));

        vertices.add(tempVertices.get(1));
        vertices.add(tempVertices.get(2));
        vertices.add(tempVertices.get(3));

        vertices.add(tempVertices.get(4));
        vertices.add(tempVertices.get(5));
        vertices.add(tempVertices.get(7));

        vertices.add(tempVertices.get(5));
        vertices.add(tempVertices.get(6));
        vertices.add(tempVertices.get(7));

        vertices.add(tempVertices.get(0));
        vertices.add(tempVertices.get(4));
        vertices.add(tempVertices.get(7));

        vertices.add(tempVertices.get(0));
        vertices.add(tempVertices.get(3));
        vertices.add(tempVertices.get(7));

        vertices.add(tempVertices.get(1));
        vertices.add(tempVertices.get(5));
        vertices.add(tempVertices.get(6));

        vertices.add(tempVertices.get(1));
        vertices.add(tempVertices.get(2));
        vertices.add(tempVertices.get(6));

        vertices.add(tempVertices.get(0));
        vertices.add(tempVertices.get(1));
        vertices.add(tempVertices.get(5));

        vertices.add(tempVertices.get(0));
        vertices.add(tempVertices.get(4));
        vertices.add(tempVertices.get(5));

        vertices.add(tempVertices.get(2));
        vertices.add(tempVertices.get(3));
        vertices.add(tempVertices.get(6));

        vertices.add(tempVertices.get(3));
        vertices.add(tempVertices.get(6));
        vertices.add(tempVertices.get(7));

        normal = new ArrayList<>(Arrays.asList(
                // ini yg belakang
                new Vector3f(0.0f, 0.0f, -1.0f),
                new Vector3f(0.0f, 0.0f, -1.0f),
                new Vector3f(0.0f, 0.0f, -1.0f),
                new Vector3f(0.0f, 0.0f, -1.0f),

                // ini yg depan
                new Vector3f(0.0f, 0.0f, 1.0f),
                new Vector3f(0.0f, 0.0f, 1.0f),
                new Vector3f(0.0f, 0.0f, 1.0f),
                new Vector3f(0.0f, 0.0f, 1.0f),

                // ini yg kiri
                new Vector3f(-1.0f, 0.0f, 0.0f),
                new Vector3f(-1.0f, 0.0f, 0.0f),
                new Vector3f(-1.0f, 0.0f, 0.0f),
                new Vector3f(-1.0f, 0.0f, 0.0f),

                // ini yg kanan
                new Vector3f(1.0f, 0.0f, 0.0f),
                new Vector3f(1.0f, 0.0f, 0.0f),
                new Vector3f(1.0f, 0.0f, 0.0f),
                new Vector3f(1.0f, 0.0f, 0.0f),

                // ini yg atas
                new Vector3f(0.0f, 1.0f, 0.0f),
                new Vector3f(0.0f, 1.0f, 0.0f),
                new Vector3f(0.0f, 1.0f, 0.0f),
                new Vector3f(0.0f, 1.0f, 0.0f),

                // ini yg bawah
                new Vector3f(0.0f, -1.0f, 0.0f),
                new Vector3f(0.0f, -1.0f, 0.0f),
                new Vector3f(0.0f, -1.0f, 0.0f),
                new Vector3f(0.0f, -1.0f, 0.0f)
        ));
    }

    //PUNYA KELAS, YG DIATA PUNYA MATTHEW
    /*public void createBoxVertices()
    {
        System.out.println("code");
        vertices.clear();
        Vector3f temp = new Vector3f();
        ArrayList<Vector3f> tempVertices = new ArrayList<>();
        //Titik 1 kiri atas belakang
        temp.x = (float) cpx - radiusX / 2;
        temp.y = (float) cpy + radiusY / 2;
        temp.z = (float) cpz - radiusZ / 2;
        tempVertices.add(temp);
        temp = new Vector3f();
        //Titik 2 kiri bawah belakang
        temp.x = (float) cpx - radiusX / 2;
        temp.y = (float) cpy - radiusY / 2;
        temp.z = (float) cpz - radiusZ / 2;
        tempVertices.add(temp);
        temp = new Vector3f();
        //Titik 3 kanan bawah belakang
        temp.x = (float) cpx + radiusX / 2;
        temp.y = (float) cpy - radiusY / 2;
        temp.z = (float) cpz - radiusZ / 2;
        tempVertices.add(temp);
        temp = new Vector3f();
        //Titik 4 kanan atas belakang
        temp.x = (float) cpx + radiusX / 2;
        temp.y = (float) cpy + radiusY / 2;
        temp.z = (float) cpz - radiusZ / 2;
        tempVertices.add(temp);
        temp = new Vector3f();
        //Titik 5 kiri atas depan
        temp.x = (float) cpx - radiusX / 2;
        temp.y = (float) cpy + radiusY / 2;
        temp.z = (float) cpz + radiusZ / 2;
        tempVertices.add(temp);
        temp = new Vector3f();
        //Titik 6 kiri bawah depan
        temp.x = (float) cpx - radiusX / 2;
        temp.y = (float) cpy - radiusY / 2;
        temp.z = (float) cpz + radiusZ / 2;
        tempVertices.add(temp);
        temp = new Vector3f();
        //Titik 7 kanan bawah depan
        temp.x = (float) cpx + radiusX / 2;
        temp.y = (float) cpy - radiusY / 2;
        temp.z = (float) cpz + radiusZ / 2;
        tempVertices.add(temp);
        temp = new Vector3f();
        //Titik 8 kanan atas depan
        temp.x = (float) cpx + radiusX / 2;
        temp.y = (float) cpy + radiusY / 2;
        temp.z = (float) cpz + radiusZ / 2;
        tempVertices.add(temp);
        temp = new Vector3f();

        //kotak belakang
        vertices.add(tempVertices.get(0));
        vertices.add(tempVertices.get(1));
        vertices.add(tempVertices.get(2));

        vertices.add(tempVertices.get(2));
        vertices.add(tempVertices.get(3));
        vertices.add(tempVertices.get(0));
        //kotak depan
        vertices.add(tempVertices.get(4));
        vertices.add(tempVertices.get(5));
        vertices.add(tempVertices.get(6));

        vertices.add(tempVertices.get(6));
        vertices.add(tempVertices.get(7));
        vertices.add(tempVertices.get(4));
        //kotak samping kiri
        vertices.add(tempVertices.get(0));
        vertices.add(tempVertices.get(1));
        vertices.add(tempVertices.get(4));

        vertices.add(tempVertices.get(1));
        vertices.add(tempVertices.get(5));
        vertices.add(tempVertices.get(4));
        //kotak samping kanan
        vertices.add(tempVertices.get(7));
        vertices.add(tempVertices.get(6));
        vertices.add(tempVertices.get(2));

        vertices.add(tempVertices.get(2));
        vertices.add(tempVertices.get(3));
        vertices.add(tempVertices.get(7));
        //kotak bawah
        vertices.add(tempVertices.get(1));
        vertices.add(tempVertices.get(5));
        vertices.add(tempVertices.get(6));

        vertices.add(tempVertices.get(6));
        vertices.add(tempVertices.get(2));
        vertices.add(tempVertices.get(1));
        //kotak atas
        vertices.add(tempVertices.get(0));
        vertices.add(tempVertices.get(4));
        vertices.add(tempVertices.get(7));

        vertices.add(tempVertices.get(7));
        vertices.add(tempVertices.get(0));
        vertices.add(tempVertices.get(3));

        normal = new ArrayList<>(Arrays.asList(
                new Vector3f(0.0f,  0.0f, -1.0f),
                new Vector3f(0.0f,  0.0f, -1.0f),
                new Vector3f(0.0f,  0.0f, -1.0f),
                new Vector3f(0.0f,  0.0f, -1.0f),
                new Vector3f(0.0f,  0.0f, -1.0f),
                new Vector3f(0.0f,  0.0f, -1.0f),

                new Vector3f(0.0f,  0.0f,  1.0f),
                new Vector3f(0.0f,  0.0f,  1.0f),
                new Vector3f(0.0f,  0.0f,  1.0f),
                new Vector3f(0.0f,  0.0f,  1.0f),
                new Vector3f(0.0f,  0.0f,  1.0f),
                new Vector3f(0.0f,  0.0f,  1.0f),

                new Vector3f(-1.0f,  0.0f,  0.0f),
                new Vector3f(-1.0f,  0.0f,  0.0f),
                new Vector3f(-1.0f,  0.0f,  0.0f),
                new Vector3f(-1.0f,  0.0f,  0.0f),
                new Vector3f(-1.0f,  0.0f,  0.0f),
                new Vector3f(-1.0f,  0.0f,  0.0f),

                new Vector3f(1.0f,  0.0f,  0.0f),
                new Vector3f(1.0f,  0.0f,  0.0f),
                new Vector3f(1.0f,  0.0f,  0.0f),
                new Vector3f(1.0f,  0.0f,  0.0f),
                new Vector3f(1.0f,  0.0f,  0.0f),
                new Vector3f(1.0f,  0.0f,  0.0f),

                new Vector3f(0.0f, -1.0f,  0.0f),
                new Vector3f(0.0f, -1.0f,  0.0f),
                new Vector3f( 0.0f, -1.0f,  0.0f),
                new Vector3f(0.0f, -1.0f,  0.0f),
                new Vector3f(0.0f, -1.0f,  0.0f),
                new Vector3f(0.0f, -1.0f,  0.0f),

                new Vector3f(0.0f,  1.0f,  0.0f),
                new Vector3f(0.0f,  1.0f,  0.0f),
                new Vector3f(0.0f,  1.0f,  0.0f),
                new Vector3f(0.0f,  1.0f,  0.0f),
                new Vector3f(0.0f,  1.0f,  0.0f),
                new Vector3f(0.0f,  1.0f,  0.0f)
        ));
    }*/

    public void createCylinder()
    {
        vertices.clear();
        ArrayList<Vector3f> temp = new ArrayList<>();

        for (double i = 0; i < 360; i+=0.1)
        {
            x = cpx + radiusX * (float)Math.cos(Math.toRadians(i));
            y = cpy + radiusY * (float)Math.sin(Math.toRadians(i));

            temp.add(new Vector3f((float)x, (float)y, 0.0f));
            temp.add(new Vector3f((float)x, (float)y, -radiusZ));
        }

        vertices = temp;
    }

    public void createEllipsoid()
    {
        vertices.clear();
        ArrayList<Vector3f> temp = new ArrayList<>();

        for(double v = -Math.PI/2; v<= Math.PI/2; v+=Math.PI/60){
            for(double u = -Math.PI; u<= Math.PI; u+=Math.PI/60){
                float x = radiusX * (float)(Math.cos(v) * Math.cos(u));
                float y = radiusY * (float)(Math.cos(v) * Math.sin(u) / 2);
                float z = radiusZ * (float)(Math.sin(v) / 2);
                temp.add(new Vector3f(x,y,z));
            }
        }
        vertices = temp;
    }

    public void create1SideHyperboloid()
    {
        vertices.clear();
        ArrayList<Vector3f> temp = new ArrayList<>();

        for(double v = -Math.PI/2; v<= Math.PI/2; v+=Math.PI/60){
            for(double u = -Math.PI/2; u<= Math.PI/2; u+=Math.PI/60){
                float x = radiusX * (float)((1/Math.cos(v)) * Math.cos(u));
                float y = radiusY * (float)((1/Math.cos(v)) * Math.sin(u));
                float z = radiusZ * (float)(Math.tan(v));
                temp.add(new Vector3f(x,y,z));
            }
        }
        vertices = temp;
    }

    public void create2SideHyperboloid()
    {
        vertices.clear();
        ArrayList<Vector3f> temp = new ArrayList<>();

        for(double v = -Math.PI/2; v<= Math.PI/2; v+=Math.PI/60){
            for(double u = -Math.PI/2; u<= Math.PI/2; u+=Math.PI/60){
                float x = radiusX * (float)(Math.tan(v) * Math.cos(u));
                float y = radiusY * (float)(Math.tan(v) * Math.sin(u));
                float z = radiusZ * (float)(1/Math.cos(v));
                temp.add(new Vector3f(x,y,z));
            }

            for(double u = Math.PI/2; u<= 3 * Math.PI / 2; u+=Math.PI/60){
                float x = -radiusX * (float)(Math.tan(v) * Math.cos(u));
                float y = -radiusY * (float)(Math.tan(v) * Math.sin(u));
                float z = -radiusZ * (float)(1/Math.cos(v));
                temp.add(new Vector3f(x,y,z));
            }
        }
        vertices = temp;
    }

    public void createEllipticCone()
    {
        vertices.clear();
        ArrayList<Vector3f> temp = new ArrayList<>();

        for(double v = 0; v<= 2 * Math.PI; v+=Math.PI/60){
            for(double u = -Math.PI; u<= Math.PI; u+=Math.PI/60){
                float x = (float) (radiusX * v * Math.cos(u));
                float y = (float) (radiusY * v * Math.sin(u));
                float z = (float) (radiusZ * v);
                temp.add(new Vector3f(x,y,z));
            }
        }
        vertices = temp;
    }

    public void createEllipticParaboloid()
    {
        vertices.clear();
        ArrayList<Vector3f> temp = new ArrayList<>();

        for(double v = 0; v<= 2 * Math.PI; v+=Math.PI/60){
            for(double u = -Math.PI; u<= Math.PI; u+=Math.PI/60){
                float x = (float) (radiusX * v * Math.cos(u));
                float y = (float) (radiusY * v * Math.sin(u));
                float z = (float) (Math.pow(v, 2)/11);
                temp.add(new Vector3f(x,y,z));
            }
        }
        vertices = temp;
    }

    public void createHyperboloidParaboloid()
    {
        vertices.clear();
        ArrayList<Vector3f> temp = new ArrayList<>();

        for(double v = 0; v<= 2 * Math.PI; v+=Math.PI/60){
            for(double u = -Math.PI; u<= Math.PI; u+=Math.PI/60){
                float x = radiusX * (float) (v * Math.tan(u));
                float y = radiusY * (float) (v * 1/Math.cos(u));
                float z = radiusZ * (float)(Math.pow(v, 2));
                temp.add(new Vector3f(x,y,z));
            }
        }
        vertices = temp;
    }

    public float getCpz()
    {
        return (float) cpz;
    }

    public float getRadiusX() {
        return radiusX;
    }

    public float getRadiusY() {
        return radiusY;
    }

    public float getRadiusZ() {
        return radiusZ;
    }
}