$(function() {
	var offset = 0;
	var limit = 6;
	var ajaxDone = true;
	var morePages = true;
	var editId = 0;
	var commentPostId = 0;

	$(window).scroll(scrolled);
	$("#add-post").click(showAddPost);
	$("#cancel-post-button").click(removeAddPost);
	$("#save-post-button").click(savePosts);
	$("#delete-post-button").click(deletePost);
	$("#cancel-comment").click(closeAddComment);
	$("#save-comment").click(saveComment);
	$("#search").bind("search", searchKey);
	$("main").on("click", ".editable", showEditPost);
	$("main").on("click", ".comment-icon", showAddComment);
	$("main").on("click", ".comment-count", getComments);

	getPosts();
	
	
	function scrolled() {
		if (ajaxDone && morePages) {
			var top = $(this).scrollTop();
			var size = $("body").height(); 
			var height = $(this).height();
			if (top >= size - height) {
				offset += limit;
				console.log(offset);
				getPosts();
			}
		}
	}
	
	
	function searchKey() {
		offset = 0;
		ajaxDone = false;
			$.ajax({
				url: "/search-posts",
				method: "get",
				dataType: "json",
				data: {
					text: $("#search").val(),
					limit: limit,
					offset: offset
				},
				error: function() {
					ajaxDone = true;
					ajaxError();
				},
				success: function(data) {
					ajaxDone = true;
					if (data.length < limit) {
						morePages = false;
					}
					morePages = true; 
					showSearchResults(data);
				}
			});
	}
	
	function showSearchResults(data) {
		offset = 0;
		$(".post").remove();
		buildPosts(data);
	}

	function saveComment() {
		console.log("myeditId: " + editId);
		$.ajax({
			url : "/save-comment",
			method : "POST",
			type : "json",
			data : {
				content : $("#add-comment-popup textarea").val(),
				id : editId,
				postId : commentPostId
			},
			error : ajaxError,
			success : function(data) {
				console.log(data);
				$("#add-comment-popup").hide();
				reloadPosts();
			}
		});
	}

	function getComments() {
		var postId = $(this).parent().parent().find(".editable").data("id");
		var $commentTemplate = $(this).parent().parent().find(
				".comment-template");
		$.ajax({
			url : "/get-comments",
			method : "get",
			type : "json",
			data : {
				postId : postId
			},
			error : ajaxError,
			success : function(data) {
				console.log(data);

				buildComments(data, $commentTemplate);
			}
		});
		return false;
	}

	function buildComments(data, $commentTemplate) {
		$commentTemplate.parent().find(".comment").remove();
		for (var i = 0; i < data.length; i++) {
			var $comment = $commentTemplate.clone();
			$comment.removeClass("comment-template");
			$comment.addClass("comment");
			$comment.append(data[i].text);
			$commentTemplate.parent().append($comment);
		}
	}

	function closeAddComment() {
		$(this).parent().hide();
	}

	function showAddComment() {
		editId = 0;
		commentPostId = $(this).parent().parent().find(".editable").data("id");
		var $popup = $("#add-comment-popup").detach();
		$(this).parent().parent().after($popup);
		$popup.show();
	}

	function reloadPosts() {
		offset = 0;
		$(".post").remove();
		getPosts();
	}

	function deletePost() {
		console.log("editId: " + editId);
		$.ajax({
			url : "/delete-post",
			method : "get",
			type : "json",
			data : {
				id : editId
			},
			error : ajaxError,
			success : function() {
				reloadPosts();
				console.log("success function: " + editId);
				// getPosts();
			}
		});
	}

	function showEditPost() {
		$("#delete-post-button").show();
		var text = $(this).parent().parent().find(".post-content").text();
		$("#create-post-popup textarea").val(text);
		$("#create-post-popup").addClass("show-add-popup");
		$("main").addClass("main-add-popup");
		var id = $(this).data("id");
		editId = id;
	}

	function removeAddPost() {
		$("#create-post-popup").removeClass("show-add-popup");
		$("main").removeClass("main-add-popup");

		restPost();
	}

	function restPost() {
		editId = 0;
		$("#create-post-popup textarea").val("");
	}

	function showAddPost() {
		restPost();
		$("#create-post-popup").addClass("show-add-popup");
		$("main").addClass("main-add-popup");
		$("#delete-post-button").hide();
	}

	function savePosts() {
		var content = $("#create-post-popup textarea").val();
		$.ajax({
			url : "/save-post",
			method : "post",
			type : "json",
			data : {
				content : content,
				id : editId
			},
			error : ajaxError,
			success : function() {
				reloadPosts();
				// getPosts();
			}
		});
	}

	function getPosts() {
		ajaxDone = false;
		var text = $("#search").val();
		var url = "/get-posts";
		var data = {
			limit: limit,
			offset: offset
		}
		if (text != "") {
			url = "/search-posts";
			data.text = text;
		}
		console.log("getPosts " + offset);
		$.ajax({
			url : url,
			method : "get",
			type : "json",
			data : data,
			error : function() {
				ajaxDone = true;
				ajaxError();
			},
			success : function(data) {
				ajaxDone = true;
				if (data.length < limit) {
					morePages = false;
				}
				buildPosts(data);
			}
		});
	}

	function ajaxError() {
		alert("AJAX Error!");
	}

	function buildPosts(data) {
		console.log(data);

		for (var i = 0; i < data.length; i++) {
			var $post = $("#post-template").clone();
			$post.removeAttr("id");
			$post.addClass("post");
			$post.find(".username").append(data[i].user.name);
			$post.find(".post-content").append(data[i].content);
			if (!data[i].editable) {
				$post.find(".editable").hide();
			}
			$post.find(".editable").data("id", data[i].id);
			$post.find(".comment-count").append(data[i].commentCount);
			$("main").append($post);
		}

	}

});
