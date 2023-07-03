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
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

public class Objects extends ShaderProgram
{
    int vao, vbo, nbo;

    String path;
    UniformsMap uniformsMap;
    Vector4f color;
    Matrix4f model;

    List<Vector3f> vertices, waypoints;
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

    public Objects(List<ShaderModuleData> shaderModuleDataListObjects, Objects otherObject)
    {
        super(shaderModuleDataListObjects);
        this.path = otherObject.path;
        this.uniformsMap = new UniformsMap(getProgramId());
        this.color = new Vector4f(otherObject.color);
        model = new Matrix4f().identity();

        this.vertices = new ArrayList<>(otherObject.vertices);
        this.normal = new ArrayList<>(otherObject.normal);
        childObject = new ArrayList<>();
        centerPoint = otherObject.centerPoint;

        setupVAOVBO();
    }

    //translate, rotate dan scale object
    public void translateObject(Float offsetX, Float offsetY, Float offsetZ)
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
        model = new Matrix4f().rotate((float) Math.toRadians(degree),x,y,z).mul(new Matrix4f(model));
        updateCenterPoint();
        for(Objects child:childObject)
        {
            child.rotateObject(degree,x,y,z);
        }
    }
    public void rotateObjectOnPoint(Float degree, Float x,Float y,Float z)
    {
        Vector3f temp = new Vector3f(getCenterPoint().get(0), getCenterPoint().get(1), getCenterPoint().get(2));
        translateObject(-temp.x, -temp.y, -temp.z);
        model = new Matrix4f().rotate((float) Math.toRadians(degree),x,y,z).mul(new Matrix4f(model));
        updateCenterPoint();
        for(Objects child:childObject)
        {
            child.rotateObject(degree,x,y,z);
        }
        translateObject(temp.x, temp.y, temp.z);
    }
    public void scaleObject(Float scaleX,Float scaleY,Float scaleZ)
    {
        model = new Matrix4f().scale(scaleX,scaleY,scaleZ).mul(new Matrix4f(model));
        for(Objects child:childObject)
        {
            child.scaleObject(scaleX,scaleY,scaleZ);
        }
    }

    public void moveToNextPoint(ArrayList<Vector3f> waypoints)
    {
        if(waypoints.size() != 0)
        {
            float diffX, diffY, diffZ;
            diffX = (waypoints.get(0).x - getCenterPoint().get(0));
            diffY = (waypoints.get(0).y - getCenterPoint().get(1));
            diffZ = (waypoints.get(0).z - getCenterPoint().get(2));
            translateObject(diffX, diffY, diffZ);
            waypoints.remove(0);
        }
        else
        {
            vertices.clear();
        }
    }

    public ArrayList<Vector3f> generateBezierPoints(float firstX, float firstY, float firstZ, float secondX, float secondY, float secondZ, float thirdX, float thirdY, float thirdZ)
    {
        ArrayList<Vector3f> result = new ArrayList<>();
        float newX, newY, newZ;
        for(double i = 0; i <=1; i+= 0.005)
        {
            newX = (float) ((Math.pow((1-i), 2) * firstX) + (2 * (1-i) * i * secondX) + (Math.pow(i, 2) * thirdX));
            newY = (float) ((Math.pow((1-i), 2) * firstY) + (2 * (1-i) * i * secondY) + (Math.pow(i, 2) * thirdY));
            newZ = (float) ((Math.pow((1-i), 2) * firstZ) + (2 * (1-i) * i * secondZ) + (Math.pow(i, 2) * thirdZ));
            result.add(new Vector3f(newX, newY, newZ));
        }
        waypoints = new ArrayList<>(result);
        return result;
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

        model.sortVertex();
        vertices = model.sortedVertices;
        normal = model.sortedNormals;
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

    public List<Vector3f> dataObject (){
        List<Vector3f> dataObject = new ArrayList<>();
        for (int i = 0; i < vertices.size(); i++) {
            Vector4f vertex = new Vector4f(vertices.get(i), 1.0f);
            model.transform(vertex);
            dataObject.add(new Vector3f(vertex.x, vertex.y, vertex.z));
        }
        return dataObject;
    }

    public void drawSetup(Camera camera, Projection projection)
    {
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
        uniformsMap.setUniform("dirLight.direction", new Vector3f(1f, -1f, 0f));
        uniformsMap.setUniform("dirLight.ambient", new Vector3f(0.6f,0.6f,0.6f));
        uniformsMap.setUniform("dirLight.diffuse", new Vector3f(0.5f,0.5f,0.5f));
        uniformsMap.setUniform("dirLight.specular", new Vector3f(0.6f,0.6f,0.6f));

        //posisi point light
        Vector3f[] _pointLightPositions =
        {
                new Vector3f(-102.78f, 5.32f, -171.59f),
                new Vector3f(-102.90f, 6.05f, -150.60f),
                new Vector3f(-102.73f, 8.67f, -136.17f),
                new Vector3f(-105.24f, 7.8f, -123.71f),
                new Vector3f(-105.54f, 9.01f, -112.65f),
                new Vector3f(-105.09f, 11.19f, -97.23f),
                new Vector3f(-105.55f, 10.64f, -90.18f),
                new Vector3f(-105.48f, 9.22f, -77.99f),
                new Vector3f(-103.31f, 6.66f, -66.30f),
                new Vector3f(-103.35f, 4.10f, -55.00f),
        };

        //kirim posisi light ke shader
        for(int i = 0;i< _pointLightPositions.length;i++)
        {
        uniformsMap.setUniform("pointLights["+ i +"].position",_pointLightPositions[i]);
        uniformsMap.setUniform("pointLights["+ i +"].ambient", new Vector3f(0.65f,0.65f,0.65f));
        uniformsMap.setUniform("pointLights["+ i +"].diffuse", new Vector3f(0.6f,0.6f,0.6f));
        uniformsMap.setUniform("pointLights["+ i +"].specular", new Vector3f(0.5f,0.5f,0.5f));
        uniformsMap.setUniform("pointLights["+ i +"].constant",1f);
        uniformsMap.setUniform("pointLights["+ i +"].linear", 0.08f);
        uniformsMap.setUniform("pointLights["+ i +"].quadratic", 0.08f);
        }

        //kirim posisi light dan config light ke shader
        uniformsMap.setUniform("spotLight.position",new Vector3f(new Vector3f(80.21477f, 30.752567f, 111.83497f)));
        uniformsMap.setUniform("spotLight.direction",new Vector3f(-1, 0.05f, -1));
        uniformsMap.setUniform("spotLight.ambient",new Vector3f(0.7f,0.7f,0.7f));
        uniformsMap.setUniform("spotLight.diffuse",new Vector3f(0.5f,0.5f,0.5f));
        uniformsMap.setUniform("spotLight.specular",new Vector3f(0.1f,0.1f,0.1f));
        uniformsMap.setUniform("spotLight.constant",0f);
        uniformsMap.setUniform("spotLight.linear",0.0001f);
        uniformsMap.setUniform("spotLight.quadratic",0.007f);
        uniformsMap.setUniform("spotLight.cutOff",(float)Math.cos(Math.toRadians(12.5f)));
        uniformsMap.setUniform("spotLight.outerCutOff",(float)Math.cos(Math.toRadians(12.5f)));
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
}
