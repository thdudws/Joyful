$(document).ready(function() {

    // 고객센터 클릭 시 전화번호 보여주기
    $('#customerServiceText, #serviceImage').on('click', function() {
        $('#phoneNumber').toggle();  // 전화번호의 보이기/숨기기 토글
    });
});

