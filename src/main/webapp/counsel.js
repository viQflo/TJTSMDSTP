document.addEventListener("DOMContentLoaded", function () {
    const regions = [
        "서울", "부산", "대구", "인천", "광주", "대전", "울산",
        "세종", "경기", "강원", "충북", "충남", "전북",
        "전남", "경북", "경남", "제주"
    ];

    const symptoms = [
        "우울감", "불안", "공황", "스트레스", "불면증",
        "강박", "충동 조절 문제", "감정 조절 어려움", "집중력 부족", "기억력 감퇴",
        "사회적 고립", "대인관계 어려움", "트라우마", "외상 후 스트레스", "자존감 저하"
    ];

    const regionList = document.getElementById("regionList");
    const symptomList = document.getElementById("symptomList");
    const toggleRegionButton = document.getElementById("toggleRegionButton");
    const toggleSymptomsButton = document.getElementById("toggleSymptomsButton");
    const findButton = document.querySelector(".find-button");

    let isRegionOpen = true;
    let isSymptomsOpen = true;

    // 🔹 체크박스 목록 생성
    function createCheckboxList(items, container) {
        items.forEach((item) => {
            const label = document.createElement("label");
            label.innerHTML = `<input type="checkbox" value="${item}"> ${item}`;
            container.appendChild(label);
        });
    }

    createCheckboxList(regions, regionList);
    createCheckboxList(symptoms, symptomList);

    // 🔹 지역 목록 토글
    toggleRegionButton.addEventListener("click", function () {
        isRegionOpen = !isRegionOpen;
        regionList.style.display = isRegionOpen ? "grid" : "none";
        toggleRegionButton.textContent = isRegionOpen ? "지역 목록 닫기 -" : "지역 목록 열기 +";
    });

    // 🔹 증상 목록 토글
    toggleSymptomsButton.addEventListener("click", function () {
        isSymptomsOpen = !isSymptomsOpen;
        symptomList.style.display = isSymptomsOpen ? "grid" : "none";
        toggleSymptomsButton.textContent = isSymptomsOpen ? "증상 목록 닫기 -" : "증상 목록 열기 +";
    });

    // 🔹 상담사 찾기 버튼 클릭 이벤트 (counselor.jsp로 이동)
    findButton.addEventListener("click", function () {
        const selectedRegions = Array.from(regionList.querySelectorAll("input:checked"))
            .map(input => input.value);
        const selectedSymptoms = Array.from(symptomList.querySelectorAll("input:checked"))
            .map(input => input.value);

        if (selectedRegions.length === 0 || selectedSymptoms.length === 0) {
            alert("최소 한 개의 지역과 증상을 선택하세요!");
            return;
        }

        // 선택한 지역과 증상을 URL 파라미터로 전달 (counselor.jsp로 변경)
        const queryParams = new URLSearchParams({
            location: selectedRegions.join(","), // 여러 개 선택 가능
            cs_charge: selectedSymptoms.join(",") // 여러 개 선택 가능
        }).toString();
		console.log(queryParams);
        window.location.href = `counselor.jsp?${queryParams}`;
    });
});
