const portfolioItems = [
	{ id: 1, title: "어쩌구 마음 센터", category: "#직장인#학생", image: "https://metizen.co.kr/wp-content/uploads/2022/08/2208-%E1%84%80%E1%85%A9%E1%84%85%E1%85%A9-%E1%84%8B%E1%85%B2%E1%84%85%E1%85%A5%E1%86%B8-%E1%84%8B%E1%85%A7%E1%84%92%E1%85%A2%E1%86%BC-%E1%84%83%E1%85%B5%E1%84%8C%E1%85%A5%E1%84%90%E1%85%B33.jpg", link: "https://example.com/durigolf" },
	{ id: 2, title: "어쩌구 마음 센터", category: "#직장인#학생", image: "https://metizen.co.kr/wp-content/uploads/2022/08/2208-%E1%84%80%E1%85%A9%E1%84%85%E1%85%A9-%E1%84%8B%E1%85%B2%E1%84%85%E1%85%A5%E1%86%B8-%E1%84%8B%E1%85%A7%E1%84%92%E1%85%A2%E1%86%BC-%E1%84%83%E1%85%B5%E1%84%8C%E1%85%A5%E1%84%90%E1%85%B33.jpg", link: "https://example.com/durigolf" },
	{ id: 3, title: "어쩌구 마음 센터", category: "#직장인#학생", image: "https://metizen.co.kr/wp-content/uploads/2022/08/2208-%E1%84%80%E1%85%A9%E1%84%85%E1%85%A9-%E1%84%8B%E1%85%B2%E1%84%85%E1%85%A5%E1%86%B8-%E1%84%8B%E1%85%A7%E1%84%92%E1%85%A2%E1%86%BC-%E1%84%83%E1%85%B5%E1%84%8C%E1%85%A5%E1%84%90%E1%85%B33.jpg", link: "https://example.com/durigolf" },
	{ id: 4, title: "어쩌구 마음 센터", category: "#직장인#학생", image: "https://metizen.co.kr/wp-content/uploads/2022/08/2208-%E1%84%80%E1%85%A9%E1%84%85%E1%85%A9-%E1%84%8B%E1%85%B2%E1%84%85%E1%85%A5%E1%86%B8-%E1%84%8B%E1%85%A7%E1%84%92%E1%85%A2%E1%86%BC-%E1%84%83%E1%85%B5%E1%84%8C%E1%85%A5%E1%84%90%E1%85%B33.jpg", link: "https://example.com/durigolf" },
	{ id: 5, title: "어쩌구 마음 센터", category: "#직장인#학생", image: "https://metizen.co.kr/wp-content/uploads/2022/08/2208-%E1%84%80%E1%85%A9%E1%84%85%E1%85%A9-%E1%84%8B%E1%85%B2%E1%84%85%E1%85%A5%E1%86%B8-%E1%84%8B%E1%85%A7%E1%84%92%E1%85%A2%E1%86%BC-%E1%84%83%E1%85%B5%E1%84%8C%E1%85%A5%E1%84%90%E1%85%B33.jpg", link: "https://example.com/durigolf" },
	{ id: 6, title: "어쩌구 마음 센터", category: "#직장인#학생", image: "https://metizen.co.kr/wp-content/uploads/2022/08/2208-%E1%84%80%E1%85%A9%E1%84%85%E1%85%A9-%E1%84%8B%E1%85%B2%E1%84%85%E1%85%A5%E1%86%B8-%E1%84%8B%E1%85%A7%E1%84%92%E1%85%A2%E1%86%BC-%E1%84%83%E1%85%B5%E1%84%8C%E1%85%A5%E1%84%90%E1%85%B33.jpg", link: "https://example.com/durigolf" },
	{ id: 7, title: "어쩌구 마음 센터", category: "#직장인#학생", image: "https://metizen.co.kr/wp-content/uploads/2022/08/2208-%E1%84%80%E1%85%A9%E1%84%85%E1%85%A9-%E1%84%8B%E1%85%B2%E1%84%85%E1%85%A5%E1%86%B8-%E1%84%8B%E1%85%A7%E1%84%92%E1%85%A2%E1%86%BC-%E1%84%83%E1%85%B5%E1%84%8C%E1%85%A5%E1%84%90%E1%85%B33.jpg", link: "https://example.com/durigolf" },
	{ id: 8, title: "어쩌구 마음 센터", category: "#직장인#학생", image: "https://metizen.co.kr/wp-content/uploads/2022/08/2208-%E1%84%80%E1%85%A9%E1%84%85%E1%85%A9-%E1%84%8B%E1%85%B2%E1%84%85%E1%85%A5%E1%86%B8-%E1%84%8B%E1%85%A7%E1%84%92%E1%85%A2%E1%86%BC-%E1%84%83%E1%85%B5%E1%84%8C%E1%85%A5%E1%84%90%E1%85%B33.jpg", link: "https://example.com/durigolf" },
	{ id: 9, title: "어쩌구 마음 센터", category: "#직장인#학생", image: "https://metizen.co.kr/wp-content/uploads/2022/08/2208-%E1%84%80%E1%85%A9%E1%84%85%E1%85%A9-%E1%84%8B%E1%85%B2%E1%84%85%E1%85%A5%E1%86%B8-%E1%84%8B%E1%85%A7%E1%84%92%E1%85%A2%E1%86%BC-%E1%84%83%E1%85%B5%E1%84%8C%E1%85%A5%E1%84%90%E1%85%B33.jpg", link: "https://example.com/durigolf" },
	{ id: 10, title: "어쩌구 마음 센터", category: "#직장인#학생", image: "https://metizen.co.kr/wp-content/uploads/2022/08/2208-%E1%84%80%E1%85%A9%E1%84%85%E1%85%A9-%E1%84%8B%E1%85%B2%E1%84%85%E1%85%A5%E1%86%B8-%E1%84%8B%E1%85%A7%E1%84%92%E1%85%A2%E1%86%BC-%E1%84%83%E1%85%B5%E1%84%8C%E1%85%A5%E1%84%90%E1%85%B33.jpg", link: "https://example.com/durigolf" },
	{ id: 11, title: "어쩌구 마음 센터", category: "#직장인#학생", image: "https://metizen.co.kr/wp-content/uploads/2022/08/2208-%E1%84%80%E1%85%A9%E1%84%85%E1%85%A9-%E1%84%8B%E1%85%B2%E1%84%85%E1%85%A5%E1%86%B8-%E1%84%8B%E1%85%A7%E1%84%92%E1%85%A2%E1%86%BC-%E1%84%83%E1%85%B5%E1%84%8C%E1%85%A5%E1%84%90%E1%85%B33.jpg", link: "https://example.com/durigolf" },
	{ id: 12, title: "어쩌구 마음 센터", category: "#직장인#학생", image: "https://metizen.co.kr/wp-content/uploads/2022/08/2208-%E1%84%80%E1%85%A9%E1%84%85%E1%85%A9-%E1%84%8B%E1%85%B2%E1%84%85%E1%85%A5%E1%86%B8-%E1%84%8B%E1%85%A7%E1%84%92%E1%85%A2%E1%86%BC-%E1%84%83%E1%85%B5%E1%84%8C%E1%85%A5%E1%84%90%E1%85%B33.jpg", link: "https://example.com/durigolf" },

	// 추가 데이터...
];

function renderPortfolio() {
	const grid = document.getElementById("portfolioGrid");
	portfolioItems.forEach(item => {
		const card = document.createElement("div");
		card.className = "portfolio-item";
		card.innerHTML = `
            <img src="${item.image}" alt="${item.title}">
            <div class="content">
                <h3>${item.title}</h3>
                <p>${item.category}</p>
                <a href="${item.link}" target="_blank">사이트 방문하기</a>
            </div>
        `;
		grid.appendChild(card);
	});
}

document.getElementById("resetButton").addEventListener("click", () => {
	document.getElementById("query").value = "";
	document.getElementById("category").value = "분류체계";
	document.getElementById("serviceType").value = "서비스유형";
});

document.getElementById("searchButton").addEventListener("click", () => {
	const query = document.getElementById("query").value;
	const category = document.getElementById("category").value;
	const serviceType = document.getElementById("serviceType").value;
	alert(`검색어: ${query}\n카테고리: ${category}\n서비스유형: ${serviceType}`);
});

renderPortfolio();
