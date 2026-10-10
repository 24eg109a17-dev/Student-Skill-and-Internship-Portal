package com.studentportal.service;
import com.studentportal.dto.InternshipDto.*; import com.studentportal.entity.*; import com.studentportal.exception.ApiException; import com.studentportal.repository.InternshipRepository; import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; import java.util.*;
@Service
public class InternshipService {
 private final InternshipRepository repo; public InternshipService(InternshipRepository r){repo=r;}
 @Transactional(readOnly=true)
 public List<Response> search(String q,String location,WorkMode mode,java.math.BigDecimal min,java.math.BigDecimal max,String duration){return repo.search(blank(q),blank(location),mode,min,max,blank(duration)).stream().map(this::out).toList();}
 private String blank(String s){return s==null||s.isBlank()?null:s;}
 @Transactional(readOnly=true)
 public List<Response> all(){return repo.findAll().stream().map(this::out).toList();}
 @Transactional(readOnly=true)
 public Response get(Long id){return out(repo.findById(id).orElseThrow(()->new NoSuchElementException("Internship not found")));}
 @Transactional
 public Response create(Request r){return out(repo.save(in(r,new Internship())));}
 @Transactional
 public Response update(Long id,Request r){Internship i=repo.findById(id).orElseThrow(()->new NoSuchElementException("Internship not found"));return out(repo.save(in(r,i)));}
 @Transactional
 public void delete(Long id){Internship i=repo.findById(id).orElseThrow(()->new NoSuchElementException("Internship not found"));i.setStatus(InternshipStatus.CLOSED);repo.save(i);}
 private Internship in(Request r,Internship i){if(r.applicationDeadline().isBefore(java.time.LocalDate.now()))throw new ApiException("Deadline must not be in the past"); if(r.startDate().isBefore(java.time.LocalDate.now()))throw new ApiException("Start date must not be in the past"); if(r.applicationDeadline().isBefore(r.startDate()))throw new ApiException("Application deadline must be on or after the start date");i.setCompanyName(r.companyName());i.setTitle(r.title());i.setDescription(r.description());i.setRequiredSkills(r.requiredSkills());i.setLocation(r.location());i.setWorkMode(r.workMode());i.setDuration(r.duration());i.setStipend(r.stipend());i.setStartDate(r.startDate());i.setApplicationDeadline(r.applicationDeadline());i.setEligibility(r.eligibility());i.setOpenings(r.openings());i.setStatus(r.status()==null?InternshipStatus.OPEN:r.status());return i;}
 private Response out(Internship i){return new Response(i.getId(),i.getCompanyName(),i.getTitle(),i.getDescription(),i.getRequiredSkills(),i.getLocation(),i.getWorkMode(),i.getDuration(),i.getStipend(),i.getStartDate(),i.getApplicationDeadline(),i.getEligibility(),i.getOpenings(),i.getStatus());}
 public long open(){return repo.countByStatus(InternshipStatus.OPEN);} public long total(){return repo.count();}
}
