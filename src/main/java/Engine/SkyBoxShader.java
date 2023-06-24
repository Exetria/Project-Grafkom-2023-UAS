package Engine;

import org.joml.Matrix4f;

import java.util.Arrays;

import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;

public class SkyBoxShader extends ShaderProgram
{
    private static final String VERTEX_FILE = "src/skybox/skyboxVertexShader.txt";
    private static final String FRAGMENT_FILE = "src/skybox/skyboxFragmentShader.txt";

    private int location_projectionMatrix;
    private int location_viewMatrix;

    UniformsMap uniformsMap;

    public SkyBoxShader(Matrix4f projMatrix, Matrix4f viewMatrix)
    {
        super(Arrays.asList(new ShaderProgram.ShaderModuleData("resources/shaders/skybox.vert",
                GL_VERTEX_SHADER), new ShaderProgram.ShaderModuleData("resources/shaders/skybox.frag", GL_FRAGMENT_SHADER)));
        uniformsMap = new UniformsMap(getProgramId());
        createUniforms(projMatrix, viewMatrix);
        super.bind();
    }

    public void createUniforms(Matrix4f projMatrix, Matrix4f viewMatrix)
    {
        uniformsMap.setUniform("projectionMatrix",projMatrix);
        uniformsMap.setUniform("viewMatrix",viewMatrix);
    }
}
