package org.harolrodriguez.controller;



import java.io.IOException;
import java.net.URL;
import java.security.Principal;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import org.harolrodriguez.system.Main;

/**
 *
 * @author Harol RC
 */
public class MenuPrincipalController implements Initializable {
    private Main escenarioPrincipal;
    
    @FXML MenuItem btnMenuCompras;
    @FXML MenuItem btnMenuClientes;
    @FXML MenuItem btnProgramador;
    @FXML MenuItem btnMenuProveedores;
    @FXML MenuItem btnMenuTipoProducto;
    @FXML MenuItem btnMenuCargos;
    @FXML Button btnBack;
    
    @Override
    public void initialize (URL location, ResourceBundle resources)  {

    }

    public Main getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Main escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
    
    
    
    @FXML
    public void buttonHandleEvent (ActionEvent event) throws IOException{
        if(event.getSource() == btnMenuClientes ){
            escenarioPrincipal. menuClientesView();
        }else if (event.getSource() == btnProgramador){
            escenarioPrincipal.programadorView();
        }else if(event.getSource() == btnMenuProveedores){
            escenarioPrincipal.menuProveedoresView();
        }else if(event.getSource() == btnMenuCompras){
            escenarioPrincipal.menuComprasView();
        }else if(event.getSource() == btnMenuTipoProducto){
            escenarioPrincipal.menuTipoProductoView();
        }else if (event.getSource() == btnMenuCargos){
            escenarioPrincipal.menuCargosView();
        }
    }
    
    
    
}
