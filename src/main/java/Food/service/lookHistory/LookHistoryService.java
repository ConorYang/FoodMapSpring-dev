package Food.service.lookHistory;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import Food.dto.lookHistory.LookHistoryDTO;
import Food.entity.lookHistory.LookHistory;
import Food.repository.lookHistory.LookHistoryRepository;
import Food.repository.vendor.VendorRepository;

@Service
@Transactional
public class LookHistoryService {

    @Autowired
    private VendorRepository vendorRepository;

    @Autowired
    private LookHistoryRepository lookHistoryRepository;

    public LookHistory addOrUpdateHistory(Integer memberId, Integer vendorId) {
        LookHistory history = new LookHistory();
        history.setMemberId(memberId);
        history.setVendorId(vendorId);
        history.setCreateAt(LocalDateTime.now());
        return lookHistoryRepository.save(history);
    }

    public List<LookHistory> getHistoryByMember(Integer memberId) {
        return lookHistoryRepository.findTop16ByMemberIdOrderByCreateAtDesc(memberId);
    }

    public List<LookHistoryDTO> getHistoryByMemberId(Integer memberId) {
        List<LookHistory> list = getHistoryByMember(memberId);

        return list.stream().map(lh -> {
            LookHistoryDTO dto = new LookHistoryDTO();
            dto.setLookHistoryId(lh.getLookHistoryId());
            dto.setMemberId(lh.getMemberId());
            dto.setVendorId(lh.getVendorId());
            dto.setCreateAt(lh.getCreateAt());

            // 取出 vendor 名稱與 logo
            vendorRepository.findById(lh.getVendorId()).ifPresent(v -> {
                dto.setVendorName(v.getVendorName());
                dto.setLogoUrl(v.getLogoURL());
            });

            return dto;
        }).collect(Collectors.toList());
    }

}
