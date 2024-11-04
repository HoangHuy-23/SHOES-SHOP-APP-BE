package iuh.fit.dhktpm117ctt.group06.services;

import iuh.fit.dhktpm117ctt.group06.entities.ServiceInformation;

import java.util.List;

public interface ServiceInformationService {

    List<ServiceInformation> findByName(String name);

    List<ServiceInformation> findAll();

    ServiceInformation save(ServiceInformation serviceInformation);

    void deleteById(String id);

    ServiceInformation update(ServiceInformation serviceInformation);
}
