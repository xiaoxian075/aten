$(function() {
	// var headerArr = ['首页','关于云返','招商入驻','企业宣传','新闻资讯','人才战略','联系我们'];
	// var headHref = ['index.html','about.html','enter.html','propaganda.html','news.html','recruit.html','contact.html'];
	



	/*尾部*/
	var footer = '<ul><li><a href="index.html">云返服饰</a><ul><li><a href="about.html">企业概况</a></li><li><a href="JavaScript:void(0)">企业愿景</a></li><li><a href="JavaScript:void(0)">企业文化</a></li><li><a href="JavaScript:void(0)">发展历程</a></li><li><a href="JavaScript:void(0)">管理团队</a></li><li><a href="enter.html">招商入驻</a></li></ul></li><li><a href="JavaScript:void(0)">企业品牌</a><ul><li><a href="JavaScript:void(0)">品牌理念</a></li><li><a href="JavaScript:void(0)">品牌形象</a></li><li><a href="JavaScript:void(0)">品牌荣誉</a></li></ul></li><li><a href="news.html">资讯动态</a><ul><li><a href="news.html">企业新闻</a></li><li><a href="JavaScript:void(0)">媒体关注</a></li></ul></li><li><a href="JavaScript:void(0)">人力资源</a><ul><li><a href="JavaScript:void(0)">人才理念</a></li><li><a href="recruit.html">人才招聘</a></li></ul></li><li style="margin-right: 0"><a href="contact.html">联系我们</a><ul><li><a href="contact.html">联系我们</a></li></ul></li></ul><div class="copy"><p style="font-size: 18px;">广东云返服饰有限公司</p><p style="font-size: 14px;">COPYRIGHT&copy;2016版权所有粤ICP备15084310号</p></div>';
	$('#footer').append(footer);


	/*----------------------------------------*/

	$(window).scroll( function(){
		if($('.content-index').length){
			// $(window).off('scroll');
			return;
		}
		if($('body').scrollTop() >= 180 && $('.aside').css('position') != 'fixed'){
			$('.aside').css({
			    position: 'fixed',
			    top:0
			})
		}else if($('body').scrollTop() < 180 && $('.aside').css('position') == 'fixed'){
			$('.aside').css({
			    position: 'relative'
			})
		}
	});
})