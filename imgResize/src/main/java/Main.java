import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Main
{
    private static int numberCores = Runtime.getRuntime().availableProcessors();

    private static final String SRC_FOLDER = "./data/imgBig";
    private static final String DST_FOLDER = "./data/imgSmall/";

    private static final int TARGET_HEIGHT = 500;

    public static void main(String[] args)
    {
        File srcDir = new File(SRC_FOLDER);

        long start = System.currentTimeMillis();

        File[] files = srcDir.listFiles();

        int countFilesPart = files.length / numberCores;
        int countStream = numberCores;
        if(files.length < numberCores)
        {
            countFilesPart = files.length;
            countStream = 1;

        }

        ExecutorService service = Executors.newCachedThreadPool();
        for(int indexStream = 0; indexStream < countStream; indexStream++)
        {

            if((indexStream == countStream - 1) && (files.length / numberCores != 0))
            {
                countFilesPart = (files.length - (numberCores * countFilesPart)) + countFilesPart;
            }
            File[] partFiles = new File[countFilesPart];
            System.arraycopy(files, indexStream * countFilesPart, partFiles, 0, partFiles.length);
            service.submit(new ImageResize(partFiles, TARGET_HEIGHT, DST_FOLDER, start));
        }
        service.shutdown();
    }


}

