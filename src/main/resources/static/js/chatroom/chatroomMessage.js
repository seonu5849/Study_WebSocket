$(document).ready(function() {
    let userId;

    // 1. 웹소켓 연결 설정
    const socket = new WebSocket('ws://localhost:8080/ws/chat');

    // 2. 이벤트 핸들러 설정
    socket.onopen = function(event) {
        console.log('Connected to the WebSocket server');
    };

    socket.onmessage = function(event) {
        // 서버로부터 메시지를 받았을 때 화면에 표시
        const messageBody = $('.chatroom-message');
        console.log(event);
        console.log(event.data);


//        $('#chatWindow').append('<div>' + event.data + '</div>');
    };

    socket.onclose = function(event) {
        if (event.wasClean) {
            console.log(`Connection closed cleanly, code=${event.code}, reason=${event.reason}`);
        } else {
            console.log('Connection died');
        }
    };

    socket.onerror = function(error) {
        console.log('WebSocket error: ', error);
    };

    $('#sendMessageBtn').click(function() {
        let message = $('#chatMessage').val();
        userId = 1;
//        let sendMessage = JSON.stringify({userId, message});
        let sendMessage = JSON.stringify({
            'messageType': 'TALK',
            'chatRoomId': '123-123-123',
            'senderId': 1,
            'message': message
        });

        // 메시지를 웹소켓을 통해 서버로 전송
        if(socket.readyState === WebSocket.OPEN) {
            socket.send(sendMessage);
            console.log('발송 성공');
        }
    });

});