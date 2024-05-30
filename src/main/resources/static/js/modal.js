$(document).ready(function(){
    const modal = $('.modal');
    const modalMain = $('.modal-main');
    // button 태그 사용시 type="button" 을 해주지 않으면 js에서 인식을 못한다.

    // 열기 버튼을 눌렀을 때 모달팝업이 열림
    $('.modal-btn').click(function(){
        modal.css('display', 'block');

        let exists = modalMain.find('.choose-friend').length > 0;
        if(exists) {
            $('.choose-friend').css('display', 'block');
            $('.input-chatroom').css('display', 'none');

            checkboxes.forEach(checkbox => {
                checkbox.checked = false;
            })
        }
    });
    // 닫기 버튼을 눌렀을 때 모달팝업이 닫힘
    $('.close-btn').click(function(){
        modal.css('display', 'none');
        // 요소가 있는지 없는지 판별
        let exists = modalMain.find('.choose-friend').length > 0;
        const modalView = $('.modal-view');
        let message;

        if(exists) {
            message = "닉네임을 검색하면 친구를 찾을 수 있습니다.";
            console.log('채팅방에서 닫음');
        }else {
            message = "아이디를 검색하면 친구를 찾을 수 있습니다.";
            console.log('친구목록에서 닫음');
        }
        $('input[type=text]').val('');
        modalView.empty();
        modalView.append(`<span>${message}</span>`);
    });
});