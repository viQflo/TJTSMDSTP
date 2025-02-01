document.addEventListener("DOMContentLoaded", function () {
    let searchInput = document.getElementById("searchInput");
    let searchButton = document.getElementById("searchButton");

    function filterCards() {
        let searchText = searchInput.value.toLowerCase();
        let cards = document.querySelectorAll(".portfolio-item");

        cards.forEach(card => {
            let title = card.querySelector("h3").innerText.toLowerCase();
            if (title.includes(searchText)) {
                card.style.display = "block";
            } else {
                card.style.display = "none";
            }
        });
    }

    // 버튼 클릭 시 검색 실행
    searchButton.addEventListener("click", filterCards);

    // 엔터 키 입력 시 검색 실행
    searchInput.addEventListener("keypress", function (event) {
        if (event.key === "Enter") {
            filterCards();
        }
    });
});