package com.recordshop.repository;

import com.recordshop.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, String> {

    Cart findByMemberName(String username);

    Cart findByMemberId(Long memberId);

    Collection<Object> findByIdIn(Collection<Long> ids);
}
