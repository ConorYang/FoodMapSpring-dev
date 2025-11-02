package Food.service.member;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import Food.entity.account.User;
import Food.entity.member.Member;
import Food.repository.member.MemberRepository;

@Service
public class MemberService {

	private final MemberRepository memberRepository;

	public MemberService(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}

	/**
	 * 用登入的 User 查詢對應的 Member
	 */
	public Member getMemberByAccountId(Long accountId) {
	    if (accountId == null) {
	        return null;
	    }
	    return memberRepository.findByUser_AccountId(accountId);
	}

	/**
	 * 更新會員資料先查再更新避免覆蓋掉其他欄位）
	 */
	@Transactional
	public Member updateMember(Member updatedMember) {
		Optional<Member> optional = memberRepository.findById(updatedMember.getMemberId());
		if (optional.isEmpty()) {
			throw new IllegalArgumentException("會員不存在，ID: " + updatedMember.getMemberId());
		}

		Member existing = optional.get();

		// 只更新允許修改的欄位
		existing.setUserName(updatedMember.getUserName());
		existing.setAvatarURL(updatedMember.getAvatarURL());
		existing.setGender(updatedMember.getGender());
		existing.setBirthdate(updatedMember.getBirthdate());
		existing.setPhone(updatedMember.getPhone());
		existing.setCity(updatedMember.getCity());
		existing.setDistrict(updatedMember.getDistrict());

		// 保留 User 關聯，不直接覆蓋
		if (updatedMember.getUser() != null) {
			existing.setUser(updatedMember.getUser());
		}

		return memberRepository.save(existing);
	}
	
	public Member findByUsername(String username) {
	    return memberRepository.findByUserName(username)
	            .orElseThrow(() -> new RuntimeException("Member not found: " + username));
	}
	
	
	 public Member createMemberForUser(User user) {
	        Member member = new Member();
	        member.setUser(user);
	        member.setUserName(user.getAccount()); 
	        member.setGender("男");                
	        member.setCity("");
	        member.setDistrict("");

	        return memberRepository.save(member);
	    }
	
	
	
	
	
	
	
	
	
	
	
	
}
