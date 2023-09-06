import express from "express";
import bodyParser from "body-parser";

const app = express();
const port = 3000;

app.use(express.static("public"));
app.use(bodyParser.urlencoded({ extended: true }));

var homeList = [];
var workList = [];

app.get("/", (req, res) => {
  res.locals.todoList = homeList;
  res.locals.listCategory = "home";
  res.locals.dateToDisplay = getDateToDisplay();
  res.render("index.ejs");
});

app.get("/work", (req, res) => {
    res.locals.todoList = workList;
    res.locals.listCategory = "work";
    res.locals.dateToDisplay = getDateToDisplay();
    res.render("index.ejs");
  });

app.post("/submit", (req, res) => {
    if (req.body["listCategory"]) {
        let listCategory = req.body["listCategory"];
    
        if (listCategory === "home") {
            homeList.push({
                "index" : homeList.length,
                "todo" : req.body["todo"],
                "status" : ""
            });
            res.redirect("/");
        }

        if (listCategory === "work") {
            workList.push({
                "index" : workList.length,
                "todo" : req.body["todo"],
                "status" : ""
            });
            res.redirect("/work");
        }
    }
});

app.post("/checked", (req, res) => {
    //console.log(req.body["listCategory"]);
    if (req.body["listCategory"]) {
        let listCategory = req.body["listCategory"];

        if (listCategory === "home") {
            let index = req.body["index"];
            let status = "";
            if (req.body["checked"]) {
                status = "checked";
            }
    
            homeList[index]["status"] = status;
            res.redirect("/");
        }
        if (listCategory === "work") {
            let index = req.body["index"];
            let status = "";
            if (req.body["checked"]) {
                status = "checked";
            }
    
            workList[index]["status"] = status;
            res.redirect("/work");
        }
    }
});

app.listen(port, () => {
  console.log(`Listening on port ${port}`);
});

function getDateToDisplay() {
    const months = ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"];
    const days = ["Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"];

    const date = new Date();
    let weekdayNum = date.getDay();
    let monthNum = date.getMonth();
    let dateNum = date.getDate();

    return days[weekdayNum] + ", " + months[monthNum] + " " + dateNum
}