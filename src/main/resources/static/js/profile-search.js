$(document).ready(function() {
    let friendNickname;
    let isFindFriendList = false;

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
                    isFindFriendList = result.last;
                    let friendInfo = result.userInfo;

                    const modalView = $('.modal-view');
                    modalView.empty(); // 모달 뷰 비우기
                    const userContainer = $('<div class="user-container"></div>');

                    if(friendInfo <= 0) {
                        const message = "친구를 찾을 수 없습니다.";
                        modalView.append(`<span>${message}</span>`);
                    }

                    findFriendAppendView(modalView, userContainer, friendInfo)
                },
                error: function(request, status, error) {
                    console.log(request);
                }
            });
        }, 300);
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
            modalView.append(userContainer);
        });
    }

});