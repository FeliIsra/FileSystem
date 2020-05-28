public class OpenFile {

    private int fileDescriptor;
    private LowLevelFileSystem lowLevelFileSystem;

    public OpenFile(int fileDescriptor, LowLevelFileSystem lowLevelFileSystem){
        this.fileDescriptor = fileDescriptor;
        this.lowLevelFileSystem = lowLevelFileSystem;
    }

    public int getFileDescriptor() {
        return fileDescriptor;
    }

    public void setFileDescriptor(int fileDescriptor) {
        this.fileDescriptor = fileDescriptor;
    }

}
