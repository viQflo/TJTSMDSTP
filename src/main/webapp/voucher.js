/*const vouchers = [
    { name: "사이트 1", url: "https://example1.com", image: "image1.jpg" },
    { name: "사이트 2", url: "https://example2.com", image: "image2.jpg" },
    { name: "사이트 3", url: "https://example3.com", image: "image3.jpg" }
];

function renderPortfolio() {
    const grid = document.getElementById("portfolioGrid");
    if (!grid) return; // 요소가 없으면 함수 종료

    grid.innerHTML = ""; // 기존 목록 초기화

    vouchers.forEach(item => {
        const card = document.createElement("div");
        card.className = "portfolio-item";
        card.innerHTML = `
            <img src="${item.image}" alt="${item.name}">
            <div class="content">
                <h3>${item.name}</h3>
                <a href="${item.url}" target="_blank">사이트 방문하기</a>
            </div>
        `;
        grid.appendChild(card);
    });
}

// 페이지 로드 시 바우처 목록 렌더링
document.addEventListener("DOMContentLoaded", renderPortfolio);
*/