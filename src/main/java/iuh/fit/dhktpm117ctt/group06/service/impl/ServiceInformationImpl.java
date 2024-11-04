package iuh.fit.dhktpm117ctt.group06.service.impl;

import iuh.fit.dhktpm117ctt.group06.entities.ServiceInformation;
import iuh.fit.dhktpm117ctt.group06.repository.ServiceInformationRepository;
import iuh.fit.dhktpm117ctt.group06.services.ServiceInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ServiceInformationImpl implements ServiceInformationService {


    ServiceInformationRepository serviceInformationRepository;
    @Autowired
    public ServiceInformationImpl(ServiceInformationRepository serviceInformationRepository) {
        this.serviceInformationRepository = serviceInformationRepository;
    }

    @Override
    public List<ServiceInformation> findByName(String name) {
        return serviceInformationRepository.findByName(name);
    }

    @Override
    public List<ServiceInformation> findAll() {
        return serviceInformationRepository.findAll();
    }

    @Override
    public ServiceInformation save(ServiceInformation serviceInformation) {
        return serviceInformationRepository.save(serviceInformation);
    }

    @Override
    public void deleteById(String id) {
        serviceInformationRepository.deleteById(id);
    }

    @Override
    public ServiceInformation update(ServiceInformation serviceInformation) {
        return serviceInformationRepository.save(serviceInformation);
    }
}
