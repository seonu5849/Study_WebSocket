$(document).ready(function() {
    let friendNickname;
    let isFindFriendLast;

    $('#friend-search-modal #searchFriendInput').on('input', function() {
        let $this = $(this);

        let timeoutId = setTimeout(function() {
            friendNickname = $this.val();

            if(friendNickname === '') { // 입력값이 없을 경우 실행 x
                return;
            }

            $.ajax({
                type: 'get',
                url: `/api/v1/friends/find?nickname=${friendNickname}`,
                dataType: 'json',
                success: function(result){
                    isFindFriendLast = result.hasNext;
                    let friendInfo = result.userInfo;

                    const modalView = $('.modal-view');
                    modalView.empty(); // 모달 뷰 비우기
                    const userContainer = $('<div class="user-container"></div>');

                    if(friendInfo <= 0) {
                        const message = "친구를 찾을 수 없습니다.";
                        modalView.append(`<span>${message}</span>`);
                    }

                    findFriendAppendView(modalView, userContainer, friendInfo);
                    modalView.append(userContainer);
                },
                error: function(request, status, error) {
                    console.log(request);
                }
            });
        }, 300);
    });

    $('#friend-search-modal .modal-view').scroll(function() {
        let cursorId = 0; // 커서 아이디 초기화
        let currentScroll = $(this).scrollLeft();

        let scrollWidth = $(this).prop('scrollWidth');
        let viewWidth = $(this).prop('clientWidth');


        // 스크롤이 끝까지 갈 경우 ajax를 통해 서버와 통신
        if(currentScroll >= (scrollWidth - viewWidth)) {
            console.log('끝에 도달했습니다. 다음 페이지를 불러옵니다.');

            cursorId = $(this).find('.user').length;
            console.log(cursorId);
            console.log(isFindFriendLast);

            if(isFindFriendLast) {
                $.ajax({
                    type: 'get',
                    url: `/api/v1/friends/find?cursorId=${cursorId}&nickname=${friendNickname}`,
                    dataType: 'json',
                    success: function(result) {
                        const modalView = $('.modal-view');
                        let friendInfo = result.userInfo;
                        isFindFriendLast = result.hasNext;
                        const userContainer = modalView.find('.user-container');

                        findFriendAppendView(modalView, userContainer, friendInfo);
                    },
                    error: function(request, status, error) {
                        console.log(request);
                    }
                });
            }
        }
    });

    function findFriendAppendView(modalView, userContainer, friends) {
        $.each(friends, function(index, friend) {
            const inner = `
                <div class="user">
                    <input type="hidden" class="friend-id" value="${friend.userId}"/>
                    <img src="${friend.profileUrl}" alt="">
                    <span class="nickname">${friend.nickname}</span>
                </div>
            `;
            userContainer.append(inner);

        });
    }

});