
const modal = document.querySelector('.modal');
const modalOpen = document.querySelector('.modal-btn');
const modalClose = document.querySelector('.close-btn');

// button 태그 사용시 type="button" 을 해주지 않으면 js에서 인식을 못한다.

// 열기 버튼을 눌렀을 때 모달팝업이 열림
modalOpen.addEventListener('click', function(){
    modal.style.display = 'block';

    const modalMain = document.querySelector('.modal-main');
    if(modalMain.querySelector('.choose-frend')) {
        modalFrend.style.display = "block";
        modalChatroom.style.display = "none";

        checkboxes.forEach(checkbox => {
            checkbox.checked = false;
        })
    }
});
// 닫기 버튼을 눌렀을 때 모달팝업이 닫힘
modalClose.addEventListener('click', function(){
    modal.style.display = 'none';
});
