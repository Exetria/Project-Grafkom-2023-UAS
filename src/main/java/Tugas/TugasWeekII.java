package Tugas;

import Engine.*;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.opengl.GL;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL20.*;

public class TugasWeekII
{
    private Window window = new Window(800, 800, "window");

    ArrayList<Objects>  objects = new ArrayList<>();

    public static void main(String[] args) {
        new TugasWeekII().run();
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
        //background rumput
        {
        objects.add(new Rectangle
        (
            Arrays.asList(new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER), new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)),
            new ArrayList<>
            (
                List.of
                    (
                            new Vector3f(1.0f, -0.65f, 0.0f),       //kanan atas
                            new Vector3f(-1.5f, -0.65f, 0.0f),      //kiri atas
                            new Vector3f(1.0f, -1.0f, 0.0f),        //kanan bawah
                            new Vector3f(-1.0f, -1.0f, 0.0f)        //kiri bawah
                    )
            ),
                new Vector4f(0.0f, 0.5f, 0.0f, 1.0f),    //color
            Arrays.asList(0, 1, 2, 1, 2, 3)
            )
        );
        }

        //background langit
        {
            objects.add(new Rectangle
                    (
                            Arrays.asList(new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER), new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)),
                            new ArrayList<>
                                    (
                                            List.of
                                                    (
                                                            new Vector3f(1.0f, 1.0f, 0.0f),
                                                            new Vector3f(-1.0f, 1.0f, 0.0f),
                                                            new Vector3f(1.0f, -0.65f, 0.0f),
                                                            new Vector3f(-1.5f, -0.65f, 0.0f)
                                                    )
                                    ),
                            new Vector4f(0.19f, 0.19f, 0.88f, 1.0f),    //color,
                            Arrays.asList(0, 1, 2, 1, 2, 3)
                    )
            );
        }

        //atap belakang
        {
            objects.add(new Rectangle
                    (
                            Arrays.asList(new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER), new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)),
                            new ArrayList<>
                                    (
                                            List.of
                                                    (
                                                            new Vector3f(0.36f, 0.05f, 0.0f),
                                                            new Vector3f(-0.36f, 0.05f, 0.0f),
                                                            new Vector3f(0.2f, -0.35f, 0.0f),
                                                            new Vector3f(-0.6f, -0.35f, 0.0f)
                                                    )
                                    ),
                            new Vector4f(1.0f, 0.0f, 0.0f, 1.0f),    //color,
                            Arrays.asList(0, 1, 2, 1, 2, 3)
                    )
            );
        }

        //bangunan rumah bawah
        {
            objects.add
            (new Rectangle
                (
                    Arrays.asList(new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER), new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)),
                    new ArrayList<>
                    (
                        List.of
                        (
                                new Vector3f(0.5f, -0.25f, 0.0f),     //kanan atas
                                new Vector3f(-0.5f, -0.25f, 0.0f),    //kiri atas
                                new Vector3f(0.5f, -0.75f, 0.0f),    //kanan bawah
                                new Vector3f(-0.5f, -0.75f, 0.0f)    //kiri bawah
                        )
                    ),
                    new Vector4f(0.68f, 0.5f, 0.0f, 1.0f),    //color
                    Arrays.asList(0, 1, 2, 1, 2, 3)
                )
            );
        }

        //bangunan rumah atas
        {
            objects.add(new Rectangle
                            (
                                    Arrays.asList(new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER), new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)),
                                    new ArrayList<>
                                            (
                                                    List.of
                                                            (
                                                                    new Vector3f(0.2f, 0.0f, 0.0f),    //kanan atas
                                                                    new Vector3f(-0.35f, 0.f, 0.0f),   //kiri atas
                                                                    new Vector3f(0.2f, -0.25f, 0.0f),    //kanan bawah
                                                                    new Vector3f(-0.5f, -0.25f, 0.0f)    //kiri bawah
                                                            )
                                            ),
                                    new Vector4f(0.68f, 0.5f, 0.0f, 1.0f),    //color
                                    Arrays.asList(0, 1, 2, 1, 2, 3)
                            )
                    );
        }

        //atap depan
        {
            objects.add(new Rectangle
                    (
                            Arrays.asList(new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER), new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)),
                            new ArrayList<>
                                    (
                                            List.of
                                                    (
                                                            new Vector3f(0.36f, 0.05f, 0.0f),
                                                            new Vector3f(-0.36f, 0.0f, 0.0f),
                                                            new Vector3f(0.6f, -0.35f, 0.0f),
                                                            new Vector3f(-0.15f, -0.35f, 0.0f)
                                                    )
                                    ),
                            new Vector4f(1.0f, 0.0f, 0.0f, 1.0f),    //color,
                            Arrays.asList(0, 1, 2, 1, 2, 3)
                    )
            );
        }

        //cerobong bagian coklat
        {
            objects.add
                    (new Rectangle
                            (
                                    Arrays.asList(new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER), new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)),
                                    new ArrayList<>
                                            (
                                                    List.of
                                                            (
                                                                    new Vector3f(0.25f, 0.13f, 0.0f),     //kanan atas
                                                                    new Vector3f(0.17f, 0.13f, 0.0f),    //kiri atas
                                                                    new Vector3f(0.25f, -0.02f, 0.0f),    //kanan bawah
                                                                    new Vector3f(0.17f, -0.02f, 0.0f)    //kiri bawah
                                                            )
                                            ),
                                    new Vector4f(0.68f, 0.5f, 0.0f, 1.0f),    //color
                                    Arrays.asList(0, 1, 2, 1, 2, 3)
                            )
                    );
        }

        //penutup cerobong yg merah
        {
            objects.add
                    (new Rectangle
                            (
                                    Arrays.asList(new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER), new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)),
                                    new ArrayList<>
                                            (
                                                    List.of
                                                            (
                                                                    new Vector3f(0.26f, 0.145f, 0.0f),     //kanan atas
                                                                    new Vector3f(0.16f, 0.145f, 0.0f),    //kiri atas
                                                                    new Vector3f(0.26f, 0.12f, 0.0f),    //kanan bawah
                                                                    new Vector3f(0.16f, 0.12f, 0.0f)    //kiri bawah
                                                            )
                                            ),
                                    new Vector4f(0.69f, 0.01f, 0.01f, 1.0f),    //color
                                    Arrays.asList(0, 1, 2, 1, 2, 3)
                            )
                    );
        }

        //bulan
        {
            objects.add(new Circle
                    (
                            Arrays.asList
                                    (new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER), new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)),
                            new ArrayList<>(),
                            new Vector4f(1.0f, 1.0f, 0.0f, 1.0f),0.12, -0.7, 0.7, 1
                    )
            );
        }

        //penutup bulan
        {
            objects.add(new Circle
                    (
                            Arrays.asList
                                    (new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER), new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)),
                            new ArrayList<>(),
                            new Vector4f(0.19f, 0.19f, 0.88f, 1.0f),0.12, -0.65, 0.698, 1
                    )
            );
        }

        //asap 1 (paling bawah)
        {
            objects.add(new Circle
                    (
                            Arrays.asList
                                    (new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER), new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)),
                            new ArrayList<>(),
                            new Vector4f(0.5f, 0.5f, 0.5f, 1.0f),0.054, 0.211, 0.2, 1.5, 1
                    )
            );
        }

        //asap 2 (tengah)
        {
            objects.add(new Circle
                    (
                            Arrays.asList
                                    (new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER), new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)),
                            new ArrayList<>(),
                            new Vector4f(0.5f, 0.5f, 0.5f, 1.0f),0.075, 0.243, 0.237, 2, 1
                    )
            );
        }

        //asap 3 (paling atas)
        {
            objects.add(new Circle
                    (
                            Arrays.asList
                                    (new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER), new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)),
                            new ArrayList<>(),
                            new Vector4f(0.5f, 0.5f, 0.5f, 1.0f),0.11, 0.313, 0.284, 3, 1
                    )
            );
        }

        //bintang 1 (kiri bawah)
        {
            objects.add
                    (new Star
                            (
                                    Arrays.asList(new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER), new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)),
                                    new ArrayList<>(),
                                    new Vector4f(0.68f, 0.5f, 0.0f, 1.0f),    //color
                                    Arrays.asList(0, 3, 3, 1, 1, 4, 4, 2, 2, 0), 0.047, -0.33, 0.455
                            )
                    );
        }

        //bintang 2 (tengah atas)
        {
            objects.add
                    (new Star
                            (
                                    Arrays.asList(new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER), new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)),
                                    new ArrayList<>(),
                                    new Vector4f(0.68f, 0.5f, 0.0f, 1.0f),    //color
                                    Arrays.asList(0, 3, 3, 1, 1, 4, 4, 2, 2, 0), 0.021, -0.1, 0.815
                            )
                    );
        }

        //bintang 3 (kanan tengah)
        {
            objects.add
                    (new Star
                            (
                                    Arrays.asList(new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER), new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)),
                                    new ArrayList<>(),
                                    new Vector4f(0.68f, 0.5f, 0.0f, 1.0f),    //color
                                    Arrays.asList(0, 3, 3, 1, 1, 4, 4, 2, 2, 0), 0.05, 0.67, 0.615
                            )
                    );
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

            for (Objects object : objects)
            {
                object.draw();
            }

            //Poll for window event
            //
            glDisableVertexAttribArray(0);
            glfwPollEvents();
        }
    }
}