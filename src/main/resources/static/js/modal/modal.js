$(document).ready(function(){
    const modalMain = $('.modal-main');

    // button 태그 사용시 type="button" 을 해주지 않으면 js에서 인식을 못한다.

    // 열기 버튼을 눌렀을 때 모달팝업이 열림
//    $('.modal-btn').click(function(){
//        modal.css('display', 'block');
//
//        let exists = modalMain.find('.choose-friend').length > 0;
//        if(exists) {
//            $('.choose-friend').css('display', 'block');
//            $('.input-chatroom').css('display', 'none');
//
//            checkboxes.forEach(checkbox => {
//                checkbox.checked = false;
//            })
//        }
//    });

    // profile -> #addFriendBtn 버튼 클릭시 #friend-add-modal 출력
    $('#addFriendBtn').click(function() {
        $('#friend-add-modal').css('display', 'block');
        messageMainView("아이디를 검색하면 친구를 찾을 수 있습니다.");
    });

    // profile -> #searchFriendBtn 버튼 클릭시 #friend-search-modal 출력
    $('#searchFriendBtn').click(function() {
        $('#friend-search-modal').css('display', 'block');
        messageMainView("닉네임을 검색하면 친구를 찾을 수 있습니다.");
    });

    function messageMainView(message) {
        const modalView = $('.modal-view');

        $('input[type=text]').val('');
        modalView.empty();
        modalView.append(`<span>${message}</span>`);
    }

    // profile -> 친구추가 모달창 닫기
    $('#friendAddCloseBtn, #friendSearchCloseBtn, #profileViewCloseBtn, #editViewCloseBtn').click(function(){
        let modal = $(this).closest('.modal');
        modal.css('display', 'none');
    });

});