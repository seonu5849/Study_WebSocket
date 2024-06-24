$(document).ready(function() {
    const memberModal = $('#memberModal');

    $('#memberInfoBtn').click(function() {
        let chatRoomId = getChatRoomId();
        $.ajax({
            type: 'get',
            url: `/api/v1/chatrooms/${chatRoomId}/members`,
            dataType: 'json',
            success: function(result) {
                console.log(result);
                const userContainer = $('.user-info');

                $.each(result, function(index, user) {
                    const userWrapper = $('<div>', {class: 'user-wrapper'});
                    const inner = `<div class="user">
                                       <input type="hidden" id="userId" value="${user.userId}"/>
                                       <img src="/api/v1/friends/profile/${user.profileUrl}" alt="">
                                       <span class="nickname">${user.nickname}</span>
                                   </div>`;
                    userWrapper.append(inner);
                    userContainer.append(userWrapper);
                });
            },
            error: function(request, status, error) {
                console.log(request);
            }
        });

        open();
    });

    $('#memberModal').on('click',function(e) {
        e.target.id === memberModal.prop('id') ? close() : false;

    });

    function open() {
        memberModal.css('display', 'block');
    }
    function close() {
        memberModal.css('display', 'none');
    }

    function getChatRoomId() {
        // 현재 URL을 가져오기
        let currentUrl = window.location.href;

        // URL 객체 생성
        let url = new URL(currentUrl);

        // pathname에서 pathVariable을 추출
        let path = url.pathname;
        // 경로를 '/'로 분리하여 배열로 변환
        let pathParts = path.split('/');
        // pathParts 배열에서 두 번째 요소를 아이디로 사용
        return pathParts[2];
    }
});