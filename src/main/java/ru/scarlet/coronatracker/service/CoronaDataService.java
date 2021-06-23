package ru.scarlet.coronatracker.service;

import lombok.Data;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;
import ru.scarlet.coronatracker.model.LocationStats;

import javax.annotation.PostConstruct;
import java.io.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
@Data
public class CoronaDataService {


    private static String DATA_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";

    private List<LocationStats> allStats = new ArrayList<>();


    @PostConstruct
    public void fetchData() throws IOException, InterruptedException {
        {

            List<LocationStats> newStats = new ArrayList<>();

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(DATA_URL)).build();
            HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
            StringReader reader = new StringReader(httpResponse.body());
            Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(reader);
            for (CSVRecord record : records) {
                LocationStats locationStats = new LocationStats();
                locationStats.setState(record.get("Province/State"));
                locationStats.setCountry(record.get("Country/Region"));
                int latestCases = Integer.parseInt(record.get(record.size() - 1));
                locationStats.setLatest(latestCases);
                int prevDayCases = Integer.parseInt(record.get(record.size() - 2));
                locationStats.setDiffFromYest(latestCases-prevDayCases);
                int prePrevDayCases = Integer.parseInt(record.get(record.size()-3))-prevDayCases;
                locationStats.setDelta(prePrevDayCases);
//                if (locationStats.getState()==null||locationStats.getState().equals("")||locationStats.getState().equals(" ")){locationStats.setState("Не указано");}
                newStats.add(locationStats);
            }
            this.allStats=newStats;
        }
    }
}
