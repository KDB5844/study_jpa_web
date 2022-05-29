package jpabook.jpashop.form;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class BookForm {

    // 상품의 공통 속성
    private Long id;
    
    private String name;
    private int price;
    private int stockQuantity;

    // BOOK 의 속성
    private String author;
    private String isbn;

}
