$(document).ready(function() {

    $(document).on('click', '#profileScroll .user-container', function() {
        const user = $(this).find('.user');
        const userId = user.find('.friend-id').val();

        const profileView = $('#profile-view-modal');
        const editBox = profileView.find('.edit-box');
        let appendBtn;
        profileView.css('display', 'block');
        editBox.empty();

        $.ajax({
            type: 'get',
            url: `/api/v1/profile/${userId}`,
            dataType: 'json',
            success: function(result) {
                if(result.backgroundUrl !== null) {
                    profileView.find('.modal-popup').css({
                        'background-color': 'bisque',
                        'background-image': `url("${result.backgroundUrl}")`,
                        'background-size': 'cover',
                        'background-position': 'center'
                    });
                }
                profileView.find('img').attr('src',result.profileUrl);

                profileView.find('#userId').val(result.userId);
                profileView.find('.nickname').text(result.nickname);
                profileView.find('.statusMessage').text(result.statusMessage);

                if(result.mine) { // 내 프로필을 열었을 때
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
            },
            error: function(request, status, error){
                console.log(request);
            }
        });
    });

    // "프로필 편집" 버튼 클릭시
    $(document).on('click', '#editProfile', function() {
        $('#profile-edit-modal').css('display', 'block');
    });

});