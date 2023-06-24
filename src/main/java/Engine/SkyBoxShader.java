package Engine;

import org.joml.Matrix4f;

import java.util.Arrays;

import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;

public class SkyBoxShader extends ShaderProgram
{
    UniformsMap uniformsMap;

    public SkyBoxShader()
    {
        super(Arrays.asList(new ShaderProgram.ShaderModuleData("resources/shaders/skybox.vert",
                GL_VERTEX_SHADER), new ShaderProgram.ShaderModuleData("resources/shaders/skybox.frag", GL_FRAGMENT_SHADER)));
        uniformsMap = new UniformsMap(getProgramId());
//        createUniforms(projMatrix, viewMatrix);
        super.bind();
    }

    public void createViewUniform(Matrix4f viewMatrix)
    {
        uniformsMap.setUniform("viewMatrix",viewMatrix);
    }

    public void createProjectionUniform(Matrix4f projMatrix)
    {
        uniformsMap.setUniform("projectionMatrix",projMatrix);
    }

    public void createUniforms(Matrix4f projMatrix, Matrix4f viewMatrix)
    {
        uniformsMap.setUniform("projectionMatrix",projMatrix);
        uniformsMap.setUniform("viewMatrix",viewMatrix);
    }
}
