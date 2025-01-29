document.addEventListener("DOMContentLoaded", function () {
    let user = {
        name: "홍길동",
        email: "hong@example.com",
        phone: "010-1234-5678",
        job: "홈프로텍터",
    };

    let isEditing = false;

    let counselRequests = [
        { date: "2024-02-20", type: "심리 상담", name: "홍길동", status: "대기 중" },
        { date: "2024-02-18", type: "심리 상담", name: "홍길동", status: "완료" },
        { date: "2024-02-15", type: "지원금 상담", name: "홍길동", status: "진행 중" },
    ];

    document.getElementById("name").value = user.name;
    document.getElementById("email").value = user.email;
    document.getElementById("phone").value = user.phone;

    const tableBody = document.getElementById("counselTableBody");
    counselRequests.forEach((request) => {
        let row = document.createElement("tr");
        row.innerHTML = `
            <td>${request.date}</td>
            <td>${request.type}</td>
            <td>${request.name}</td>
            <td class="status ${request.status}">${request.status}</td>
        `;
        tableBody.appendChild(row);
    });

    document.getElementById("editButton").addEventListener("click", function () {
        isEditing = !isEditing;
        document.getElementById("name").disabled = !isEditing;
        document.getElementById("email").disabled = !isEditing;
        document.getElementById("phone").disabled = !isEditing;
        this.textContent = isEditing ? "수정 완료" : "정보 수정";
    });

    document.getElementById("logoutButton").addEventListener("click", function () {
        alert("로그아웃 되었습니다.");
    });
});
