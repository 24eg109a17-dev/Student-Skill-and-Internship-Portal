package com.studentportal.controller;
import com.studentportal.dto.InternshipDto.Response; import com.studentportal.entity.WorkMode; import com.studentportal.service.InternshipService; import org.springframework.web.bind.annotation.*; import java.math.BigDecimal; import java.util.*;
@RestController @RequestMapping("/api/internships")
public class InternshipController {private final InternshipService s;public InternshipController(InternshipService s){this.s=s;}
 @GetMapping List<Response> list(@RequestParam(required=false)String q,@RequestParam(required=false)String location,@RequestParam(required=false)WorkMode workMode,@RequestParam(required=false)BigDecimal minStipend,@RequestParam(required=false)BigDecimal maxStipend,@RequestParam(required=false)String duration){return s.search(q,location,workMode,minStipend,maxStipend,duration);}
 @GetMapping("/{id}") Response get(@PathVariable Long id){return s.get(id);}
}
