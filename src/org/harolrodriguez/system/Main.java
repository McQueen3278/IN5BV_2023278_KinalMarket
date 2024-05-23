package org.harolrodriguez.system;


import java.io.InputStream;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.harolrodriguez.controller.MenuCargosController;
import org.harolrodriguez.controller.MenuClientesController;
import org.harolrodriguez.controller.MenuComprasController;
import org.harolrodriguez.controller.MenuEmailProveedorController;
import org.harolrodriguez.controller.MenuEmpleadosController;
import org.harolrodriguez.controller.MenuFacturasController;
import org.harolrodriguez.controller.MenuPrincipalController;
import org.harolrodriguez.controller.MenuProductoController;
import org.harolrodriguez.controller.MenuProveedoresController;
import org.harolrodriguez.controller.MenuTelefonoProveedorController;
import org.harolrodriguez.controller.MenuTipoProductoController;
import org.harolrodriguez.controller.ProgramadorController;

/*
Harol Rodriguez
11/04
Modificaciones:
 */
public class Main extends Application {

    private Stage escenarioPrincipal;
    private Scene escena;
    private final String URLVIEW = "/org/harolrodriguez/view/";


    /* FXLMLLoader, Parent*/
    @Override
    public void start(Stage escenarioPrincipal) throws Exception {
        this.escenarioPrincipal = escenarioPrincipal;
        this.escenarioPrincipal.setTitle("Kinal Market");
        menuPrincipalView();
        //Image image = new Image("org/harolrodriguez/images/LogoP.png");
        
        //escenarioPrincipal.getIcons().add(image);
//        Parent root = FXMLLoader.load(getClass().getResource("/org/harolrodriguez/view/MenuPrincipalView.fxml"));
//        Scene escena = new Scene(root);
//        escenarioPrincipal.setScene(escena);
        escenarioPrincipal.show();

    }

    public Initializable cambiarEscena(String fxmlName, int widht, int heigth) throws Exception {
        Initializable resultado = null;
        FXMLLoader loader = new FXMLLoader();

        InputStream file = Main.class.getResourceAsStream(URLVIEW + fxmlName);
        loader.setBuilderFactory(new JavaFXBuilderFactory());
        loader.setLocation(Main.class.getResource(URLVIEW + fxmlName));

        escena = new Scene((AnchorPane) loader.load(file), widht, heigth);
        escenarioPrincipal.setScene(escena);
        escenarioPrincipal.sizeToScene();
        resultado = (Initializable) loader.getController();

        return resultado;
    }

    public void menuPrincipalView() {
        try {
            MenuPrincipalController menuPrincipalView = (MenuPrincipalController) cambiarEscena("MenuPrincipalView.fxml", 765, 500);
            menuPrincipalView.setEscenarioPrincipal(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
   
    public void menuClientesView(){
       try {
            MenuClientesController menuClientesView = (MenuClientesController) cambiarEscena("MenuClientesView.fxml", 844, 530);
                menuClientesView.setEscenarioPrincipal(this);
                this.escenarioPrincipal.setTitle("Clientes");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    public void menuProveedoresView(){
       try {
            MenuProveedoresController menuProveedoresView = (MenuProveedoresController) cambiarEscena("ProovedoresView.fxml", 1030, 648);
                menuProveedoresView.setEscenarioPrincipal(this);
                this.escenarioPrincipal.setTitle("Proveedores");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void menuComprasView(){
       try {
            MenuComprasController menuComprasView = (MenuComprasController) cambiarEscena("MenuComprasView.fxml", 877, 545);
                menuComprasView.setEscenarioPrincipal(this);
                this.escenarioPrincipal.setTitle("Compras");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void menuTipoProductoView(){
       try {
            MenuTipoProductoController menuTipoProductoView = (MenuTipoProductoController) cambiarEscena("MenuTipoProductoView.fxml", 794, 519);
                menuTipoProductoView.setEscenarioPrincipal(this);
                this.escenarioPrincipal.setTitle("Tipo de Producto");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void menuCargosView(){
       try {
            MenuCargosController menuCargosView = (MenuCargosController) cambiarEscena("MenuCargosView.fxml", 780, 481);
                menuCargosView.setEscenarioPrincipal(this);
                this.escenarioPrincipal.setTitle("Cargos");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
     public void menuEmpleadosView(){
       try {
            MenuEmpleadosController menuEmpleadosView = (MenuEmpleadosController) cambiarEscena("MenuEmpleadosView.fxml", 934, 600);
                menuEmpleadosView.setEscenarioPrincipal(this);
                this.escenarioPrincipal.setTitle("Empleados");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
      public void menuFacturasView(){
       try {
            MenuFacturasController menuFacturasView = (MenuFacturasController) cambiarEscena("MenuFacturasView.fxml", 878, 550);
                menuFacturasView.setEscenarioPrincipal(this);
                this.escenarioPrincipal.setTitle("Facturas");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
      
      public void menuTelefonoProveedorView(){
       try {
            MenuTelefonoProveedorController menuTelefonoProveedorView = (MenuTelefonoProveedorController) cambiarEscena("MenuTelefonoProveedorView.fxml", 1012, 610);
                menuTelefonoProveedorView.setEscenarioPrincipal(this);
                this.escenarioPrincipal.setTitle("Telefono Proveedor");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
      
       public void menuEmailProveedorView(){
       try {
            MenuEmailProveedorController menuTelefonoProveedorView = (MenuEmailProveedorController) cambiarEscena("MenuEmailProveedorView.fxml", 865, 540);
                menuTelefonoProveedorView.setEscenarioPrincipal(this);
                this.escenarioPrincipal.setTitle("Email Proveedor");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
       
       public void menuProductoView(){
       try {
            MenuProductoController menuProductoView = (MenuProductoController) cambiarEscena("MenuProductoView.fxml", 1295, 807);
                menuProductoView.setEscenarioPrincipal(this);
                this.escenarioPrincipal.setTitle("Productos");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
     
    public void programadorView(){
       try {
            ProgramadorController programadorView = (ProgramadorController) cambiarEscena("ProgramadorView.fxml", 705, 442);
                programadorView.setEscenarioPrincipal(this);
                this.escenarioPrincipal.setTitle("Programador");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
