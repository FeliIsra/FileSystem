import java.util.function.Consumer;

public class HighLevelAdapter {

    private LowLevelFileSystem lowLevelFileSystem;

    public HighLevelAdapter(LowLevelFileSystem lowLevelFileSystem){
        this.lowLevelFileSystem = lowLevelFileSystem;
    }

    public OpenFile openFile(String path){
        int fileDescriptor = this.lowLevelFileSystem.openFile(path);
        return new OpenFile(fileDescriptor);
    }

    public void closeFile(int fileDescriptor){
        lowLevelFileSystem.closeFile(fileDescriptor);
    }

    public void syncRead(Buffer buffer, OpenFile file){
        int readBytes = this.lowLevelFileSystem.syncReadFile(
                file.getFileDescriptor(),
                buffer.getBytes(),
                buffer.getStart(),
                buffer.getEnd()
        );

        buffer.limit(readBytes);
    }

    public void readAsync(Consumer<Buffer> callback, OpenFile file) {
        Buffer buffer = new Buffer(100);
        this.lowLevelFileSystem.asyncReadFile(
                file.getFileDescriptor(),
                buffer.getBytes(),
                buffer.getStart(),
                buffer.getEnd(),
                readBytes -> {
                    buffer.limit(readBytes);
                    callback.accept(buffer);
                }
        );
    }

    public void syncWriteFile(Buffer buffer, OpenFile file){
        this.lowLevelFileSystem.syncWriteFile(
                file.getFileDescriptor(),
                buffer.getBytes(),
                buffer.getStart(),
                buffer.getEnd()
        );
    }
}
