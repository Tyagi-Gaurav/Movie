import express from "express";

const app = express();
const port = 3000;

//Custom middleware or interceptor
function logger(req, res, next) {
    console.log(req.method + " " + req.url);
    next();
};

app.use(logger);

app.get("/", (req, res) => {
  res.send("Hello");
});

app.listen(port, () => {
  console.log(`Listening on port ${port}`);
});
