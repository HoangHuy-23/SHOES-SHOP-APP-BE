//package iuh.fit.dhktpm117ctt.group06.service.impl;
//
//import iuh.fit.dhktpm117ctt.group06.entities.CitizenCard;
//import iuh.fit.dhktpm117ctt.group06.repository.CitizenCardRepository;
//import iuh.fit.dhktpm117ctt.group06.service.CitizenCardService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Optional;
//
//@Service
//public class CitizenCardServiceImpl implements CitizenCardService {
//
//    private final CitizenCardRepository citizenCardRepository;
//
//    @Autowired
//    public CitizenCardServiceImpl(CitizenCardRepository citizenCardRepository) {
//        this.citizenCardRepository = citizenCardRepository;
//    }
//
//    @Override
//    public Optional<CitizenCard> findByCardNumber(String cardNumber) {
//        return citizenCardRepository.findByCardNumber(cardNumber);
//    }
//
//    @Override
//    public void deleteByCardNumber(String cardNumber) {
//        citizenCardRepository.deleteByCardNumber(cardNumber);
//    }
//
//    @Override
//    public CitizenCard save(CitizenCard citizenCard) {
//        return citizenCardRepository.save(citizenCard);
//    }
//
//    @Override
//    public List<CitizenCard> findAll() {
//        return citizenCardRepository.findAll();
//    }
//}
