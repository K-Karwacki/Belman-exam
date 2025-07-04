module dk.easv.belmanexam{
  requires javafx.graphics;
  requires javafx.controls;
  requires javafx.fxml;
  requires javafx.swing;
  requires java.naming;

  requires java.desktop;

  requires com.gluonhq.attach.pictures;
//  requires charm.down.common;
//  requires com.gluonhq.;

  requires metadata.extractor;
  requires jbcrypt;
  requires jdk.httpserver;
  requires java.sql;
  requires com.microsoft.sqlserver.jdbc;
  requires com.google.api.client;
  requires com.google.api.services.drive;
  requires google.api.client;
  requires com.google.api.client.json.gson;
  requires com.google.api.client.auth;
    requires com.google.api.client.extensions.java6.auth;
    requires com.google.api.client.extensions.jetty.auth;
    requires org.apache.pdfbox;
  requires java.management;
    requires org.json;

    opens dk.easv.belmanexam.ui.controllers.main to javafx.fxml;
  opens dk.easv.belmanexam.ui.controllers.components to javafx.fxml;
  opens dk.easv.belmanexam.ui.controllers.admin to javafx.fxml;
  opens dk.easv.belmanexam.ui.controllers.admin.dashboards to javafx.fxml;
  opens dk.easv.belmanexam.ui.controllers.operator.dashboards to javafx.fxml;
  opens dk.easv.belmanexam.ui.controllers.qa.dashboards to javafx.fxml;

  exports dk.easv.belmanexam;

  exports dk.easv.belmanexam.services.interfaces;
  exports dk.easv.belmanexam.services.factories;
  exports dk.easv.belmanexam.services.implementations;

  exports dk.easv.belmanexam.exceptions;
  exports dk.easv.belmanexam.ui;
  exports dk.easv.belmanexam.ui.controllers.main;
  exports dk.easv.belmanexam.ui.controllers.components;
  exports dk.easv.belmanexam.ui.controllers.admin;
  exports dk.easv.belmanexam.ui.controllers.admin.dashboards;
  exports dk.easv.belmanexam.ui.controllers.operator.dashboards;
  exports dk.easv.belmanexam.ui.controllers.qa.dashboards;

  exports dk.easv.belmanexam.entities;
  exports dk.easv.belmanexam.services.utils;
  exports dk.easv.belmanexam.repositories.interfaces;
  exports dk.easv.belmanexam.repositories.utils;
  exports dk.easv.belmanexam.repositories.utils.mappers;
  exports dk.easv.belmanexam.ui.models;
  exports dk.easv.belmanexam.auth;
  exports dk.easv.belmanexam.services;


}