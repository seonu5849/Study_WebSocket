$(document).ready(function(){

    let duplicateStatus = false;
    $('#duplicate-btn').click(function(){
        const email = $('#email').val();

        if(email === ''){
            alert('아이디를 입력해주세요.');
            return;
        }

        $.ajax({
           type: 'post',
           url: '/register/duplicate',
           dataType: 'json', // 받아올 데이터 타입
           data: {
                'email': email
           },
           success: function(result){
                alert('사용가능한 아이디입니다.');
                duplicateStatus = true;
                console.log(result);
           },
           error: function(request, status, error){
                alert(request.responseJSON.message);
           }
        });
    });

    $('#submit').click(function(){
        const fields = {
            '아이디': $('#email'),
            '닉네임': $('#nickname'),
            '비밀번호': $('#password'),
            '비밀번호 확인': $('#password-check')
        };

        let isEmpty = false;

        $.each(fields, function(key, field) { // 반복을 통해 비어있는 필드가 있는지를 탐색
            if(field.val() === '') {
                alert(key + '을(를) 입력해주세요.');
                field.focus();
                isEmpty = true;
                return false;
            }
        });

        if(isEmpty) {
            return; // 비어있는 필드가 있는 경우 이후 코드를 실행하지 않음.
        }

        // 중복체크를 클릭하지 않았을 경우 경고 메시지
        if(!duplicateStatus) {
            alert('아이디 중복확인을 해주세요.');
            return;
        }

        // 비밀번호와 비밀번호 확인이 같은지를 확인
        if(fields['비밀번호'].val() !== fields['비밀번호 확인'].val()) {
            alert('비밀번호와 비밀번호 확인이 다릅니다.');
            fields['비밀번호'].focus();
            return;
        }

        // 모든 검사를 마친 후 서버로 데이터 전송
        $.ajax({
            type: 'post',
            url: '/register',
            contentType: 'application/json',
            dataType: 'json',
            data: JSON.stringify({
                'email': fields['아이디'].val(),
                'nickname': fields['닉네임'].val(),
                'password': fields['비밀번호'].val()
            }),
            success: function(result) {
                alert(result.message);
                location.href = '/login';
            },
            error: function(request, status, error) {
                alert(request.responseJSON.message);
            }
        });
    });

    $('#cancel').click(function() {
        location.href = "/login";
    });
})