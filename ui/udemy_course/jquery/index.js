$("h1").css("color", "red"); //Changing style
$("h1").addClass("big-title margin-50"); //Applying class to an element.

$("button").text("Don't click me!"); // To select all buttons.

$("button").html("<a>some html!</a>"); // To select all buttons.

//$("img").attr("src") //Get attribute of an element

//$("a").attr("href", "new link") //Set attribute of an element

//Adding event listener to an element
//$("h1").click(function() {
//    $("h1").css("color", "purple")
//});

$("button").click(function() {
    $("h1").css("color", "purple")
});

//$(document).keypress(function(event) {
////    console.log(event.key);
//    $("h1").text(event.key);
//});

$("h1").on("mouseover", function(event) {
    $("h1").css("color", "purple")
});


//Adding elements
$("h1").before("<button>New</button>") //Will add a new element before

$("h1").prepend("<button>New</button>") //Will add after opening of h1 element

//Animations
$("h1").click(function() {
//    $("h1").toggle()
//    $("h1").fadeOut() //or fadeIn()
//    $("h1").animate({opacity: 0.5}) //Can only add those CSS values that have numeric value.
//    $("h1").slideUp().slideDown().animate({opacity: 0.5})
    $("h1").slideUp().slideDown().animate({opacity: 0.5})
});