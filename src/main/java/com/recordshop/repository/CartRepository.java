package com.recordshop.repository;

import com.recordshop.dto.CartDetailDto;
import com.recordshop.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long> {

    Cart findByMemberId(Long memberId);

    Collection<Object> findByIdIn(Collection<Long> ids);

}
