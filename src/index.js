import express from "express";
import {dirname, join} from 'path'
import {fileURLToPath} from "url";


const app = express();

const __dirname = dirname(fileURLToPath(import.meta.url));
console.log(__dirname, '/views'); 


app.set('views', join(__dirname, '/views'))
app.set('view engine', 'ejs');

app.get("/", (req, res) => res.render("index"));

app.get("/bout", (req, res) => res.render("index"));

app.get("/tech", (req, res) => res.render("index"));

app.get("/contact", (req, res) => res.render("contact"));

app.listen(3000);
console.log("server listening on port 3000");
