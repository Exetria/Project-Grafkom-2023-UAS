package Engine;

import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

public class SkyBoxRenderer
{
    private static final float SIZE = 500f;
    private static final float [] VERTICES =
    {
        -SIZE,  SIZE, -SIZE,
        -SIZE, -SIZE, -SIZE,
        SIZE, -SIZE, -SIZE,
        SIZE, -SIZE, -SIZE,
        SIZE,  SIZE, -SIZE,
        -SIZE,  SIZE, -SIZE,

        -SIZE, -SIZE,  SIZE,
        -SIZE, -SIZE, -SIZE,
        -SIZE,  SIZE, -SIZE,
        -SIZE,  SIZE, -SIZE,
        -SIZE,  SIZE,  SIZE,
        -SIZE, -SIZE,  SIZE,

        SIZE, -SIZE, -SIZE,
        SIZE, -SIZE,  SIZE,
        SIZE,  SIZE,  SIZE,
        SIZE,  SIZE,  SIZE,
        SIZE,  SIZE, -SIZE,
        SIZE, -SIZE, -SIZE,

        -SIZE, -SIZE,  SIZE,
        -SIZE,  SIZE,  SIZE,
        SIZE,  SIZE,  SIZE,
        SIZE,  SIZE,  SIZE,
        SIZE, -SIZE,  SIZE,
        -SIZE, -SIZE,  SIZE,

        -SIZE,  SIZE, -SIZE,
        SIZE,  SIZE, -SIZE,
        SIZE,  SIZE,  SIZE,
        SIZE,  SIZE,  SIZE,
        -SIZE,  SIZE,  SIZE,
        -SIZE,  SIZE, -SIZE,

        -SIZE, -SIZE, -SIZE,
        -SIZE, -SIZE,  SIZE,
        SIZE, -SIZE, -SIZE,
        SIZE, -SIZE, -SIZE,
        -SIZE, -SIZE,  SIZE,
        SIZE, -SIZE,  SIZE
    };

    private static String[] TEXTURE_FILE_NAMES = {"resources/skybox/right.jpg", "resources/skybox/left.jpg", "resources/skybox/top.jpg",
            "resources/skybox/bottom.jpg", "resources/skybox/back.jpg", "resources/skybox/front.jpg"};
//    private RawModel cube;
    private int texture;
    private SkyBoxShader shader;

    public SkyBoxRenderer(Matrix4f projMatrix)
    {
//        cube = loader.loadToVAO(vertices, 3);
        texture = Utils.loadCubeMap(TEXTURE_FILE_NAMES);
        shader = new SkyBoxShader();
        shader.createProjectionUniform(projMatrix);
    }

    public void render(Camera camera)
    {
//        shader.start()
        shader.createViewUniform(camera.getViewMatrix());
//        GL30.glBindVertexArray(cube.getVAOID());
        GL20.glEnableVertexAttribArray(0);
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL13.GL_TEXTURE_CUBE_MAP, texture);
//        GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, cube.getVertexCount());
        GL20.glDisableVertexAttribArray(0);
        GL30.glBindVertexArray(0);
//        shader.stop()
    }

}
