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
                    <img src="/api/v1/friends/profile/${friend.profileUrl}" alt="">
                    <span class="nickname">${friend.nickname}</span>
                </div>
            `;
            userContainer.append(inner);
        });
    }

    // 친구 검색 후 프로필 사진 클릭 이벤트
    $(document).on('click', '#friend-search-modal .user', function() {
        let $this = $(this);
        let userId = $this.find('.friend-id').val();
        console.log(userId);
        clickUserProfile(userId);
    });

    // 추후 export, import를 배우면 없앨 예정
    function clickUserProfile(userId) {
        const profileView = $('#profile-view-modal');
        const editBox = profileView.find('.edit-box');

        profileView.css('display', 'block');
        editBox.empty();

        $.ajax({
            type: 'get',
            url: `/api/v1/profile/${userId}`,
            dataType: 'json',
            success: function(result) {
                __userId = result.userId;
                __nickname = result.nickname;
                __statusMessage = result.statusMessage;
                __profileUrl = result.profileUrl;

                profileViewModal(profileView, editBox, result);
            },
            error: function(request, status, error){
                console.log(request);
            }
        });
    }

    function profileViewModal(profileView, editBox, user) {
        let appendBtn;
        if(user.backgroundUrl !== null) {
            profileView.find('.modal-popup').css({
                'background-color': 'bisque',
                'background-image': `url("${result.backgroundUrl}")`,
                'background-size': 'cover',
                'background-position': 'center'
            });
        }
        profileView.find('img').attr('src','/api/v1/friends/profile/'+user.profileUrl);

        profileView.find('#userId').val(user.userId);
        profileView.find('.nickname').text(user.nickname);
        profileView.find('.statusMessage').text(user.statusMessage);

        if(user.mine) { // 내 프로필을 열었을 때
            appendBtn = `
                <button type="button" id="editProfile">
                    <i class="fa-solid fa-user-pen fa-xl" style="color: #ffffff;"></i>
                    <span class="button-text">프로필 편집</span>
                </button>
            `;
        } else { // 다른 사람 프로필을 열었을 때
            appendBtn = `
                <button type="button" id="addChatroom">
                    <i class="fa-solid fa-comments fa-xl" style="color: #ffffff;"></i>
                    <span class="button-text">1:1 채팅</span>
                </button>
            `;
        }
        editBox.append(appendBtn);
    }

});