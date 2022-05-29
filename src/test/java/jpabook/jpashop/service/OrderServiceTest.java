package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.domain.embedded.Address;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.exception.NotEnoughStockException;
import jpabook.jpashop.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class OrderServiceTest {

    @PersistenceContext
    EntityManager em;

    @Autowired
    OrderService orderService;

    @Autowired
    OrderRepository orderRepository;

    @Test
    public void 상품주문() {
        //given
        Member member = createMember();

        Item book = createBook("시골 JPA", 10000, 10);

        //when
        int orderCount = 2;
        Long order = orderService.order(member.getId(), book.getId(), orderCount);

        //then
        Order findOrder = orderRepository.findOne(order);
        assertEquals(OrderStatus.ORDER, findOrder.getStatus());
        assertEquals(1, findOrder.getOrderItems().size());
        assertEquals(10000 * orderCount, findOrder.getTotalPrice());
        assertEquals(8, book.getStockQuantity());

    }

    @Test
    public void 상품주문_재고수량초과() {
        //given
        Member member = createMember();
        Item book = createBook("시골 JPA", 10000, 10);

        int orderCount = 11;

        //then
        assertThrows(NotEnoughStockException.class, () -> {
            //when
            orderService.order(member.getId(), book.getId(), orderCount);
        });
    }

    private Item createBook(String name, int price, int stockQuantity) {
        Item book = new Book();
        book.setName(name);
        book.setPrice(price);
        book.setStockQuantity(stockQuantity);
        em.persist(book);
        return book;
    }

    @Test
    public void 주문취소() {
        //given
        Member member = createMember();
        Item book = createBook("시골JPA", 10000, 10);

        int orderCount = 2;

        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

        //when
        orderService.cancelOrder(orderId);

        //then
        Order findOrder = orderRepository.findOne(orderId);
        assertThat(findOrder.getStatus()).isEqualTo(OrderStatus.CANCEL);
        assertThat(book.getStockQuantity()).isEqualTo(10);
    }


    private Member createMember() {
        Member member = new Member();
        member.setName("회원1");
        member.setAddress(new Address("서울", "강가", "123-123"));
        em.persist(member);
        return member;
    }
}