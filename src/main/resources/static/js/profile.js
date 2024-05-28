$(document).ready(function() {
    let timeoutId;

    $('#searchFriendInput').on('input', function() {
        let $this = $(this);

        clearTimeout(timeoutId); // 이전 타이머 제거
        timeoutId = setTimeout(function() {
            let userInput = $this.val();
            console.log(userInput);
            let cursorId = 0; // 커서 아이디 초기화
            let url = `/api/v1/friend?cursorId=${cursorId}&email=${userInput}`;

            $.ajax({
                type: 'get',
                url: url,
                dataType: 'json',
                success: function(result) {
                    const $view = $('.modal-view');
                    $view.empty();
                    if(result.length <= 0) {
                        const message = "아이디를 검색하면 친구를 찾을 수 있습니다.";
                        $view.append(`<span>${message}</span>`);
                    } else {
                        $.each(result, function(key, user) {
                            const userContainer = $('<div class="user-container"></div>');

                            const userHtml = `
                                <div class="user">
                                    <img src=${user.profileUrl} alt="">
                                    <div class="user-info">
                                        <span class="nickname">${user.nickname}</span>
                                    </div>
                                </div>
                            `;

                            userContainer.append(userHtml); // 사용자 HTML을 userContainer에 추가
                            $view.append(userContainer); // userContainer를 view에 추가
                        });
                    }
                },
                error: function(request, status, error) {
                    console.log(request);
                }
            });
        }, 300); // 300ms 후에 요청을 보내도록 설정
    });
});