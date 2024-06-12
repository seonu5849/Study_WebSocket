$(document).ready(function() {

    $('.chat').click(function() {
        let $this = $(this);
        let chatRoomId = $this.find('#chatRoomId').val();

        $.ajax({
            type: 'get',
            url: '/api/v1/userInfo',
            dataType: 'json',
            success: function(result) {
                sessionStorage.setItem('userInfo', JSON.stringify(result));
                console.log(result);
                location.href = `/chatrooms/${chatRoomId}`;
            },
            error: function(request, status, error) {
                console.log(request);
            }
        });

    });

});