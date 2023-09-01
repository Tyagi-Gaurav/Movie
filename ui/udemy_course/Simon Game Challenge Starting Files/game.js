//Create A New Pattern
var levelNum = -1;
var userClickedPattern = [];
var gamePattern = [];
var buttonColors = ["red", "blue", "green", "yellow"];
var index=0;

function updateLevel() {
    levelNum++;
    //Update title
    $("#level-title").text("Level " + levelNum);
}

function resetIndex() {
    index = -1;
}

function resetGamePattern() {
    gamePattern = [];
}

function resetUserClickedPattern() {
    userClickedPattern = [];
}

function resetLevel() {
    levelNum = -1;
}

function incrementIndex() {
    index++;
}

function nextSequence() {
    return Math.floor(1 + 3 * Math.random())
}

function selectRandomColor() {
    var randomChosenColour = buttonColors[nextSequence()];
    gamePattern.push(randomChosenColour);
    return randomChosenColour;
}

function animateChosenColor(userChosenColour) {
    $("#" + userChosenColour).fadeOut(50).fadeIn(50);
    playSound(userChosenColour);
}

function playSound(fileLocation) {
    const audio = new Audio("sounds/" + fileLocation + ".mp3");
    audio.play();
}

function animatePress(currentColour) {
    $("#" + currentColour).addClass("pressed");
    setTimeout(function() {
        $("#" + currentColour).removeClass('pressed ');
    }, 100);
}

function checkAnswer(currentIndex) {
//    console.log("Index " + index);
//    console.log("userClickedPattern: " + userClickedPattern);
//    console.log("gamePattern: " + gamePattern);

    if (userClickedPattern.length <= gamePattern.length) {
        if (userClickedPattern[currentIndex] === gamePattern[currentIndex]) {
            if (currentIndex === gamePattern.length - 1) {
               //User passed level
               setTimeout(function() {
                   initialise();
               }, 1000);
            }
        } else {
            userFailed();
        }
    } else {
        userFailed();
    }
}

function userFailed() {
    $("#level-title").text("Game Over, Press Any Key to Restart");
    resetLevel();
}

function initialise() {
    updateLevel();
    resetIndex();
    resetUserClickedPattern();
    var chosenColor = selectRandomColor();
    //Use jQuery to select the button with the same id as the randomChosenColour
    animateChosenColor(chosenColor);
}

$("body").keypress(function(event) {
    if (levelNum < 0) {
        resetGamePattern();
        initialise();
    }
});

//Check Which Button is Pressed
$(".btn").click(function(event) {
    incrementIndex();
    var userChosenColour = this.id;
    userClickedPattern.push(userChosenColour)
    playSound(userChosenColour);
    animatePress(userChosenColour);
    checkAnswer(index);
});
