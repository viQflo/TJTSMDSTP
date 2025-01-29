document.addEventListener('mousemove', function(event) {
	let star = document.createElement('div');
	star.classList.add('star');
	document.body.appendChild(star);

	// 별 위치 설정
	star.style.left = `${event.clientX}px`;
	star.style.top = `${event.clientY}px`;

	// 일정 시간이 지나면 별 제거
	setTimeout(() => {
		star.remove();
	}, 1000);
});
