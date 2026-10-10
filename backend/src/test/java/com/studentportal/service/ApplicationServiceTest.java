package com.studentportal.service;
import com.studentportal.dto.ApplicationDto.CreateRequest; import com.studentportal.entity.*; import com.studentportal.exception.ApiException; import com.studentportal.repository.*; import org.junit.jupiter.api.Test; import org.mockito.*; import java.time.*; import java.util.*;
import static org.junit.jupiter.api.Assertions.*; import static org.mockito.Mockito.*;
class ApplicationServiceTest {
 @Test void rejectsDuplicateApplication(){JobApplicationRepository a=mock(JobApplicationRepository.class);StudentRepository s=mock(StudentRepository.class);InternshipRepository i=mock(InternshipRepository.class);ApplicationService service=new ApplicationService(a,s,i);
  Student st=Student.builder().id(1L).user(User.builder().email("x@x.com").build()).build();Internship in=Internship.builder().id(2L).status(InternshipStatus.OPEN).applicationDeadline(LocalDate.now().plusDays(1)).build();
  when(s.findByUserEmail("x@x.com")).thenReturn(Optional.of(st));when(i.findById(2L)).thenReturn(Optional.of(in));when(a.existsByStudentIdAndInternshipId(1L,2L)).thenReturn(true);
  assertThrows(ApiException.class,()->service.create("x@x.com",new CreateRequest(2L,null,null)));
 }
}
