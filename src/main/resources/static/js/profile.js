$(document).ready(function() {
    const $view = $('.modal-view');
    let timeoutId; // 처음 조회시 시간을 두고 서버로 전송되도록 설정
    let userInput; // 유저가 입력한 input 데이터를 담을 변수
    let lastScroll = 0; // 현재 스크롤 위치 저장
    let friendList = []; // 서보로부터 받은 유저의 정보를 담는 배열 (계속해서 축적함.)
    let isModalLast = true; // 모달창에서 다음 유저가 있는지를 boolean 타입으로 받음.
    let isFriendLast = true; // 친구목록에서 다음 유저가 있는지를 boolean 타입으로 받음.

    $('#friend-add-modal #searchFriendInput').on('input', function() {
        let $this = $(this);

        clearTimeout(timeoutId); // 이전 타이머 제거
        timeoutId = setTimeout(function() {
            userInput = $this.val();

            if(userInput === ''){ // 입력값이 없을 경우에는 서버에 전송 x
                return;
            }

            $.ajax({
                type: 'get',
                url: `/api/v1/friends/search?cursorId=0&email=${userInput}`,
                dataType: 'json',
                success: function(result) {
                    let userInfo = result.userInfo;
                    isModalLast = result.hasNext;

                    friendList = userInfo;
                    $view.empty();
                    if(userInfo <= 0) {
                        const message = "일치하는 유저가 없습니다.";
                        $view.append(`<span>${message}</span>`);
                    } else {
                        viewUserContainer(userInfo);
                    }
                },
                error: function(request, status, error) {
                    console.log(request);
                }
            });
        }, 300); // 300ms 후에 요청을 보내도록 설정
    });

    // 모달창 무한 스크롤
    $('#friend-add-modal .modal-view').scroll(function() {
        // 현재 높이 저장
        let currentScroll = $(this).scrollTop();
        let cursorId = 0; // 커서 아이디 초기화

        // 전체 문서의 높이
        let scrollHeight = $view.prop('scrollHeight');
        let viewHeight = $view.prop('clientHeight');

        if(currentScroll > lastScroll) {
            if(currentScroll >= (scrollHeight-viewHeight)) {
                console.log('끝에 도달했습니다. 다음 페이지를 불러옵니다.');
                // 커서 아이디를 유저아이디로 사용할 경우
//                let lastUser = $('.modal-view .user').last();
//                let userId = lastUser.find('.user-id').val();

                let cursorId = friendList.length;

                if(isModalLast) { // 마지막일 경우 서버와 통신하지 않는다.
                    $.ajax({
                        type: 'get',
                        url: `/api/v1/friends/search?cursorId=${cursorId}&email=${userInput}`,
                        dataType: 'json',
                        success: function(result) {
                            let userInfo = result.userInfo;
                            isModalLast = result.hasNext;
                            friendList.push(...userInfo);

                            viewUserContainer(userInfo);

                        },
                        error: function(request, status, error) {
                            console.log(request);
                        }
                    });
                }
            }
        }
        // 현재위치 최신화
        lastScroll = currentScroll;
    });

    function viewUserContainer(userInfo) {
        $.each(userInfo, function(key, user) {
            const userContainer = $('<div class="user-container"></div>');

            const userHtml = `
                <div class="user">
                    <input type="hidden" class="user-id" value="${user.userId}"></input>
                    <img src="d3BPj1706541860.jpg" alt="">
                    <div class="user-info">
                        <span class="nickname">${user.nickname}</span>
                        <button type="button" id="selectFriendBtn" class="select-friend">친구추가</button>
                    </div>
                </div>
            `;

            userContainer.append(userHtml); // 사용자 HTML을 userContainer에 추가
            $view.append(userContainer); // userContainer를 view에 추가
        });
    }

    // 동적 생성된 버튼 클릭 이벤트
    $(document).on('click', '.select-friend', function() {
        let $this = $(this);
        let user = $this.closest('.user');
        let userId = user.find('.user-id').val();

        $.ajax({
            type: 'post',
            url: '/api/v1/friends/'+userId,
            dataType: 'json',
//            data: {
//                'friendId': userId
//            },
            success: function(result) {
                if(result) {
                    let successAddText = $('<span></span>').text('친구완료');
                    $this.replaceWith(successAddText);
                }else {
                    alert('친구추가를 실패했습니다. 다시 시도해주세요.');
                }
            },
            error: function(request, status, error) {
                console.log(request);
            }
        });
    });

    $('#profileScroll').scroll(function() {
        let $this = $(this);
        // 현재 스크롤 위치
        let currentScroll = $this.scrollTop();
        // 스크롤 뷰 높이
        let scrollHeight = $('#profileScroll').prop('scrollHeight');
        let clientHeight = $('#profileScroll').prop('clientHeight');

        if(currentScroll >= (scrollHeight - clientHeight)) {
            // 현재 friendList에 있는 user의 개수 = cursorId
            let cursorId = $('.friend-list').find('.user').length;

            if(isFriendLast) {
                $.ajax({
                    type: 'get',
                    url: `/api/v1/friends/list?cursorId=${cursorId}`,
                    dataType: 'json',
                    success: function(result) {
                        const friendList = $('.friend-list');
                        isFriendLast = result.hasNext;

                        $.each(result.userInfo, function(index, friend){
                            let userContainer = $('<div class="user-container"></div>');

                            let inner = `
                                <div class="user">
                                    <input type="hidden" class="friend-id" value="${friend.userId}"/>
                                    <img src="${friend.profileUrl}" alt="">
                                    <div class="user-info">
                                        <div class="user-top">
                                            <div>
                                                <span class="nickname">${friend.nickname}</span>
                                            </div>
                                        </div>
                                        <span class="comment">${friend.description == null ? '' : friend.description}</span>
                                    </div>
                                </div>
                            `;

                            userContainer.append(inner);
                            friendList.append(userContainer);
                        });
                    },
                    error: function(request, status, error) {
                        console.log(request);
                    }
                });
            }
        }
    });
});
