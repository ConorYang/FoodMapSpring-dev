package Food.entity.member;

import java.time.LocalDate;

import Food.entity.account.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Members")
public class Member {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "MemberID")
	private Integer memberId;

	@Column(name = "UserName", nullable = false, length = 30)
	private String userName;

	@Column(name = "AvatarURL", length = 1000)
	private String avatarURL;

	@Column(name = "Gender", length = 10)
	private String gender;

	@Column(name = "Birthdate")
	private LocalDate birthdate;

	@Column(name = "Phone", length = 20)
	private String phone;

	@Column(name = "City", nullable = false, length = 10)
	private String city;

	@Column(name = "District", nullable = false, length = 10)
	private String district;

	// 關聯到Account
	@OneToOne
	@JoinColumn(name = "AccountID", referencedColumnName = "AccountID")
	private User user;


	public Integer getMemberId() {
		return memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getAvatarURL() {
		return avatarURL;
	}

	public void setAvatarURL(String avatarURL) {
		this.avatarURL = avatarURL;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public LocalDate getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(LocalDate birthdate) {
		this.birthdate = birthdate;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
