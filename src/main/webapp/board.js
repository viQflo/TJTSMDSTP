document.addEventListener("DOMContentLoaded", function () {
    const postsPerPage = 10;
    let posts = [];
    let currentPage = 1;
    let searchQuery = "";
    let searchFilter = "제목";

    // 게시글 데이터를 가져오는 함수
    async function fetchPosts() {
        try {
            const response = await fetch("http://localhost:8000/api/board");
            const data = await response.json();
            posts = data;
            renderPosts();
            renderPagination();
        } catch (error) {
            console.error("게시글을 가져오는 중 오류 발생:", error);
        }
    }

    // 검색 기능
    function handleSearch() {
        searchQuery = document.getElementById("search-query").value;
        searchFilter = document.getElementById("search-filter").value;
        currentPage = 1; // 검색 시 첫 페이지로 이동
        renderPosts();
        renderPagination();
    }

    document.getElementById("search-button").addEventListener("click", handleSearch);

    // 게시글을 화면에 렌더링하는 함수
    function renderPosts() {
        const postsList = document.getElementById("posts-list");
        postsList.innerHTML = "";

        const filteredPosts = posts.filter((post) => {
            const title = post.post_title || "";
            const content = post.post_content || "";
            const author = post.email || "";

            switch (searchFilter) {
                case "제목":
                    return title.includes(searchQuery);
                case "내용":
                    return content.includes(searchQuery);
                case "제목+내용":
                    return title.includes(searchQuery) || content.includes(searchQuery);
                case "작성자":
                    return author.includes(searchQuery);
                default:
                    return true;
            }
        });

        const startIndex = (currentPage - 1) * postsPerPage;
        const paginatedPosts = filteredPosts.slice(startIndex, startIndex + postsPerPage);

        paginatedPosts.forEach((post) => {
            const postItem = document.createElement("div");
            postItem.className = "post-item";
            postItem.innerHTML = `
                ${post.thumbnail ? `<img src="${post.thumbnail}" class="post-thumbnail">` : ""}
                <div class="post-info">
                    <div class="post-title">${post.post_title}</div>
                    <div class="post-meta">
                        👤 ${post.email} | 👁 ${post.post_views} | 👍 ${post.post_likes}
                    </div>
                </div>
            `;
            postItem.addEventListener("click", () => {
                window.location.href = `/post/${post.post_idx}`;
            });
            postsList.appendChild
