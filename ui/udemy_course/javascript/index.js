//Lesson 84 - String length
var enteredText = prompt("Enter text");
var len = enteredText.length;

alert("You have written " + len + " characters, you have " + (280 - len) + " characters left.");

//Lesson 85 - Slicing and Extracting parts of a string
var name = "Gaurav";
name.slice(0, 1); //Take a slice starting from position 0 and 1 character
name.slice(5, 6); //Last character
name.slice(0, name.length) //Full String

//Lesson 86 - Changing casing
var name = "Gaurav";
name = name.toUpperCase();
name = name.toLowerCase();

//function
function lifeInWeeks(age) {
    var dest = 90 - age
    var days = dest * 365
    var weeks = Math.floor(dest * 52)
    var months = Math.floor(dest * 12)

    console.log("days " + days)
    console.log("Weeks " + weeks)
    console.log("Months " + months)
}

lifeInWeeks(56)

var n = Math.random()
//Psesudo random number between 1 & 6 (Like for a dice game)
console.log(Math.floor(1 + 6 * Math.random()))

//If then else
if (n === 1) { //With 3 equals, it compares data type.
    //do Something
} else {
    //do Something else
}

//Collections
var g = ["a" , "b", "c"]

var input = prompt("Is it part of array?")

if (g.includes(input)) {
    alert(" Welcome  !!")
} else {
    alert("Better next time !!")
}

//Fibonacci
function fibonacciGenerator (n) {
    //Write your code here:
    var a = 0;
    var b = 1;

    var output = [];

    output[0] = a;
    output[1] = b;

    for(var i=3; i<=n; ++i) {
        var c = a + b;
        output[i-1] = c;
        a = b;
        b = c;
    }

    return output;
}

//Higher-Order functions
function calculate(a, b, f) {
    return f(a,b);
}

calculate(2, 3, (a, b) => { return a + b;})

//Constructor Function
function BellBoy(name, age, hasWorkPermit, languages) {
    this.name = name;
    this.age = age;
    this.hasWorkPermit = hasWorkPermit;
    this.languages = languages;
    this.moveSuitcase = function() {
        alert("May I move your suitcase?");
    }
}

//Associated function

var bellboy1 = new BellBoy("Raj", 22, "yes", "English");

