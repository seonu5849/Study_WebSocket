$(document).ready(function() {

    $('#userBtn').click(function() {
        location.href = '/profile';
    });

    $('#chatroomId').click(function() {
        location.href = '/chatrooms/list';
    });
    $('#logoutBtn').click(function() {
        location.href = '/logout';
    });
});