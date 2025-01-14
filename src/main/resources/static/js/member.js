$(document).ready(function() {

    // 고객센터 클릭 시 전화번호 보여주기
    $('#customerServiceText, #serviceImage').on('click', function() {
        $('#phoneNumber').toggle();  // 전화번호의 보이기/숨기기 토글
    });


});

function showMessage() {
    // 회원가입 완료 메시지
    alert("회원가입이 완료되었습니다. \n로그인을 해주세요.");
}

function showDeleteMessage() {
    // 회원탈퇴 완료 메시지
    alert("회원을 탈퇴합니다.");
}


