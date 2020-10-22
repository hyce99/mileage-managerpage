package mileage;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ManagerpageRepository extends CrudRepository<Managerpage, Long> {

    List<Managerpage> findByMemberId(Long memberId);

}