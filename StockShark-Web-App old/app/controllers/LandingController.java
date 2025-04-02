package com.sad.stockshark.Controllers;
import com.sad.stockshark.classes.Database;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class LandingController {

   public void getDetails(){

      Database.getFromDatabase();

   }
   }