package com.company;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileWriter;
import java.io.IOException;

import java.util.*;

public class Main {

    private static final String URL_PARSE = "https://clck.ru/MCHxP";
    private static final String PATH_JSON = "data\\mapMoscow.json";

    private static final String URL_CONNECTION = "https://ru.wikipedia.org";


    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public static void main(String[] args) throws IOException {
        FileWriter file = new FileWriter(PATH_JSON);

        Document doc = Jsoup.connect(URL_PARSE).userAgent("Chrome/4.0.249.0 Safari/532.5").referrer("http://www.google.com").get();

        Map<String, List<String>> stationsJson = new TreeMap<>();

        Element table = doc.select("table").get(3);
        Elements tr = table.select("tr");

        ArrayList<List<Connection>> connectionsJson = new ArrayList<>();

        ArrayList<Line> linesJson = new ArrayList<>();

        Element tableLine = doc.select("table").get(8);
        Elements spanLine = tableLine.select("tr").get(1).select("dd");

        spanLine.stream().forEach(element ->
        {
            String[] line = element.text().split(" ");
            linesJson.add(new Line(line[0], line[1]));
        });


        tr.stream().skip(1).forEach(element ->
        {
            Elements td = element.select("td");

            String stationName = td.get(1).child(0).text();
            String lineNumber = td.get(0).child(0).text();

            if(stationsJson.containsKey(lineNumber))
                {
                    stationsJson.get(lineNumber).add(stationName);
                }
                else
                    {
                     stationsJson.put(lineNumber, new ArrayList<>());
                     stationsJson.get(lineNumber).add(stationName);
                    }


            List<String> connectionsNumber = td.get(3).children().eachText();
                if((connectionsNumber.size() > 0) && (!td.get(3).attr("data-sort-value").matches("Infinity")))
                {
                    List<String> nameConnections = new ArrayList<>();
                    td.get(3).select("a").stream().skip(0).forEach(href ->
                    {

                        try {
                            Document parseNameConnection = Jsoup.connect(URL_CONNECTION + href.attr("href")).userAgent("Chrome/4.0.249.0 Safari/532.5").referrer("http://www.google.com").get();
                            String[] name = parseNameConnection.select("h1").text().split("\\(");
                            nameConnections.add(name[0].trim());
                        } catch (IOException e) {
                        }

                    });

                    List<Connection> connections = new ArrayList<>();
                    connections.add(new Connection(lineNumber, stationName));
                    for(int i = 0; i < connectionsNumber.size(); i++)
                    {
                        connections.add(new Connection(connectionsNumber.get(i), nameConnections.get(i)));
                    }
                    connectionsJson.add(connections);
                }
        });


        Metro metro = new Metro(stationsJson, linesJson, connectionsJson);
        file.write(GSON.toJson(metro));
        file.close();
        System.out.println("Парсинг завершён");
    }
}

class Line {
    private String number;
    private String name;

    public Line(String number, String name)
    {
            this.number = number;
            this.name = name;

    }
}

class Connection{
    private String line;
    private String station;

    public Connection(String line, String station)
    {
        this.line = line;
        this.station = station;
    }
}

class Metro{
    private Map<String, List<String>> stations;
    private ArrayList<Line> lines;
    private ArrayList<List<Connection>> connections;

    public Metro(Map<String, List<String>> stationsJson, ArrayList<Line> linesJson, ArrayList<List<Connection>> connectionsJson)
    {
        this.stations = stationsJson;
        this.lines = linesJson;
        this.connections = connectionsJson;
    }
}





