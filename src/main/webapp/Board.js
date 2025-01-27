document.addEventListener("DOMContentLoaded", function () {
    const postsPerPage = 10;
    let posts = [];
    let currentPage = 1;
    let searchQuery = '';
    let searchFilter = '제목';

    // 게시글 데이터를 가져오는 함수
    async function fetchPosts() {
        try {
            const response = await fetch('http://localhost:8000/api/board');
            const data = await response.json();
            posts = data;
            renderPosts();
            renderPagination();
        } catch (error) {
            console.error('게시글을 가져오는 중 오류 발생:', error);
        }
    }

    // 게시글을 화면에 렌더링하는 함수
    function renderPosts() {
        const postsList = document.getElementById("posts-list");
        postsList.innerHTML = '';

        const filteredPosts = posts.filter((post) => {
            const title = post.post_title || '';
            const content = post.post_content || '';
            const author = post.email || '';
            
            switch (searchFilter) {
                case '제목':
                    return title.includes(searchQuery);
                case '내용':
                    return content.includes(searchQuery);
                case '제목+내용':
                    return title.includes(searchQuery) || content.includes(searchQuery);
                case '작성자':
                    return author.includes(searchQuery);
                default:
                    return true;
            }
        });

        const startIndex = (currentPage - 1) * postsPerPage;
        const paginatedPosts = filteredPosts.slice(startIndex, startIndex + postsPerPage);

        paginatedPosts.forEach((post) => {
            const postItem = document.createElement('div');
            postItem.className = 'post-item';
            postItem.innerHTML = `
                <img src="${post.thumbnail}" alt="Thumbnail" class="post-thumbnail">
                <div class="post-info">
                    <div class="post-title">${post.post_title}</div>
                    <div class="post-meta">
                        👤 ${post.email} | 👁 ${post.post_views} | 👍 ${post.post_likes}
                    </div>
                </div>
            `;
            postItem.addEventListener('click', () => {
                window.location.href = `/post/${post.post_idx}`;
            });
            postsList.appendChild(postItem);
        });
    }

    // 페이지네이션 버튼을 렌더링하는 함수
    function renderPagination() {
        const pagination = document.getElementById("pagination");
        pagination.innerHTML = '';

        const filteredPosts = posts.filter((post) => {
            const title = post.post_title || '';
            const content = post.post_content || '';
            const author = post.email || '';
            
            switch (searchFilter) {
                case '제목':
                    return title.includes(searchQuery);
                case '내용':
                    return content.includes(searchQuery);
                case '제목+내용':
                    return title.includes(searchQuery) || content.includes(searchQuery);
                case '작성자':
                    return author.includes(searchQuery);
                default:
                    return true;
            }
        });

        const totalPages = Math.ceil(filteredPosts.length / postsPerPage);

        // 이전 버튼
        if (currentPage > 1) {
            const prevButton = document.createElement('button');
            prevButton.textContent = '이전';
            prevButton.addEventListener('click', () => {
                currentPage--;
                renderPosts();
                renderPagination();
            });
            pagination.appendChild(prevButton);
        }

        // 페이지 번호 버튼
        for (let i = 1; i <= totalPages; i++) {
            const pageButton = document.createElement('button');
            pageButton.textContent = i;
            if (i === currentPage) {
                pageButton.disabled = true;
            }
            pageButton.addEventListener('click', () => {
                currentPage = i;
                renderPosts();
                renderPagination();
            });
            pagination.appendChild(pageButton);
        }

        // 다음 버튼
        if (currentPage < totalPages) {
            const nextButton = document.createElement('button');
            nextButton.textContent = '다음';
            nextButton.addEventListener('click', () => {
                currentPage++;
                renderPosts();
                renderPagination();
            });
            pagination.appendChild(nextButton);
        }
    }

  
