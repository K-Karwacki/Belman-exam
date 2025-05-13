module dk.easv.belmanexam{
  requires javafx.graphics;
  requires javafx.controls;
  requires javafx.fxml;
  requires javafx.swing;

  requires java.desktop;
  requires java.base;

  requires com.gluonhq.attach.pictures;
//  requires charm.down.common;
//  requires com.gluonhq.;

  requires jbcrypt;

  opens dk.easv.belmanexam.ui.controllers.main to javafx.fxml;
  opens dk.easv.belmanexam.ui.controllers.components to javafx.fxml;
  opens dk.easv.belmanexam.ui.controllers.admin to javafx.fxml;
  opens dk.easv.belmanexam.ui.controllers.admin.dashboards to javafx.fxml;
  opens dk.easv.belmanexam.ui.controllers.operator to javafx.fxml;
  opens dk.easv.belmanexam.ui.controllers.operator.dashboards to javafx.fxml;
  opens dk.easv.belmanexam.ui.controllers.qa to javafx.fxml;
  opens dk.easv.belmanexam.ui.controllers.qa.dashboards to javafx.fxml;

  exports dk.easv.belmanexam;

  exports dk.easv.belmanexam.services.interfaces;
  exports dk.easv.belmanexam.services.factories;
  exports dk.easv.belmanexam.services.implementations;

  exports dk.easv.belmanexam.ui;
  exports dk.easv.belmanexam.ui.controllers.main;
  exports dk.easv.belmanexam.ui.controllers.components;
  exports dk.easv.belmanexam.ui.controllers.admin;
  exports dk.easv.belmanexam.ui.controllers.admin.dashboards;
  exports dk.easv.belmanexam.ui.controllers.operator;
  exports dk.easv.belmanexam.ui.controllers.operator.dashboards;
  exports dk.easv.belmanexam.ui.controllers.qa;
  exports dk.easv.belmanexam.ui.controllers.qa.dashboards;

  exports dk.easv.belmanexam.model;
  exports dk.easv.belmanexam.repositories.interfaces;
  exports dk.easv.belmanexam.repositories.utils;


}