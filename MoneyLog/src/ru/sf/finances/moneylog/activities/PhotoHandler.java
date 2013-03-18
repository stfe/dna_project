package ru.sf.finances.moneylog.activities;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.os.Environment;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Processes photo
 */
public class PhotoHandler implements Camera.PictureCallback {

    private final Activity activity;

    public PhotoHandler(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void onPictureTaken(byte[] data, Camera camera) {
        File pictureFileDir = getDir();

        /**
         * Check if dir exists and create new one if not
         */
        if (!pictureFileDir.exists() && !pictureFileDir.mkdirs()) {
            Toast.makeText(activity, "Can't create directory to save image.",
                    Toast.LENGTH_LONG).show();
        }

        /**
         * Create a new picture file name
         */
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyymmddhhmmss");
        String date = dateFormat.format(new Date());
        String photoFile = "Picture_" + date + ".jpg";
        String filename = pictureFileDir.getPath() + File.separator + photoFile;
        File pictureFile = new File(filename);

        try {
            FileOutputStream fos = new FileOutputStream(pictureFile);
            fos.write(data);
            fos.close();
            Toast.makeText(activity, "New Image saved:" + photoFile,
                    Toast.LENGTH_LONG).show();
            if (((AddTransaction)activity).getBitmap() != null) {
                ((AddTransaction)activity).getBitmap().recycle();
            }
            ((AddTransaction)activity).setBitmap(BitmapFactory.decodeByteArray(data, 0, data.length));
            ((AddTransaction)activity).getImageView().setImageBitmap(((AddTransaction)activity).getBitmap());

        } catch (Exception error) {
            Toast.makeText(activity, "Image could not be saved.",
                    Toast.LENGTH_LONG).show();
        }
    }

    private File getDir() {
        File sdDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        return new File(sdDir, "CustomCameraAPI");
    }
}
