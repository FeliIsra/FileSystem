public class OpenFile {

    private int fileDescriptor;

    public OpenFile(int fileDescriptor){
        this.fileDescriptor = fileDescriptor;
    }

    public int getFileDescriptor() {
        return fileDescriptor;
    }

    public void setFileDescriptor(int fileDescriptor) {
        this.fileDescriptor = fileDescriptor;
    }

}
