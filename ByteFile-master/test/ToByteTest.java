import javafx.util.Pair;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.*;
import java.nio.ByteBuffer;

public final class ToByteTest {

    @Test
    public final void run() throws IOException {
        final File dir = new File("src/files");
        if (dir.isDirectory()){
            final File[] innerFiles = dir.listFiles();
            if (innerFiles != null){
                for (final File file : innerFiles){
                    if (file.isFile() && file.getName().endsWith("buf")){
                        if (file.mkdir()){
                            System.out.println("Creating new folder...");
                        }
                        final InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(file));
                        final BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                        final Pair<String[], String[]> verticesAndNormals = this.getVerticesAndNormals(bufferedReader);
                        this.convertToByte(verticesAndNormals.getKey(), file.getName(), "v");
                        this.convertToByte(verticesAndNormals.getValue(), file.getName(), "n");
                        System.out.println("Successful!!!");
                    }
                }
            } else {
                System.out.println("dir 'files' is Empty");
            }
        }
    }

    private Pair<String[], String[]> getVerticesAndNormals(final BufferedReader bufferedReader) throws IOException {
        final String[] vertices = this.convertToArray(bufferedReader.readLine());
        final String[] normals = this.convertToArray(bufferedReader.readLine());
        return new Pair<>(vertices, normals);
    }

    private String[] convertToArray(final String string){
        return string.split(" ");
    }

    private void convertToByte(final String[] strings, final String fileName, final String prefix) throws IOException {
        final ByteBuffer byteBuffer = ByteBuffer.allocate(strings.length * 4);
        for (final String str :strings) {
            final float coordinate = Float.parseFloat(str);
            byteBuffer.putFloat(coordinate);
        }
        FileUtils.writeByteArrayToFile(new File("src/files/" + fileName + "/" + prefix + fileName), byteBuffer.array());
    }

    @Test
    public final void convertToByte() throws IOException {

    }
}