// Import express.js
const express = require("express");

//--------------------------------------------------------------------------------
const session = require("express-session");

// Create express app
var app = express();

// Set up session middleware
app.use(session({
    secret: 'z1x2c3v4b5n6m7a8s9d',  // Use a strong, random secret key
    resave: false,
    saveUninitialized: true
}));

// Add static files location
app.use(express.static("./app/public"));

// Use the Pug templating engine
app.set('view engine', 'pug');
app.set('views', './app/views');

// Get the functions in the db.js file to use
const db = require('./services/db');

var activeUser= new User("guest","","","","","","","",false);
// ---------------------------------------------------------------------------

// Create a route for root - /
app.get("/", async function(req, res)
 {
    activeUser =  req.session.activeUser || activeUser;
    //console.log(activeUser)
    const currentPage = parseInt(req.query.page) || 1; // Get the page number from the query or default to 1
    const itemsPerPage = 4; // Number of items per page

    // Get inventory items based on the page
    const inventoryItems = await Inventory.displayinventory(itemsPerPage, currentPage);
    //console.log(inventoryItems)
    // Render appropriate page based on whether the user is logged in
    //console.log(req.session.activeUser);
    //console.log(activeUser);
    if (activeUser.login_Status) {
        res.render("home-logged-in", {
            title: 'Home',
            products: inventoryItems.results,
            nextPage: inventoryItems.nextPage,
            prevPage: inventoryItems.prevPage,
        });
    } else {
        res.render("home", {
            title: 'Home',
            products: inventoryItems.results,
            nextPage: inventoryItems.nextPage,
            prevPage: inventoryItems.prevPage
        });
    }
});





// Start server on port 3000
app.listen(3000,function(){
    console.log(`Server running at http://127.0.0.1:3000/`);
});