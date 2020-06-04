import java.util.function.Consumer;

public class OpenFile {

    private int fileDescriptor;
    private LowLevelFileSystem lowLevelFileSystem;

    public OpenFile(int fileDescriptor, LowLevelFileSystem lowLevelFileSystem) {
        this.fileDescriptor = fileDescriptor;
        this.lowLevelFileSystem = lowLevelFileSystem;
    }

    public int getFileDescriptor() {
        return fileDescriptor;
    }

    public void setFileDescriptor(int fileDescriptor) {
        this.fileDescriptor = fileDescriptor;
    }

    public void close(){
        lowLevelFileSystem.closeFile(fileDescriptor);
    }

    public void syncRead(Buffer buffer){
        int readBytes = this.lowLevelFileSystem.syncReadFile(
                this.fileDescriptor,
                buffer.getBytes(),
                buffer.getStart(),
                buffer.getEnd()
        );

        buffer.limit(readBytes);
    }

    public void readAsync(Consumer<Buffer> callback){
        Buffer buffer = new Buffer(100);
        this.lowLevelFileSystem.asyncReadFile(
                this.fileDescriptor,
                buffer.getBytes(),
                buffer.getStart(),
                buffer.getEnd(),
                readBytes -> {
                    buffer.limit(readBytes);
                    callback.accept(buffer);
                }
        );
    }

    public void syncWriteFile(Buffer buffer){
        this.lowLevelFileSystem.syncWriteFile(
                this.fileDescriptor,
                buffer.getBytes(),
                buffer.getStart(),
                buffer.getEnd()
        );
    }
}
