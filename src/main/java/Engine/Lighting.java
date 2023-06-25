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

public class Objects extends ShaderProgram {
    boolean collision;
    int vao, vbo, nbo;

    String path;
    UniformsMap uniformsMap;
    Vector3f positiveBorder, negativeBorder, determinator1, determinator2;
    Vector4f color;
    Matrix4f model;

    List<Vector3f> vertices, waypoints;
    List<Vector3f> normal;
    List<Objects> childObject;
    List<Float> centerPoint;

    public Objects(List<ShaderModuleData> shaderModuleDataList, List<Vector3f> vertices, Vector4f color, List<Vector3f> normal, String path) {
        super(shaderModuleDataList);

        this.path = path;
        this.uniformsMap = new UniformsMap(getProgramId());
        this.color = color;
        model = new Matrix4f().identity();

        this.vertices = vertices;
        this.normal = normal;
        childObject = new ArrayList<>();
        centerPoint = Arrays.asList(0f, 0f, 0f);

        positiveBorder = new Vector3f(0, 0, 0);
        negativeBorder = new Vector3f(0, 0, 0);

        loadObject();

        createBorders();

        setupVAOVBO();
    }

    public Objects(List<ShaderModuleData> shaderModuleDataListObjects, Objects otherObject) {
        super(shaderModuleDataListObjects);
        this.path = otherObject.path;
        this.uniformsMap = new UniformsMap(getProgramId());
        this.color = new Vector4f(otherObject.color);
        model = new Matrix4f().identity();

        this.vertices = new ArrayList<>(otherObject.vertices);
        this.normal = new ArrayList<>(otherObject.normal);
        childObject = new ArrayList<>();
        centerPoint = otherObject.centerPoint;

        positiveBorder = new Vector3f(otherObject.positiveBorder);
        negativeBorder = new Vector3f(otherObject.negativeBorder);

        setupVAOVBO();
    }

    //translate, rotate dan scale object
    public void translateObject(Float offsetX, Float offsetY, Float offsetZ) {
        model = new Matrix4f().translate(offsetX, offsetY, offsetZ).mul(new Matrix4f(model));
        updateCenterPoint();

        System.out.println();
        for (Objects child : childObject) {
            child.translateObject(offsetX, offsetY, offsetZ);
        }
    }

    public void translateObject(Float offsetX, Float offsetY, Float offsetZ, List<Objects> otherObjects) {
        model = new Matrix4f().translate(offsetX, offsetY, offsetZ).mul(new Matrix4f(model));
        updateCenterPoint();
        for (Objects child : childObject) {
            child.translateObject(offsetX, offsetY, offsetZ);
        }

        determinator1 = new Vector3f(getCenterPoint().get(0) + Math.abs(positiveBorder.x) + 0.001f, getCenterPoint().get(1) + Math.abs(positiveBorder.y) + 0.001f, getCenterPoint().get(2) + Math.abs(positiveBorder.z) + 0.001f);
        determinator2 = new Vector3f(getCenterPoint().get(0) - Math.abs(negativeBorder.x) - 0.001f, getCenterPoint().get(1) - Math.abs(negativeBorder.y) - 0.001f, getCenterPoint().get(2) - Math.abs(negativeBorder.z) - 0.001f);

        for (Objects i : otherObjects) {
            if (i != this) {
                System.out.println("Det1: " + determinator1.x + " " + determinator1.y + " " + determinator1.z);
                System.out.println("Det2: " + determinator2.x + " " + determinator2.y + " " + determinator2.z);
                System.out.println();
                if ((determinator1.x > i.getCenterPoint().get(0) + Math.abs(positiveBorder.x) + 0.001f && determinator2.x < i.getCenterPoint().get(0) - Math.abs(negativeBorder.x) - 0.001f) &&
                        (determinator1.y > i.getCenterPoint().get(1) + Math.abs(positiveBorder.y) + 0.001f && determinator2.y < i.getCenterPoint().get(1) - Math.abs(negativeBorder.y) - 0.001f) &&
                        (determinator1.z > i.getCenterPoint().get(2) + Math.abs(positiveBorder.z) + 0.001f && determinator2.z < i.getCenterPoint().get(2) - Math.abs(negativeBorder.z) - 0.001f))
                    collision = true;
                break;
            }
        }

        if (collision) {
            model = new Matrix4f().translate(-offsetX, -offsetY, -offsetZ).mul(new Matrix4f(model));
            updateCenterPoint();
            for (Objects child : childObject) {
                child.translateObject(offsetX, offsetY, offsetZ);
            }
        }
        System.out.println(collision);
        System.out.println();
        collision = false;
    }

    public void rotateObject(Float degree, Float x, Float y, Float z) {
        model = new Matrix4f().rotate((float) Math.toRadians(degree), x, y, z).mul(new Matrix4f(model));
        updateCenterPoint();
        for (Objects child : childObject) {
            child.rotateObject(degree, x, y, z);
        }
        createBorders();
    }

    public void rotateObjectOnPoint(Float degree, Float x, Float y, Float z) {
        Vector3f temp = new Vector3f(getCenterPoint().get(0), getCenterPoint().get(1), getCenterPoint().get(2));
        translateObject(-temp.x, -temp.y, -temp.z);
        model = new Matrix4f().rotate((float) Math.toRadians(degree), x, y, z).mul(new Matrix4f(model));
        updateCenterPoint();
        for (Objects child : childObject) {
            child.rotateObject(degree, x, y, z);
        }
        translateObject(temp.x, temp.y, temp.z);
    }

    public void rotateObjectOnPoint(Float degree, Float x, Float y, Float z, List<Objects> otherObjects) {
        Vector3f temp = new Vector3f(getCenterPoint().get(0), getCenterPoint().get(1), getCenterPoint().get(2));
        translateObject(-temp.x, -temp.y, -temp.z, otherObjects);
        model = new Matrix4f().rotate((float) Math.toRadians(degree), x, y, z).mul(new Matrix4f(model));
        updateCenterPoint();
        for (Objects child : childObject) {
            child.rotateObject(degree, x, y, z);
        }
        translateObject(temp.x, temp.y, temp.z, otherObjects);
    }

    public void scaleObject(Float scaleX, Float scaleY, Float scaleZ) {
        model = new Matrix4f().scale(scaleX, scaleY, scaleZ).mul(new Matrix4f(model));
        for (Objects child : childObject) {
            child.scaleObject(scaleX, scaleY, scaleZ);
        }
    }

    public boolean moveToNextPoint(ArrayList<Vector3f> waypoints) {
        if (waypoints.size() != 0) {
            float diffX, diffY, diffZ;
            diffX = (waypoints.get(0).x - getCenterPoint().get(0));
            diffY = (waypoints.get(0).y - getCenterPoint().get(1));
            diffZ = (waypoints.get(0).z - getCenterPoint().get(2));
            translateObject(diffX, diffY, diffZ);
            waypoints.remove(0);
            return true;
        } else {
            vertices.clear();
            return true;
        }
    }

    public ArrayList<Vector3f> generateBezierPoints(float firstX, float firstY, float firstZ, float secondX, float secondY, float secondZ, float thirdX, float thirdY, float thirdZ) {
        ArrayList<Vector3f> result = new ArrayList<>();
        float newX, newY, newZ;
        for (double i = 0; i <= 1; i += 0.005) {
            newX = (float) ((Math.pow((1 - i), 2) * firstX) + (2 * (1 - i) * i * secondX) + (Math.pow(i, 2) * thirdX));
            newY = (float) ((Math.pow((1 - i), 2) * firstY) + (2 * (1 - i) * i * secondY) + (Math.pow(i, 2) * thirdY));
            newZ = (float) ((Math.pow((1 - i), 2) * firstZ) + (2 * (1 - i) * i * secondZ) + (Math.pow(i, 2) * thirdZ));
            result.add(new Vector3f(newX, newY, newZ));
        }

//        childObject.add(new Objects(
//                Arrays.asList
//                        (new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER), new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)),
//                new ArrayList<>(),
//                new Vector4f(66/255f, 80/255f, 93/255f, 1.0f), new ArrayList<>(),
//                "resources/objects/Tiro/shell.obj"
//                )
//        );
        waypoints = new ArrayList<>(result);
        return result;
    }

    public void loadObject() {
        vertices.clear();
        normal.clear();

        Model model = new Model();

        try {
            model = ObjLoader.loadModel(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }

        model.sortVertex();
        vertices = model.sortedVertices;
        normal = model.sortedNormals;
    }

    public void setupVAOVBO() {
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
    public void addVertices(Vector3f newVertices) {
        //Nambah vertice lalu setup VAO VBO NBO lagi
        vertices.add(newVertices);
        setupVAOVBO();
    }

    public void drawSetup(Camera camera, Projection projection) {
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
        uniformsMap.setUniform("dirLight.direction", new Vector3f(0f, 0f, 0f));
        uniformsMap.setUniform("dirLight.ambient", new Vector3f(0.f, 0.f, 0.f));
        uniformsMap.setUniform("dirLight.diffuse", new Vector3f(0.f, 0.f, 0.4f));
        uniformsMap.setUniform("dirLight.specular", new Vector3f(0.f, 0.f, 0.5f));

        //posisi point light
        Vector3f[] _pointLightPositions =
                {
                        new Vector3f(6.751f, -7.0331f, 3.1479f)
//            new Vector3f(2.3f, -3.3f, -4.0f),
//            new Vector3f(4.160f, 3.550f, 5f),
//            new Vector3f(0.0f, 0.0f, 30.0f)
                };

        //kirim posisi light ke shader
        for (int i = 0; i < _pointLightPositions.length; i++) {
            uniformsMap.setUniform("pointLights[" + i + "].position", _pointLightPositions[i]);
            uniformsMap.setUniform("pointLights[" + i + "].ambient", new Vector3f(0.0f, 0.0f, 0.0f));
            uniformsMap.setUniform("pointLights[" + i + "].diffuse", new Vector3f(0.8f, 0.8f, 0.8f));
            uniformsMap.setUniform("pointLights[" + i + "].specular", new Vector3f(0, 0, 0f));
            uniformsMap.setUniform("pointLights[" + i + "].constant", 0);
            uniformsMap.setUniform("pointLights[" + i + "].linear", 0.0f);
            uniformsMap.setUniform("pointLights[" + i + "].quadratic", 0f);
        }

        //kirim posisi light dan config light ke shader
        uniformsMap.setUniform("spotLight.position", new Vector3f(getCenterPoint().get(0) - 1, getCenterPoint().get(1), getCenterPoint().get(2) - 1));
        uniformsMap.setUniform("spotLight.direction", new Vector3f(0, 0, 2));
        uniformsMap.setUniform("spotLight.ambient", new Vector3f(0.5f, 0.5f, 0.5f));
        uniformsMap.setUniform("spotLight.diffuse", new Vector3f(1.0f, 1.0f, 1.0f));
        uniformsMap.setUniform("spotLight.specular", new Vector3f(1.0f, 1.0f, 1.0f));
        uniformsMap.setUniform("spotLight.constant", 1.0f);
        uniformsMap.setUniform("spotLight.linear", 0.09f);
        uniformsMap.setUniform("spotLight.quadratic", 0.032f);
        uniformsMap.setUniform("spotLight.cutOff", (float) Math.cos(Math.toRadians(12.5f)));
        uniformsMap.setUniform("spotLight.outerCutOff", (float) Math.cos(Math.toRadians(12.5f)));


//        camera.printPos(camera);
    }

    public void draw(Camera camera, Projection projection) {
        //binding vao, vbo, nbo dan setup lighting
        drawSetup(camera, projection);

        //config drawing
        glLineWidth(10); //ketebalan garis
        glPointSize(10); //besar kecil vertex
        glDrawArrays(GL_TRIANGLES, 0, vertices.size());

        //draw setiap child
        for (Objects child : childObject) {
            child.draw(camera, projection);
        }
    }

    //update center point object
    public void updateCenterPoint() {
        Vector3f destTemp = new Vector3f();
        model.transformPosition(0.0f, 0.0f, 0.0f, destTemp);
        centerPoint.set(0, destTemp.x);
        centerPoint.set(1, destTemp.y);
        centerPoint.set(2, destTemp.z);
    }

    //getter center point object
    public List<Float> getCenterPoint() {
        updateCenterPoint();
        return centerPoint;
    }
}