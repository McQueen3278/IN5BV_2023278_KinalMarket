package org.harolrodriguez.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import org.harolrodriguez.system.Main;

public class ProgramadorController implements Initializable {
    
    private Main escenarioPrincipal;
    @FXML Button btnBack;
    @FXML ImageView imgBack;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
      
    }

    public Main getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Main escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
    
    public void clickAtras (ActionEvent event) throws IOException{
        if(event.getSource() == btnBack){
            escenarioPrincipal.menuPrincipalView();
        }else if (event.getSource() == imgBack){
            escenarioPrincipal.menuPrincipalView();
        }
    }
}
