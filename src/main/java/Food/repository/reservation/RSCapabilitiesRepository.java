package Food.repository.reservation;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import Food.entity.reservation.RSCapabilities;
import Food.entity.reservation.RSCapabilitiesId;

@Repository
public interface RSCapabilitiesRepository extends JpaRepository<RSCapabilities, RSCapabilitiesId> {

    List<RSCapabilities> findByIdVendorIdOrderByIdReservationDateDesc(Integer vendorId);

    List<RSCapabilities> findByIdVendorIdOrderByIdReservationDateAsc(Integer vendorId);
}
