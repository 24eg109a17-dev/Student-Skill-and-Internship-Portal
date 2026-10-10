package com.studentportal.repository;
import com.studentportal.entity.*; import org.springframework.data.jpa.repository.*; import org.springframework.data.repository.query.Param; import java.util.*;
public interface InternshipRepository extends JpaRepository<Internship,Long>{
 @Query("select i from Internship i where i.status = com.studentportal.entity.InternshipStatus.OPEN and (:q is null or lower(i.title) like lower(concat('%',:q,'%')) or lower(i.companyName) like lower(concat('%',:q,'%')) or lower(i.requiredSkills) like lower(concat('%',:q,'%'))) and (:location is null or lower(i.location) like lower(concat('%',:location,'%'))) and (:workMode is null or i.workMode=:workMode) and (:minStipend is null or i.stipend>=:minStipend) and (:maxStipend is null or i.stipend<=:maxStipend) and (:duration is null or lower(i.duration) like lower(concat('%',:duration,'%')))")
 List<Internship> search(@Param("q") String q,@Param("location") String location,@Param("workMode") WorkMode workMode,@Param("minStipend") java.math.BigDecimal minStipend,@Param("maxStipend") java.math.BigDecimal maxStipend,@Param("duration") String duration);
 long countByStatus(InternshipStatus status);
}
