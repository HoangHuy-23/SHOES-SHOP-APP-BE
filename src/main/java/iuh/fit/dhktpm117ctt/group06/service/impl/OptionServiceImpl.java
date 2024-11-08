package iuh.fit.dhktpm117ctt.group06.service.impl;

import iuh.fit.dhktpm117ctt.group06.repository.OptionRepository; // Giả sử bạn đã có repository này
import iuh.fit.dhktpm117ctt.group06.service.OptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OptionServiceImpl implements OptionService {

    private final OptionRepository optionRepository;

    @Autowired
    public OptionServiceImpl(OptionRepository optionRepository) {
        this.optionRepository = optionRepository;
    }

    @Override
    public List<Option> findAllOptions() {
        return optionRepository.findAll();
    }

    @Override
    public Optional<Option> findOptionById(String id) {
        return optionRepository.findById(id);
    }

    @Override
    public List<Option> findOptionsByName(String name) {
        return optionRepository.findByName(name);
    }


    @Override
    public Option saveOption(Option option) {
        return optionRepository.save(option);
    }

    @Override
    public void deleteOptionById(String id) {
        optionRepository.deleteById(id);
    }

    @Override
    public void deleteOptionByName(String name) {
        optionRepository.deleteByName(name);
    }
}
