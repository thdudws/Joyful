//package com.recordshop.entity;
//
//import jakarta.persistence.*;
//import lombok.Getter;
//import lombok.Setter;
//
//import java.time.LocalDateTime;
//
//@Entity
//@Getter @Setter
//@Table(name = "payment")
//public class Payment {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "payment_id")
//    private Long id;
//
//    @Column(unique = true, nullable = false)
//    private String orderNumber; //아임포트 Merchant UID
//
//    @Column(unique = true, nullable = false)
//    private String impUid; // 아임포트 결제 UID
//
//    @Column(nullable = false)
//    private Long amount;
//
//    @Column(nullable = false)
//    private String paymentMethod;
//
//    @Column(nullable = false)
//    private String paymentStatus;
//
//    private LocalDateTime createdAt; // 결제 요청 시간
//    private LocalDateTime updatedAt; // 상태 변경 시간
//
//    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    @JoinColumn(name = "member_id")
//    private Member member;
//
//    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    @JoinColumn(name = "cart_id")
//    private Cart cart;
//}
