package yammer.com.navtest;

import java.io.FileNotFoundException;
import java.io.IOException;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.Toast;

/**
 * Android Bitmap Object to .bmp image (Windows BMP v3 24bit) file util class
 * <p/>
 * ref : http://en.wikipedia.org/wiki/BMP_file_format
 *
 * @author ultrakain ( ultrasonic@gmail.com )
 * @since 2012-09-27
 */
public class AndroidBmpUtil {

    private final int BMP_WIDTH_OF_TIMES = 4;
    private final int BYTE_PER_PIXEL = 3;

    /**
     * Android Bitmap Object to Window's v3 24bit Bmp Format File
     *
     * @param orgBitmap
     * @param filePath
     * @return file saved result
     */
    public boolean save(Bitmap orgBitmap, String filename, Activity activity) {

        OutputStream fOutputStream = null;
        File file = new File(filename);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            file.mkdirs();
        }

        try {
            fOutputStream = new FileOutputStream(file);

            orgBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOutputStream);

            fOutputStream.flush();
            fOutputStream.close();

            MediaStore.Images.Media.insertImage(activity.getContentResolver(), file.getAbsolutePath(), file.getName(), file.getName());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(activity, "Save Failed", Toast.LENGTH_SHORT).show();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(activity, "Save Failed", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    /**
     * Is last pixel in Android Bitmap width
     *
     * @param width
     * @param i
     * @return
     */
    private boolean isBitmapWidthLastPixcel(int width, int i) {
        return i > 0 && (i % (width - 1)) == 0;
    }

    /**
     * BMP file is a multiples of 4?
     *
     * @param width
     * @return
     */
    private boolean isBmpWidth4Times(int width) {
        return width % BMP_WIDTH_OF_TIMES > 0;
    }

    /**
     * Write integer to little-endian
     *
     * @param value
     * @return
     * @throws IOException
     */
    private byte[] writeInt(int value) throws IOException {
        byte[] b = new byte[4];

        b[0] = (byte) (value & 0x000000FF);
        b[1] = (byte) ((value & 0x0000FF00) >> 8);
        b[2] = (byte) ((value & 0x00FF0000) >> 16);
        b[3] = (byte) ((value & 0xFF000000) >> 24);

        return b;
    }

    /**
     * Write integer pixel to little-endian byte array
     *
     * @param value
     * @return
     * @throws IOException
     */
    private byte[] write24BitForPixcel(int value) throws IOException {
        byte[] b = new byte[3];

        b[0] = (byte) (value & 0x000000FF);
        b[1] = (byte) ((value & 0x0000FF00) >> 8);
        b[2] = (byte) ((value & 0x00FF0000) >> 16);

        return b;
    }

    /**
     * Write short to little-endian byte array
     *
     * @param value
     * @return
     * @throws IOException
     */
    private byte[] writeShort(short value) throws IOException {
        byte[] b = new byte[2];

        b[0] = (byte) (value & 0x00FF);
        b[1] = (byte) ((value & 0xFF00) >> 8);

        return b;
    }
}