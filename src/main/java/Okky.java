import Engine.*;
import org.joml.Vector2f;
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
                    (800,800,"Hello World");
    private ArrayList<Objects> objects
            = new ArrayList<>();
    private ArrayList<Objects> objectsRectangle
            = new ArrayList<>();

    private ArrayList<Objects> objectsPointsControl
            = new ArrayList<>();

    private MouseInput mouseInput;
    int countDegree = 0;
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

        objects.add(new Objects(
                shader,
                new ArrayList<>(),
                new Vector4f(warnakapal),
                new ArrayList<>(),
                "C:\\Java\\Git\\UAS\\Project-Grafkom-2023-UAS\\resources\\Aset\\okky\\badan.obj"

        ));
        objects.add(new Objects(
                shader,
                new ArrayList<>(),
                new Vector4f(warnakapal),
                new ArrayList<>(),
                "C:\\Java\\Git\\UAS\\Project-Grafkom-2023-UAS\\resources\\Aset\\okky\\intaibawah.obj"

        ));

        objects.add(new Objects(
                shader,
                new ArrayList<>(),
                new Vector4f(warnalayar),
                new ArrayList<>(),
                "C:\\Java\\Git\\UAS\\Project-Grafkom-2023-UAS\\resources\\Aset\\okky\\layardepan.obj"

        ));
        objects.add(new Objects(
                shader,
                new ArrayList<>(),
                new Vector4f(warnalayar),
                new ArrayList<>(),
                "C:\\Java\\Git\\UAS\\Project-Grafkom-2023-UAS\\resources\\Aset\\okky\\layarbelakang.obj"

        ));

        objects.add(new Objects(
                shader,
                new ArrayList<>(),
                new Vector4f(warnalayar),
                new ArrayList<>(),
                "C:\\Java\\Git\\UAS\\Project-Grafkom-2023-UAS\\resources\\Aset\\okky\\mainsail.obj"

        ));

        objects.add(new Objects(
                shader,
                new ArrayList<>(),
                new Vector4f(warnamoncong),
                new ArrayList<>(),
                "C:\\Java\\Git\\UAS\\Project-Grafkom-2023-UAS\\resources\\Aset\\okky\\meriamkanan.obj"

        ));


        objects.add(new Objects(
                shader,
                new ArrayList<>(),
                new Vector4f(warnamoncong),
                new ArrayList<>(),
                "C:\\Java\\Git\\UAS\\Project-Grafkom-2023-UAS\\resources\\Aset\\okky\\meriamkiri.obj"

        ));
        objects.add(new Objects(
                shader,
                new ArrayList<>(),
                new Vector4f(warnamoncong),
                new ArrayList<>(),
                "C:\\Java\\Git\\UAS\\Project-Grafkom-2023-UAS\\resources\\Aset\\okky\\moncong.obj"

        ));
        objects.add(new Objects(
                shader,
                new ArrayList<>(),
                new Vector4f(warnasetir),
                new ArrayList<>(),
                "C:\\Java\\Git\\UAS\\Project-Grafkom-2023-UAS\\resources\\Aset\\okky\\standsetir.obj"

        ));

        objects.add(new Objects(
                shader,
                new ArrayList<>(),
                new Vector4f(warnasetir),
                new ArrayList<>(),
                "C:\\Java\\Git\\UAS\\Project-Grafkom-2023-UAS\\resources\\Aset\\okky\\gaganglayarbelakang1.obj"

        ));

        objects.add(new Objects(
                shader,
                new ArrayList<>(),
                new Vector4f(warnasetir),
                new ArrayList<>(),
                "C:\\Java\\Git\\UAS\\Project-Grafkom-2023-UAS\\resources\\Aset\\okky\\gaganglayarbelakang2.obj"

        ));

        objects.add(new Objects(
                shader,
                new ArrayList<>(),
                new Vector4f(warnasetir),
                new ArrayList<>(),
                "C:\\Java\\Git\\UAS\\Project-Grafkom-2023-UAS\\resources\\Aset\\okky\\gaganglayardepan1.obj"

        ));

        objects.add(new Objects(
                shader,
                new ArrayList<>(),
                new Vector4f(warnasetir),
                new ArrayList<>(),
                "C:\\Java\\Git\\UAS\\Project-Grafkom-2023-UAS\\resources\\Aset\\okky\\gaganglayardepan2.obj"

        ));

        objects.add(new Objects(
                shader,
                new ArrayList<>(),
                new Vector4f(warnasetir),
                new ArrayList<>(),
                "C:\\Java\\Git\\UAS\\Project-Grafkom-2023-UAS\\resources\\Aset\\okky\\gaganglayartengah1.obj"

        ));

        objects.add(new Objects(
                shader,
                new ArrayList<>(),
                new Vector4f(warnasetir),
                new ArrayList<>(),
                "C:\\Java\\Git\\UAS\\Project-Grafkom-2023-UAS\\resources\\Aset\\okky\\gaganglayartengah2.obj"

        ));

        objects.add(new Objects(
                shader,
                new ArrayList<>(),
                new Vector4f(warnatongkat),
                new ArrayList<>(),
                "C:\\Java\\Git\\UAS\\Project-Grafkom-2023-UAS\\resources\\Aset\\okky\\tongkatdepan.obj"
        ));

        objects.add(new Objects(
                shader,
                new ArrayList<>(),
                new Vector4f(warnatongkat),
                new ArrayList<>(),
                "C:\\Java\\Git\\UAS\\Project-Grafkom-2023-UAS\\resources\\Aset\\okky\\tongkattengah.obj"

        ));


        objects.add(new Objects(
                shader,
                new ArrayList<>(),
                new Vector4f(warnatongkat),
                new ArrayList<>(),
                "C:\\Java\\Git\\UAS\\Project-Grafkom-2023-UAS\\resources\\Aset\\okky\\tongkatbelakang.obj"

        ));

        objects.add(new Objects(
                shader,
                new ArrayList<>(),
                new Vector4f(warnatongkatbawah),
                new ArrayList<>(),
                "C:\\Java\\Git\\UAS\\Project-Grafkom-2023-UAS\\resources\\Aset\\okky\\tongkatbawahdepan.obj"

        ));

        objects.add(new Objects(
                shader,
                new ArrayList<>(),
                new Vector4f(warnatongkatbawah),
                new ArrayList<>(),
                "C:\\Java\\Git\\UAS\\Project-Grafkom-2023-UAS\\resources\\Aset\\okky\\tongkatbawahtengah.obj"

        ));

        objects.add(new Objects(
                shader,
                new ArrayList<>(),
                new Vector4f(warnatongkatbawah),
                new ArrayList<>(),
                "C:\\Java\\Git\\UAS\\Project-Grafkom-2023-UAS\\resources\\Aset\\okky\\tongkatbawahbelakang.obj"

        ));

        objects.add(new Objects(
                shader,
                new ArrayList<>(),
                new Vector4f(warnasetir),
                new ArrayList<>(),
                "C:\\Java\\Git\\UAS\\Project-Grafkom-2023-UAS\\resources\\Aset\\okky\\pagarkanandepan.obj"

        ));

        objects.add(new Objects(
                shader,
                new ArrayList<>(),
                new Vector4f(warnasetir),
                new ArrayList<>(),
                "C:\\Java\\Git\\UAS\\Project-Grafkom-2023-UAS\\resources\\Aset\\okky\\pagardepansetir.obj"

        ));

        objects.add(new Objects(
                shader,
                new ArrayList<>(),
                new Vector4f(warnasetir),
                new ArrayList<>(),
                "C:\\Java\\Git\\UAS\\Project-Grafkom-2023-UAS\\resources\\Aset\\okky\\pagarbelakang.obj"

        ));

        objects.add(new Objects(
                shader,
                new ArrayList<>(),
                new Vector4f(warnasetir),
                new ArrayList<>(),
                "C:\\Java\\Git\\UAS\\Project-Grafkom-2023-UAS\\resources\\Aset\\okky\\pagarkiribelakang.obj"

        ));

        objects.add(new Objects(
                shader,
                new ArrayList<>(),
                new Vector4f(warnasetir),
                new ArrayList<>(),
                "C:\\Java\\Git\\UAS\\Project-Grafkom-2023-UAS\\resources\\Aset\\okky\\pagarkiribelakang2.obj"

        ));


        objects.add(new Objects(
                shader,
                new ArrayList<>(),
                new Vector4f(warnasetir),
                new ArrayList<>(),
                "C:\\Java\\Git\\UAS\\Project-Grafkom-2023-UAS\\resources\\Aset\\okky\\pagarkiridepan.obj"

        ));

        objects.add(new Objects(
                shader,
                new ArrayList<>(),
                new Vector4f(warnasetir),
                new ArrayList<>(),
                "C:\\Java\\Git\\UAS\\Project-Grafkom-2023-UAS\\resources\\Aset\\okky\\pagarkiri.obj"

        ));


        objects.add(new Objects(
                shader,
                new ArrayList<>(),
                new Vector4f(warnasetir),
                new ArrayList<>(),
                "C:\\Java\\Git\\UAS\\Project-Grafkom-2023-UAS\\resources\\Aset\\okky\\pagarkanan.obj"

        ));

        objects.add(new Objects(
                shader,
                new ArrayList<>(),
                new Vector4f(warnasetir),
                new ArrayList<>(),
                "C:\\Java\\Git\\UAS\\Project-Grafkom-2023-UAS\\resources\\Aset\\okky\\pagarkanandepan.obj"
        ));

        objects.add(new Objects(
                shader,
                new ArrayList<>(),
                new Vector4f(warnasetir),
                new ArrayList<>(),
                "C:\\Java\\Git\\UAS\\Project-Grafkom-2023-UAS\\resources\\Aset\\okky\\pagarkiridepan.obj"
        ));

        objects.add(new Objects(
                shader,
                new ArrayList<>(),
                new Vector4f(warnasetir),
                new ArrayList<>(),
                "C:\\Java\\Git\\UAS\\Project-Grafkom-2023-UAS\\resources\\Aset\\okky\\pagarsetir.obj"
        ));

        objects.add(new Objects(
                shader,
                new ArrayList<>(),
                new Vector4f(warnasetir),
                new ArrayList<>(),
                "C:\\Java\\Git\\UAS\\Project-Grafkom-2023-UAS\\resources\\Aset\\okky\\pagardepansetir.obj"

        ));

        objects.add(new Objects(
                shader,
                new ArrayList<>(),
                new Vector4f(warnasetir),
                new ArrayList<>(),
                "C:\\Java\\Git\\UAS\\Project-Grafkom-2023-UAS\\resources\\Aset\\okky\\pagarintai1.obj"

        ));

        objects.add(new Objects(
                shader,
                new ArrayList<>(),
                new Vector4f(warnasetir),
                new ArrayList<>(),
                "C:\\Java\\Git\\UAS\\Project-Grafkom-2023-UAS\\resources\\Aset\\okky\\pagarintai2.obj"

        ));

        objects.add(new Objects(
                shader,
                new ArrayList<>(),
                new Vector4f(warnasetir),
                new ArrayList<>(),
                "C:\\Java\\Git\\UAS\\Project-Grafkom-2023-UAS\\resources\\Aset\\okky\\gaganglayardepan1.obj"

        ));

        objects.add(new Objects(
                shader,
                new ArrayList<>(),
                new Vector4f(163/255f, 160/255f, 52/255f,1.0f),
                new ArrayList<>(),
                "C:\\Java\\Git\\UAS\\Project-Grafkom-2023-UAS\\resources\\Aset\\okky\\gaganglayardepan2.obj"

        ));

        objects.add(new Objects(
                shader,
                new ArrayList<>(),
                new Vector4f(warnasetir),
                new ArrayList<>(),
                "C:\\Java\\Git\\UAS\\Project-Grafkom-2023-UAS\\resources\\Aset\\okky\\gaganglayartengah1.obj"

        ));


        objects.add(new Objects(
                shader,
                new ArrayList<>(),
                new Vector4f(warnasetir),
                new ArrayList<>(),
                "C:\\Java\\Git\\UAS\\Project-Grafkom-2023-UAS\\resources\\Aset\\okky\\gaganglayartengah2.obj"

        ));


        objects.add(new Objects(
                shader,
                new ArrayList<>(),
                new Vector4f(warnasetir),
                new ArrayList<>(),
                "C:\\Java\\Git\\UAS\\Project-Grafkom-2023-UAS\\resources\\Aset\\okky\\gaganglayarbelakang1.obj"

        ));

        objects.add(new Objects(
                shader,
                new ArrayList<>(),
                new Vector4f(warnasetir),
                new ArrayList<>(),
                "C:\\Java\\Git\\UAS\\Project-Grafkom-2023-UAS\\resources\\Aset\\okky\\gaganglayarbelakang2.obj"

        ));


        objects.add(new Objects(
                shader,
                new ArrayList<>(),
                new Vector4f(warnatangga),
                new ArrayList<>(),
                "C:\\Java\\Git\\UAS\\Project-Grafkom-2023-UAS\\resources\\Aset\\okky\\tanggakanan.obj"

        ));

        objects.add(new Objects(
                shader,
                new ArrayList<>(),
                new Vector4f(warnatangga),
                new ArrayList<>(),
                "C:\\Java\\Git\\UAS\\Project-Grafkom-2023-UAS\\resources\\Aset\\okky\\tanggakiri.obj"

        ));


        objects.add(new Objects(
                shader,
                new ArrayList<>(),
                new Vector4f(warnasetir),
                new ArrayList<>(),
                "C:\\Java\\Git\\UAS\\Project-Grafkom-2023-UAS\\resources\\Aset\\okky\\setirdalam.obj"

        ));

        objects.add(new Objects(
                shader,
                new ArrayList<>(),
                new Vector4f(warnasetir),
                new ArrayList<>(),
                "C:\\Java\\Git\\UAS\\Project-Grafkom-2023-UAS\\resources\\Aset\\okky\\setirdalam1.obj"

        ));


        objects.add(new Objects(
                shader,
                new ArrayList<>(),
                new Vector4f(warnasetir),
                new ArrayList<>(),
                "C:\\Java\\Git\\UAS\\Project-Grafkom-2023-UAS\\resources\\Aset\\okky\\setirdalam2.obj"

        ));


        objects.add(new Objects(
                shader,
                new ArrayList<>(),
                new Vector4f(warnasetir),
                new ArrayList<>(),
                "C:\\Java\\Git\\UAS\\Project-Grafkom-2023-UAS\\resources\\Aset\\okky\\setirdalam3.obj"
        ));

        // Kitchen Set
        objects.add(new Objects(
                shader,
                new ArrayList<>(),
                new Vector4f(warnasetir),
                new ArrayList<>(),
                "C:\\Java\\Git\\UAS\\Project-Grafkom-2023-UAS\\resources\\Aset\\okky\\setirdalam4.obj"
        ));

        objects.add(new Objects(
                shader,
                new ArrayList<>(),
                new Vector4f(warnasetir),
                new ArrayList<>(),
                "C:\\Java\\Git\\UAS\\Project-Grafkom-2023-UAS\\resources\\Aset\\okky\\setir.obj"
        ));

        objects.add(new Objects(
                shader,
                new ArrayList<>(),
                new Vector4f(warnasetir),
                new ArrayList<>(),
                "C:\\Java\\Git\\UAS\\Project-Grafkom-2023-UAS\\resources\\Aset\\okky\\setir1.obj"
        ));

        objects.add(new Objects(
                shader,
                new ArrayList<>(),
                new Vector4f(warnasetir),
                new ArrayList<>(),
                "C:\\Java\\Git\\UAS\\Project-Grafkom-2023-UAS\\resources\\Aset\\okky\\setir2.obj"
        ));


        objects.add(new Objects(
                shader,
                new ArrayList<>(),
                new Vector4f(warnasetir),
                new ArrayList<>(),
                "C:\\Java\\Git\\UAS\\Project-Grafkom-2023-UAS\\resources\\Aset\\okky\\setir3.obj"
        ));


        objects.add(new Objects(
                shader,
                new ArrayList<>(),
                new Vector4f(warnasetir),
                new ArrayList<>(),
                "C:\\Java\\Git\\UAS\\Project-Grafkom-2023-UAS\\resources\\Aset\\okky\\setir4.obj"
        ));

        objects.add(new Objects(
                shader,
                new ArrayList<>(),
                new Vector4f(warnasetir),
                new ArrayList<>(),
                "C:\\Java\\Git\\UAS\\Project-Grafkom-2023-UAS\\resources\\Aset\\okky\\setir5.obj"
        ));

        objects.add(new Objects(
                shader,
                new ArrayList<>(),
                new Vector4f(warnasetir),
                new ArrayList<>(),
                "C:\\Java\\Git\\UAS\\Project-Grafkom-2023-UAS\\resources\\Aset\\okky\\setir6.obj"
        ));

        objects.add(new Objects(
                shader,
                new ArrayList<>(),
                new Vector4f(warnasetir),
                new ArrayList<>(),
                "C:\\Java\\Git\\UAS\\Project-Grafkom-2023-UAS\\resources\\Aset\\okky\\setir7.obj"
        ));

        objects.add(new Objects(
                shader,
                new ArrayList<>(),
                new Vector4f(warnasetir),
                new ArrayList<>(),
                "C:\\Java\\Git\\UAS\\Project-Grafkom-2023-UAS\\resources\\Aset\\okky\\setir8.obj"
        ));

        objects.add(new Objects(
                shader,
                new ArrayList<>(),
                new Vector4f(warnasetir),
                new ArrayList<>(),
                "C:\\Java\\Git\\UAS\\Project-Grafkom-2023-UAS\\resources\\Aset\\okky\\intaiatas.obj"
        ));


        objects.add(new Objects(
                shader,
                new ArrayList<>(),
                new Vector4f(warnasetir),
                new ArrayList<>(),
                "C:\\Java\\Git\\UAS\\Project-Grafkom-2023-UAS\\resources\\Aset\\okky\\pagarintai3.obj"
        ));

        objects.add(new Objects(
                shader,
                new ArrayList<>(),
                new Vector4f(warnasetir),
                new ArrayList<>(),
                "C:\\Java\\Git\\UAS\\Project-Grafkom-2023-UAS\\resources\\Aset\\okky\\pagarintai4.obj"
        ));

        objects.add(new Objects(
                shader,
                new ArrayList<>(),
                new Vector4f(warnasetir),
                new ArrayList<>(),
                "C:\\Java\\Git\\UAS\\Project-Grafkom-2023-UAS\\resources\\Aset\\okky\\pagarintai5.obj"
        ));

        objects.add(new Objects(
                shader,
                new ArrayList<>(),
                new Vector4f(warnasetir),
                new ArrayList<>(),
                "C:\\Java\\Git\\UAS\\Project-Grafkom-2023-UAS\\resources\\Aset\\okky\\pagarintai6.obj"
        ));

        objects.add(new Objects(
                shader,
                new ArrayList<>(),
                new Vector4f(warnasetir),
                new ArrayList<>(),
                "C:\\Java\\Git\\UAS\\Project-Grafkom-2023-UAS\\resources\\Aset\\okky\\pagarintai7.obj"
        ));

        objects.add(new Objects(
                shader,
                new ArrayList<>(),
                new Vector4f(warnasetir),
                new ArrayList<>(),
                "C:\\Java\\Git\\UAS\\Project-Grafkom-2023-UAS\\resources\\Aset\\okky\\pagarintai8.obj"
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
        if (window.isKeyPressed(GLFW_KEY_1)) {
            objects.get(0).translateObject(0.0f,1.0f,0.0f);

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
