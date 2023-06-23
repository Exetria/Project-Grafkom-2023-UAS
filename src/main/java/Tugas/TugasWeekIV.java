package Tugas;

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

class TugasWeekIV
{
    private final Window window = new Window(1000, 1000, "window");
    boolean start = true;

    ArrayList<Sphere> star = new ArrayList<>();
    ArrayList<Sphere> planets = new ArrayList<>();
    ArrayList<Sphere> moons = new ArrayList<>();

    public static void main(String[] args)
    {
        new TugasWeekIV().run();
    }

    public void run()
    {
        init();
        loop();
    }

    public void init()
    {
        if(start)
        {
            window.init();
            GL.createCapabilities();
            start = !start;
        }
        //Matahari
        {
            star.add(new Sphere
                    (
                            Arrays.asList
                                    (new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER), new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)),
                            new ArrayList<>(),
                            new Vector4f(1.0f, 1.0f, 0.0f, 1.0f),
                            0.1, 0.1, 0.1, 0, 0, 0, 1
                    )
            );
            star.get(0).scaleObject(1.5f, 1.5f, 1.5f);
        }

        //Merkurius
        {
            planets.add(new Sphere
                    (
                            Arrays.asList
                                    (new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER), new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)),
                            new ArrayList<>(),
                            new Vector4f(1.0f, 0.0f, 0.0f, 1.0f),
                            0.1, 0.1, 0.1, 0, 0, 0, 1
                    )
            );
            planets.get(0).scaleObject(0.4f, 0.4f, 0.4f);
            planets.get(0).translateObject(0.165f, 0.165f, 0);
        }

        //Venus
        {
            planets.add(new Sphere
                    (
                            Arrays.asList
                                    (new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER), new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)),
                            new ArrayList<>(),
                            new Vector4f(0.3f, 0.3f, 0.5f, 1.0f),
                            0.1, 0.1, 0.1, 0, 0, 0, 1
                    )
            );
            planets.get(1).scaleObject(0.5f, 0.5f, 0.5f);
            planets.get(1).translateObject(0.25f, 0.25f, 0);
        }

        //Bumi
        {
            planets.add(new Sphere
                    (
                            Arrays.asList
                                    (new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER), new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)),
                            new ArrayList<>(),
                            new Vector4f(0.0f, 0.0f, 1.0f, 1.0f),
                            0.1, 0.1, 0.1, 0, 0, 0, 1
                    )
            );
            planets.get(2).scaleObject(0.8f, 0.8f, 0.8f);
            planets.get(2).translateObject(0.43f, 0.43f, 0);

        }

        //Mars
        {
            planets.add(new Sphere
                    (
                            Arrays.asList
                                    (new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER), new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)),
                            new ArrayList<>(),
                            new Vector4f(1.0f, 1.0f, 0.7f, 1.0f),
                            0.1, 0.1, 0.1, 0, 0, 0, 1
                    )
            );
            planets.get(3).scaleObject(0.8f, 0.8f, 0.8f);
            planets.get(3).translateObject(0.7f, 0.7f, 0);
        }

        //Bulan
        {
            moons.add(new Sphere
                    (
                            Arrays.asList
                                    (new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER), new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)),
                            new ArrayList<>(),
                            new Vector4f(0.5f, 0.5f, 0.5f, 1.0f),
                            0.05, 0.05, 0.05, 0, 0, 0, 1
                    )
            );
            moons.get(0).translateObject(0.1f, 0.1f, 0.1f);
            moons.get(0).translateObject(0.45f, 0.45f, 0);
        }
    }

    public void input()
    {
        if(window.isKeyPressed(GLFW_KEY_F))
        {
            planets.get(0).rotateObject((float) Math.toRadians(1), 0, 0, 1);
            planets.get(1).rotateObject((float) Math.toRadians(1), 0, 0, 1);
            planets.get(2).rotateObject((float) Math.toRadians(1), 0, 0, 1);
            planets.get(3).rotateObject((float) Math.toRadians(1), 0, 0, 1);
            moons.get(0).rotateObject((float) Math.toRadians(1), 0, 0, 1);
        }

        if(window.isKeyPressed(GLFW_KEY_G))
        {
            double oldx, oldy;
            star.get(0).rotateObject((float) Math.toRadians(1), 0, 0, 1);

            for (Sphere i: planets)
            {
                oldx = i.getCpx();
                oldy = i.getCpy();
                i.translateObject((float) -i.getCpx(), (float) -i.getCpy(), 0);
                i.rotateObject((float) Math.toRadians(1), 0, 0, 1);
                i.translateObject((float) oldx, (float) oldy, 0);

            }

            oldx = moons.get(0).getCpx();
            oldy = moons.get(0).getCpy();
            moons.get(0).translateObject((float)-moons.get(0).getCpx(), (float)-moons.get(0).getCpy(), 0);
            moons.get(0).rotateObject((float) Math.toRadians(1), 0, 0, 1);
            moons.get(0).translateObject((float)oldx, (float)oldy, 0);


        }

        if(window.isKeyPressed(GLFW_KEY_H))
        {
//            moons.get(0).rotateObjectOnPoint((float) Math.toRadians(1), 0, 0, 1,
//                    (float) planets.get(2).getCpx(), (float) planets.get(2).getCpy());
        }

        if(window.isKeyPressed(GLFW_KEY_DELETE))
        {
            star.clear();
            planets.clear();
            moons.clear();
            init();
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
            for (Objects objects : this.star)
            {
                //1 warna
//                objects.draw();
            }
            for (Objects objects : this.planets)
            {
                //1 warna
//                objects.draw();
            }
            for (Objects objects : this.moons)
            {
                //1 warna
//                objects.draw();
            }

            //Poll for window event
            glDisableVertexAttribArray(0);
            glfwPollEvents();

            input();
        }
    }
}