const nextBtn = document.querySelector('.modal-next-btn');
const modalFriend = document.querySelector('.choose-friend');
const modalChatroom = document.querySelector('.input-chatroom');
const submitBtn = document.querySelector('.modal-submit-btn');
const checkboxes = document.querySelectorAll('.checkbox');
const backBtn = document.querySelector('.modal-back-btn');

nextBtn.addEventListener('click', function(){
    // localStorage에 데이터 저장
    const chooseFriends = [];
    checkboxes.forEach(checkbox => {
        if(checkbox.checked) {
            const userContainer = checkbox.closest('.choose-friend .user');
            const nickname = userContainer.querySelector('.nickname').textContent;
            const imgSrc = userContainer.querySelector('img').getAttribute('src');
            const checkboxId = checkbox.id;

            const userObj = {
                nickname : nickname,
                imgSrc : imgSrc,
                checkboxId, checkboxId
            };

            chooseFriends.push(userObj);
        }
    });

    // 로컬스토리지에 저장
    localStorage.setItem('chooseFriend', JSON.stringify(chooseFriends));
    console.log(chooseFriends);
    
    if(chooseFriends.length > 0) {
        inputChatRoomTitle();
    }else {
        alert('친구를 선택해주세요.');
    }
});

function inputChatRoomTitle() {
    // '다음' 버튼 클릭 시 보여줄 화면과 안보여줄 화면 세팅
    modalFriend.style.display = "none";
    modalChatroom.style.display = "block";

    const friendList = modalChatroom.querySelector('.friend-list');
    friendList.innerHTML = ''; // friendList를 초기화

    JSON.parse(localStorage.getItem('chooseFriend')).forEach(friend => {
        const userContainer = document.createElement('div');
        userContainer.className = 'user';
        
        userContainer.innerHTML = `
            <img src="${friend.imgSrc}" alt="">
            <div class="user-info">
                <span class="nickname">${friend.nickname}</span>
            </div>`;

        friendList.appendChild(userContainer);
    });
}

backBtn.addEventListener('click', function(){
    modalChatroom.style.display = "none";
    modalFriend.style.display = "block";
});