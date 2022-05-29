package jpabook.jpashop.form;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.embedded.Address;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class MemberForm {

    @NotEmpty(message = "회원 이름은 필수 입니다.")
    private String name;

    private String city;

    private String street;

    private String zipcode;

    public Member toEntity() {
        Member member = new Member();
        Address address = new Address(this.getCity(), this.getStreet(), this.getZipcode());
        member.setName(this.getName());
        member.setAddress(address);
        return member;
    }

}
