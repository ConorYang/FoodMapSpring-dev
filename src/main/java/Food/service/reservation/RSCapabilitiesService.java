package Food.service.reservation;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import Food.entity.reservation.RSCapabilities;
import Food.entity.reservation.RSCapabilitiesId;
import Food.repository.reservation.RSCapabilitiesRepository;

@Service
public class RSCapabilitiesService {

    private final RSCapabilitiesRepository repository;

    public RSCapabilitiesService(RSCapabilitiesRepository repository) {
        this.repository = repository;
    }

    public List<RSCapabilities> findAll() {
        return repository.findAll();
    }

    public Optional<RSCapabilities> findById(RSCapabilitiesId id) {
        return repository.findById(id);
    }

    public List<RSCapabilities> findByVendorId(Integer vendorId, boolean desc) {
        if (desc) {
            return repository.findByIdVendorIdOrderByIdReservationDateDesc(vendorId);
        } else {
            return repository.findByIdVendorIdOrderByIdReservationDateAsc(vendorId);
        }
    }

    public RSCapabilities save(RSCapabilities entity) {
        return repository.save(entity);
    }

    public void deleteById(RSCapabilitiesId id) {
        repository.deleteById(id);
    }
}
