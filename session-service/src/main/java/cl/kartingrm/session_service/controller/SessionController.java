package cl.kartingrm.session_service.controller;

import cl.kartingrm.session_service.model.Session;
import cl.kartingrm.session_service.service.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.DayOfWeek;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/sessions")
@RequiredArgsConstructor
public class SessionController {

    private final SessionService s;

    @GetMapping
    public List<Session> all() { return s.all(); }

    @PostMapping
    public Session save(@RequestBody Session x) { return s.save(x); }

    /** Registro de participantes tras crear una reserva */
    @PutMapping("/{id}/register")
    public void add(@PathVariable Long id, @RequestParam int n){
        s.register(id,n);
    }

    /** Disponibilidad semanal usada por el frontend */
    @GetMapping("/availability")
    public Map<DayOfWeek, List<Session>> week(@RequestParam LocalDate from,
                                             @RequestParam LocalDate to){
        return s.week(from,to);
    }
}
