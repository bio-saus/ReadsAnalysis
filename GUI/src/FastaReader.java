import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Created by matthijs on 29-9-14.
 */
public class FastaReader extends RandomAccessFile {

    long indexHeader;
    long indexBody;

    public FastaReader(String filepath) throws FileNotFoundException {
        super(filepath, "r");
        try {
            boolean header = false;
            char c;
            while ((c = (char) readByte()) != 0) {
                if (c == '>') {
                    indexHeader = getFilePointer();
                    header = true;
                } else if (header && c == '\n') {
                    break;
                }
            }
            header = false;
            indexBody = getFilePointer();
            seek(0);
        } catch (IOException e) {
        }
        ;
    }

    public String getSequence(long start, long end) throws IOException {
        long length = end - start;
        long current = getFilePointer();
        String seq = "";
        seek(indexBody + start);
        for (long i = 0; i < length; ++i) seq += (char) readByte();
        seek(current);
        return seq;
    }

    public long getSequenceLength() throws IOException {
        return length() - indexBody;
    }

    public String getHeader() throws IOException {
        long current = getFilePointer();
        String header = "";
        seek(0);
        for (long i = 0; i < indexBody; ++i) header += readChar();
        seek(current);
        return header;
    }
}
