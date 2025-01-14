//package com.recordshop.service;
//
//import com.recordshop.dto.PaymentRequestDto;
//import com.recordshop.entity.Payment;
//import com.recordshop.repository.PaymentRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//@Service
//@RequiredArgsConstructor
//public class PaymentService {
//
//
//    private final PaymentRepository paymentRepository;
//
//    /*
//     * 결제 정보 저장
//     *  paymentRequestDto 클라이언트로부터 전달된 결제 요청 DTO 저장된 Payment 엔티티
//     */
//    @Transactional
//    public Payment savePayment(PaymentRequestDto paymentRequestDto) {
//        Payment payment = new Payment();
//        payment.setOrderNumber(paymentRequestDto.getOrderNumber());
//        payment.setImpUid(paymentRequestDto.getImpUid());
//        payment.setAmount(paymentRequestDto.getAmount());
//        payment.setPaymentMethod(paymentRequestDto.getPaymentMethod());
//        payment.setPaymentStatus("ready"); // 초기 상태는 'ready'
//        // createdAt과 updatedAt은 @CreationTimestamp와 @UpdateTimestamp로 자동 설정되므로 수동 설정할 필요 없음
//        return paymentRepository.save(payment);
//    }
//
//    /*
//      결제 상태 업데이트 orderNumber 주문 번호 (Merchant UID)
//        status 업데이트할 결제 상태 (e.g., paid, cancelled)
//     */
//    @Transactional
//    public void updatePaymentStatus(String orderNumber, String status) {
//        Payment payment = paymentRepository.findByOrderNumber(orderNumber)
//                .orElseThrow(() -> new IllegalArgumentException("해당 주문 번호에 대한 결제를 찾을 수 없습니다: " + orderNumber));
//
//        payment.setPaymentStatus(status); // 상태를 업데이트
//
//        // updatedAt은 @UpdateTimestamp로 자동 설정되므로 수동 설정할 필요 없음
//        paymentRepository.save(payment);
//    }
//
//    /*
//     * 주문 번호로 결제 정보 조회 orderNumber 주문 번호 (Merchant UID)
//     * @return 결제 정보 엔티티
//     */
//    @Transactional(readOnly = true)
//    public Payment getPaymentByOrderNumber(String orderNumber) {
//        return paymentRepository.findByOrderNumber(orderNumber)
//                .orElseThrow(() -> new IllegalArgumentException("해당 주문 번호에 대한 결제 정보를 찾을 수 없습니다: " + orderNumber));
//    }
//
//    /*
//     * 아임포트 UID로 결제 정보 조회 impUid 아임포트 결제 UID
//     * @return 결제 정보 엔티티
//     */
//    @Transactional(readOnly = true)
//    public Payment getPaymentByImpUid(String impUid) {
//        return paymentRepository.findByImpUid(impUid)
//                .orElseThrow(() -> new IllegalArgumentException("해당 아임포트 UID에 대한 결제 정보를 찾을 수 없습니다: " + impUid));
//    }
//
//
//
//}