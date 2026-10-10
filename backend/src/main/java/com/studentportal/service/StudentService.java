package com.studentportal.service;
import com.studentportal.dto.ProfileDto.*; import com.studentportal.dto.SkillDto; import com.studentportal.entity.*; import com.studentportal.exception.ApiException; import com.studentportal.repository.*; import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; import java.util.*;
@Service
public class StudentService {
 private final StudentRepository students; private final UserRepository users; private final SkillRepository skills;
 public StudentService(StudentRepository s,UserRepository u,SkillRepository k){students=s;users=u;skills=k;}
 @Transactional(readOnly=true)
 public Student me(String email){return students.findByUserEmail(email).orElseThrow(()->new NoSuchElementException("Student profile not found"));}
 @Transactional(readOnly=true)
 public Response profile(String email){Student s=me(email);return response(s);}
 @Transactional
 public Response update(String email,Request r){Student s=me(email);User u=s.getUser();if(!u.getEmail().equalsIgnoreCase(r.email()))throw new ApiException("Email cannot be changed from the profile. Please keep the account email unchanged.");
  s.setFullName(r.fullName());s.setPhone(r.phone());s.setCollege(r.college());s.setDegree(r.degree());s.setBranch(r.branch());s.setYearOfStudy(r.yearOfStudy());s.setCgpa(r.cgpa());s.setLocation(r.location());s.setGithubUrl(r.githubUrl());s.setLinkedinUrl(r.linkedinUrl());s.setPortfolioUrl(r.portfolioUrl());s.setResumeUrl(r.resumeUrl());return response(students.save(s));}
 private Response response(Student s){int n=13,filled=0; Object[] v={s.getFullName(),s.getUser().getEmail(),s.getPhone(),s.getCollege(),s.getDegree(),s.getBranch(),s.getYearOfStudy(),s.getCgpa(),s.getLocation(),s.getGithubUrl(),s.getLinkedinUrl(),s.getPortfolioUrl(),s.getResumeUrl()};for(Object x:v)if(x!=null&&!x.toString().isBlank())filled++;return new Response(s.getId(),s.getFullName(),s.getUser().getEmail(),s.getPhone(),s.getCollege(),s.getDegree(),s.getBranch(),s.getYearOfStudy(),s.getCgpa(),s.getLocation(),s.getGithubUrl(),s.getLinkedinUrl(),s.getPortfolioUrl(),s.getResumeUrl(),Math.round(filled*100f/n));}
 @Transactional(readOnly=true)
 public List<SkillDto.Response> getSkills(String email){return me(email).getSkills().stream().map(x->new SkillDto.Response(x.getId(),x.getName())).sorted(java.util.Comparator.comparing(SkillDto.Response::name)).toList();}
 @Transactional
 public SkillDto.Response addSkill(String email,String name){Student s=me(email);String n=name.trim();Skill sk=skills.findByNameIgnoreCase(n).orElseGet(()->skills.save(Skill.builder().name(n).build()));if(!s.getSkills().add(sk))throw new ApiException("Skill already added");students.save(s);return new SkillDto.Response(sk.getId(),sk.getName());}
 @Transactional
 public SkillDto.Response updateSkill(String email,Long id,String name){Student s=me(email); Skill old=skills.findById(id).orElseThrow(()->new NoSuchElementException("Skill not found")); if(s.getSkills().stream().noneMatch(x->x.getId().equals(id)))throw new ApiException("Skill is not part of your profile"); String n=name.trim(); Skill target=skills.findByNameIgnoreCase(n).orElse(old); s.getSkills().remove(old); s.getSkills().add(target); students.save(s); return new SkillDto.Response(target.getId(),target.getName());}
 @Transactional
 public void deleteSkill(String email,Long id){Student s=me(email);boolean ok=s.getSkills().removeIf(x->x.getId().equals(id));if(!ok)throw new NoSuchElementException("Skill not found");students.save(s);}
 @Transactional(readOnly=true)
 public List<Response> all(){return students.findAll().stream().map(s->response(s)).toList();}
}
