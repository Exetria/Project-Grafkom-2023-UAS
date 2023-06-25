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
import static org.lwjgl.opengl.GL30.*;

public class Okky {
    private Window window =
            new Window
                    (1000,1000,"Hello World");
    private ArrayList<Objects> objects
            = new ArrayList<>();
    private ArrayList<Objects> objectsRectangle
            = new ArrayList<>();

    private ArrayList<Objects> objectsPointsControl
            = new ArrayList<>();

    SkyBoxCube skybox;
    private MouseInput mouseInput;
    int countDegree = 0;
    float lurus;
    float belok;
    float posx=0.0f;
    float posy=2.5f;
    float posz=-2.25f;
    float rotx=0.0f;
    float roty=(float)Math.toRadians(180);
    boolean pos;
    private Vector3f temp;


    Projection projection = new Projection(window.getWidth(),window.getHeight());
    Camera camera = new Camera();
    public void init(){
        window.init();
        GL.createCapabilities();
        glEnable(GL_DEPTH_TEST);
        glEnable(GL_CULL_FACE);
        glCullFace(GL_BACK);
        glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);
        glDepthMask(true);
        glDepthFunc(GL_LEQUAL);
        glDepthRange(0.0f, 0.5f);

        skybox =new SkyBoxCube();
        pos=false;
        lurus=0.0f;
        belok=0.0f;
        temp=new Vector3f(0.0f, 2.5f, -2.25f);

        mouseInput = window.getMouseInput();
        camera.setPosition(0,0,1.0f);
        camera.setRotation((float)Math.toRadians(0.0f),(float)Math.toRadians(30.0f));
        //code
//        objects.add(new Object2d(
//            Arrays.asList(
//                //shaderFile lokasi menyesuaikan objectnya
//                new ShaderProgram.ShaderModuleData
//                ("resources/shaders/scene.vert"
//                , GL_VERTEX_SHADER),
//                new ShaderProgram.ShaderModuleData
//                ("resources/shaders/scene.frag"
//                , GL_FRAGMENT_SHADER)
//            ),
//            new ArrayList<>(
//                List.of(
//                    new Vector3f(0.0f,0.5f,0.0f),
//                    new Vector3f(-0.5f,-0.5f,0.0f),
//                    new Vector3f(0.5f,-0.5f,0.0f)
//                )
//            ),
//            new Vector4f(0.0f,1.0f,1.0f,1.0f)
//        ));
//        objects.add(new Objects(
//            Arrays.asList(
//                //shaderFile lokasi menyesuaikan objectnya
//                new ShaderProgram.ShaderModuleData
//                ("resources/shaders/" +
//                    "sceneWithVerticesColor.vert"
//                        , GL_VERTEX_SHADER),
//                new ShaderProgram.ShaderModuleData
//                    ("resources/shaders/" +
//                    "sceneWithVerticesColor.frag"
//                            , GL_FRAGMENT_SHADER)
//        ),
//        new ArrayList<>(
//                List.of(
//                    new Vector3f(0.0f,0.5f,0.0f),
//                    new Vector3f(-0.5f,-0.5f,0.0f),
//                    new Vector3f(0.5f,-0.5f,0.0f)
//                )
//            ),
//        new ArrayList<>(
//            List.of(
//                new Vector3f(1.0f,0.0f,0.0f),
//                new Vector3f(0.0f,1.0f,0.0f),
//                new Vector3f(0.0f,0.0f,1.0f)
//            )
//        )
//        ));
//        objectsRectangle.add(new Rectangle(
//            Arrays.asList(
//                //shaderFile lokasi menyesuaikan objectnya
//                new ShaderProgram.ShaderModuleData
//                ("resources/shaders/scene.vert"
//                , GL_VERTEX_SHADER),
//                new ShaderProgram.ShaderModuleData
//                ("resources/shaders/scene.frag"
//                , GL_FRAGMENT_SHADER)
//            ),
//            new ArrayList<>(
//                List.of(
//                    new Vector3f(0.0f,0.0f,0.0f),
//                    new Vector3f(0.5f,0.0f,0.0f),
//                    new Vector3f(0.0f,0.5f,0.0f),
//                    new Vector3f( 0.5f,0.5f,0.0f)
//                )
//            ),
//            new Vector4f(0.0f,1.0f,1.0f,1.0f),
//            Arrays.asList(0,1,2,1,2,3)
//
//        ));
//        objectsPointsControl.add(new Objects(
//            Arrays.asList(
//                //shaderFile lokasi menyesuaikan objectnya
//                new ShaderProgram.ShaderModuleData
//                ("resources/shaders/scene.vert"
//                , GL_VERTEX_SHADER),
//                new ShaderProgram.ShaderModuleData
//                ("resources/shaders/scene.frag"
//                , GL_FRAGMENT_SHADER)
//            ),
//            new ArrayList<>(),
//            new Vector4f(0.0f,1.0f,1.0f,1.0f)
//        ));

        Vector4f warnasetir = new Vector4f(70/255f, 200/255f, 125/255f, 1f);
        Vector4f warnakapal = new Vector4f(43/255f, 125/255f, 26/255f, 1f);
        Vector4f warnalayar = new Vector4f(1f, 1f, 1f,1f);
        Vector4f warnamoncong = new Vector4f(0f, 0f, 0f,1.f);
        Vector4f warnatongkat = new Vector4f(0f, 0f, 1f, 1.f);
        Vector4f warnatongkatbawah = new Vector4f(1f, 107/255f, 196/255f, 1.f);
        Vector4f warnatangga = new Vector4f(121/255f, 124/255f, 132/255f, 1.f);


        List<ShaderProgram.ShaderModuleData> shader = Arrays.asList(
                //shaderFile lokasi menyesuaikan objectnya
                new ShaderProgram.ShaderModuleData
                        ("resources/shaders/scene.vert"
                                , GL_VERTEX_SHADER),
                new ShaderProgram.ShaderModuleData
                        ("resources/shaders/scene.frag"
                                , GL_FRAGMENT_SHADER)
        );

        //1
        objects.add(new Objects(
                shader,
                new ArrayList<>(),
                new Vector4f(warnakapal),
                new ArrayList<>(),
                "C:\\Java\\Git\\UAS\\Project-Grafkom-2023-UAS\\resources\\objects\\okky\\badan.obj"

        ));

        //2
        objects.add(new Objects(
                shader,
                new ArrayList<>(),
                new Vector4f(warnakapal),
                new ArrayList<>(),
                "C:\\Java\\Git\\UAS\\Project-Grafkom-2023-UAS\\resources\\objects\\okky\\intaibawah.obj"

        ));

        //3
        objects.add(new Objects(
                shader,
                new ArrayList<>(),
                new Vector4f(warnalayar),
                new ArrayList<>(),
                "C:\\Java\\Git\\UAS\\Project-Grafkom-2023-UAS\\resources\\objects\\okky\\layardepan.obj"

        ));

        //4
        objects.add(new Objects(
                shader,
                new ArrayList<>(),
                new Vector4f(warnalayar),
                new ArrayList<>(),
                "C:\\Java\\Git\\UAS\\Project-Grafkom-2023-UAS\\resources\\objects\\okky\\layarbelakang.obj"

        ));

        //5
        objects.add(new Objects(
                shader,
                new ArrayList<>(),
                new Vector4f(warnalayar),
                new ArrayList<>(),
                "C:\\Java\\Git\\UAS\\Project-Grafkom-2023-UAS\\resources\\objects\\okky\\mainsail.obj"

        ));

        //6
        objects.add(new Objects(
                shader,
                new ArrayList<>(),
                new Vector4f(warnamoncong),
                new ArrayList<>(),
                "C:\\Java\\Git\\UAS\\Project-Grafkom-2023-UAS\\resources\\objects\\okky\\meriamkanan.obj"

        ));

        //7
        objects.add(new Objects(
                shader,
                new ArrayList<>(),
                new Vector4f(warnamoncong),
                new ArrayList<>(),
                "C:\\Java\\Git\\UAS\\Project-Grafkom-2023-UAS\\resources\\objects\\okky\\meriamkiri.obj"

        ));

        //8
        objects.add(new Objects(
                shader,
                new ArrayList<>(),
                new Vector4f(warnamoncong),
                new ArrayList<>(),
                "C:\\Java\\Git\\UAS\\Project-Grafkom-2023-UAS\\resources\\objects\\okky\\moncong.obj"

        ));

        //9
        objects.add(new Objects(
                shader,
                new ArrayList<>(),
                new Vector4f(warnasetir),
                new ArrayList<>(),
                "C:\\Java\\Git\\UAS\\Project-Grafkom-2023-UAS\\resources\\objects\\okky\\standsetir.obj"

        ));

        //10
        objects.add(new Objects(
                shader,
                new ArrayList<>(),
                new Vector4f(warnasetir),
                new ArrayList<>(),
                "C:\\Java\\Git\\UAS\\Project-Grafkom-2023-UAS\\resources\\objects\\okky\\gaganglayarbelakang1.obj"

        ));

        //11
        objects.add(new Objects(
                shader,
                new ArrayList<>(),
                new Vector4f(warnasetir),
                new ArrayList<>(),
                "C:\\Java\\Git\\UAS\\Project-Grafkom-2023-UAS\\resources\\objects\\okky\\gaganglayarbelakang2.obj"

        ));

        //12
        objects.add(new Objects(
                shader,
                new ArrayList<>(),
                new Vector4f(warnasetir),
                new ArrayList<>(),
                "C:\\Java\\Git\\UAS\\Project-Grafkom-2023-UAS\\resources\\objects\\okky\\gaganglayardepan1.obj"

        ));

        //13
        objects.add(new Objects(
                shader,
                new ArrayList<>(),
                new Vector4f(warnasetir),
                new ArrayList<>(),
                "C:\\Java\\Git\\UAS\\Project-Grafkom-2023-UAS\\resources\\objects\\okky\\gaganglayardepan2.obj"

        ));

        //14
        objects.add(new Objects(
                shader,
                new ArrayList<>(),
                new Vector4f(warnasetir),
                new ArrayList<>(),
                "C:\\Java\\Git\\UAS\\Project-Grafkom-2023-UAS\\resources\\objects\\okky\\gaganglayartengah1.obj"

        ));

        //15
        objects.add(new Objects(
                shader,
                new ArrayList<>(),
                new Vector4f(warnasetir),
                new ArrayList<>(),
                "C:\\Java\\Git\\UAS\\Project-Grafkom-2023-UAS\\resources\\objects\\okky\\gaganglayartengah2.obj"

        ));

        //16
        objects.add(new Objects(
                shader,
                new ArrayList<>(),
                new Vector4f(warnatongkat),
                new ArrayList<>(),
                "C:\\Java\\Git\\UAS\\Project-Grafkom-2023-UAS\\resources\\objects\\okky\\tongkatdepan.obj"
        ));

        //17
        objects.add(new Objects(
                shader,
                new ArrayList<>(),
                new Vector4f(warnatongkat),
                new ArrayList<>(),
                "C:\\Java\\Git\\UAS\\Project-Grafkom-2023-UAS\\resources\\objects\\okky\\tongkattengah.obj"

        ));

        //18
        objects.add(new Objects(
                shader,
                new ArrayList<>(),
                new Vector4f(warnatongkat),
                new ArrayList<>(),
                "C:\\Java\\Git\\UAS\\Project-Grafkom-2023-UAS\\resources\\objects\\okky\\tongkatbelakang.obj"

        ));

        //19
        objects.add(new Objects(
                shader,
                new ArrayList<>(),
                new Vector4f(warnatongkatbawah),
                new ArrayList<>(),
                "C:\\Java\\Git\\UAS\\Project-Grafkom-2023-UAS\\resources\\objects\\okky\\tongkatbawahdepan.obj"

        ));

        //20
        objects.add(new Objects(
                shader,
                new ArrayList<>(),
                new Vector4f(warnatongkatbawah),
                new ArrayList<>(),
                "C:\\Java\\Git\\UAS\\Project-Grafkom-2023-UAS\\resources\\objects\\okky\\tongkatbawahtengah.obj"

        ));

        //21
        objects.add(new Objects(
                shader,
                new ArrayList<>(),
                new Vector4f(warnatongkatbawah),
                new ArrayList<>(),
                "C:\\Java\\Git\\UAS\\Project-Grafkom-2023-UAS\\resources\\objects\\okky\\tongkatbawahbelakang.obj"

        ));

        //22
        objects.add(new Objects(
                shader,
                new ArrayList<>(),
                new Vector4f(warnasetir),
                new ArrayList<>(),
                "C:\\Java\\Git\\UAS\\Project-Grafkom-2023-UAS\\resources\\objects\\okky\\pagarkanandepan.obj"

        ));

        //23
        objects.add(new Objects(
                shader,
                new ArrayList<>(),
                new Vector4f(warnasetir),
                new ArrayList<>(),
                "C:\\Java\\Git\\UAS\\Project-Grafkom-2023-UAS\\resources\\objects\\okky\\pagardepansetir.obj"

        ));

        //24
        objects.add(new Objects(
                shader,
                new ArrayList<>(),
                new Vector4f(warnasetir),
                new ArrayList<>(),
                "C:\\Java\\Git\\UAS\\Project-Grafkom-2023-UAS\\resources\\objects\\okky\\pagarbelakang.obj"

        ));

        //25
        objects.add(new Objects(
                shader,
                new ArrayList<>(),
                new Vector4f(warnasetir),
                new ArrayList<>(),
                "C:\\Java\\Git\\UAS\\Project-Grafkom-2023-UAS\\resources\\objects\\okky\\pagarkiribelakang.obj"

        ));

        //26
        objects.add(new Objects(
                shader,
                new ArrayList<>(),
                new Vector4f(warnasetir),
                new ArrayList<>(),
                "C:\\Java\\Git\\UAS\\Project-Grafkom-2023-UAS\\resources\\objects\\okky\\pagarkiribelakang2.obj"

        ));

        //27
        objects.add(new Objects(
                shader,
                new ArrayList<>(),
                new Vector4f(warnasetir),
                new ArrayList<>(),
                "C:\\Java\\Git\\UAS\\Project-Grafkom-2023-UAS\\resources\\objects\\okky\\pagarkiridepan.obj"

        ));

        //28
        objects.add(new Objects(
                shader,
                new ArrayList<>(),
                new Vector4f(warnasetir),
                new ArrayList<>(),
                "C:\\Java\\Git\\UAS\\Project-Grafkom-2023-UAS\\resources\\objects\\okky\\pagarkiri.obj"

        ));

        //29
        objects.add(new Objects(
                shader,
                new ArrayList<>(),
                new Vector4f(warnasetir),
                new ArrayList<>(),
                "C:\\Java\\Git\\UAS\\Project-Grafkom-2023-UAS\\resources\\objects\\okky\\pagarkanan.obj"

        ));

        //30
        objects.add(new Objects(
                shader,
                new ArrayList<>(),
                new Vector4f(warnasetir),
                new ArrayList<>(),
                "C:\\Java\\Git\\UAS\\Project-Grafkom-2023-UAS\\resources\\objects\\okky\\pagarkanandepan.obj"
        ));

        //31
        objects.add(new Objects(
                shader,
                new ArrayList<>(),
                new Vector4f(warnasetir),
                new ArrayList<>(),
                "C:\\Java\\Git\\UAS\\Project-Grafkom-2023-UAS\\resources\\objects\\okky\\pagarkiridepan.obj"
        ));

        //32
        objects.add(new Objects(
                shader,
                new ArrayList<>(),
                new Vector4f(warnasetir),
                new ArrayList<>(),
                "C:\\Java\\Git\\UAS\\Project-Grafkom-2023-UAS\\resources\\objects\\okky\\pagarsetir.obj"
        ));

        //33
        objects.add(new Objects(
                shader,
                new ArrayList<>(),
                new Vector4f(warnasetir),
                new ArrayList<>(),
                "C:\\Java\\Git\\UAS\\Project-Grafkom-2023-UAS\\resources\\objects\\okky\\pagardepansetir.obj"

        ));

        //34
        objects.add(new Objects(
                shader,
                new ArrayList<>(),
                new Vector4f(warnasetir),
                new ArrayList<>(),
                "C:\\Java\\Git\\UAS\\Project-Grafkom-2023-UAS\\resources\\objects\\okky\\pagarintai1.obj"

        ));

        //35
        objects.add(new Objects(
                shader,
                new ArrayList<>(),
                new Vector4f(warnasetir),
                new ArrayList<>(),
                "C:\\Java\\Git\\UAS\\Project-Grafkom-2023-UAS\\resources\\objects\\okky\\pagarintai2.obj"

        ));

        //36
        objects.add(new Objects(
                shader,
                new ArrayList<>(),
                new Vector4f(warnasetir),
                new ArrayList<>(),
                "C:\\Java\\Git\\UAS\\Project-Grafkom-2023-UAS\\resources\\objects\\okky\\gaganglayardepan1.obj"

        ));

        //37
        objects.add(new Objects(
                shader,
                new ArrayList<>(),
                new Vector4f(163/255f, 160/255f, 52/255f,1.0f),
                new ArrayList<>(),
                "C:\\Java\\Git\\UAS\\Project-Grafkom-2023-UAS\\resources\\objects\\okky\\gaganglayardepan2.obj"

        ));

        //38
        objects.add(new Objects(
                shader,
                new ArrayList<>(),
                new Vector4f(warnasetir),
                new ArrayList<>(),
                "C:\\Java\\Git\\UAS\\Project-Grafkom-2023-UAS\\resources\\objects\\okky\\gaganglayartengah1.obj"

        ));

        //39
        objects.add(new Objects(
                shader,
                new ArrayList<>(),
                new Vector4f(warnasetir),
                new ArrayList<>(),
                "C:\\Java\\Git\\UAS\\Project-Grafkom-2023-UAS\\resources\\objects\\okky\\gaganglayartengah2.obj"

        ));

        //40
        objects.add(new Objects(
                shader,
                new ArrayList<>(),
                new Vector4f(warnatangga),
                new ArrayList<>(),
                "C:\\Java\\Git\\UAS\\Project-Grafkom-2023-UAS\\resources\\objects\\okky\\tanggakanan.obj"

        ));

        //41
        objects.add(new Objects(
                shader,
                new ArrayList<>(),
                new Vector4f(warnatangga),
                new ArrayList<>(),
                "C:\\Java\\Git\\UAS\\Project-Grafkom-2023-UAS\\resources\\objects\\okky\\tanggakiri.obj"

        ));

        //42
        objects.add(new Objects(
                shader,
                new ArrayList<>(),
                new Vector4f(warnasetir),
                new ArrayList<>(),
                "C:\\Java\\Git\\UAS\\Project-Grafkom-2023-UAS\\resources\\objects\\okky\\setirdalam.obj"

        ));

        //43
        objects.add(new Objects(
                shader,
                new ArrayList<>(),
                new Vector4f(warnasetir),
                new ArrayList<>(),
                "C:\\Java\\Git\\UAS\\Project-Grafkom-2023-UAS\\resources\\objects\\okky\\setirdalam1.obj"

        ));

        //44
        objects.add(new Objects(
                shader,
                new ArrayList<>(),
                new Vector4f(warnasetir),
                new ArrayList<>(),
                "C:\\Java\\Git\\UAS\\Project-Grafkom-2023-UAS\\resources\\objects\\okky\\setirdalam2.obj"

        ));

        //45
        objects.add(new Objects(
                shader,
                new ArrayList<>(),
                new Vector4f(warnasetir),
                new ArrayList<>(),
                "C:\\Java\\Git\\UAS\\Project-Grafkom-2023-UAS\\resources\\objects\\okky\\setirdalam3.obj"
        ));

        //46
        objects.add(new Objects(
                shader,
                new ArrayList<>(),
                new Vector4f(warnasetir),
                new ArrayList<>(),
                "C:\\Java\\Git\\UAS\\Project-Grafkom-2023-UAS\\resources\\objects\\okky\\setirdalam4.obj"
        ));

        //47
        objects.add(new Objects(
                shader,
                new ArrayList<>(),
                new Vector4f(warnasetir),
                new ArrayList<>(),
                "C:\\Java\\Git\\UAS\\Project-Grafkom-2023-UAS\\resources\\objects\\okky\\setir.obj"
        ));

        //48
        objects.add(new Objects(
                shader,
                new ArrayList<>(),
                new Vector4f(warnasetir),
                new ArrayList<>(),
                "C:\\Java\\Git\\UAS\\Project-Grafkom-2023-UAS\\resources\\objects\\okky\\setir1.obj"
        ));

        //49
        objects.add(new Objects(
                shader,
                new ArrayList<>(),
                new Vector4f(warnasetir),
                new ArrayList<>(),
                "C:\\Java\\Git\\UAS\\Project-Grafkom-2023-UAS\\resources\\objects\\okky\\setir2.obj"
        ));

        //50
        objects.add(new Objects(
                shader,
                new ArrayList<>(),
                new Vector4f(warnasetir),
                new ArrayList<>(),
                "C:\\Java\\Git\\UAS\\Project-Grafkom-2023-UAS\\resources\\objects\\okky\\setir3.obj"
        ));


        objects.add(new Objects(
                shader,
                new ArrayList<>(),
                new Vector4f(warnasetir),
                new ArrayList<>(),
                "C:\\Java\\Git\\UAS\\Project-Grafkom-2023-UAS\\resources\\objects\\okky\\setir4.obj"
        ));

        objects.add(new Objects(
                shader,
                new ArrayList<>(),
                new Vector4f(warnasetir),
                new ArrayList<>(),
                "C:\\Java\\Git\\UAS\\Project-Grafkom-2023-UAS\\resources\\objects\\okky\\setir5.obj"
        ));

        objects.add(new Objects(
                shader,
                new ArrayList<>(),
                new Vector4f(warnasetir),
                new ArrayList<>(),
                "C:\\Java\\Git\\UAS\\Project-Grafkom-2023-UAS\\resources\\objects\\okky\\setir6.obj"
        ));

        objects.add(new Objects(
                shader,
                new ArrayList<>(),
                new Vector4f(warnasetir),
                new ArrayList<>(),
                "C:\\Java\\Git\\UAS\\Project-Grafkom-2023-UAS\\resources\\objects\\okky\\setir7.obj"
        ));

        objects.add(new Objects(
                shader,
                new ArrayList<>(),
                new Vector4f(warnasetir),
                new ArrayList<>(),
                "C:\\Java\\Git\\UAS\\Project-Grafkom-2023-UAS\\resources\\objects\\okky\\setir8.obj"
        ));

        objects.add(new Objects(
                shader,
                new ArrayList<>(),
                new Vector4f(warnasetir),
                new ArrayList<>(),
                "C:\\Java\\Git\\UAS\\Project-Grafkom-2023-UAS\\resources\\objects\\okky\\intaiatas.obj"
        ));


        objects.add(new Objects(
                shader,
                new ArrayList<>(),
                new Vector4f(warnasetir),
                new ArrayList<>(),
                "C:\\Java\\Git\\UAS\\Project-Grafkom-2023-UAS\\resources\\objects\\okky\\pagarintai3.obj"
        ));

        objects.add(new Objects(
                shader,
                new ArrayList<>(),
                new Vector4f(warnasetir),
                new ArrayList<>(),
                "C:\\Java\\Git\\UAS\\Project-Grafkom-2023-UAS\\resources\\objects\\okky\\pagarintai4.obj"
        ));

        objects.add(new Objects(
                shader,
                new ArrayList<>(),
                new Vector4f(warnasetir),
                new ArrayList<>(),
                "C:\\Java\\Git\\UAS\\Project-Grafkom-2023-UAS\\resources\\objects\\okky\\pagarintai5.obj"
        ));

        objects.add(new Objects(
                shader,
                new ArrayList<>(),
                new Vector4f(warnasetir),
                new ArrayList<>(),
                "C:\\Java\\Git\\UAS\\Project-Grafkom-2023-UAS\\resources\\objects\\okky\\pagarintai6.obj"
        ));

        objects.add(new Objects(
                shader,
                new ArrayList<>(),
                new Vector4f(warnasetir),
                new ArrayList<>(),
                "C:\\Java\\Git\\UAS\\Project-Grafkom-2023-UAS\\resources\\objects\\okky\\pagarintai7.obj"
        ));

        objects.add(new Objects(
                shader,
                new ArrayList<>(),
                new Vector4f(warnasetir),
                new ArrayList<>(),
                "C:\\Java\\Git\\UAS\\Project-Grafkom-2023-UAS\\resources\\objects\\okky\\pagarintai8.obj"
        ));

    }
    public void input(){
        float move = 0.01f;
        if (window.isKeyPressed(GLFW_KEY_LEFT_SHIFT)) {
            camera.moveForward(move);
        }
        if (window.isKeyPressed(GLFW_KEY_LEFT_CONTROL)) {
            camera.moveBackwards(move);
        }
        if (window.isKeyPressed(GLFW_KEY_A)) {
            camera.moveLeft(move);
        }
        if (window.isKeyPressed(GLFW_KEY_D)) {
            camera.moveRight(move);
        }
        if (window.isKeyPressed(GLFW_KEY_W)){
            camera.moveUp(move);
        }
        if (window.isKeyPressed(GLFW_KEY_S)){
            camera.moveDown(move);
        }

        if(mouseInput.isLeftButtonPressed()){
            Vector2f displayVec = window.getMouseInput().getDisplVec();
            camera.addRotation((float)Math.toRadians(displayVec.x * 0.1f),
                    (float)Math.toRadians(displayVec.y * 0.1f));
        }
        if(mouseInput.isRightButtonPressed()){
            System.out.println(window.getMouseInput().getCurrentPos());
        }
        if(window.getMouseInput().getScroll().y != 0){
            projection.setFOV(projection.getFOV()- (window.getMouseInput().getScroll().y*0.01f));
            window.getMouseInput().setScroll(new Vector2f());
        }
        if (window.isKeyPressed(GLFW_KEY_RIGHT)) {
            for(Objects i: objects){
                i.rotateObject((float)Math.toRadians(0.1),0.0f,-1f,0.0f);
            }
            camera.rotateTowardsPoint(0.0f,(float)Math.toRadians(-0.1),0,0,0);
            belok=belok+0.1f;
            lurus=lurus+0.1f;
            posx=camera.getPosition().x();
            posy=camera.getPosition().y();
            posz=camera.getPosition().z();
            rotx=camera.getRotation().x();
            roty=camera.getRotation().y();
        }
        if (window.isKeyPressed(GLFW_KEY_LEFT)) {
            for(Objects i: objects){
                i.rotateObject((float)Math.toRadians(0.1),0.0f,1f,0.0f);
            }
            camera.rotateTowardsPoint(0.0f,(float)Math.toRadians(0.1),0,0,0);
            belok=belok-0.1f;
            lurus=lurus-0.1f;
            posx=camera.getPosition().x();
            posy=camera.getPosition().y();
            posz=camera.getPosition().z();
            rotx=camera.getRotation().x();
            roty=camera.getRotation().y();
        }
        if (window.isKeyPressed(GLFW_KEY_UP)) {

            for (Objects i : objects) {
                i.translateObject((float)Math.sin(Math.toRadians(lurus))*-0.01f, 0.0f, (float)Math.cos(Math.toRadians(lurus))*0.01f);
            }
            camera.moveForward(move);
            posx=camera.getPosition().x();
            posy=camera.getPosition().y();
            posz=camera.getPosition().z();
            rotx=camera.getRotation().x();
            roty=camera.getRotation().y();
        }


        if (window.isKeyPressed(GLFW_KEY_DOWN)) {
            for(Objects i: objects){
                i.translateObject((float)Math.sin(Math.toRadians(lurus))*0.01f,0.0f,-(float)Math.cos(Math.toRadians(lurus))*0.01f);
            }
            camera.moveBackwards(move);
            posx=camera.getPosition().x();
            posy=camera.getPosition().y();
            posz=camera.getPosition().z();
            rotx=camera.getRotation().x();
            roty=camera.getRotation().y();
        }

        if (window.isKeyPressed(GLFW_KEY_X)) {
            camera.setPosition(posx,posy,posz);
            camera.setRotation(rotx,roty);
        }



    }
    public void loop(){
        while (window.isOpen()) {
            window.update();
            glClearColor(0.0f,
                    0.0f, 0.0f,
                    0.0f);
            GL.createCapabilities();
            input();

            //code
            for(Objects object: objects){
                object.draw(camera,projection);
            }
//            for(Objects object: objectsRectangle){
//                object.draw();
//            }
//            for(Objects object: objectsPointsControl){
//                object.drawLine();
//            }

            // Restore state
            glDisableVertexAttribArray(0);

            skybox.draw(camera,projection);
            // Poll for window events.
            // The key callback above will only be
            // invoked during this call.
            glfwPollEvents();
        }
    }
    public void run() {

        init();
        loop();

        // Terminate GLFW and
        // free the error callback
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }
    public static void main(String[] args) {
        new Okky().run();
    }
}
