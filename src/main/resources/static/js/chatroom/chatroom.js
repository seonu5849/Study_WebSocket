$(document).ready(function() {

    $(document).on('click', '.chat', function() {
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

    $('#searchRoomBtn').click(function() {
        const $searchBar = $('#searchBar');
        const $content = $('.chatroom');

        // 화면에 보여지지 않는다면 보여지도록
        if($searchBar.css('display') !== 'flex') {
            searchFunc.searchBarOpen($searchBar, $content);
            return;
        }
        searchFunc.searchBarClose($searchBar, $content);

    });

    const searchFunc = {
        searchBarOpen: function($searchBar, $content) {
            view($searchBar);
            $content.css({
                'margin-top': $searchBar.outerHeight() + 'px',
                'height': $content.outerHeight() - $searchBar.outerHeight() + 'px'
            });
        },
        searchBarClose: function($searchBar, $content) {
            close($searchBar);
            $content.css({
                'margin-top': '0',
                'height': '550px'
            });
            $.searchFunc.search('');
        }
    };

    function view($obj) {
        $obj.css('display', 'flex');
    }
    function close($obj) {
        $obj.hide();
    }
});