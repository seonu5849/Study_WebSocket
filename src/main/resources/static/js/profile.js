$(document).ready(function() {
    const $view = $('.modal-view');
    let timeoutId; // 처음 조회시 시간을 두고 서버로 전송되도록 설정
    let userInput; // 유저가 입력한 input 데이터를 담을 변수
    let lastScroll = 0; // 현재 스크롤 위치 저장
    let friendList = []; // 서보로부터 받은 유저의 정보를 담는 배열 (계속해서 축적함.)
    let isLast; // 다음 유저가 있는지를 boolean 타입으로 받음.

    $('#searchFriendInput').on('input', function() {
        let $this = $(this);

        clearTimeout(timeoutId); // 이전 타이머 제거
        timeoutId = setTimeout(function() {
            userInput = $this.val();

            if(userInput === ''){ // 입력값이 없을 경우에는 서버에 전송 x
                return;
            }

            $.ajax({
                type: 'get',
                url: `/api/v1/friend?cursorId=0&email=${userInput}`,
                dataType: 'json',
                success: function(result) {
                    let userInfo = result.userInfo;
                    isLast = result.last;

                    friendList = userInfo;
                    $view.empty();
                    if(userInfo <= 0) {
                        const message = "아이디를 검색하면 친구를 찾을 수 있습니다.";
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

    // 무한 스크롤
    $view.scroll(function(e) {
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

                if(!isLast) { // 마지막일 경우 서버와 통신하지 않는다.
                    $.ajax({
                        type: 'get',
                        url: `/api/v1/friend?cursorId=${cursorId}&email=${userInput}`,
                        dataType: 'json',
                        success: function(result) {
                            let userInfo = result.userInfo;
                            isLast = result.last;
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
        let user = $(this).closest('.user');
        let userId = user.find('.user-id').val();

        $.ajax({
            type: 'post',
            url: '/api/v1/friend',
            dataType: 'json',
            data: {
                'friendId': userId
            },
            success: function(result) {
                console.log(result);
            },
            error: function(request, status, error) {
                console.log(request);
            }
        });
    });
});
