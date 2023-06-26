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

    //milik okky
    float[] belok;
    float[] posx = {0f, -100f, 100f};
    float[] posy = {2.5f, 0f, 4.1f};
    float[] posz = {-2.25f, -100f, 100f};
    float[] rotx = {0.0f, 0, 0};
    float[] roty = {(float) Math.toRadians(180), 0, 0};
    float[] temprotx = {(float) Math.toRadians(30), 0, 0};
    float[] temproty ={0,0,0};
    float[] temposx={0,0,0};
    float[] temposy={0,0,0};
    float[] temposz={0,0,0};
    float selisihx = 0;
    float selisihz = 0;
    boolean[] pos;
    boolean[] geser;
    boolean fpp2, tpp2 = false;
    private Vector3f temp;

    boolean cameraMode = true, fired = false;
    float movement = 0.01f;

    int input = 0;

    public static void main(String[] args) {
        new Main().run();
    }

    public void run() {
        init();
        loop();
    }

    public void init() {
        window.init();
        GL.createCapabilities();
        glEnable(GL_DEPTH_TEST);
        camera.setPosition(0, 20f, 0f);
        camera.setRotation((float) Math.toRadians(0f), (float) Math.toRadians(180f));

        skybox = new SkyBoxCube();

        Vector4f warnalayar = new Vector4f(1f, 1f, 1f, 1f);
        Vector4f warnaspeaker = new Vector4f(60 / 255f, 60 / 255f, 63 / 255f, 1f);
        Vector4f warnamoncong = new Vector4f(0f, 0f, 0f, 1.f);
        Vector4f warnatongkat = new Vector4f(22 / 255f, 6 / 255f, 9 / 255f, 1.0f);
        Vector4f warnatongkatbawah = new Vector4f(7 / 255f, 2 / 255f, 7 / 255f, 1.f);
        Vector4f warnakayu = new Vector4f(76 / 255f, 36 / 255f, 20 / 255f, 1.f);
        Vector4f warnasetir = new Vector4f(20 / 255f, 16 / 255f, 19 / 255f, 1.0f);

        skybox = new SkyBoxCube();
        pos = new boolean[]{true, true, true};
        geser = new boolean[]{false, false, false};
        belok = new float[]{0.0f, 0.0f, 0.0f};
        temp = new Vector3f(0.0f, 2.5f, -2.25f);

        //VINCENTIUS IMMANUEL TIRO
        //C14210047
        {
            //BODY
            objects.add(new Objects
                    (
                            Arrays.asList
                                    (new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER), new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)),
                            new ArrayList<>(),
                            new Vector4f(86 / 255f, 99 / 255f, 107 / 255f, 1.0f), new ArrayList<>(),
                            "resources/objects/Tiro/body.obj"
                    )
            );

            //GUN 1
            objects.get(0).addChildObject(new Objects
                    (
                            Arrays.asList
                                    (new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER), new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)),
                            new ArrayList<>(),
                            new Vector4f(162 / 255f, 138 / 255f, 41 / 255f, 1.0f), new ArrayList<>(),
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
                            new Vector4f(162 / 255f, 138 / 255f, 41 / 255f, 1.0f), new ArrayList<>(),
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
                            new Vector4f(162 / 255f, 138 / 255f, 41 / 255f, 1.0f), new ArrayList<>(),
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
                            new Vector4f(162 / 255f, 138 / 255f, 41 / 255f, 1.0f), new ArrayList<>(),
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
                            new Vector4f(162 / 255f, 138 / 255f, 41 / 255f, 1.0f), new ArrayList<>(),
                            "resources/objects/Tiro/secondaryGun.obj"
                    )
            );

            //AA GUN & BOATS
            objects.get(0).addChildObject(new Objects
                    (
                            Arrays.asList
                                    (new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER), new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)),
                            new ArrayList<>(),
                            new Vector4f(111 / 255f, 111 / 255f, 111 / 255f, 1.0f), new ArrayList<>(),
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
        objects.get(0).translateObject(-100f, 0f, -100f);

        //FABIAN OKKY D. S.
        //C14210196
        {
            //1
            objects.add(new Objects(
                    Arrays.asList
                            (new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER), new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)),
                    new ArrayList<>(),
                    new Vector4f(warnakayu),
                    new ArrayList<>(),
                    "resources/objects/okky/badan.obj"

            ));

            //2
            objects.get(1).addChildObject(new Objects(
                    Arrays.asList
                            (new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER), new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)),
                    new ArrayList<>(),
                    new Vector4f(warnakayu),
                    new ArrayList<>(),
                    "resources/objects/okky/intaibawah.obj"

            ));

            //3
            objects.get(1).addChildObject(new Objects(
                    Arrays.asList
                            (new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER), new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)),
                    new ArrayList<>(),
                    new Vector4f(warnalayar),
                    new ArrayList<>(),
                    "resources/objects/okky/layardepan.obj"

            ));

            //4
            objects.get(1).addChildObject(new Objects(
                    Arrays.asList
                            (new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER), new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)),
                    new ArrayList<>(),
                    new Vector4f(warnalayar),
                    new ArrayList<>(),
                    "resources/objects/okky/layarbelakang.obj"

            ));

            //5
            objects.get(1).addChildObject(new Objects(
                    Arrays.asList
                            (new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER), new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)),
                    new ArrayList<>(),
                    new Vector4f(warnalayar),
                    new ArrayList<>(),
                    "resources/objects/okky/mainsail.obj"

            ));

            //6
            objects.get(1).addChildObject(new Objects(
                    Arrays.asList
                            (new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER), new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)),
                    new ArrayList<>(),
                    new Vector4f(warnamoncong),
                    new ArrayList<>(),
                    "resources/objects/okky/meriamkanan.obj"

            ));

            //7
            objects.get(1).addChildObject(new Objects(
                    Arrays.asList
                            (new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER), new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)),
                    new ArrayList<>(),
                    new Vector4f(warnamoncong),
                    new ArrayList<>(),
                    "resources/objects/okky/meriamkiri.obj"

            ));

            //8
            objects.get(1).addChildObject(new Objects(
                    Arrays.asList
                            (new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER), new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)),
                    new ArrayList<>(),
                    new Vector4f(warnamoncong),
                    new ArrayList<>(),
                    "resources/objects/okky/moncong.obj"

            ));

            //9
            objects.get(1).addChildObject(new Objects(
                    Arrays.asList
                            (new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER), new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)),
                    new ArrayList<>(),
                    new Vector4f(warnatongkat),
                    new ArrayList<>(),
                    "resources/objects/okky/standsetir.obj"

            ));

            //10
            objects.get(1).addChildObject(new Objects(
                    Arrays.asList
                            (new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER), new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)),
                    new ArrayList<>(),
                    new Vector4f(warnakayu),
                    new ArrayList<>(),
                    "resources/objects/okky/gaganglayarbelakang1.obj"

            ));

            //11
            objects.get(1).addChildObject(new Objects(
                    Arrays.asList
                            (new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER), new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)),
                    new ArrayList<>(),
                    new Vector4f(warnakayu),
                    new ArrayList<>(),
                    "resources/objects/okky/gaganglayarbelakang2.obj"

            ));

            //12
            objects.get(1).addChildObject(new Objects(
                    Arrays.asList
                            (new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER), new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)),
                    new ArrayList<>(),
                    new Vector4f(warnakayu),
                    new ArrayList<>(),
                    "resources/objects/okky/gaganglayardepan1.obj"

            ));

            //13
            objects.get(1).addChildObject(new Objects(
                    Arrays.asList
                            (new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER), new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)),
                    new ArrayList<>(),
                    new Vector4f(warnakayu),
                    new ArrayList<>(),
                    "resources/objects/okky/gaganglayardepan2.obj"

            ));

            //14
            objects.get(1).addChildObject(new Objects(
                    Arrays.asList
                            (new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER), new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)),
                    new ArrayList<>(),
                    new Vector4f(warnakayu),
                    new ArrayList<>(),
                    "resources/objects/okky/gaganglayartengah1.obj"

            ));

            //15
            objects.get(1).addChildObject(new Objects(
                    Arrays.asList
                            (new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER), new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)),
                    new ArrayList<>(),
                    new Vector4f(warnakayu),
                    new ArrayList<>(),
                    "resources/objects/okky/gaganglayartengah2.obj"

            ));

            //16
            objects.get(1).addChildObject(new Objects(
                    Arrays.asList
                            (new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER), new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)),
                    new ArrayList<>(),
                    new Vector4f(warnatongkat),
                    new ArrayList<>(),
                    "resources/objects/okky/tongkatdepan.obj"
            ));

            //17
            objects.get(1).addChildObject(new Objects(
                    Arrays.asList
                            (new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER), new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)),
                    new ArrayList<>(),
                    new Vector4f(warnatongkat),
                    new ArrayList<>(),
                    "resources/objects/okky/tongkattengah.obj"

            ));

            //18
            objects.get(1).addChildObject(new Objects(
                    Arrays.asList
                            (new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER), new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)),
                    new ArrayList<>(),
                    new Vector4f(warnatongkat),
                    new ArrayList<>(),
                    "resources/objects/okky/tongkatbelakang.obj"

            ));

            //19
            objects.get(1).addChildObject(new Objects(
                    Arrays.asList
                            (new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER), new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)),
                    new ArrayList<>(),
                    new Vector4f(warnatongkatbawah),
                    new ArrayList<>(),
                    "resources/objects/okky/tongkatbawahdepan.obj"

            ));

            //20
            objects.get(1).addChildObject(new Objects(
                    Arrays.asList
                            (new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER), new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)),
                    new ArrayList<>(),
                    new Vector4f(warnatongkatbawah),
                    new ArrayList<>(),
                    "resources/objects/okky/tongkatbawahtengah.obj"

            ));

            //21
            objects.get(1).addChildObject(new Objects(
                    Arrays.asList
                            (new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER), new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)),
                    new ArrayList<>(),
                    new Vector4f(warnatongkatbawah),
                    new ArrayList<>(),
                    "resources/objects/okky/tongkatbawahbelakang.obj"

            ));

            //22
            objects.get(1).addChildObject(new Objects(
                    Arrays.asList
                            (new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER), new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)),
                    new ArrayList<>(),
                    new Vector4f(warnatongkat),
                    new ArrayList<>(),
                    "resources/objects/okky/pagarkanandepan.obj"

            ));

            //23
            objects.get(1).addChildObject(new Objects(
                    Arrays.asList
                            (new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER), new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)),
                    new ArrayList<>(),
                    new Vector4f(warnatongkat),
                    new ArrayList<>(),
                    "resources/objects/okky/pagardepansetir.obj"

            ));

            //24
            objects.get(1).addChildObject(new Objects(
                    Arrays.asList
                            (new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER), new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)),
                    new ArrayList<>(),
                    new Vector4f(warnatongkat),
                    new ArrayList<>(),
                    "resources/objects/okky/pagarbelakang.obj"

            ));

            //25
            objects.get(1).addChildObject(new Objects(
                    Arrays.asList
                            (new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER), new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)),
                    new ArrayList<>(),
                    new Vector4f(warnatongkat),
                    new ArrayList<>(),
                    "resources/objects/okky/pagarkiribelakang.obj"

            ));

            //26
            objects.get(1).addChildObject(new Objects(
                    Arrays.asList
                            (new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER), new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)),
                    new ArrayList<>(),
                    new Vector4f(warnatongkat),
                    new ArrayList<>(),
                    "resources/objects/okky/pagarkiribelakang2.obj"

            ));

            //27
            objects.get(1).addChildObject(new Objects(
                    Arrays.asList
                            (new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER), new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)),
                    new ArrayList<>(),
                    new Vector4f(warnatongkat),
                    new ArrayList<>(),
                    "resources/objects/okky/pagarkiridepan.obj"

            ));

            //28
            objects.get(1).addChildObject(new Objects(
                    Arrays.asList
                            (new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER), new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)),
                    new ArrayList<>(),
                    new Vector4f(warnatongkat),
                    new ArrayList<>(),
                    "resources/objects/okky/pagarkiri.obj"

            ));

            //29
            objects.get(1).addChildObject(new Objects(
                    Arrays.asList
                            (new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER), new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)),
                    new ArrayList<>(),
                    new Vector4f(warnatongkat),
                    new ArrayList<>(),
                    "resources/objects/okky/pagarkanan.obj"

            ));

            //30
            objects.get(1).addChildObject(new Objects(
                    Arrays.asList
                            (new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER), new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)),
                    new ArrayList<>(),
                    new Vector4f(warnatongkat),
                    new ArrayList<>(),
                    "resources/objects/okky/pagarkanandepan.obj"
            ));

            //31
            objects.get(1).addChildObject(new Objects(
                    Arrays.asList
                            (new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER), new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)),
                    new ArrayList<>(),
                    new Vector4f(warnatongkat),
                    new ArrayList<>(),
                    "resources/objects/okky/pagarkiridepan.obj"
            ));

            //32
            objects.get(1).addChildObject(new Objects(
                    Arrays.asList
                            (new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER), new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)),
                    new ArrayList<>(),
                    new Vector4f(warnatongkat),
                    new ArrayList<>(),
                    "resources/objects/okky/pagarsetir.obj"
            ));

            //33
            objects.get(1).addChildObject(new Objects(
                    Arrays.asList
                            (new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER), new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)),
                    new ArrayList<>(),
                    new Vector4f(warnatongkat),
                    new ArrayList<>(),
                    "resources/objects/okky/pagardepansetir.obj"

            ));

            //34
            objects.get(1).addChildObject(new Objects(
                    Arrays.asList
                            (new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER), new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)),
                    new ArrayList<>(),
                    new Vector4f(warnakayu),
                    new ArrayList<>(),
                    "resources/objects/okky/pagarintai1.obj"

            ));

            //35
            objects.get(1).addChildObject(new Objects(
                    Arrays.asList
                            (new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER), new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)),
                    new ArrayList<>(),
                    new Vector4f(warnakayu),
                    new ArrayList<>(),
                    "resources/objects/okky/pagarintai2.obj"

            ));

            //40
            objects.get(1).addChildObject(new Objects(
                    Arrays.asList
                            (new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER), new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)),
                    new ArrayList<>(),
                    new Vector4f(warnatongkat),
                    new ArrayList<>(),
                    "resources/objects/okky/tanggakanan.obj"

            ));

            //41
            objects.get(1).addChildObject(new Objects(
                    Arrays.asList
                            (new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER), new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)),
                    new ArrayList<>(),
                    new Vector4f(warnatongkat),
                    new ArrayList<>(),
                    "resources/objects/okky/tanggakiri.obj"

            ));

            //42
            objects.get(1).addChildObject(new Objects(
                    Arrays.asList
                            (new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER), new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)),
                    new ArrayList<>(),
                    new Vector4f(warnasetir),
                    new ArrayList<>(),
                    "resources/objects/okky/setirdalam.obj"

            ));

            //43
            objects.get(1).addChildObject(new Objects(
                    Arrays.asList
                            (new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER), new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)),
                    new ArrayList<>(),
                    new Vector4f(warnasetir),
                    new ArrayList<>(),
                    "resources/objects/okky/setirdalam1.obj"

            ));

            //44
            objects.get(1).addChildObject(new Objects(
                    Arrays.asList
                            (new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER), new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)),
                    new ArrayList<>(),
                    new Vector4f(warnasetir),
                    new ArrayList<>(),
                    "resources/objects/okky/setirdalam2.obj"

            ));

            //45
            objects.get(1).addChildObject(new Objects(
                    Arrays.asList
                            (new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER), new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)),
                    new ArrayList<>(),
                    new Vector4f(warnasetir),
                    new ArrayList<>(),
                    "resources/objects/okky/setirdalam3.obj"
            ));

            //46
            objects.get(1).addChildObject(new Objects(
                    Arrays.asList
                            (new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER), new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)),
                    new ArrayList<>(),
                    new Vector4f(warnasetir),
                    new ArrayList<>(),
                    "resources/objects/okky/setirdalam4.obj"
            ));

            //47
            objects.get(1).addChildObject(new Objects(
                    Arrays.asList
                            (new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER), new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)),
                    new ArrayList<>(),
                    new Vector4f(warnasetir),
                    new ArrayList<>(),
                    "resources/objects/okky/setir.obj"
            ));

            //48
            objects.get(1).addChildObject(new Objects(
                    Arrays.asList
                            (new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER), new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)),
                    new ArrayList<>(),
                    new Vector4f(warnasetir),
                    new ArrayList<>(),
                    "resources/objects/okky/setir1.obj"
            ));

            //49
            objects.get(1).addChildObject(new Objects(
                    Arrays.asList
                            (new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER), new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)),
                    new ArrayList<>(),
                    new Vector4f(warnasetir),
                    new ArrayList<>(),
                    "resources/objects/okky/setir2.obj"
            ));

            //50
            objects.get(1).addChildObject(new Objects(
                    Arrays.asList
                            (new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER), new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)),
                    new ArrayList<>(),
                    new Vector4f(warnasetir),
                    new ArrayList<>(),
                    "resources/objects/okky/setir3.obj"
            ));


            objects.get(1).addChildObject(new Objects(
                    Arrays.asList
                            (new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER), new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)),
                    new ArrayList<>(),
                    new Vector4f(warnasetir),
                    new ArrayList<>(),
                    "resources/objects/okky/setir4.obj"
            ));

            objects.get(1).addChildObject(new Objects(
                    Arrays.asList
                            (new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER), new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)),
                    new ArrayList<>(),
                    new Vector4f(warnasetir),
                    new ArrayList<>(),
                    "resources/objects/okky/setir5.obj"
            ));

            objects.get(1).addChildObject(new Objects(
                    Arrays.asList
                            (new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER), new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)),
                    new ArrayList<>(),
                    new Vector4f(warnasetir),
                    new ArrayList<>(),
                    "resources/objects/okky/setir6.obj"
            ));

            objects.get(1).addChildObject(new Objects(
                    Arrays.asList
                            (new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER), new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)),
                    new ArrayList<>(),
                    new Vector4f(warnasetir),
                    new ArrayList<>(),
                    "resources/objects/okky/setir7.obj"
            ));

            objects.get(1).addChildObject(new Objects(
                    Arrays.asList
                            (new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER), new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)),
                    new ArrayList<>(),
                    new Vector4f(warnasetir),
                    new ArrayList<>(),
                    "resources/objects/okky/setir8.obj"
            ));

            objects.get(1).addChildObject(new Objects(
                    Arrays.asList
                            (new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER), new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)),
                    new ArrayList<>(),
                    new Vector4f(warnakayu),
                    new ArrayList<>(),
                    "resources/objects/okky/intaiatas.obj"
            ));


            objects.get(1).addChildObject(new Objects(
                    Arrays.asList
                            (new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER), new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)),
                    new ArrayList<>(),
                    new Vector4f(warnakayu),
                    new ArrayList<>(),
                    "resources/objects/okky/pagarintai3.obj"
            ));

            objects.get(1).addChildObject(new Objects(
                    Arrays.asList
                            (new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER), new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)),
                    new ArrayList<>(),
                    new Vector4f(warnakayu),
                    new ArrayList<>(),
                    "resources/objects/okky/pagarintai4.obj"
            ));

            objects.get(1).addChildObject(new Objects(
                    Arrays.asList
                            (new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER), new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)),
                    new ArrayList<>(),
                    new Vector4f(warnakayu),
                    new ArrayList<>(),
                    "resources/objects/okky/pagarintai5.obj"
            ));

            objects.get(1).addChildObject(new Objects(
                    Arrays.asList
                            (new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER), new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)),
                    new ArrayList<>(),
                    new Vector4f(warnakayu),
                    new ArrayList<>(),
                    "resources/objects/okky/pagarintai6.obj"
            ));

            objects.get(1).addChildObject(new Objects(
                    Arrays.asList
                            (new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER), new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)),
                    new ArrayList<>(),
                    new Vector4f(warnakayu),
                    new ArrayList<>(),
                    "resources/objects/okky/pagarintai7.obj"
            ));

            objects.get(1).addChildObject(new Objects(
                    Arrays.asList
                            (new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER), new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)),
                    new ArrayList<>(),
                    new Vector4f(warnakayu),
                    new ArrayList<>(),
                    "resources/objects/okky/pagarintai8.obj"
            ));
            objects.get(1).addChildObject(new Objects(
                    Arrays.asList
                            (new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER), new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)),
                    new ArrayList<>(),
                    new Vector4f(warnaspeaker),
                    new ArrayList<>(),
                    "resources/objects/okky/speaker.obj"
            ));
        }

        //CLEMENT GUNADI
        //C14210183
        {
            objects.add(new Objects
                    (
                            Arrays.asList
                                    (new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER), new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)),
                            new ArrayList<>(),
                            new Vector4f(0.98f, 0.83f, 0.64f, 1.0f), new ArrayList<>(),
                            "resources\\objects\\Clement\\badanMobil.obj"
                    )
            );

            objects.get(2).getChildObject().add(new Objects
                    (
                            Arrays.asList
                                    (new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER), new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)),
                            new ArrayList<>(),
                            new Vector4f(0.0f, 0.0f, 0.0f, 1.0f), new ArrayList<>(),
                            "resources\\objects\\Clement\\banDepanMobil.obj"
                    )
            );
            objects.get(2).getChildObject().get(0).translateObject(1.03f,-0.64f,3.35f);

            objects.get(2).getChildObject().add(new Objects
                    (
                            Arrays.asList
                                    (new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER), new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)),
                            new ArrayList<>(),
                            new Vector4f(0.0f, 0.0f, 0.0f, 1.0f), new ArrayList<>(),
                            "resources\\objects\\Clement\\banBelakangMobil.obj"
                    )
            );
            objects.get(2).getChildObject().get(1).translateObject(1.03f,-0.64f,0.17f);

            objects.get(2).getChildObject().add(new Objects
                    (
                            Arrays.asList
                                    (new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER), new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)),
                            new ArrayList<>(),
                            new Vector4f(0.0f, 0.0f, 0.0f, 1.0f), new ArrayList<>(),
                            "resources\\objects\\Clement\\objectWarnaHitam.obj"
                    )
            );

            objects.get(2).getChildObject().add(new Objects
                    (
                            Arrays.asList
                                    (new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER), new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)),
                            new ArrayList<>(),
                            new Vector4f(1.0f, 1.0f, 1.0f, 1.0f), new ArrayList<>(),
                            "resources\\objects\\Clement\\kacaMobil.obj"
                    )
            );

            objects.get(2).getChildObject().add(new Objects
                    (
                            Arrays.asList
                                    (new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER), new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)),
                            new ArrayList<>(),
                            new Vector4f(1.0f, 0.0f, 0.0f, 1.0f), new ArrayList<>(),
                            "resources\\objects\\Clement\\lampuMerah.obj"
                    )
            );

            objects.get(2).getChildObject().add(new Objects
                    (
                            Arrays.asList
                                    (new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER), new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)),
                            new ArrayList<>(),
                            new Vector4f(1.0f, 0.45f, 0.09f, 1.0f), new ArrayList<>(),
                            "resources\\objects\\Clement\\lampuOrange.obj"
                    )
            );

            objects.get(2).getChildObject().add(new Objects
                    (
                            Arrays.asList
                                    (new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER), new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)),
                            new ArrayList<>(),
                            new Vector4f(0.98f, 0.8f, 0.64f, 1.0f), new ArrayList<>(),
                            "resources\\objects\\Clement\\senjataMobil.obj"
                    )
            );

            objects.get(2).getChildObject().get(6).translateObject(1.05f,0.9f,1.2f);

            objects.get(2).getChildObject().add(new Objects
                    (
                            Arrays.asList
                                    (new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER), new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)),
                            new ArrayList<>(),
                            new Vector4f(1.0f, 1.0f, 1.0f, 1.0f), new ArrayList<>(),
                            "resources\\objects\\Clement\\lampuMobil.obj"
                    )
            );
        }
        objects.get(2).translateObject(100f, 4.1f, 100f);

        //ENVIRONMENT
        {
            environment.add(new Objects
                    (
                            Arrays.asList
                                    (new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER), new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)),
                            new ArrayList<>(),
                            new Vector4f(119 / 255f, 148 / 255f, 40 / 255f, 1.0f), new ArrayList<>(),
                            "resources/objects/Tiro/island.obj"
                    )
            );
            environment.get(0).getChildObject().add(new Objects
                    (
                            Arrays.asList
                                    (new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER), new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)),
                            new ArrayList<>(),
                            new Vector4f(187 / 255f, 148 / 255f, 111 / 255f, 1.0f), new ArrayList<>(),
                            "resources/objects/Tiro/lighthouse.obj"
                    )
            );
            environment.get(0).getChildObject().add(new Objects
                    (
                            Arrays.asList
                                    (new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER), new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)),
                            new ArrayList<>(),
                            new Vector4f(152 / 255f, 156 / 255f, 157 / 255f, 1.0f), new ArrayList<>(),
                            "resources/objects/Tiro/rocks.obj"
                    )
            );

            environment.add(new Objects
                    (
                            Arrays.asList
                                    (new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER), new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)),
                            new ArrayList<>(),
                            new Vector4f(31 / 255f, 130 / 255f, 155 / 255f, 1.0f), new ArrayList<>(),
                            "resources/objects/Tiro/ocean2.obj"
                    )
            );

            environment.add(new Objects
                    (
                            Arrays.asList
                                    (new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER), new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)),
                            environment.get(1)
                    )
            );
            environment.get(2).translateObject(160f, 0f, 0f);

            environment.add(new Objects
                    (
                            Arrays.asList
                                    (new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER), new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)),
                            environment.get(1)
                    )
            );
            environment.get(3).translateObject(-160f, 0f, 0f);

            environment.add(new Objects
                    (
                            Arrays.asList
                                    (new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER), new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)),
                            environment.get(1)
                    )
            );
            environment.get(4).translateObject(0f, 0f, 160f);

            environment.add(new Objects
                    (
                            Arrays.asList
                                    (new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER), new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)),
                            environment.get(1)
                    )
            );
            environment.get(5).translateObject(0f, 0f, -160f);

            environment.add(new Objects
                    (
                            Arrays.asList
                                    (new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER), new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)),
                            environment.get(1)
                    )
            );
            environment.get(6).translateObject(160f, 0f, 160f);

            environment.add(new Objects
                    (
                            Arrays.asList
                                    (new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER), new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)),
                            environment.get(1)
                    )
            );
            environment.get(7).translateObject(160f, 0f, -160f);

            environment.add(new Objects
                    (
                            Arrays.asList
                                    (new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER), new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)),
                            environment.get(1)
                    )
            );
            environment.get(8).translateObject(-160f, 0f, 160f);

            environment.add(new Objects
                    (
                            Arrays.asList
                                    (new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER), new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)),
                            environment.get(1)
                    )
            );
            environment.get(9).translateObject(-160f, 0f, -160f);
        }
        environment.get(0).scaleObject(3f, 3f, 3f);
        environment.get(0).translateObject(100f, 0f, 100f);
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

    public void input() {
        {
            if (window.isKeyPressed(GLFW_KEY_1)) {
                input = 1;
            } else if (window.isKeyPressed(GLFW_KEY_2)) {
                input = 2;
            } else if (window.isKeyPressed(GLFW_KEY_3)) {
                input = 3;
            }
        }
        if (input == 1)
        {
            if (!geser[0]) {
                if (window.isKeyPressed(GLFW_KEY_RIGHT)) {
                    objects.get(1).rotateObject(0.1f, 0.0f, -1f, 0.0f);

                    camera.setPosition(posx[0], posy[0], posz[0]);
                    camera.rotateTowardsPoint(0.0f, (float) Math.toRadians(-0.1), 0, 0, 0);
                    belok[0] = belok[0] + 0.1f;
                    posx[0] = camera.getPosition().x();
                    posy[0] = camera.getPosition().y();
                    posz[0] = camera.getPosition().z();
                    rotx[0] = camera.getRotation().x();
                    roty[0] = camera.getRotation().y();
                }
                if (window.isKeyPressed(GLFW_KEY_LEFT)) {
                    objects.get(1).rotateObject(0.1f, 0.0f, 1f, 0.0f);

                    belok[0] = belok[0] - 0.1f;
//            if(geser){
//                selisihx=temposx-posx;
//                selisihz=temposz-posz;
//                camera.setPosition(posx,posy,posz);
//                camera.rotateTowardsPoint(0.0f,(float)Math.toRadians(0.1),0,0,0);
//                posx=camera.getPosition().x();
//                posy=camera.getPosition().y();
//                posz=camera.getPosition().z();
//                rotx=camera.getRotation().x();
//                roty=camera.getRotation().y();
//                temposx=posx-selisihx;
//                temposz=posz+selisihz;
//                camera.setPosition(temposx,temposy,temposz);
//
//            }
//            else{
//
//                camera.rotateTowardsPoint(0.0f,(float)Math.toRadians(0.1),0,0,0);
//                posx=camera.getPosition().x();
//                posy=camera.getPosition().y();
//                posz=camera.getPosition().z();
//                rotx=camera.getRotation().x();
//                roty=camera.getRotation().y();
//            }
                    camera.setPosition(posx[0], posy[0], posz[0]);
                    camera.rotateTowardsPoint(0.0f, (float) Math.toRadians(0.1), 0, 0, 0);
                    posx[0] = camera.getPosition().x();
                    posy[0] = camera.getPosition().y();
                    posz[0] = camera.getPosition().z();
                    rotx[0] = camera.getRotation().x();
                    roty[0] = camera.getRotation().y();
                }
            }
            if (window.isKeyPressed(GLFW_KEY_UP)) {

                objects.get(1).translateObject((float) Math.sin(Math.toRadians(belok[0])) * -0.01f, 0.0f, (float) Math.cos(Math.toRadians(belok[0])) * 0.01f);

                if (!pos[0]) {
                    if (geser[0]) {
                        selisihx = temposx[0] - posx[0];
                        selisihz = temposz[0] - posz[0];
                        camera.setPosition(posx[0], posy[0], posz[0]);
                        camera.setRotation(0, roty[0]);
                        camera.moveForward(movement);
                        camera.setRotation((float) Math.toRadians(30), roty[0]);
                        posx[0] = camera.getPosition().x();
                        posy[0] = camera.getPosition().y();
                        posz[0] = camera.getPosition().z();
                        rotx[0] = camera.getRotation().x();
                        roty[0] = camera.getRotation().y();

                        temposx[0] = posx[0] + selisihx;
                        temposz[0] = posz[0] + selisihz;
                        camera.setPosition(temposx[0], temposy[0], temposz[0]);
                        camera.setRotation(temprotx[0], temproty[0]);

                    } else {
                        camera.setRotation(0, roty[0]);
                        camera.moveForward(movement);
                        camera.setRotation((float) Math.toRadians(30), roty[0]);
                        posx[0] = camera.getPosition().x();
                        posy[0] = camera.getPosition().y();
                        posz[0] = camera.getPosition().z();
                        rotx[0] = camera.getRotation().x();
                        roty[0] = camera.getRotation().y();
                    }

                } else {
                    camera.moveForward(movement);
                    posx[0] = camera.getPosition().x();
                    posy[0] = camera.getPosition().y();
                    posz[0] = camera.getPosition().z();
                    rotx[0] = camera.getRotation().x();
                    roty[0] = camera.getRotation().y();
                }
            }


            if (window.isKeyPressed(GLFW_KEY_DOWN)) {
                objects.get(1).translateObject((float) Math.sin(Math.toRadians(belok[0])) * 0.01f, 0.0f, -(float) Math.cos(Math.toRadians(belok[0])) * 0.01f);


                if (!pos[0]) {
                    if (geser[0]) {
                        selisihx = temposx[0] - posx[0];
                        selisihz = temposz[0] - posz[0];
                        camera.setPosition(posx[0], posy[0], posz[0]);
                        camera.setRotation(0, roty[0]);
                        camera.moveBackwards(movement);
                        camera.setRotation((float) Math.toRadians(30), roty[0]);
                        posx[0] = camera.getPosition().x();
                        posy[0] = camera.getPosition().y();
                        posz[0] = camera.getPosition().z();
                        rotx[0] = camera.getRotation().x();
                        roty[0] = camera.getRotation().y();
                        temposx[0] = posx[0] + selisihx;
                        temposz[0] = posz[0] + selisihz;
                        camera.setPosition(temposx[0], temposy[0], temposz[0]);
                        camera.setRotation(temprotx[0], temproty[0]);
                    } else {
                        camera.setRotation(0, roty[0]);
                        camera.moveBackwards(movement);
                        camera.setRotation((float) Math.toRadians(30), roty[0]);
                        posx[0] = camera.getPosition().x();
                        posy[0] = camera.getPosition().y();
                        posz[0] = camera.getPosition().z();
                        rotx[0] = camera.getRotation().x();
                        roty[0] = camera.getRotation().y();
                    }

                } else {
                    camera.moveBackwards(movement);
                    posx[0] = camera.getPosition().x();
                    posy[0] = camera.getPosition().y();
                    posz[0] = camera.getPosition().z();
                    rotx[0] = camera.getRotation().x();
                    roty[0] = camera.getRotation().y();
                }

            }

            if (!pos[0]) {
                if (window.isKeyPressed(GLFW_KEY_Q)) {
                    camera.rotateTowardsPoint(0.0f, (float) Math.toRadians(0.1f), 0, 0, 0);
                    temposx[0] = camera.getPosition().x();
                    temposy[0] = camera.getPosition().y();
                    temposz[0] = camera.getPosition().z();
                    temprotx[0] = camera.getRotation().x();
                    temproty[0] = camera.getRotation().y();
                    geser[0] = true;
                }
                if (window.isKeyPressed(GLFW_KEY_E)) {
                    camera.rotateTowardsPoint(0.0f, (float) Math.toRadians(-0.1f), 0, 0, 0);
                    temposx[0] = camera.getPosition().x();
                    temposy[0] = camera.getPosition().y();
                    temposz[0] = camera.getPosition().z();
                    temprotx[0] = camera.getRotation().x();
                    temproty[0] = camera.getRotation().y();
                    geser[0] = true;
                }
            }

            if (window.isKeyPressed(GLFW_KEY_X)) {
                if (!pos[0]) {
                    posx[0] = posx[0] - 7 * (float) Math.sin(Math.toRadians(belok[0]));
                    posy[0] -= 5;
                    posz[0] = posz[0] + 7 * (float) Math.cos(Math.toRadians(belok[0]));
                    camera.setPosition(posx[0], posy[0], posz[0]);
                } else {
                    camera.setPosition(posx[0], posy[0], posz[0]);
                }
                geser[0] = false;
                pos[0] = true;
                camera.setRotation(0, roty[0]);
                posx[0] = camera.getPosition().x();
                posy[0] = camera.getPosition().y();
                posz[0] = camera.getPosition().z();
                rotx[0] = camera.getRotation().x();
                roty[0] = camera.getRotation().y();
            }

            if (window.isKeyPressed(GLFW_KEY_Z)) {
                if (pos[0]) {
                    posx[0] = (posx[0] + 7 * (float) Math.sin(Math.toRadians(belok[0])));
                    posy[0] += 5;
                    posz[0] = posz[0] - 7 * (float) Math.cos(Math.toRadians(belok[0]));
                    camera.setPosition(posx[0], posy[0], posz[0]);
                } else {
                    camera.setPosition(posx[0], posy[0], posz[0]);
                }
                pos[0] = false;
                geser[0] = false;
                camera.setRotation((float) Math.toRadians(30), roty[0]);
                posx[0] = camera.getPosition().x();
                posy[0] = camera.getPosition().y();
                posz[0] = camera.getPosition().z();
                rotx[0] = camera.getRotation().x();
                roty[0] = camera.getRotation().y();
            }

            if (window.isKeyPressed(GLFW_KEY_SPACE)) {
                objects.get(0).getChildObject().get(objects.get(0).getChildObject().size() - 1).rotateObjectOnPoint(0.5f, 0f, 1f, 0f);
            }
        }
        else if (input == 2)
        {
            if (window.isKeyPressed(GLFW_KEY_X)) {
                    fpp2 = true;
                camera.setPosition(objects.get(0).getChildObject().get(0).getCenterPoint().get(0),
                        objects.get(0).getChildObject().get(0).getCenterPoint().get(1)+7.1f, objects.get(0).getChildObject().get(0).getCenterPoint().get(2)-24);
            }
            if (window.isKeyPressed(GLFW_KEY_V)) {
                fpp2 = true;
                camera.setPosition(objects.get(0).getChildObject().get(0).getCenterPoint().get(0),
                        objects.get(0).getChildObject().get(0).getCenterPoint().get(1)+56f, objects.get(0).getChildObject().get(0).getCenterPoint().get(2)-110);
            }
            if (window.isKeyPressed(GLFW_KEY_C)) {
                fpp2 = false;
                tpp2 = false;
            }
            if (window.isKeyPressed(GLFW_KEY_Q)) {
                objects.get(0).getChildObject().get(0).rotateObjectOnPoint(0.1f, 0f, 1f, 0f);
                objects.get(0).getChildObject().get(1).rotateObjectOnPoint(0.1f, 0f, 1f, 0f);
                objects.get(0).getChildObject().get(2).rotateObjectOnPoint(-0.1f, 0f, 1f, 0f);
                objects.get(0).getChildObject().get(3).rotateObjectOnPoint(-0.1f, 0f, 1f, 0f);
            }

            if (window.isKeyPressed(GLFW_KEY_E)) {
                objects.get(0).getChildObject().get(0).rotateObjectOnPoint(-0.1f, 0f, 1f, 0f);
                objects.get(0).getChildObject().get(1).rotateObjectOnPoint(-0.1f, 0f, 1f, 0f);
                objects.get(0).getChildObject().get(2).rotateObjectOnPoint(0.1f, 0f, 1f, 0f);
                objects.get(0).getChildObject().get(3).rotateObjectOnPoint(0.1f, 0f, 1f, 0f);
            }
            if(!fpp2)
            {
                if (window.isKeyPressed(GLFW_KEY_W)) {
                    camera.moveForward(movement*10);
                }

                if (window.isKeyPressed(GLFW_KEY_S)) {
                    camera.moveBackwards(movement*10);
                }

                if (window.isKeyPressed(GLFW_KEY_A)) {
                    camera.moveLeft(movement*10);
                }

                if (window.isKeyPressed(GLFW_KEY_D)) {
                    camera.moveRight(movement*10);
                }

                if (tpp2)
                {
                    if (window.isKeyPressed(GLFW_KEY_A)) {
                        camera.rotateTowardsPoint(0, -0.01f, objects.get(1).getCenterPoint().get(0), objects.get(1).getCenterPoint().get(1), objects.get(1).getCenterPoint().get(2));
                    }

                    if (window.isKeyPressed(GLFW_KEY_D)) {
                        camera.rotateTowardsPoint(0, +0.01f, objects.get(1).getCenterPoint().get(0), objects.get(1).getCenterPoint().get(1), objects.get(1).getCenterPoint().get(2));
                    }
                }
            }
            else
            {
                    camera.setPosition(objects.get(0).getChildObject().get(0).getCenterPoint().get(0),
                            objects.get(0).getChildObject().get(0).getCenterPoint().get(1)+7.1f, objects.get(0).getChildObject().get(0).getCenterPoint().get(2)-24);
            }

            //================================================================================

            //ARROWS BUAT ROTATE CAMERA
            {
                if (window.isKeyPressed(GLFW_KEY_UP)) {
                    camera.addRotation(((float) Math.toRadians(-0.1)), 0);
                }

                if (window.isKeyPressed(GLFW_KEY_DOWN)) {
                    camera.addRotation(((float) Math.toRadians(0.1)), 0);
                }

                if (window.isKeyPressed(GLFW_KEY_LEFT)) {
                    camera.addRotation(0f, ((float) Math.toRadians(-0.1)));
                }

                if (window.isKeyPressed(GLFW_KEY_RIGHT)) {
                    camera.addRotation(0f, ((float) Math.toRadians(0.1)));
                }
            }

            //================================================================================

            //IJKL BUAT TRANSLATE OBJECT
            {
                if (window.isKeyPressed(GLFW_KEY_U)) {
                    objects.get(0).translateObject(0f, 0f, movement * 5);
                        if(fpp2)
                        {
                            camera.setPosition(objects.get(0).getChildObject().get(0).getCenterPoint().get(0),
                                    objects.get(0).getChildObject().get(0).getCenterPoint().get(1)+7.1f, objects.get(0).getChildObject().get(0).getCenterPoint().get(2)-24);
                        }
                }

                if (window.isKeyPressed(GLFW_KEY_O)) {
                    objects.get(0).translateObject(0f, 0f, -movement * 5);
                }

                if (window.isKeyPressed(GLFW_KEY_I)) {
                    objects.get(0).translateObject(0f, movement * 5, 0f);
                }

                if (window.isKeyPressed(GLFW_KEY_K)) {
                    objects.get(0).translateObject(0f, -movement * 5, 0f);
                }

                if (window.isKeyPressed(GLFW_KEY_J)) {
                    objects.get(0).translateObject(-movement * 5, 0f, 0f);
                }

                if (window.isKeyPressed(GLFW_KEY_L)) {
                    objects.get(0).translateObject(movement * 5, 0f, 0f);
                }
            }

            //================================================================================
            if (checkCollision(objects.get(0).getUpdatedVertice(), environment.get(0).getUpdatedVertice())) {
                objects.get(0).translateObject(-1f, 0f, -1f);
            }
            if (window.isKeyPressed(GLFW_KEY_G)) {
                cameraMode = false;
                System.out.println("OBJ");
            }
            if (window.isKeyPressed(GLFW_KEY_F)) {
                cameraMode = true;
                System.out.println("CAM");
            }
            if(window.isKeyPressed(GLFW_KEY_SPACE))
            {
                System.out.println(camera.getPosition().x + " " + camera.getPosition().y + " " + camera.getPosition().z);
                System.out.println(objects.get(0).getCenterPoint().get(0) + " " + objects.get(0).getCenterPoint().get(1) + " " + objects.get(0).getCenterPoint().get(2));
            }
            if (window.isKeyPressed(GLFW_KEY_R) && !fired)
            {
                System.out.println("FIRED");
                List<Float> gun = objects.get(0).getChildObject().get(0).getCenterPoint();
                objects.get(0).addChildObject(new Objects
                        (
                                Arrays.asList
                                        (new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER), new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)),
                                new ArrayList<>(),
                                new Vector4f(158 / 255f, 139 / 255f, 116 / 255f, 1.0f), new ArrayList<>(),
                                "resources/objects/Tiro/shell.obj"
                        )
                );
                objects.get(0).getChildObject().get(objects.get(0).getChildObject().size() - 1).translateObject(1.2491567f, 5.067824f, 58.865852f);
                objects.get(0).getChildObject().get(objects.get(0).getChildObject().size() - 1).scaleObject(0.7f, 0.7f, 0.7f);
                objects.get(0).getChildObject().get(objects.get(0).getChildObject().size() - 1).rotateObject(-90f, 0f, 1f, 0f);
                waypoints = objects.get(0).getChildObject().get(objects.get(0).getChildObject().size() - 1).generateBezierPoints(1.2491567f-100f, 5.067824f, 58.865852f-100f,
                        1.2491567f-100f, 7.067824f, 70.865852f-100f, 1.2491567f-100f, 3.067824f, 1000.865852f-100f);

                objects.get(0).addChildObject(new Objects
                        (
                                Arrays.asList
                                        (new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER), new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)),
                                new ArrayList<>(),
                                new Vector4f(158 / 255f, 139 / 255f, 116 / 255f, 1.0f), new ArrayList<>(),
                                "resources/objects/Tiro/shell.obj"
                        )
                );
                objects.get(0).getChildObject().get(objects.get(0).getChildObject().size() - 1).translateObject(-1.2491567f, 9.067824f, 58.865852f);
                objects.get(0).getChildObject().get(objects.get(0).getChildObject().size() - 1).scaleObject(0.7f, 0.7f, 0.7f);
                objects.get(0).getChildObject().get(objects.get(0).getChildObject().size() - 1).rotateObject(-90f, 0f, 1f, 0f);
                waypoints2 = objects.get(0).getChildObject().get(objects.get(0).getChildObject().size() - 1).generateBezierPoints(-1.2491567f-100f, 5.067824f, 58.865852f-100f,
                        -1.2491567f-100f, 7.067824f, 70.865852f-100f, -1.2491567f-100f, 3.067824f, 1000.865852f-100f);
                fired = true;
            }
            else if (window.isKeyPressed(GLFW_KEY_R)) {
                objects.get(0).getChildObject().get(objects.get(0).getChildObject().size() - 2).moveToNextPoint(waypoints);
                objects.get(0).getChildObject().get(objects.get(0).getChildObject().size() - 1).moveToNextPoint(waypoints2);
            }

            if (window.isKeyPressed(GLFW_KEY_LEFT_SHIFT)) {
                camera.moveUp(movement * 5);
            }

            if (window.isKeyPressed(GLFW_KEY_LEFT_CONTROL)) {
                camera.moveDown(movement * 5);
            }

            if (window.getMouseInput().isLeftButtonPressed()) {
                Vector2f displayVector = window.getMouseInput().getDisplVec();
                camera.addRotation((float) Math.toRadians(displayVector.x * 0.1f), (float) Math.toRadians(displayVector.y * 0.1f));
            }

            if (window.getMouseInput().getScroll().y != 0) {
                projection.setFOV(projection.getFOV() - (window.getMouseInput().getScroll().y * 0.01f));
                window.getMouseInput().setScroll(new Vector2f());
            }
        }
        else if (input == 3)
        {
            if (cameraMode) {
                if (window.isKeyPressed(GLFW_KEY_Q)) {
                    camera.moveDown(movement);
                }

                if (window.isKeyPressed(GLFW_KEY_E)) {
                    camera.moveUp(movement);
                }

                if (window.isKeyPressed(GLFW_KEY_W)) {
                    camera.moveForward(movement);
                }

                if (window.isKeyPressed(GLFW_KEY_S)) {
                    camera.moveBackwards(movement);
                }

                if (window.isKeyPressed(GLFW_KEY_A)) {
                    camera.moveLeft(movement);
                }

                if (window.isKeyPressed(GLFW_KEY_D)) {
                    camera.moveRight(movement);
                }
            } else {
                if (window.isKeyPressed(GLFW_KEY_Q)) {
                    camera.moveUp(movement);
                }

                if (window.isKeyPressed(GLFW_KEY_E)) {
                    camera.moveUp(movement);
                }

                if (window.isKeyPressed(GLFW_KEY_W)) {
                    camera.moveForward(movement);
                }

                if (window.isKeyPressed(GLFW_KEY_S)) {
                    camera.moveBackwards(movement);
                }

                if (window.isKeyPressed(GLFW_KEY_A)) {
                    camera.moveLeft(movement * 10);
//                    camera.rotateTowardsPoint(0, -0.01f, objects.get(2).getCenterPoint().get(0), objects.get(2).getCenterPoint().get(1), objects.get(2).getCenterPoint().get(2));
                }

                if (window.isKeyPressed(GLFW_KEY_D)) {
                    camera.moveRight(movement * 10);
//                    camera.rotateTowardsPoint(0, 0.01f, objects.get(2).getCenterPoint().get(0), objects.get(2).getCenterPoint().get(1), objects.get(2).getCenterPoint().get(2));
                }
                //IJKL BUAT TRANSLATE OBJECT
                {
                    if (window.isKeyPressed(GLFW_KEY_U)) {
                        objects.get(2).translateObject(0f, 0f, movement);
                    }

                    if (window.isKeyPressed(GLFW_KEY_O)) {
                        objects.get(2).translateObject(0f, 0f, -movement);
                    }

                    if (window.isKeyPressed(GLFW_KEY_I)) {
                        objects.get(2).translateObject(0f, movement, 0f);
                    }

                    if (window.isKeyPressed(GLFW_KEY_K)) {
                        objects.get(2).translateObject(0f, 0 - movement, 0f);
                    }

                    if (window.isKeyPressed(GLFW_KEY_J)) {
                        objects.get(2).translateObject(movement, 0f, 0f);
                    }

                    if (window.isKeyPressed(GLFW_KEY_L)) {
                        objects.get(2).translateObject(-movement, 0f, 0f);
                    }

                    if (window.isKeyPressed(GLFW_KEY_N)) {
                        objects.get(2).getChildObject().get(6).rotateObjectOnPoint(0.1f, 0f, -1f, 0f);
                    }

                    if (window.isKeyPressed(GLFW_KEY_M)) {
                        objects.get(2).getChildObject().get(6).rotateObjectOnPoint(0.1f, 0f, 1f, 0f);
                    }
                }
            }
        }

        //================================================================================

        //ARROWS BUAT ROTATE CAMERA
        {
            if (window.isKeyPressed(GLFW_KEY_UP)) {
                camera.addRotation(((float) Math.toRadians(-1)), 0);
            }

            if (window.isKeyPressed(GLFW_KEY_DOWN)) {
                camera.addRotation(((float) Math.toRadians(1)), 0);
            }

            if (window.isKeyPressed(GLFW_KEY_LEFT)) {
                camera.addRotation(0f, ((float) Math.toRadians(-1)));
            }

            if (window.isKeyPressed(GLFW_KEY_RIGHT)) {
                camera.addRotation(0f, ((float) Math.toRadians(1)));
            }
        }

        //================================================================================



        //================================================================================

        if (window.isKeyPressed(GLFW_KEY_F)) {
            cameraMode = false;
            System.out.println("OBJ");
        }

        if (window.isKeyPressed(GLFW_KEY_G)) {
            cameraMode = true;
            System.out.println("CAM");
        }

        if (window.isKeyPressed(GLFW_KEY_LEFT_SHIFT)) {
            camera.moveUp(movement * 5);
        }

        if (window.isKeyPressed(GLFW_KEY_LEFT_CONTROL)) {
            camera.moveDown(movement*5);
        }

        if (window.getMouseInput().isLeftButtonPressed()) {
            Vector2f displayVector = window.getMouseInput().getDisplVec();
            camera.addRotation((float) Math.toRadians(displayVector.x * 0.1f), (float) Math.toRadians(displayVector.y * 0.1f));
        }

        if (window.getMouseInput().getScroll().y != 0) {
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