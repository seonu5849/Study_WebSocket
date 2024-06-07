$(document).ready(function() {

    let __userId;
    let __nickname;
    let __statusMessage;
    let __profileUrl;

    // 프로필 클릭시 프로필 정보 출력
    $(document).on('click', '#profileScroll .user-container', function() {
        const user = $(this).find('.user');
        const userId = user.find('.friend-id').val();

        clickUserProfile(userId);
    });
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

    // "프로필 편집" 버튼 클릭시
    $(document).on('click', '#editProfile', function() {
        const editModal = $('#profile-edit-modal');
        editModal.css('display', 'block');
        editModal.find('#userId').val(__userId);
        editModal.find('#nickname').val(__nickname);
        if(__statusMessage !== '') {
            editModal.find('#statusMessage').val(__statusMessage);
        }

        if(__profileUrl !== ''){
            editModal.find('#profileImg').attr('src', '/api/v1/friends/profile/'+__profileUrl);
        }

        // jQuery 커스텀 이벤트를 트리거하여 값을 전달
        const originalValues = {
            profileImage: editModal.find('#profileImg').attr('src'),
            nickname: editModal.find('#nickname').val(),
            statusMessage: editModal.find('#statusMessage').val()
        };
        $(document).trigger('originalValues', originalValues);
    });

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