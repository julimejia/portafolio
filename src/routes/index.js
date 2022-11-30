import { Router } from "express";
const router = Router();

router.get("/", (req, res) => res.render("index"));

router.get("/tech", (req, res) => res.render("tech", {title : 'tech that i know how to use', x: 30}  ));

router.get("/contact", (req, res) => res.render("contact", {title : "social media"}));

export default router;
