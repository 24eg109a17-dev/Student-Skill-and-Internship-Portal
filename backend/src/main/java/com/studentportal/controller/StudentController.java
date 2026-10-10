package com.studentportal.controller;
import com.studentportal.dto.ProfileDto.*; import com.studentportal.dto.SkillDto; import com.studentportal.service.StudentService; import jakarta.validation.Valid; import org.springframework.security.core.Authentication; import org.springframework.web.bind.annotation.*; import java.util.*;
@RestController @RequestMapping("/api/students")
public class StudentController {private final StudentService s;public StudentController(StudentService s){this.s=s;}
 @GetMapping("/profile") Response profile(Authentication a){return s.profile(a.getName());}
 @PutMapping("/profile") Response update(Authentication a,@Valid @RequestBody Request r){return s.update(a.getName(),r);}
 @GetMapping("/skills") List<SkillDto.Response> skills(Authentication a){return s.getSkills(a.getName());}
 @PostMapping("/skills") SkillDto.Response add(Authentication a,@Valid @RequestBody SkillDto.Request r){return s.addSkill(a.getName(),r.name());}
 @PutMapping("/skills/{id}") SkillDto.Response update(Authentication a,@PathVariable Long id,@Valid @RequestBody SkillDto.Request r){return s.updateSkill(a.getName(),id,r.name());}
 @DeleteMapping("/skills/{id}") void del(Authentication a,@PathVariable Long id){s.deleteSkill(a.getName(),id);}
}
