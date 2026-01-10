package report;

import net.masterthought.cucumber.Configuration;
import net.masterthought.cucumber.ReportBuilder;
import java.io.File;
import java.util.Collections;
import java.util.List;

public class GenerateReport {
    public static void main(String[] args) {
        File outputDir = new File("build/cucumber-report");
        List<String> jsonFiles = Collections.singletonList("target/cucumber.json"); // igual que tu runner

        Configuration config = new Configuration(outputDir, "Proyecto QA");
        ReportBuilder reportBuilder = new ReportBuilder(jsonFiles, config);
        reportBuilder.generateReports();
    }
}