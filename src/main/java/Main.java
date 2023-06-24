import Engine.*;
import org.joml.Vector2f;
import org.joml.Vector4f;
import org.lwjgl.opengl.GL;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;

public class Main
{
    private final Window window = new Window(1000, 1000, "window");
    Camera camera = new Camera();
    Projection projection = new Projection(window.getWidth(), window.getHeight());

    ArrayList<Objects> objects = new ArrayList<>();
    ArrayList<Sphere> spheres = new ArrayList<>();

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
        camera.setPosition(0, 0,  0.5f);
        camera.setRotation((float) Math.toRadians(0f),  (float) Math.toRadians(0f));

        spheres.add(new Sphere
                (
                        Arrays.asList
                                (new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER), new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)),
                        new ArrayList<>(),
                        new Vector4f(1.0f, 0.0f, 0.0f, 1.0f),
                        "C:/Users/Vince/OneDrive/Documents/GitHub/Obj/squidward.obj"
                )
        );
        spheres.get(0).translateObject(0, 0, -2);

        spheres.add(new Sphere
                (
                        Arrays.asList
                                (new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER), new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)),
                        new ArrayList<>(),
                        new Vector4f(1.0f, 0.0f, 0.0f, 1.0f),
                        "C:/Users/Vince/OneDrive/Documents/GitHub/Obj/testing2.obj"
                )
        );
        spheres.get(0).translateObject(0, 0, 10);
    }

    public void input()
    {
        //WASDQE BUAT ROTATE ATAU TRANSLATE CAMERA
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
            for (Sphere i: spheres)
            {
                camera.moveUp(movement);
            }
        }

        if(window.isKeyPressed(GLFW_KEY_LEFT_CONTROL))
        {
            for (Sphere i: spheres)
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