package iuh.fit.dhktpm117ctt.group06.repository;



import iuh.fit.dhktpm117ctt.group06.entities.CitizenCard;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CitizenCardRepository extends JpaRepository<CitizenCard, String> {

    // Tìm thẻ căn cước công dân dựa trên số thẻ
    Optional<CitizenCard> findByCardNumber(String cardNumber);

    void deleteByCardNumber(String cardNumber);
}