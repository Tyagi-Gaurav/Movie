/* 
1. Use the inquirer npm package to get user input.
2. Use the qr-image npm package to turn the user entered URL into a QR code image.
3. Create a txt file to save the user input using the native fs node module.
*/
import inquirer from "inquirer";
import qr from "qr-image";
import fs from "node:fs";

inquirer
  .prompt([
    {
        name: "url",
        message : "Enter URL:"
    }
  ])
  .then((answers) => {
    console.log("you entered : " + answers.url)
    var qr_svg = qr.image(answers.url);
    qr_svg.pipe(fs.createWriteStream('qr_img.png'));

    fs.writeFile("URL.txt", answers.url, (err) => {
        if (err) throw err;
        console.log("File has been saved");
    });
  }).catch((error) => {
        if (error.isTtyError) {
          console.log("Error occurred while reading: " + error);
        } else {
          console.log("Some other error occurred: " + error);
        }
      });

