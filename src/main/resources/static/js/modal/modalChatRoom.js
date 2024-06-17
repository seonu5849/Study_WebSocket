const chooseFriends = [];
let inputNickname;
let isFindFriend;

$(document).ready(function() {

    $('#findFriendInput').on('input', function() {
        let $this = $(this);

        let timeoutId = setTimeout(function() {
            inputNickname = $this.val();

            if(inputNickname === '') {
                return;
            }

            $.ajax({
                type: 'get',
                url: `/api/v1/friends/find?nickname=${inputNickname}`,
                dataType: 'json',
                success: function(result) {
                    isFindFriend = result.hasNext;
                    let friendInfo = result.userInfo;

                    const modalView = $('#userList');
                    modalView.empty(); // 모달 뷰 비우기
                    const userContainer = $('<div class="user-container"></div>');

                    if(friendInfo <= 0) {
                        const message = "친구를 찾을 수 없습니다.";
                        modalView.append(`<span>${message}</span>`);
                    }
                    findFriendList(modalView, userContainer, friendInfo)

                    modalView.append(userContainer);
                },
                error: function(request, status, error) {
                    console.log(request);
                }
            })
        }, 300);
    });

    function findFriendList(modalView, userContainer, friends) {
        $.each(friends, function(index, friend) {
            const inner = `
                <div class="user">
                    <img src="/api/v1/friends/profile/${friend.profileUrl}" alt="">
                    <div class="user-info">
                        <input type="hidden" id="userId" value="${friend.userId}"/>
                        <span class="nickname">${friend.nickname}</span>
                        <div class="checkbox-area">
                            <input class="checkbox" type="checkbox" id="nick-check">
                        </div>
                    </div>
                </div>
            `;
            userContainer.append(inner);
        });
    }

    $('.modal-next-btn').click(function(){
        if(chooseFriends.length > 0) {
            inputChatRoomTitle();
            inputTitle();
        }else {
            alert('친구를 선택해주세요.');
        }
    });

    // 체크박스 변경 이벤트 감지
    $(document).on('change', 'input[type="checkbox"]', function() {
        const isChecked = $(this).is(':checked');
        const user = $(this).closest('.user');
        let checkboxId = user.find('#userId').val();
        let nickname = user.find('.nickname').text();
        let imgSrc = user.find('img').attr('src');

        const userObj = {
            id: checkboxId,
            nickname : nickname,
            imgSrc : imgSrc
        };
        if(isChecked) {
            chooseFriends.push(userObj);
        }else {
            for(let i=0; i < chooseFriends.length; i++) {
                if(chooseFriends[i].id === checkboxId) {
                    chooseFriends.splice(i, 1); // 배열에서 제거
                    break; // 루프 종료
                }
            }
        }
        console.log(chooseFriends); // 제거된 상태의 배열 확인
    });

    function inputChatRoomTitle() {
        // '다음' 버튼 클릭 시 보여줄 화면과 안보여줄 화면 세팅
        $('.choose-friend').css('display', 'none');
        $('.input-chatroom').css('display', 'block');

        const friendList = $('.friend-list');
        friendList.empty(); // friendList를 초기화
        const userContainer = $('<div>', { class: 'user-container' });

        $.each(chooseFriends, function(index, friend) {
            const inner = `
                <div class="user">
                    <img src="${friend.imgSrc}" alt="">
                    <div class="user-info">
                        <span class="nickname">${friend.nickname}</span>
                    </div>
                </div>`;

            userContainer.append(inner);
        });
        friendList.append(userContainer);
    }

    function inputTitle() {
        const modalView = $('#inputTitle');
        modalView.empty();

        const input = $('<input>', {class: 'title', type:'text', placeholder:'채팅방의 이름을 입력해주세요.'});
        modalView.append(input);
    }

    // 뒤로 버튼 눌렀을 때
    $('.modal-back-btn').click(function(){
        $('.choose-friend').css('display', 'block');
        $('.input-chatroom').css('display', 'none');
    });

    // 최종 확인 눌렀을 때
    $('#addChatroomBtn').click(function() {
        let chatroomTitle = $('#inputTitle .title').val();

        // title이 입력되지 않으면 실행되지 않도록 하기
        if(chatroomTitle === '') {
            alert('채팅방의 제목을 입력해주세요.');
            $('#inputTitle .title').focus();
            return;
        }

        // 배열에서 유저 ID만 추출하여 배열로 다시 만들기
        let userIdArray = [];
        for(let i = 0; i < chooseFriends.length; i++) {
            userIdArray.push(chooseFriends[i].id);
        }

        $.ajax({
            type: 'post',
            url: '/api/v1/chatrooms/create',
            contentType: 'application/json',
            data: JSON.stringify({
                title: chatroomTitle,
                userIds: userIdArray
            }),
            success: function(result) {
                console.log(result);
                if(result) {
//                    $('#addRoomModal').css('display', 'none');
                    location.href = "/chatrooms/"+result;
                }
            },
            error: function(request, status, error) {
                console.log(request);
            }
        });
    });
});