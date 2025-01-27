document.addEventListener('DOMContentLoaded', () => {
<<<<<<< HEAD
    const postsList = document.getElementById('posts-list');
    const paginationButtons = document.getElementById('pagination-buttons');
    const searchQueryInput = document.getElementById('search-query');
    const searchFilterSelect = document.getElementById('search-filter');
    const writePostButton = document.getElementById('write-post-button');
    
    const postsPerPage = 10;
    let posts = [];
    let filteredPosts = [];
    let currentPage = 1;
=======
	const postsList = document.getElementById('posts-list');
	const paginationButtons = document.getElementById('pagination-buttons');
	const searchQueryInput = document.getElementById('search-query');
	const searchFilterSelect = document.getElementById('search-filter');
	const writePostButton = document.getElementById('write-post-button');
>>>>>>> branch 'master' of https://github.com/viQflo/TJTSMDSTP

<<<<<<< HEAD
    const fetchPosts = async () => {
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
    };

    const renderPosts = () => {
        postsList.innerHTML = '';
        const startIndex = (currentPage - 1) * postsPerPage;
        const paginatedPosts = filteredPosts.slice(startIndex, startIndex + postsPerPage);

        paginatedPosts.forEach(post => {
            const postElement = document.createElement('div');
            postElement.classList.add('post');
            postElement.innerHTML = `
                <div>
                    <h2>${post.post_title}</h2>
                    <p>작성자: ${post.email}</p>
                    <p>조회수: ${post.post_views}</p>
                    <p>작성일: ${new Date(post.create_dt).toLocaleString()}</p>
                </div>
            `;
            postsList.appendChild(postElement);
        });
    };

    const renderPaginationButtons = () => {
        paginationButtons.innerHTML = '';
        const totalPages = Math.ceil(filteredPosts.length / postsPerPage);

        for (let i = 1; i <= totalPages; i++) {
            const button = document.createElement('button');
            button.textContent = i;
            button.classList.add('pagination-button');
            button.addEventListener('click', () => {
                currentPage = i;
                renderPosts();
            });
            paginationButtons.appendChild(button);
        }
    };

    const handleSearch = () => {
        const searchQuery = searchQueryInput.value.toLowerCase();
        const searchFilter = searchFilterSelect.value;
        
        filteredPosts = posts.filter(post => {
            const title = post.post_title.toLowerCase();
            const content = post.post_content.toLowerCase();
            const author = post.email.toLowerCase();

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

        currentPage = 1;
        renderPosts();
        renderPaginationButtons();
    };
=======
	const postsPerPage = 10;
	let posts = [];
	let filteredPosts = [];
	let currentPage = 1;
>>>>>>> branch 'master' of https://github.com/viQflo/TJTSMDSTP

<<<<<<< HEAD
    const handleWritePostButtonClick = () => {
        const isLoggedIn = localStorage.getItem('token');
        if (!isLoggedIn) {
            alert('로그인이 필요한 서비스입니다.');
            window.location.href = '/login.html';
        } else {
            window.location.href = '/dopost.html';
        }
    };
=======
	const fetchPosts = async () => {
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
	};

	const renderPosts = () => {
		postsList.innerHTML = '';
		const startIndex = (currentPage - 1) * postsPerPage;
		const paginatedPosts = filteredPosts.slice(startIndex, startIndex + postsPerPage);

		paginatedPosts.forEach(post => {
			const postElement = document.createElement('div');
			postElement.classList.add('post');
			postElement.innerHTML = `
                <div>
                    <h2>${post.post_title}</h2>
                    <p>작성자: ${post.email}</p>
                    <p>조회수: ${post.post_views}</p>
                    <p>작성일: ${new Date(post.create_dt).toLocaleString()}</p>
                </div>
            `;
			postsList.appendChild(postElement);
		});
	};

	const renderPaginationButtons = () => {
		paginationButtons.innerHTML = '';
		const totalPages = Math.ceil(filteredPosts.length / postsPerPage);

		for (let i = 1; i <= totalPages; i++) {
			const button = document.createElement('button');
			button.textContent = i;
			button.classList.add('pagination-button');
			button.addEventListener('click', () => {
				currentPage = i;
				renderPosts();
			});
			paginationButtons.appendChild(button);
		}
	};

	const handleSearch = () => {
		const searchQuery = searchQueryInput.value.toLowerCase();
		const searchFilter = searchFilterSelect.value;
>>>>>>> branch 'master' of https://github.com/viQflo/TJTSMDSTP

<<<<<<< HEAD
    searchQueryInput.addEventListener('input', handleSearch);
    searchFilterSelect.addEventListener('change', handleSearch);
    writePostButton.addEventListener('click', handleWritePostButtonClick);
=======
		filteredPosts = posts.filter(post => {
			const title = post.post_title.toLowerCase();
			const content = post.post_content.toLowerCase();
			const author = post.email.toLowerCase();
>>>>>>> branch 'master' of https://github.com/viQflo/TJTSMDSTP

<<<<<<< HEAD
    fetchPosts();
=======
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

		currentPage = 1;
		renderPosts();
		renderPaginationButtons();
	};

	const handleWritePostButtonClick = () => {
		const isLoggedIn = localStorage.getItem('token');
		if (!isLoggedIn) {
			alert('로그인이 필요한 서비스입니다.');
			window.location.href = '/login.html';
		} else {
			window.location.href = '/dopost.html';
		}
	};

	searchQueryInput.addEventListener('input', handleSearch);
	searchFilterSelect.addEventListener('change', handleSearch);
	writePostButton.addEventListener('click', handleWritePostButtonClick);

	fetchPosts();
>>>>>>> branch 'master' of https://github.com/viQflo/TJTSMDSTP
});
