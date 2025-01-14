package com.recordshop.repository;

import com.recordshop.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, String> {
<<<<<<< HEAD

    Cart findByMemberId(Long memberId);

=======

    Cart findByMemberName(String username);

    Cart findByMemberId(Long memberId);

    Collection<Object> findByIdIn(Collection<Long> ids);
>>>>>>> 34e881db6c31b3223564fe05e792f8077de29c95
}
