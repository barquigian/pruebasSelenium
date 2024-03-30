Feature: Yo como usuario
  quiero poder registrarme en el sistema
  para poder hacer uso de las funcionalidades.

  Scenario: Registro exitoso
    Given Soy un usuario no registrado
    And proporciono los datos de registro
    When invoco el servicio de registro
    Then obtengo un status code 201
    And mis datos registrados

  Scenario Outline: Validación de campos requeridos en el registro
    Given Soy un usuario no registrado
    And proporciono los datos de registro omitiendo el "<campo>"
    When invoco el servicio de registro
    Then obtengo un status code 400
    And un mensaje que indica que el "<mensaje>"
    Examples:
      | campo    | mensaje                              |
      |  usuario | El nombre de usuario es obligatorio. |
      |  clave   | La clave es obligatoria.             |

  Scenario: Usuario registrado intenta registrarse nuevamente
    Given Soy un usuario registrado
    And proporciono los datos de registro
    When invoco el servicio de registro
    Then obtengo un status code 400
    And un mensaje que indica que el "Usuario ya registrado."

  Scenario: Registro con datos incorrectos
    Given Soy un usuario no registrado
    And proporciono datos incorrectos de registro
    When invoco el servicio de registro
    Then obtengo un status code 400
    And un mensaje que indica que el "Formato de datos inválido."

  Scenario: Registro con nombre de usuario existente
    Given Soy un usuario no registrado
    And proporciono un correo de usuario existente
    When invoco el servicio de registro
    Then obtengo un status code 400
    And un mensaje que indica que el "Nombre de correo ya en uso."

  Scenario: Registro con contraseña débil
    Given Soy un usuario no registrado
    And proporciono una contraseña débil
    When invoco el servicio de registro
    Then obtengo un status code 400
    And un mensaje que indica que la "Contraseña debe tener al menos 8 caracteres."

  Scenario: Registro con datos faltantes
    Given Soy un usuario no registrado
    And no proporciono datos de registro
    When invoco el servicio de registro
    Then obtengo un status code 400
    And un mensaje que indica que "Faltan datos de registro."