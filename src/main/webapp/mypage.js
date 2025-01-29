document.addEventListener("DOMContentLoaded", function () {
    let isEditing = false;
    const editBtn = document.getElementById("edit-btn");
    const logoutBtn = document.getElementById("logout-btn");
    const changePasswordBtn = document.getElementById("change-password-btn");

    // 상담 신청 리스트
    const counselList = [
        { date: "2024-02-20", type: "심리 상담", name: "홍길동", status: "대기 중" },
        { date: "2024-02-18", type: "심리 상담", name: "홍길동", status: "완료" },
        { date: "2024-02-15", type: "지원금 상담", name: "홍길동", status: "진행 중" }
    ];

    // 상담 목록 동적 추가
    const tableBody = document.getElementById("counsel-list");
    counselList.forEach(item => {
        const row = document.createElement("tr");
        row.innerHTML = `
            <td>${item.date}</td>
            <td>${item.type}</td>
            <td>${item.name}</td>
            <td class="${getStatusClass(item.status)}">${item.status}</td>
        `;
        tableBody.appendChild(row);
    });

    function getStatusClass(status) {
        if (status === "대기 중") return "status-pending";
        if (status === "진행 중") return "status-progress";
        if (status === "완료") return "status-complete";
        return "";
    }

    // 정보 수정 버튼 클릭 시
    editBtn.addEventListener("click", function () {
        isEditing = !isEditing;
        document.getElementById("name").disabled = !isEditing;
        document.getElementById("email").disabled = !isEditing;
        document.getElementById("phone").disabled = !isEditing;
        editBtn.textContent = isEditing ? "수정 완료" : "정보 수정";
    });

    // 로그아웃 버튼 클릭 시
    logoutBtn.addEventListener("click", function () {
        alert("로그아웃 되었습니다.");
    });

    // 비밀번호 변경 버튼 클릭 시
    changePasswordBtn.addEventListener("click", function () {
        const newPassword = document.getElementById("new-password").value;
        if (newPassword.trim() === "") {
            alert("새 비밀번호를 입력하세요.");
        } else {
            alert(`새 비밀번호: ${newPassword}`);
            document.getElementById("new-password").value = "";
        }
    });
});
