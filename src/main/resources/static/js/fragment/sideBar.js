$(document).ready(function() {

    $('#userBtn').click(function() {
        location.href = '/profile';
    });

    $('#chatroomId').click(function() {
        location.href = '/chatroom';
    });
    $('#logoutBtn').click(function() {
        location.href = '/logout';
    });
});