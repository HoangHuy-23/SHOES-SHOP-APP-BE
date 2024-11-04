package iuh.fit.dhktpm117ctt.group06.repository;

import iuh.fit.dhktpm117ctt.group06.entities.ServiceInformation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ServiceInformationRepository extends JpaRepository<ServiceInformation, String> {
        List<ServiceInformation> findByName(String name);

}