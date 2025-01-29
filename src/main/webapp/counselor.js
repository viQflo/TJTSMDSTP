document.addEventListener("DOMContentLoaded", function () {
    const counselors = [
        { name: "김상담", field: "심리 상담", rating: 4.9, img: "https://d2v80xjmx68n4w.cloudfront.net/gigs/6TKbY1680183666.jpg", new: false },
        { name: "이마음", field: "우울증 전문", rating: 4.7, img: "https://d2v80xjmx68n4w.cloudfront.net/gigs/6TKbY1680183666.jpg", new: true },
        { name: "박멘토", field: "직장 스트레스", rating: 4.8, img: "https://d2v80xjmx68n4w.cloudfront.net/gigs/6TKbY1680183666.jpg", new: false },
        { name: "태양", field: "PTSD 전문", rating: 4.4, img: "https://image-cdn.hypb.st/https%3A%2F%2Fkr.hypebeast.com%2Ffiles%2F2024%2F08%2F20%2Fjujutsu-kaisen-final-chapter-release-date-01.jpg?q=75&w=800&cbr=1&fit=max", new: true },
        { name: "승화", field: "직장 스트레스", rating: 4.7, img: "https://image-cdn.hypb.st/https%3A%2F%2Fkr.hypebeast.com%2Ffiles%2F2024%2F08%2F20%2Fjujutsu-kaisen-final-chapter-release-date-01.jpg?q=75&w=800&cbr=1&fit=max", new: true },
        { name: "소형", field: "심리 상담", rating: 4.1, img: "https://image-cdn.hypb.st/https%3A%2F%2Fkr.hypebeast.com%2Ffiles%2F2024%2F08%2F20%2Fjujutsu-kaisen-final-chapter-release-date-01.jpg?q=75&w=800&cbr=1&fit=max", new: false },
        { name: "수정", field: "직장 스트레스", rating: 4.5, img: "https://image-cdn.hypb.st/https%3A%2F%2Fkr.hypebeast.com%2Ffiles%2F2024%2F08%2F20%2Fjujutsu-kaisen-final-chapter-release-date-01.jpg?q=75&w=800&cbr=1&fit=max", new: true },
        { name: "지우", field: "PTSD 전문", rating: 4.5, img: "https://image-cdn.hypb.st/https%3A%2F%2Fkr.hypebeast.com%2Ffiles%2F2024%2F08%2F20%2Fjujutsu-kaisen-final-chapter-release-date-01.jpg?q=75&w=800&cbr=1&fit=max", new: false },
        { name: "선용", field: "심리 상담", rating: 4.7, img: "https://image-cdn.hypb.st/https%3A%2F%2Fkr.hypebeast.com%2Ffiles%2F2024%2F08%2F20%2Fjujutsu-kaisen-final-chapter-release-date-01.jpg?q=75&w=800&cbr=1&fit=max", new: true },
        { name: "은혜", field: "직장 스트레스", rating: 4.5, img: "https://image-cdn.hypb.st/https%3A%2F%2Fkr.hypebeast.com%2Ffiles%2F2024%2F08%2F20%2Fjujutsu-kaisen-final-chapter-release-date-01.jpg?q=75&w=800&cbr=1&fit=max", new: true },
        { name: "민정", field: "심리 상담", rating: 4.5, img: "https://image-cdn.hypb.st/https%3A%2F%2Fkr.hypebeast.com%2Ffiles%2F2024%2F08%2F20%2Fjujutsu-kaisen-final-chapter-release-date-01.jpg?q=75&w=800&cbr=1&fit=max", new: true },
        { name: "범석", field: "PTSD 전문", rating: 4.5, img: "https://image-cdn.hypb.st/https%3A%2F%2Fkr.hypebeast.com%2Ffiles%2F2024%2F08%2F20%2Fjujutsu-kaisen-final-chapter-release-date-01.jpg?q=75&w=800&cbr=1&fit=max", new: true }
    ];

    const counselorList = document.getElementById("counselor-list");
    const searchInput = document.getElementById("search");
    const filterSelect = document.getElementById("filter");

    // 🔹 예약 모달 관련 요소
    const modal = document.getElementById("reservationModal");
    const modalTitle = document.getElementById("modalTitle");
    const modalInfo = document.getElementById("modalInfo");
    const confirmReservation = document.getElementById("confirmReservation");
    const closeBtn = document.querySelector(".close");

    // 🔹 상담사 리스트 동적 생성
    function displayCounselors(filteredCounselors) {
        counselorList.innerHTML = ""; // 기존 내용 초기화
        filteredCounselors.forEach(counselor => {
            const card = document.createElement("div");
            card.classList.add("counselor-card");
            card.innerHTML = `
                <img src="${counselor.img}" alt="${counselor.name}">
                <h3>${counselor.name}</h3>
                <p>${counselor.field}</p>
                <p class="rating">⭐ ${counselor.rating}</p>
                <button class="book-btn">예약하기</button>
            `;

            // 예약 버튼 클릭 시 모달 열기
            card.querySelector(".book-btn").addEventListener("click", function () {
                openModal(counselor.name, counselor.field);
            });

            counselorList.appendChild(card);
        });
    }

    // 🔹 모달 열기 (예약 버튼을 눌렀을 때만 실행)
    function openModal(name, field) {
        modalTitle.textContent = `${name} 상담사와 예약 신청`;
        modalInfo.textContent = `분야: ${field}`;
        modal.style.display = "flex"; // 모달을 보이게 설정
    }

    // 🔹 모달 닫기 함수
    function closeModal() {
        modal.style.display = "none"; // 모달을 숨김
    }

    // 🔹 모달 닫기 버튼 클릭 시 닫기
    closeBtn.addEventListener("click", closeModal);

    // 🔹 모달 외부 클릭 시 닫기
    window.addEventListener("click", function (event) {
        if (event.target === modal) {
            closeModal();
        }
    });

    // 🔹 예약 완료 버튼 클릭 시 알림 표시 후 닫기
    confirmReservation.addEventListener("click", function () {
        alert("예약 신청 완료! 마이페이지에서 신정현활을 확인하세요!");
        closeModal();
    });

    // 🔹 검색 및 필터 기능
    function filterCounselors() {
        let filtered = counselors.filter(counselor =>
            counselor.name.includes(searchInput.value) || counselor.field.includes(searchInput.value)
        );

        if (filterSelect.value === "high-rating") {
            filtered = filtered.sort((a, b) => b.rating - a.rating);
        } else if(filterSelect.value === "low-rating") {
            filtered = filtered.sort((a, b) => a.rating - b.rating);}
        
        else if (filterSelect.value === "new") {
            filtered = filtered.filter(counselor => counselor.new);
        }

        displayCounselors(filtered);
    }

    searchInput.addEventListener("input", filterCounselors);
    filterSelect.addEventListener("change", filterCounselors);

    // 🔹 초기 상담사 리스트 출력
    displayCounselors(counselors);
});
