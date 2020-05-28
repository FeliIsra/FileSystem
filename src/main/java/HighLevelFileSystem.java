import java.util.function.Consumer;

public interface HighLevelFileSystem {
    public OpenFile openFile(String path);

    public void closeFile(int fileDescriptor);
    public void syncRead(Buffer buffer, OpenFile file);
    public void readAsync(Consumer<Buffer> callback, OpenFile file);
    public void syncWriteFile(Buffer buffer, OpenFile file);

}
