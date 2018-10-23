
$(document).ready(() => {
	var menu = $('.menu');

	$('.menu-button').on('click', () => {
		if (menu.css('display') == 'none') {
			$(menu).css('display', 'flex');
		}
		else if (menu.css('display') == 'flex') {
			$(menu).css('display', 'none');
		}
	});
})