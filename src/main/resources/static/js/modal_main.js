const nextBtn = document.querySelector('.modal-next-btn');
const modalFrend = document.querySelector('.choose-frend');
const modalChatroom = document.querySelector('.input-chatroom');
const submitBtn = document.querySelector('.modal-submit-btn');
const checkboxes = document.querySelectorAll('.checkbox');
const backBtn = document.querySelector('.modal-back-btn');

nextBtn.addEventListener('click', function(){
    // localStorage에 데이터 저장
    const chooseFrends = [];
    checkboxes.forEach(checkbox => {
        if(checkbox.checked) {
            const userContainer = checkbox.closest('.choose-frend .user');
            const nickname = userContainer.querySelector('.nickname').textContent;
            const imgSrc = userContainer.querySelector('img').getAttribute('src');
            const checkboxId = checkbox.id;

            const userObj = {
                nickname : nickname,
                imgSrc : imgSrc,
                checkboxId, checkboxId
            };

            chooseFrends.push(userObj);
        }
    });

    // 로컬스토리지에 저장
    localStorage.setItem('chooseFrend', JSON.stringify(chooseFrends));
    console.log(chooseFrends);
    
    if(chooseFrends.length > 0) {
        inputChatRoomTitle();
    }else {
        alert('친구를 선택해주세요.');
    }
});

function inputChatRoomTitle() {
    // '다음' 버튼 클릭 시 보여줄 화면과 안보여줄 화면 세팅
    modalFrend.style.display = "none";
    modalChatroom.style.display = "block";

    const frendList = modalChatroom.querySelector('.frend-list');
    frendList.innerHTML = ''; // frendList를 초기화

    JSON.parse(localStorage.getItem('chooseFrend')).forEach(frend => {
        const userContainer = document.createElement('div');
        userContainer.className = 'user';
        
        userContainer.innerHTML = `
            <img src="${frend.imgSrc}" alt="">
            <div class="user-info">
                <span class="nickname">${frend.nickname}</span>
            </div>`;

        frendList.appendChild(userContainer);
    });
}

backBtn.addEventListener('click', function(){
    modalChatroom.style.display = "none";
    modalFrend.style.display = "block";
});