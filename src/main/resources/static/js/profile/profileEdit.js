$(document).ready(function(){

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
            }
            reader.readAsDataURL(file); // 파일을 읽은 후 성공적으로 읽으면 onload 이벤트가 실행
            // 실행 순서 : FileReader 객체 생성 -> onload 핸들러 설정 -> readAsDataURL 메소드를 통해 파일을 읽기 -> 성공하면 onload 핸들러 실행
        }
    });

});