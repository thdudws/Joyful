document.addEventListener("DOMContentLoaded", function () {
    // 가격을 포맷팅하는 함수
    function formatPrice(price) {
        return Number(price).toLocaleString() + "원"; // 쉼표 추가하고 '원' 붙임
    }

    // 안전하게 숫자를 변환하는 함수
    function safeParseInt(value, defaultValue = 0) {
        let parsedValue = parseInt(value.replace("원", "").trim().replace(",", ""), 10);
        return isNaN(parsedValue) ? defaultValue : parsedValue; // 변환 실패 시 기본값 반환
    }

    // 가격에 쉼표를 추가하는 공통 함수
    function updatePrices(selector) {
        document.querySelectorAll(selector).forEach(function (element) {
            let price = safeParseInt(element.innerText); // 안전한 숫자 변환
            element.innerText = formatPrice(price); // 쉼표 추가 후 '원' 붙여서 표시
        });
    }

    // 주문 금액 합계를 갱신
    function getOrderTotalPrice() {
        let totalPrice = 0;

        // 각 상품의 총 금액을 더함
        document.querySelectorAll('[id^="totalPrice_"]').forEach(function (element) {
            let price = safeParseInt(element.innerText); // 안전한 숫자 변환
            totalPrice += price;
        });

        // `final_pay` 요소에서 현재 값 가져오기
        let finalPayElement = document.getElementById("final_pay");
        if (finalPayElement) {
            let finalPayPrice = safeParseInt(finalPayElement.innerText); // 안전한 숫자 변환
            totalPrice += finalPayPrice; // final_pay 금액을 총합에 더함
        }

        // 전체 주문 금액에 쉼표 추가 후 `orderTotalPrice`에 반영
        let orderTotalPriceElement = document.getElementById("orderTotalPrice");
        if (orderTotalPriceElement) {
            orderTotalPriceElement.innerText = formatPrice(totalPrice); // '원' 붙여서 표시
        }
    }

    // 페이지 로딩 시 바로 가격과 총 주문 금액을 갱신
    updatePrices(".price"); // 각 상품의 가격에 쉼표 추가
    updatePrices('[id^="totalPrice_"]'); // 각 상품의 총 금액에 쉼표 추가
    getOrderTotalPrice(); // 총 주문 금액 계산

    // HTML 요소에서 메시지 속성 값을 가져옴
    var messageElement = document.getElementById("message");
    if (messageElement) {
        var successMessage = messageElement.getAttribute("data-success-message");
        var errorMessage = messageElement.getAttribute("data-error-message");

        // 성공 메시지가 있으면 alert 띄우기
        if (successMessage) {
            alert(successMessage);
        }

        // 오류 메시지가 있으면 alert 띄우기
        if (errorMessage) {
            alert(errorMessage);
        }
    }
});
