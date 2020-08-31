import org.imgscalr.Scalr;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;


public class ImageResize implements Runnable
{
    private File[] files;
    private int targetHeight;
    private String dstFolder;
    private long start;

    public ImageResize(File[] files, int targetHeight, String dstFolder, long start) {
        this.files = files;
        this.targetHeight = targetHeight;
        this.dstFolder = dstFolder;
        this.start = start;
    }

    @Override
    public void run() {
        try {
            for (File file : files) {
                BufferedImage image = ImageIO.read(file);
                if (image == null) {
                    continue;
                }

                BufferedImage imageSmall = resize(image, 2 * targetHeight, Scalr.Method.SPEED);
                imageSmall = resize(imageSmall, targetHeight, Scalr.Method.ULTRA_QUALITY);


                File newFile = new File(dstFolder + file.getName());
                ImageIO.write(imageSmall, "jpg", newFile);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        System.out.println("Время выполнения: " + (System.currentTimeMillis() - start));
    }

    public static BufferedImage resize(BufferedImage image, int height, Scalr.Method scalr)
    {
        return Scalr.resize(image,
               scalr,
               Scalr.Mode.FIT_TO_WIDTH,
               height,
               Scalr.OP_ANTIALIAS);
    }


}
