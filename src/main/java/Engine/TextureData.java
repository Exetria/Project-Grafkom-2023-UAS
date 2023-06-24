package Engine;

import java.nio.ByteBuffer;

public class TextureData
{
    private int width, height;
    private ByteBuffer data;

    public TextureData(ByteBuffer data, int width, int height) {
        this.width = width;
        this.height = height;
        this.data = data;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public ByteBuffer getBuffer() {
        return data;
    }
}
