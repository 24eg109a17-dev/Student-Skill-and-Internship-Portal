package com.studentportal.controller;
import com.studentportal.dto.ApplicationDto.StatusRequest;
import com.studentportal.dto.InternshipDto.Request;
import com.studentportal.dto.InternshipDto.Response;
import com.studentportal.dto.ApplicationDto;
import com.studentportal.service.*;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController @RequestMapping("/api/admin")
public class AdminController {
 private final StudentService students; private final InternshipService internships; private final ApplicationService apps;
 public AdminController(StudentService s,InternshipService i,ApplicationService a){students=s;internships=i;apps=a;}
 @GetMapping("/students") Object students(){return students.all();}
 @GetMapping("/applications") Object applications(){return apps.all();}
 @PostMapping("/internships") Response create(@Valid @RequestBody Request r){return internships.create(r);}
 @PutMapping("/internships/{id}") Response update(@PathVariable Long id,@Valid @RequestBody Request r){return internships.update(id,r);}
 @DeleteMapping("/internships/{id}") void close(@PathVariable Long id){internships.delete(id);}
 @PutMapping("/applications/{id}/status") ApplicationDto.Response status(@PathVariable Long id,@Valid @RequestBody StatusRequest r){return apps.status(id,r.status());}
 @GetMapping("/internships") List<Response> internships(){return internships.all();}
 @GetMapping("/stats") Map<String,Object> stats(){return Map.of("totalStudents",students.all().size(),"totalInternships",internships.total(),"openInternships",internships.open(),"totalApplications",apps.count(),"selectedApplications",apps.selected(),"rejectedApplications",apps.rejected());}
}
