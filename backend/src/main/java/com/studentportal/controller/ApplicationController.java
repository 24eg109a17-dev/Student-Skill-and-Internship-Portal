package com.studentportal.controller;
import com.studentportal.dto.ApplicationDto.*; import com.studentportal.service.ApplicationService; import jakarta.validation.Valid; import org.springframework.security.core.Authentication; import org.springframework.web.bind.annotation.*; import java.util.*;
@RestController @RequestMapping("/api/applications")
public class ApplicationController {private final ApplicationService s;public ApplicationController(ApplicationService s){this.s=s;}
 @PostMapping Response create(Authentication a,@Valid @RequestBody CreateRequest r){return s.create(a.getName(),r);}
 @GetMapping("/my") List<Response> mine(Authentication a){return s.mine(a.getName());}
 @GetMapping("/{id}") Response get(Authentication a,@PathVariable Long id){return s.get(a.getName(),id);}
}
