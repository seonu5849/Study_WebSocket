//$(document).ready(function(){
//
//    $('#submit').click(function(){
//        const form = $('#form');
//        const fields = {
//            '아이디': $('#email'),
//            '비밀번호': $('#password')
//        };
//
//        // 반복을 통해 비어있는 input을 찾고 비어있는 곳으로 안내하는 로직
//        let isEmpty = false;
//        $.each(fields, function(key, field) {
//            if(field.val() === '') {
//                alert(key+'을(를) 입력해주세요.');
//                field.focus();
//                isEmpty = true;
//                return false;
//            }
//        });
//
//        // 비어있는 input이 있다면 이후 로직은 실행되지 않도록 하기
//        if(isEmpty) {
//            return;
//        }
//
//        console.log('here1');
//
//        form.attr('action', '/login');
//        form.attr('method', 'post');
//        form.submit();
//        console.log('here2');
//
////        $.ajax({
////            type: 'post',
////            url: '/login',
////            dataType: 'json',
////            data: {
////                'email': fields['아이디'].val(),
////                'password': fields['비밀번호'].val()
////            },
////            success: function(result) {
////                console.log(result);
////            },
////            error: function(request, status, error) {
////                console.log(request);
////            }
////        });
//    });
//
//});