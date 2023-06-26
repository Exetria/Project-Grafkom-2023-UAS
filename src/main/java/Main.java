import Engine.*;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.opengl.GL;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;

public class Main
{
    private final Window window = new Window(1920, 1080, "window");
    Camera camera = new Camera();
    Projection projection = new Projection(window.getWidth(), window.getHeight());

    SkyBoxCube skybox;
    ArrayList<Objects> objects = new ArrayList<>();
    ArrayList<Objects> environment = new ArrayList<>();
    ArrayList<Vector3f> waypoints = new ArrayList<>();
    ArrayList<Vector3f> waypoints2 = new ArrayList<>();

    boolean cameraMode = true, fired = false;
    float movement= 0.1f;

    public static void main(String[] args)
    {
        new Main().run();
    }

    public void run()
    {
        init();
        loop();
    }

    public void init()
    {
        window.init();
        GL.createCapabilities();
        glEnable(GL_DEPTH_TEST);
//        camera.setPosition(0, 40,  -60f);
        camera.setPosition(0, 20,  0f);
        camera.setPosition(0, 0f,0f);
        camera.setRotation((float) Math.toRadians(0f),  (float) Math.toRadians(180f));

        skybox = new SkyBoxCube();

        //VINCENTIUS IMMANUEL TIRO
        //C14210047
        {
            //BODY
            objects.add(new Objects
                    (
                            Arrays.asList
                                    (new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER), new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)),
                            new ArrayList<>(),
                            new Vector4f(86/255f, 99/255f, 107/255f, 1.0f), new ArrayList<>(),
                            "resources/objects/Tiro/body.obj"
                    )
            );

            //GUN 1
            objects.get(0).addChildObject(new Objects
                    (
                            Arrays.asList
                                    (new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER), new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)),
                            new ArrayList<>(),
                            new Vector4f(162/255f, 138/255f, 41/255f, 1.0f), new ArrayList<>(),
                            "resources/objects/Tiro/gun.obj"
                    )
            );
            objects.get(0).getChildObject().get(0).translateObject(0f, 4f, 47.15f);

            //GUN 2
            objects.get(0).addChildObject(new Objects
                    (
                            Arrays.asList
                                    (new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER), new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)),
                            new ArrayList<>(),
                            new Vector4f(162/255f, 138/255f, 41/255f, 1.0f), new ArrayList<>(),
                            "resources/objects/Tiro/gun.obj"
                    )
            );
            objects.get(0).getChildObject().get(1).translateObject(0f, 6.1f, 35.3f);

            //GUN 3
            objects.get(0).addChildObject(new Objects
                    (
                            Arrays.asList
                                    (new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER), new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)),
                            new ArrayList<>(),
                            new Vector4f(162/255f, 138/255f, 41/255f, 1.0f), new ArrayList<>(),
                            "resources/objects/Tiro/gun.obj"
                    )
            );
            objects.get(0).getChildObject().get(2).rotateObject(180f, 0f, 1f, 0f);
            objects.get(0).getChildObject().get(2).translateObject(0f, 6.2f, -37.8f);

            //GUN 4
            objects.get(0).addChildObject(new Objects
                    (
                            Arrays.asList
                                    (new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER), new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)),
                            new ArrayList<>(),
                            new Vector4f(162/255f, 138/255f, 41/255f, 1.0f), new ArrayList<>(),
                            "resources/objects/Tiro/gun.obj"
                    )
            );
            objects.get(0).getChildObject().get(3).rotateObject(180f, 0f, 1f, 0f);
            objects.get(0).getChildObject().get(3).translateObject(0f, 4f, -50.01f);

            //SECONDARY GUN
            objects.get(0).addChildObject(new Objects
                    (
                            Arrays.asList
                                    (new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER), new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)),
                            new ArrayList<>(),
                            new Vector4f(162/255f, 138/255f, 41/255f, 1.0f), new ArrayList<>(),
                            "resources/objects/Tiro/secondaryGun.obj"
                    )
            );

            //AA GUN & BOATS
            objects.get(0).addChildObject(new Objects
                    (
                            Arrays.asList
                                    (new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER), new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)),
                            new ArrayList<>(),
                            new Vector4f(111/255f, 111/255f, 111/255f, 1.0f), new ArrayList<>(),
                            "resources/objects/Tiro/AAandBoats.obj"
                    )
            );

            //SUPERSTRUCTURES
            objects.get(0).addChildObject(new Objects
                    (
                            Arrays.asList
                                    (new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER), new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)),
                            new ArrayList<>(),
                            new Vector4f(0.1f, 0.1f, 0.1f, 1.0f), new ArrayList<>(),
                            "resources/objects/Tiro/superstructures.obj"
                    )
            );

            //FENCE
            objects.get(0).addChildObject(new Objects
                    (
                            Arrays.asList
                                    (new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER), new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)),
                            new ArrayList<>(),
                            new Vector4f(0f, 0f, 0f, 1.0f), new ArrayList<>(),
                            "resources/objects/Tiro/fence.obj"
                    )
            );

        }

        //FABIAN OKKY D. S.
        //C14210196
        {

        }

        //CLEMENT GUNADI
        //C14210183
//        {
//            objects.add(new Objects
//                    (
//                            Arrays.asList
//                                    (new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER), new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)),
//                            new ArrayList<>(),
//                            new Vector4f(0.98f, 0.83f, 0.64f, 1.0f), new ArrayList<>(),
//                            "C:\\Users\\Clement\\Intellij\\Project-Grafkom-2023-UAS\\resources\\objects\\Clement\\badanMobil.obj"
//                    )
//            );
//
//            objects.get(2).getChildObject().add(new Objects
//                    (
//                            Arrays.asList
//                                    (new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER), new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)),
//                            new ArrayList<>(),
//                            new Vector4f(0.0f, 0.0f, 0.0f, 1.0f), new ArrayList<>(),
//                            "C:\\Users\\Clement\\Intellij\\Project-Grafkom-2023-UAS\\resources\\objects\\Clement\\banDepanMobil.obj"
//                    )
//            );
//            objects.get(2).getChildObject().get(0).translateObject(1.03f,-0.64f,3.35f);
//
//            objects.get(2).getChildObject().add(new Objects
//                    (
//                            Arrays.asList
//                                    (new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER), new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)),
//                            new ArrayList<>(),
//                            new Vector4f(0.0f, 0.0f, 0.0f, 1.0f), new ArrayList<>(),
//                            "C:\\Users\\Clement\\Intellij\\Project-Grafkom-2023-UAS\\resources\\objects\\Clement\\banBelakangMobil.obj"
//                    )
//            );
//            objects.get(2).getChildObject().get(1).translateObject(1.03f,-0.64f,0.17f);
//
//            objects.get(2).getChildObject().add(new Objects
//                    (
//                            Arrays.asList
//                                    (new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER), new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)),
//                            new ArrayList<>(),
//                            new Vector4f(0.0f, 0.0f, 0.0f, 1.0f), new ArrayList<>(),
//                            "C:\\Users\\Clement\\Intellij\\Project-Grafkom-2023-UAS\\resources\\objects\\Clement\\objectWarnaHitam.obj"
//                    )
//            );
//
//            objects.get(2).getChildObject().add(new Objects
//                    (
//                            Arrays.asList
//                                    (new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER), new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)),
//                            new ArrayList<>(),
//                            new Vector4f(1.0f, 1.0f, 1.0f, 1.0f), new ArrayList<>(),
//                            "C:\\Users\\Clement\\Intellij\\Project-Grafkom-2023-UAS\\resources\\objects\\Clement\\kacaMobil.obj"
//                    )
//            );
//
//            objects.get(2).getChildObject().add(new Objects
//                    (
//                            Arrays.asList
//                                    (new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER), new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)),
//                            new ArrayList<>(),
//                            new Vector4f(1.0f, 0.0f, 0.0f, 1.0f), new ArrayList<>(),
//                            "C:\\Users\\Clement\\Intellij\\Project-Grafkom-2023-UAS\\resources\\objects\\Clement\\lampuMerah.obj"
//                    )
//            );
//
//            objects.get(2).getChildObject().add(new Objects
//                    (
//                            Arrays.asList
//                                    (new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER), new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)),
//                            new ArrayList<>(),
//                            new Vector4f(1.0f, 0.45f, 0.09f, 1.0f), new ArrayList<>(),
//                            "C:\\Users\\Clement\\Intellij\\Project-Grafkom-2023-UAS\\resources\\objects\\Clement\\lampuOrange.obj"
//                    )
//            );
//
//            objects.get(2).getChildObject().add(new Objects
//                    (
//                            Arrays.asList
//                                    (new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER), new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)),
//                            new ArrayList<>(),
//                            new Vector4f(0.98f, 0.8f, 0.64f, 1.0f), new ArrayList<>(),
//                            "C:\\Users\\Clement\\Intellij\\Project-Grafkom-2023-UAS\\resources\\objects\\Clement\\senjataMobil.obj"
//                    )
//            );
//
//            objects.get(2).getChildObject().get(6).translateObject(1.05f,0.9f,1.2f);
//
//            objects.get(2).getChildObject().add(new Objects
//                    (
//                            Arrays.asList
//                                    (new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER), new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)),
//                            new ArrayList<>(),
//                            new Vector4f(1.0f, 1.0f, 1.0f, 1.0f), new ArrayList<>(),
//                            "C:\\Users\\Clement\\Intellij\\Project-Grafkom-2023-UAS\\resources\\objects\\Clement\\lampuMobil.obj"
//                    )
//            );
//        }

        //ENVIRONMENT
        {
            environment.add(new Objects
                    (
                            Arrays.asList
                                    (new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER), new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)),
                            new ArrayList<>(),
                            new Vector4f(119/255f, 148/255f, 40/255f, 1.0f), new ArrayList<>(),
                            "resources/objects/Tiro/island.obj"
                    )
            );
            environment.get(0).getChildObject().add(new Objects
                    (
                            Arrays.asList
                                    (new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER), new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)),
                            new ArrayList<>(),
                            new Vector4f(187/255f, 148/255f, 111/255f, 1.0f), new ArrayList<>(),
                            "resources/objects/Tiro/lighthouse.obj"
                    )
            );
            environment.get(0).getChildObject().add(new Objects
                    (
                            Arrays.asList
                                    (new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER), new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)),
                            new ArrayList<>(),
                            new Vector4f(152/255f, 156/255f, 157/255f, 1.0f), new ArrayList<>(),
                            "resources/objects/Tiro/rocks.obj"
                    )
            );
            environment.get(0).scaleObject(3f, 3f, 3f);
            environment.get(0).translateObject(100f, 0f, 100f);

//            environment.add(new Objects
//                    (
//                            Arrays.asList
//                                    (new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER), new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)),
//                            new ArrayList<>(),
//                            new Vector4f(31/255f, 130/255f, 155/255f, 1.0f), new ArrayList<>(),
//                            "resources/objects/Tiro/ocean.obj"
//                    )
//            );

//            environment.add(new Objects
//                    (
//                            Arrays.asList
//                                    (new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER), new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)),
//                            environment.get(1)
//                    )
//            );
//            environment.get(2).translateObject(160f, 0f, 0f);
//
//            environment.add(new Objects
//                    (
//                            Arrays.asList
//                                    (new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER), new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)),
//                            environment.get(1)
//                    )
//            );
//            environment.get(3).translateObject(-160f, 0f, 0f);
//
//            environment.add(new Objects
//                    (
//                            Arrays.asList
//                                    (new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER), new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)),
//                            environment.get(1)
//                    )
//            );
//            environment.get(4).translateObject(0f, 0f, 160f);
//
//            environment.add(new Objects
//                    (
//                            Arrays.asList
//                                    (new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER), new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)),
//                            environment.get(1)
//                    )
//            );
//            environment.get(5).translateObject(0f, 0f, -160f);
//
//            environment.add(new Objects
//                    (
//                            Arrays.asList
//                                    (new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER), new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)),
//                            environment.get(1)
//                    )
//            );
//            environment.get(6).translateObject(160f, 0f, 160f);
//
//            environment.add(new Objects
//                    (
//                            Arrays.asList
//                                    (new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER), new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)),
//                            environment.get(1)
//                    )
//            );
//            environment.get(7).translateObject(160f, 0f, -160f);
//
//            environment.add(new Objects
//                    (
//                            Arrays.asList
//                                    (new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER), new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)),
//                            environment.get(1)
//                    )
//            );
//            environment.get(8).translateObject(-160f, 0f, 160f);
//
//            environment.add(new Objects
//                    (
//                            Arrays.asList
//                                    (new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER), new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)),
//                            environment.get(1)
//                    )
//            );
//            environment.get(9).translateObject(-160f, 0f, -160f);

//            environment.add(new Objects
//                    (
//                            Arrays.asList
//                                    (new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER), new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)),
//                            environment.get(1)
//                    )
//            );
//            environment.get(9).translateObject(320f, 0f, 0f);
        }
    }


    boolean checkCollision(List<Vector3f> v1, List<Vector3f> v2) {
        float minX1 = 999999999999999f, minY1 = 999999999999999f, minZ1 = 999999999999999f;
        float maxX1 = -999999999999999f, maxY1 = -999999999999999f, maxZ1 = -999999999999999f;

        float minX2 = 999999999999999f, minY2 = 999999999999999f, minZ2 = 999999999999999f;
        float maxX2 = -999999999999999f, maxY2 = -999999999999999f, maxZ2 = -999999999999999f;

        for (Vector3f vertex : v1) {

            minX1 = Math.min(minX1, vertex.x);
            minY1 = Math.min(minY1, vertex.y);
            minZ1 = Math.min(minZ1, vertex.z);

            maxX1 = Math.max(maxX1, vertex.x);
            maxY1 = Math.max(maxY1, vertex.y);
            maxZ1 = Math.max(maxZ1, vertex.z);
        }

        for (Vector3f vertex : v2) {
            minX2 = Math.min(minX2, vertex.x);
            minY2 = Math.min(minY2, vertex.y);
            minZ2 = Math.min(minZ2, vertex.z);

            maxX2 = Math.max(maxX2, vertex.x);
            maxY2 = Math.max(maxY2, vertex.y);
            maxZ2 = Math.max(maxZ2, vertex.z);
        }

        // Memeriksa tumbukan pada sumbu x
        if (maxX1 < minX2 || minX1 > maxX2) {
            return false; // Terjadi pemisahan pada sumbu x
        }

        // Memeriksa tumbukan pada sumbu y
        if (maxY1 < minY2 || minY1 > maxY2) {
            return false; // Terjadi pemisahan pada sumbu y
        }

        // Memeriksa tumbukan pada sumbu z
        if (maxZ1 < minZ2 || minZ1 > maxZ2) {
            return false; // Terjadi pemisahan pada sumbu z
        }

        return true; // Terdapat tumbukan pada semua sumbu
    }
    public void input()
    {
        //WASDQE BUAT ROTATE ATAU TRANSLATE CAMERA
        {
            if(cameraMode)
            {
                if(window.isKeyPressed(GLFW_KEY_Q))
                {
                    camera.moveDown(movement);
                }

                if(window.isKeyPressed(GLFW_KEY_E))
                {
                    camera.moveUp(movement);
                }

                if(window.isKeyPressed(GLFW_KEY_W))
                {
                    camera.moveForward(movement);
                }

                if(window.isKeyPressed(GLFW_KEY_S))
                {
                    camera.moveBackwards(movement);
                }

                if(window.isKeyPressed(GLFW_KEY_A))
                {
                    camera.moveLeft(movement);
                }

                if(window.isKeyPressed(GLFW_KEY_D))
                {
                    camera.moveRight(movement);
                }
            }
            else
            {
                if(window.isKeyPressed(GLFW_KEY_Q))
                {
                    camera.moveUp(movement);
                }

                if(window.isKeyPressed(GLFW_KEY_E))
                {
                    camera.moveUp(movement);
                }

                if(window.isKeyPressed(GLFW_KEY_W))
                {
                    camera.moveForward(movement);
                }

                if(window.isKeyPressed(GLFW_KEY_S))
                {
                    camera.moveBackwards(movement);
                }

                if(window.isKeyPressed(GLFW_KEY_A))
                {
                    objects.get(0).getChildObject().get(0).rotateObjectOnPoint(-0.1f, 0f, 1f, 0f);
                    objects.get(0).getChildObject().get(1).rotateObjectOnPoint(-0.1f, 0f, 1f, 0f);
                    objects.get(0).getChildObject().get(2).rotateObjectOnPoint(0.1f, 0f, 1f, 0f);
                    objects.get(0).getChildObject().get(3).rotateObjectOnPoint(0.1f, 0f, 1f, 0f);
                }

                if(window.isKeyPressed(GLFW_KEY_D))
                {
                    objects.get(0).getChildObject().get(0).rotateObjectOnPoint(0.1f, 0f, 1f, 0f);
                    objects.get(0).getChildObject().get(1).rotateObjectOnPoint(0.1f, 0f, 1f, 0f);
                    objects.get(0).getChildObject().get(2).rotateObjectOnPoint(-0.1f, 0f, 1f, 0f);
                    objects.get(0).getChildObject().get(3).rotateObjectOnPoint(-0.1f, 0f, 1f, 0f);
                }
            }
        }

        //================================================================================

        //ARROWS BUAT ROTATE CAMERA
        {
            if(window.isKeyPressed(GLFW_KEY_UP))
            {
                camera.addRotation(((float) Math.toRadians(-1)), 0);
            }

            if(window.isKeyPressed(GLFW_KEY_DOWN))
            {
                camera.addRotation(((float) Math.toRadians(1)), 0);
            }

            if(window.isKeyPressed(GLFW_KEY_LEFT))
            {
                camera.addRotation(0f, ((float) Math.toRadians(-1)));
            }

            if(window.isKeyPressed(GLFW_KEY_RIGHT))
            {
                camera.addRotation(0f, ((float) Math.toRadians(1)));
            }
        }

        //================================================================================

        //IJKL BUAT TRANSLATE OBJECT
        {
            if(window.isKeyPressed(GLFW_KEY_U))
            {
                for (Objects i: objects)
                {
//                    i.translateObject(0f, 0f, movement, objects);
                }
                objects.get(0).translateObject(0f, 0f, movement, objects);
            }

            if(window.isKeyPressed(GLFW_KEY_O))
            {
                for (Objects i: objects)
                {
//                    i.translateObject(0f, 0f, -movement, objects);
                }
                objects.get(0).translateObject(0f, 0f, -movement, objects);
            }

            if(window.isKeyPressed(GLFW_KEY_I))
            {
                for (Objects i: objects)
                {
//                    i.translateObject(0f, movement, 0f, objects);
                }
                objects.get(0).translateObject(0f, movement, 0f, objects);
            }

            if(window.isKeyPressed(GLFW_KEY_K))
            {
                for (Objects i: objects)
                {
//                    i.translateObject(0f, -movement, 0f, objects);
                }
                objects.get(0).translateObject(0f, -movement, 0f, objects);
            }

            if(window.isKeyPressed(GLFW_KEY_J))
            {
                for (Objects i: objects)
                {
//                    i.translateObject(-movement, 0f, 0f, objects);
                }
                objects.get(0).translateObject(-movement, 0f, 0f, objects);
            }

            if(window.isKeyPressed(GLFW_KEY_L))
            {
                for (Objects i: objects)
                {
//                    i.translateObject(movement, 0f, 0f, objects);
                }
                objects.get(0).translateObject(movement, 0f, 0f, objects);
            }
        }

        //================================================================================

        if(window.isKeyPressed(GLFW_KEY_G))
        {
            cameraMode = false;
            System.out.println("OBJ");
        }

        if(window.isKeyPressed(GLFW_KEY_F))
        {
            cameraMode = true;
            System.out.println("CAM");
        }
        if(window.isKeyPressed(GLFW_KEY_R) && !fired)
        {
//            System.out.println(camera.getPosition().x + " " + camera.getPosition().y + " " + camera.getPosition().z);
            objects.get(0).addChildObject(new Objects
                    (
                            Arrays.asList
                                    (new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER), new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)),
                            new ArrayList<>(),
                            new Vector4f(158/255f, 139/255f, 116/255f, 1.0f), new ArrayList<>(),
                            "resources/objects/Tiro/shell.obj"
                    )
            );
            objects.get(0).getChildObject().get(objects.get(0).getChildObject().size()-1).translateObject(1.2491567f, 5.067824f, 58.865852f);
            objects.get(0).getChildObject().get(objects.get(0).getChildObject().size()-1).scaleObject(0.7f, 0.7f, 0.7f);
            objects.get(0).getChildObject().get(objects.get(0).getChildObject().size()-1).rotateObject(-90f, 0f, 1f, 0f);
            waypoints = objects.get(0).getChildObject().get(objects.get(0).getChildObject().size()-1).generateBezierPoints(1.2491567f, 5.067824f, 58.865852f,
                    1.2491567f, 5.067824f, 70.865852f, 1.2491567f, 3.067824f, 1000.865852f);

            objects.get(0).addChildObject(new Objects
                    (
                            Arrays.asList
                                    (new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER), new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)),
                            new ArrayList<>(),
                            new Vector4f(158/255f, 139/255f, 116/255f, 1.0f), new ArrayList<>(),
                            "resources/objects/Tiro/shell.obj"
                    )
            );
            objects.get(0).getChildObject().get(objects.get(0).getChildObject().size()-1).translateObject(-1.2491567f, 9.067824f, 58.865852f);
            objects.get(0).getChildObject().get(objects.get(0).getChildObject().size()-1).scaleObject(0.7f, 0.7f, 0.7f);
            objects.get(0).getChildObject().get(objects.get(0).getChildObject().size()-1).rotateObject(-90f, 0f, 1f, 0f);
            waypoints2 = objects.get(0).getChildObject().get(objects.get(0).getChildObject().size()-1).generateBezierPoints(-1.2491567f, 5.067824f, 58.865852f,
                    -1.2491567f, 5.067824f, 70.865852f, -1.2491567f, 3.067824f, 1000.865852f);
            fired = true;
        }
        else if(window.isKeyPressed(GLFW_KEY_R))
        {
            objects.get(0).getChildObject().get(objects.get(0).getChildObject().size()-2).moveToNextPoint(waypoints);
            objects.get(0).getChildObject().get(objects.get(0).getChildObject().size()-1).moveToNextPoint(waypoints2);
//            System.out.println(camera.getPosition().x + " " + camera.getPosition().y + " " + camera.getPosition().z);
//            System.out.println(objects.get(0).getChildObject().get(0).getCenterPoint().get(0) + " " + objects.get(0).getChildObject().get(0).getCenterPoint().get(1) + " "
//                    + objects.get(0).getChildObject().get(0).getCenterPoint().get(2));
        }

        if (checkCollision(objects.get(0).getUpdatedVertice(), environment.get(0).getUpdatedVertice()))
        {
            objects.get(0).translateObject(-1f,0f,-1f);
        }

        if(window.isKeyPressed(GLFW_KEY_LEFT_SHIFT))
        {
            for (Objects i: objects)
            {
                camera.moveUp(movement);
            }
        }

        if(window.isKeyPressed(GLFW_KEY_LEFT_CONTROL))
        {
            for (Objects i: objects)
            {
                camera.moveDown(movement);
            }
        }

        if(window.getMouseInput().isLeftButtonPressed())
        {
            Vector2f displayVector = window.getMouseInput().getDisplVec();
            camera.addRotation((float) Math.toRadians(displayVector.x * 0.1f), (float) Math.toRadians(displayVector.y * 0.1f));
        }

        if(window.getMouseInput().getScroll().y != 0)
        {
            projection.setFOV(projection.getFOV() - (window.getMouseInput().getScroll().y * 0.01f));
            window.getMouseInput().setScroll(new Vector2f());
        }


    }

    public void loop()
    {
        while (window.isOpen())
        {
            //Restore State
            window.update();
            glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
            GL.createCapabilities();

            //Code
            for (Objects objects : this.objects)
            {
                //gambar sekalian child
                objects.draw(camera, projection);
            }
            for (Objects objects : this.environment)
            {
                //gambar sekalian child
                objects.draw(camera, projection);
            }
            skybox.draw(camera, projection);

            //Poll for window event
            glDisableVertexAttribArray(0);
            glfwPollEvents();

            input();
        }
    }
}