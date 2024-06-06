package org.harolrodriguez.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.swing.JOptionPane;
import org.harolrodriguez.bean.Compras;
import org.harolrodriguez.db.Conexion;
import org.harolrodriguez.system.Main;

public class MenuComprasController implements Initializable {

    private enum operaciones {
        AGREGAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO
    }
    private operaciones tipoDeOperaciones = operaciones.NINGUNO;
    private ObservableList<Compras> listaCompras;
    private Main escenarioPrincipal;
    @FXML
    private Button btnBack;
    @FXML
    private TextField txtCompraId;
    @FXML
    private DatePicker txtfechaCompra;

    @FXML
    private TextField txttotalCompra;
    @FXML
    private TableView tblCompras;
    @FXML
    private TableColumn colcompraId;
    @FXML
    private TableColumn colfechaCompra;
    @FXML
    private TableColumn coltotalCompra;
    @FXML
    private Button btnAgregar;
    @FXML
    private Button btnEliminar;
    @FXML
    private Button btnEditar;
    @FXML
    private Button btnReporte;
    @FXML
    private ImageView imgAgregar;
    @FXML
    private ImageView imgEliminar;
    @FXML
    private ImageView imgEditar;
    @FXML
    private ImageView imgReporte;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarDatos();
    }

    public void cargarDatos() {
        tblCompras.setItems(getCompras());
        colcompraId.setCellValueFactory(new PropertyValueFactory<Compras, Integer>("compraId"));
        colfechaCompra.setCellValueFactory(new PropertyValueFactory<Compras, LocalDate>("fechaCompra"));
        coltotalCompra.setCellValueFactory(new PropertyValueFactory<Compras, String>("totalCompra"));
    }

    public void selecdcionarElemento() {
        txtCompraId.setText(String.valueOf(((Compras) tblCompras.getSelectionModel().getSelectedItem()).getCompraId()));
        txttotalCompra.setText(((Compras) tblCompras.getSelectionModel().getSelectedItem()).getTotalCompra());
        
    }

    public void Agregar() {
        switch (tipoDeOperaciones) {
            case NINGUNO:
                activarControles();
                btnAgregar.setText("Guardar");
                btnEliminar.setText("Cancelar");
                btnEditar.setDisable(true);
                btnReporte.setDisable(true);
                imgAgregar.setImage(new Image("/org/harolrodriguez/images/Guardar.png"));
                imgEliminar.setImage(new Image("/org/harolrodriguez/images/Cancelar.png"));
                tipoDeOperaciones = operaciones.ACTUALIZAR;
                cargarDatos();
                break;
            case ACTUALIZAR:
                guardar();
                activarControles();
                limpiarControles();
                btnAgregar.setText("Agregar");
                btnEliminar.setText("Eliminar");
                btnEditar.setDisable(false);
                btnReporte.setDisable(false);
                imgAgregar.setImage(new Image("/org/harolrodriguez/images/CarritoC.png"));
                imgEliminar.setImage(new Image("/org/harolrodriguez/images/EliminarCarrito.png"));
                tipoDeOperaciones = operaciones.NINGUNO;
                cargarDatos();
                break;
        }

    }

    public void guardar() {
        Compras registro = new Compras();
        registro.setCompraId(Integer.parseInt(txtCompraId.getText()));
        LocalDate fechaN = txtfechaCompra.getValue();
        Date fechaC = Date.valueOf(fechaN);
        registro.setTotalCompra(txttotalCompra.getText());
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarCompra(?, ?, ?) }");
            procedimiento.setInt(1, registro.getCompraId());
            procedimiento.setDate(2, fechaC);
            procedimiento.setString(3, registro.getTotalCompra());
            procedimiento.execute();
            listaCompras.add(registro);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ObservableList<Compras> getCompras() {
        ArrayList<Compras> lista = new ArrayList<>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarCompras()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                java.sql.Date fecha = resultado.getDate("fechaCompra");
                LocalDate fechaDP = fecha.toLocalDate();
                lista.add(new Compras(resultado.getInt("compraId"),
                        fechaDP,
                        resultado.getString("totalCompra")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listaCompras = FXCollections.observableArrayList(lista);

    }

    public void eliminar() {
        switch (tipoDeOperaciones) {
            case ACTUALIZAR:
                desactivarControles();
                limpiarControles();
                btnAgregar.setText("Agregar");
                btnEliminar.setText("Eliminar");
                btnEditar.setDisable(false);
                btnReporte.setDisable(false);
                imgAgregar.setImage(new Image("/org/harolrodriguez/CarritoC.png"));
                imgEliminar.setImage(new Image("/org/harolrodriguez/images/EliminarCarrito.png"));
                tipoDeOperaciones = operaciones.NINGUNO;
                break;
            default:
                if (tblCompras.getSelectionModel().getSelectedItem() != null) {
                    int respuesta = JOptionPane.showConfirmDialog(null, "Confirmar si elimina el registro", "Eliminar Compra", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (respuesta == JOptionPane.YES_NO_OPTION) {
                        try {
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ElimiarCategoria(?) }");
                            procedimiento.setInt(1, ((Compras) tblCompras.getSelectionModel().getSelectedItem()).getCompraId());
                            procedimiento.execute();
                            listaCompras.remove(tblCompras.getSelectionModel().getSelectedItem());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, " Debe de Seleccionar un Elemento ");
                }
        }
    }

    public void editar() {
        switch (tipoDeOperaciones) {
            case NINGUNO:

                if (tblCompras.getSelectionModel().getSelectedItem() != null) {
                    btnEditar.setText("Actualizar");
                    btnReporte.setText("Cancelar");
                    btnAgregar.setDisable(true);
                    btnEliminar.setDisable(true);
                    imgEditar.setImage(new Image("/org/harolrodriguez/images/Guardar.png"));
                    imgReporte.setImage(new Image("/org/harolrodriguez/images/Reportes.png"));
                    activarControles();
                    txtCompraId.setEditable(false);
                    tipoDeOperaciones = operaciones.ACTUALIZAR;
                } else {
                    JOptionPane.showMessageDialog(null, "Debe seleccionar un Elemento");
                }
                break;
            case ACTUALIZAR:

                actualizar();
                btnEditar.setText("Editar");
                btnReporte.setText("Reportes");
                btnAgregar.setDisable(false);
                btnEliminar.setDisable(false);
                imgEditar.setImage(new Image("/org/harolrodriguez/images/EditarC.png"));
                imgReporte.setImage(new Image("/org/harolrodriguez/images/Reportes.png"));
                desactivarControles();
                tipoDeOperaciones = operaciones.NINGUNO;
                cargarDatos();
        }
    }

    public void actualizar() {
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EditarCompra(?, ?, ?) }");
            Compras registro = (Compras) tblCompras.getSelectionModel().getSelectedItem();
            registro.setCompraId(Integer.parseInt(txtCompraId.getText()));
            LocalDate fechaN = txtfechaCompra.getValue();
            Date fechaC = Date.valueOf(fechaN);
            procedimiento.setInt(1, registro.getCompraId());
            procedimiento.setDate(2, fechaC);
            procedimiento.setString(3, registro.getTotalCompra());
            procedimiento.execute();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void reporte() {
        switch (tipoDeOperaciones) {
            case ACTUALIZAR:
                desactivarControles();
                limpiarControles();
                btnEditar.setText("Editar");
                btnReporte.setText("Reporte");
                btnAgregar.setDisable(false);
                btnEliminar.setDisable(false);
                imgEditar.setImage(new Image("/org/harolrodriguez/images/EditarC.png"));
                imgReporte.setImage(new Image("/org/harolrodriguez/images/Reportes.png"));
                tipoDeOperaciones = operaciones.NINGUNO;
        }
    }

    public void desactivarControles() {
        txtCompraId.setEditable(false);
        txtfechaCompra.setEditable(false);
        txttotalCompra.setEditable(false);

    }

    public void activarControles() {
        txtCompraId.setEditable(true);
        txtfechaCompra.setEditable(true);
        txttotalCompra.setEditable(true);

    }

    public void limpiarControles() {
        txtCompraId.clear();
        txttotalCompra.clear();

    }

    public Main getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Main escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

    @FXML
    private ImageView imgBack;

    public void clickAtras(ActionEvent event) throws IOException {
        if (event.getSource() == btnBack) {
            escenarioPrincipal.menuPrincipalView();
        } else if (event.getSource() == imgBack) {
            escenarioPrincipal.menuPrincipalView();
        }
    }
}
