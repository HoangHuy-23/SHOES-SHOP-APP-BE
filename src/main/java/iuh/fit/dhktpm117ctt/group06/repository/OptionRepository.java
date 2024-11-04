package iuh.fit.dhktpm117ctt.group06.repository;

import iuh.fit.dhktpm117ctt.group06.entities.Option;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OptionRepository extends JpaRepository<Option, String> {

    // Tìm tất cả tùy chọn có tên bằng name

    List<Option> findByName(String name);

    // Tìm tất cả tùy chọn dựa trên loại dịch vụ

    List<Option> findByServiceInformations_ServiceType(String serviceType);

    //Xóa theo tên

    void deleteByName(String name);
}