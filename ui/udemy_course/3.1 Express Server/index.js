import express from "express";

const app = express();
const port = 3000;

app.get("/", (req, resp) => {
    console.log("Full request: " + req)
    console.log("Headers: " + req.rawHeaders)
    resp.send("Hello, World!");
});

app.get("/about", (req, resp) => {
    resp.send("You've reached about endpoint");
});

app.get("/contact", (req, resp) => {
    resp.send("You've reached contact endpoint");
});

app.post("/register", (req, res) => {
    res.sendStatus(201);
});

app.put("/user/random", (req, res) => {
    res.sendStatus(200);
});

app.patch("/user/random", (req, res) => {
    res.sendStatus(200);
});

app.delete("/user/random", (req, res) => {
    res.sendStatus(200);
});

app.listen(port, () => {
    //Callback function
    console.log(`Server running on port ${port}`);
});