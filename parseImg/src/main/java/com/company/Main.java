package com.company;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;

import java.util.Scanner;

public class Main {

    private static final String PATH_DESKTOP =  System.getProperty("user.home") + "\\Desktop\\imgParse\\";

    private static String nameImg;
    private static int number = 0;
    private static String pathDestinationFolder;

    public static void main(String[] args) throws IOException {

        pathDestination();

        if(pathDestinationFolder.equals(""))
        {
            System.out.println("Неправильный путь к папке!");
            pathDestination();
        }

        if(pathDestinationFolder.equals("1"))
        {
            pathDestinationFolder = PATH_DESKTOP;
        }

        if(!Files.isDirectory(Path.of(pathDestinationFolder)))
        {
            File folder = new File(pathDestinationFolder);
            folder.mkdirs();
            pathDestinationFolder += "\\";
        }

        Document doc = Jsoup.connect("https://lenta.ru/").userAgent("Chrome/4.0.249.0 Safari/532.5").referrer("http://www.google.com").get();
        doc.select("img").forEach(element -> {
            downloadImage(element.attr("src"), pathDestinationFolder);
        });

    }

    private static void pathDestination()
    {
        System.out.println("Введите путь к папке куда копировать или введите - <1> для копирования на Рабочий стол");
        Scanner scanner = new Scanner(System.in);
        pathDestinationFolder = scanner.nextLine();
    }

    public static void downloadImage(String sourceUrl, String pathDestinationFolder) {
        try {
            URL imageUrl = new URL(sourceUrl);
            BufferedImage img = ImageIO.read(imageUrl);
            number++;
            nameImg = "download(" + Integer.toString(number) + ")";
            ImageIO.write(img, "jpg", new File(pathDestinationFolder + nameImg + ".jpg"));
        }
        catch (IOException ex)
        {
            System.out.println("Изображение не скачено : " + sourceUrl);
        }
    }
}
