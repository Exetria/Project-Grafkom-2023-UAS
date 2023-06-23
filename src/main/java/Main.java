import Engine.*;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.opengl.GL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL20.*;

public class Main
{
    private final Window window = new Window(800, 800, "window");
    Camera camera = new Camera();
    Projection projection = new Projection(window.getWidth(), window.getHeight());

    ArrayList<Objects> objects = new ArrayList<>();
    ArrayList<Sphere> spheres = new ArrayList<>();
    ArrayList<Objects> multipleColorObjects = new ArrayList<>();
    ArrayList<Objects> objectsPointControl =  new ArrayList<>();

    float movement= 0.01f, x, y, z;
    int q, e = 0;

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
        camera.setPosition(0, 0,  0.5f);
        camera.setRotation((float) Math.toRadians(0f),  (float) Math.toRadians(0f));

        //segitiga pake 1 warna
        /*{
            objects.add(new Objects(
                            Arrays.asList
                                    (
                                            new ShaderProgram.ShaderModuleData
                                                    ("resources/shaders/scene.vert", GL_VERTEX_SHADER),
                                            new ShaderProgram.ShaderModuleData
                                                    ("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)
                                    ),
                            new ArrayList<>
                                    (
                                            List.of
                                                    (
                                                            new Vector3f(0.0f, 0.5f, 0.0f),
                                                            new Vector3f(-0.5f, -0.5f, 0.0f),
                                                            new Vector3f(0.5f, -0.5f, 0.0f)
                                                    )
                                    ),
                            new Vector4f(0.0f, 1.0f, 1.0f, 1.0f)    //color
                    )
            );
        }
        objects.get(0).translateObject(0.2f, 0.2f, 0.2f);
        objects.get(0).rotateObject((float) Math.toRadians(45), 0f, 0f, 1f);
        objects.get(0).scaleObject(0.5f, 0.5f, 0.5f);*/

       //segitiga pake banyak warna
        /*{
            multipleColorObjects.add(new Object2d(
                            Arrays.asList
                                    (
                                            new ShaderProgram.ShaderModuleData
                                                    ("resources/shaders/sceneWithVerticesColor.vert", GL_VERTEX_SHADER),
                                            new ShaderProgram.ShaderModuleData
                                                    ("resources/shaders/sceneWithVerticescolor.frag", GL_FRAGMENT_SHADER)
                                    ),
                            new ArrayList<>
                                    (
                                            List.of
                                                    (
                                                            new Vector3f(0.0f, 0.5f, 0.0f),
                                                            new Vector3f(-0.5f, -0.5f, 0.0f),
                                                            new Vector3f(0.5f, -0.5f, 0.0f)
                                                    )
                                    ),
                            new ArrayList<>                                         //color
                                    (
                                            List.of
                                                    (
                                                            new Vector3f(0.75f, 0.50f, 0.60f),
                                                            new Vector3f(0.50f, 1.0f, 0.80f),
                                                            new Vector3f(0.5f, 0.0f, 1.0f)
                                                    )
                                    )
                    )
            );
        }*/

        //bikin kotak pake 2 segitiga yang digambar urut per titik
        /*{
            objects.add(new Rectangle
                    (
                            Arrays.asList
                                    (new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER), new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)),
                            new ArrayList<>
                                    (
                                            List.of
                                                    (
                                                            new Vector3f(0.0f, 0.0f, 0.0f),
                                                            new Vector3f(0.5f, 0.0f, 0.0f),
                                                            new Vector3f(0.0f, 0.5f, 0.0f),
                                                            new Vector3f(0.5f, 0.5f, 0.0f)
                                                    )
                                    ),
                            new Vector4f(0.0f, 1.0f, 1.0f, 1.0f),    //color,
                            Arrays.asList(0, 1, 2, 1, 2, 3)
                    )
            );
        }*/

        //objectsPointControl
        /*{
            objectsPointControl.add(new Objects(
                            Arrays.asList
                                    (
                                            new ShaderProgram.ShaderModuleData
                                                    ("resources/shaders/scene.vert", GL_VERTEX_SHADER),
                                            new ShaderProgram.ShaderModuleData
                                                    ("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)
                                    ),
                            new ArrayList<>(),
                            new Vector4f(0.0f, 1.0f, 1.0f, 1.0f)    //color
                    )
            );
        }*/

        //bikin sphere
        /*{
            spheres.add(new Sphere
                (
                        Arrays.asList
                                (new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER), new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)),
                        new ArrayList<>(),
                        new Vector4f(1.0f, 0.0f, 0.0f, 1.0f),0.1, 0.1, 0.1, 0, 0, 0, 3
                )
            );
        }

        {
            spheres.get(0).getChildObjects().add(new Sphere
                    (
                            Arrays.asList
                                    (new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER), new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)),
                            new ArrayList<>(),
                            new Vector4f(0.0f, 0.0f, 1.0f, 1.0f),0.1, 0.1, 0.1, 0, 0, 0, 1
                    )
            );

            spheres.get(0).translateObject(-0.25f, -0.25f, 0);
            spheres.get(0).getChildObjects().get(0).translateObject(0.5f, 0.5f, 1f);
        }*/

        //bikin box (shading)
        {
            spheres.add(new Sphere
                    (
                            Arrays.asList
                                    (new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER), new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)),
                            new ArrayList<>(),
                            new Vector4f(1.0f, 0.0f, 0.0f, 1.0f),0.1, 0.1, 0.1, 0, 0, 0, 0
                    )
            );
        }
    }

    public void input()
    {
        //WASDQE BUAT ROTATE ATAU TRANSLATE CAMERA
        {
            if(window.isKeyPressed(GLFW_KEY_Q) && q == 0)
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
                for (Sphere i: spheres)
                {
                    i.translateObject(0f, 0, 0.001f);
                }
            }

            if(window.isKeyPressed(GLFW_KEY_O))
            {
                for (Sphere i: spheres)
                {
                    i.translateObject(0f, 0, -0.001f);
                }
            }

            if(window.isKeyPressed(GLFW_KEY_I))
            {
                for (Sphere i: spheres)
                {
                    i.translateObject(0f, 0.001f, 0f);
                }
            }

            if(window.isKeyPressed(GLFW_KEY_K))
            {
                for (Sphere i: spheres)
                {
                    i.translateObject(0f, -0.001f, 0f);
                }
            }

            if(window.isKeyPressed(GLFW_KEY_J))
            {
                for (Sphere i: spheres)
                {
                    i.translateObject(-0.001f, 0f, 0f);
                }
            }

            if(window.isKeyPressed(GLFW_KEY_L))
            {
                for (Sphere i: spheres)
                {
                    i.translateObject(0.001f, 0f, 0f);
                }
            }
        }

        //================================================================================

        if(window.isKeyPressed(GLFW_KEY_LEFT_SHIFT))
        {
            camera.moveForward(movement);
        }

        if(window.isKeyPressed(GLFW_KEY_LEFT_CONTROL))
        {
            camera.moveBackwards(movement);
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

            for (Sphere objects : this.spheres)
            {
                //gambar sekalian child
                objects.draw(camera, projection);
            }

            //Poll for window event
            glDisableVertexAttribArray(0);
            glfwPollEvents();

            input();
        }
    }
}