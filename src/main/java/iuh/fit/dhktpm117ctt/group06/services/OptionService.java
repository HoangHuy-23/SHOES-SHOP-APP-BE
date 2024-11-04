package iuh.fit.dhktpm117ctt.group06.services;

import iuh.fit.dhktpm117ctt.group06.entities.Option;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface OptionService {

    List<Option> findAllOptions();

    Optional<Option> findOptionById(String id);

    List<Option> findOptionsByName(String name);

    Option saveOption(Option option);

    void deleteOptionById(String id);

    void deleteOptionByName(String name);
}