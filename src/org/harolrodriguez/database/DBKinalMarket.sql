drop database if exists DBKinalMarket;
Create database DBKinalMarket;

use DBKinalMarket;

-- Se crea la entidad Clientes
create table Clientes(
	CodigoCliente int not null,
	NITcliente varchar(10) not null,
	nombreCliente varchar(50)not null,
	apellidoCliente varchar(50) not null,
	direccionCliente varchar(250),
	telefonoCliente varchar(15),
	correoCliente varchar(250),
	primary key PK_Clientes(codigoCliente)

);



-- Se crea la entidad Proveedores
create table Proveedores(
	codigoProveedor int not null,
    NITproveedor varchar(10) not null,
    nombreProveedor varchar(60) not null,
    apellidoProveedor varchar(60) not null,
    direccionProveedor varchar(150) not null,
    razonSocial varchar(60),
    contactoPrincipal varchar(100),
    paginaWeb varchar(50),
    primary key PK_codigoProveedor(codigoProveedor)
);
 
 -- Se crea la entidad Compras
Create table Compras(
	compraId int not null,
    fechaCompra date ,
    totalCompra decimal (10,2) not null,
    primary key PK_compraId(compraId)
);

-- Se crea la entidad categoriaProductos
Create table categoriaProductos(
	categoriaProductoId int not null,
    nombreCategoria varchar(30) not null,
    descripcionCategoria varchar(100) not null,
    primary key PK_categoriaProductoId (categoriaProductoId)
);

-- Se crea la entidad Cargos
Create table Cargos (
	cargoId int not null,
    nombreCargo varchar (30) not null,
    descripcionCargo varchar (100) not null,
    primary key PK_cargoId (cargoID)
);

-- Se crea la entidad Empleados
Create table Empleados (
	empleadoId int not null,
    nombreEmpleado varchar (30) not null,
    apellidoEmpleado varchar(30) not null,
    sueldo decimal(10,2) not null,
    horaEntrada time ,
    horaSalida time,
    cargoId int ,
    primary key PK_empleadoId (empleadoId) ,
    constraint FK_Cargos_Empleados foreign key (cargoId)
		references Cargos(cargoId)
        ON DELETE CASCADE
);

-- Se crea la entidad Facturas
Create table Facturas(
    facturaId int not null,
    facha date,
    hora time,
    total decimal (10,2) not null,
    codigoCliente int ,
    empleadoId int,
    primary key PK_facturaId (facturaId),
    constraint FK_Clientes_Facturas foreign key (codigoCliente)
		references Clientes(codigoCliente),
	constraint FK_Empleados_Facturas foreign key (empleadoId)
		references Empleados(empleadoId)
        ON DELETE CASCADE
);

-- Se crea la entidad telefonoProveedores
create table telefonoProveedor(
	codigoTelefonoProveedor int not null,
    numeroPrincipal varchar(08) not null,
    numeroSecundario varchar(08),
    observaciones varchar(45),
    codigoProveedor int ,
    primary key PK_codigoTelefonoProveedor (codigoTelefonoProveedor),
    constraint FK_Proveedores_telefonoProveedor foreign key (codigoProveedor)
		references Proveedores (codigoProveedor)
        ON DELETE CASCADE
        
);

-- se crea la entidad EmailProveedor
Create table EmailProveedor(
	codigoEmailProveedor int not null,
    emailProveedor varchar(50) not null,
    descripcion varchar(100),
    codigoProveedor int ,
    primary key PK_codigoEmailProveedor (codigoEmailProveedor),
    constraint FK_Proveedores_EmailProveedor foreign key (codigoProveedor)
		references Proveedores (codigoProveedor)
        ON DELETE CASCADE
);

-- Se crea la entidad Productos
Create table Productos (
	productoId int not null,
    nombreProducto varchar(50) not null,
    descripcionProducto varchar(100) not null,
    cantidadStock int not null,
	precioVentaUnitario decimal(10,2) not null,
    precioVentaMayor decimal(10,2) not null,
    precioCompra decimal (10,2) not null,
    codigoProveedor int ,
    categoriaProductoId int,
    primary key PK_productoId(productoId),
    constraint FK_Proveedores_Productos foreign key (codigoProveedor)
		references Proveedores(codigoProveedor),
	constraint FK_categoriaProductos_Productos foreign key (categoriaProductoId)
		references categoriaProductos(categoriaProductoId)
        ON DELETE CASCADE
);

-- Se crea la entidad detalleFactura
create table detalleFactura(
	detalleFacturaId int not null,
    facturaId int not null,
    productoId int not null,
    primary key PK_detalleFacturaId (detalleFacturaId),
    constraint FK_Facturas_detalleFactura foreign key (facturaId)
		references Facturas(facturaId),
	constraint FK_Productos_detalleFactura foreign key (productoId)
		references Productos(productoId)
        ON DELETE CASCADE
);


-- Se crea la entidad detalleCompra
create table detalleCompra (
	detalleCompraId int not null,
    cantidadCompra int not null,
    productoId int,
    compraId int,
    primary key detalleCompraId (detalleCompraId),
    constraint FK_Compras_detalleCompra foreign key (compraId)
		references Compras(compraId),
	constraint FK_Productos_detalleCompra foreign key (productoId)
		references Productos(productoId)
        ON DELETE CASCADE
);
-- -------------------- PROCEDIMIENTOS ALMACENADOS ---------------------
-- -------------------- Clientes --------------------------------------
-- ---------------------------------------- Agregar Clientes ---------------------------------------------------------------------------------
Delimiter $$
	create procedure sp_agregarCliente (in CodigoCliente int, NITcliente varchar(10), in nombreCliente varchar(50), in apellidoCliente varchar(50),
    in direccionCliente varchar(250), in telefonoCliente varchar(15), in correoCliente varchar(250))
		Begin 
			Insert into Clientes (CodigoCliente, NITCliente, nombreCliente, apellidoCliente, direccionCliente,
            telefonoCliente, correoCliente) values
            (CodigoCliente, NITCliente, nombreCliente, apellidoCliente, direccionCliente,
            telefonoCliente, correoCliente);
	end $$
Delimiter ;
call sp_AgregarCliente(01, '14587452', 'Harol', 'Rodriguez', 'Zona 7', '41812768','hrodriguez-2023278@kinal.edu.gt');
call sp_AgregarCliente(02, '14587452', 'Oliver', 'Donis', 'Zona 3', '41832768','hrodriguez-2023378@kinal.edu.gt');

INSERT INTO Clientes (CodigoCliente, NITcliente, nombreCliente, apellidoCliente, direccionCliente, telefonoCliente, correoCliente) 
VALUES (2, '0987654321', 'Maria', 'Gomez', 'Calle Falsa 456, Ciudad', '5559876543', 'maria.gomez@example.com');

INSERT INTO Clientes (CodigoCliente, NITcliente, nombreCliente, apellidoCliente, direccionCliente, telefonoCliente, correoCliente) 
VALUES (3, '1230984567', 'Carlos', 'Lopez', 'Av. Libertad 789, Ciudad', '5551230987', 'carlos.lopez@example.com');

INSERT INTO Clientes (CodigoCliente, NITcliente, nombreCliente, apellidoCliente, direccionCliente, telefonoCliente, correoCliente) 
VALUES (4, '0981237654', 'Ana', 'Martinez', 'Calle Principal 321, Ciudad', '5559871234', 'ana.martinez@example.com');

INSERT INTO Clientes (CodigoCliente, NITcliente, nombreCliente, apellidoCliente, direccionCliente, telefonoCliente, correoCliente) 
VALUES (5, '5678901234', 'Luis', 'Garcia', 'Av. Secundaria 654, Ciudad', '5556789012', 'luis.garcia@example.com');

INSERT INTO Clientes (CodigoCliente, NITcliente, nombreCliente, apellidoCliente, direccionCliente, telefonoCliente, correoCliente) 
VALUES (6, '6543210987', 'Elena', 'Rodriguez', 'Calle Tercera 987, Ciudad', '5557890123', 'elena.rodriguez@example.com');

INSERT INTO Clientes (CodigoCliente, NITcliente, nombreCliente, apellidoCliente, direccionCliente, telefonoCliente, correoCliente) 
VALUES (7, '2345678901', 'Miguel', 'Hernandez', 'Av. Cuarta 111, Ciudad', '5552345678', 'miguel.hernandez@example.com');

INSERT INTO Clientes (CodigoCliente, NITcliente, nombreCliente, apellidoCliente, direccionCliente, telefonoCliente, correoCliente) 
VALUES (8, '3456789012', 'Sofia', 'Torres', 'Calle Quinta 222, Ciudad', '5553456789', 'sofia.torres@example.com');

INSERT INTO Clientes (CodigoCliente, NITcliente, nombreCliente, apellidoCliente, direccionCliente, telefonoCliente, correoCliente) 
VALUES (9, '4567890123', 'Jorge', 'Diaz', 'Av. Sexta 333, Ciudad', '5554567890', 'jorge.diaz@example.com');

INSERT INTO Clientes (CodigoCliente, NITcliente, nombreCliente, apellidoCliente, direccionCliente, telefonoCliente, correoCliente) 
VALUES (10, '5678901235', 'Laura', 'Vasquez', 'Calle Séptima 444, Ciudad', '5555678901', 'laura.vasquez@example.com');
-- -----------------------------------------Listsar Clientes -----------------------------------
Delimiter $$
	create procedure sp_ListarClientes()
		Begin
			select
            C.CodigoCliente,
            C.NITCliente,
            C.nombreCliente,
            C.apellidoCliente,
            C.direccionCliente,
            C.telefonoCliente,
            C.correoCliente
            from Clientes C;
		end $$
Delimiter ;
call sp_ListarClientes();
-- -----------------------------Buscar Cliebtes ---------------------------------------------
Delimiter $$
	create procedure sp_BuscarClientes (in cID int)
		Begin
			select
            C.CodigoCliente,
            C.NITCliente,
            C.apellidoCliente,
            C.direccionCliente,
            C.telefonoCliente,
            C.correoCliente
            from Clientes C;
		end $$
Delimiter ;
call sp_BuscarClientes(01);
-- -----------------------------------------------Editar Clientes ------------------------------------------------------
Delimiter $$
	Create procedure sp_EditarCliente(in _CodigoCliente int, _NITcliente varchar(10), in _nombreCliente varchar(50), in _apellidoCliente varchar(50),
    in _direccionCliente varchar(250), in _telefonoCliente varchar(8), in _correoCliente varchar(45))
		Begin
			update Clientes C
				set
                C.CodigoCliente = _CodigoCliente,
                C.NITcliente = _NITcliente,
                C.nombreCliente = _nombreCliente,
                C.apellidoCliente = _apellidoCliente,
                C.direccionCliente = _direccionCliente,
                C.telefonoCliente = _telefonoCliente,
                C.correoCliente = _correoCliente
                where C.codigoCliente = _codigoCliente;
	end$$
    Delimiter ;
call sp_EditarCliente(01,'14587525','Marcelo','Fernandez','Zona 11','50504178','mfernandez-2023278@kinal.edu.gt'); 
-- ------------------------------------Eliminar Clientes -------------------------------------------
Delimiter $$
	Create procedure sp_EliminarClientes (in codCli int)
		Begin 
			Delete from Clientes
				where codigoCliente = codCli;
		End$$
Delimiter  ;
call sp_EliminarClientes(02);
-- --------------------------------Agregar Proveedores ----------------------------------------------
Delimiter $$
	Create procedure sp_AgregarProveedores (in codigoProveedor int, in NITproveedor varchar(10), in nombreProveedor varchar(60),
    in apellidoProveedor varchar(60), in direccionProveedor varchar(150), in razonSocial varchar(60), in contactoPrincipal varchar(50), in paginaWeb varchar (50))
		Begin 
			Insert into Proveedores(codigoProveedor,NITproveedor,nombreProveedor,apellidoProveedor,direccionProveedor,razonSocial,contactoPrincipal,
            paginaWeb) values 
            (codigoProveedor,NITproveedor,nombreProveedor,apellidoProveedor,direccionProveedor,razonSocial,contactoPrincipal,
            paginaWeb);
		end$$
Delimiter ;
call sp_AgregarProveedores(01,'1826400k','Rolando','Espinoza','Zona 7','Vender','41812768','RolandoEspinoza.com');
call sp_AgregarProveedores(02,'1826400k','Ronaldo','Sutuj','Zona 7','Vender','41812328','RonaldoSutuj.com');

INSERT INTO Proveedores (codigoProveedor, NITproveedor, nombreProveedor, apellidoProveedor, direccionProveedor, razonSocial, contactoPrincipal, paginaWeb) 
VALUES (1, '1234567890', 'Carlos', 'Santos', 'Av. Las Flores 123, Ciudad', 'Santos Importaciones', 'carlos.santos@example.com', 'www.santosimport.com');
INSERT INTO Proveedores (codigoProveedor, NITproveedor, nombreProveedor, apellidoProveedor, direccionProveedor, razonSocial, contactoPrincipal, paginaWeb) 
VALUES (3, '5678901234', 'Miguel', 'Hernandez', 'Av. Libertad 789, Ciudad', 'Hernandez y Cia', 'miguel.hernandez@example.com', 'www.hernandezycia.com');

INSERT INTO Proveedores (codigoProveedor, NITproveedor, nombreProveedor, apellidoProveedor, direccionProveedor, razonSocial, contactoPrincipal, paginaWeb) 
VALUES (4, '3456789012', 'Ana', 'Rodriguez', 'Calle Secundaria 321, Ciudad', 'Rodriguez Proveedores', 'ana.rodriguez@example.com', 'www.rodriguezproveedores.com');

INSERT INTO Proveedores (codigoProveedor, NITproveedor, nombreProveedor, apellidoProveedor, direccionProveedor, razonSocial, contactoPrincipal, paginaWeb) 
VALUES (5, '2345678901', 'Luis', 'Gomez', 'Av. Tercera 654, Ciudad', 'Gomez y Asociados', 'luis.gomez@example.com', 'www.gomezyasociados.com');

INSERT INTO Proveedores (codigoProveedor, NITproveedor, nombreProveedor, apellidoProveedor, direccionProveedor, razonSocial, contactoPrincipal, paginaWeb) 
VALUES (6, '6789012345', 'Sofia', 'Martinez', 'Calle Cuarta 987, Ciudad', 'Martinez Suministros', 'sofia.martinez@example.com', 'www.martinezsuministros.com');

INSERT INTO Proveedores (codigoProveedor, NITproveedor, nombreProveedor, apellidoProveedor, direccionProveedor, razonSocial, contactoPrincipal, paginaWeb) 
VALUES (7, '7890123456', 'Jorge', 'Lopez', 'Av. Quinta 111, Ciudad', 'Lopez Trading', 'jorge.lopez@example.com', 'www.lopeztrading.com');

INSERT INTO Proveedores (codigoProveedor, NITproveedor, nombreProveedor, apellidoProveedor, direccionProveedor, razonSocial, contactoPrincipal, paginaWeb) 
VALUES (8, '8901234567', 'Elena', 'Vasquez', 'Calle Sexta 222, Ciudad', 'Vasquez y Compañia', 'elena.vasquez@example.com', 'www.vasquezycompania.com');

INSERT INTO Proveedores (codigoProveedor, NITproveedor, nombreProveedor, apellidoProveedor, direccionProveedor, razonSocial, contactoPrincipal, paginaWeb) 
VALUES (9, '9012345678', 'Mario', 'Garcia', 'Av. Séptima 333, Ciudad', 'Garcia Proveedores', 'mario.garcia@example.com', 'www.garciaproveedores.com');

INSERT INTO Proveedores (codigoProveedor, NITproveedor, nombreProveedor, apellidoProveedor, direccionProveedor, razonSocial, contactoPrincipal, paginaWeb) 
VALUES (10, '0123456789', 'Lucia', 'Torres', 'Calle Octava 444, Ciudad', 'Torres Exportaciones', 'lucia.torres@example.com', 'www.torresexport.com');
-- ----------------------------------- Listar Proveedores -------------------------------------------------------------------
Delimiter $$
	Create procedure sp_ListarProveedores()
		Begin
			Select
				
            P.codigoProveedor,
            P.NITproveedor,
            P.nombreProveedor,
            P.apellidoProveedor,
            P.direccionProveedor,
            P.razonSocial,
            P.contactoPrincipal,
            P.paginaWeb
            from Proveedores P;
		end $$
Delimiter ;
call sp_ListarProveedores();
-- ---------------------------------Buscar Proveedores ----------------------------------------------------------------------
Delimiter $$
	Create procedure sp_BuscarProveedor(in pID int)
		Begin
			Select 
            P.codigoProveedor,
            P.NITproveedor,
            P.nombreProveedor,
            P.apellidoProveedor,
            P.direccionProveedor,
            P.razonSocial,
            P.contactoPrincipal,
            P.paginaWeb
            from Proveedores P;
		end $$
Delimiter ;
call sp_BuscarProveedor(01);
-- -----------------------------------------Editar Proveedor --------------------------------------------------
Delimiter $$
	Create procedure sp_EditarProveedor(in _codigoProveedor int, in _NITproveedor varchar(10), in _nombreProveedor varchar(60),
    in _apellidoProveedor varchar(60), in _direccionProveedor varchar(150), in _razonSocial varchar(60), in _contactoPrincipal varchar(50), in _paginaWeb varchar (50))
		Begin
			update Proveedores P
				set
                P.codigoProveedor = _codigoProveedor,
                p.NITproveedor = _NITproveedor,
                p.nombreProveedor = _nombreProveedor,
                p.apellidoProveedor = _apellidoProveedor,
                p.direccionProveedor = _direccionProveedor,
                P.razonSocial = _razonSocial,
                P.contactoPrincipal = contactoPrincipal,
                P.paginaWeb = _paginaWeb
                where P.codigoProveedor = _codigoProveedor;
	end $$
Delimiter ;
call sp_EditarProveedor(01,'1856400k','Rolando','Espinoza','Zona 13','Ganar Dinero','41812768','RolandoEspinoza.com');
-- --------------------------------------------- Eliminar Proveedor -----------------------------------------------------------
Delimiter $$
	Create procedure sp_EliminarProveedor(in codPro int)
		Begin
			Delete from Proveedores
            where codigoProveedor = codPro;
	end$$
Delimiter ;
call sp_EliminarProveedor(01);

-- ------------------------------------------ Agrear Compra------------------------------------------------------------------------
Delimiter $$
	Create procedure sp_AgregarCompra(in compraId int, in fechaCompra date, in totalCompra decimal (10,2))
		begin
			Insert into Compras(compraId, fechaCompra,totalCompra)
				values(compraId, fechaCompra,totalCompra);
		End$$
Delimiter ;
call sp_AgregarCompra(01,'2021-04-22','10000.00');
call sp_AgregarCompra(02,'2022-05-13','5000.00');

INSERT INTO Compras (compraId, fechaCompra, totalCompra) 
VALUES (1, '2024-01-15', 1500.50);
INSERT INTO Compras (compraId, fechaCompra, totalCompra) 
VALUES (3, '2024-03-10', 3200.00);

INSERT INTO Compras (compraId, fechaCompra, totalCompra) 
VALUES (4, '2024-04-05', 1800.25);

INSERT INTO Compras (compraId, fechaCompra, totalCompra) 
VALUES (5, '2024-05-12', 2100.50);

INSERT INTO Compras (compraId, fechaCompra, totalCompra) 
VALUES (6, '2024-06-18', 2750.00);

INSERT INTO Compras (compraId, fechaCompra, totalCompra) 
VALUES (7, '2024-07-22', 4000.75);

INSERT INTO Compras (compraId, fechaCompra, totalCompra) 
VALUES (8, '2024-08-15', 3350.50);

INSERT INTO Compras (compraId, fechaCompra, totalCompra) 
VALUES (9, '2024-09-10', 2900.00);

INSERT INTO Compras (compraId, fechaCompra, totalCompra) 
VALUES (10, '2024-10-05', 4500.25);
-- ----------------------------------------- Listar Compras ------------------------------------------------------------------------------
Delimiter $$
	Create procedure sp_ListarCompras()
		begin
			select
            C.compraId,
            C.fechaCompra,
            C.totalCompra
            from Compras C;
		End $$
Delimiter ;
call sp_ListarCompras();
-- ------------------------------------------ Buscar Compras -------------------------------------------------------------------------
Delimiter $$
	Create procedure sp_BuscarCompras(in cID int)
		begin
			select
            C.compraId,
            C.fechaCompra,
            C.totalCompra
            from Compras C;
		End $$
Delimiter ;
call sp_BuscarCompras(01);
-- ---------------------------------------- Editar compras ------------------------------------------------------------------------------
Delimiter $$
	Create procedure sp_EditarCompra(in _compraId int, in _fechaCompra date, in _totalCompra decimal (10,2))
		begin
			Update Compras C
				set 
                C.compraId = _compraId,
                C.fechaCompra = _fechaCompra,
                C.totalCompra = _totalCompra
                where C.compraId = _compraId;
		End$$
Delimiter ;
call sp_EditarCompra(01,'2022-04-22','9500.00');
-- ------------------------------------------Eliminar Compra ------------------------------------------------------------------------
Delimiter $$
	Create procedure sp_EliminarCompra(in compID int)
		begin 
			delete from Compras
            where compraId = compID;
		end$$
Delimiter ;
call sp_EliminarCompra(01);
-- ----------------------------------------------Agregar CategoriaProductos -----------------------------------------------------
Delimiter $$
	Create procedure sp_agregarCategoriaProductos(in categoriaProductoId int, in nombreCategoria varchar(30), in descripcionCategoria varchar(100))
		Begin
			Insert into categoriaProductos(categoriaProductoId, nombreCategoria , descripcionCategoria)
				values (categoriaProductoId, nombreCategoria , descripcionCategoria);
		End$$
Delimiter ;
call sp_agregarCategoriaProductos(01,'Vegetales','Esta categoria esta compuesta por todo tipo de vegetal');
call sp_agregarCategoriaProductos(02,'Carnes','Esta categoria esta compuesta por las diferentes clases de carnes');

INSERT INTO categoriaProductos (categoriaProductoId, nombreCategoria, descripcionCategoria) 
VALUES (1, 'Electrónica', 'Dispositivos electrónicos y gadgets.');

INSERT INTO categoriaProductos (categoriaProductoId, nombreCategoria, descripcionCategoria) 
VALUES (3, 'Alimentos', 'Productos comestibles y bebidas.');

INSERT INTO categoriaProductos (categoriaProductoId, nombreCategoria, descripcionCategoria) 
VALUES (4, 'Hogar', 'Artículos para el hogar y decoración.');

INSERT INTO categoriaProductos (categoriaProductoId, nombreCategoria, descripcionCategoria) 
VALUES (5, 'Deportes', 'Equipo y ropa deportiva.');

INSERT INTO categoriaProductos (categoriaProductoId, nombreCategoria, descripcionCategoria) 
VALUES (6, 'Juguetes', 'Juguetes y juegos para niños.');

INSERT INTO categoriaProductos (categoriaProductoId, nombreCategoria, descripcionCategoria) 
VALUES (7, 'Libros', 'Libros y material de lectura.');

INSERT INTO categoriaProductos (categoriaProductoId, nombreCategoria, descripcionCategoria) 
VALUES (8, 'Salud', 'Productos de salud y cuidado personal.');

INSERT INTO categoriaProductos (categoriaProductoId, nombreCategoria, descripcionCategoria) 
VALUES (9, 'Automotriz', 'Accesorios y repuestos para vehículos.');

INSERT INTO categoriaProductos (categoriaProductoId, nombreCategoria, descripcionCategoria) 
VALUES (10, 'Tecnología', 'Productos y servicios tecnológicos.');
-- ---------------------------------------------- Listar categorias--------------------------------------------------------------------------
Delimiter $$ 
	Create procedure sp_ListarCategorias()
		begin
			Select
			C.categoriaProductoId,
            C.nombreCategoria,
            C.descripcionCategoria
            from categoriaProductos C;
	End$$
Delimiter ;
call sp_ListarCategorias();
-- ----------------------------------------- Buscar Categorias ------------------------------------------------------------------------
Delimiter $$ 
	Create procedure sp_BuscarCategorias(in cID int)
		begin
			Select
			C.categoriaProductoId,
            C.nombreCategoria,
            C.descripcionCategoria
            from categoriaProductos C;
	End$$
Delimiter ;
call sp_BuscarCategorias(01);
-- --------------------------------------------- Editar Categorias -------------------------------------------------------------------------
Delimiter $$
	Create procedure sp_editarCategoria(in _categoriaProductoId int, in _nombreCategoria varchar(30), in _descripcionCategoria varchar(100))
		Begin
			Update categoriaProductos C
				Set
                C.categoriaProductoId = _categoriaProductoId,
                C.nombreCategoria = _nombreCategoria,
                C.descripcionCategoria = _descripcionCategoria
                where C.categoriaProductoId = _categoriaProductoId;
	End$$
Delimiter ;
call sp_editarCategoria(01,'Vegetales Españoles','Solo los vegetales producidos en españa estan en este campo');
-- ------------------------------------------------- Eliminar Categoria --------------------------------------------------------------------------
Delimiter $$
	Create procedure sp_ElimiarCategoria(in catPro int)
		Begin
			Delete from categoriaProductos
            where categoriaProductoId = catPro;
		end$$
Delimiter ;
call sp_ElimiarCategoria(01);
-- ---------------------------------------------------- Agregar Cargo ------------------------------------------------------------------------------------
Delimiter $$
	Create procedure sp_agregarCargo(in cargoId int, in nombreCargo varchar (30), in descripcionCargo varchar (100))
		Begin
			Insert into Cargos (cargoId, nombreCargo, descripcionCargo)
				values (cargoId, nombreCargo, descripcionCargo);
		End$$
Delimiter ;
call sp_agregarCargo(01,'Encargado de Ventas','La persona en este puesto es la encargada de gestionar el area de ventas');
call sp_agregarCargo(02,'Jefe Informatica','La persona en este puesto es la encargada de dirigir todo el departamento de informatica');

INSERT INTO Cargos (cargoId, nombreCargo, descripcionCargo) 
VALUES (1, 'Gerente General', 'Responsable de la dirección y administración general.');
INSERT INTO Cargos (cargoId, nombreCargo, descripcionCargo) 
VALUES (3, 'Desarrollador', 'Desarrollo y mantenimiento de software.');

INSERT INTO Cargos (cargoId, nombreCargo, descripcionCargo) 
VALUES (4, 'Soporte Técnico', 'Atención y resolución de problemas técnicos.');

INSERT INTO Cargos (cargoId, nombreCargo, descripcionCargo) 
VALUES (5, 'Diseñador Gráfico', 'Creación de material gráfico y visual.');

INSERT INTO Cargos (cargoId, nombreCargo, descripcionCargo) 
VALUES (6, 'Vendedor', 'Responsable de ventas y atención al cliente.');

INSERT INTO Cargos (cargoId, nombreCargo, descripcionCargo) 
VALUES (7, 'Recursos Humanos', 'Gestión de personal y administración de recursos humanos.');

INSERT INTO Cargos (cargoId, nombreCargo, descripcionCargo) 
VALUES (8, 'Marketing', 'Desarrollo y ejecución de estrategias de marketing.');

INSERT INTO Cargos (cargoId, nombreCargo, descripcionCargo) 
VALUES (9, 'Analista de Datos', 'Análisis e interpretación de datos empresariales.');

INSERT INTO Cargos (cargoId, nombreCargo, descripcionCargo) 
VALUES (10, 'Logística', 'Coordinación y gestión de operaciones logísticas.');
-- --------------------------------------------- Listar Cargos --------------------------------------------------------------------------------
Delimiter $$
	Create procedure sp_listarCargos()
		Begin
			Select 
            C.cargoId,
            C.nombreCargo,
            C.descripcionCargo
            from Cargos C;
	End $$
Delimiter ;
call sp_listarCargos();
-- ------------------------------------------ Buscar Cargo -------------------------------------------------------------------------------------
Delimiter $$
	Create procedure sp_buscarCargos(in cID int)
		Begin
			Select 
            C.cargoId,
            C.nombreCargo,
            C.descripcionCargo
            from Cargos C;
	End $$
Delimiter ;
call sp_buscarCargos(01);
-- -----------------------------------------------Editar Cargo--------------------------------------------------------------------------------
Delimiter $$
	Create procedure sp_editarCargo(in _cargoId int, in _nombreCargo varchar (30), in _descripcionCargo varchar (100))
		Begin
			Update Cargos C
				Set
                C.cargoId = _cargoId,
                C.nombreCargo = _nombreCargo,
                C.descripcionCargo = _descripcionCargo
                where C.cargoId = _cargoId;
	End$$
Delimiter ;
call sp_editarCargo(01,'Administrador de Almacen','El encargado de almacen gestiona todo lo relacionado a ventas e invemtarios');
-- ------------------------------------------------Eliminar Cargo ---------------------------------------------------------------------------
Delimiter $$
	Create procedure sp_eliminarCargo(in carID int)
		Begin
			Delete from Cargos
				where cargoId = carID;
		End$$
Delimiter ;
call sp_eliminarCargo(01);
-- ------------------------------------------------ Agregar Empleados ---------------------------------------------------------------------
Delimiter $$
	Create procedure sp_agregarEmpleados(in empleadoId int, in nombreEmpleado varchar (30), in apellidoEmpleado varchar(30), in  sueldo decimal(10,2),
    in horaEntrada time, in horaSalida time, in cargoId int)
		Begin
			Insert Into Empleados(empleadoId, nombreEmpleado, apellidoEmpleado, sueldo, horaEntrada, horaSalida, cargoId)
             values (empleadoId, nombreEmpleado, apellidoEmpleado, sueldo, horaEntrada, horaSalida, cargoId);
		End$$
Delimiter ;
call sp_agregarEmpleados(01, 'Juan', 'Perez', 2000.00, '08:00:00', '17:00:00', 02 );
call sp_agregarEmpleados(02, 'María', 'González', 1800.00, '09:00:00', '18:00:00', 02);

INSERT INTO Empleados (empleadoId, nombreEmpleado, apellidoEmpleado, sueldo, horaEntrada, horaSalida, cargoId) 
VALUES (1, 'Juan', 'Perez', 3500.00, '08:00:00', '16:00:00', 1);
INSERT INTO Empleados (empleadoId, nombreEmpleado, apellidoEmpleado, sueldo, horaEntrada, horaSalida, cargoId) 
VALUES (3, 'Carlos', 'Lopez', 3200.75, '10:00:00', '18:00:00', 3);

INSERT INTO Empleados (empleadoId, nombreEmpleado, apellidoEmpleado, sueldo, horaEntrada, horaSalida, cargoId) 
VALUES (4, 'Ana', 'Martinez', 2500.00, '07:00:00', '15:00:00', 4);

INSERT INTO Empleados (empleadoId, nombreEmpleado, apellidoEmpleado, sueldo, horaEntrada, horaSalida, cargoId) 
VALUES (5, 'Luis', 'Garcia', 3000.25, '08:30:00', '16:30:00', 5);

INSERT INTO Empleados (empleadoId, nombreEmpleado, apellidoEmpleado, sueldo, horaEntrada, horaSalida, cargoId) 
VALUES (6, 'Elena', 'Rodriguez', 2700.50, '09:00:00', '17:00:00', 6);

INSERT INTO Empleados (empleadoId, nombreEmpleado, apellidoEmpleado, sueldo, horaEntrada, horaSalida, cargoId) 
VALUES (7, 'Miguel', 'Hernandez', 2600.75, '10:00:00', '18:00:00', 7);

INSERT INTO Empleados (empleadoId, nombreEmpleado, apellidoEmpleado, sueldo, horaEntrada, horaSalida, cargoId) 
VALUES (8, 'Sofia', 'Torres', 2400.00, '08:00:00', '16:00:00', 8);

INSERT INTO Empleados (empleadoId, nombreEmpleado, apellidoEmpleado, sueldo, horaEntrada, horaSalida, cargoId) 
VALUES (9, 'Jorge', 'Diaz', 3300.25, '09:30:00', '17:30:00', 9);

INSERT INTO Empleados (empleadoId, nombreEmpleado, apellidoEmpleado, sueldo, horaEntrada, horaSalida, cargoId) 
VALUES (10, 'Laura', 'Vasquez', 2900.50, '08:00:00', '16:00:00', 10);
-- -------------------------------------------------- Listar empleados ----------------------------------------------------------------------
Delimiter $$
Create procedure sp_listarEmpleados()
    Begin
        Select 
            E.empleadoId,
            E.nombreEmpleado,
            E.apellidoEmpleado,
            E.sueldo,
            E.horaEntrada,
            E.horaSalida,
            E.cargoId
        from Empleados E;
    End $$
Delimiter ;
call sp_listarEmpleados();
-- -------------------------------------------  Buscar Empleado -----------------------------------------------------------------------------------
Delimiter $$
Create procedure sp_buscarEmpleados(in eID int)
    Begin
        Select 
            E.empleadoId,
            E.nombreEmpleado,
            E.apellidoEmpleado,
            E.sueldo,
            E.horaEntrada,
            E.horaSalida,
            E.cargoId
        from Empleados E;
    End $$
Delimiter ;
call sp_buscarEmpleados(01);
-- ----------------------------------------------Editar Empleado -----------------------------------------------------------------------------------
Delimiter $$
Create procedure sp_editarEmpleado(in _empleadoId int, in _nombreEmpleado varchar(30), in _apellidoEmpleado varchar(30), in _sueldo decimal(10,2), 
in _horaEntrada time, in _horaSalida time, in _cargoId int)
    Begin
        Update Empleados E
            Set
            E.nombreEmpleado = _nombreEmpleado,
            E.apellidoEmpleado = _apellidoEmpleado,
            E.sueldo = _sueldo,
            E.horaEntrada = _horaEntrada,
            E.horaSalida = _horaSalida,
            E.cargoId = _cargoId
            where E.empleadoId = _empleadoId;
    End$$
Delimiter ;
call sp_editarEmpleado(01, 'Juan', 'López', 2500.00, '08:30:00', '17:30:00', 02);
-- -------------------------------------------------- Eliminar empleado ----------------------------------------------------------------------------------------
Delimiter $$
Create procedure sp_eliminarEmpleado(in empID int)
    Begin
        Delete from Empleados 
			where empleadoId = empID;
    End$$
Delimiter ;
call sp_eliminarEmpleado(01);
-- ---------------------------------------------- Agregar Factura ----------------------------------------------------------------------------------------
Delimiter $$
	Create procedure sp_agregarFactura(in facturaId int, in fecha date, in hora time, in total decimal(10,2), in codigoCliente int, in empleadoId int)
		Begin
			Insert Into Facturas(facturaId, facha, hora, total, codigoCliente, empleadoId)
			values (facturaId, fecha, hora, total, codigoCliente, empleadoId);
		End$$
Delimiter ;
call sp_agregarFactura(01, '2024-04-23', '14:30:00', 150.50, 01, 02);
call sp_agregarFactura(02, '2024-04-24', '10:00:00', 300.75, 01, 02);
call sp_agregarFactura(03, '2024-04-25', '12:45:00', 250.00, 01, 02);

INSERT INTO Facturas (facturaId, facha, hora, total, codigoCliente, empleadoId) 
VALUES (1, '2024-01-10', '10:30:00', 150.75, 1, 1);
INSERT INTO Facturas (facturaId, facha, hora, total, codigoCliente, empleadoId) 
VALUES (4, '2024-01-25', '13:15:00', 400.00, 4, 4);

INSERT INTO Facturas (facturaId, facha, hora, total, codigoCliente, empleadoId) 
VALUES (5, '2024-01-30', '14:00:00', 500.75, 5, 5);

INSERT INTO Facturas (facturaId, facha, hora, total, codigoCliente, empleadoId) 
VALUES (6, '2024-02-05', '15:30:00', 250.50, 6, 6);

INSERT INTO Facturas (facturaId, facha, hora, total, codigoCliente, empleadoId) 
VALUES (7, '2024-02-10', '16:45:00', 300.25, 7, 7);

INSERT INTO Facturas (facturaId, facha, hora, total, codigoCliente, empleadoId) 
VALUES (8, '2024-02-15', '17:00:00', 450.00, 8, 8);

INSERT INTO Facturas (facturaId, facha, hora, total, codigoCliente, empleadoId) 
VALUES (9, '2024-02-20', '18:30:00', 350.75, 9, 9);

INSERT INTO Facturas (facturaId, facha, hora, total, codigoCliente, empleadoId) 
VALUES (10, '2024-02-25', '19:00:00', 400.50, 10, 10);
-- ----------------------------------------------- Listar Factura ------------------------------------------------------------------------------------
Delimiter $$
	Create procedure sp_listarFacturas()
		Begin
			Select 
				F.facturaId,
				F.facha,
				F.hora,
				F.total,
				F.codigoCliente,
				F.empleadoId
			from Facturas F;
		End $$
Delimiter ;
call sp_listarFacturas();
-- ----------------------------------------------Buscar Factura -------------------------------------------------------------------------------
Delimiter $$
	Create procedure sp_buscarFacturas(in fID int)
		Begin
			Select 
				F.facturaId,
				F.facha,
				F.hora,
				F.total,
				F.codigoCliente,
				F.empleadoId
			from Facturas F;
		End $$
Delimiter ;
call sp_buscarFacturas(01);
-- ----------------------------------------------- editar Factura -----------------------------------------------------------------------------
Delimiter $$
	Create procedure sp_editarFactura(in _facturaId int, in _fecha date, in _hora time, in _total decimal(10,2), in _codigoCliente int, in _empleadoId int)
		Begin
			Update Facturas
			Set facha = _fecha,
				hora = _hora,
				total = _total,
				codigoCliente = _codigoCliente,
				empleadoId = _empleadoId
			where facturaId = _facturaId;
		End$$
Delimiter ;
call sp_editarFactura(01, '2024-04-24', '15:00:00', 200.00, 01, 02);
-- ------------------------------------------------------- Eliminar Factura ---------------------------------------------------------------------------
Delimiter $$
	Create procedure sp_eliminarFactura(in facID int)
		Begin
			Delete from Facturas 
            where facturaId = facID;
		End$$
Delimiter ;
call sp_eliminarFactura(01);

-- -------------------------------------------------Agregar Telefono proveedo---------------------------------------------------------------------
 Delimiter $$
Create procedure sp_agregarTelefonoProveedor(in codigoTelefonoProveedor int, in numeroPrincipal varchar(8), in numeroSecundario varchar(8), 
                                              in observaciones varchar(45), in codigoProveedor int)
    Begin
        Insert Into telefonoProveedor(codigoTelefonoProveedor, numeroPrincipal, numeroSecundario, observaciones, codigoProveedor)
        values (codigoTelefonoProveedor, numeroPrincipal, numeroSecundario, observaciones, codigoProveedor);
    End$$
Delimiter ;
call sp_agregarTelefonoProveedor(01, '12345678', '87654321', 'Número principal y secundario', 02);
call sp_agregarTelefonoProveedor(02, '98765432', '41812768', 'Número principal', 02);
call sp_agregarTelefonoProveedor(03, '87654321', '23456789', 'Número principal y secundario', 02);

INSERT INTO telefonoProveedor (codigoTelefonoProveedor, numeroPrincipal, numeroSecundario, observaciones, codigoProveedor) 
VALUES (1, '5551234', '5555678', 'Contacto principal', 1);
INSERT INTO telefonoProveedor (codigoTelefonoProveedor, numeroPrincipal, numeroSecundario, observaciones, codigoProveedor) 
VALUES (4, '5554567', '5558901', 'Número directo del proveedor', 4);

INSERT INTO telefonoProveedor (codigoTelefonoProveedor, numeroPrincipal, numeroSecundario, observaciones, codigoProveedor) 
VALUES (5, '5555678', '5559012', 'Disponible 24/7', 5);

INSERT INTO telefonoProveedor (codigoTelefonoProveedor, numeroPrincipal, numeroSecundario, observaciones, codigoProveedor) 
VALUES (6, '5556789', '5550123', 'Contacto secundario', 6);

INSERT INTO telefonoProveedor (codigoTelefonoProveedor, numeroPrincipal, numeroSecundario, observaciones, codigoProveedor) 
VALUES (7, '5557890', '5551234', 'Número principal', 7);

INSERT INTO telefonoProveedor (codigoTelefonoProveedor, numeroPrincipal, numeroSecundario, observaciones, codigoProveedor) 
VALUES (8, '5558901', '5552345', 'Usar fuera de horario de oficina', 8);

INSERT INTO telefonoProveedor (codigoTelefonoProveedor, numeroPrincipal, numeroSecundario, observaciones, codigoProveedor) 
VALUES (9, '5559012', '5553456', 'Preferido para emergencias', 9);

INSERT INTO telefonoProveedor (codigoTelefonoProveedor, numeroPrincipal, numeroSecundario, observaciones, codigoProveedor) 
VALUES (10, '5550123', '5554567', 'Número del gerente', 10);
-- -------------------------------------------------- Listar Telefonos Proveedores ------------------------------*----------------------------------
Delimiter $$
Create procedure sp_listarTelefonoProveedor()
    Begin
        Select 
            TP.codigoTelefonoProveedor,
            TP.numeroPrincipal,
            TP.numeroSecundario,
            TP.observaciones,
            TP.codigoProveedor
        from telefonoProveedor TP;
    End$$
Delimiter ;
call sp_listarTelefonoProveedor();
-- ------------------------------------------ Buscar telefono Proveedor --------------------------------------------------------------------------
Delimiter $$
Create procedure sp_buscarTelefonoProveedor(in tID int)
    Begin
        Select 
            TP.codigoTelefonoProveedor,
            TP.numeroPrincipal,
            TP.numeroSecundario,
            TP.observaciones,
            TP.codigoProveedor
        from telefonoProveedor TP;
    End$$
Delimiter ;
call sp_buscarTelefonoProveedor(01);
-- ----------------------------------------------------- Editar telefono Proveedor --------------------------------------------------------
Delimiter $$
Create procedure sp_editarTelefonoProveedor(in _codigoTelefonoProveedor int, in _numeroPrincipal varchar(8), in _numeroSecundario varchar(8), 
                                            in _observaciones varchar(45), in _codigoProveedor int)
    Begin
        Update telefonoProveedor TP
        Set
        TP.numeroPrincipal = _numeroPrincipal,
        TP.numeroSecundario = _numeroSecundario,
        TP.observaciones = _observaciones,
        TP.codigoProveedor = _codigoProveedor
        where TP.codigoTelefonoProveedor = _codigoTelefonoProveedor;
    End$$
Delimiter ;
call sp_editarTelefonoProveedor(01, '87654321', '12345678', 'Número principal y secundario', 02);
-- ----------------------------------------------- Eliminar telefono Proveedor -------------------------------------------------------------
Delimiter $$
Create procedure sp_eliminarTelefonoProveedor(in codTP int)
    Begin
        Delete from telefonoProveedor 
        where codigoTelefonoProveedor = codTP;
    End$$
Delimiter ;

call sp_eliminarTelefonoProveedor(01);
-- -*---------------------------------------------- Agregar Email Proveedor ---------------------------------------------------------------
Delimiter $$
Create procedure sp_agregarEmailProveedor(in codigoEmailProveedor int, in emailProveedor varchar(50), in descripcion varchar(100), in codigoProveedor int)
    Begin
        Insert Into EmailProveedor(codigoEmailProveedor, emailProveedor, descripcion, codigoProveedor)
        values (codigoEmailProveedor, emailProveedor, descripcion, codigoProveedor);
    End$$
Delimiter ;
call sp_agregarEmailProveedor(01, 'proveedor1@example.com', 'Correo principal', 02);
call sp_agregarEmailProveedor(02, 'proveedor2@example.com', 'Correo principal', 02);
call sp_agregarEmailProveedor(03, 'proveedor3@example.com', 'Correo principal', 02);

INSERT INTO EmailProveedor (codigoEmailProveedor, emailProveedor, descripcion, codigoProveedor) 
VALUES (1, 'info@santosimport.com', 'Email principal de contacto', 1);
INSERT INTO EmailProveedor (codigoEmailProveedor, emailProveedor, descripcion, codigoProveedor) 
VALUES (4, 'contacto@rodriguezproveedores.com', 'Contacto general', 4);

INSERT INTO EmailProveedor (codigoEmailProveedor, emailProveedor, descripcion, codigoProveedor) 
VALUES (5, 'info@gomezyasociados.com', 'Información general', 5);

INSERT INTO EmailProveedor (codigoEmailProveedor, emailProveedor, descripcion, codigoProveedor) 
VALUES (6, 'ventas@martinezsuministros.com', 'Ventas y comercial', 6);

INSERT INTO EmailProveedor (codigoEmailProveedor, emailProveedor, descripcion, codigoProveedor) 
VALUES (7, 'atencion@lopeztrading.com', 'Atención al cliente', 7);

INSERT INTO EmailProveedor (codigoEmailProveedor, emailProveedor, descripcion, codigoProveedor) 
VALUES (8, 'info@vasquezycompania.com', 'Información y contacto', 8);

INSERT INTO EmailProveedor (codigoEmailProveedor, emailProveedor, descripcion, codigoProveedor) 
VALUES (9, 'ventas@garciaproveedores.com', 'Ventas y distribución', 9);

INSERT INTO EmailProveedor (codigoEmailProveedor, emailProveedor, descripcion, codigoProveedor) 
VALUES (10, 'soporte@torresexport.com', 'Soporte y asistencia', 10);
-- ----------------------------------------------- Listar Emaail Proveedores ---------------------------------------------------------------------------
Delimiter $$
Create procedure sp_listarEmailProveedor()
    Begin
        Select 
            EP.codigoEmailProveedor,
            EP.emailProveedor,
            EP.descripcion,
            EP.codigoProveedor
        from EmailProveedor EP;
    End$$
Delimiter ;
call sp_listarEmailProveedor();
-- ------------------------------------------- Buscar Email Proveedor---------------------------------------------------------------------------
Delimiter $$
Create procedure sp_buscarEmailProveedor(in ePID int)
    Begin
        Select 
            EP.codigoEmailProveedor,
            EP.emailProveedor,
            EP.descripcion,
            EP.codigoProveedor
        from EmailProveedor EP;
    End$$
Delimiter ;
call sp_buscarEmailProveedor(01);
-- ------------------------------------------- Editar Email Proveedor -----------------------------------------------------------------------
Delimiter $$
Create procedure sp_editarEmailProveedor(in _codigoEmailProveedor int, in _emailProveedor varchar(50), in _descripcion varchar(100), in _codigoProveedor int)
    Begin
        Update EmailProveedor EP
        Set
        EP.emailProveedor = _emailProveedor,
        EP.descripcion = _descripcion,
        EP.codigoProveedor = _codigoProveedor
        where EP.codigoEmailProveedor = _codigoEmailProveedor;
    End$$
Delimiter ;
call sp_editarEmailProveedor(01, 'proveedor1@example.com', 'Correo secundario', 02);
-- --------------------------------------------------------- Eliminar Email Proveedor --------------------------------------------------------------
Delimiter $$
Create procedure sp_eliminarEmailProveedor(in codEP int)
    Begin
        Delete from EmailProveedor 
        where codigoEmailProveedor = codEP;
    End$$
Delimiter ;
call sp_eliminarEmailProveedor(01);

-- ----------------------------------------------------- Agregar Producto ---------------------------------------------------------------------
Delimiter $$
	Create procedure sp_agregarProducto(in _productoId int, in _nombreProducto varchar(50), in _descripcionProducto varchar(100), 
	in _cantidadStock int, in _precioVentaUnitario decimal(10,2), in _precioVentaMayor decimal(10,2), in _precioCompra decimal(10,2), 
    in _codigoProveedor int, in _categoriaProductoId int)
		Begin
			Insert Into Productos(productoId, nombreProducto, descripcionProducto, cantidadStock, precioVentaUnitario, 
            precioVentaMayor, precioCompra, codigoProveedor, categoriaProductoId)
			values (_productoId, _nombreProducto, _descripcionProducto, _cantidadStock, _precioVentaUnitario, _precioVentaMayor,
            _precioCompra, _codigoProveedor, _categoriaProductoId);
		End$$
Delimiter ;
call sp_agregarProducto(01, 'Laptop', 'Laptop de última generación', 10, 1200.00, 1100.00, 1000.00, 02, 02);
call sp_agregarProducto(2, 'Teléfono móvil', 'Teléfono inteligente de gama media', 20, 500.00, 450.00, 400.00, 02, 02);
call sp_agregarProducto(3, 'Tablet', 'Tablet con pantalla HD', 15, 300.00, 280.00, 250.00, 02, 02);

INSERT INTO Productos (productoId, nombreProducto, descripcionProducto, cantidadStock, precioVentaUnitario, precioVentaMayor, precioCompra, codigoProveedor, categoriaProductoId) 
VALUES (1, 'Laptop HP', 'Laptop HP de 15 pulgadas', 50, 700.00, 650.00, 600.00, 1, 1);
INSERT INTO Productos (productoId, nombreProducto, descripcionProducto, cantidadStock, precioVentaUnitario, precioVentaMayor, precioCompra, codigoProveedor, categoriaProductoId) 
VALUES (4, 'Café Colombiano', 'Café premium de Colombia', 100, 12.00, 10.00, 8.00, 4, 3);

INSERT INTO Productos (productoId, nombreProducto, descripcionProducto, cantidadStock, precioVentaUnitario, precioVentaMayor, precioCompra, codigoProveedor, categoriaProductoId) 
VALUES (5, 'Sofá de Cuero', 'Sofá de cuero de 3 plazas', 20, 800.00, 750.00, 700.00, 5, 4);

INSERT INTO Productos (productoId, nombreProducto, descripcionProducto, cantidadStock, precioVentaUnitario, precioVentaMayor, precioCompra, codigoProveedor, categoriaProductoId) 
VALUES (6, 'Balón de Fútbol', 'Balón de fútbol profesional', 150, 30.00, 25.00, 20.00, 6, 5);

INSERT INTO Productos (productoId, nombreProducto, descripcionProducto, cantidadStock, precioVentaUnitario, precioVentaMayor, precioCompra, codigoProveedor, categoriaProductoId) 
VALUES (7, 'Juguete Lego', 'Set de construcción Lego', 120, 50.00, 45.00, 40.00, 7, 6);

INSERT INTO Productos (productoId, nombreProducto, descripcionProducto, cantidadStock, precioVentaUnitario, precioVentaMayor, precioCompra, codigoProveedor, categoriaProductoId) 
VALUES (8, 'Novela Bestseller', 'Novela de autor reconocido', 300, 15.00, 12.00, 10.00, 8, 7);

INSERT INTO Productos (productoId, nombreProducto, descripcionProducto, cantidadStock, precioVentaUnitario, precioVentaMayor, precioCompra, codigoProveedor, categoriaProductoId) 
VALUES (9, 'Vitaminas Multivitamínicas', 'Suplemento de vitaminas y minerales', 500, 20.00, 18.00, 15.00, 9, 8);

INSERT INTO Productos (productoId, nombreProducto, descripcionProducto, cantidadStock, precioVentaUnitario, precioVentaMayor, precioCompra, codigoProveedor, categoriaProductoId) 
VALUES (10, 'Filtro de Aceite', 'Filtro de aceite para automóvil', 80, 35.00, 30.00, 25.00, 10, 9);
-- -----------------------------------------------Listar Producto -----------------------------------------------------------------------------------
Delimiter $$
	Create procedure sp_listarProductos()
		Begin
			Select 
				P.productoId,
				P.nombreProducto,
				P.descripcionProducto,
				P.cantidadStock,
				P.precioVentaUnitario,
				P.precioVentaMayor,
				P.precioCompra,
				P.codigoProveedor,
				P.categoriaProductoId
			from Productos P;
		End$$
Delimiter ;
call sp_listarProductos();
-- ----------------------------------------------- Buscar Producto ---------------------------------------------------------------------------------
Delimiter $$
	Create procedure sp_buscarProductos(in pID int)
		Begin
			Select 
				P.productoId,
				P.nombreProducto,
				P.descripcionProducto,
				P.cantidadStock,
				P.precioVentaUnitario,
				P.precioVentaMayor,
				P.precioCompra,
				P.codigoProveedor,
				P.categoriaProductoId
			from Productos P;
		End$$
Delimiter ;
call sp_buscarProductos(02);
-- --------------------------------------------- Editar Producto --------------------------------------------------------------------------
Delimiter $$
	Create procedure sp_editarProducto(in _productoId int, in _nombreProducto varchar(50), in _descripcionProducto varchar(100), 
		in _cantidadStock int, in _precioVentaUnitario decimal(10,2), in _precioVentaMayor decimal(10,2), in 
			_precioCompra decimal(10,2), in _codigoProveedor int, in _categoriaProductoId int)
		Begin
			Update Productos P
			Set
			P.nombreProducto = _nombreProducto,
			P.descripcionProducto = _descripcionProducto,
			P.cantidadStock = _cantidadStock,
			P.precioVentaUnitario = _precioVentaUnitario,
			P.precioVentaMayor = _precioVentaMayor,
			P.precioCompra = _precioCompra,
			P.codigoProveedor = _codigoProveedor,
			P.categoriaProductoId = _categoriaProductoId
			where P.productoId = _productoId;
		End$$
Delimiter ;
call sp_editarProducto(01, 'Laptop', 'Laptop de última generación', 15, 1300.00, 1200.00, 1100.00, 02, 02);
-- --------------------------------------------------Eliminar Producto ----------------------------------------------------------------
Delimiter $$
Create procedure sp_eliminarProducto(in proID int)
    Begin
        Delete from Productos 
        where productoId = proID;
    End$$
Delimiter ;
call sp_eliminarProducto(01);
-- --------------------------------------------------- Agregar detalle Factura ---------------------------------------------------------------
Delimiter $$
	Create procedure sp_agregarDetalleFactura(in detalleFacturaId int, in facturaId int, in productoId int)
		Begin
			Insert Into detalleFactura(detalleFacturaId, facturaId, productoId)
			values (detalleFacturaId, facturaId, productoId);
		End$$
Delimiter ;
call sp_agregarDetalleFactura(01, 02, 03);
call sp_agregarDetalleFactura(02, 03, 02);
call sp_agregarDetalleFactura(03, 02, 03);

INSERT INTO detalleFactura (detalleFacturaId, facturaId, productoId) 
VALUES (1, 1, 1);
INSERT INTO detalleFactura (detalleFacturaId, facturaId, productoId) 
VALUES (4, 4, 4);

INSERT INTO detalleFactura (detalleFacturaId, facturaId, productoId) 
VALUES (5, 5, 5);

INSERT INTO detalleFactura (detalleFacturaId, facturaId, productoId) 
VALUES (6, 6, 6);

INSERT INTO detalleFactura (detalleFacturaId, facturaId, productoId) 
VALUES (7, 7, 7);

INSERT INTO detalleFactura (detalleFacturaId, facturaId, productoId) 
VALUES (8, 8, 8);

INSERT INTO detalleFactura (detalleFacturaId, facturaId, productoId) 
VALUES (9, 9, 9);

INSERT INTO detalleFactura (detalleFacturaId, facturaId, productoId) 
VALUES (10, 10, 10);
-- ------------------------------------------------Listar detalleFactura----------------------------------------------------------------------
Delimiter $$
	Create procedure sp_listarDetalleFactura()
		Begin
			Select 
				DF.detalleFacturaId,
				DF.facturaId,
				DF.productoId
			from detalleFactura DF;
		End$$
Delimiter ;
call sp_listarDetalleFactura();
-- -------------------------------------------------Buscar dettalleFactura------------------------------------------------------------------------
Delimiter $$
	Create procedure sp_buscarDetalleFactura(in dID int)
		Begin
			Select 
				DF.detalleFacturaId,
				DF.facturaId,
				DF.productoId
			from detalleFactura DF;
		End$$
Delimiter ;
call sp_buscarDetalleFactura(02);
-- --------------------------------------------- editar detalleFactura ------------------------------------------------------------------
Delimiter $$
	Create procedure sp_editarDetalleFactura(in _detalleFacturaId int, in _facturaId int, in _productoId int)
		Begin
			Update detalleFactura DF
			Set
			DF.facturaId = _facturaId,
			DF.productoId = _productoId
			where DF.detalleFacturaId = _detalleFacturaId;
		End$$
Delimiter ;
call sp_editarDetalleFactura(01, 02, 02);
-- ------------------------------------------------ Eliminar detalleFactura -------------------------------------------------------------
Delimiter $$
	Create procedure sp_eliminarDetalleFactura(in detID int)
		Begin
			Delete from detalleFactura 
            where detalleFacturaId = detID;
		End$$
Delimiter ;
call sp_eliminarDetalleFactura(01);

-- ----------------------------------------------- Agregar detalle Compra -----------------------------------------------------------------
Delimiter $$
	Create procedure sp_agregarDetalleCompra(in detalleCompraId int, in cantidadCompra int, in productoId int, in compraId int)
		Begin
			Insert Into detalleCompra(detalleCompraId, cantidadCompra, productoId, compraId)
			values (detalleCompraId, cantidadCompra, productoId, compraId);
		End$$
Delimiter ;
call sp_agregarDetalleCompra(01, 05, 02, 02);
call sp_agregarDetalleCompra(02, 10, 02, 02);
call sp_agregarDetalleCompra(03, 07, 03, 02);

INSERT INTO detalleCompra (detalleCompraId, cantidadCompra, productoId, compraId) 
VALUES (1, 10, 1, 1);
INSERT INTO detalleCompra (detalleCompraId, cantidadCompra, productoId, compraId) 
VALUES (4, 25, 4, 4);

INSERT INTO detalleCompra (detalleCompraId, cantidadCompra, productoId, compraId) 
VALUES (5, 30, 5, 5);

INSERT INTO detalleCompra (detalleCompraId, cantidadCompra, productoId, compraId) 
VALUES (6, 12, 6, 6);

INSERT INTO detalleCompra (detalleCompraId, cantidadCompra, productoId, compraId) 
VALUES (7, 18, 7, 7);

INSERT INTO detalleCompra (detalleCompraId, cantidadCompra, productoId, compraId) 
VALUES (8, 22, 8, 8);

INSERT INTO detalleCompra (detalleCompraId, cantidadCompra, productoId, compraId) 
VALUES (9, 14, 9, 9);

INSERT INTO detalleCompra (detalleCompraId, cantidadCompra, productoId, compraId) 
VALUES (10, 16, 10, 10);
-- -------------------------------------------- Listar detalle Compra ----------------------------------------------------------------------
Delimiter $$
	Create procedure sp_listarDetalleCompra()
		Begin
			Select 
				DC.detalleCompraId,
				DC.cantidadCompra,
				DC.productoId,
				DC.compraId
			from detalleCompra DC;
		End$$
Delimiter ;
call sp_listarDetalleCompra();
-- --------------------------------------------- Buscar detalle Compra -----------------------------------------------------------------------
Delimiter $$
	Create procedure sp_buscarDetalleCompra(in dID int)
		Begin
			Select 
				DC.detalleCompraId,
				DC.cantidadCompra,
				DC.productoId,
				DC.compraId
			from detalleCompra DC;
		End$$
Delimiter ;
call sp_buscarDetalleCompra(01);
-- -------------------------------------------- Editar detalle Compra -----------------------------------------------------------------------------
Delimiter $$
	Create procedure sp_editarDetalleCompra(in _detalleCompraId int, in _cantidadCompra int, in _productoId int, in _compraId int)
		Begin
			Update detalleCompra DC
			Set
            DC.detalleCompraId = _detalleCompraId,
			DC.cantidadCompra = _cantidadCompra,
			DC.productoId = _productoId,
			DC.compraId = _compraId
			where DC.detalleCompraId = _detalleCompraId;
		End$$
Delimiter ;
call sp_editarDetalleCompra(01, 08, 02, 02);
-- ---------------------------------------------- Eliminar detalle Compra ---------------------------------------------------------------------
Delimiter $$
	Create procedure sp_eliminarDetalleCompra(in detcID int)
		Begin
			Delete from detalleCompra 
				where detalleCompraId = detcID;
		End$$
Delimiter ;
call sp_eliminarDetalleCompra(01);



