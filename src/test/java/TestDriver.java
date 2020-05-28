import org.junit.*;

import java.util.function.Consumer;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

public class TestDriver {

    private LowLevelFileSystem lowLevelFileSystem;
    private HighLevelAdapter highLevelFileSystem;
    private OpenFile aFile;

    @Before
    public void setup() {
        lowLevelFileSystem = mock(LowLevelFileSystem.class);
        highLevelFileSystem = new HighLevelAdapter(lowLevelFileSystem);
    }

    @After
    public void tearDown() {
    }

    // TODO tests para abrir un archivo
    @Test
    public void sePuedeAbrirUnArchivo() {
        highLevelFileSystem.openFile("unaRuta");
        verify(lowLevelFileSystem).openFile("unaRuta");
    }

    @Test
    public void alAbrirUnArchivoMeDevuelveUnArchivoConElFD() {
        when(lowLevelFileSystem.openFile("unaRuta")).thenReturn(100);
        OpenFile file = highLevelFileSystem.openFile("unaRuta");
        Assert.assertEquals(file.getFileDescriptor(), 100);
    }

    @Test
    public void sePuedeCerrarUnArchivo() {
        when(lowLevelFileSystem.openFile("unaRuta")).thenReturn(100);
        aFile = highLevelFileSystem.openFile("unaRuta");
        highLevelFileSystem.closeFile(aFile.getFileDescriptor());
        verify(lowLevelFileSystem).closeFile(anyInt());
    }


    @Ignore
    public void sePuedeLeerUnArchivoSincronicamente() {
        when(lowLevelFileSystem.openFile("unaRuta")).thenReturn(100);

        aFile = highLevelFileSystem.openFile("unaRuta");
        Buffer buffer = new Buffer(10);
        highLevelFileSystem.readAsync((Consumer<Buffer>) buffer, aFile);
        verify(lowLevelFileSystem)
                .syncReadFile(eq(aFile.getFileDescriptor()),
                        same(buffer.getBytes()), eq(buffer.getStart()),
                        eq(buffer.getEnd()));
    }

    @Test
    public void cambiarInicioDeBuffer() {
        Buffer buffer = new Buffer(10);
        buffer.limit(5);
        Assert.assertEquals(buffer.getEnd(), 5);
    }

    @Test
    public void sePuedeLeerAsincronicamente() {
        when(lowLevelFileSystem.openFile("unaRuta")).thenReturn(100);
        aFile = highLevelFileSystem.openFile("unaRuta");
        highLevelFileSystem.readAsync((buffer) -> {

        }, aFile);
        verify(lowLevelFileSystem).
                asyncReadFile(anyInt(),
                        any(byte[].class),
                        anyInt(),
                        anyInt(),
                        any(Consumer.class));
    }

}