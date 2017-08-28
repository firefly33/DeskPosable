function selectMenuItem(item) {
    $('nav ul li').removeClass('active');
    $('nav ul '+item).addClass('active');
}
