package cl.kartingrm.report_service.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reports")
public class ReportController {
    @GetMapping
    public ResponseEntity<Void> ok() {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/by-rate")
    public List<Map<String, Object>> byRate(@RequestParam String from,
                                            @RequestParam String to) {
        return List.of();
    }

    @GetMapping("/by-group")
    public List<Map<String, Object>> byGroup(@RequestParam String from,
                                             @RequestParam String to) {
        return List.of();
    }

    @GetMapping("/by-rate/csv")
    public ResponseEntity<byte[]> csv(@RequestParam String from,
                                      @RequestParam String to) {
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=report.csv")
                .body("rate,total\n".getBytes());
    }
}
