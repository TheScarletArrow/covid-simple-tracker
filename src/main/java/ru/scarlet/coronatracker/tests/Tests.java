package ru.scarlet.coronatracker.tests;

import lombok.SneakyThrows;
import org.junit.Assert;
import org.junit.Test;
import ru.scarlet.coronatracker.model.LocationStats;
import ru.scarlet.coronatracker.service.CoronaDataService;

import java.io.IOException;
import java.util.List;

public class Tests {
    CoronaDataService service = new CoronaDataService();


    public List<LocationStats> stats = service.getAllStats();
    @Test
    public void arrayNotNull() throws IOException, InterruptedException {
        service.fetchData();
        var all = service.getAllStats();
       Assert.assertNotNull(all);
    }

    @Test
    public void noNullElements() throws IOException, InterruptedException {
        service.fetchData();

        var all = service.getAllStats();

        for (var lov : all) {

            assert lov != null;
        }

    }

    @SneakyThrows
    @Test
    public void containsCountry() {
        service.fetchData();
        var all = service.getAllStats();

        for (var lov : all) {
            Assert.assertNotNull(lov.getCountry());

        }
    }

    @Test
    public void hasStateNotNull() throws IOException, InterruptedException {
        service.fetchData();

        var all = service.getAllStats();

        for (var lov : all) {

            Assert.assertNotNull(lov.getState());

        }

    }


}