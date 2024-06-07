$(document).ready(function(){
    // 기본값
    let originalValues = {};
    $(document).on('originalValues', function(e, values) {
        originalValues = values;
        console.log(originalValues);
    });

    // editView 의 닫기 버튼
    $('#editViewCloseBtn').click(function() {
        $('#profile-edit-modal').css('display', 'none');
    });

    $('#uploadBtn').click(function() {
        $('#profileInput').click();
    });

    $('#profileInput').change(function(e) {
        let file = e.target.files[0]; // 선택한 파일을 e.target.files[0]으로 가져옴.
        console.log(file);
        if(file) {
            let reader = new FileReader(); // 선택한 파일을 FileReader를 사용해서 읽기 위해 객체 생성
            reader.onload = function(e) {
                $('#profileImg').attr('src', e.target.result); // 파일을 읽은 후 img 태그의 src 속성을 업데이트해서 미리 보기
                checkChanges(); // 이미지가 변경된 것인지를 판단, 변경됐다면 확인버튼 활성화
            }
            reader.readAsDataURL(file); // 파일을 읽은 후 성공적으로 읽으면 onload 이벤트가 실행
            // 실행 순서 : FileReader 객체 생성 -> onload 핸들러 설정 -> readAsDataURL 메소드를 통해 파일을 읽기 -> 성공하면 onload 핸들러 실행
        }
    });

    $('#nickname, #statusMessage').on('input', function() {
        checkChanges();
    });

    $('#editBtn').click(function(e) {
        e.preventDefault(); // 폼의 기본 제출 동작을 막습니다.

        let formData = new FormData();
        let userId = $('#userId').val();
        let fileInput = $('#profileInput')[0].files[0];
        let nickname = $('#nickname').val();
        let statusMessage = $('#statusMessage').val();

        // JSON 데이터를 문자열로 변환하여 FormData에 추가
        formData.append('request', new Blob([JSON.stringify({
                                                         'nickname': nickname,
                                                         'statusMessage': statusMessage
                                                     })], {type:'application/json'}));
        formData.append('profileImg', fileInput);
        console.log(fileInput);


        $.ajax({
            type:'patch',
            url: `/api/v1/profile/${userId}/edit`,
            data: formData,
            processData: false, // 파일 데이터를 문자열로 변환하지 않도록 설정
            contentType: false, // 기본적으로 'application/x-www-form-urlencoded'으로 설정되는 것을 방지
            success: function(result) {
                alert('수정이 완료되었습니다.');
                location.href = "/profile";
            },
            error: function(request, status, error) {
                console.log(request);
            }
        });
    });

    // 파일, 닉네임, 상태메시지가 변경될 경우에만 확인버튼이 활성화 되도록 하기
    function checkChanges() {
        let currentValues = {
            profileImage: $('#profileImg').attr('src'),
            nickname: $('#nickname').val(),
            statusMessage: $('#statusMessage').val()
        };

        if(currentValues.profileImage !== originalValues.profileImage ||
            currentValues.nickname !== originalValues.nickname ||
            currentValues.statusMessage !== originalValues.statusMessage) {
                $('#editBtn').css({
                    'background-color': 'rgba(255, 228, 196, 0.8)',
                    'color': 'rgba(0, 0, 0, 1)'
                });
                $('#editBtn').prop('disabled', false);
            } else {
                $('#editBtn').css({
                    'background-color': 'rgba(200, 200, 200, 0.1)',
                    'color': 'rgba(200, 200, 200, 0.6)'
                });
                $('#editBtn').prop('disabled', true);
            }
    }

});