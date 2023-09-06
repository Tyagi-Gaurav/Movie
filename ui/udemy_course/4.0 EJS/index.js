import express from "express";

const app = express();
const port = 3000;
const weekday = ["Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"];

app.get("/", (req, res) => {
  const d = new Date();
  var dayOfWeek = d.getDay();
  var dayType = "weekday";
  var workType = "work hard";
  
  if (dayOfWeek == 0 || dayOfWeek == 6) {
    dayType = "weekend";
    workType = "have fun";
  } 

  res.render("index.ejs",
  {
    "dayType" : dayType,
    "workType" : workType
  });
});

app.listen(port, () => {
  console.log(`Listening on port ${port}`);
});