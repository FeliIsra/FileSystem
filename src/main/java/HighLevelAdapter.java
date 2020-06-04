import java.util.function.Consumer;

public class HighLevelAdapter{

    private  static LowLevelFileSystem lowLevelFileSystem;

    public HighLevelAdapter(LowLevelFileSystem lowLevelFileSystem){
        this.lowLevelFileSystem = lowLevelFileSystem;
    }

    public OpenFile openFile(String path){
        int fileDescriptor = lowLevelFileSystem.openFile(path);
        return new OpenFile(fileDescriptor, lowLevelFileSystem);
    }
}
