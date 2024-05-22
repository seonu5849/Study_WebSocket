const chatroom = document.querySelector('.chat-room');
const chooseFrend = document.querySelector('.choose-frend .modal-view');

for(let i=0; i<10; i++) {
    // user-container 요소 생성
    const userContainer = document.createElement('div');
    userContainer.className = 'user-container';

    // user 요소 생성
    userContainer.innerHTML = `
        <div class="user">
            <img src="d3BPj1706541860.jpg" alt="">
            <div class="user-info">
                <div class="user-top">
                    <div>
                        <span class="nickname">닉네임 ${i + 1}</span>
                        <span class="person-count">${Math.floor(Math.random() * 10)}</span>
                    </div>
                    <span class="date">${new Date().toLocaleDateString()}</span>
                </div>
                <span class="comment">마지막내용입니다아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아</span>
            </div>
        </div>
    `;

    // chat-room에 user-container 추가
    chatroom.appendChild(userContainer);
}

for(let i=0; i<10; i++) {
    const userContainer = document.createElement('div');
    userContainer.className = 'user';

    userContainer.innerHTML = `<img src="d3BPj1706541860.jpg" alt="">
    <div class="user-info">
        <span class="nickname">닉네임${i+1}</span>
        <div class="checkbox-area">
            <input class="checkbox" type="checkbox" name="" id="nick-check${i+1}">
            <label for="nick-check${i+1}"></label>
        </div>
    </div>`

    chooseFrend.appendChild(userContainer);
}