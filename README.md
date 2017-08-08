# foie_gras
Proyecto formativo del sena.

Por favor, mantener actualizado este archivo y anexar los cambios, correcciones y nuevas funcionalidades a continuacion:

 1. arreglos:

	- corregido los nombre de los modulos y controladores (camelCase).
	- corregido el redireccionamiento al index del sistema cuando se hace clic en el nombre del rol.
	- varios fix a la hora de redireccionar (redireccionamiento en los controladores).
  
2) nuevo:
	- permiso padre ahora es el nombre del modulo, antes era la url de listar de los modulos.
	- ahora aparece el nombre del usuario en vez del documento cuando se valida.
	- se implementa el campo telefono en el modulo crearProyecto.
	- se implementa el modulo rol con su crud.

3) experimental:
	-a la hora de hacer reportes en excel, ya no aparece la columna opciones y sus respectivos botones*
  
4)To do:
	- vincular la tabla detalles_pedidos al modulo pedidos.
	- implementar los campos restantes en el modulo proyectos (converters)
	- quitar la columna opciones cuando se validan los ayudantes
	- mejorar los reportes*
	- implementar los reportes en pdf
	~~- implementar el modulo de 'roles'~~
	- implementar el modulo de 'configuracion del usuario'
	- implementar el modulo 'configuracion del sistema' a los usuarios root y administrador
	- corregir los mensajes de error cuando se lista (todos los modulos)
	- implementar la programacion, procedimientos y demas en la base de datos
  
