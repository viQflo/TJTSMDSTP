const postsPerPage = 10;  // 한 페이지당 10개씩 표시
let posts = [];
let filteredPosts = [];
let currentPage = 1;

// 초기 게시글 데이터를 가져오는 함수
async function fetchPosts() {
	try {
		const response = await fetch('http://localhost:8000/api/board');
		const data = await response.json();
		posts = data;
		filteredPosts = posts;
		renderPosts();
		renderPaginationButtons();
	} catch (error) {
		console.error('Error fetching posts:', error);
	}
}

// 게시글 목록을 렌더링하는 함수
function renderPosts() {
	const postsList = document.getElementById('posts-list');
	postsList.innerHTML = '';  // 기존 내용 제거

	const startIndex = (currentPage - 1) * postsPerPage;
	const endIndex = startIndex + postsPerPage;
	const paginatedPosts = filteredPosts.slice(startIndex, endIndex);

	paginatedPosts.forEach((post) => {
		const postElement = document.createElement('div');
		postElement.classList.add('post-item');
		postElement.innerHTML = `
            <img src="${post.thumbnail}" alt="Thumbnail" class="post-thumbnail" />
            <div class="post-info">
                <p class="post-title">${post.post_title}</p>
                <div class="post-meta">
                    <span>👤 ${post.email}</span>
                    <span>👁 ${post.post_views}</span>
                    <span>👍 ${post.post_likes}</span>
                    <span>📅 ${new Date(post.create_dt).toLocaleString('ko-KR', {
			year: 'numeric',
			month: 'long',
			day: 'numeric',
			hour: '2-digit',
			minute: '2-digit'
		})}</span>
                </div>
            </div>
        `;
		postElement.onclick = () => handlePostClick(post.post_idx);
		postsList.appendChild(postElement);
	});
}

// 페이지네이션 버튼을 렌더링하는 함수
function renderPaginationButtons() {
	const paginationButtons = document.getElementById('pagination-buttons');
	paginationButtons.innerHTML = '';  // 기존 내용 제거

	const totalPages = Math.ceil(filteredPosts.length / postsPerPage);

	const prevButton = document.createElement('button');
	prevButton.classList.add('pagination-button');
	prevButton.innerText = '이전';
	prevButton.disabled = currentPage === 1;
	prevButton.onclick = () => {
		if (currentPage > 1) {
			currentPage--;
			renderPosts();
			renderPaginationButtons();
		}
	};
	paginationButtons.appendChild(prevButton);

	for (let i = 1; i <= totalPages; i++) {
		const pageButton = document.createElement('button');
		pageButton.classList.add('pagination-button');
		pageButton.innerText = i;
		pageButton.onclick = () => {
			currentPage = i;
			renderPosts();
			renderPaginationButtons();
		};
		paginationButtons.appendChild(pageButton);
	}

	const nextButton = document.createElement('button');
	nextButton.classList.add('pagination-button');
	nextButton.innerText = '다음';
	nextButton.disabled = currentPage === totalPages;
	nextButton.onclick = () => {
		if (currentPage < totalPages) {
			currentPage++;
			renderPosts();
			renderPaginationButtons();
		}
	};
	paginationButtons.appendChild(nextButton);
}

// 게시글 클릭 시 상세 페이지로 이동하는 함수
function handlePostClick(postIdx) {
	window.location.href = `/post/${postIdx}`;
}

// 검색 처리 함수
function handleSearch() {
	const searchQuery = document.getElementById('search-query').value.toLowerCase();
	const searchFilter = document.getElementById('search-filter').value;

	filteredPosts = posts.filter((post) => {
		const title = post.post_title.toLowerCase();
		const content = post.post_content.toLowerCase();
		const author = post.email.toLowerCase();

		switch (search
