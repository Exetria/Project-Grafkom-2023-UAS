package Engine;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

public class Objects extends ShaderProgram
{
    int vao, vbo, nbo, pick, texture;

    String path;
    UniformsMap uniformsMap;
    Vector4f color;
    Matrix4f model;

    List<Vector3f> vertices;
    List<Vector3f> normal;
    List<Objects> childObject;
    List<Float> centerPoint;

    public Objects(List<ShaderModuleData> shaderModuleDataList, List<Vector3f> vertices, Vector4f color, List<Vector3f> normal, String path)
    {
        super(shaderModuleDataList);

        this.path = path;
        this.uniformsMap = new UniformsMap(getProgramId());
        this.color = color;
        model = new Matrix4f().identity();

        this.vertices = vertices;
        this.normal = normal;
        childObject = new ArrayList<>();
        centerPoint = Arrays.asList(0f,0f,0f);

        loadObject();

        setupVAOVBO();
    }

    //translate, rotate dan scale object
    public void translateObject(Float offsetX,Float offsetY,Float offsetZ)
    {
        model = new Matrix4f().translate(offsetX,offsetY,offsetZ).mul(new Matrix4f(model));
        updateCenterPoint();
        for(Objects child:childObject)
        {
            child.translateObject(offsetX,offsetY,offsetZ);
        }
    }
    public void rotateObject(Float degree, Float x,Float y,Float z)
    {
        model = new Matrix4f().rotate(degree,x,y,z).mul(new Matrix4f(model));
        updateCenterPoint();
        for(Objects child:childObject)
        {
            child.rotateObject(degree,x,y,z);
        }
    }
    public void scaleObject(Float scaleX,Float scaleY,Float scaleZ)
    {
        model = new Matrix4f().scale(scaleX,scaleY,scaleZ).mul(new Matrix4f(model));
        for(Objects child:childObject)
        {
            child.translateObject(scaleX,scaleY,scaleZ);
        }
    }

    public void loadObject()
    {
        vertices.clear();
        normal.clear();

        Model model = new Model();

        try
        {
            model = ObjLoader.loadModel(new File(path));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        for (Face face : model.faces)
        {
            Vector3f n1 = model.normals.get((int) face.normal.x - 1);
            normal.add(n1);

            Vector3f v1 = model.vertices.get((int) face.vertex.x - 1);
            vertices.add(v1);

            Vector3f n2 = model.normals.get((int) face.normal.y - 1);
            normal.add(n2);

            Vector3f v2 = model.vertices.get((int) face.vertex.y - 1);
            vertices.add(v2);

            Vector3f n3 = model.normals.get((int) face.normal.z - 1);
            normal.add(n3);

            Vector3f v3 = model.vertices.get((int) face.vertex.z - 1);
            vertices.add(v3);
        }
    }

    public void setupVAOVBO()
    {
        //setup VAO -> kirim vertexs ke shader
        vao = glGenVertexArrays();
        glBindVertexArray(vao);

        //setup VBO
        vbo = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBufferData(GL_ARRAY_BUFFER, Utils.listoFloat(vertices), GL_STATIC_DRAW);

        //setup NBO -> kirim normals ke shader
        nbo = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, nbo);
        glBufferData(GL_ARRAY_BUFFER, Utils.listoFloat(normal), GL_STATIC_DRAW);
    }

    public void addVertices(Vector3f newVertices)
    {
        //Nambah vertice lalu setup VAO VBO NBO lagi
        vertices.add(newVertices);
        setupVAOVBO();
    }

    public void drawSetup(Camera camera, Projection projection){
        bind();
        uniformsMap.setUniform("uni_color", color);
        uniformsMap.setUniform("model", model);
        uniformsMap.setUniform("view", camera.getViewMatrix());
        uniformsMap.setUniform("projection", projection.getProjMatrix());

        //bind VBO
        glEnableVertexAttribArray(0);
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);

        //bind NBO
        glEnableVertexAttribArray(1);
        glBindBuffer(GL_ARRAY_BUFFER, nbo);
        glVertexAttribPointer(1, 3, GL_FLOAT, false, 0, 0);

        //kirim direction ke shader
        uniformsMap.setUniform("dirLight.direction", new Vector3f(-1f,-.5f,-0.0f));
        uniformsMap.setUniform("dirLight.ambient", new Vector3f(0.05f,0.05f,0.05f));
        uniformsMap.setUniform("dirLight.diffuse", new Vector3f(0.4f,0.4f,0.4f));
        uniformsMap.setUniform("dirLight.specular", new Vector3f(0.5f,0.5f,0.5f));

        //posisi point light
        Vector3f[] _pointLightPositions =
        {
            new Vector3f(6.751f, -7.0331f, 3.1479f),
            new Vector3f(2.3f, -3.3f, -4.0f),
            new Vector3f(4.160f, 3.550f, 5f),
            new Vector3f(0.0f, 0.0f, -3.0f)
        };

        //kirim posisi light ke shader
        for(int i = 0;i< _pointLightPositions.length;i++)
        {
            uniformsMap.setUniform("pointLights["+ i +"].position",_pointLightPositions[i]);
            uniformsMap.setUniform("pointLights["+ i +"].ambient", new Vector3f(0.5f,0.5f,0.05f));
            uniformsMap.setUniform("pointLights["+ i +"].diffuse", new Vector3f(0.8f,0.8f,0.8f));
            uniformsMap.setUniform("pointLights["+ i +"].specular", new Vector3f(1.0f,1.0f,1.0f));
            uniformsMap.setUniform("pointLights["+ i +"].constant",1.0f );
            uniformsMap.setUniform("pointLights["+ i +"].linear", 0.09f);
            uniformsMap.setUniform("pointLights["+ i +"].quadratic", 0.032f);

        }

        //kirim posisi light dan config light ke shader
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
        //binding vao, vbo, nbo dan setup lighting
        drawSetup(camera, projection);

        //config drawing
        glLineWidth(10); //ketebalan garis
        glPointSize(10); //besar kecil vertex
        glDrawArrays(GL_TRIANGLES, 0, vertices.size());

        //draw setiap child
        for(Objects child:childObject)
        {
            child.draw(camera,projection);
        }
    }

    //update center point object
    public void updateCenterPoint()
    {
        Vector3f destTemp = new Vector3f();
        model.transformPosition(0.0f,0.0f,0.0f,destTemp);
        centerPoint.set(0,destTemp.x);
        centerPoint.set(1,destTemp.y);
        centerPoint.set(2,destTemp.z);
        System.out.println(centerPoint.get(0) + " " + centerPoint.get(1));
    }

    //getter center point object
    public List<Float> getCenterPoint()
    {
        updateCenterPoint();
        return centerPoint;
    }

    //tambah child
    public void addChildObject(Objects newObject)
    {
        childObject.add(newObject);
    }

    //getter setter child
    public List<Objects> getChildObject()
    {
        return childObject;
    }
    public void setChildObject(List<Objects> childObject)
    {
        this.childObject = childObject;
    }
}
