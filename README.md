# foie_gras
Proyecto formativo del sena.

Por favor, mantener actualizado este archivo y anexar los cambios, correcciones y nuevas funcionalidades a continuacion:

 1. arreglos:

	- corregido los nombre de los modulos y controladores (camelCase).
	
	- corregido el redireccionamiento al index del dashboard cuando se hace clic en el nombre del rol.
	
	- varios fix a la hora de redireccionar (redireccionamiento en los controladores).
	
	- correccion en el modulo proveedores->listarProveedores,
ahora permite listar con dataTable();

	- se corige el error de header (r)
	
	- se corrige el modulo de pedidos, en la base de datos se unifican detalles pedidos y pedidos.
 


  
2) nuevo:

	- permiso padre ahora es el nombre del modulo, antes era la url de listar de los modulos.
	
	- ahora aparece el nombre del usuario en vez del documento cuando se valida.
	
	- se implementa el campo telefono en el modulo crearProyecto.
	
	- se implementa el modulo rol con su crud.

	- se implementa el filtro de busqueda en todos los modulos.

	- se agregaron las imagenes del filtro de busqueda

	-implementado el menu dinamico en todos los modulos->listarXxx.xxxxx, ahora no permite el acceso si no tiene el permiso.
	
	el sistema ahora permite validar si un usuario esta activo en el sistema

	-mas texto en properties español


3) experimental:

	-a la hora de hacer reportes en excel, ya no aparece la columna opciones y sus respectivos botones*
  
	- se implementa la version 0.2 de los reportes en pdf, png, json, xml, txt, sql, cvs, word y excel y sus respectivas librerias

	- Traduccion parcial de dataTable();

	- Traduccion del index, contacto y login

	- prototipo envio email "orlando"

4)ToDo:

	- vincular la tabla detalles_pedidos al modulo pedidos.
	
	- implementar los campos restantes en el modulo proyectos (converters).
	
	- quitar la columna opciones cuando se validan los ayudantes.
	
	- mejorar los reportes*.
	
	- implementar los reportes en pdf.
		
	- implementar el modulo de 'configuracion del usuario'.
	
	- implementar el modulo 'configuracion del sistema' a los usuarios root y administrador.
	
	- corregir los mensajes de error cuando se lista (todos los modulos).*
	
	- implementar la programacion, procedimientos y demas en la base de datos.

	- implementar el menu dinamico en los reportes, estados...

	- implementar el boton "cambiar estado" en todos los mudulos

	- hacer la validacion del estado de los usuarios, roles...

*por alguna razon ya no aparece el mensaje de error cuando de ingresa a cualquier modulo->listarXxx.xxxxx

  
