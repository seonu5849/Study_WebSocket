$(document).ready(function() {
    const messageBox = $('#chatRoomMessage');
    let userInfo = JSON.parse(sessionStorage.getItem('userInfo'));
    let chatRoomId = getChatRoomId();
    console.log(userInfo);
    scrollToBotton(); // 채팅방 입장시 스크롤을 맨 아래로

    $('#chatroomBackBtn').click(function(){
        location.href = "/chatrooms/list";
    });

    // 1. 웹소켓 연결 설정
    const socket = new WebSocket('ws://localhost:8080/ws/chat?chatRoomId='+chatRoomId);

    // 2. 이벤트 핸들러 설정
    socket.onopen = function(event) {
        console.log('Connected to the WebSocket server');
    };

    socket.onmessage = function(event) {
        // 서버로부터 메시지를 받았을 때 화면에 표시
        const messageBody = $('.chatroom-message');
        console.log(event);
        console.log(event.data);
        let messageData = JSON.parse(event.data);
        let messageUser = messageData.userInfo;

        // 줄 바꿈 처리
        let messageContent = messageData.message.replace(/\n/g, '<br>');

        if(messageUser.id !== userInfo.id) {
            const otherMessage = `
                <div class="user-container">
                    <div class="other">
                        <img src="/api/v1/friends/profile/${messageUser.profileImg}" alt=""  id="profileImg">
                        <div class="box">
                            <span class="nickname">${messageUser.nickname}</span>
                            <div class="other-message-box">
                                <div class="balloon">
                                    <span class="comment">${messageContent}</span>
                                </div>
                                <span class="send-time">${getCurrentTime(messageData.sendTime)}</span>
                            </div>
                        </div>
                    </div>
                </div>
            `;
            messageBox.append(otherMessage);
            scrollToBotton(); // 채팅메시지가 오면 스크롤을 맨 아래로
        }
    };

    socket.onclose = function(event) {
        console.log(event.reason);
        if (event.reason === 'User not authenticated') {
            location.href = '/login';
        } else {
            console.log('Connection died');
        }
    };

    socket.onerror = function(error) {
        console.log('WebSocket error: ', error);
    };


    // 엔터 키 이벤트 감지
    $('#chatMessage').keydown(function(event) {
        if (event.keyCode === 13 && !event.shiftKey) {
            event.preventDefault(); // 폼 전송 방지
            // 버튼 클릭 이벤트 발생
            sendMessage();
        }else if(event.keyCode === 13 && event.shiftKey) {
            // 기존 동작 그대로
        }
    });
    $('#sendMessageBtn').click(function() {
        sendMessage();
    });

    function sendMessage() {
        let message = $('#chatMessage').val();

        let sendMessage = JSON.stringify({
            'messageType': 'TALK',
            'chatRoomId': chatRoomId,
            'userInfo': userInfo,
            'message': message,
            'sendTime': new Date()
        });

        let parseSendMessage = JSON.parse(sendMessage);
        console.log(parseSendMessage);

        // 메시지를 웹소켓을 통해 서버로 전송
        if(socket.readyState === WebSocket.OPEN) {
            socket.send(sendMessage);
            console.log('발송 성공');
            console.log(parseSendMessage.message);
            // 줄 바꿈 처리
            let messageContent = parseSendMessage.message.replace(/\n/g, '<br>');

            const myMessage = `
                <div class="user-container">
                    <div class="mine">
                        <span class="send-time">${getCurrentTime(parseSendMessage.sendTime)}</span>
                        <div class="balloon">
                            <span class="comment">${messageContent}</span>
                        </div>
                    </div>
                </div>
            `;

            messageBox.append(myMessage);
            $('#chatMessage').val('');
            scrollToBotton(); // 채팅메시지를 발송하면 스크롤을 맨 아래로
        }
    }

    $(document).on('click', '.other #profileImg', function() {
        let userId = $(this).siblings('#userId').val(); // siblings = 형제 요소 중에서 찾기
        jQuery.clickUserProfile(userId);
    });

    function getChatRoomId() {
        // 현재 URL을 가져오기
        let currentUrl = window.location.href;

        // URL 객체 생성
        let url = new URL(currentUrl);

        // pathname에서 pathVariable을 추출
        let path = url.pathname;
        // 경로를 '/'로 분리하여 배열로 변환
        let pathParts = path.split('/');
        // pathParts 배열에서 두 번째 요소를 아이디로 사용
        return pathParts[2];
    }

    // 12시간 형식 (오전/오후)
    function getCurrentTime(inputTime) {
        const date = new Date(inputTime)
        let hours = date.getHours();
        const minutes = date.getMinutes().toString().padStart(2, '0');
        const ampm = hours >= 12 ? '오후' : '오전';
        hours = hours % 12;
        hours = hours ? hours : 12; // 0을 12로 변환
        const hoursStr = hours.toString().padStart(2, '0');
        return `${ampm} ${hoursStr}:${minutes}`;
    }

    // 스크롤을 맨 밑으로 보내는 함수
    function scrollToBotton() {
        const chatContainer = $('.chatroom-message');
        let messageHeight = chatContainer.prop('scrollHeight') - chatContainer.prop('clientHeight');
        chatContainer.scrollTop(messageHeight);
    }
});