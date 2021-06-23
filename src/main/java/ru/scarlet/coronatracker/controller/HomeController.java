package ru.scarlet.coronatracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.scarlet.coronatracker.model.LocationStats;
import ru.scarlet.coronatracker.service.CoronaDataService;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    CoronaDataService service;


    @GetMapping("/")
    public String home(Model model){
        List<LocationStats> allStats = service.getAllStats();
        int sum = allStats.stream().mapToInt(LocationStats::getLatest).sum();
        int totalNewCases = allStats.stream().mapToInt(LocationStats::getDiffFromYest).sum();
        int preTotal = allStats.stream().limit(allStats.size()-1)
                .mapToInt(LocationStats::getDiffFromYest).sum();

        model.addAttribute("locationStats", allStats);
        model.addAttribute("totalReportedCases", sum);
        model.addAttribute("totalNewCases", totalNewCases);
        model.addAttribute("preTotal", preTotal);
        model.addAttribute("delta", (totalNewCases-preTotal));
        return "home";
    }
    @GetMapping("/info")
    public String info(){
        return "info";
    }
}
