import Engine.*;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.opengl.GL;

import java.util.ArrayList;
import java.util.Arrays;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;

public class Tiro
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
        new Tiro().run();
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

//        environment.add(new Objects
//                (
//                        Arrays.asList
//                                (new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER), new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)),
//                        new ArrayList<>(),
//                        new Vector4f(66/255f, 80/255f, 93/255f, 1.0f), new ArrayList<>(),
//                        "resources/objects/Tiro/ocean.obj"
//                )
//        );
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
                    i.translateObject(0f, 0f, movement);
                }
            }

            if(window.isKeyPressed(GLFW_KEY_O))
            {
                for (Objects i: objects)
                {
                    i.translateObject(0f, 0f, -movement);
                }
            }

            if(window.isKeyPressed(GLFW_KEY_I))
            {
                for (Objects i: objects)
                {
                    i.translateObject(0f, movement, 0f);
                }
            }

            if(window.isKeyPressed(GLFW_KEY_K))
            {
                for (Objects i: objects)
                {
                    i.translateObject(0f, -movement, 0f);
                }
            }

            if(window.isKeyPressed(GLFW_KEY_J))
            {
                for (Objects i: objects)
                {
                    i.translateObject(-movement, 0f, 0f);
                }
            }

            if(window.isKeyPressed(GLFW_KEY_L))
            {
                for (Objects i: objects)
                {
                    i.translateObject(movement, 0f, 0f);
                }
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
//            environment.get(0).draw(camera, projection);
            skybox.draw(camera, projection);

            //Poll for window event
            glDisableVertexAttribArray(0);
            glfwPollEvents();

            input();
        }
    }
}