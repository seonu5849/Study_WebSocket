$(document).ready(function() {
    let checkedUserIdArray = [];

    $('.close-button').click(function() {
        close($('#memberAddModal'));
    });

    $('.search-input').on('input', function() {
        let $this = $(this);
        const container = $('.user-list');

        let timeout = setTimeout(function() {
            let chatRoomId = jQuery.getChatRoomId();
            $.ajax({
               type: 'get',
               url: `/api/v1/chatrooms/${chatRoomId}/friends`,
               data: {
                  'nickname': $this.val()
               },
               success: function(result) {
                    if(result.userInfo.length === 0) {
                        setMessage(container, '일치하는 친구가 없습니다.');
                        return;
                    }
                    setInnerUserInfo(container, result.userInfo);
               },
               error: function(request, status, error) {
                    console.log(request);
               }
            });
        }, 300);
    });

    $(document).on('change', 'input[type="checkbox"]', function() {
        const isChecked = $(this).is(':checked');
        let userId = $(this).siblings('#userId').val();

        if(!isChecked) {
            removeUserIdArray(userId);
            return;
        }
        checkedUserIdArray.push(userId);
    });

    $('#inviteBtn').click(function() {
        let chatRoomId = jQuery.getChatRoomId();
        if(checkedUserIdArray.length <= 0 ) {
            alert('선택한 친구가 없습니다.');
            return;
        }

        $.ajax({
            type: 'post',
            url: `/api/v1/chatrooms/${chatRoomId}/invite`,
            contentType: 'application/json',
            dataType: 'json',
            data: JSON.stringify(checkedUserIdArray),
            success: function(result) {
                location.href = `/chatrooms/${result}`;
            },
            error: function(request, status, error) {
                console.log(request);
            }
        });
    });

    function close($object) {
        $object.hide();
    }

    const setInnerUserInfo = function(container, results) {
        container.empty(); // 컨테이너 비우기
        $.each(results, function(index, result) {
            const inner = `<div class="user-item">
                                       <input type="hidden" id="userId" value="${result.userId}">
                                       <div class="user-avatar">
                                           <img src="/api/v1/friends/profile/${result.profileUrl}" alt="">
                                       </div>
                                       <div class="user-name">${result.nickname}</div>
                                       <input type="checkbox">
                                   </div>`;
            container.append(inner);
        });
    }

    let setMessage = function(container, message) {
        container.empty();
        let span = $('<span>', {class: 'message'});
        span.text(`${message}`);
        container.append(span);
    }

    let removeUserIdArray = function(userId) {
        for(let i = 0; i < checkedUserIdArray.length; i++) {
            if(checkedUserIdArray[i] === userId) {
                checkedUserIdArray.splice(i, 1); // 배열에서 제거
                break;
            }
        }
    }
});