/*
  p5.js is a JavaScript library to make coding accessible
  for artists, designers, educators, and beginners.

  Read the docs at https://p5js.org/reference/
  Check out examples at https://p5js.org/examples/

  octocat.js is a JavaScript library to make octocats.
  Read the docs at https://github.com/octocademy/octocat.js
*/

import p5 from "p5.js";
import Octocat from "octocat.js";

let octocat;

p5.setup = () => {
  createCanvas(300, 300);
  octocat = new Octocat();

  // Put your cursor below the arrows to start editing
  // then click on an octocat setting to insert it
  // in your code. When you make changes, your octocat
  // will automatically update!
  //
  // ⬇️️️️️️️️️️️️️️️️️️️️️️️️⬇️⬇️⬇️⬇️
  octocat.setEyes("Neutral")
  octocat.setEyeColor("#5675B8")
  octocat.blink()
  octocat.setMouth("Chillin")
  octocat.setFacialHair("Stubble shadow")
  octocat.setTop("T-shirt")
  octocat.setFootwear("Converse Shoes")
  octocat.setHeadgear("Dad Cap")
  octocat.setProp("Laptop")
  octocat.setPuddleColor("#42185A")
};

// ♻️ The draw method gets called repeatedly
// you can make changes here to animate!
p5.draw = () => {
  background("white");
  octocat.draw();
};
