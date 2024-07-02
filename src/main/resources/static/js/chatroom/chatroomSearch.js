$(document).ready(function() {

    $('#searchBar input[type=text]').on('input', function() {
        let $this = $(this);

        let time = setTimeout(function() {
            let title = $this.val();
            $.searchFunc.search(title);
        }, 500);
    })

    // jquery로 함수 모듈화
    $.searchFunc = {
        search: function(title) {
            $.ajax({
                type: 'get',
                url: '/api/v1/chatrooms/search',
                dataType: 'json',
                data: {
                    'title': title
                },
                success: function(result) {
                    console.log(result);
                    const container = $('.chatroom-container');
                    container.empty();
                    $.searchFunc.addChatroomDiv(container, result);
                },
                error: function(request, status, error) {
                    console.log(request);
                }
            });
        },
        addChatroomDiv: function(container, chatrooms){
            $.each(chatrooms, function(index, chatroom) {
                const inner = `<div class="wrapper">
                    <div class="chat">
                        <img src="/api/v1/friends/profile/${chatroom.profileImg}" alt="" class="chat-profile">
                        <div class="chat-info">
                            <div class="chat-top">
                                <div>
                                    <input type="hidden" name="chatroomId" id="chatRoomId" value="${chatroom.chatRoomId}">
                                    <span class="title">${chatroom.title}</span>
                                    <span class="person-count">${chatroom.personCount}</span>
                                </div>
                                <span class="date">${chatroom.lastCommentDate}</span>
                            </div>
                            <span class="comment">${chatroom.lastComment}</span>
                        </div>
                    </div>
                </div>`;
                container.append(inner);
            });
        }
    }
});