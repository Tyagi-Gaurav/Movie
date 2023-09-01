var allButtons = document.querySelectorAll("button");

function MyAudio(fileLocation) {
    this.fileLocation = fileLocation
    this.play = function() {
        //Tap info the audio hardware
//        var audioFile = new File(fileLocation);
        //Check file exists at location
//        if (audioFile.exists()){
            //Play file
            const audio = new Audio(fileLocation);
            audio.play();
//        }
    }
}

var tom1 = new MyAudio("sounds/tom-1.mp3");
var tom2 = new MyAudio("sounds/tom-2.mp3");
var tom3 = new MyAudio("sounds/tom-3.mp3");
var tom4 = new MyAudio("sounds/tom-4.mp3");
var snare = new MyAudio("sounds/snare.mp3");
var crash = new MyAudio("sounds/crash.mp3");
var kick = new MyAudio("sounds/kick-bass.mp3");


for(var i=0; i < allButtons.length;++i) {
    allButtons[i].addEventListener("click", function() {
        makeSound(this.innerHTML);
        buttonAnimation(this.innerHTML);
    });
}

function makeSound(key) {
    switch (key) {
        case 'w' : tom1.play(); break;
        case 'a' : tom2.play(); break;
        case 's' : tom3.play(); break;
        case 'd' : tom4.play(); break;
        case 'j' : snare.play(); break;
        case 'k' : crash.play(); break;
        case 'l' : kick.play(); break;
        default:
    }
}

document.addEventListener("keypress", function(event) {
    makeSound(event.key);
    buttonAnimation(event.key);
});

function buttonAnimation(curentKey) {
    var activeButton = document.querySelector("." + curentKey)
    activeButton.classList.add("pressed");
    setTimeout(function() {
        activeButton.classList.remove("pressed");
    }, 100);


}